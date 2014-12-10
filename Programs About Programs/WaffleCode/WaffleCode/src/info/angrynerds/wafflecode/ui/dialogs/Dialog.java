package info.angrynerds.wafflecode.ui.dialogs;

import java.util.ArrayList;


public abstract class Dialog {
	
	public ArrayList<DialogDoneListener> listeners;
	
	public Dialog() {
		listeners = new ArrayList<DialogDoneListener>();
	}
	
	public abstract void showDialog();
	
	public void addDoneListener(DialogDoneListener d) {
		listeners.add( d );
	}
	
	public void notifyDoneListeners( Object data ) {
		for(DialogDoneListener d : listeners) {
			d.dialogDone( data );
		}
	}
	
}
