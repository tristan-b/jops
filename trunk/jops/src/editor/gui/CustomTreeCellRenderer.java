package gui;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.softmed.jops.InfoObject;


@SuppressWarnings("serial")
public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
    
    @Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);

		DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) value;
		Object obj = dnode.getUserObject();
		
		if(obj !=null && ! (obj instanceof String)){
			if(obj instanceof InfoObject){
				InfoObject info = (InfoObject) obj;
				
				if(info.getName()!=null && !info.getName().equals(""))
					setText(info.getName()  + " - " + obj.getClass().getSimpleName());
					else
					setText(obj.getClass().getSimpleName());
			}
			else
			{
				setText((String) value);
			}
		}
		return this;
	}

}
