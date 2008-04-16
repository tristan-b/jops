package org.softmed.jops.fileloading;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.softmed.jops.ParticleSystem;


public class Test {

	
IBindingFactory bfact;

	
	
	
	
	public Test() {
		try {
			bfact = BindingDirectory.getFactory(ParticleSystem.class);
		} catch (JiBXException e) {
			e.printStackTrace();
		}
	}

	public Object load(File file,Class classe) throws Throwable {
		try {
			bfact = BindingDirectory.getFactory(classe);
		} catch (JiBXException e) {
			e.printStackTrace();
		}
		IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
		Object obj = uctx.unmarshalDocument(
				new FileInputStream(file), null);
		return obj;
	}

	public void save(File file, Object ps) throws Throwable {
		try {
			bfact = BindingDirectory.getFactory(ps.getClass());
		} catch (JiBXException e) {
			e.printStackTrace();
		}
		

		IMarshallingContext mctx = bfact.createMarshallingContext();
		mctx.setIndent(4);
		mctx.marshalDocument(ps, "UTF-8", null, new FileOutputStream(file));

		
	}
	
}
