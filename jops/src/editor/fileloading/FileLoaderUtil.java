package fileloading;

import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.softmed.jops.ParticleRender;
import org.softmed.jops.ParticleSystem;
import org.softmed.jops.fileloading.FileLoaderImplementation;
import org.softmed.jops.fileloading.JIBXLoader;

import textures.Textures;
import gui.DevToolBar;
import gui.Editor;
import gui.PSDetail;
import gui.generator.CustomFileFilter;

public class FileLoaderUtil {

	public static final String extension = "ops";

	// FileLoaderImplementation old = new XMLFileLoader();

	// FileLoaderImplementation impl = new XMLFileLoader();
	// FileLoaderImplementation impl = new TestXML();
	FileLoaderImplementation impl = new JIBXLoader();

	private JFrame waiting;

	public static String PS_NAME;

	public void convertDirectory(FileStatus fstat, PSDetail detail)
			throws Throwable {
		JFileChooser chooser = new JFileChooser();

		if (fstat.getDirectory() != null)
			chooser.setCurrentDirectory(fstat.getDirectory());

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Choose Directory to convert files");

		int returnVal = chooser.showOpenDialog(null);

		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;

		File choice = chooser.getSelectedFile();

		if (!choice.isDirectory())
			return;

		showWaitGUI();

		System.out.println("You chose to convert all files in this directory: "
				+ choice.getAbsolutePath());

		// XMLFileLoader xml = (XMLFileLoader) impl;
		// xml.setUseConverters(false);

		File[] files = choice.listFiles();
		ParticleSystem ps = null;
		System.out.println("Converting " + files.length);

		for (File file : files) {
			try {
				ps = impl.load(file);

				String fpath = file.getAbsolutePath();
				fpath = fpath.substring(0, fpath.lastIndexOf('.'));
				fpath = fpath + "." + FileLoaderUtil.extension;
				file.renameTo(new File(fpath));
				impl.save(file, ps);
			} catch (Throwable t) {
				t.printStackTrace();
				System.out.println("Error converting file : " + file.getName());
			}
			System.out.println("Converted file : " + file.getName());
		}

		if (fstat.getFile() != null)
			Editor.EDITOR.setTitle(fstat.getFile().getName());

	}

	public void load(FileStatus fstat, PSDetail detail) throws Throwable {
		// TODO check to see if tis dirty, if so, ask to confirm....

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

		JFileChooser chooser = new JFileChooser();

		if (fstat.getDirectory() != null)
			chooser.setCurrentDirectory(fstat.getDirectory());

		CustomFileFilter filter = new CustomFileFilter(
				"Open Particle Editor Files");
		filter.addExtension(extension);
		filter.addExtension("ope");
		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(null);

		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;

		File choice = chooser.getSelectedFile();

		if (choice.isDirectory())
			return;

		System.out.println("You chose to open this file: " + choice.getName());

		fstat.setDirectory(chooser.getCurrentDirectory());

		reallyLoadTheFile(fstat, detail, choice);
	}

	protected void showWaitGUI() {
		Editor.EDITOR.setTitle("Please wait");
		/*
		 * waiting = new JFrame("Please Wait");
		 * //waiting.getContentPane().setLayout(new Grid)
		 * waiting.setPreferredSize(new Dimension(300,100));
		 * waiting.getContentPane().add(new JLabel("This may take a few
		 * seconds..."));
		 * 
		 * waiting.setLocationRelativeTo( null ); waiting.pack();
		 * //waiting.setVisible(true);//
		 */
	}

