package info.angrynerds.wafflecode.utils;

public class Globals {
	// Debug
	public static final boolean VERBOSE = true;
	
	// Versions
	public static final String SERVER_VERSION = "\"Toaster\" 1.0";
	public static final String CLIENT_VERSION = "\"Toaster\" 1.0";
	
	// Networking flags
	public static final String NULL_USERNAME = "asdf!@#$jkl;";
	
	public static final String INSERT_START_FLAG = "INSERT_The_Text(*)";
	public static final String INSERT_SEPARATOR = "__Insert-Separator__";
	
	public static final String DELETE_START_FLAG = "DELETE_The_Text(!)";
	public static final String DELETE_SEPARATOR = "__Delete-Separator__";
	
	public static final String RENAME_FILE_FLAG = "We am RENAMing da file from:";
	public static final String RENAME_FILE_SEPARATOR = "--toooo--";
	
	// Networking IPs and ports
	public static final String LOCALHOST_IP = "127.0.0.1";
	public static final String DANIELS_HAMACHI_IP = "5.77.142.133";
	public static final String JOHNS_IP = "67.250.56.239";
	public static int SERVER_PORT = 4444;
	public static String SERVER_IP = JOHNS_IP;
        
        //Integration
        public static final String OS = System.getProperty("os.name");
	
	public static void setServerIP(String ip) {
		SERVER_IP = ip;
	}
}