package cn.sh.zyh.ftpserver.impl.command;

import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;
import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

public class PASS implements ICommand {
	public static final String COMMAND_NAME = "PASS";
	
	public void execute(String line, StringTokenizer st, FtpSession session) {
		if (session.getUsername() == null) {
			session.reply(ResponseCode.BAD_SEQUENCE_OF_COMMANDS, Messages
					.getString(MessageKeys.RESP_503_USER_FIRST));
		} else {
			session.setPassword(StringUtils.getNextToken(st, ""));
			session.hasLogin();

			session.reply(ResponseCode.USER_LOGGED_IN_PROCEED, Messages
					.getString(MessageKeys.RESP_230_LOGGED_IN, session
							.getUsername()));
		}
	}
}
