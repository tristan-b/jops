/*
 * ParticleRendererClient.java
 *
 * Created on 28 de Fevereiro de 2006, 1:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package renderer;

import java.util.List;

import org.openmali.FastMath;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Vector2f;
import org.openmali.vecmath2.Vector3f;
import org.softmed.jops.Generator;
import org.softmed.jops.GeneratorBehaviour;
import org.softmed.jops.Particle;
import org.softmed.jops.ParticleManager;
import org.softmed.jops.ParticleSystem;
import org.softmed.jops.modifiers.Modifier;
import org.softmed.jops.modifiers.PointMass;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.glu.Sphere;

/**
 * 
 * @author eu
 */
public class TriangleParticleRenderer extends RendererClient {

	public static boolean showLines = false;

	public static boolean showGenerators = true;

	public static boolean showPointMasses = true;

	public static boolean showPointMasseTreshold = true;

	public static float indicatorSize = 0.05f;

	ParticleManager manager;

	// ParticleFastMath math = new ParticleFastMath();

	float mult = (float) (Math.sqrt(2));

	// TextureSpinUV uv = new TextureSpinUV();

	Vector3f bottomright = new Vector3f();

	Vector3f topleft = new Vector3f();

	Vector3f bottomleft = new Vector3f();

	Vector3f bottomrightT = new Vector3f();

	Vector3f topleftT = new Vector3f();

	Vector3f bottomleftT = new Vector3f();

	protected List<Generator> generators;

	// protected List<GeneratorBehaviour> rules;
	// FastMath math = new FastMath();

	Generator gen;

	GeneratorBehaviour rule;

	Particle part;

	private List<Particle> particles;

	private List<ParticleSystem> systems;

	// private int image;

	private float tsize;

	// private float sin;

	// private float cos;

	private Vector2f textureRotationTemp = new Vector2f(0.5f, 0.5f);

	private Vector2f bl = new Vector2f();

	private Vector2f br = new Vector2f();

	private Vector2f tl = new Vector2f();

	private ParticleSystem ps;

	private float sx;

	private float sy;

	private float sz;

	private float ux;

	private float uy;

	private float uz;

	Sphere sphere = new Sphere();

	private List<Modifier> modifiers;

	private Modifier modifier;

	private PointMass point;

	/** Creates a new instance of ParticleRendererClient */
	public TriangleParticleRenderer(ParticleManager manager) {
		this.manager = manager;
		systems = manager.getSystems();
	}

	@Override
	public void render() {

		setPolygonRenderMethod();

		if (showGenerators)
			for (int i = 0; i < systems.size(); i++) {
				ps = systems.get(i);
				if (!ps.isRenderable())
					continue;

				generators = systems.get(i).getGenerators();

				for (int j = 0; j < generators.size(); j++) {
					gen = generators.get(j);
					GL11.glPushMatrix();
					GL11.glTranslatef(gen.getPosition().getX(), gen
							.getPosition().getY(), -gen.getPosition().getZ());
					GL11.glColor3f(0, 255, 0);
					sphere.draw(indicatorSize, 15, 15);
					GL11.glPopMatrix();
				}

			}

		if (showPointMasses)
			for (int i = 0; i < systems.size(); i++) {
				ps = systems.get(i);
				if (!ps.isRenderable())
					continue;

				modifiers = systems.get(i).getModifiers();

				for (int j = 0; j < modifiers.size(); j++) {
					modifier = modifiers.get(j);
					if (modifier instanceof PointMass) {
						point = (PointMass) modifier;
						GL11.glPushMatrix();
						GL11.glTranslatef(point.getPosition().getX(), point
								.getPosition().getY(), -point.getPosition()
								.getZ());

						GL11.glColor3f(255, 0, 0);
						sphere.draw(indicatorSize, 15, 15);

						if (showPointMasseTreshold && point.isThreshold()) {
							GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK,
									GL11.GL_LINE);
							GL11.glColor3f(255, 0, 0);
							sphere.draw(point.getCurrentTreshold(), 15, 15);
							setPolygonRenderMethod();
						}

						GL11.glPopMatrix();
					}
				}
				// */
			}

		setPolygonRenderMethod();
		// setPolygonRenderMethod();

