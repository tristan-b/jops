package gui.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fileloading.FileLoader;
import fileloading.FileLoaderUtil;
import gui.Editor;
import gui.SizeablePanel;



@SuppressWarnings("serial")
public class QuickFileBrowser extends SizeablePanel implements ActionListener,
		ListSelectionListener {

	List<String> filenames = new ArrayList<String>();
	
	public static final int REFRESH_TIME = 1000;

	JButton change = new JButton("Change Directory");

	JScrollPane scrollLista;

	JList lista = new JList();

	private DefaultListModel model;

	private File currentDirectory;

	private static DirectoryRefresher refresher;

	public static void closeIt()
	{
		if(refresher!=null)
			refresher.setRefreshing(false);
	}
	
	
	public QuickFileBrowser() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		change.setHorizontalAlignment(SwingConstants.LEFT);
		setDimension(change, 150, 20);

		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		header.add(change);
		header.add(Box.createHorizontalGlue());

		add(header);
		change.addActionListener(this);
		// lista.setVisibleRowCount(30);
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista.setCellRenderer(new CustomListCellRenderer());
		model = new DefaultListModel();
		lista.setModel(model);
		scrollLista = new JScrollPane(lista);
		// scrollLista.add(lista);
		setDimension(scrollLista, 200, 620);
		add(scrollLista);

		lista.addListSelectionListener(this);
		setDimension(150, 700);

		// this.setVisible(true);
		// */
		refresher = new DirectoryRefresher(this);
	}

	public void actionPerformed(ActionEvent e) {

		JFileChooser chooser = new JFileChooser();

		if (FileLoader.fileStatus.getDirectory() != null)
			chooser.setCurrentDirectory(FileLoader.fileStatus.getDirectory());

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Choose Directory to convert files");

		int returnVal = chooser.showOpenDialog(null);

		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;

		File choice = chooser.getSelectedFile();

		if (!choice.isDirectory())
			return;

		FileLoader.fileStatus.setDirectory(choice);

		loadFilesInDirectory(choice);
	}

	public void loadFilesInDirectory(File choice) {
		// choice = new File(choice.getAbsolutePath());
		currentDirectory = choice;
		File[] fileList = choice.listFiles();

		filenames.clear();
		model.clear();
		if (fileList != null)
			for (File file : fileList) {
				if (file.isFile() && (file.getName().endsWith("."+ FileLoaderUtil.extension) || file.getName().endsWith(".ope")) ){
					model.addElement(file);
					filenames.add(file.getName());
				}
				
			}

		scrollLista.getViewport().repaint();
		scrollLista.revalidate();
		scrollLista.repaint();
	}

	public void refreshDirectory() {
		if (currentDirectory == null)
			return;

		File[] fileList = currentDirectory.listFiles();

		// model.clear();
		if (fileList == null)
			return;

		boolean dirty = false;
		int count = 0;
		for (File file : fileList) {
			if (file.isFile() && (file.getName().endsWith("."+ FileLoaderUtil.extension) || file.getName().endsWith(".ope"))){
				count ++;
				if (!filenames.contains(file.getName())) {
					dirty = true;
					break;
				}
			}
		}
		
		if(count!=filenames.size())
			dirty = true;
		
		if (dirty)
			loadFilesInDirectory(currentDirectory);

	}

	public void valueChanged(ListSelectionEvent e) {
		//System.out.println("adjusting GUI -> " + e.getValueIsAdjusting());
		
		if (lista.getSelectedValue() == null)
			return;

		if (Editor.DIRTY) {
			int duh = JOptionPane
					.showConfirmDialog(
							Editor.EDITOR,
							"You have unsaved changes on this scene.\n"
									+ "Would you like to cancel loading so that you can save this scene ???\n"
									+ "If you click yes, you will cancel loading a file, and you will have\n the option of saving this scene."
									+ "\nIf you click no you will lose all changes applied to this scene.",
							"test2", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
			if (duh == JOptionPane.YES_OPTION)
				return;

		}
//		if( e.getValueIsAdjusting())
		try {
			FileLoader.reallyLoadTheFile((File) lista.getSelectedValue());
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
