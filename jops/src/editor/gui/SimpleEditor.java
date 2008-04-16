package gui;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JComponent;

public abstract class SimpleEditor extends SizeablePanel{

	protected JComponent destination;
	protected JComponent old;

	public JComponent getDestination() {
		return destination;
	}

	public void setDestination(JComponent destination) {
		this.destination = destination;
	}
	
	public abstract void setObject(Object obj);
	
	protected void refreshSelf()
	{
		invalidate();
		revalidate();
		repaint();
	}
	
	protected void refreshDestination()
	{
		destination.invalidate();
		destination.revalidate();
		destination.repaint();
	}

	public JComponent getOld() {
		return old;
	}

	public void setOld(JComponent old) {
		this.old = old;
	}
	
	public void back()
	{
		if(old == null)
			return;
		
		Container parent = this.getParent();
		parent.remove(this);
		parent.add(old);
		
		parent.invalidate();
		parent.repaint();
		
	}
	
	public void setEasyDimension(int width, int height)
	{
		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setSize(width,height);
	}
	
	public void setEasyDimension(JComponent comp ,int width, int height)
	{
		
		comp.setPreferredSize(new Dimension(width, height));
		comp.setMinimumSize(new Dimension(width, height));
		comp.setMaximumSize(new Dimension(width, height));
		comp.setSize(width,height);
	}
	
}
