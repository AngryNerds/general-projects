package info.angryNerds.waffleCode;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UI {
	
	Controller controller;
	
	JFrame frame;
	JTextPane codePane;
	
	public UI(Controller controller) {
		this.controller = controller;
		
		frame = new JFrame("WaffleCode");
		codePane = new JTextPane();
		frame.add(codePane, BorderLayout.CENTER);
		frame.setSize(new Dimension(640, 480));
		frame.setVisible(true);
		
		initMenu();
	}
	
	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
			JMenuItem createProject = new JMenuItem("Create Project...");
			createProject.addActionListener(new MenuBarListener());
			file.add(createProject);
			JMenuItem createFile = new JMenuItem("Create File...");
			createFile.addActionListener(new MenuBarListener());
			file.add(createFile);
		menuBar.add(file);
		
		frame.setJMenuBar(menuBar);
	}
	
	private class MenuBarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String comm = e.getActionCommand();
			if(comm.equals("Create Project...")) {
				
			}
			else if(comm.equals("Create File...")) {
				
			}
		}
		
	}
}
