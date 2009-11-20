package cn.sh.zyh.ftpserver.impl.command;

import java.io.File;
import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;
import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

public class DELE implements ICommand {

	public static final String COMMAND_NAME = "DELE";

	public void execute(String line, StringTokenizer st, FtpSession session) {
		if (!session.isLogin()) {
			session.reply(ResponseCode.NOT_LOGGED_IN, Messages
					.getString(MessageKeys.RESP_503_USER_FIRST));
			return;
		}

		final String curDir = session.getCurrentDir();
		String path = StringUtils.getNextToken(st, "");
		path = StringUtils.createNativePath(StringUtils.resolvePath(path,
				curDir), curDir);

		final File file = new File(path);
		if (!file.exists()) {
			session.reply(550, path + ": file does not exist");
		} else if (!file.delete()) {
			session.reply(550, path + ": could not delete file");
		} else {
			session
					.reply(250, Messages
							.getString(MessageKeys.RESP_250_DELE_OK));
		}
	}

}
