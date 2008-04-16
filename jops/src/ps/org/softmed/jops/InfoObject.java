package org.softmed.jops;

public class InfoObject implements Cloneable {

	@Override
	public Object clone() {
		try {
			InfoObject obj = (InfoObject) super.clone();
			obj.setName(name);
			obj.setDescription(description);
			return obj;
		} catch (CloneNotSupportedException cnse) {
			// won't happen
			return null;
		}
	}

	protected String name;
	protected String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
