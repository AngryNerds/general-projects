package info.angrynerds.wafflecode;

import info.angrynerds.wafflecode.mvc.WaffleController;

/**
 * This is the class that starts it all.  Why is it in a class of its own?  I have no idea.
 * @version See Globals.
 * @author Daniel Glus
 */
public class AppLauncher {
	public static void main(String[] args) {
		new WaffleController().runApplication();
	}
}