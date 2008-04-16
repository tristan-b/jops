package org.softmed.jops.fileloading;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.softmed.jops.ParticleSystem;


public interface FileLoaderImplementation {

	void save(File file, ParticleSystem ps) throws IOException,DataFormatException;
	
	ParticleSystem load(File file) throws IOException,DataFormatException;

	ParticleSystem load(InputStream inputStream) throws IOException,DataFormatException  ;
	
	ParticleSystem load(URL url) throws IOException,DataFormatException ;
	
	String getName(InputStream inputStream) throws IOException,DataFormatException;
	
	String getName(URL url) throws IOException,DataFormatException;
	
	String getName(String filePath) throws IOException,DataFormatException;

}
