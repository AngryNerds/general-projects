/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.angrynerds.wafflecode.ui;

import java.io.File;

/**
 *
 * @author johnlhota
 */
public class FileBrowserFile {
    
    File file;
    
    public FileBrowserFile(File file) {
        this.file = file;
    }
    
    public FileBrowserFile(String path) {
        file = new File(path);
    }
    
    @Override
    public String toString() {
        return file.getName();
    }
    
    public File getFile() {
        return file;
    }
    
    public void setFile(File file) {
        this.file = file;
    }
}
