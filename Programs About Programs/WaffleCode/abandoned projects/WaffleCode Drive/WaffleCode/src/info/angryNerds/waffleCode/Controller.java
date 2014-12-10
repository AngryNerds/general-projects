package info.angryNerds.waffleCode;

import java.io.IOException;

public class Controller {
	
	DriveConnection conn;
	UI ui;
	
	public void go() {
		try {
			conn = new DriveConnection();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		ui = new UI(this);
	}
	
	public static void main(String[] args) {
		new Controller().go();
	}
	
}
