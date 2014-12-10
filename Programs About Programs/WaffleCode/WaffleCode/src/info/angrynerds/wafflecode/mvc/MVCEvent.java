package info.angrynerds.wafflecode.mvc;

import info.angrynerds.wafflecode.utils.Globals;
import info.angrynerds.wafflecode.utils.Project;

import java.io.File;

public class MVCEvent {
	private MVCEventType type;	// Used in all messages
	private String text;		// Only used in chat, insert, or delete messages
	private int offset;			// Only used in insert or delete messages
	private int length;			// Only used in delete messages
	private Project proj;		// Replace with some kind of proj-id?
	private File doc;			// Replace with just the file name?
	
	/**
	 * Mainly for chat messages
	 */
	public MVCEvent(MVCEventType type, String text) {
		this(type, text, -1, -1, null, null);
	}
	
	/**
	 * For insert messages
	 */
	public MVCEvent(MVCEventType type, String text, int offset, File doc, Project proj) {
		this(type, text, offset, -1, doc, proj);
	}
	
	/**
	 * For delete messages
	 */
	public MVCEvent(MVCEventType type, String text, int offset, int length, File doc, Project proj) {
		this.type = type;
		this.text = text; 			// We don't really need to know this... remove it?
		this.offset = offset;		// YES WE DO!
		this.length = length;		//Okay, geez.
		this.doc = doc;
		this.proj = proj;
	}
	
	public MVCEventType getType() {
		return type;
	}
	
	public String getText() {
		return text;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public int getLength() {
		return length;
	}
	
	public File getDoc() {
		return doc;
	}
	
	public Project getProject() {
		return proj;
	}
	
	public String toString() {
		@SuppressWarnings("unused")
		String relPath = "";//This stores the path from the proj's dir to the file.
							//so, even if the proj's are in diff places for diff users, one
							//program can find the file the other is referencing
		if(doc != null && proj != null) {
			relPath = doc.getAbsolutePath().substring(proj.getDir().getAbsolutePath().length());
		}
		
		switch(type) {
		case VIEW_CODE_INSERTED:
			return Globals.INSERT_START_FLAG + text + Globals.INSERT_SEPARATOR + offset;/* +
					Globals.INSERT_SEPARATOR + relPath +  Globals.INSERT_SEPARATOR + proj.ID;*/
		case VIEW_CODE_DELETED:
			return Globals.DELETE_START_FLAG + offset + Globals.DELETE_SEPARATOR +
					length;// + Globals.DELETE_SEPARATOR + relPath + Globals.DELETE_SEPARATOR + proj.ID;
		default:
			final int MAX_TEXT_LENGTH = 100;	// Number of characters
			String textStr = (text.length() <= MAX_TEXT_LENGTH)
			?text:text.substring(0, MAX_TEXT_LENGTH);
			return "MVCEvent [type = " + type + ", text = \"" + textStr + "\"]";
		}//TODO fix this code and the thing that sends & receives events in controller!!
	}
	
	//public static MVCEvent parse( String text ) {
	//	return null;
	//}
}