package cn.sh.zyh.ftpserver.impl;

import cn.sh.zyh.ftpserver.impl.command.ABOR;
import cn.sh.zyh.ftpserver.impl.command.CWD;
import cn.sh.zyh.ftpserver.impl.command.DELE;
import cn.sh.zyh.ftpserver.impl.command.EPRT;
import cn.sh.zyh.ftpserver.impl.command.EPSV;
import cn.sh.zyh.ftpserver.impl.command.HELP;
import cn.sh.zyh.ftpserver.impl.command.LIST;
import cn.sh.zyh.ftpserver.impl.command.LPRT;
import cn.sh.zyh.ftpserver.impl.command.LPSV;
import cn.sh.zyh.ftpserver.impl.command.NLST;
import cn.sh.zyh.ftpserver.impl.command.NOOP;
import cn.sh.zyh.ftpserver.impl.command.PASS;
import cn.sh.zyh.ftpserver.impl.command.PASV;
import cn.sh.zyh.ftpserver.impl.command.PORT;
import cn.sh.zyh.ftpserver.impl.command.PWD;
import cn.sh.zyh.ftpserver.impl.command.QUIT;
import cn.sh.zyh.ftpserver.impl.command.RETR;
import cn.sh.zyh.ftpserver.impl.command.STOR;
import cn.sh.zyh.ftpserver.impl.command.SYST;
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
		} else if (PORT.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new PORT();
		} else if (SYST.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new SYST();
		} else if (NOOP.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new NOOP();
		} else if (CWD.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new CWD();
		} else if (PWD.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new PWD();
		} else if (PASV.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new PASV();
		} else if (LPSV.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new LPSV();
		} else if (EPSV.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new EPSV();
		} else if (LIST.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new LIST();
		} else if (NLST.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new NLST();
		} else if (RETR.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new RETR();
		} else if (STOR.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new STOR();
		} else if (DELE.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new DELE();
		} else if (HELP.COMMAND_NAME.equalsIgnoreCase(name)) {
			return new HELP();
		}

		return null;
	}
}
