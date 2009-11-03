package cn.sh.zyh.ftpserver.impl.command;

import java.io.IOException;
import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;

public class PASV implements ICommand {
	public static final String COMMAND_NAME = "PASV";

	public void execute(String line, StringTokenizer st, FtpSession session) {
		if (!session.isLogin()) {
			session.reply(ResponseCode.NOT_LOGGED_IN, Messages
					.getString(MessageKeys.RESP_503_USER_FIRST));
			return;
		}
		if (session.isEpsvAll()) {
			session.reply(ResponseCode.REQUESTED_EPSV_ALL_501, Messages
					.getString(MessageKeys.RESP_501_AFTER_EPSV_ALL,
							COMMAND_NAME));
			return;
		}

		session.setPassiveMode(true);
		try {
			int port = session.getDataHandler().listen();
			final String address = session.getHostIP().replace('.', ',') + ','
					+ (port >> 8) + ',' + (port & 0xFF);
			session.reply(ResponseCode.ENTERING_PASSIVE_MODE, Messages
					.getString(MessageKeys.RESP_227_PASV_MODE, address));
		} catch (final IOException e) {
			session.reply(ResponseCode.SYNTAX_ERROR,
					"Exception during server socket creation");
		}
	}
}
