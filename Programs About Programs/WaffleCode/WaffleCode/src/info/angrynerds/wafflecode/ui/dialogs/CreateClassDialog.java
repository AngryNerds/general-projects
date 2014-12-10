package info.angrynerds.wafflecode.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JTextField;

import info.angrynerds.wafflecode.mvc.Controller;

public class CreateClassDialog extends CreateDialog {
	
	JTextField className;
	JButton doneButton;
	
	public CreateClassDialog(Controller controller) {
		super(controller);
	}

	@Override
	public void showDialog() {
		super.showDialog();
		
		frame.setTitle("Create Class");
		
		className = new JTextField("Foo");
		frame.add( BorderLayout.CENTER, className );
		
		doneButton = new JButton("Done");
		doneButton.addActionListener( new DoneListener() );
		frame.add( BorderLayout.SOUTH, doneButton );
	}
	
	private class DoneListener implements ActionListener  {

		@Override
		public void actionPerformed(ActionEvent a) {
			String n = className.getText();
			notifyDoneListeners( new File( controller.getModel().getProject().getDir(),
					n + (n.endsWith(".java")? "":".java") ) );
		}
		
	}
	
}
