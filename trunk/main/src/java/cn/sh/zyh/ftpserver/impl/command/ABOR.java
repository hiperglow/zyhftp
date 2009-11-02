package cn.sh.zyh.ftpserver.impl.command;

import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;

public class ABOR implements ICommand {
	public static final String COMMAND_NAME = "ABOR";
	
	public void execute(String line, StringTokenizer st, FtpSession session) {
		session.reply(ResponseCode.CONN_CLOSED_TRANSFER_ABORTED, Messages
				.getString(MessageKeys.RESP_426_TRANSFER_CLOSED));

		session.reply(ResponseCode.CLOSING_DATA_CONNECTION, Messages
				.getString(MessageKeys.RESP_226_ABORT_OK));
	}
}
