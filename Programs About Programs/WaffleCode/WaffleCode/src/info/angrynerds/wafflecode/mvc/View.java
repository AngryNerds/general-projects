package info.angrynerds.wafflecode.mvc;

import java.awt.*;
import java.io.File;

import javax.swing.JFrame;

import info.angrynerds.wafflecode.ui.CodeEditor;
import info.angrynerds.wafflecode.ui.Console;
import info.angrynerds.wafflecode.utils.*;

public abstract class View {
	public abstract void setVisible(boolean visible);
	
	public abstract void setConsoleVisible(boolean visible);

	public abstract Printable getPrintable();

	public abstract void openProject();
	public abstract void selectProject(Project proj);

	public abstract void openDoc(File doc);

	public abstract File getDoc();
	
	public abstract Console getConsole();

	//public abstract void inform(String content);
	
	public abstract Chat getChat();
	//public abstract void tellChat(String message);
	
	/**
	 * Takes the specified dimension and returns bounds for that dimension that center
	 * the dimension on the screen.
	 * @param dimension The size of the component.
	 * @return The centered bounds for that component.
	 */
	public static Rectangle getCenteredBounds(Dimension dimension) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - dimension.width)/2;
		int y = (screen.height - dimension.height)/2;
		Rectangle result = new Rectangle(x, y, dimension.width, dimension.height);
		return result;
	}

	public static Rectangle getCenteredBounds(int width, int height) {
		return getCenteredBounds(new Dimension(width, height));
	}

	public abstract void handleException(Exception ex, String string);
	
	public abstract JFrame getFrame();

	public abstract CodeEditor getCodeEditor();

	public abstract void createProject();

	public abstract void createClass();

	public abstract void createFile();
}