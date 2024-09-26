package fileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 
 * @author General Cleans csc2a projects
 * Cleans the project by deleting files generated by the student' eclipse 
 * replacing them with my settings / data
 */
public class Clean {

	String status = "";
    int countFol = 0;
	File location =null;
	public Clean(File location) {
		this.location=location;
	}
	
	public String Start() {
		recurse(location);
		return countFol +" folders cleaned";
	}

	private void recurse(File location) {
			
		for (File file : location.listFiles()) {
			if (file.isDirectory()) {
				if(file.getName().contains("src"))
				{
					countFol++; //counts the number of projects we've cleaned
					
					//copy our configuration settings
					writeConfigs(file.getParentFile());
				}
				   
				
				if (file.getName().equalsIgnoreCase(".settings"))
					deleletFol(file);
				else if(!file.getName().contains("#"))
					recurse(file);
				
			}
			else if ((file.getName().contains("build.bat"))) {
			
				Scanner scanner;
				String line = "";
				
				try {
					scanner = new Scanner(new File("docs\\build.bat"));
					while (scanner.hasNext()) {
						line += scanner.nextLine() + "\n";
					}
					scanner.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				writeFile(file, line);
			}
			
			

		}

	}

	/**
	 *  Copies our configuration files to the folders that we are setting up
	 * @param configLoc folder Location of the configuration file we want to edit/create
	 */
	private void writeConfigs(File configLoc) {
		 String configurations[]   = {".project",".classpath"};
			
		 for (String configName : configurations) {
			 
			 File fileToWrite = new File(configLoc,configName);
			
				Scanner scanner;
				String line = "";
				
				try {
					scanner = new Scanner(new File(configName));
					while (scanner.hasNext()) {
						line += scanner.nextLine() + "\n";
					}
					scanner.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				writeFile(fileToWrite, line);
				
		}
	}
	private void deleletFol(File fol) {
		for (File file : fol.listFiles()) {
			status += "\n" + file.delete();
		}
		status += "\n" + fol.delete();
	}

	void writeFile(File file, String text) {
		FileOutputStream fos;
		PrintWriter pw;
		try {
			fos = new FileOutputStream(file, false);
			pw = new PrintWriter(fos);
			pw.write(text);

			pw.flush();
			pw.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return status;
	}
}