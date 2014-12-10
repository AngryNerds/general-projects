package info.angrynerds.wafflecode.utils;

import info.angrynerds.wafflecode.mvc.Controller;
import info.angrynerds.wafflecode.mvc.MVCEvent;
import info.angrynerds.wafflecode.mvc.MVCEventType;
import info.angrynerds.wafflecode.mvc.MVCListener;

import java.util.*;

import javax.swing.event.*;
import javax.swing.text.*;

public class EditListener implements DocumentListener {
	
	private List<MVCListener> listeners;
	
	private Controller controller;
	
	public EditListener(Controller c) {
		listeners = new ArrayList<MVCListener>();
		
		controller = c;
		addMVCListener( (MVCListener) controller );
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
	public void insertUpdate(DocumentEvent e) {
		int offset = e.getOffset();
		int length = e.getLength();
		Document doc = e.getDocument();
		
		String text;
		try {
			text = doc.getText(offset, length);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
			System.out.println("[EditListener / insertUpdate()] had to quit bc of BadLocEx");
			return;
		}
		
		if(controller.justInserted() != null) {
			if( controller.justInserted().equals( text ) ) {//Check to make sure we aren't re-sending the edit we just got.
				controller.acknowledgeInsert();
				return;
			}
		}
		
		if( controller.justOpened() ) {
			controller.setJustOpened( false );
			return;
		}
		
		notifyMVCListeners(new MVCEvent(MVCEventType.VIEW_CODE_INSERTED, text, offset, length,
				controller.getModel().getDoc(), controller.getModel().getProject()) );
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		int offset = e.getOffset();
		int length = e.getLength();
		
		String text;
		
		try {
			text = e.getDocument().getText( offset, length );
		}
		catch(BadLocationException ex) {
			ex.printStackTrace();
			System.out.println("[EditListener / removeUpdate()] had to quit bc of BadLocEx");
			return;
		}
		
		if(controller.justDeleted() != null) {
			if( controller.justDeleted().equals( text ) ) {//Check to make sure we aren't re-sending the edit we just got.
				controller.acknowledgeDelete();
				return;
			}
		}
		notifyMVCListeners(new MVCEvent(MVCEventType.VIEW_CODE_DELETED, text, offset, length, controller.getModel().getDoc(), controller.getModel().getProject()) );
		
		controller.getView().getFrame().getRootPane().putClientProperty(
				"Window.documentModified", Boolean.TRUE); //This will change the appearance of the red "x"
														  //to tell the user their changes are unsaved.
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) { }
}
