package cn.sh.zyh.ftpserver.impl.command;

import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;

public class PWD implements ICommand {
	public static final String COMMAND_NAME = "PWD";

	public void execute(String line, StringTokenizer st, FtpSession session) {
		session.reply(ResponseCode.PATHNAME_CREATED, Messages.getString(
				MessageKeys.RESP_257_PWD, session.getCurrentDirectory()));
	}
}
