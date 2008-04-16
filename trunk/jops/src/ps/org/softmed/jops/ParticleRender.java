package org.softmed.jops;

import java.io.File;

public class ParticleRender extends InfoObject {

	protected String textureName;

	protected int textureId;

	protected int sourceFactor;

	protected int destinationFactor;
	
	
	public ParticleRender()
	{
		sourceFactor = 770;
		destinationFactor = 772;
		textureName = "media/textures/particle.bmp";
	}

	public int getSourceFactor() {
		return sourceFactor;
	}

	public void setSourceFactor(int blendMode1) {
		this.sourceFactor = blendMode1;
	}

	public int getDestinationFactor() {
		return destinationFactor;
	}

	public void setDestinationFactor(int blendMode2) {
		this.destinationFactor = blendMode2;
	}

	public int getTextureId() {
		return textureId;
	}

	public void setTextureId(int textureId) {
		this.textureId = textureId;
	}

	public String getTextureName() {
		if (textureName == null)
			return textureName;

		// analyse string and keep ONLY the last name
		int lastIndex = textureName.lastIndexOf(File.separator);
		if (lastIndex > 1)
			this.textureName = textureName.substring(lastIndex + 1);
		//else
		//	this.textureName = textureName;

		return textureName;
	}

	public void setTextureName(String textureName) {

		if (textureName == null)
			this.textureName = textureName;

		// analyse string and keep ONLY the last name

		int lastIndex = textureName.lastIndexOf('\\');
		if (lastIndex > 1)
			this.textureName = textureName.substring(lastIndex + 1);
		else
			this.textureName = textureName;

		System.out.println("Texture Name->" + this.textureName);
	}

	
}
