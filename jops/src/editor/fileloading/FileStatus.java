package fileloading;

import java.io.File;

public class FileStatus {
	File file;
	File directory;

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public void reset(){
		file = null;
		//directory=null;
	}

}
