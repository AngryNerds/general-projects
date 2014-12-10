package info.angrynerds.wafflecode.utils;

import info.angrynerds.wafflecode.mvc.WaffleView;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class AutoCompleter implements KeyListener, CaretListener {

	// FIXME yarr... why can't we use these?
	private int charLoc, prevCharLoc;
	private WaffleView view;
	private JTextPane codeEditor;
	private Document doc;

	public AutoCompleter(WaffleView wv) {
		charLoc = 0;
		view = wv;
		codeEditor = view.getCodeEditor();
		doc = codeEditor.getDocument();
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		prevCharLoc = charLoc;
		// TODO Auto-generated method stub
		int charLoc = codeEditor.getCaretPosition() - 1;
		
		try {
			System.out.println( codeEditor.getText().charAt(charLoc) );
		}
		catch(IndexOutOfBoundsException oops) {
			System.out.println(charLoc);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
//		int key = e.getKeyCode();FIXME
//		if(key == KeyEvent.VK_ENTER) {
//			try {
//				if( codeEditor.getText().charAt(charLoc-1) == '{' ) {//Line-breaks count as a char, we must not.
//					System.out.println("Insert a Line Break, an Indent, another line break, and a close curly bracket.");
//					if(doc != null) {
//						try {
//							doc.insertString(charLoc, "}", null);
//						}
//						catch (BadLocationException ex) {
//							ex.printStackTrace();
//						}
//					}
//					else System.out.println("[ac] Oh no null doc");
//				}
//			}
//			catch(IndexOutOfBoundsException i) {
//				System.out.println(charLoc + " does not exist");
//			}
//		}
	}

	@Override
	public void keyTyped(KeyEvent e) { }

}
