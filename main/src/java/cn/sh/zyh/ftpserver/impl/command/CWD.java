package cn.sh.zyh.ftpserver.impl.command;

import java.io.File;
import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;
import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

public class CWD implements ICommand {
	public static final String COMMAND_NAME = "CWD";

	public void execute(String line, StringTokenizer st, FtpSession session) {
		if (!session.isLogin()) {
			session.reply(ResponseCode.NOT_LOGGED_IN, Messages
					.getString(MessageKeys.RESP_503_USER_FIRST));
			return;
		}

		String newDir = StringUtils.getNextToken(st, "/");
		newDir = StringUtils.resolvePath(newDir, session.getCurrentDirectory());
		final File file = new File(StringUtils.createNativePath(newDir, session
				.getCurrentDirectory()));
		if (!file.exists()) {
			session.reply(ResponseCode.CODE_550_DIR_NOT_FOUND, newDir
					+ Messages.getString(MessageKeys.RESP_550_DIR_NOT_FOUND));
			return;
		}
		if (!file.isDirectory()) {
			session.reply(550, newDir
					+ Messages.getString(MessageKeys.RESP_550_NOT_A_DIR));
			return;
		}

		session.setCurrentDirectory(newDir);
		session.reply(ResponseCode.REQUESTED_FILE_ACTION_OK, Messages
				.getString(MessageKeys.RESP_200_COMMAND_OK, COMMAND_NAME));
	}
}
