package input;

import org.openmali.vecmath2.Vector2f;

public class MouseSmoother {

	int maxIndex = 5;
	int index = 0;
	Vector2f[] mouseSmooth = new Vector2f[maxIndex];

	public void setSize(int size) {
		maxIndex = size;
		reset();
	}

	public void reset() {
		mouseSmooth = new Vector2f[maxIndex];
		index = 0;
	}

	public void addMovement(Vector2f v) {
		if (index == maxIndex)
			index = 0;
		mouseSmooth[index] = v;
		index++;
	}

	public void addMovement(float x, float y) {
		if (index == maxIndex)
			index = 0;
		mouseSmooth[index] = new Vector2f(x, y);
		index++;
	}

	public Vector2f getAverageMovement() {
		Vector2f temp = new Vector2f();
		Vector2f v = null;
		for (int i = 0; i < index; i++) {
			v = mouseSmooth[i];
			temp.add(v);
		}
		temp.div(maxIndex);
		return temp;
	}

	public Vector2f getTotalMovement() {
		Vector2f temp = new Vector2f();
		Vector2f v = null;
		for (int i = 0; i < index; i++) {
			v = mouseSmooth[i];
			temp.add(v);
		}
		return temp;
	}

}
