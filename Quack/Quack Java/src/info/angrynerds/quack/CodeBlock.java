package info.angrynerds.quack;

import java.util.Vector;

public class CodeBlock extends Code {
	
	private Vector<Code> lines;
	private boolean open;
	private String prefix;
	private int runningIndex;
	
	public CodeBlock(String pre) {
		lines = new Vector<Code>();
		open = true;
		runningIndex = 0;
		prefix = pre;
	}

	public void add(String raw) {
		if( lines.lastElement() instanceof CodeBlock && ((CodeBlock)lines.lastElement()).isOpen() ) {
			((CodeBlock)lines.lastElement()).add(raw);
		}
		else {
			lines.addElement(new CodeLine(raw));
		}
	}
	
	public void end() {
		open = false;
	}
	
	public void endBlock() {
		if(lines.lastElement() instanceof CodeBlock) {
			((CodeBlock)lines.lastElement()).end();
		}
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void startBlock(String prefix) {
		if( lines.lastElement() instanceof CodeBlock && ((CodeBlock)lines.lastElement()).isOpen() ) {
			((CodeBlock)lines.lastElement()).startBlock(prefix); //If we're in a block, make a sub-block!
		}
		else {
			lines.addElement(new CodeBlock(prefix)); //Else, start a new one
		}
	}
}
