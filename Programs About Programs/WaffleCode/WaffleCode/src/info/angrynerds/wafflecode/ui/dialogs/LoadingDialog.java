package info.angrynerds.wafflecode.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;

import info.angrynerds.wafflecode.mvc.View;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JButton;

public class LoadingDialog extends Dialog {
	private JFrame frame;
	private JLabel label;
	private JProgressBar bar;
	
	private float increment, progress;
	
	//Easter egg stuff
	private static final boolean SLOW_LOAD = false;
	private boolean backwards;
	
	public LoadingDialog(int numberOfTimesMethodWillBeCalled) {
		super();
		
		increment = 100 / numberOfTimesMethodWillBeCalled;
		progress = 0;
		
		backwards = false;
	}
	
	@Override
	public void showDialog() {
		if(frame == null) {
			buildGUI();
		}
		frame.setVisible(true);
	}
	
	public void hideDialog() {
		if(frame == null) {
			buildGUI();
		}
		//if(progress >= 100) {
			frame.setVisible(false);
		/*}
		else {
			progress();
			
			hideDialog();
		}*/
	}
	
	public void progress() {

		/*if(SLOW_LOAD) {
			try {
				Thread.sleep(100 + (int)(Math.random() * 100));
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}*/
		
		//if(!backwards) {
			progress += increment;
                        label.setText((int) Math.random()*45 +
                                " minutes " + (int) Math.random()*60 +  " seconds remaining.");
		/*}
		else {
			progress -= 2;
		}*/
		
		frame.setTitle("Loading... " + progress + "%");
		bar.setValue((int) progress);
	}
	
	private void buildGUI() {
		frame = new JFrame("Loading WaffleCode... 0%");
		//frame.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel(new BorderLayout());
			mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			//mainPanel.setLayout( new BorderLayout() );
			JPanel upperPanel = new JPanel();
				label = new JLabel("Loading... 0%");
				upperPanel.add(label);
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.exit(1);
				}
			});
			upperPanel.add(Box.createHorizontalStrut(300));
			upperPanel.add(cancelButton);
			mainPanel.add(upperPanel, BorderLayout.NORTH);
			bar = new JProgressBar();
			bar.setStringPainted(true);
			mainPanel.add(bar, BorderLayout.CENTER);
			//mainPanel.addKeyListener( (KeyListener) new UnloadListener() );
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setResizable(false);
		frame.setBounds(View.getCenteredBounds(500, 120));
	}
	
	/*private class UnloadListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			//if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				backwards = true;
			//}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//if( e.getKeyCode() == KeyEvent.VK_LEFT ) {
				backwards = false;		
			//}
		}
		
	}*/
}