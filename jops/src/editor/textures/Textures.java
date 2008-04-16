package textures;

import org.lwjgl.devil.IL;
import org.lwjgl.devil.ILU;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ByteOrder;
import java.io.File;
import org.lwjgl.*;
import org.lwjgl.opengl.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.openmali.vecmath2.Vector2f;
import org.openmali.vecmath2.Vector3f;

/**
 * Handles textures
 * 
 * @author Anders
 */
public class Textures {

	static AWTGLCanvas canvas;

	static String defaultDirectory;

	public static void setup(AWTGLCanvas c) {
		canvas = c;
	}

	private static Map<String, Integer> texhash = new HashMap<String, Integer>();

	private static Map<Integer, String> texhash2 = new HashMap<Integer, String>();

	/**
	 * Handles a texture request. If the texture is already loaded the OpenGL
	 * handle will be given directly else the texture will be loaded.
	 * 
	 * @param filename
	 *            the filename (with path)
	 * @return returns the integer handle
	 * @throws TextureNotFoundException
	 *             this ist thrown if the file could not be loaded
	 */

	public static void releaseTexture(int id) {
		String name = texhash2.get(id);
		texhash.remove(name);
		texhash2.remove(id);

		try {
			// canvas.makeCurrent();
			GLContext.useContext(canvas);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// int[] dasss = new int[1];
		// dasss[0] = id;
		IntBuffer buf = BufferUtils.createIntBuffer(1);
		buf.put(id);
		buf.rewind();

		// IntBuffer ib = IntBuffer.wrap(dasss);
		GL11.glDeleteTextures(buf);

		// IL.ilDeleteImages(image);

		try {
			// canvas.releaseContext();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int requestTexture(String filename, boolean repeat)
			throws TextureNotFoundException {
		String filenameOnly = null;

		filenameOnly = getFileNameOnly(filename);

		System.out.println("Request Texture ->" + filenameOnly);
		try {
			IL.create();
			ILU.create();
			// if(canvas.is)
			canvas.makeCurrent();

			GLContext.useContext(canvas);

		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 0;
		try {

			if (!Textures.texhash.containsKey(filename)) {
				File file = new File(filename);
				if (!file.exists()) {
					int index = filename.lastIndexOf('\\');
					if (index >= 0)
						file = new File(defaultDirectory + "/"
								+ filename.substring(index));
					else
						file = new File(defaultDirectory + "/" + filename);
					filename = file.getCanonicalPath();
					filenameOnly = getFileNameOnly(filename);

				}

				i = makeTextureFromFile(filename, repeat);
				Textures.texhash.put(filenameOnly, i);
				texhash2.put(i, filenameOnly);
				System.out.println("Request Texture -> Doesnt Exist -> " + i);
			} else {
				i = (Integer) Textures.texhash.get(filenameOnly);
				System.out.println("Request Texture -> Exists -> " + i);
			}

			// canvas.releaseContext();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	protected static String getFileNameOnly(String filename) {
		String filenameOnly = null;
		int lastIndex = filename.lastIndexOf(File.separator);
		if (lastIndex > 1)
			filenameOnly = filename.substring(lastIndex + 1);
		else
			filenameOnly = new String(filename);
		return filenameOnly;
	}

	/**
	 * Creates a texture from a image file
	 * 
	 * @param filename
	 *            the filename (with path)
	 * @throws TextureNotFoundException
	 *             thrown if the file could not be loaded
	 * @return returns the integer handle
	 */
	public static int makeTextureFromFile(String filename, boolean repeat)
			throws TextureNotFoundException {
		IntBuffer image = ByteBuffer.allocateDirect(4).order(
				ByteOrder.nativeOrder()).asIntBuffer();
		IL.ilGenImages(image);
		IL.ilBindImage(image.get(0));
		int il_handle = image.get(0);
		System.out.println("Loading " + filename);
		IL.ilLoadImage(filename);
		IL.ilConvertImage(IL.IL_RGBA, IL.IL_BYTE);
		ILU.iluFlipImage(); // turns it up side down to fit OpenGL coord sys.
		ByteBuffer scratch = IL.ilGetData();
		if (IL.ilGetError() != IL.IL_NO_ERROR) {
			System.out.println("Error " + ILU.iluErrorString(IL.ilGetError())
					+ ", file " + filename);
			throw new TextureNotFoundException(filename);
		}
		// Create A IntBuffer? For Image Address In Memory
		IntBuffer buf = ByteBuffer.allocateDirect(4).order(
				ByteOrder.nativeOrder()).asIntBuffer();

		// GL11.glEnable (GL11.GL_BLEND);
		// GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		// GL11.glEnable (GL11.GL_ALPHA_TEST);
		// GL11.glAlphaFunc (GL11.GL_NOTEQUAL, 0.0f);
		// GL11.glAlphaFunc(GL11.GL_GREATER, 0.5f);
		// */

		GL11.glGenTextures(buf); // Create Texture In OpenGL
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, buf.get(0));
		// Typical Texture Generation Using Data From The Image
		// Linear Filtering
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
				GL11.GL_LINEAR);
		// Linear Filtering
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
				GL11.GL_LINEAR);

		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
				(repeat ? GL11.GL_REPEAT : GL11.GL_CLAMP));
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
				(repeat ? GL11.GL_REPEAT : GL11.GL_CLAMP));
		// Generate The Texture
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, IL
				.ilGetInteger(IL.IL_IMAGE_WIDTH), IL
				.ilGetInteger(IL.IL_IMAGE_HEIGHT), 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, scratch);

		image.put(0, il_handle);
		image.rewind();
		IL.ilDeleteImages(image);

		int id = buf.get(0);

		buf = null;

		scratch.clear();
		scratch = null;

		return id; // Return Image Address In Memory
	}

	/**
	 * Creates a vertex with associated normal and texture coordiantes
	 * 
	 * @param vertex
	 *            the point coordinates
	 * @param normal
	 *            the normal associated with the vertex
	 * @param texture
	 *            the associated texture coordinates
	 */
	public static void texVertex(Vector3f vertex, Vector3f normal,
			Vector2f texture) {
		GL11.glNormal3f(normal.getX(), normal.getY(), normal.getZ());
		GL11.glTexCoord2f(texture.getX(), texture.getY());
		GL11.glVertex3f(vertex.getX(), vertex.getY(), vertex.getZ());
	}

	public static String getDefaultDirectory() {
		return defaultDirectory;
	}

	public static void setDefaultDirectory(String defaultDirectory) {
		Textures.defaultDirectory = defaultDirectory;
		File file = new File(defaultDirectory);
		boolean exists = file.exists();
		boolean dir = file.isDirectory();

		if (!dir || !exists)
			throw new RuntimeException(
					"DefaultDir does not Exist or is not a dir !!! -> "
							+ defaultDirectory);

	}

}
