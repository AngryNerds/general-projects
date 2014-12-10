package info.angrynerds.wafflecode.utils;

import info.angrynerds.wafflecode.mvc.*;

import java.io.File;

import javax.swing.JOptionPane;

public class Project {
	
	@SuppressWarnings("unused")
	private String title, owner;
	
	private Controller controller;
	
	private File directory;
	
	public final int ID = 0;
	
	public Project(String title, File dir,  Controller controller) {
		this.title = title;
		this.owner = controller.getUsername();
		this.directory = dir;
		
		this.controller = controller;
	}
	
	public Project( Controller controller ) {
		this.controller = controller;
		this.owner = controller.getUsername();
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String t) {
		title = t;
	}

	public String getPath() {
		if(directory != null) {
			return directory.getAbsolutePath();
		}
		else return "";
	}
	
	public void setDir(File dir) {
		directory = dir;
	}
	
	public File getDir() {
		return directory;
	}
}
