package info.angrynerds.wafflecode.ui;


import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.text.*;

public class SyntaxHighlighter implements ActionListener {
	
	private JTextPane codeEditor;
	private String[] keywords = {"boolean", "byte", "char", "double", "float", "int", "long", "short", "public",
			"private", "protected", "abstract", "final", "native", "static", "strictfp", "synchronized",
			"transient", "volatile", "if", "else", "do", "while", "switch", "case", "default", "for",
			"break", "continue", "assert", "class", "extends", "implements", "import", "instanceof", "interface",
			"new", "package", "super", "this", "catch", "finally", "try", "throw", "throws", "return",
			"void", "const", "goto", "enum"};
	
	//Styles for various code-stuffs.
	private SimpleAttributeSet plainStyle, commentStyle, javadocStyle, keywordStyle, stringStyle, instanceStyle, globalStyle;
	
	//Comment types
	public static final int SINGLE_LINE = 0, MULTI_LINE = 1, JAVADOC = 2;
	
	public SyntaxHighlighter(JTextPane code) {
		codeEditor = code;
		
		//Style for plain old boring code
		plainStyle = new SimpleAttributeSet();
		StyleConstants.setFontFamily(plainStyle, "monospace");
		StyleConstants.setItalic(plainStyle, false);
		StyleConstants.setForeground(plainStyle, Color.BLACK);
		
		//Style for comments
		commentStyle = new SimpleAttributeSet();
		StyleConstants.setForeground( commentStyle, Color.GREEN.darker() );
		
		//javadocs
		javadocStyle = new SimpleAttributeSet();
		StyleConstants.setForeground( javadocStyle, Color.CYAN.darker() );
		
		//Style for keywords
		keywordStyle = new SimpleAttributeSet();
		StyleConstants.setForeground( keywordStyle, Theme.LIGHTER );
		
		//Style for strings
		stringStyle = new SimpleAttributeSet();
		StyleConstants.setItalic(stringStyle, true);
		StyleConstants.setForeground( stringStyle, Color.GRAY );
		
		//Style for instance variables
		instanceStyle = new SimpleAttributeSet();
		StyleConstants.setForeground( instanceStyle, Theme.LIGHTER );
		
		globalStyle = new SimpleAttributeSet();
		StyleConstants.setItalic( globalStyle, true );
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int selectionStart = codeEditor.getSelectionStart(),
				selectionEnd = codeEditor.getSelectionEnd();
		
		StyledDocument doc = codeEditor.getStyledDocument();
		String docText = "";
		
		try {
			docText = doc.getText(0, doc.getLength());
		}
		catch (BadLocationException ex) {
			ex.printStackTrace();
		}
		
		//clear the current styles
		doc.setCharacterAttributes(0, docText.length(), plainStyle, true);
		
		highlight(doc, docText);
		
		//reposition caret and/or selection
		fixSelection(selectionStart, selectionEnd);
	}

	private void highlight(StyledDocument doc, String text) {
		int commentStart = -1;
		int quoteStart = -1;
		
		int commentType = -1;
		
		for(int i = 0; i < text.length(); i++) {
			
			boolean inComment = commentStart != -1;
			boolean inQuote = quoteStart != -1;
			
			if( !inComment ) {
				if( text.charAt(i) == '\"' ) {
					if( quoteStart == -1 ) {
						quoteStart = i;
					}
					else {
						doc.setCharacterAttributes(quoteStart, i - quoteStart + 1, stringStyle, true);
						// the + 1 is because the length of '"' is 1.
						quoteStart = -1;
					}
				}
			}
			if( !inQuote ) {
				if( text.substring(i).startsWith("//") && commentStart == -1 ) {
					commentStart = i;
					commentType = SINGLE_LINE;
				}
				else if( commentType == SINGLE_LINE && text.charAt(i) == '\n' && commentStart != -1 ) {
					doc.setCharacterAttributes(commentStart, i - commentStart, commentStyle, true);
					commentStart = commentType = -1;
				}
				else if( text.substring(i).startsWith("/*") && commentStart == -1 ) {
					
					commentStart = i;
					
					if( text.substring(i).startsWith("/**") ) {
						commentType = JAVADOC;
					}
					else {
						commentType = MULTI_LINE;
					}
				}
				else if( text.substring(i).startsWith("*/") && commentStart != -1 ) {
					if( commentType == MULTI_LINE ) {
						doc.setCharacterAttributes(commentStart, i - commentStart + "*/".length(), commentStyle, true);
					}
					else if( commentType == JAVADOC ) {
						doc.setCharacterAttributes(commentStart, i - commentStart + "*/".length(), javadocStyle, true);
					}
					commentStart = commentType = -1;
				}
			}
			
			if( !( inComment || inQuote ) ) {
				for(String keyword : keywords) {
					if(text.length() - i - keyword.length() >= 0) {
						
						if( text.substring(i, i + keyword.length()).equals(keyword)) {
							
							//Pattern p = Pattern.compile("\W" + keyword + "\W");
							//Matcher m = p.matcher( text.substring( i - 1, i + keyword.length() + 1 ) );
							//boolean matches = m.matches();
							doc.setCharacterAttributes(i, keyword.length(), keywordStyle, false);
						}
						
					}
					else break;//FIXME? could this break cause an error?
				}
			}
		}
	}
	
	/**
	 * Since editing the TextPane de-selects text, this reselects it.
	 * @param start The former selection's start.
	 * @param end The former selection's end.
	 */
	private void fixSelection(int start, int end) {
		if(start == end) {
			codeEditor.setCaretPosition(start);
		}
		else {
			codeEditor.setSelectionStart( Math.min(start, end) );
			codeEditor.setSelectionEnd( Math.max(start, end) );
		}
	}
}