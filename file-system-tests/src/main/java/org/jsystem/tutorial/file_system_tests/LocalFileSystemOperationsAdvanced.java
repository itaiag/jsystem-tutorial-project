package org.jsystem.tutorial.file_system_tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jsystem.framework.ParameterProperties;
import jsystem.framework.TestProperties;
import jsystem.framework.scenario.Parameter;
import jsystem.framework.scenario.UseProvider;
import jsystem.framework.scenario.ValidationError;
import junit.framework.SystemTestCase4;

import org.jsystem.file_system_so.FileAttributes;
import org.jsystem.file_system_so.LocalFileSystem;
import org.junit.Before;
import org.junit.Test;

/**
 * Provides building blocks for performing file system operations on local
 * machine
 * 
 * @author Itai Agmon
 *
 */
public class LocalFileSystemOperationsAdvanced extends SystemTestCase4 {


	private StandardCopyOption copyOption;

	private org.jsystem.file_system_so.LocalFileSystem.FileOrFolder fileOrFolder;

	private String tempFile, prefix, suffix, content, fileFromRepository;

	private File file, sourceFile, destinationFile;

	private long expectedFileSize;

	private boolean append, emptyBeforeDeleting;

	private FileAttributes[] fileAttributesArr;

	private LocalFileSystem fileSystem;

	@Before
	public void setUp() throws Exception {
		fileSystem = (LocalFileSystem) system.getSystemObject("fileSystem");
	}

	/**
	 * Create temporary file with the specified prefix and suffix. Return the
	 * full path of the newly created file.
	 * 
	 */
	@Test
	@TestProperties(name = "Local - Create temp file with prefix '${prefix}' and suffix '${suffix}'", paramsInclude = {
			"prefix", "suffix" }, returnParam = { "tempFile" })
	public void createTempFile() throws IOException {
		tempFile = fileSystem.createTempFile(prefix, suffix);
	}

	/**
	 * Create multiple files according to user preferences.
	 */
	@Test
	@TestProperties(name = "Local - Create multiple files", paramsInclude = { "fileAttributesArr" })
	public void createMultipleFiles() throws Exception {
		fileSystem.createMultipleFiles(fileAttributesArr);
	}

	/**
	 * Write specified content to file.
	 * 
	 */
	@Test
	@TestProperties(name = "Local - Write content to file '${file}'", paramsInclude = { "file", "content", "append" })
	public void writeToFile() throws IOException {
		fileSystem.writeToFile(file, content, append);
	}

	/**
	 * Copy the specified source file to the specified destination. Notice that
	 * you can specify additional options.
	 */
	@Test
	@TestProperties(name = "Local - Copy file '${sourceFile}' to '${destinationFile}'", paramsInclude = { "sourceFile",
			"destinationFile", "copyOption" })
	public void copyFile() throws IOException {
		fileSystem.copyFile(sourceFile, destinationFile, copyOption);
	}

	/**
	 * Asserts that size of the specified file is equals to the expected
	 * specified file size.
	 */
	@Test
	@TestProperties(name = "Local - Assert that file '${file}' size is '${expectedFileSize}'", paramsInclude = {
			"file", "expectedFileSize" })
	public void assertFileSize() throws IOException {
		fileSystem.assertFileSize(file, expectedFileSize);
	}

	@Test
	@TestProperties(name = "Local - Copy file '${fileFromRepository}' to '${destinationFile}'", paramsInclude = {
			"fileFromRepository", "destinationFile", "copyOption" })
	public void copyFileFromRepository() throws IOException {
		fileSystem.copyFileFromRepository(fileFromRepository, destinationFile, copyOption);
	}

	/**
	 * Delete file with specified name
	 * 
	 */
	@Test
	@TestProperties(name = "Local - Delete file or directory '${file}'", paramsInclude = { "file", "fileOrFolder",
			"emptyBeforeDeleting" })
	public void deleteFileOrDirectory() throws IOException {
		fileSystem.deleteFileOrDirectory(file, fileOrFolder);
	}

	@Override
	public ValidationError[] validate(HashMap<String, Parameter> map, String methodName) throws Exception {
		List<ValidationError> veList = new ArrayList<ValidationError>();
		if (methodName.equals("copyFile")) {
			if (map.get("SourceFile").getValue() == null || map.get("SourceFile").getValue().toString().isEmpty()) {
				ValidationError ve = new ValidationError();
				ve.setTitle("Source file is not specified");
				veList.add(ve);
			}
			if (map.get("DestinationFile").getValue() == null
					|| map.get("DestinationFile").getValue().toString().isEmpty()) {
				ValidationError ve = new ValidationError();
				ve.setTitle("Destination file is not specified");
				veList.add(ve);
			}
		}
		return veList.toArray(new ValidationError[] {});
	}

	@Override
	public void handleUIEvent(HashMap<String, Parameter> map, String methodName) throws Exception {
		if (methodName.equals("deleteFileOrDirectory")) {
			if (map.get("FileOrFolder").getValue() != null
					&& map.get("FileOrFolder").getValue().toString().equals("FOLDER")) {
				map.get("EmptyBeforeDeleting").setVisible(true);
			} else {
				map.get("EmptyBeforeDeleting").setVisible(false);
			}
		}
	}

	/**** Setters and Getters method ****/

	public String getTempFile() {
		return tempFile;
	}

	public void setTempFile(String tempFile) {
		this.tempFile = tempFile;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}

	public org.jsystem.file_system_so.LocalFileSystem.FileOrFolder getFileOrFolder() {
		return fileOrFolder;
	}

	public void setFileOrFolder(org.jsystem.file_system_so.LocalFileSystem.FileOrFolder fileOrFolder) {
		this.fileOrFolder = fileOrFolder;
	}

	public File getSourceFile() {
		return sourceFile;
	}

	@ParameterProperties(description = "Source file")
	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}

	public File getDestinationFile() {
		return destinationFile;
	}

	public void setDestinationFile(File destinationFile) {
		this.destinationFile = destinationFile;
	}

	public StandardCopyOption getCopyOption() {
		return copyOption;
	}

	@ParameterProperties(section = "Additional Options")
	public void setCopyOption(StandardCopyOption copyOption) {
		this.copyOption = copyOption;
	}

	public long getExpectedFileSize() {
		return expectedFileSize;
	}

	public void setExpectedFileSize(long expectedFileSize) {
		this.expectedFileSize = expectedFileSize;
	}

	public String getFileFromRepository() {
		return fileFromRepository;
	}

	public void setFileFromRepository(String fileFromRepository) {
		this.fileFromRepository = fileFromRepository;
	}

	public FileAttributes[] getFileAttributesArr() {
		return fileAttributesArr;
	}

	@UseProvider(provider = jsystem.extensions.paramproviders.ObjectArrayParameterProvider.class)
	public void setFileAttributesArr(FileAttributes[] fileAttributesArr) {
		this.fileAttributesArr = fileAttributesArr;
	}

	public boolean isEmptyBeforeDeleting() {
		return emptyBeforeDeleting;
	}

	public void setEmptyBeforeDeleting(boolean emptyBeforeDeleting) {
		this.emptyBeforeDeleting = emptyBeforeDeleting;
	}

	/*************/

}
