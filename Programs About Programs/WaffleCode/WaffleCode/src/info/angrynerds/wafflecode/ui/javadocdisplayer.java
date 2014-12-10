package info.angrynerds.wafflecode.ui;

import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JTextPane;

public class JavaDocDisplayer {
	
	JTextPane codeEditor;
	JEditorPane displayPane;
	ArrayList<String> codePieces;
	
	//JavaDocFinder finder;
	
	public JavaDocDisplayer(CodeEditor code) {
		
		codeEditor = code;
		
		codePieces = new ArrayList<String>();
		
		codePieces.add("John");
		codePieces.add("Daniel");
		codePieces.add("Miles");
	}
	
	public String getText( int offs ) {
		
		// In the future, we should probably cycle through a list of classes and functions or something
		String allCode = codeEditor.getText(), hoveredCode;
		
		for( String possibleMatch : codePieces ) {
			
			for( int i = 0; i < possibleMatch.length(); i++) {
				try {
					hoveredCode = allCode.substring( offs - i, possibleMatch.length() );
					
					if(hoveredCode.equals( possibleMatch )) {
						//Display the JavaDoc.
						//This only occurs when the first possibleMatch is found.. after that, nuthin. FIXME!
						
						System.out.println("display javadoc for entry " + possibleMatch);
						return possibleMatch;
					}
				}
				catch(IndexOutOfBoundsException thatDarnException) {
					//Aw, forget it.
				}
			}
		}
		
		return null;
	
	}
	
}
