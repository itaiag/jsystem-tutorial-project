package org.jsystem.tutorial.file_system_tests;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.DosFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jsystem.framework.ParameterProperties;
import jsystem.framework.TestProperties;
import jsystem.framework.scenario.Parameter;
import jsystem.framework.scenario.UseProvider;
import jsystem.framework.scenario.ValidationError;
import junit.framework.Assert;
import junit.framework.SystemTestCase4;

import org.junit.Test;

public class LocalFileSystemOperations extends SystemTestCase4 {

	public enum FileOrFolder {
		FILE, FOLDER
	}

	private static final String REPOSITORY_FOLDER = "c:\\";

	private StandardCopyOption copyOption;

	private FileOrFolder fileOrFolder;

	private String tempFile, prefix, suffix, content, fileFromRepository;

	private File file, sourceFile, destinationFile;

	private long expectedFileSize;

	private boolean append, emptyBeforeDeleting;

	private FileAttributes[] fileAttributesArr;

	/**
	 * Create temporary file with the specified prefix and suffix. Return the
	 * full path of the newly created file.
	 * 
	 * @throws IOException
	 */
	@Test
	@TestProperties(name = "Local - Create temp file with prefix '${prefix}' and suffix '${suffix}'", paramsInclude = {
			"prefix", "suffix" }, returnParam = { "tempFile" })
	public void createTempFile() throws IOException {
		report.step("About to create temporary file");
		tempFile = File.createTempFile(prefix, suffix).getAbsolutePath();
		report.report("Created file with name: " + tempFile);
	}

	@Test
	@TestProperties(name = "Local - Create multiple files", paramsInclude = { "fileAttributesArr" })
	public void createMultipleFiles() throws IOException {
		if (null == fileAttributesArr || fileAttributesArr.length == 0) {
			return;
		}
		for (FileAttributes attr : fileAttributesArr) {
			Path filePath = Paths.get(attr.getFolder() + "/" + attr.getName());
			report.report("About to create file " + filePath);
			Files.createFile(filePath);
			if (attr.isReadOnly()) {
				new File(filePath.toString()).setReadOnly();
			}
		}
	}

	/**
	 * Write specified content to file.
	 * 
	 * @throws IOException
	 */
	@Test
	@TestProperties(name = "Local - Write content to file '${file}'", paramsInclude = { "file", "content", "append" })
	public void writeToFile() throws IOException {
		report.step("About to write content to file");
		report.report("File content", content, true);
		final Path filePath = Paths.get(file.getAbsolutePath());
		Files.write(filePath, content.getBytes(), append ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);

	}

	/**
	 * Copy the specified source file to the specified destination. Notice that
	 * you can specify additional options.
	 */
	@Test
	@TestProperties(name = "Local - Copy file '${sourceFile}' to '${destinationFile}'", paramsInclude = { "sourceFile",
			"destinationFile", "copyOption" })
	public void copyFile() throws IOException {
		Files.copy(Paths.get(sourceFile.getAbsolutePath()), Paths.get(destinationFile.getAbsolutePath()), copyOption);
	}

	@Test
	@TestProperties(name = "Local - Assert that file '${file}' size is '${size}'", paramsInclude = { "file", "expectedFileSize" })
	public void assertFileSize() throws IOException {
		Path filePath = Paths.get(file.getAbsolutePath());
		DosFileAttributes attrs = Files.readAttributes(filePath, DosFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
		Assert.assertEquals(expectedFileSize, attrs.size());
	}

	@Test
	@TestProperties(name = "Local - Copy file '${fileFromRepository}' to '${destinationFile}'", paramsInclude = {
			"fileFromRepository", "destinationFile", "copyOption" })
	public void copyFileFromRepository() throws IOException {
		Path sourcePath = Paths.get(REPOSITORY_FOLDER + "/" + fileFromRepository);
		Path destinationPath = Paths.get(destinationFile.getAbsolutePath());
		if (copyOption != null) {
			Files.copy(sourcePath, destinationPath, copyOption);
		} else {
			Files.copy(sourcePath, destinationPath);
		}
	}

	/**
	 * Delete file with specified name
	 * 
	 * @throws IOException
	 */
	@Test
	@TestProperties(name = "Local - Delete file or directory '${file}'", paramsInclude = { "file", "fileOrFolder",
			"emptyBeforeDeleting" })
	public void deleteFileOrDirectory() throws IOException {
		report.step("About to delete file " + file);
		if (null == file) {
			report.report("File can't be null");
			return;
		}
		if (!file.exists()) {
			report.report("Specified file is not exist");
			return;

		}
		switch (fileOrFolder) {
		case FILE:
			if (file.isDirectory()) {
				report.report("Specified file is not directory");
				return;
			}

			break;
		case FOLDER:
			if (file.isFile()) {
				report.report("Specified file is not a file");
				return;
			}
			break;

		}
		Files.delete(Paths.get(file.getAbsolutePath()));

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

	public FileOrFolder getFileOrFolder() {
		return fileOrFolder;
	}

	public void setFileOrFolder(FileOrFolder fileOrFolder) {
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

	public String[] getFileFromRepositoryOptions() {
		return new File(REPOSITORY_FOLDER).list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return new File(dir, name).isFile();
			}

		});
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

}
