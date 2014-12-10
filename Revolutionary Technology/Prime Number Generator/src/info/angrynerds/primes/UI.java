package info.angrynerds.primes;

import java.awt.Graphics;
import java.math.BigInteger;
import java.util.HashMap;

import javax.swing.*;

public class UI {
	
	JFrame frame;
	
	HashMap<BigInteger, BigInteger> remainders;
	
	public UI(HashMap<BigInteger, BigInteger> map) {
		remainders = map;
		
		frame = new JFrame();
		frame.getContentPane().add(new GraphPanel());
		frame.setVisible(true);
	}
	
	class GraphPanel extends JPanel {
		
		@Override
		public void paint(Graphics g) {
			for(BigInteger i : remainders.keySet()) {
				
			}
		}
		
	}
	
}
