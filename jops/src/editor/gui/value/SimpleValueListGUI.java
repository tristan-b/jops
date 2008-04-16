package gui.value;

import gui.Editor;
import gui.SizeablePanel;
import gui.indicators.Indicator;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.softmed.jops.fileloading.converters.IValueConverter;
import org.softmed.jops.valuelists.SimpleGenericValueList;
import org.softmed.jops.values.GenericValue;


public class SimpleValueListGUI extends SizeablePanel implements EditActionListener,
		ActionListener, ChangeListener {
    
    private static final long serialVersionUID = -8058310913980001708L;
    
    JPanel parent = new JPanel();

	JPanel content = new JPanel();

	boolean valid;

	JCheckBox validGUI = new JCheckBox("Valid");

	ValueListChangedListener listener;

	JCheckBox repeat = new JCheckBox("Repeat");

	//JCheckBox random = new JCheckBox("Random");
	
	//FloatIndicator randomness  = new FloatIndicator();

	JScrollPane spane = new JScrollPane();

	SimpleGenericValueList gvaluelist;

	private List vlist;

	private Object[] args;

	List<GenericValueGUI> list = new ArrayList<GenericValueGUI>();

	private IValueConverter converter;

	protected void signalChange() {
		if (listener != null)
			listener.changed();
	}

	public void setCustomEnabled(boolean enabled) {

		for (GenericValueGUI g : list) {
			g.setCustomEnabled(enabled);
		}

	}

	public SimpleValueListGUI(String title) {
		validGUI.setVisible(false);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		setDimension(590, 215);
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		parent.setLayout(new BoxLayout(parent, BoxLayout.PAGE_AXIS));

		parent.add(content);
		parent.add(Box.createVerticalGlue());
		JPanel shor = new JPanel();
		shor.setLayout(new BoxLayout(shor, BoxLayout.LINE_AXIS));

		JLabel label = new JLabel(title);
		shor.add(label);
		shor.add(repeat);
		
		shor.add(validGUI);
		validGUI.addActionListener(this);
		repeat.addActionListener(this);

		add(shor);
		spane.setViewportView(parent);

		add(spane);

		invalidate();
		revalidate();
		repaint();

		spane.invalidate();
		spane.revalidate();
		spane.repaint();
	}

//	public void setValueList(SimpleGenericValueList gvaluelist, Object... args) {
//		this.gvaluelist = gvaluelist;
//		vlist = gvaluelist.getValues();
//		this.args = args;
//		repeat.setSelected(gvaluelist.isRepeat());
//
//		reload();
//	}
	
	public void setValueList(IValueConverter converter,SimpleGenericValueList gvaluelist,Object... args) {
		this.converter = converter;
		this.gvaluelist = gvaluelist;
		vlist = gvaluelist.getValues();
		this.args = args;
		repeat.setSelected(gvaluelist.isRepeat());

		reload();
	}

	public void reload() {
		list.clear();
		content.removeAll();
		GenericValue elem;
		GenericValueGUI vgui;

		for (int i = 0; i < vlist.size(); i++) {
			elem = (GenericValue) vlist.get(i);
			vgui = new GenericValueGUI(converter,elem);
			//vgui.setConverter();
			vgui.setArgs(args);
			vgui.setGvalue(elem);
			vgui.setEditActionListener(this);
			content.add(vgui);
			list.add(vgui);
		}

		content.invalidate();
		content.revalidate();
		content.repaint();
		// this.revalidate();
	}

	public void changed(GenericValueGUI gui) {
		Editor.setDirty(true);
		int index = getIndex(gui);
		GenericValueGUI nextGUI = getNextIndex(index);
		GenericValueGUI previousGUI = getPreviousIndex(index);

		GenericValue nextValue = null;
		GenericValue previousValue = null;

		if (previousGUI != null)
			previousValue = previousGUI.getGvalue();

		if (nextGUI != null)
			nextValue = nextGUI.getGvalue();

		GenericValue value = gui.getGvalue();
		if (previousValue != null) {
			if (value.getTime() <= previousValue.getTime()) {
				value.setTime(previousValue.getTime() + 0.01f);
				gui.refresh();
			}
		} else {
			if (value.getTime() > 0) {
				value.setTime(0.0f);
				gui.refresh();
			}
		}

		if (nextValue != null) {
			if (value.getTime() >= nextValue.getTime()) {
				value.setTime(nextValue.getTime() - 0.01f);
				gui.refresh();
			}
		}

		gvaluelist.compileArray();
		signalChange();
	}

	public void remove(GenericValueGUI gui) {
		// you can't delete the first value my friend !!!
		if (getIndex(gui) == 0)
			return;

		content.remove(gui);
		list.remove(gui);
		gvaluelist.getValues().remove(gui.getGvalue());
		gvaluelist.compileArray();
		signalChange();

		content.invalidate();
		content.revalidate();
		content.repaint();
	}

	public void addNew(GenericValueGUI gui) {

		int index = getIndex(gui);
		GenericValueGUI nextGUI = getNextIndex(index);
		GenericValue elem;
		Object obj = null;

		try {
			// create a new generic value and put a new object value
			elem = new GenericValue();
			//elem.setConverter(converter);
			obj = gui.getValueInd().constructNew();
			elem.setValue(obj);
			// so we add a new one with correct timings
			// if there is one next to it
			if (nextGUI == null)
				elem.setTime(gui.getGvalue().getTime() + 1.0f);
			else {

				float previous = gui.getGvalue().getTime();
				float next = nextGUI.getGvalue().getTime();

				// TODO send an error pop up
				// if( (int)(next*10f) == (int)(previous*10f))
				// return;
				// if(gui.getTimeInd().getSlider().getValue() ==
				// nextGUI.getTimeInd().getSlider().getValue()
				// )
				// return;

				elem.setTime((next + previous) / 2.0f);
			}

			// create a gui for the new genericvalue
			GenericValueGUI vgui;
			vgui = new GenericValueGUI(converter,elem);
//			vgui.setConverter(converter);
			vgui.setArgs(args);
			vgui.setEditActionListener(this);

			content.add(vgui, index + 1);
			list.add(vgui);

			gvaluelist.addValue(elem);
			gvaluelist.compileArray();
			signalChange();

		} catch (Throwable e) {
			e.printStackTrace();
		}

		content.invalidate();
		content.revalidate();
		content.repaint();

	}

	protected int getIndex(JComponent comp) {
		Component[] array = content.getComponents();
		int index = -1;
		for (int i = 0; i < array.length; i++) {
			Component component = array[i];

			if (component == comp) {
				index = i;
				break;
			}
		}
		return index;
	}

	protected GenericValueGUI getPreviousIndex(int index) {
		// int i = getIndex(c);

		if (index <= 0)
			return null;
		else
			return (GenericValueGUI) content.getComponent(index - 1);
	}

	protected GenericValueGUI getNextIndex(int index) {
		if (index >= content.getComponents().length - 1 || index < 0)
			return null;
		else
			return (GenericValueGUI) content.getComponent(index + 1);
	}

	public void actionPerformed(ActionEvent e) {
		Editor.setDirty(true);
		Object source = e.getSource();
		if (source == repeat)
			gvaluelist.setRepeat(repeat.isSelected());
		/*else if (source == validGUI)
			gvaluelist.setActive(validGUI.isSelected());
		else if (source == random)
			gvaluelist.setRandom(random.isSelected());
//*/
		signalChange();

	}

	public ValueListChangedListener getListener() {
		return listener;
	}

	public void setListener(ValueListChangedListener listener) {
		this.listener = listener;
	}
	
    @Override
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
		validGUI.setVisible(valid);
	}

	public void changed(Indicator indicator) {
	
		/*
		gvaluelist.setRandomness(randomness.getValue().intValue());
		signalChange();
		//*/
	}

}
