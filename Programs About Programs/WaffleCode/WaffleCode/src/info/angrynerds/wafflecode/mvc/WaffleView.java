package info.angrynerds.wafflecode.mvc;

import info.angrynerds.wafflecode.ui.*;
import info.angrynerds.wafflecode.ui.dialogs.CreateClassDialog;
import info.angrynerds.wafflecode.ui.dialogs.CreateFileDialog;
import info.angrynerds.wafflecode.ui.dialogs.CreateProjectDialog;
import info.angrynerds.wafflecode.ui.dialogs.DialogDoneListener;
import info.angrynerds.wafflecode.utils.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.plaf.synth.*;

public class WaffleView extends View implements Printable, MVCListener {
	private JFrame frame;
	private Controller controller;
	private Console console;
	private ErrorHandler errorHandler;
	
	// CHAT
	private Chat chat;
	
	// CODE
	private CodeEditor code;
	private JScrollPane codeScroller;
	private JTextField title;
	private SyntaxHighlighter highlighter;
	private AutoCompleter ac;
	
	// CONSOLE
	private JPanel consoleCardPanel;
	private String CONSOLE_CARD = "Console Panel";
	private JPanel consoleCard;
	private JTextArea consoleArea;
	private String EMPTY_CARD = "Empty Panel";
	private JPanel emptyCard;
	
	// FILE BROWSER
	private FileBrowser files;
	
	// Model
	private Model model;
	private Project project;
	private ArrayList<File> tabs;
	private File doc;//the file that's actually open
	
	public WaffleView(Controller controller) {
		this.controller = controller;
		chat = new Chat(controller);
		files = new FileBrowser(controller);
		console = new Console();
		console.addMVCListener(this);
		chat.addMVCListener(this);
		controller.addMVCListener(this);
		errorHandler = new ErrorHandler(console);
		tabs = new ArrayList<File>();
		
		model = controller.getModel();
	}
	
	public void openProject() {
		System.setProperty("apple.awt.fileDialogForDirectories", "true");//A Mac thing
		FileDialog chooser = new FileDialog( frame );
		chooser.setVisible(true);
		String f = chooser.getFile();
		
		if(f != null) {
			File dir = new File( chooser.getDirectory() + f );
		
			Project p = new Project(dir.getName(), dir, controller);
			controller.getModel().selectProject(p);
			
			selectProject( p );
		}
	}
	
	public void selectProject(Project proj) {
		project = proj;
		// Conduct some sanity checks...
		assert proj != null : "The project (local variable \"proj\") was null!";
		assert title != null : "The title was null.";
		title.setText( project.getTitle() );
		
		files.updateNodes( project.getDir() );
	}
	
	public void openDoc(File doc) {
		if( !tabs.contains( doc ) ) {
			tabs.add(doc);
			this.doc = doc;
			code.setText( model.getText(doc) );
			title.setText( doc.getName() );
			
			frame.getRootPane().putClientProperty("Window.documentFile", new File( doc.getAbsolutePath() )); //Tells the Window that this has a doc.
		}
                code.setEnabled(true);
                title.setEnabled(true);
	}
	
	public void setVisible(boolean visible) {
		if(frame == null) {
			buildGUI();
		}
		console.println("About to make frame visible...");
		frame.setVisible(visible);
		console.println("Just made frame visible.");
		console.println(" === Result of code.getText() (second time) ===");
		console.println(code.getText());
		console.println(" === END RESULT ===");

		startNullUsernameJob();
	}

	/**
	 * After 5 seconds, enable the console to start popping up the window.
	 */
	private void startNullUsernameJob() {
		Runnable job = new Runnable() {
			public void run() {
				try {
					Thread.sleep(5000);
				} catch(InterruptedException ex) {
					ex.printStackTrace();
				}
				// Let the console display messages
				console.println(Globals.NULL_USERNAME);
			}
		};
		Thread thread = new Thread(job);
		thread.start();
	}

