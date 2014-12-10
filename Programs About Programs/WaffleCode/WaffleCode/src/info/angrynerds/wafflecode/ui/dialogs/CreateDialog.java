package info.angrynerds.wafflecode.ui.dialogs;

import javax.swing.JFrame;

import info.angrynerds.wafflecode.mvc.Controller;

public abstract class CreateDialog extends Dialog {
	
	protected JFrame frame;
	
	protected Controller controller;
	
	public CreateDialog(Controller controller) {
		super();
		this.controller = controller;
	}
	
	@Override
	public void showDialog() {
		frame = new JFrame();
		frame.setLocationRelativeTo( controller.getView().getFrame() );
		frame.setVisible( true );
	}
	
}
