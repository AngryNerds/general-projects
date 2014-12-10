package info.angrynerds.wafflecode.mvc;

import info.angrynerds.wafflecode.code.*;
import info.angrynerds.wafflecode.network.*;
import info.angrynerds.wafflecode.utils.*;

public interface Controller {
	public void runApplication();
	
	public void usernameCallback(
			String username, String IP);
	
	public String getUsername();
	public NetworkHelper getNetworkHelper();
	public void setIP(String ip);
	
	public View getView();
	public Model getModel();
	
	public void addMVCListener(MVCListener listener);
	public void notifyMVCListeners(MVCEvent event);

	
	public String justInserted();
	public String justDeleted();
	public void acknowledgeInsert();
	public void acknowledgeDelete();

	public Runner getRunner();

	public void runCode();

	public boolean justOpened();
	public void setJustOpened(boolean b);
        
        public MacHelper getMacHelper();
}