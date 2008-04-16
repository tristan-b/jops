package gui.util;

import java.awt.Component;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class CustomListCellRenderer extends DefaultListCellRenderer{
    
    private static final long serialVersionUID = -453584357956795530L;
    
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
		
		File info = (File) value;
		
		
	
		setText(info.getName());
		
		return this;
	}
	
}
