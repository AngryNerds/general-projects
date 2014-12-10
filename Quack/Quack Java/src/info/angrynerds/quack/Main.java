package info.angrynerds.quack;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Main extends JFrame {
	
	JFrame mainWindow;
	Parser parser;
	Runner runner;
	
	public Main() {
		parser = new Parser();
		runner = new Runner();
		
		setTitle("Quack");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,600);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(true);
	}
	
	public static void main(String[] args) {
		Main mainWindow = new Main();
		mainWindow.initUI();
		mainWindow.setVisible(true);
	}
	
	static JPanel mainPanel = new JPanel();
	static JLabel mainTitle = new JLabel("Welcome to Quack");
	static JTextArea codeArea = new JTextArea("Type Code Here...");
	
	private void initUI() {
		
		mainPanel.setBackground(Color.DARK_GRAY);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		mainPanel.setLayout(new BorderLayout());
		
		mainTitle.setFont(new Font("Sans Serif", Font.BOLD, 40));
		mainTitle.setForeground(Color.WHITE);
		
		codeArea.setFont(new Font("American Typewriter", Font.PLAIN, 12));
		codeArea.setBackground(Color.WHITE);
		codeArea.setForeground(Color.GRAY);
		codeArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		codeArea.setPreferredSize(new Dimension(100,50));
		
		codeArea.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				codeArea.setText("");
				codeArea.setForeground(Color.BLACK);
			}
			@Override
			public void focusLost(FocusEvent arg0) { }
		});
		
		JButton runButton = new JButton("Run Code");
		runButton.setPreferredSize(new Dimension(100,50));
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				parseAndRun(codeArea.getText());
			}
		});
		
		mainPanel.add(mainTitle, BorderLayout.NORTH);
		mainPanel.add(codeArea, BorderLayout.CENTER);
		mainPanel.add(runButton, BorderLayout.SOUTH);
		
		this.add(mainPanel);
	}
	
	private void parseAndRun (String rawCode) {
		
		CodeBlock code = parser.parse(rawCode);
		
		runner.run(code);
	}
}
