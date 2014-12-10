package info.angrynerds.quack;

import java.util.HashMap;
import java.util.Vector;

public class Parser {
	
	private CodeBlock parsedCode;
	
	public Parser() {
		parsedCode = new CodeBlock("");
	}
	
	public CodeBlock parse(String code) {
		separateLines(code);
		//declareFunctions(code);
		return parsedCode;
	}
	
	private void separateLines(String code) {		
		
		boolean inString = false;
		
		int start = 0, depth = 0;
		
		for(int i=0; i<code.length(); i++)//Count the number of semicolons (and therefore the amount of lines of code).
		{
			char character = code.charAt(i);
			
			if(character == '\"')//If the semicolon is within double quotes, do not count it.
			{
				inString = !inString; //We have now entered, or exited, a string.
			}
			
			if(!inString) {
				if(character == ';')//Count the semicolon if we are not in a string.
				{
					parsedCode.add(code.substring(start, i-1));
					start = i + 1;
				}
				else if(character == '{') {
					String prefix = code.substring(start, i-1);//The pre-block st. (such as if(blah))
					
					parsedCode.startBlock(prefix);
				}
				else if(character == '}') {
					parsedCode.endBlock();
				}
			}
		}
	}
	
}
