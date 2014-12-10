package info.angrynerds.wafflecode.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import info.angrynerds.wafflecode.mvc.Controller;
import info.angrynerds.wafflecode.utils.Project;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * Dialog to create a new project.
 * @author John Lhota
 *
 */
public class CreateProjectDialog extends CreateDialog {
	
	private JFrame frame;
	private JTextField name, fileLocation;
	
	private Project project;
	
	public CreateProjectDialog(Controller controller) {
		super(controller);
	}
	
	/**
	 * Shows a frame with project-creation options.
	 * @return An instance of project with a non-existent file path.
	 */
	@Override
	public void showDialog() {
		project = new Project( controller );
		
		if(frame == null) {
			buildUI();
		}
	}
	
	private void buildUI() {
		frame = new JFrame();
		
		JPanel main = new JPanel();
		
		main.setLayout( new BoxLayout( main, BoxLayout.Y_AXIS ) );
		
		JPanel namePanel = new JPanel();
		namePanel.add( new JLabel("Name: ") );
		name = new JTextField("", 10);
		name.requestFocus();
		namePanel.add( name );
		main.add(namePanel);
		
		JPanel filePanel = new JPanel();
			fileLocation = new JTextField("", 20);
			filePanel.add( fileLocation );
			JButton setLocation = new JButton("Set Path");
			setLocation.addActionListener( new SetLocationListener() );
			filePanel.add(setLocation);
		main.add(filePanel);
		
		frame.getContentPane().add( BorderLayout.CENTER, main );
		
		JPanel bottomPanel = new JPanel();
		JButton done = new JButton("Done");
		done.addActionListener( new DoneListener() );
		bottomPanel.add(done);
		frame.getContentPane().add( BorderLayout.SOUTH, bottomPanel );
		
		frame.setSize( 550, 400 );
		frame.setLocationRelativeTo( controller.getView().getFrame() );
		frame.setVisible(true);
	}
	
	private class SetLocationListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent a) {
			FileDialog dialog = new FileDialog( frame );
			dialog.setMode( dialog.SAVE ); //OMG I referenced a static from an instance... so hipster.
			dialog.setVisible( true );
			String projLoc = dialog.getFile();
			
			fileLocation.setText( projLoc );
		}
		
	}
	
	private class DoneListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent potatoe) {
			frame.setVisible(false);
			
			project.setTitle( name.getText() );
			project.setDir( new File(fileLocation.getText()) );
			
			notifyDoneListeners( project );
		}
		
	}
	
}
