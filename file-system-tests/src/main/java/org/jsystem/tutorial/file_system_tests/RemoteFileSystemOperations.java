package org.jsystem.tutorial.file_system_tests;

import jsystem.framework.TestProperties;
import junit.framework.SystemTestCase4;

import org.jsystem.file_system_so.Machine;
import org.junit.Before;
import org.junit.Test;

public class RemoteFileSystemOperations extends SystemTestCase4 {

	private Machine machine;

	private String sourceFolder, destinationFolder, sourceFile, destinationFile, folder, file, content;

	private boolean append;
	
	@Before
	public void setUp() throws Exception {
		machine = (Machine) system.getSystemObject("machine");
	}

	@Test
	@TestProperties(name = "Remote - Copy file '${sourceFolder}/${sourceFile}' to '${destinationFolder}/${destinationFile}'", paramsInclude = {
			"sourceFolder", "destinationFolder", "sourceFile", "destinationFile" })
	public void copyFile() throws Exception {
		machine.fileSystem.copyFile(sourceFolder, sourceFile, destinationFolder, destinationFile);
	}

	@Test
	@TestProperties(name = "Remote - Create new file in folder '${folder}' with name '${file}'", paramsInclude = {
			"folder", "file" })
	public void createNewFile() throws Exception {
		machine.fileSystem.createNewFile(folder, file);
	}

	@Test
	@TestProperties(name = "Remote - Write content to file '${folder}/${file}'", paramsInclude = { "folder", "file",
			"content", "append" })
	public void writeContentToFile() throws Exception {
		machine.fileSystem.writeContentToFile(folder, file, content, append);
	}

	@Test
	@TestProperties(name = "Remote - Delete file '${folder}/${file}'", paramsInclude = { "folder", "file" })
	public void deleteFile() throws Exception {
		machine.fileSystem.deleteFile(folder, file);
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public String getDestinationFile() {
		return destinationFile;
	}

	public void setDestinationFile(String destinationFile) {
		this.destinationFile = destinationFile;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public String getDestinationFolder() {
		return destinationFolder;
	}

	public void setDestinationFolder(String destinationFolder) {
		this.destinationFolder = destinationFolder;
	}

}
