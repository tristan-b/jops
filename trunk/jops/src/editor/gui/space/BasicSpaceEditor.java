package gui.space;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import gui.InfoObjectEditor;
import gui.SimpleEditor;

public abstract class BasicSpaceEditor extends SimpleEditor implements ActionListener{

	public static final int widthB = 80;
	public static final int heightB = 20;
	
	protected JButton back = new JButton("Back");
	//protected JLabel label = new JLabel("Point Space - Nothing to Edit");
	protected JPanel column = new JPanel();
	
	
	InfoObjectEditor infoEditor = new InfoObjectEditor();
	protected JPanel top;
	
	public BasicSpaceEditor(JComponent destination)
	{
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		back.setVisible(false);
		back.addActionListener(this);
		
		top = new JPanel();
		top.setLayout(new BoxLayout(top,BoxLayout.LINE_AXIS));
		top.add(back);
		//top.add(label);
		add(top);
		
		
		column.setLayout(new BoxLayout(column,BoxLayout.PAGE_AXIS));
		add(column);
		setDestination(destination);
		
		add(infoEditor);

	}
	
	
	
	@Override
	public void setOld(JComponent old) {
		super.setOld(old);
		
		back.setVisible(old != null);
		
	}


	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == back)
		{
			back();
		}else
		actionDone(source);
		
		refreshDestination();
	}




	protected abstract void actionDone(Object source) ;

	@Override
	public void setObject(Object obj) {
		infoEditor.setObject(obj);
		setObjectMine(obj);

	}




	protected abstract void setObjectMine(Object obj) ;
	
	
}
