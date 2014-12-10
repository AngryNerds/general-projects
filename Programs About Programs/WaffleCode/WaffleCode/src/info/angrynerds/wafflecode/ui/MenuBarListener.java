package info.angrynerds.wafflecode.ui;

import info.angrynerds.wafflecode.mvc.Controller;
import info.angrynerds.wafflecode.mvc.Model;
import info.angrynerds.wafflecode.mvc.View;
import info.angrynerds.wafflecode.utils.*;

import java.awt.event.*;

import javax.swing.*;

public class MenuBarListener implements ActionListener {
	private Controller controller;
	private View view;
	private Model model;
	private JEditorPane codeArea;
	
	public MenuBarListener(Controller controller, JEditorPane code) {
		this.controller = controller;
		this.view = controller.getView();
		this.model = controller.getModel();
		this.codeArea = code;
	}
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		boolean allowedToPrint = true;
		
		// FILE
		if(command.equals("New Project...")) {
			view.createProject();
		}
		else if(command.equals("New Class...")) {
			view.createClass();
		}
		else if(command.equals("New File...")) {
			view.createFile();
		}
		else if(command.equals("Open Project...")) {
			view.openProject();
		}
		// EDIT
		else if(command.equals("Copy")) {
			codeArea.copy();
		}
		else if(command.equals("Cut")) {
			codeArea.cut();
		}
		else if(command.equals("Paste")) {
			codeArea.paste();
		}
		// VIEW
		else if(command.equals("Hide Console")) {
			allowedToPrint = false;
			controller.getView().getConsole().hideWindow();
			controller.getView().getConsole().setAllowedToShowWindow(false);
			controller.getView().setConsoleVisible(false);
		}
		else if(command.equals("In a separate window")) {		// Show the console in a separate window
			allowedToPrint = false;
			controller.getView().setConsoleVisible(false);
			controller.getView().getConsole().setAllowedToShowWindow(true);
			controller.getView().getConsole().showWindow();
		}
		else if(command.equals("Docked to the bottom of this window")) {
			allowedToPrint = false;
			controller.getView().getConsole().setAllowedToShowWindow(false);
			controller.getView().getConsole().hideWindow();
			controller.getView().setConsoleVisible(true);
		}
		// RUN
		else if(command.equals("Run")) {
			controller.runCode();
		}
		// NETWORK
		else if(command.equals("Change Server IP...")) {
			Globals.SERVER_IP = JOptionPane.showInputDialog(
					"Type in the New IP Address of the Server.", Globals.SERVER_IP);
		}
		else if(command.equals("Change Server Port...")) {
			Globals.SERVER_PORT = Integer.parseInt(JOptionPane.showInputDialog(
					"Type in the New Port Address of the Server.", Globals.SERVER_PORT));
		}
		
		if(allowedToPrint) {
			controller.getView().getConsole().println( "Menu: " + command );
		}
	}
}