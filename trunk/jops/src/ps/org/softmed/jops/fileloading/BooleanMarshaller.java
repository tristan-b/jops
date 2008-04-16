package org.softmed.jops.fileloading;

import org.jibx.runtime.IAliasable;
import org.jibx.runtime.IMarshaller;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshaller;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.jibx.runtime.impl.MarshallingContext;
import org.jibx.runtime.impl.UnmarshallingContext;



public class BooleanMarshaller implements IMarshaller, IUnmarshaller, IAliasable {
	private String m_uri;

	private int m_index;

	private String m_name;

	public BooleanMarshaller() {
		m_uri = null;
		m_index = 0;
		m_name = "hashmap";
	}

	public BooleanMarshaller(String uri, int index, String name) {
		m_uri = uri;
		m_index = index;
		m_name = name;
	}

	public boolean isExtension(int arg0) {
		return false;
	}

	public boolean isPresent(IUnmarshallingContext arg0) throws JiBXException {
		return arg0.isAt(m_uri, m_name);
	}

	public void marshal(Object arg0, IMarshallingContext arg1)
			throws JiBXException {

		 if (arg0 instanceof Boolean) {
			MarshallingContext ctx = (MarshallingContext) arg1;

			ctx.startTagAttributes(m_index, m_name).closeStartContent();
			ctx.content(((Boolean)arg0).toString());

			ctx.endTag(m_index, m_name);
		}
	}

	public Object unmarshal(Object arg0, IUnmarshallingContext arg1)
			throws JiBXException {

		// make sure we're at the appropriate start tag
		UnmarshallingContext ctx = (UnmarshallingContext) arg1;
		if (!ctx.isAt(m_uri, m_name)) {
			ctx.throwStartTagNameError(m_uri, m_name);
		}

		ctx.parseToStartTag(m_uri, m_name);

	//	String type = ctx.attributeText(m_uri, TYPE);
		ctx.parsePastStartTag(m_uri, m_name);

		String text = ctx.parseContentText();

		ctx.parsePastEndTag(m_uri, m_name);

		Object obj = null;
		obj = new Boolean(text);

		return obj;
	}

}
