package info.angrynerds.wafflecode.mvc;

import java.io.File;

import info.angrynerds.wafflecode.utils.*;

public interface Model extends MVCListener {
	public String getTitleOfProgram();
	public void setTitleOfProgram(String str);
	
	
	public Controller getController();
	
	
	public Project getProject();
	
	public File getDoc();
	public void openDoc(File doc);
	
	
	public void insert(int start, String what);
	public void remove(int start, int length);
	
	public PropSaver getProps();
	public void loadProps();
	
	
	public void saveCurrentDoc();
	
	
	public String getText(File file);
	public File createDoc(String string, Project example);
	public void selectProject(Project project);
	public void createProject(Project project);
	public Project findProject(int parseInt);
	void saveDoc(File doc, String text);
}
