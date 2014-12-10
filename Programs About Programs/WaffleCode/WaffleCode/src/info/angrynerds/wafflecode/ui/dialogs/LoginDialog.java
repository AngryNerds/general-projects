package info.angrynerds.wafflecode.ui.dialogs;

import java.awt.*;
import java.awt.event.*;

import info.angrynerds.wafflecode.mvc.*;
import info.angrynerds.wafflecode.utils.*;

import javax.swing.*;

/**
 * This class also does loading.
 * @author Daniel Glus
 */
public class LoginDialog extends Dialog {
	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTextField serverIPField;
	private JButton loginButton;
	private JLabel feedbackLabel;
	
	private JPanel loginCard;
	
	private Controller controller;
	private Model model;
	private PropSaver props;
	private LoadingDialog loadingDialog;
	
	private boolean loggingIn;
	
	public LoginDialog(Controller controller) {
		super();
		
		this.controller = controller;
		this.model = controller.getModel();
		props = model.getProps();
		
		loggingIn = false;
	}
	
	/**
	 * The method to<br />
	 * show the dialog.
	 */
	@Override
	public void showDialog() {
		if(frame == null) {
			frame = new JFrame("WaffleCode Login");
			initializeLoginCard();
			frame.getContentPane().add(BorderLayout.CENTER, loginCard);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setBounds(View.getCenteredBounds(500, 300));
		}
		frame.setVisible(true);
	}
	
	private void initializeLoginCard() {
		loginCard = new JPanel(new BorderLayout());
		JPanel top = new JPanel();
			JLabel title = new JLabel("LOGIN");
				title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			top.add(title);
		loginCard.add(BorderLayout.NORTH, top);
		JPanel center = new JPanel(new GridLayout(3, 2));
			((GridLayout)center.getLayout()).setHgap(10);
			((GridLayout)center.getLayout()).setVgap(10);
			center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			center.add(new JLabel("Username:"));
			usernameField = new JTextField(20);
				usernameField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
				//usernameField.setText(props.get("username"));
				usernameField.addKeyListener(new LoginListener());
			center.add(usernameField);
			center.add(new JLabel("Password:"));
			passwordField = new JPasswordField();
				passwordField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
				usernameField.addKeyListener(new LoginListener());
			center.add(passwordField);
			center.add(new JLabel("Server IP:"));
			serverIPField = new JTextField(20);
				serverIPField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
				serverIPField.setText(Globals.LOCALHOST_IP);
			center.add(serverIPField);
		loginCard.add(BorderLayout.CENTER, center);
		JPanel bottom = new JPanel(new GridLayout(3, 1));
			bottom.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			loginButton = new JButton("Login");
				loginButton.addActionListener(new LoginListener());
			bottom.add(loginButton);
			feedbackLabel = new JLabel("You only have to enter a username.");
			bottom.add(feedbackLabel);
			JPanel IPs = new JPanel();
			IPs.add(new JLabel("IPs: Daniel's Hamachi IP is "));
			JTextField field = new JTextField(Globals.DANIELS_HAMACHI_IP);
				field.setBackground(IPs.getBackground());
			IPs.add(field);
			IPs.add(new JLabel(", John's IP is "));
			field = new JTextField(Globals.JOHNS_IP);
				field.setBackground(IPs.getBackground());
			IPs.add(field);
			bottom.add(IPs);
		loginCard.add(BorderLayout.SOUTH, bottom);
	}
	
	private class LoginListener implements ActionListener, KeyListener {
		private void submitInfo() {
			if(!loggingIn) { //Check to make sure that the LoginListener does not fire twice.
				String username = usernameField.getText().trim();
				if(username.equals("") ) {
					feedbackLabel.setText("Please enter a username.");
				}
				else {
					usernameField.setEditable(false);
					passwordField.setEditable(false);
					frame.setVisible(false);
					controller.usernameCallback(usernameField.getText(),
							(serverIPField.getText().isEmpty())
							?Globals.LOCALHOST_IP:serverIPField.getText());
				}
				
				loggingIn = true;
			}
		}
		
		public void actionPerformed(ActionEvent a) {
			submitInfo();
		}
		public void keyReleased(KeyEvent e) { 
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				submitInfo();
			}
		}
		public void keyPressed(KeyEvent e) { }
		public void keyTyped(KeyEvent e) { }
	}
}