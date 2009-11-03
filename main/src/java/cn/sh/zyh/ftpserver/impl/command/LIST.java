package cn.sh.zyh.ftpserver.impl.command;

import java.io.IOException;
import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

public class LIST implements ICommand {
	public static final String COMMAND_NAME = "LIST";

	public void execute(String line, StringTokenizer st, FtpSession session) {
		String requestedPath = "";
		final String curDir = session.getCurrentDirectory();
		if (line.toLowerCase().startsWith("list -") || !st.hasMoreTokens()) {
			requestedPath = StringUtils.createNativePath(curDir, curDir);
		} else {
			String token = StringUtils.createNativePath(st.nextToken(), curDir);

			if (!token.startsWith("-")) {
				requestedPath = token;
			} else {
				requestedPath = StringUtils.createNativePath(curDir, curDir);
			}
		}

	/*	try {
			// FileList files = new RequestHandler().listRequested(null);
			// return session.getDataHandler().sendList(files);
		} catch (IOException e) {
			session.reply(550, "error listing files");
		}*/
	}

}
