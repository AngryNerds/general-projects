package info.angrynerds.wafflecode.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Properties;

public class PropSaver {
	
	Properties props;
	private boolean loaded;
	
	public static final boolean ENABLED = false;
	
	public PropSaver() {
		
	}
	
	public void load() throws IOException {
		if(ENABLED) {

			props = new Properties();

                        //BufferedReader reader = new BufferedReader(new FileInputStream("props.properties"));
			//props.load(reader);     

			//reader.close(); // better in finally block

			loaded = true;
		} else {
			loaded = true;
		}
	}
	
	public String get(String name) {
		if(ENABLED) {
			if (props.getProperty(name) != null) {
				return props.getProperty(name);
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
	
	public void set(String name, String value) throws IOException {
		if(ENABLED) {
			props.setProperty(name, value);        
			FileOutputStream outputStream = new FileOutputStream("props.properties");        
			props.store(outputStream, "---No Comment---");

			outputStream.close();
		}
	}
	
	public boolean loaded() {
		return loaded;
	}
}