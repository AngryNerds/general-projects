package info.angrynerds.wafflecode.network;

import info.angrynerds.wafflecode.mvc.View;
import info.angrynerds.wafflecode.utils.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

/**
 * This is <strong>THE</strong> server, which connects with all the clients.
 * It can:
 * <ul>
 * <li>Talk with all the clients</li>
 * <li>Print out detailed debug information</li>
 * <li>See all messages sent anywhere</li>
 * <li>See all of the IPs and usernames of all the clients</li>
 * <li>Handle keystrokes made in the program code area</li>
 * </ul>
 * @author Daniel Glus
 * @version 1.0
 */
public class WaffleServer {
	private ArrayList<ClientHandler> clientHandlers;
	private int numberOfClients = 0;
	private int messagesRecieved = 0;
	private boolean VERBOSE = true;
	private boolean TAKE_A_LONG_TIME_TO_LOAD = false;
	
	private JFrame frame;
	private JTextArea area;
	private JTextField chatField;
	private JLabel ipLabel;
	private JLabel portLabel;
	private JLabel numClientsLabel;
	private JLabel messagesLabel;
	private JTextArea clientList;
	private JCheckBox debugOutput;
	
	private JFrame loadingFrame;
	private JLabel loadingLabel;
	private String loadingText = "Instantiating donuts...";
	private JProgressBar loadingBar;
	
	/**
	 * Run this to run the server, NOT the application.
	 * @param args Nobody uses this, definitely not this application.
	 * 
	 * <svg xmlns="http://www.w3.org/2000/svg" version="1.1" height="190">
	 * <polygon points="100,10 40,180 190,60 10,60 160,180"
	 * style="fill:lime;stroke:purple;stroke-width:5;fill-rule:evenodd;" />
	 * </svg>
	 */
	public static void main(String[] args) {
		new WaffleServer().go();
	}
	
	/**
	 * This sets up the loading thing.
	 */
	public void initLoadFrame() {
		loadingFrame = new JFrame("Loading WaffleCode Server...");
		GridLayout layout = new GridLayout(2, 1);
		layout.setHgap(10);
		layout.setVgap(10);
		JPanel mainPanel = new JPanel(layout);
			mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			loadingLabel = new JLabel("Loading... 0%");
			mainPanel.add(loadingLabel);
			loadingBar = new JProgressBar();
			loadingBar.setStringPainted(true);
			mainPanel.add(loadingBar);
		loadingFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		loadingFrame.setBounds(View.getCenteredBounds(500, 120));
		loadingFrame.setVisible(true);
	}
	
