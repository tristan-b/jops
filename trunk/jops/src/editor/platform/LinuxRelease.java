package platform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LinuxRelease {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(args[0]);
		
		File batchFile = new File("jops-editor.sh");
		try {
			batchFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(batchFile);
			String command = "#!/bin/sh \n\n ";
			command += "#JAVA=\"java -agentlib:yjpagent\" \n";
			command+= "JAVA=java\n\n";
			command+="$JAVA -Djava.library.path=\"native\" -Xms64m -Xmx128m -classpath "+ args[1] + ":" + args[0] +" gui.Main";
			

			
			fos.write(command.getBytes());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
