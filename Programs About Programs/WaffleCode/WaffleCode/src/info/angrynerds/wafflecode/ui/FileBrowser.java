package info.angrynerds.wafflecode.ui;

import info.angrynerds.wafflecode.mvc.Controller;

import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.File;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class FileBrowser {
	
	private Controller controller;
	
	private JPanel panel;
	private JTree fileTree;
	
	public FileBrowser(Controller c) {
		controller = c;
	}
	
	public JPanel buildUI( File projDir ) {
		if(panel == null) {
			panel = new JPanel();
		}
		panel.setBackground(Theme.BACKGROUND);
		
                DefaultMutableTreeNode projNode = findFilesIn( projDir );
		fileTree = new JTree( projNode );
		
		fileTree.addMouseListener( (MouseListener) new FileOpenListener() );
		JScrollPane treeScroller = new JScrollPane(fileTree,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(treeScroller);
		
		return panel;
	}
	
	//FIXME doesn't update screen unless the root is closed
	public void updateNodes(File projDir) {
            //((FileBrowserFile) fileTree.getModel().getRoot()).setFile( projDir );
            
            fileTree = new JTree( findFilesIn(projDir) );
            fileTree.getRootPane().repaint();
	}
	
	private DefaultMutableTreeNode findFilesIn( File dir ) {
		// Turn dir into treenode.
		DefaultMutableTreeNode node = new DefaultMutableTreeNode( new FileBrowserFile(dir) );
		
		// The list of files can also be retrieved as File objects
		File[] files = dir.listFiles();
		
		if(files == null) {
			System.out.println("\"" + dir.getAbsolutePath() + "\" is not a valid directory.");
			return node;
			//throw new IOException("Could not find specified directory.");
		}
		
		for(File f : files) {
			if( f.isDirectory() ) { // If it's a directory, use some recursion to add all the files within it.
				System.out.println("Found the directory \"" + f.getName() + "\"");
				DefaultMutableTreeNode newDir = findFilesIn( f );
				node.add( newDir );
			}
			else { // If it's a file, add it to the tree.
				System.out.println("Found the file \"" + f.getName() + "\" at " + f.getAbsolutePath());
				DefaultMutableTreeNode subNode = new DefaultMutableTreeNode( new FileBrowserFile(f) );
				node.add( subNode );
			}
		}
		
		return node;
	}
	
	private class FileOpenListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int clickedRow = fileTree.getRowForLocation( e.getX(), e.getY() );
			
			if( clickedRow != -1 && e.getClickCount() > 1 ) {
				TreePath path = fileTree.getPathForLocation( e.getX(), e.getY() );
				/*String name = ((DefaultMutableTreeNode) path.getLastPathComponent()).toString();
				String url = path.toString();
				url = url.substring( 1 + fileTree.getModel().getRoot().toString().length(),
						url.length() - 1 ).replace(", ", "/");
				//In the previous line, we convert the TreePath's format from [root, thing, blah] to root/thing/blah,
				//we're about to combine it with the path to root, so we make sure we don't say root/root by removing one of them.
				url = projectDir + url;
				System.out.println( url );
				File file = new File( url );*/
				
				File file = ((FileBrowserFile) ((DefaultMutableTreeNode)
                                        path.getLastPathComponent()).getUserObject()).getFile();
                                System.out.println(((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject());
                                                                
				String name = file.getName();
				System.out.println("Attempting to open " + name + "...");
				
				
				if( !file.isDirectory() ) {
                                    if( name.endsWith(".java") ) { //TODO Extend to allow whatever lang the project is written in.
					controller.getModel().openDoc( file );
                                    }
                                    else {
                                        try {
						Desktop.getDesktop().open( file );
					}
					catch (IOException ex) {
						ex.printStackTrace();
					}
                                    }
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) { }

		@Override
		public void mouseExited(MouseEvent e) { }

		@Override
		public void mousePressed(MouseEvent e) { }

		@Override
		public void mouseReleased(MouseEvent e) { }
		
	}
}
