package cn.sh.zyh.ftpserver.impl.command;

import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;

public class CWD implements ICommand {
	public static final String COMMAND_NAME = "CWD";

	public void execute(String line, StringTokenizer st, FtpSession session) {
		session.reply(ResponseCode.REQUESTED_FILE_ACTION_OK, Messages
				.getString(MessageKeys.RESP_200_COMMAND_OK, COMMAND_NAME));
	}
}
