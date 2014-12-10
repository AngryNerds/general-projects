package info.angrynerds.wafflecode.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JTextPane;
import javax.swing.UIManager;

public class CodeEditor extends JTextPane {
	
	JavaDocDisplayer jd;
	
	public CodeEditor() {
		super();
		
		setToolTipText("");//Just to let the JVM know that this thing does have a tooltip.
		jd = new JavaDocDisplayer( this );
		
		//Set up some tooltipping style
		UIManager.put( "ToolTip.background", new Color(0.0f, 0.0f, 0.0f, 0.8f) );
		UIManager.put( "ToolTip.foreground", Color.WHITE );
		UIManager.put( "ToolTip.font", new Font("Sans-Serif", Font.PLAIN, 15) );
	}
	
	/**
	 * dostuff
	 * hello
	 * blah
	 */
	@Override
	public String getToolTipText(MouseEvent e) {
		
//		try {
//			//Process p = Runtime.getRuntime().exec("javadoc -d /home/html -sourcepath /home/src -subpackages java -exclude java.net:java.lang");
//			//InputStream result = p.getInputStream();
//		} catch (IOException ex) {
//			// TODO Auto-generated catch block
//			ex.printStackTrace();
//		}
		
		Point p = e.getPoint();
		return jd.getText( viewToModel(p) );
	}
	
}
