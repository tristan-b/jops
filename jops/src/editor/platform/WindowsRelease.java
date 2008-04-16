package platform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WindowsRelease {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(args[0]);
		
		File batchFile = new File("jops-editor.bat");
		try {
			batchFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(batchFile);
			String command = "java -jar "+args[0];
			fos.write(command.getBytes());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
