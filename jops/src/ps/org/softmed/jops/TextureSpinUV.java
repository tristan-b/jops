package org.softmed.jops;




import org.openmali.FastMath;
import org.openmali.vecmath2.Vector2f;

//import particle.math.ParticleFastMath;

public class TextureSpinUV {

//	ParticleFastMath math = new ParticleFastMath();

	int resolution = 361;

	int total = resolution * 2 + 1;

	Vector2f[] bottomLeft = new Vector2f[total];

	Vector2f[] bottomRight = new Vector2f[total];

	Vector2f[] topLeft = new Vector2f[total];

	Vector2f[] topRight = new Vector2f[total];

	float sq2 = (float) Math.sqrt(2.0);

	public TextureSpinUV() {
		float cos;
		float sin;

		Vector2f bl;
		Vector2f br;
		Vector2f tl;
		Vector2f tr;

		float mult = 0.5f * sq2;

		for (int i = 0; i < resolution; i++) {
			cos = FastMath.cos(i);
			sin = FastMath.sin(i);

			bl = new Vector2f(1f - cos, 1f - sin);
			br = new Vector2f(1f + cos, 1f - sin);
			tr = new Vector2f(1f + cos, 1f + sin);
			tl = new Vector2f(1f - cos, 1f + sin);

			bl.scale(mult);
			br.scale(mult);
			tr.scale(mult);
			tl.scale(mult);
		}

	}

	public Vector2f getBottomLeft(float spin) {
		return bottomLeft[(int) spin];
	}

	public Vector2f getBottomRight(float spin) {
		return bottomRight[(int) spin];
	}

	public Vector2f getTopRight(float spin) {
		return topRight[(int) spin];
	}

	public Vector2f getTopLeft(float spin) {
		return topLeft[(int) spin];
	}

}
