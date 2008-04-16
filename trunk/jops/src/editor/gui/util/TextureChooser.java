package gui.util;

import fileloading.FileLoader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import textures.Textures;

public class TextureChooser extends JDialog implements ActionListener {
    
    private static final long serialVersionUID = -2271475019133699990L;
    
    static Map<String, ImageIcon> icons = new HashMap<String, ImageIcon>();

	static List<String> fileCache = new ArrayList<String>();

	static Map<String, Long> fileCacheModified = new HashMap<String, Long>();

	static Map<String, Long> fileCacheSize = new HashMap<String, Long>();

	static String currentDirectory;

	int IMAGES_PER_LINE = 6;

	int WIDTH = 100;

	int HEIGHT = 100;

	JButton cancel = new JButton("CANCEL");

	JButton change = new JButton("Change");

	JTextField directory = new JTextField();

	JScrollPane scroll;

	JPanel grid = new JPanel();

	private JPanel currentLinePanel;

	private int currentLineImage;

	TextureChosenListener listener;

	public TextureChooser(TextureChosenListener listener) {
		this.listener = listener;
		setTitle("Choose Texture");
		setModal(true);
		setLayout(new BorderLayout());
		setEasyDimension(630, 400);
		setEasyDimension(directory, 340, 20);
		setEasyDimension(change, 100, 20);

		directory.setEditable(false);

		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		top.add(Box.createHorizontalGlue());
		top.add(new JLabel("Directory"));
		top.add(directory);
		top.add(change);
		top.add(Box.createHorizontalGlue());

		add(top, BorderLayout.NORTH);

		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
		bottom.add(Box.createHorizontalGlue());
		// bottom.add(ok);
		bottom.add(cancel);
		bottom.add(Box.createHorizontalGlue());

		add(bottom, BorderLayout.SOUTH);

		// grid.setLayout(new GridLayout(0,IMAGES_PER_LINE));
		grid.setLayout(new BoxLayout(grid, BoxLayout.Y_AXIS));
		scroll = new JScrollPane(grid);

		setEasyDimension(scroll, 600, 300);

		showImages(Textures.getDefaultDirectory());

		add(scroll, BorderLayout.CENTER);
		// */

		change.addActionListener(this);
		// ok.addActionListener(this);
		cancel.addActionListener(this);

		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void showImages(String defaultDirectory) {

		File directory = new File(defaultDirectory);
		if (!directory.isDirectory())
			return;

		File[] files = null;
		List<File> imageFiles = null;

		files = directory.listFiles();
		imageFiles = getImageFilesFromDirectory(files);

		if (currentDirectory != null
				&& currentDirectory.equals(defaultDirectory)) {

			if (imageFiles.size() == fileCache.size()) {

				boolean reload = false;
				String name = null;
				long size;
				long modified;
				for (File file : imageFiles) {
					name = file.getName();
					if (!fileCache.contains(name)) {
						reload = true;
						break;
					}

					size = fileCacheSize.get(name);
					if (size != file.length()) {
						reload = true;
						break;
					}

					modified = fileCacheModified.get(name);
					if (modified != file.lastModified()) {
						reload = true;
						break;
					}

				}

				if (!reload) {

					this.directory.setText(defaultDirectory);
				/*	
					grid.removeAll();
					grid = new JPanel();
					grid.setLayout(new BoxLayout(grid, BoxLayout.Y_AXIS));
					scroll = new JScrollPane(grid);

					setEasyDimension(scroll, 500, 300);
//*/
					//showImages(Textures.getDefaultDirectory());

					
					for (File file : imageFiles) {
						//createButton(file);
						createDisplay(createButton(file));
						/*button = new JButton();
						button.setVerticalTextPosition(JButton.BOTTOM);
						button.setHorizontalTextPosition(JButton.CENTER);
						setEasyDimension(button, WIDTH, HEIGHT + 20);
						button.setText(file.getName());
						button.addActionListener(this);
						button.setIcon(icons.get(file.getName()));
						createDisplay(button);
						//grid.add(new JButton(icons.get(file.getName())));
						 *//*/
						 */
					}
					if (currentLinePanel != null)
						currentLinePanel.add(Box.createHorizontalGlue());
					grid.add(Box.createVerticalGlue());

					scroll.getViewport().repaint();
					scroll.revalidate();
					scroll.repaint();

					invalidate();
					validate();
					repaint();
					
					//add(scroll, BorderLayout.CENTER);
					
					return;
				}
			}

		}

		fileCache.clear();
		fileCacheSize.clear();
		fileCacheModified.clear();
		grid.removeAll();
		icons.clear();

		for (File file : imageFiles) {
			fileCache.add(file.getName());
			fileCacheSize.put(file.getName(), file.length());
			fileCacheModified.put(file.getName(), file.lastModified());
			createDisplay(createButton(file));
		}

		if(currentLinePanel!=null)
		currentLinePanel.add(Box.createHorizontalGlue());
		grid.add(Box.createVerticalGlue());

		this.directory.setText(defaultDirectory);
		currentDirectory = new String(defaultDirectory);
		Textures.setDefaultDirectory(new String(defaultDirectory));

		scroll.getViewport().repaint();
		scroll.revalidate();
		scroll.repaint();
		
		invalidate();
		validate();
		repaint();

	}

	protected List<File> getImageFilesFromDirectory(File[] files) {
		String extension = null;
		List<File> imageFiles = new ArrayList<File>();

		for (File file : files) {
			if (file.isDirectory())
				continue;

			extension = file.getName();

			if (extension.endsWith("jpg") || extension.endsWith("jpeg")
					|| extension.endsWith("gif") || extension.endsWith("bmp")
					|| extension.endsWith("tiff") || extension.endsWith("png")
					|| extension.endsWith("tga"))
				imageFiles.add(file);
		}

		return imageFiles;
	}

	private void createDisplay(Component component) {
		if (currentLinePanel == null || currentLineImage == IMAGES_PER_LINE) {
			currentLinePanel = new JPanel();
			currentLinePanel.setLayout(new BoxLayout(currentLinePanel,
					BoxLayout.X_AXIS));
			grid.add(currentLinePanel);
			currentLineImage = 0;
		}

		currentLineImage++;
		currentLinePanel.add(component);

	}

	private Component createButton(File file) {
		JButton button = new JButton();
		button.setBackground(Color.BLACK);
		button.setForeground(Color.WHITE);
		button.setVerticalTextPosition(JButton.BOTTOM);
		button.setHorizontalTextPosition(JButton.CENTER);
		setEasyDimension(button, WIDTH, HEIGHT + 20);
		try {
			ImageIcon ico = null;

			ico = icons.get(file.getName());

			if (ico == null) {

				if (file.getName().endsWith("bmp")) {
					Image image = ImageIO.read(file);
					ico = new ImageIcon(image);
				} else
					ico = new ImageIcon(file.getAbsolutePath());

				Image temp = ico.getImage();
				Image img = temp.getScaledInstance(WIDTH, HEIGHT,
						Image.SCALE_SMOOTH);
				ico.setImage(img);
				icons.put(file.getName(), ico);
			}

			button.setIcon(ico);
			button.setText(file.getName());
			button.addActionListener(this);
			
		} catch (Throwable t) {
			t.printStackTrace();
			button.setText("Image Path Error !!!");
		}

		return button;
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == change) {
			JFileChooser chooser = new JFileChooser();

			if (FileLoader.fileStatus.getDirectory() != null)
				chooser.setCurrentDirectory(new File(currentDirectory));

			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setDialogTitle("Choose Directory to convert files");

			int returnVal = chooser.showOpenDialog(null);

			if (returnVal != JFileChooser.APPROVE_OPTION)
				return;

			File choice = chooser.getSelectedFile();

			setVisible(false);
			currentDirectory = choice.getAbsolutePath();
			Textures.setDefaultDirectory(currentDirectory);
			new TextureChooser(listener);

			dispose();
			//showImages(choice.getAbsolutePath());
			
			
			//invalidate();
			//validate();
			//repaint();

		} else if (source == cancel) {
			setVisible(false);
			dispose();
		} else {
			JButton button = (JButton) source;
			String filename = button.getText();
			listener.textureSelected(currentDirectory, filename);
			setVisible(false);
			dispose();
		}

	}

	public void setEasyDimension(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setSize(width, height);
	}

	public void setEasyDimension(JComponent comp, int width, int height) {

		comp.setPreferredSize(new Dimension(width, height));
		comp.setMinimumSize(new Dimension(width, height));
		comp.setMaximumSize(new Dimension(width, height));
		comp.setSize(width, height);
	}

}
