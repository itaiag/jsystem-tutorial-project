package org.jsystem.file_system_so;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.DosFileAttributes;

import jsystem.framework.report.ReporterHelper;
import jsystem.framework.system.SystemObjectImpl;
import junit.framework.Assert;

public class LocalFileSystem extends SystemObjectImpl {

	public enum FileOrFolder {
		FILE, FOLDER
	}

	/**
	 * The location of the file repository folder. This is the folder that will
	 * be used in the <i>copyFileFromTheRepository</i> method
	 */
	private String repositoryFolder;

	public String createTempFile(String prefix, String suffix) throws IOException {
		report.step("About to create temporary file");
		String tempFile = File.createTempFile(prefix, suffix).getAbsolutePath();
		report.report("Created file with name: " + tempFile);
		return tempFile;
	}

	/**
	 * Create multiple files according to user preferences.
	 */
	public void createMultipleFiles(FileAttributes[] fileAttributesArr) throws Exception {
		report.step("About to create multiple files");
		if (null == fileAttributesArr || fileAttributesArr.length == 0) {
			report.report("No file attributes were specified by user", 2);
			return;
		}
		report.startLevel("Crating multiple files");
		try {
			for (FileAttributes attr : fileAttributesArr) {
				Path filePath = Paths.get(attr.getFolder() + "/" + attr.getName());
				report.report("About to create file " + filePath);
				Files.write(filePath, attr.getContent().getBytes(), StandardOpenOption.CREATE);
				File file = new File(filePath.toString());
				if (attr.isReadOnly()) {
					file.setReadOnly();
				}
				ReporterHelper.copyFileToReporterAndAddLink(report, file, attr.getName());
			}

		} finally {
			report.stopLevel();
		}
	}

	/**
	 * Write specified content to file.
	 * 
	 */
	public void writeToFile(File file, String content, boolean append) throws IOException {
		report.step("About to write content to file");
		report.report("File content", content, true);
		final Path filePath = Paths.get(file.getAbsolutePath());
		Files.write(filePath, content.getBytes(), append ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);

	}

	/**
	 * Copy the specified source file to the specified destination. Notice that
	 * you can specify additional options.
	 */
	public void copyFile(File sourceFile, File destinationFile, CopyOption copyOption) throws IOException {
		report.step("About to copy file");
		Files.copy(Paths.get(sourceFile.getAbsolutePath()), Paths.get(destinationFile.getAbsolutePath()), copyOption);
	}

	/**
	 * Asserts that size of the specified file is equals to the expected
	 * specified file size.
	 */
	public void assertFileSize(File file, long expectedFileSize) throws IOException {
		report.step("About to assert file size");
		Path filePath = Paths.get(file.getAbsolutePath());
		DosFileAttributes attrs = Files.readAttributes(filePath, DosFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
		Assert.assertEquals(expectedFileSize, attrs.size());
	}

	public void copyFileFromRepository(String fileFromRepository, File destinationFile, CopyOption copyOption)
			throws IOException {
		report.step("About to copy file from the repository");
		Path sourcePath = Paths.get(repositoryFolder + "/" + fileFromRepository);
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
	 */
	public void deleteFileOrDirectory(File file, FileOrFolder fileOrFolder) throws IOException {
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

	public String getRepositoryFolder() {
		return repositoryFolder;
	}

	public void setRepositoryFolder(String repositoryFolder) {
		this.repositoryFolder = repositoryFolder;
	}

}
