package gui;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class SizeablePanel  extends JPanel{
    
    private static final long serialVersionUID = 7550686888254540185L;
    
    public void setDimension(int width, int height)
	{
		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setSize(width,height);
	}
	
	public void setDimension(JComponent comp ,int width, int height)
	{
		
		comp.setPreferredSize(new Dimension(width, height));
		comp.setMinimumSize(new Dimension(width, height));
		comp.setMaximumSize(new Dimension(width, height));
		comp.setSize(width,height);
	}
	
}
