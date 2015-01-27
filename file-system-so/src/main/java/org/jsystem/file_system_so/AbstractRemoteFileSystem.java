package org.jsystem.file_system_so;

import org.junit.Assert;

import com.aqua.sysobj.conn.CliConnectionImpl;

import jsystem.framework.system.SystemObjectImpl;

/**
 * System object that represents remote file system. This class should be
 * inhrite by different classes that will implement the different services
 * according to each operating system
 * 
 * @author Itai Agmon
 *
 */
public abstract class AbstractRemoteFileSystem extends SystemObjectImpl {

	public CliConnectionImpl cliConnection;

	@Override
	public void init() throws Exception {
		super.init();
		report.report("Initializing file system from type " + this.getClass().getSimpleName());
		Assert.assertNotNull(cliConnection);
	}

	public abstract void copyFile(String sourceFolder, String sourceFile, String destinationFolder,
			String destinationFile) throws Exception;

	public abstract void createNewFile(String folder, String file) throws Exception;

	public abstract void writeContentToFile(String folder, String file, String content, boolean append)
			throws Exception;

	public abstract void deleteFile(String folder, String file) throws Exception;

	@Override
	public void close() {
		report.report("Closing file system from type " + this.getClass().getSimpleName());
	}
}
