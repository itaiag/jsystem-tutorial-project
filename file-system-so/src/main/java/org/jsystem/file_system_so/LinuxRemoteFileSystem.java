package org.jsystem.file_system_so;

import com.aqua.sysobj.conn.CliCommand;

/**
 * This system object provides different file system operations over a Possix
 * operating system.
 * 
 * @author Itai Agmon
 *
 */
public class LinuxRemoteFileSystem extends AbstractRemoteFileSystem {

	@Override
	public void copyFile(String sourceFolder, String sourceFile, String destinationFolder, String destinationFile)
			throws Exception {
		CliCommand command = new CliCommand("");
		String[] commandStrings = new String[2];
		commandStrings[0] = "cd ~";
		commandStrings[1] = "cp " + sourceFolder + "/" + sourceFile + " " + destinationFolder + "/" + destinationFile;
		command.setCommands(commandStrings);
		command.addErrors("No such file or directory");
		cliConnection.handleCliCommand("Copying file", command);
	}

	@Override
	public void createNewFile(String folder, String file) throws Exception {
		CliCommand command = new CliCommand("");
		String[] commandStrings = new String[3];
		commandStrings[0] = "cd ~";
		commandStrings[1] = "cd " + folder;
		commandStrings[2] = "touch " + file;
		command.setCommands(commandStrings);
		cliConnection.handleCliCommand("Creating new file", command);
	}

	@Override
	public void writeContentToFile(String folder, String file, String content, boolean append) throws Exception {
		String redirctType = append ? ">>" : ">";
		CliCommand command = new CliCommand("");
		String[] commandStrings = new String[3];
		commandStrings[0] = "cd ~";
		commandStrings[1] = "cd " + folder;
		commandStrings[2] = "echo " + content + redirctType + file;
		command.setCommands(commandStrings);
		cliConnection.handleCliCommand("Writing content to file", command);
	}

	@Override
	public void deleteFile(String folder, String file) throws Exception {
		CliCommand command = new CliCommand("");
		String[] commandStrings = new String[3];
		commandStrings[0] = "cd ~";
		commandStrings[1] = "cd " + folder;
		commandStrings[2] = "rm " + file;
		command.setCommands(commandStrings);
		command.addErrors("No such file or directory");
		cliConnection.handleCliCommand("Deleting file", command);
	}

}
