package cn.sh.zyh.ftpserver.impl.command;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;

public class QUIT implements ICommand {
	public static final String COMMAND_NAME = "QUIT";

	private final Logger logger = LoggerFactory.getLogger("ConnectionHandler");

	public void execute(String line, StringTokenizer st, FtpSession session) {
		logger.debug("QUIT received, closing session.");

		session.close();
		session.reply(ResponseCode.GOODBYE, Messages
				.getString(MessageKeys.RESP_221_BYE));
	}
}
