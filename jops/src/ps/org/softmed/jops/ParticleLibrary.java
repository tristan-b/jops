package org.softmed.jops;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.softmed.jops.cloner.Cloner;
import org.softmed.jops.cloner.DefaultCloner;
import org.softmed.jops.fileloading.DataFormatException;
import org.softmed.jops.fileloading.FileLoaderImplementation;
import org.softmed.jops.fileloading.JIBXLoader;



public class ParticleLibrary {

	FileLoaderImplementation fileImpl  = new JIBXLoader();

	Map<String, ParticleSystem> originals = new HashMap<String, ParticleSystem>();

	Cloner cloner = new DefaultCloner();
	
	public String setOriginalFromFile(File file) throws IOException,DataFormatException {
		String pathname = file.getPath();
		String name = fileImpl.getName(pathname);
		setOriginal(name,fileImpl.load(file));
		return name;
	}
	
	public String setOriginalFromFile(String pathname) throws IOException,DataFormatException {
		File file = new File(pathname);
		String name = fileImpl.getName(pathname);
		setOriginal(name,fileImpl.load(file));
		return name;
	}

	public String setOriginalFromStream(InputStream inputStream) throws IOException,DataFormatException{
		String name = fileImpl.getName(inputStream);
		setOriginal(name,fileImpl.load(inputStream));
		return name;
	}
	
	public String setOriginalFromURL(URL url) throws IOException,DataFormatException{
		String name = fileImpl.getName(url);
		setOriginal(name,fileImpl.load(url));
		return name;
	}
	
	public void setOriginal(String index, ParticleSystem ps) {
		if (ps != null && index != null)
			originals.put(index, ps);
	}
	
	public ParticleSystem getOriginal(String index) {
			return originals.get(index);
	}

	public void removeOriginal(String index) {
		originals.remove(index);
	}

	
	
	public ParticleSystem getStandaloneCopy(String index){
		ParticleSystem original = originals.get(index);
		if(original!=null)
			return cloner.getStandaloneCopy(original);
		else
			return null;
	}
	
	public ParticleSystem getStandaloneCopy(ParticleSystem original){
		if(original!=null)
			return cloner.getStandaloneCopy(original);
		else
			return null;
	}

	/*
	public ParticleSystem getSharedCopy(String index){
		ParticleSystem original = originals.get(index);
		if(original!=null)
			return original.getSharedCopy();
		else
			return null;
	}
	//*/
	
	public void purgeAll() {
		originals.clear();
	}

	protected String getSimpleFileName(String pathname) {
		String simplename = null;
		if (pathname == null)
			simplename = pathname;

		// analyse string and keep ONLY the last name

		int lastIndex = pathname.lastIndexOf('\\');
		if (lastIndex > 1)
			simplename = pathname.substring(lastIndex + 1);
		else
			simplename = pathname;

		return simplename;
	}

	public FileLoaderImplementation getFileImpl() {
		return fileImpl;
	}

	public void setFileImpl(FileLoaderImplementation fileImpl) {
		this.fileImpl = fileImpl;
	}

	public Cloner getCloner() {
		return cloner;
	}

	public void setCloner(Cloner cloner) {
		this.cloner = cloner;
	}



}
