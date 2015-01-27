package org.jsystem.file_system_so;

import java.util.ArrayList;

import systemobject.terminal.Prompt;

import com.aqua.sysobj.conn.LinuxDefaultCliConnection;

public class UbuntuCliConnection extends LinuxDefaultCliConnection {

	public Prompt[] getPrompts() {
		ArrayList<Prompt> prompts = new ArrayList<Prompt>();
		Prompt p = new Prompt();
		p.setCommandEnd(true);
		p.setPrompt("# ");
		prompts.add(p);

		// Adding the prompt we need for the Ubuntu OS
		p = new Prompt();
		p.setCommandEnd(true);
		p.setPrompt("$ ");
		prompts.add(p);

		p = new Prompt();
		p.setPrompt("login: ");
		p.setStringToSend(getUser());
		prompts.add(p);

		p = new Prompt();
		p.setPrompt("Password: ");
		p.setStringToSend(getPassword());
		prompts.add(p);
		return prompts.toArray(new Prompt[prompts.size()]);
	}

}
