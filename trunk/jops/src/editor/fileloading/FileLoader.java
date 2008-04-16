package fileloading;

import gui.PSDetail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class FileLoader implements ActionListener {

	
	
	public static FileLoaderUtil flUtil = new FileLoaderUtil();
	
	public JMenuItem save;
	
	public JMenuItem saveAs;

	public JMenuItem load;
	
	public JMenuItem convertDirectory;
	
	String name ;
	
	public static FileStatus fileStatus = new FileStatus();

	static PSDetail detail;

	public static void reallyLoadTheFile(File file) throws Throwable{
		flUtil.reallyLoadTheFile(fileStatus, detail,  file);
	}
	
	public FileLoader() {
		/*
		save = new JButton("SAVE");
		save.addActionListener(this);

		saveAs = new JButton("SAVE AS");
		saveAs.addActionListener(this);
		
		load = new JButton("LOAD");
		load.addActionListener(this);
		
		
		convertDirectory = new JButton("Convert Files");
		convertDirectory.addActionListener(this);
		//*/
		
		fileStatus.setDirectory(new File("./media/particle_systems"));

	}

	public void actionPerformed(ActionEvent e) {
		try{
		Object source = e.getSource();
		if (source == convertDirectory) {
			//TODO
			flUtil.convertDirectory(fileStatus, detail);
			
			
		} else
		if (source == load) {

			flUtil.load(fileStatus, detail);
			
			
		} else if (source == save) {
			
			flUtil.save(fileStatus, detail.getParticleSystem());
			
			
		}else if (source == saveAs) {
			
			flUtil.saveAs(fileStatus, detail.getParticleSystem());
			
			
		}
		
		}catch(Throwable t){
			t.printStackTrace();
			JOptionPane.showMessageDialog(detail,t.getMessage());
		}
	}


	
	

	public PSDetail getDetail() {
		return detail;
	}

	public void setDetail(PSDetail detail) {
		FileLoader.detail = detail;
	}

	
	public FileStatus getFileStatus() {
		return fileStatus;
	}

}
