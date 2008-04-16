package org.softmed.jops.fileloading;



public interface CustomConverter  {


	public abstract Object fromString(String str);

	public abstract String toString(Object obj);



}
