package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class Editor extends JFrame {
    
    private static final long serialVersionUID = 4263631722431719995L;
    
    public static Editor EDITOR = null;

	public static boolean DIRTY = false;

	JPanel left = new JPanel();

	JPanel right = new JPanel();

	JPanel center = new JPanel();

	JPanel topCenter = new JPanel();

	JPanel bottomCenter = new JPanel();

	JPanel bottom = new JPanel();

	protected JToolBar toolbar = new JToolBar(null, JToolBar.HORIZONTAL);

	private Container container;

	static String fileName;

	public static void setDirty(boolean state) {
		// DIRTY = state;
		EDITOR.refreshTitle(Editor.fileName, state);
	}
	
    @Override
	public void setTitle(String msg) {
		// fileName = msg;
		refreshTitle(msg, false);
	}

	protected void refreshTitle(String msg, boolean dirty) {
		if (DIRTY != dirty || !msg.equals(Editor.fileName)) {
            Editor.fileName = msg;
			DIRTY=dirty;
			super.setTitle((DIRTY ? "*" : "") + fileName
					+ " - Java Open Particle System Editor V0.5");
		}
	}

	public Editor() {
		EDITOR = this;

		setTitle("untitled");

		container = this.getContentPane();
		container.setLayout(new BorderLayout());

		center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
		center.add(topCenter);
		center.add(bottomCenter);

		// this.s

		container.add(toolbar, BorderLayout.NORTH);
		container.add(center, BorderLayout.CENTER);
		container.add(bottom, BorderLayout.SOUTH);
		container.add(left, BorderLayout.WEST);
		container.add(right, BorderLayout.EAST);

		topCenter.setPreferredSize(new Dimension(600, 400));
		topCenter.setMinimumSize(new Dimension(600, 400));
		topCenter.setMaximumSize(new Dimension(600, 400));

		bottomCenter.setPreferredSize(new Dimension(600, 300));
		bottomCenter.setMinimumSize(new Dimension(600, 300));
		bottomCenter.setMaximumSize(new Dimension(600, 300));

		/*
		 * left.setPreferredSize(new Dimension(260, 700));
		 * left.setMaximumSize(new Dimension(260, 700)); left.setSize(new
		 * Dimension(260, 700)); //
		 */

		this.setPreferredSize(new Dimension(800, 600));
		this.setMinimumSize(new Dimension(300, 300));
		this.setMaximumSize(new Dimension(1280, 1024));

		this.pack();
		this.setVisible(true);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

		addWindowListener(new WindowAdapter() {
			@Override
            public void windowClosing(WindowEvent we) {
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
				System.exit(0);

			}
		});

	}

	public JToolBar getToolbar() {
		return toolbar;
	}

}
