package view;

import org.openmali.FastMath;
import org.openmali.vecmath2.Vector3f;
import org.softmed.jops.math.AnglesFastMath;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

// import org.openmali.vecmath2.*; // java 3d API
public class Camera {

	Display display;

	AnglesFastMath math = new AnglesFastMath();

	private CameraListener cameraListener;

	public float speed = 2f;

	public Vector3f mPosition = new Vector3f(); // Vector3f posi��o

	public Vector3f mLookAt = new Vector3f();; // Vector3f olhar para

	public Vector3f mUpVector = new Vector3f();; // Vector3f "para cima �

	// nesta direc��o"

	public Vector3f mSpeed = new Vector3f();; // Vector3f velocidade

	public Vector3f mStraffVector = new Vector3f();; // Vector3f direc��o de

	// straff

	public float mFieldOfViewAngle; // Field Of View

	public float mClosePlane; // plano limite mais pr�ximo de rendering

	public float mFarPlane; // plano limite mais afastado de rendering

	public float mVerticalRotationAngle; // angulo de rota��o vertical

	public float mLateralRotationAngle; // angulo de rota��o lateral

	// boolean bob; //bob ligado ou desligado (movimento ao andar)
	// boolean tagging; //tagging ligado ou n�o (seguir um alvo)

	public Camera() {
		reset();

		// vector velocidade
		mSpeed.set(30.6f, 30.6f, 30.6f);

		// caracter�sticas do sistema de vis�o
		mFieldOfViewAngle = 45;
		mClosePlane = 0.001f;
		mFarPlane = 512.0f;
		// bob=true;

		update();

		// setCameraPosition(3f,3f,3f);
		// setLookingAt(0f,0f,0f);

	}

	public void reset() {
		mVerticalRotationAngle = 120.0f;
		mLateralRotationAngle = 225.0f;

		// ponto posi��o
		mPosition.set(1.4f, 1.4f, 1.4f);
	}

	public void calcUpVector() {
		float correctedVerticalAngle = mVerticalRotationAngle - 90f;
		mUpVector.setX(math.cos(mLateralRotationAngle)
				* math.sin(correctedVerticalAngle));
		mUpVector.setZ(math.sin(mLateralRotationAngle)
				* (float) math.sin(correctedVerticalAngle));
		mUpVector.setY(math.cos(correctedVerticalAngle));

		// mUpVector.normalise();

		/*
		 * mUpVector.x = (float) math.cos(mLateralRotationAngle) (float)
		 * math.sin(mVerticalRotationAngle); mUpVector.z = (float)
		 * math.sin(mLateralRotationAngle) (float)
		 * math.sin(mVerticalRotationAngle); mUpVector.y = (float)
		 * math.sin(mVerticalRotationAngle); // simplify(mUpVector); //
		 * mUpVector.normalise(); //
		 */
	};

	// calcula o vector mLookAt a partir de mVerticalRotationAngle e
	// mLateralRotationAngle
	public void calcLookAt() {
		// float correctedVerticalAngle = 90f - mVerticalRotationAngle;
		mLookAt.setX(math.cos(mLateralRotationAngle)
				* math.sin(mVerticalRotationAngle));
		mLookAt.setZ(math.sin(mLateralRotationAngle)
				* math.sin(mVerticalRotationAngle));
		mLookAt.setY(math.cos(mVerticalRotationAngle));

		// mLookAt.normalise();
		/*
		 * mLookAt.x = (float) math.cos(mLateralRotationAngle) (float)
		 * math.cos(mVerticalRotationAngle); mLookAt.y = (float)
		 * math.sin(mVerticalRotationAngle); mLookAt.z = (float)
		 * math.sin(mLateralRotationAngle) (float)
		 * math.cos(mVerticalRotationAngle); // simplify(mLookAt); //
		 * mLookAt.normalise(); //
		 */
	};

	// calcula o vector straff a partir de rotcVer e mLateralRotationAngle
	public void calcStraffVector() {

		// mStraffVector.cross(mLookAt, mUpVector, mStraffVector);

		mStraffVector.cross(mLookAt, mUpVector);

		if (mStraffVector.getX() == 0.0f && mStraffVector.getY() == 0.0f
				&& mStraffVector.getZ() == 0.0f) {
			write("Error ->Zero Straff Vector");
		}

	};

