package info.angrynerds.wafflecode.mvc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.text.BadLocationException;

import info.angrynerds.wafflecode.utils.*;

public class WaffleModel implements Model {

	private PropSaver props;
	
	private Controller controller;
	
	private Project project;
	private File doc;//file that is actually open
	private ArrayList<File> tabs;
	
	public WaffleModel(Controller controller) {
		tabs = new ArrayList<File>();
		
		this.controller = controller;
	}
	
	@Override
	public void insert(int start, String what) {
//		try {
//			//stuff
//		}
//		catch (IOException ex) {
//			ex.printStackTrace();
//		}
	}

	@Override
	public void remove(int start, int length) {
//		try {
//			//stuff
//		}
//		catch (IOException ex) {
//			ex.printStackTrace();
//		}
	}
	
	public String getTitleOfProgram() {
		return project.getTitle();
	}

	public void setTitleOfProgram(String title) {
		project.setTitle(title);
	}
	
	@Override
	public Project getProject() {
		return project;
	}
	
	@Override
	public void selectProject(Project project) {
		this.project = project;
	}
	
	@Override
	public File getDoc() {
		return doc;
	}
	
	@Override
	public void openDoc(File doc) {		
            this.doc = doc;
	    controller.setJustOpened( true );
	    controller.getView().openDoc( doc );
	}

	@Override
	public PropSaver getProps() {
		if(props != null) {
			return props;
		}
		else {
			props = new PropSaver();
			return getProps();
		}
	}

	@Override
	public void loadProps() {
		try {
			getProps().load();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	public void itemRead(MVCEvent event) {
		/*switch(event.getType()) {
		case CONTROLLER_CODE_INSERTED:
			if( !event.getDoc().equals( doc ) ) {
				String text = getText(event.getDoc());
				saveDoc(event.getDoc(), text.substring(0, event.getOffset())
						+ event.getText() + text.substring(event.getOffset()));
			}
			break;
		case CONTROLLER_CODE_DELETED:
			if( !event.getDoc().equals( doc ) ) {
				String text = getText(event.getDoc());
				saveDoc(event.getDoc(), text.substring(0, event.getOffset()) +
						text.substring(event.getOffset() + event.getLength() ));
			}
			break;
		}*/
	}
	
	@Override
	public void saveDoc(File doc, String text) {
		try {
			FileWriter writer = new FileWriter( doc );
			writer.write( text );
			writer.close();
			System.out.println("saved");
		}
		catch (IOException armageddon) {
			armageddon.printStackTrace();
		}
	}
	
	@Override
	public void saveCurrentDoc() {
		saveDoc(doc, controller.getView().getCodeEditor().getText() );
	}

	@Override
	public String getText(File file) {
		String text = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while( ( line = reader.readLine() ) != null ) {
				text += line + '\n';
			}
			reader.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			text = "ERROR: Could not find file.";
		}
		catch (IOException e) {
			e.printStackTrace();
			text = "ERROR: Could not read file contents.";
		}
		
		return text;
	}

	@Override
	public File createDoc(String name, Project p) {
		File f = new File( p.getDir().getAbsolutePath() + "/" + (name.endsWith(".java")? name+".java" : name) );
		
		try {
			FileWriter writer = new FileWriter(f);
			writer.write("");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return f;
	}
	
	@Override
	public Controller getController() {
		return controller;
	}

	@Override
	public void createProject(Project hypothetical) {
		File location = hypothetical.getDir();
		location.mkdir();
		File src = new File(location, "src");
		src.mkdir();
		File bin = new File(location, "bin");
		bin.mkdir();
	}

	@Override
	public Project findProject(int parseInt) {
		// TODO Auto-generated method stub
		return null;
	}

}