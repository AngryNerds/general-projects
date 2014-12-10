/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.angrynerds.wafflecode.utils;

import com.apple.eawt.*;
import com.apple.eawt.OpenFilesHandler;
import com.apple.eawt.FullScreenUtilities;
import info.angrynerds.wafflecode.mvc.*;
import java.io.File;
import java.util.List;

/**
 * A nifty little helper class that allows for deeper integration into the Mac OS.
 * Note: the errors in this class aren't actually errors, for some reason they compile just fine.
 * @author John Lhota
 */
public class MacHelper {
    
    private Controller controller;
    private Model model;
    private View view;
    
    Application app;
    
    public MacHelper(Controller controller) {
        this.controller = controller;
        model = controller.getModel();
        view = controller.getView();
    }
    
    public void go() {
        if(Globals.OS.startsWith("Mac OS X")) {
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "WaffleCode");
        
            app = Application.getApplication();
            app.setOpenFileHandler( new FileOpener() );
        }
    }
    
    public void enableFullScreen() {
        FullScreenUtilities.setWindowCanFullScreen( controller.getView().getFrame(), true );
    }
    
    private class FileOpener implements OpenFilesHandler {

            @Override
            public void openFiles(AppEvent.OpenFilesEvent ofe) {
                List<File> files = ofe.getFiles();
                
                if( files.size() == 1 ) {
                    File file = files.get(0);
                    if(file.isDirectory()) {
                        model.selectProject( new Project( file.getName(), file, controller ) );
                    }
                    else {
                        model.openDoc( file );
                    }
                }
                else {
                    Project project;
                    for(File file : files) {
                        model.openDoc( file );
                    }
                }
            }
            
        }
    
}
