package info.angrynerds.wafflecode.utils;

import info.angrynerds.wafflecode.mvc.*;
import info.angrynerds.wafflecode.mvc.View;
import info.angrynerds.wafflecode.ui.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.text.*;
import javax.sound.midi.*;

public class Chat implements Printable, MVCListener {
	private JTextPane incoming;
	private JTextField outgoing;
	
	private StyledDocument chatDoc;
	private SimpleAttributeSet senderStyle;
	
	private String prevSender;
	
	private Controller controller;
	
	//private SimpleAttributeSet senderStyle;
	
	// Listeners
	private List<MVCListener> listeners;
	
	//To play a beep when a chat arrives.
	private Sequencer player;
	
	public Chat(Controller controller) {
		this.controller = controller;
		listeners = new ArrayList<MVCListener>();
		
		senderStyle = new SimpleAttributeSet();
		StyleConstants.setBold(senderStyle, true);
		
		//senderStyle = new SimpleAttributeSet();
		//StyleConstants.setBold(senderStyle, true);
		
		//Set up sound stuff
		
		try {
			player = MidiSystem.getSequencer();
			player.open();
			
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			
			Track track = seq.createTrack();
			
			ShortMessage a = new ShortMessage();
			a.setMessage(144, 3, 76, 110);
			MidiEvent noteOn = new MidiEvent(a, 1);
			track.add(noteOn);
			
			ShortMessage b = new ShortMessage();
			b.setMessage(128, 6, 44, 100);
			MidiEvent noteOff = new MidiEvent(b, 3);
			track.add(noteOff);
			
			player.setSequence(seq);
		}
		catch(Exception ex) {
			ex.printStackTrace();
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
	
	@Override
	public void println(Object o) {
		String str = o.toString();
		if(!str.endsWith("\n")) {
			str += '\n';
		}
		print(str);
	}
	
	@Override
	public void print(Object o) {
		print(o, null);
	}
	
	public void print(Object o, SimpleAttributeSet style) {
		try {
			chatDoc.insertString(chatDoc.getLength(), o.toString(), style);
			View view = controller.getView();
			Console console = view.getConsole();
			console.print(o);
		} 
		catch (BadLocationException e) {
			controller.getView().handleException(e,
					"the Chat print(Object, SimpleAttributeSet) method");
		}
	}
	
	public JPanel buildUI() {
		JPanel chatPanel = new JPanel(new BorderLayout());
		chatPanel.setBackground(Theme.BACKGROUND);
		chatPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,
				5, 5, 5), BorderFactory.createLineBorder(Color.BLACK)),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		incoming = new JTextPane();
			incoming.setAutoscrolls(true);
			incoming.setEditable(false);
			chatDoc = incoming.getStyledDocument();
		chatPanel.add(new JScrollPane(incoming));
		outgoing = new JTextField(20);
			outgoing.addKeyListener(new SendChatListener());
		JButton sendButton = new JButton("Send");
			sendButton.addActionListener(new SendChatListener());
		JPanel bottomChatPanel = new JPanel();
			bottomChatPanel.add(outgoing);
			bottomChatPanel.add(sendButton);
		chatPanel.add(bottomChatPanel, BorderLayout.SOUTH);		
		return chatPanel;
	}
	
	private class SendChatListener implements ActionListener, KeyListener {
		public void messageReceived() {
			String text = outgoing.getText();			// Actually get the text
			outgoing.setText("");						// Clear the text entry
			notifyMVCListeners(new MVCEvent(MVCEventType.VIEW_CHAT_SENT, text));	// Send the chat over the network
		}
		
		public void actionPerformed(ActionEvent a) {
			messageReceived();
		}

		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				messageReceived();
			}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}

	public void itemRead(MVCEvent event) {
		switch(event.getType()) {
			case CONTROLLER_CHAT_RECIEVED:
				String message = event.getText();
				String separator = ": ";
				String[] parts = message.split(separator);
				String sender = parts[0];
				String actualMessage = parts[1];
				if(!sender.equals(prevSender)) {
					// Msg sent by a new person.
					//print(sender + ": ", senderStyle);
					println(message);
				}
				else {
					// Msg sent by same person.
					println(actualMessage);
				}
				prevSender = sender;
				
				//If this part of the window is not focused, alert user
				if( !( outgoing.isFocusOwner() || incoming.isFocusOwner() ) ) {
					try {
						player.start(); //Play the sound
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
				}
				
				break;
		}
	}
}