package info.angrynerds.wafflecode.network;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import info.angrynerds.wafflecode.mvc.*;
import info.angrynerds.wafflecode.utils.*;

/**
 * This class is the only class that the client <i>should</i> use to send things over
 *  the network.
 * It allows the client to communicate with the server.
 * @author Daniel Glus
 */
public class NetworkHelper {
	private Printable console;
	private Controller controller;
	private boolean ready;
	
	// Networking and streams
	private InputStreamReader streamReader;
	private BufferedReader reader;
	private PrintWriter writer;
	private Socket socket;
	
	// For chat
	private String username;
	
	// For StreamEvents
	private List<MVCListener> listeners;
	
	public NetworkHelper(Controller controller, String username, String IP) {
		this.controller = controller;
		this.console = controller.getView().getConsole();
		this.username = username;
		Globals.setServerIP(IP);
		listeners = new ArrayList<MVCListener>();
	}
	
	public void addReaderListener(MVCListener listener) {
		listeners.add(listener);
	}
	
	public void notifyMVCListeners(MVCEvent event) {
		console.println("[NetworkHelper] Throwing " + event.toString());
		for(MVCListener listener:listeners) {
			listener.itemRead(event);
		}
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public void setUpNetworking() {
		if(!ready) {
			try {
				console.println("[NetworkHelper] Attempting to connect to server...");
				socket = new Socket(Globals.SERVER_IP, Globals.SERVER_PORT);
				streamReader = new InputStreamReader(socket.getInputStream());
				reader = new BufferedReader(streamReader);
				writer = new PrintWriter(socket.getOutputStream());
				console.println("[NetworkHelper] Connection to server established.");
				Thread readerThread = new Thread(new IncomingReader());
				readerThread.start();
				console.println("[NetworkHelper] Reader thread started.");
				ready = true;
			} catch(ConnectException ex) {
				if(ex.getLocalizedMessage().startsWith("Connection refused")) {
					console.println("[Network] Oh, umad - the server doesn't" +
							" seem to be on, so you can't chat");
					System.out.println("[Network] Oh, umad - the server doesn't" +
							" seem to be on, so you can't chat");
				}
				controller.getView().handleException(ex,
					"the NetworkHelper setUpNetworking() method");
				console.println("[Network] Attempt failed because of " +
						"ConnectException: " + ex.getLocalizedMessage());
				
				//INITIATE OFFLINE MODE (which works like github)
			} catch(IOException ex) {
				controller.getView().handleException(ex,
						"the NetworkHelper setUpNetworking() method");
				console.println("[Network] Attempt failed because of" +
						" IOException: " +	ex.getLocalizedMessage());
			}
		}
	}

	public String getUsername() {
		return username;
	}

	public void sendTextToServer(String text) {
		if(ready) {
			try {
				writer.println(text);
				writer.flush();
				console.println("Just sent some text to the server.");
				console.println("Namely, \"" + text + "\"");
			} catch(Exception ex) {
				controller.getView().handleException(ex,
						"the NetworkHelper sendTextToServer() method");
				JOptionPane.showConfirmDialog(null,
						"There was an error...", "An Error!",
						JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
		else {
			System.out.println("[NetworkHelper] Oh, darn... Somebody" +
				" tried to send text to the server, but I wasn't ready.");
			console.println("[NetworkHelper] Oh, darn... Somebody" +
					" tried to send text to the server, but I wasn't ready.");
		}
	}
	
	private class IncomingReader implements Runnable {
		public void run() {
			try {
				while(true) {
					while(!reader.ready()); //Wait for it...
					String data = reader.readLine();
					notifyMVCListeners(new MVCEvent(MVCEventType.NETWORK_MESSAGE_RECIEVED, data));
				}
			} catch(Exception ex) {
				controller.getView().handleException(ex, "the IncomingReader in NetworkHelper");
				JOptionPane.showConfirmDialog(null,
						"There was an error in NetworkHelper's IncomingReader:\n" +
						ex.getLocalizedMessage(), "An Error Has Occured!",
						JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}