	public void progress(int progress) {
		loadingLabel.setText("Loading... " + progress + "%                " + loadingText);
		loadingBar.setValue(progress);
		if(TAKE_A_LONG_TIME_TO_LOAD) {
			try {
				Thread.sleep(100 + (int)(Math.random() * 100));
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		if(progress == 100) {
			loadingFrame.setVisible(false);
		}
	}
	
	public void progress(int progress, String message) {
		loadingText = message;
		progress(progress);
	}
	
	/**
	 * This is the main method for initializing the program.  It is responsible for initializing the server.
	 * It also contains the main loop that waits for connections and grants them individual connections.
	 */
	public void go() {
		initLoadFrame();
		clientHandlers = new ArrayList<ClientHandler>();
		progress(1);
		buildGUI();
		println("**************************");
		println("WaffleCode Server - " + Globals.SERVER_VERSION);
		println("Author: Daniel Glus");
		println("SERVER CONSOLE");
		println("**************************");
		println("Type \"help\" (without the quotes) into the command box at the\n\t\tbottom" +
				" to obtain a list of the commands.");
		println("Initialized non-network stuff.");
		try {
			ServerSocket serverSock = new ServerSocket(Globals.SERVER_PORT);
			println("Initialized ServerSocket.");
			println("Current port: " + Globals.SERVER_PORT);
			println("-");
			while(true) {			// START MAIN LOOP
				println("[MainLoop] Waiting for a connection...");
				Socket clientSocket = serverSock.accept();
				if(VERBOSE) println("[MainLoop] ServerSocket accepted a client socket.");
				ClientHandler handler = new ClientHandler(clientSocket);
				clientHandlers.add(handler);
				Thread t = new Thread(handler);
				t.start();
				println("[MainLoop] Got a connection!\n" + ++numberOfClients +
						" people are now connected.\n-");
				updateInfo();
			}
		} catch(IOException ex) {
			handleException(ex, "the server while loop");
		}
	}
	
	/**
	 * Tells everyone the message, with no formatting.
	 * @param message The message to be sent to everyone in the <code>clientHandlers</code> list.
	 */
	public void tellEveryone(String message) {
		for(ClientHandler handler:clientHandlers) {
			PrintWriter writer = new PrintWriter(handler.getSocketOutputStream());
			writer.println(message);
			writer.flush();
		}
	}
	
	/**
	 * Updates the information on the right side of the window.
	 */
	public void updateInfo() {
		ipLabel.setText( Globals.SERVER_IP );
		portLabel.setText(Globals.SERVER_PORT + "");
		numClientsLabel.setText( String.format("%,d", numberOfClients) );
		messagesLabel.setText( String.format("%,d", messagesRecieved) );
		
		if(clientHandlers.size() > 0) {
			// StringBuffers are thread-safe versions of StringBuilders.
			StringBuffer builder = new StringBuffer();
			int i = 1;
			for(ClientHandler handler:clientHandlers) {
				String ip = (handler.getIP().equals(Globals.LOCALHOST_IP))
				?"Localhost":handler.getIP();
				builder.append(i++ + ") IP: " + ip +
						((!handler.getUsername().equals(Globals.NULL_USERNAME))
						?("\n   Username: " + handler.getUsername()):"") + "\n");
			}
			clientList.setText(builder.toString());
		} else {
			clientList.setText("<no clients are connected>");
		}
	}
	
	public void println(Object o) {
		String str = o.toString();
		str += (str.endsWith("\n"))?"":"\n";
		print(str);
	}
	
	public void print(Object o) {
		area.append(o.toString());
		area.setCaretPosition(area.getText().length());
	}
	
	public void buildGUI() {
		frame = new JFrame("WaffleCode Server (" + Globals.SERVER_VERSION + ")");	progress(3, "Meditating...");
		JPanel mainPanel = new JPanel(new BorderLayout());							progress(4);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));		progress(6);
		area = new JTextArea();														progress(7);
		area.setEditable(false);													progress(9);
		area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));					progress(10,
				"Slamming revolving doors...");
		area.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));				progress(11);
		mainPanel.add(new JScrollPane(area));										progress(12);
		JPanel bottom = new JPanel(new BorderLayout());								progress(13);
		// TOP, LEFT, BOTTOM, RIGHT
		bottom.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));			progress(14);
		JPanel buttonsPanel = new JPanel();											progress(15);
			JPanel chatPanel = new JPanel();										progress(16);
			chatPanel.setBorder(BorderFactory.createTitledBorder("Chat"));			progress(17);
			chatField = new JTextField(40);											progress(18);
			chatField.addKeyListener(new GUIListener());							progress(19);
			chatPanel.add(chatField);												progress(20, "Slamming revolving doors...");
			JButton sendButton = new JButton("Send Chat");							progress(21);
			sendButton.addActionListener(new GUIListener());						progress(22);
			chatPanel.add(sendButton);												progress(23);
			buttonsPanel.add(chatPanel);											progress(24);
			JButton clearButton = new JButton("Clear Screen");						progress(25);
				clearButton.addActionListener(new GUIListener());					progress(26);
			buttonsPanel.add(clearButton);											progress(27);
		bottom.add(buttonsPanel, BorderLayout.EAST);								progress(28);
		JPanel right = new JPanel();												progress(30, "Finding Waldo...");
		right.setLayout(new BorderLayout());										progress(31);
			JPanel serverInfo = new JPanel();										progress(33);
			serverInfo.setLayout(new GridLayout(6, 2));								progress(34);
			((GridLayout)serverInfo.getLayout()).setHgap(5);						progress(36);
			((GridLayout)serverInfo.getLayout()).setVgap(5);						progress(37);
			serverInfo.setBorder(BorderFactory.createTitledBorder("Server Info"));	progress(39);
			serverInfo.add(new JLabel("Server Version"));							progress(40,
					"Eating a 72-ounce steak...");
			serverInfo.add(new JLabel(Globals.SERVER_VERSION));						progress(42);
			serverInfo.add(new JLabel("Client Version"));							progress(43);
			serverInfo.add(new JLabel(Globals.CLIENT_VERSION));						progress(45);
			serverInfo.add(new JLabel("Server IP"));								progress(46);
			ipLabel = new JLabel("asdf");											progress(48);
			serverInfo.add(ipLabel);												progress(49);
			serverInfo.add(new JLabel("Server Port"));								progress(51,
					"Fighting a ferocious bugbear...");
			portLabel = new JLabel("asdf");											progress(53);
			serverInfo.add(portLabel);												progress(54);
			serverInfo.add(new JLabel("# of clients"));								progress(55);
			numClientsLabel = new JLabel("asdf");									progress(57);
			serverInfo.add(numClientsLabel);										progress(58);
			serverInfo.add(new JLabel("Lifetime messages"));						progress(60, "Reading law textbooks...");
			messagesLabel = new JLabel("asdf");										progress(61);
			serverInfo.add(messagesLabel);											progress(63);
		right.add(serverInfo, BorderLayout.NORTH);									progress(64);
		JPanel clientListPanel = new JPanel(new BorderLayout());					progress(66);
			clientListPanel.setBorder(BorderFactory.createTitledBorder("Client List"));		progress(67);
			clientList = new JTextArea();											progress(68);
			clientList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));		progress(70, 
					"Finding blue stuff to fill up this loading bar...");
			clientList.setEditable(false);											progress(71);
			clientList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));			progress(73);
			clientList.setText("asdf");												progress(74);
			clientListPanel.add(new JScrollPane(clientList));						progress(76);
		right.add(clientListPanel, BorderLayout.CENTER);							progress(78);
		JPanel outputPanel = new JPanel(new BorderLayout());						progress(80,
				"Finding blue stuff to fill up this loading bar...");
			outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));		progress(81);
			debugOutput = new JCheckBox("Print debugging information");				progress(83);
			debugOutput.addActionListener(new GUIListener());						progress(84);
			debugOutput.setSelected(VERBOSE);										progress(86);
			outputPanel.add(debugOutput);											progress(87);
		right.add(outputPanel, BorderLayout.SOUTH);									progress(89);
		frame.getContentPane().add(right, BorderLayout.EAST);						progress(91, "Actually loading...");
		frame.getContentPane().add(bottom, BorderLayout.SOUTH);						progress(93);
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);					progress(95);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);						progress(98);
		frame.setBounds(View.getCenteredBounds(800, 500));							progress(99);
		progress(100);
		frame.setVisible(true);
		updateInfo();
	}
	
	private void handleException(Exception ex, String location) {
		handleException(ex, location, null);
	}
	
	private void handleException(Exception ex, String location,
			ClientHandler problem) {
		String exceptionName = ex.getClass().getSimpleName();
		println("\n===DARN THERE WAS AN ERROR===");
		println("There was a " + exceptionName + " at " + location + ".");
		println("Exception message: " + ex.getLocalizedMessage() + ".");
		if(ex.getLocalizedMessage().equals("Connection reset")) {
			println("The client probably disconnected.");
			println("Removed client " +
					(problem.getUsername().equals(Globals.NULL_USERNAME)
							?("with ip " +
					problem.getIP()):problem.getUsername()) +
					" from client list.");
			clientHandlers.remove(problem);
			println(--numberOfClients + " people are now connected.");
		}
		println(exceptionName + " stack trace:");
		ex.printStackTrace(new PrintWriter(new AreaWriter()));
		ex.printStackTrace();
		updateInfo();
	}
	
	private class AreaWriter extends Writer {
		public void close() throws IOException {
			println("[AreaWriter] AreaWriter closed.");
		}

		public void flush() throws IOException {
			println("[AreaWriter] AreaWriter flushed.");
		}

		public void write(char[] arg0, int arg1, int arg2) throws IOException {
			StringBuilder string = new StringBuilder();
			for(int i = arg1; i < arg2; i++) {
				string.append(arg0[i]);
			}
			print(string.toString());
		}
	}
	
	/**
	 * This class serves two main purposes:
	 * <ol>
	 * <li>Act as a thread job assigned to Threads, one per user, that capture chats sent
	 * from the user and pass the chats on to everybody.</li>
	 * <li>Act as a database item that holds the IP and the username of the client that is
	 * represented by this particular ThreadHandler.</li>
	 * </ol>
	 * <style type="text/css">
	 * ::selection{
	 * background-color: #FF6EB4;
	 * }
	 * </style>
	 * @author Daniel Glus
	 */
	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket sock;
		private String username = Globals.NULL_USERNAME;
		
		public ClientHandler(Socket clientSocket) {
			try {
				sock = clientSocket;
				println("[ClientHandler()] Local port of client is: " +
						sock.getLocalPort());
				println("[ClientHandler()] Remote port of client is: " +
						sock.getPort());
				println("[ClientHandler()] Client host address is: " +
						sock.getInetAddress().getHostAddress());
				InputStreamReader isr = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isr);
			} catch(IOException ex) {
				println("aw nuts, the ClientHandler constructor threw an ioexception");
				handleException(ex, "the ClientHandler constructor");
			}
		}
		
		public String getIP() {
			return getSocket().getInetAddress().getHostAddress();
		}

		public OutputStream getSocketOutputStream() {
			try {
				return sock.getOutputStream();
			} catch (IOException e) {
				handleException(e, "getSocketOutputStream() in ClientHandler");
			}
			return null;
		}
		
		
		public void run() {
			String message;
			//try {
			try {
				while( (message = reader.readLine()) != null ) {
					String userid = message.split(": ")[0];
					if(username.equals(Globals.NULL_USERNAME)) {
						username = userid;
					}
					if(message.contains(Globals.INSERT_START_FLAG)) {
						String text = message.split(Globals.INSERT_START_FLAG)[1];
						println("[ClientHandler] " + userid + " inserted \"" + text + "\".");
						tellEveryone(message);
					} else if(message.contains(Globals.DELETE_START_FLAG)) {
						println("[ClientHandler] " + userid + " deleted text.");
						tellEveryone(message);
					} else {
						String text = message.substring(message.indexOf(":") + 1);
						println("[ClientHandler] Got a chat message from " + userid + ": \"" + text + "\".");
						tellEveryone(message);
						println("[ClientHandler] Just told everyone the message.");
						messagesRecieved++;
					}
					updateInfo();
				}
			}
			catch(SocketException e1) {
				handleException(e1, "the ClientHandler while loop");
			} 
			catch (IOException e2) {
				handleException(e2, "the ClientHandler while loop");
			}
		}

		public Socket getSocket() {
			return sock;
		}

		public String getUsername() {
			return username;
		}
	}

	public class GUIListener implements ActionListener, KeyListener {
		private void onMessageSentFromServer() {
			println("[GUIListener] Telling everyone: \"" + chatField.getText() + "\".");
			messagesRecieved++;
			tellEveryone("<SERVER>: " + chatField.getText());
			chatField.setText("");
			updateInfo();
		}

		public void actionPerformed(ActionEvent a) {
			Object source = a.getSource();
			if(source instanceof JCheckBox) {
				VERBOSE = debugOutput.isSelected();
				println("[DebugOutput] Debug information will " + (VERBOSE?"":"not ") + "be printed.");
			}
			else if(source instanceof JButton) {
				String text = ((JButton)source).getText();
				if(text.equals("Clear Screen")) {
					area.setText("");
				}
				else if(text.equals("Send Chat")) {
					onMessageSentFromServer();
				}
			}
		}

		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				onMessageSentFromServer();
			}
		}

		public void keyReleased(KeyEvent arg0) {}
		public void keyTyped(KeyEvent arg0) {}
	}
}