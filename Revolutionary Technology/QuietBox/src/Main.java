import java.io.ByteArrayOutputStream;

import javax.sound.sampled.*;

public class Main {
	
	AudioFormat format;
	GUI gui;
	
	public void go() {
		gui = new GUI();
		
		TargetDataLine line;
		format = new AudioFormat(20, (int) Math.pow(2, 6), 2, false, false);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class,
		                format); // format is an AudioFormat object
		if (!AudioSystem.isLineSupported(info)) {
		    System.out.println("audio system: line unsupported");
		}
		// Obtain and open the line.
		try {
		    line = (TargetDataLine) AudioSystem.getLine(info);
		    line.open(format);
		} catch (LineUnavailableException ex) {
		    ex.printStackTrace();
		    return;
		}

		// Assume that the TargetDataLine, line, has already
		// been obtained and opened.
		ByteArrayOutputStream out  = new ByteArrayOutputStream();
		int numBytesRead;
		byte[] data = new byte[line.getBufferSize() / 5];

		// Begin audio capture.
		line.start();

		// Here, stopped is a global boolean set by another thread.
		while (true) {
		    // Read the next chunk of data from the TargetDataLine.
		    numBytesRead =  line.read(data, 0, data.length);
		    // Save this chunk of data.
		    out.write(data, 0, numBytesRead);
		}
	}
	
	public static void main(String[] args) {
		new Main().go();
	}
	
}
