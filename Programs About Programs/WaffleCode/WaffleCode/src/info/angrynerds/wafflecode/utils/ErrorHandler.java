package info.angrynerds.wafflecode.utils;

import info.angrynerds.wafflecode.ui.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class ErrorHandler {
	private Console console;
	
	public ErrorHandler(Console console) {
		this.console = console;
	}
	
	public void handleException(Exception ex, String location) {
		console.showWindow();
		String exceptionName = ex.getClass().getSimpleName();
		console.println("\n===DARN THERE WAS AN ERROR===");
		console.println("There was a " + exceptionName + " at " + location + ".");
		console.println("Exception message: " + ex.getLocalizedMessage() + ".");
		console.println(exceptionName + " stack trace:");
		ex.printStackTrace(new PrintWriter(new AreaWriter()));
		ex.printStackTrace();
	}
	
	private class AreaWriter extends Writer {
		public void close() throws IOException {
			console.println("AreaWriter closed.");
		}

		public void flush() throws IOException {
			console.println("AreaWriter flushed.");
		}

		public void write(char[] arg0, int arg1, int arg2) throws IOException {
			StringBuilder string = new StringBuilder();
			for(int i = arg1; i < arg2; i++) {
				boolean INDIVIDUAL_CHARACTERS = false;
				if(INDIVIDUAL_CHARACTERS) {
					if((arg0[i] != '\n') && (arg0[i] != '\r')) {
						System.out.println(arg0[i] + " ... appended");
						string.append(arg0[i]);
					} else {
						System.out.println("<newline> appended");
					}
				} else {
					string.append(arg0[i]);
				}
			}
			console.println(string.toString());
		}
	}
}