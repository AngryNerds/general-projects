package info.angrynerds.wafflecode.ui.dialogs;

import info.angrynerds.wafflecode.mvc.Controller;

public class CreateFileDialog extends CreateDialog {

	public CreateFileDialog(Controller controller) {
		super(controller);
	}
	
	@Override
	public void showDialog() {
		super.showDialog();
		
		frame.setTitle("Create File");
	}

}