	// porque s�o usados pela rotina de teclado
	// altera a posi��o da c�mara - para Frente

	public void moveForward(float mAmount) {

		mPosition.addX(mLookAt.getX() * mAmount * speed);
		mPosition.addZ(mLookAt.getZ() * mAmount * speed);

		// the camera does not move vertically
		// mPosition.y += mLookAt.y * mAmount * speed;
	};

	// altera a posi��o da c�mara - para Tr�s
	public void moveBackward(float mAmount) {

		mPosition.subX(mLookAt.getX() * mAmount * speed);
		mPosition.subZ(mLookAt.getZ() * mAmount * speed);

		// the camera does not move vertically
		// mPosition.y -= mLookAt.y * mAmount * speed;

	};

	// altera a posi��o da c�mara - straff para a esquerda
	public void leftStraff(float mAmount) {

		mPosition.addX(mStraffVector.getX() * mAmount * speed);
		mPosition.addY(mStraffVector.getY() * mAmount * speed);
		mPosition.addZ(mStraffVector.getZ() * mAmount * speed);

	};

	// altera a posi��o da c�mara - straff para a direita
	public void rightStraff(float mAmount) {

		mPosition.subX(mStraffVector.getX() * mAmount * speed);
		mPosition.subY(mStraffVector.getY() * mAmount * speed);
		mPosition.subZ(mStraffVector.getZ() * mAmount * speed);

	};

	public void moveUp(float mAmount) {
		mPosition.addY(mAmount * speed);
	};

	public void moveDown(float mAmount) {
		mPosition.subY(mAmount * speed);
	};

	// faz update aos vectores da c�mara, apenas quando se altera os angulos -
	// input do rato
	public void update() {
		calcUpVector();
		calcLookAt();
		calcStraffVector();
		if (cameraListener != null)
			cameraListener.cameraChanged(this);
		// write(getStringInfo());
	};

	public String getStringInfo() {

		return new String("POS:" + mPosition.toString() + " |LA"
				+ mLookAt.toString() + " |UP:" + mUpVector.toString()
				+ " |SFF:" + mStraffVector.toString() + " |VA : "
				+ mVerticalRotationAngle + " |LA" + mLateralRotationAngle + "");

	}

	/**
	 * changeLateralAngle
	 * 
	 * 
	 * to look left or right
	 * 
	 * @param aTimeLapse
	 *            float
	 */
	public void changeLateralAngle(float aAngle) {

		mLateralRotationAngle += aAngle;
		// limites de rota��o horizontal
		if (mLateralRotationAngle >= 360) {
			mLateralRotationAngle -= 360;
		}
		;
		if (mLateralRotationAngle <= -360) {
			mLateralRotationAngle += 360;
		}
		;

		// update();

	}

	/**
	 * changeVerticalAngle
	 * 
	 * 
	 * to look up and down
	 * 
	 * @param aTimeLapse
	 *            float
	 */
	public void changeVerticalAngle(float aAngle) {
		mVerticalRotationAngle += aAngle;
		// limites de rota��o vertical
		if (mVerticalRotationAngle >= 180) {
			mVerticalRotationAngle = 180;
		}
		;
		if (mVerticalRotationAngle <= 0) {
			mVerticalRotationAngle = 0;
		}
		;
		// update();
	}

	public void simplify(Vector3f v) {
		if (v.getX() < 0.000001f && v.getX() > -0.000001f)
			v.setX(0f);

		if (v.getY() < 0.000001f && v.getY() > -0.000001f)
			v.setY(0f);

		if (v.getZ() < 0.000001f && v.getY() > -0.000001f)
			v.setZ(0f);

	}

	public CameraListener getCameraListener() {
		return cameraListener;
	}

	public void setCameraListener(CameraListener cameraListener) {
		this.cameraListener = cameraListener;
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	protected void write(String msg) {
		System.out.println(msg);
	}

	public void setCameraPosition(float x, float y, float z) {
		mPosition.set(x, y, z);
	}

	public void setLookingAt(float x, float y, float z) {
		// doesn't work
		float v = math.atan(FastMath.sqrt(x * x + z * z) / y);
		float h = math.atan(z / x);
		System.out.println("V->" + v + " |H->" + h);
		math.convertSphericalToCartesian(h, v, mLookAt);
		mLookAt.set(x, y, z);
	}

}