	private void buildGUI() {
		if(frame == null) {
			frame = new JFrame("WaffleCode (" + Globals.CLIENT_VERSION + ")");
			setUpMenuBar();
			JPanel chatPanel = chat.buildUI();
			JPanel filePanel = files.buildUI( new File("/Users/" + System.getProperty("user.name") + "/Dropbox/Angry Nerds/WaffleCode/Prototype Project") );
			JPanel codePanel = new JPanel(new BorderLayout());
				codePanel.setBackground(Theme.BACKGROUND);
				codePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
				code = new CodeEditor();
                                        code.setEnabled(false);
					code.setAutoscrolls(true);
					System.out.println(" === Result of code.getText() (first time) ===");
					System.out.println(code.getText());
					System.out.println(" === END RESULT ===");
					code.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						ac = new AutoCompleter(this);
						code.addCaretListener(ac);
						code.addKeyListener(ac);
						code.getDocument().addDocumentListener( new EditListener( controller ) );
						JPanel noWrapPanel = new JPanel( new BorderLayout() );
						noWrapPanel.add(BorderLayout.CENTER, code);
						highlighter = new SyntaxHighlighter(code);
						Timer t = new Timer(1000, highlighter);
						t.setRepeats(true);
						t.start();
						codeScroller = new JScrollPane(noWrapPanel,
				                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				codePanel.add(codeScroller, BorderLayout.CENTER);
				title = new JTextField(20);
					title.setHorizontalAlignment(JTextField.CENTER);
					title.setBackground(Theme.BACKGROUND);
					title.setBorder(null);
					title.addFocusListener( new RenamingListener() );
					title.addActionListener( new RenamingListener() );
                                        title.setEnabled(false);
				JPanel titlePanel = new JPanel();	// Just for layout and aesthetic purposes
					titlePanel.add(title);
					titlePanel.setBackground(Theme.BACKGROUND);
				codePanel.add(titlePanel, BorderLayout.NORTH);
			consoleCardPanel = new JPanel(new CardLayout());
				consoleCardPanel.setBackground(Theme.BACKGROUND);
				consoleCardPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				emptyCard = new JPanel();
				emptyCard.setBackground(Theme.BACKGROUND);
				consoleCardPanel.add(emptyCard, EMPTY_CARD);
				consoleCard = new JPanel(new BorderLayout());
				consoleArea = new JTextArea(10, 20);
					consoleArea.setEditable(false);
					consoleArea.setFont( Theme.CONSOLE_FONT );
				consoleCard.add(new JScrollPane(consoleArea));
			consoleCardPanel.add(consoleCard, CONSOLE_CARD);
			frame.getContentPane().add(consoleCardPanel, BorderLayout.SOUTH);
			frame.getContentPane().add(codePanel, BorderLayout.CENTER);
			frame.getContentPane().add(filePanel, BorderLayout.WEST);
			frame.getContentPane().add(chatPanel, BorderLayout.EAST);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setBounds(View.getCenteredBounds(1000, 600));
			frame.setBackground(Theme.BACKGROUND);
                        
                        controller.getMacHelper().enableFullScreen();
			
			// UNCOMMENT TO ENABLE POINTLESS FUN.
			//frame.getRootPane().putClientProperty("Window.alpha", new Float(0.5));
		}
	}
	
        private void style() {
            try {
                SynthLookAndFeel laf = new SynthLookAndFeel();
                laf.load( WaffleView.class.getResourceAsStream("laf.xml"), WaffleView.class);
                UIManager.setLookAndFeel( laf );
            }
            catch(Exception ex) {
                ex.printStackTrace();;
            }
        }
        
	private void setUpMenuBar() {
		if(frame != null) {			
			JMenuBar bar = MenuMaker.initMenu(controller, code);
			frame.setJMenuBar(bar);
		}
	}
	
	public void setConsoleVisible(boolean visible) {
		CardLayout layout = (CardLayout) consoleCardPanel.getLayout();
		layout.show(consoleCardPanel, (visible ? CONSOLE_CARD : EMPTY_CARD));
	}

	public void inform(String updated) {/*
		if(updated != code.getText()) {
			code.setText(updated);
		}
	*/}
	
	public CodeEditor getCodeEditor() {
		return code;
	}

	public File getDoc() {
		return doc;
	}

	public void println(Object o) {
		chat.println(o);
	}

	public void print(Object o) {
		chat.print(o);
	}

	public Printable getPrintable() {
		return (Printable) chat;
	}

	public Chat getChat() {
		return chat;
	}

	public Console getConsole() {
		return console;
	}

	public void itemRead(MVCEvent event) {
		console.println("[WaffleView/itemRead()] Got a " + event.toString());
		switch(event.getType()) {
		case CONSOLE_MESSAGE:
			if(frame == null) {
				System.out.println("[WaffleView] Just got MVCEvent" +
						" - frame is null, so building GUI");
				buildGUI();
			}
			String str = event.getText();
			consoleArea.append(str);
			break;
		case CONTROLLER_CODE_INSERTED:
			try {
				code.getDocument().insertString( event.getOffset(), event.getText(), null );
			} 
			catch (BadLocationException e) {
				e.printStackTrace();
			}
			break;
		case CONTROLLER_CODE_DELETED:
			try {
				code.getDocument().remove( event.getOffset(), event.getLength() );
			} 
			catch (BadLocationException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	/**
	 * This method just delegates to the ErrorHandler.
	 * @see ErrorHandler.handleException(Exception, String)
	 * @param ex The exception
	 * @param location Where this method is called.
	 */
	public void handleException(Exception ex, String location) {
		errorHandler.handleException(ex, location);
	}
	
	@Override
	public JFrame getFrame() {
		return frame;
	}
	
	@Override
	public void createProject() {
		CreateProjectDialog dialog = new CreateProjectDialog( controller );
		dialog.showDialog();
		dialog.addDoneListener( new MakeProjectListener() );
	}
	

	@Override
	public void createClass() {
		CreateClassDialog dialog = new CreateClassDialog( controller );
		dialog.showDialog();
		dialog.addDoneListener( new MakeClassListener() );
	}

	@Override
	public void createFile() {
		CreateFileDialog dialog = new CreateFileDialog( controller );
		dialog.showDialog();
		dialog.addDoneListener( new MakeFileListener() );
	}
	
	private class RenamingListener implements ActionListener, FocusListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			File newDoc = new File( doc.getParent(), title.getText() );
			doc.renameTo( newDoc );
			// tell filebrowser to update
			// send over server
		}

		@Override
		public void focusGained(FocusEvent arg0) { }

		@Override
		public void focusLost(FocusEvent arg0) {
			// if they didn't press return, the file shall not be renamed.
			//title.setText( doc.getName() );
		}
	}
	
	private class MakeProjectListener implements DialogDoneListener {

		@Override
		public void dialogDone(Object data) {
			Project project = (Project) data;
			
			controller.getModel().createProject( project );
			
			selectProject( project );
		}
	}
	
	private class MakeClassListener implements DialogDoneListener {

		@Override
		public void dialogDone(Object data) {
			File klass = (File) data;
			controller.getModel().saveDoc( klass, "public class Foo {\n\t\n}" );
			openDoc( klass );
		}
		
	}
	
	private class MakeFileListener implements DialogDoneListener {

		@Override
		public void dialogDone(Object data) {
			File file = (File) data;
			controller.getModel().saveDoc( file, "" );
		}
		
	}
}