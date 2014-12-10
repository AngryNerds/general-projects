package info.angrynerds.wafflecode.ui;

import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import info.angrynerds.wafflecode.mvc.Controller;

import javax.swing.JEditorPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuMaker {
	public static JMenuBar initMenu(Controller controller, JEditorPane code) {
		
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		// If this is a Mac, put the MenuBar at the top of the screen.
		
		JMenuBar bar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		
			JMenuItem save = new JMenuItem("[auto-save status]"); // Just to say "Saving..." or "Saved."
			save.setEnabled(false);
			file.add(save);
			
			file.addSeparator();
			
			JMenuItem newProject = new JMenuItem("New Project...");
				newProject.addActionListener(new MenuBarListener(controller, code));
				//newProject.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_N,
						//( InputEvent.SHIFT_MASK & (Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())) ));
				file.add(newProject);
			
			JMenuItem newClass = new JMenuItem("New Class...");
				newClass.addActionListener(new MenuBarListener(controller, code));
				newClass.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_N,
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) );
			file.add(newClass);
			
			JMenuItem newFile = new JMenuItem("New File...");
				newFile.addActionListener(new MenuBarListener(controller, code));
				//newProject.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_N,
						//( InputEvent.ALT_MASK | (Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())) ));
			file.add(newFile);
			
			file.addSeparator();
			
			JMenuItem openProject = new JMenuItem("Open Project...");
				openProject.addActionListener(new MenuBarListener(controller, code));
				newProject.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_O,
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() ) );
			file.add(openProject);
		
		JMenu edit = new JMenu("Edit");
		
			JMenuItem copy = new JMenuItem("Copy");
			copy.addActionListener(new MenuBarListener(controller, code));
			edit.add(copy);
			JMenuItem cut = new JMenuItem("Cut");
			cut.addActionListener(new MenuBarListener(controller, code));
			edit.add(cut);
			JMenuItem paste = new JMenuItem("Paste");
			paste.addActionListener(new MenuBarListener(controller, code));
			edit.add(paste);
			
			edit.addSeparator();
			
			JMenuItem comment = new JMenuItem("Comment Out");
			paste.addActionListener(new MenuBarListener(controller, code));
			edit.add(comment);
			
		JMenu view = new JMenu("View");	
		
			JMenu showConsole = new JMenu("Show Console");
			
			JMenuItem windowItem = new JMenuItem("In a separate window");
				windowItem.addActionListener(new MenuBarListener(controller, code));
			showConsole.add(windowItem);
			
			JMenuItem dockedItem = new JMenuItem("Docked to the bottom of this window");
				dockedItem.addActionListener(new MenuBarListener(controller, code));
			showConsole.add(dockedItem);
			
			view.add(showConsole);
			
			JMenuItem hideItem = new JMenuItem("Hide Console");
				hideItem.addActionListener(new MenuBarListener(controller, code));
				view.add(hideItem);
		
		JMenu run = new JMenu("Run");
			
			JMenuItem runCode = new JMenuItem("Run Project");
				runCode.addActionListener( new MenuBarListener(controller, code) );
				run.add(runCode);
			
		JMenu network = new JMenu("Network");
		
			JMenuItem ip = new JMenuItem("Change Server IP...");
			ip.addActionListener(new MenuBarListener(controller, code));
			network.add(ip);
			JMenuItem port = new JMenuItem("Change Server Port...");
			port.addActionListener(new MenuBarListener(controller, code));
			network.add(port);

		bar.add(file);
		bar.add(edit);
		bar.add(view);
		bar.add(run);
		bar.add(network);
		
		return bar;
	}
}
