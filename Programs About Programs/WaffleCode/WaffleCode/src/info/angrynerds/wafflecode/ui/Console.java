package info.angrynerds.wafflecode.ui;

import info.angrynerds.wafflecode.mvc.*;
import info.angrynerds.wafflecode.utils.*;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

/**
 * A nerdy little console that displays
 *  friendly messages about what exactly the program is doing.
 * Pops up when one of two things happen:
 * <ol>
 * <li>An error or exception occurs</li>
 * <li>The user wants it to</li>
 * </ol>
 * You should include a call to this class's <code>handleException()</code> in
 * EVERY error handler (in the client code).  In fact, that call should be
 * the only code in that error handler, since this code already prints out
 * the stack trace. This console also sends an MVCEvent to the View when it
 * has a message so that the View can update its own console.
 * <i>The View has an instance of this class as one of its instance variables.</i>
 * @author Daniel Glus
 */
public class Console implements Printable {
	private JFrame frame;
	private JTextArea area;
	private List<MVCListener> listeners;
	
	private boolean allowedToShowWindow = false;	// For user
	private boolean noPopUps = true;				// Internal
	
	public Console() {
		listeners = new ArrayList<MVCListener>();
	}
	
	public void addMVCListener(MVCListener listener) {
		listeners.add(listener);
	}
	
	public void notifyMVCListeners(MVCEvent event) {
		for(MVCListener listener:listeners) {
			listener.itemRead(event);
		}
	}
	
	/**
	 * The assistant method to <code>showWindow()</code> that does the dirty work
	 * of building the GUI.  However,
	 * this method does NOT display the window; <code>showWindow()</code> does
	 * that.  This method should only be
	 * called once.
	 */
	private void setUpGUI() {
		if(frame == null) {	// Check again
			frame = new JFrame("WaffleCode Console");
			JPanel mainPanel = new JPanel(new BorderLayout());
				mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				area = new JTextArea(10, 20);
					area.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					area.setEditable(false);
					area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
					area.setLineWrap(true);
				mainPanel.add(BorderLayout.CENTER, new JScrollPane(area));
			frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
			frame.setBounds(View.getCenteredBounds(775, 500));
		}
	}
	
	/**
	 * If the frame is null, then that means that we didn't make the GUI yet.
	 *   So make the GUI by calling the method
	 * and then, since the frame must not be null, make the frame visible.
	 */
	public void showWindow() {
		if(frame == null) {
			setUpGUI();
		}
		if(allowedToShowWindow) {
			frame.setVisible(true);
		}
	}

	public void println(Object o) {
		String str = o.toString();
		if(str.equals(Globals.NULL_USERNAME)) {
			noPopUps = false;
		} else {
			if(!str.endsWith("\n")) str += "\n";
			print(str);
		}
	}

	public void print(Object o) {
		if(frame == null) {
			setUpGUI();
		}
		if(!noPopUps) showWindow();
		area.append(o.toString());
		if(!o.toString().startsWith("[WaffleView/itemRead()]")) {
			/* If it doesn't come from inside the event handler for this event,
			* tell WaffleView. */
			notifyMVCListeners(new MVCEvent(MVCEventType.CONSOLE_MESSAGE,
					o.toString()));
		}
	}

	public void hideWindow() {
		if(frame == null) {
			setUpGUI();
		}
		frame.setVisible(false);
	}
	
	/**
	 * If allowedToShowWindow is false, we don't want anybody to be able to set it
	 * @param bool
	 */
	public void setAllowedToShowWindow(boolean bool) {
		allowedToShowWindow = bool;
	}
}