		if (!showLines) {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_DEPTH_TEST);

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.01f);

			GL11.glDepthMask(false);
		}

		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Really
		// Nice
		// Perspective
		// Calculations
		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);

		sx = status.getCamera().mStraffVector.getX() * 0.5f;
		sy = status.getCamera().mStraffVector.getY() * 0.5f;
		sz = status.getCamera().mStraffVector.getZ() * 0.5f;

		ux = status.getCamera().mUpVector.getX() * 0.5f;
		uy = status.getCamera().mUpVector.getY() * 0.5f;
		uz = status.getCamera().mUpVector.getZ() * 0.5f;

		topleft.setX(ux - sx);
		topleft.setY(uy - sy);
		topleft.setZ(uz - sz);

		bottomleft.setX(-ux - sx);
		bottomleft.setY(-uy - sy);
		bottomleft.setZ(-uz - sz);

		bottomright.setX(-ux + sx);
		bottomright.setY(-uy + sy);
		bottomright.setZ(-uz + sz);

		for (int i = 0; i < systems.size(); i++) {
			ps = systems.get(i);
			if (!ps.isRenderable())
				continue;

			generators = systems.get(i).getGenerators();

			for (int j = 0; j < generators.size(); j++) {
				gen = generators.get(j);
				if (gen.getRender() != null) {
					GL11.glBlendFunc(gen.getRender().getSourceFactor(), gen
							.getRender().getDestinationFactor());
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, gen.getRender()
							.getTextureId());

					render(gen, gen.getParticles());
				}
			}
		}

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		// GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);

	}

	protected void setPolygonRenderMethod() {
		if (showLines)
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		else
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}

	public void render(Generator gen2, List<Particle> particles) {

		GL11.glBegin(GL11.GL_TRIANGLES);
		/*
		 * float sx = 0 ; float sy = 0; float sz = 0;
		 * 
		 * float ux = 0 ; float uy = 0; float uz = 0; //
		 */
		Vector3f side = new Vector3f();
		Vector3f up = new Vector3f();
		Vector3f dir = new Vector3f();

		/*
		 * float cosH; float sinH; float cosV; float sinV;
		 */

		float correctedVAngle;

		float width, height;

		float texWidth, texHeight;

		Vector3f tup = new Vector3f();
		Vector3f tdir = new Vector3f();

		if (gen2.isAbsoluteParticleAngle()) {
			for (int i = 0; i < particles.size(); i++) {
				part = particles.get(i);
				if (part.life < 0.0f && gen2.isKillParticles())
					continue;

				GL11.glColor4ub(part.color.getRedByte(), part.color
						.getGreenByte(), part.color.getBlueByte(),
						(byte) (part.alpha * 255f));

				tsize = part.size;

				correctedVAngle = part.angleV - FastMath.PI_HALF;

				tup.setX(FastMath.cos(part.angleH)
						* FastMath.sin(correctedVAngle));
				tup.setZ(FastMath.sin(part.angleH)
						* FastMath.sin(correctedVAngle));
				tup.setY(FastMath.cos(correctedVAngle));

				tdir
						.setX(FastMath.cos(part.angleH)
								* FastMath.sin(part.angleV));
				tdir
						.setZ(FastMath.sin(part.angleH)
								* FastMath.sin(part.angleV));
				tdir.setY(FastMath.cos(part.angleV));

				side.cross(tdir, tup);

				// calculate quad
				// apply width and height
				width = part.width * 0.5f;
				height = part.height * 0.5f;

				tup.scale(height);
				tdir.set(side);
				tdir.scale(width);

				Point3f mup = new Point3f(tup);
				mup.scale(-0.5f);
				Point3f mdir = new Point3f(tdir);
				mdir.scale(-0.5f);

				mup.add(mdir);

				topleftT.setX(tup.getX() - tdir.getX() - mup.getX());
				topleftT.setY(tup.getY() - tdir.getY() - mup.getY());
				topleftT.setZ(tup.getZ() - tdir.getZ() - mup.getZ());

				bottomleftT.setX(-tup.getX() - tdir.getX() - mup.getX());
				bottomleftT.setY(-tup.getY() - tdir.getY() - mup.getY());
				bottomleftT.setZ(-tup.getZ() - tdir.getZ() - mup.getZ());

				bottomrightT.setX(-tup.getX() + tdir.getX() - mup.getX());
				bottomrightT.setY(-tup.getY() + tdir.getY() - mup.getY());
				bottomrightT.setZ(-tup.getZ() + tdir.getZ() - mup.getZ());

				// apply scale
				bottomrightT.scale(tsize);
				bottomleftT.scale(tsize);
				topleftT.scale(tsize);

				// texture coordinates
				setTexture(part);

				GL11.glTexCoord2f(bl.getX(), bl.getY());
				GL11.glVertex3f(part.position.getX() + bottomleftT.getX(),
						part.position.getY() + bottomleftT.getY(),
						-part.position.getZ() - bottomleftT.getZ());

				GL11.glTexCoord2f(br.getX(), br.getY());
				GL11.glVertex3f(part.position.getX() + bottomrightT.getX(),
						part.position.getY() + bottomrightT.getY(),
						-part.position.getZ() - bottomrightT.getZ());

				GL11.glTexCoord2f(tl.getX(), tl.getY());
				GL11.glVertex3f(part.position.getX() + topleftT.getX(),
						part.position.getY() + topleftT.getY(), -part.position
								.getZ()
								- topleftT.getZ());

			}
		} else {
			for (int i = 0; i < particles.size(); i++) {
				part = particles.get(i);
				if (part.life < 0.0f && gen2.isKillParticles())
					continue;

				GL11.glColor4ub(part.color.getRedByte(), part.color
						.getGreenByte(), part.color.getBlueByte(),
						(byte) (part.alpha * 255f));

				tsize = part.size;

				// apply width and height
				width = part.width * 0.5f;
				height = part.height * 0.5f;

				tup.set(ux, uy, uz);
				tup.scale(height);

				tdir.set(sx, sy, sz);
				tdir.scale(width);

				Point3f mup = new Point3f(tup);
				mup.scale(-0.5f);
				Point3f mdir = new Point3f(tdir);
				mdir.scale(-0.5f);

				mup.add(mdir);

				topleftT.setX(tup.getX() - tdir.getX() - mup.getX());
				topleftT.setY(tup.getY() - tdir.getY() - mup.getY());
				topleftT.setZ(tup.getZ() - tdir.getZ() - mup.getZ());

				bottomleftT.setX(-tup.getX() - tdir.getX() - mup.getX());
				bottomleftT.setY(-tup.getY() - tdir.getY() - mup.getY());
				bottomleftT.setZ(-tup.getZ() - tdir.getZ() - mup.getZ());

				bottomrightT.setX(-tup.getX() + tdir.getX() - mup.getX());
				bottomrightT.setY(-tup.getY() + tdir.getY() - mup.getY());
				bottomrightT.setZ(-tup.getZ() + tdir.getZ() - mup.getZ());

				// apply scale
				bottomrightT.scale(tsize);
				bottomleftT.scale(tsize);
				topleftT.scale(tsize);

				setTexture(part);

				GL11.glTexCoord2f(bl.getX(), bl.getY());
				GL11.glVertex3f(part.position.getX() + bottomleftT.getX(),
						part.position.getY() + bottomleftT.getY(),
						-part.position.getZ() - bottomleftT.getZ());

				GL11.glTexCoord2f(br.getX(), br.getY());
				GL11.glVertex3f(part.position.getX() + bottomrightT.getX(),
						part.position.getY() + bottomrightT.getY(),
						-part.position.getZ() - bottomrightT.getZ());

				GL11.glTexCoord2f(tl.getX(), tl.getY());
				// GL11.glTexCoord2f(0.0f, 1.0f);
				GL11.glVertex3f(part.position.getX() + topleftT.getX(),
						part.position.getY() + topleftT.getY(), -part.position
								.getZ()
								- topleftT.getZ());

			}
		}
		GL11.glEnd();

	}

	public List<Particle> getParticles() {
		return particles;
	}

	private void setTexture(Particle particle) {
		final float texWidth = 1f / particle.texWidth;
		final float texHeight = 1f / particle.texHeight;

		bl.set(-0.5f, -0.5f);
		br.set(+1.5f, -0.5f);
		tl.set(-0.5f, +1.5f);

		rotate2f(bl, particle.angle);
		rotate2f(br, particle.angle);
		rotate2f(tl, particle.angle);

		final float bias = 0.5f;
		bl.set(bl.getX() * texWidth + bias, bl.getY() * texHeight + bias);
		br.set(br.getX() * texWidth + bias, br.getY() * texHeight + bias);
		tl.set(tl.getX() * texWidth + bias, tl.getY() * texHeight + bias);

	}

	protected void rotate2f(Vector2f v, float angle) {
		// System.out.println(v.toString() + " angle->" + angle);
		textureRotationTemp.setX(v.getX() * FastMath.cos(angle) - v.getY()
				* FastMath.sin(angle));
		textureRotationTemp.setY(v.getX() * FastMath.sin(angle) + v.getY()
				* FastMath.cos(angle));
		v.set(textureRotationTemp);
		// System.out.println(v.toString());
	}
}