	public void reallyLoadTheFile(FileStatus fstat, PSDetail detail, File choice)
			throws Throwable {

		showWaitGUI();

		fstat.setFile(choice);

		// XMLFileLoader xml = (XMLFileLoader) impl;
		// xml.setUseConverters(false);

		ParticleSystem ps = null;

		long before = System.currentTimeMillis();

		try {
			DevToolBar.PARTICLE_MANAGER.purge();
			DevToolBar.PARTICLE_MANAGER.getParticleLibrary().purgeAll();

			PS_NAME = DevToolBar.PARTICLE_MANAGER.load(fstat.getFile());
			ps = DevToolBar.PARTICLE_MANAGER.getParticleLibrary().getOriginal(
					PS_NAME);
			// ps =
			// DevToolBar.PARTICLE_MANAGER.getParticleLibrary().getOriginal(PS_NAME);
			// DevToolBar.PARTICLE_MANAGER.getSystems().add(ps);
			/*
			 * List<Generator> gens = ps.getGenerators(); for (Generator
			 * generator : gens) { if (generator.getRender() != null) { int id =
			 * Textures.requestTexture(generator.getRender() .getTextureName(),
			 * false); generator.getRender().setTextureId(id); } } //
			 */
			List<ParticleRender> renders = ps.getRenders();
			for (ParticleRender render : renders) {
				int id = Textures
						.requestTexture(render.getTextureName(), false);
				render.setTextureId(id);
			}

			ps = DevToolBar.PARTICLE_MANAGER.getCopyAttached(PS_NAME);
			// ps = impl.load(fstat.getFile());
			// ps = impl.load(fstat.getFile());
		} catch (Throwable t) {
			t.printStackTrace();
			JOptionPane.showMessageDialog(Editor.EDITOR,
					"Error loading file \"" + fstat.getFile().getName() + "\"");

			return;
		}

		// fstat.getFile().
		/*
		 * List<ParticleRender> renders = ps.getRenders(); for (ParticleRender
		 * render : renders) { render.setSourceFactor(render.getBlendMode1());
		 * render.setDestinationFactor(render.getBlendMode2()); }
		 * 
		 * List<Generator> gens2 = ps.getGenerators(); for (Generator generator :
		 * gens2) { generator.setKillParticles(true); } //
		 */

		// detail.getManager().purge();
		// detail.getManager().getSystems().add(ps);
		detail.setParticleSystem(ps);

		long after = System.currentTimeMillis();
		long diff = after - before;
		System.out.println("Loading took -> " + diff / 1000f + " sec. - "
				+ diff + "  miliseconds");

		Editor.setDirty(false);
		Editor.EDITOR.setTitle(choice.getName());

		hideWaiting();
	}

	protected void hideWaiting() {
		if (waiting != null) {
			waiting.setVisible(false);
			waiting.dispose();
		}
	}

	public void save(FileStatus fstat, ParticleSystem ps) throws Throwable {
		if (fstat.getFile() == null || fstat.getFile().isDirectory()
				|| !fstat.getFile().exists())
			saveAs(fstat, ps);
		else {
			showWaitGUI();
			// XMLFileLoader xml = (XMLFileLoader) impl;
			// xml.setUseConverters(false);

			try {
				long before = System.currentTimeMillis();

				// String name = fstat.getFile().getName();
				// name = name.substring(0,name.lastIndexOf('.'));
				// boolean result = fstat.getFile().renameTo(new
				// File(name+".opf"));
				// File novo = new File();
				// fstat.setFile(novo);
				// */
				String fpath = fstat.getFile().getAbsolutePath();
				fpath = fpath.substring(0, fpath.lastIndexOf('.') + 1);
				fpath = fpath + FileLoaderUtil.extension;
				fstat.getFile().renameTo(new File(fpath));
				impl.save(fstat.getFile(), ps);

				long after = System.currentTimeMillis();
				long diff = after - before;
				System.out.println("Save took -> " + diff / 1000f + " sec. - "
						+ diff + "  miliseconds");

				Editor.setDirty(false);
				Editor.EDITOR.setTitle(fstat.getFile().getName());
			} catch (Throwable e) {

				JOptionPane.showMessageDialog(Editor.EDITOR,
						"Error Saving file \"" + fstat.getFile().getName()
								+ "\"");

				e.printStackTrace();
			}
		}
	}

	public void saveAs(FileStatus fstat, ParticleSystem ps) throws Throwable {
		JFileChooser chooser = new JFileChooser();

		if (fstat.getDirectory() != null)
			chooser.setCurrentDirectory(fstat.getDirectory());

		CustomFileFilter filter = new CustomFileFilter(
				"Open Particle Editor Files");
		filter.addExtension(extension);
		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(null);

		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;

		File choice = chooser.getSelectedFile();

		if (choice.isDirectory())
			return;

		showWaitGUI();

		System.out.println("You chose to open this file: " + choice.getName());

		if (!choice.getName().endsWith("." + extension)) {

			choice = new File(choice.getCanonicalPath() + "." + extension);

		}

		fstat.setDirectory(chooser.getCurrentDirectory());
		fstat.setFile(choice);

		// XMLFileLoader xml = (XMLFileLoader) impl;
		// xml.setUseConverters(false);
		long before = System.currentTimeMillis();

		try {
			impl.save(fstat.getFile(), ps);
		} catch (Throwable t) {
			t.printStackTrace();
			JOptionPane.showMessageDialog(Editor.EDITOR, "Error Saving file \""
					+ fstat.getFile().getName() + "\"");
			return;
		}

		long after = System.currentTimeMillis();
		long diff = after - before;
		System.out.println("Save took -> " + diff / 1000f + " sec. - " + diff
				+ "  miliseconds");

		Editor.setDirty(false);

		Editor.EDITOR.setTitle(choice.getName());

	}

}
