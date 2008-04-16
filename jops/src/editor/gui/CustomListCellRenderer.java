package gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import org.softmed.jops.InfoObject;


public class CustomListCellRenderer extends DefaultListCellRenderer{
    
    private static final long serialVersionUID = 2061737587086607949L;
    
    @Override
	public Component getListCellRendererComponent(JList list, Object value,
			int number ,boolean sel, boolean expanded) {

		super.getListCellRendererComponent(list, value, number, sel, expanded);

		if(value == null)
			return this;
		
		
		if(value instanceof String){
			setText((String) value);
			return this;
		}
		
		InfoObject info = (InfoObject) value;
		
		
		if(info.getName()!=null && !info.getName().equals(""))
		setText(info.getName()  + " - " + value.getClass().getSimpleName());
		else
		setText(value.getClass().getSimpleName());	
		 
		return this;
	}
	
}
