package gui.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomFileFilter extends javax.swing.filechooser.FileFilter {

	List<String> extension = new ArrayList<String>();
	private String description;

	public CustomFileFilter(String n)
	{
	 description = n;	
	}
	
	@Override
	public boolean accept(File f) {

		if(f.isDirectory())
			return true;
		
		String name = f.getName();
		
		for (String ext : extension) {
			if(name.endsWith(ext))
				return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
	
		
		
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addExtension(String string) {
		extension.add(string);
		
	}

}
