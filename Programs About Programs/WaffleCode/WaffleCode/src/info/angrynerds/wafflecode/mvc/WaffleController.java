package info.angrynerds.wafflecode.mvc;

import java.io.File;
import java.util.*;

import info.angrynerds.wafflecode.code.Runner;
import info.angrynerds.wafflecode.network.*;
import info.angrynerds.wafflecode.ui.*;
import info.angrynerds.wafflecode.ui.dialogs.LoadingDialog;
import info.angrynerds.wafflecode.ui.dialogs.LoginDialog;
import info.angrynerds.wafflecode.utils.*;

public class WaffleController implements Controller, MVCListener {
	private View view;
	private Model model;
	private Runner runner;
	private NetworkHelper networkHelper;
        private MacHelper macHelper;
	
	/**
	 * For loading
	 */
	private LoadingDialog loadingDialog;
	
	// Listener
	private List<MVCListener> listeners;
	
	private String justInserted, justDeleted;
	
	private boolean justOpened;
	
	public void runApplication() {
		listeners = new ArrayList<MVCListener>();
		try {
			com.apple.eawt.FullScreenUtilities.toString();
                macHelper = new MacHelper(this);
                macHelper.go();
		} catch(Error err) {
			System.err.println("[WaffleCode] Well darn I guess com.apple doesn't exist.");
		}
		
		try {
			model = new WaffleModel(this);
			view = new WaffleView(this);
			
			view.getChat().addMVCListener(this);// So we'll know when chat sends a msg.
			addMVCListener(view.getChat()); //So chat will know when we get a msg.
			
			runner = new Runner( this );

			// Get saved data.
			//model.loadProps();

			LoginDialog dialog = new LoginDialog(this);
			dialog.showDialog();
		} catch(Exception ex) {
			view.handleException(ex, "the Controller runApplication() method");
		}
	}
	
	/**
	 * Wrapper method for loading ONLY.
	 * @param i The progress, out of 100.
	 */
	private void progress() {
		loadingDialog.progress();
	}
	
	public void usernameCallback(String username, String IP) {
		try {
			this.loadingDialog = new LoadingDialog( 10 );
			loadingDialog.showDialog();				progress();
			view.getConsole().println("Start of usernameCallback(); username = " +
					username + "; IP = " + IP);		progress();
			networkHelper = new NetworkHelper(this, username, IP);
													progress();
			view.getConsole().println("After networkHelper constructor");
													progress();

			networkHelper.addReaderListener(this);	progress();
			networkHelper.setUpNetworking();		progress();
			
			loadingDialog.hideDialog();
			view.setVisible(true);
		} catch(Exception ex) {
			view.handleException(ex,
				"the controller usernameCallback() method");
		}
	}
	
	public void addMVCListener(MVCListener listener) {
		listeners.add(listener);
	}
	
	public void notifyMVCListeners(MVCEvent event) {
		for(MVCListener listener:listeners) {
			listener.itemRead(event);
		}
	}
	
	public String getUsername() {
		return networkHelper.getUsername();
	}
	
	public View getView() {
		return view;
	}

	public Model getModel() {
		return model;
	}
	
	public void setIP(String ip) {
		Globals.setServerIP(ip);
	}

	public File getDoc() {
		return view.getDoc();
	}
	
	public NetworkHelper getNetworkHelper() {
		return networkHelper;
	}

	@Override
	public void itemRead(MVCEvent event) {
		
		System.out.println("[Controller] About to read MVCEvent [" + event.getText() + "]");
		
		switch(event.getType()) {
		case NETWORK_MESSAGE_RECIEVED:
			// Categorize message
			if(event.getText().contains(Globals.INSERT_START_FLAG) && event.getText().contains(Globals.INSERT_SEPARATOR)) {
				// Text was inserted, so pass on event
				String content = event.getText().substring( event.getText().indexOf(Globals.INSERT_START_FLAG)
						+ Globals.INSERT_START_FLAG.length());
				System.out.println("content is " + content);
				
				String[] parts = content.split(Globals.INSERT_SEPARATOR);
				String text = parts[0];
				int location = Integer.parseInt( parts[1] );
				File doc = new File( parts[2] );
				Project proj = model.findProject( Integer.parseInt(parts[3]) );
				
				System.out.println("text: " + text + ", location: " + location);
				
				if( !event.getText().substring(0, event.getText().indexOf( ": " ) ).equals( getUsername() ) ) {
					notifyMVCListeners(new MVCEvent(MVCEventType.CONTROLLER_CODE_INSERTED,
							text,
							location,
							doc,
							proj));
					
					justInserted = text;
				}
			}
			else if(event.getText().contains(Globals.DELETE_START_FLAG)) {
				// Text was deleted, so pass on event
				String content = event.getText().substring( event.getText().indexOf(Globals.DELETE_START_FLAG)
						+ Globals.DELETE_START_FLAG.length());
				System.out.println("content is " + content);
				
				String[] parts = content.split(Globals.DELETE_SEPARATOR);
				String text = parts[0];
				int location = Integer.parseInt( parts[1] );
				Project proj = model.findProject( Integer.parseInt(parts[3]) );
				File doc = new File( proj.getDir(), parts[2] );
				
				System.out.println("text: " + text + ", location: " + location);
				
				if( !event.getText().substring(0, event.getText().indexOf( ": " ) ).equals( getUsername() ) ) {
					notifyMVCListeners(new MVCEvent(MVCEventType.CONTROLLER_CODE_DELETED,
							text,
							location,
							model.getDoc(),
							model.getProject()));
					
					justDeleted = text;
				}
			}
			else {
				// Assume it's a chat
				notifyMVCListeners(new MVCEvent(MVCEventType.CONTROLLER_CHAT_RECIEVED, event.getText()));
			}
			break;
		case VIEW_CHAT_SENT:
			// The user sent a message, so just pass it on to the NetworkHelper
			if(networkHelper.isReady()) {
				networkHelper.sendTextToServer(networkHelper.getUsername() + ": " + event.getText());
			}
			else {
				//save in offline mode.
			}
			break;
		case VIEW_CODE_INSERTED:
			// The user inserted code
			networkHelper.sendTextToServer(getUsername() + ": " + event.toString());
			model.saveCurrentDoc();
		case VIEW_CODE_DELETED:
			// The user deleted code
			networkHelper.sendTextToServer(getUsername() + ": " + event.toString());
			model.saveCurrentDoc();
		}
	}
	
	@Override public String justInserted() { return justInserted; }
	@Override public String justDeleted() { return justDeleted; }
	@Override public void acknowledgeInsert() { justInserted = null; }
	@Override public void acknowledgeDelete() { justDeleted = null; }

	@Override
	public Runner getRunner() {
		return runner;
	}

	@Override
	public void runCode() {
		runner.runJava( model.getProject() );
	}

	@Override
	public boolean justOpened() {
		return justOpened;
	}
	
	@Override
	public void setJustOpened( boolean b ) {
		justOpened = b;
	}
        
        @Override
        public MacHelper getMacHelper() {
            return macHelper;
        }
}