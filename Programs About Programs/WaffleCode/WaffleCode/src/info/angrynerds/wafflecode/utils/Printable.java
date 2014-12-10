package info.angrynerds.wafflecode.utils;

/**
 * Any class that implements this should be able to take messages given to it by the methods
 * in this class and display them to the user somehow; preferably through a GUI.  The only
 * difference between the methods in this class is that <code>println(Object)</code> adds
 *  a newline to the end of the string.
 * @author Daniel Glus
 */
public interface Printable {
	
	public void println(Object o);
	public void print(Object o);
	
}
