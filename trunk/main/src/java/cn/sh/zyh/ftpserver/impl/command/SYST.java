package cn.sh.zyh.ftpserver.impl.command;

import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;

public class SYST implements ICommand {
	public static final String COMMAND_NAME = "SYST";
	
	public void execute(String line, StringTokenizer st, FtpSession session) {
		// get server system info
		String systemName = System.getProperty("os.name");
		if (systemName == null) {
			systemName = "UNKNOWN";
		} else {
			systemName = systemName.toUpperCase();
			systemName = systemName.replace(' ', '-');
		}

		session.reply(215, systemName);
	}
}
