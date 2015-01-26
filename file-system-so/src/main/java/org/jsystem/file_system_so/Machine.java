package org.jsystem.file_system_so;

import jsystem.framework.system.SystemObjectImpl;

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
