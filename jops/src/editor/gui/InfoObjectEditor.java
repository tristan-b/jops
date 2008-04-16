package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.softmed.jops.InfoObject;


public class InfoObjectEditor extends SimpleEditor implements KeyListener {
    
    private static final long serialVersionUID = -873384553814034640L;
    
    public static PSTree PSTREE;

	JTextField name = new JTextField();

	JTextArea description = new JTextArea();

	InfoObject info;

	public InfoObjectEditor() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		setDimension(name, 250, 20);
		//setDimension(description, 250, 70);

		name.addKeyListener(this);
		description.addKeyListener(this);
		
		JPanel der = new JPanel();
		der.setLayout(new BoxLayout(der, BoxLayout.X_AXIS));
		der.add(new JLabel("Name"));
		der.add(Box.createHorizontalGlue());
		add(der);
		
		//add(new JLabel("Name"));
		add(name);
		
		JPanel der2 = new JPanel();
		der2.setLayout(new BoxLayout(der2, BoxLayout.X_AXIS));
		der2.add(new JLabel("Description"));
		der2.add(Box.createHorizontalGlue());
		add(der2);
		
		//add(new JLabel("Description"));
		JScrollPane panel = new JScrollPane(description);
		setDimension(panel, 250, 70);
		
		add(panel);
	}

	@Override
	public void setObject(Object obj) {
		info = (InfoObject) obj;
		if (info == null) {
			name.setText("");
			description.setText("");
			return;
		}

		name.setText(info.getName());
		description.setText(info.getDescription());
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(KeyEvent e) {
		Editor.setDirty(true);
		
		if (info != null) {
			if (e.getSource() == name) {
				info.setName(name.getText());

				if (PSTREE != null)
					PSTREE.refreshNode(info);
			} else
				info.setDescription(description.getText());
		}

	}

	public void keyTyped(KeyEvent e) {

	}

}
