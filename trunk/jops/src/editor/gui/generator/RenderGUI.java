package gui.generator;

import gui.Editor;
import gui.InfoObjectEditor;
import gui.SimpleEditor;
import gui.util.TextureChooser;
import gui.util.TextureChosenListener;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.softmed.jops.ParticleRender;

import textures.TextureNotFoundException;
import textures.Textures;

public class RenderGUI extends SimpleEditor implements ActionListener, TextureChosenListener {
    
    private static final long serialVersionUID = -8706693734669604245L;
    
    BlendGUI bgui = new BlendGUI();

	//static File directory = new File("./textures");

	ParticleRender render;

	int x = 100;

	int y = 100;

	JButton load = new JButton("Change");

	JLabel label = new JLabel();

	JLabel image = new JLabel();

	InfoObjectEditor infoEditor = new InfoObjectEditor();

	private String canonicalPath;

	public RenderGUI(ParticleRender gen) {
		render = gen;
		bgui.setRender(render);

		setup();
	}

	public RenderGUI() {
		setup();
	}

	protected void setup() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		image.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		setDimension(250, 350);
		setDimension(image, x, y);
		setDimension(load, 100, 20);
		setDimension(label, 250, 20);

		load.addActionListener(this);

		JPanel first = new JPanel();
		first.setLayout(new BoxLayout(first, BoxLayout.X_AXIS));
		first.add(new JLabel("Blend Options"));
		first.add(Box.createHorizontalGlue());
		add(first);
		
		add(bgui);
		
		//add(new JLabel("Texture"));
		
		first = new JPanel();
		first.setLayout(new BoxLayout(first, BoxLayout.X_AXIS));
		first.add(new JLabel("Texture"));
		first.add(Box.createHorizontalGlue());
		add(first);
		
		first = new JPanel();
		first.setLayout(new BoxLayout(first, BoxLayout.X_AXIS));
		first.add(load);
		first.add(Box.createHorizontalGlue());
		add(first);
		
		first = new JPanel();
		first.setLayout(new BoxLayout(first, BoxLayout.X_AXIS));
		first.add(image);
		first.add(Box.createHorizontalGlue());
		add(first);
		
		first = new JPanel();
		first.setLayout(new BoxLayout(first, BoxLayout.X_AXIS));
		first.add(new JLabel("FileName: "));
		first.add(label);
		first.add(Box.createHorizontalGlue());
		
		add(first);
		//add(label);
		

		add(infoEditor);

		reload();
	}

	protected void reload() {
		if (render == null)
			return;

		String path = canonicalPath;// render.getTextureName();

		try {
			if (path == null) {
				File f = new File("./media/textures/" + render.getTextureName());
				path = f.getCanonicalPath();
			}else
				path = path + "/" + render.getTextureName();

			ImageIcon ico = null;
//			
//			ico = new ImageIcon("media/textures/particle.png");
//			image.setIcon(ico);
//			load.setIcon(ico);
////
			if (path.endsWith("bmp")) {
				System.out.println("Texture Path->"+path);
				File myImageFile = new File(path);
				Image image = ImageIO.read(myImageFile);
				ico = new ImageIcon(image);
			} else
				ico = new ImageIcon(path);

			Image temp = ico.getImage();
			Image img = temp.getScaledInstance(x, y, Image.SCALE_SMOOTH);
			ico.setImage(img);
			image.setIcon(ico);
			label.setText(render.getTextureName());
			
			
		} catch (Throwable t) {
			t.printStackTrace();
			image.setIcon(null);
			label.setText("Image Path Error !!!");
		}

		refreshSelf();

	}

	public void actionPerformed(ActionEvent e) {
/*
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(directory);

		CustomFileFilter filter = new CustomFileFilter("Images");
		filter.addExtension("jpg");
		filter.addExtension("jpeg");
		filter.addExtension("gif");
		filter.addExtension("bmp");
		// filter.addExtension("tng");
		filter.addExtension("tiff");
		filter.addExtension("png");
		filter.addExtension("tga");
		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: "
					+ chooser.getSelectedFile().getName());
			try {
				canonicalPath = chooser.getSelectedFile().getCanonicalPath();
				System.out.println("Texture Can Path->"+canonicalPath);
				int id = Textures.requestTexture(canonicalPath, false);

				Textures.releaseTexture(render.getTextureId());
				render.setTextureId(id);
				render.setTextureName(chooser.getSelectedFile()
						.getCanonicalPath());

				directory = chooser.getCurrentDirectory();

				Editor.setDirty(true);
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			reload();
		}
//*/
		
		/*TextureChooser tchooser = */new TextureChooser(this);
		
	}

	public ParticleRender getRender() {
		return render;
	}

	public void setRender(ParticleRender generator) {
		this.render = generator;
		bgui.setRender(generator);
		infoEditor.setObject(generator);
		reload();
	}

	@Override
	public void setObject(Object obj) {
		setRender((ParticleRender) obj);

	}

	public void textureSelected(String path, String filename) {
		Textures.setDefaultDirectory(path);
		canonicalPath = path;
		int id;
		try {
			id = Textures.requestTexture(filename, false);
		} catch (TextureNotFoundException e) {
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(Editor.EDITOR, "Error loading texture \"" + filename+"\"");
			return;
		}

		Textures.releaseTexture(render.getTextureId());
		render.setTextureId(id);
		render.setTextureName(filename);

	//	directory = chooser.getCurrentDirectory();

		Editor.setDirty(true);
		
		reload();
		
	}

}
