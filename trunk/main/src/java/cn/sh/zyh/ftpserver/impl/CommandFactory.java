package cn.sh.zyh.ftpserver.impl;

import cn.sh.zyh.ftpserver.impl.command.ABOR;
import cn.sh.zyh.ftpserver.impl.command.EPRT;
import cn.sh.zyh.ftpserver.impl.command.LPRT;
import cn.sh.zyh.ftpserver.impl.command.PASS;
import cn.sh.zyh.ftpserver.impl.command.QUIT;
import cn.sh.zyh.ftpserver.impl.command.TYPE;
import cn.sh.zyh.ftpserver.impl.command.USER;

public class CommandFactory {
	private static CommandFactory instance = new CommandFactory();

	private CommandFactory() {
	}

	public static CommandFactory GetInstance() {
		return instance;
	}

	public ICommand getCommand(final String name) {
		if (USER.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new USER();
		} else if (PASS.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new PASS();
		} else if (QUIT.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new QUIT();
		} else if (TYPE.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new TYPE();
		} else if (ABOR.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new ABOR();
		} else if (LPRT.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new LPRT();
		} else if (EPRT.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new EPRT();
		}

		return null;
	}
}
