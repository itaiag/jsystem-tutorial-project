package org.jsystem.file_system_so;

import jsystem.framework.system.SystemObjectImpl;

/**
 * A system object that represents a remote machine. The machine can be from
 * type Windows or Linux. Most of the exposes services are related to the file
 * system
 * 
 * @author Itai Agmon
 *
 */
public class Machine extends SystemObjectImpl {

	public AbstractRemoteFileSystem fileSystem;

	@Override
	public void init() throws Exception {
		super.init();
		report.report("Initiliazing machine");
	}

	@Override
	public void close() {
		report.report("Closing machine");
	}

}
