package info.angrynerds.wafflecode.code;

import info.angrynerds.wafflecode.mvc.Controller;
import info.angrynerds.wafflecode.utils.Project;

import java.io.*;

/**
 * 
 * @author John Lhota
 * This class compiles (if applicable) and runs code, based on whatever language the project is written in.
 */
public class Runner {
	
	Controller controller;
	
	
	public Runner(Controller controller) {
		this.controller = controller;
	}
	
	public void runJava(Project proj) {
		Process program = null;     
        @SuppressWarnings("unused")
		PrintWriter writer;
		BufferedReader reader;

		System.out.println("CallHelloPgm.main() invoked");

		// call the Hello class
		try
		{
                        //TODO change to an array FIXME
			program = Runtime.getRuntime().exec("java com.ibm.as400.system.Hello");
		}
		catch(IOException e)
		{
			System.err.println("Error on exec() method");
			e.printStackTrace();  
		}

		// read from the called program's standard output stream
		try
		{//start a new thread?
			reader = new BufferedReader(
					new InputStreamReader( program.getInputStream() ));  
			System.out.println(reader.readLine());
		}
		catch(IOException e)
		{
			System.err.println("Error on inStream.readLine()");
			e.printStackTrace();  
		}
	}
	
}
