package cn.sh.zyh.ftpserver.impl.command;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;
import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

public class USER implements ICommand {
	public static final String COMMAND_NAME = "USER";

	/** The debug logger instance. */
	private final Logger logger = LoggerFactory.getLogger("FtpSession");

	/**
	 * Execute command.
	 */
	public void execute(final String line, final StringTokenizer st,
			final FtpSession session) {
		// clean user information
		session.cleanUser();

		final String user = StringUtils.getNextToken(st, "");
		logger.debug("Client " + session.getClientIP() + " user name: " + user);

		if ("".equals(user)) {
			session.reply(ResponseCode.SYNTAX_ERROR, Messages.getString(
					MessageKeys.RESP_500_NOT_UNDERSTOOD, StringUtils.formatReplyMsg(
							COMMAND_NAME, line)));
		} else {
			session.setUsername(user);
			session.reply(ResponseCode.USER_NAME_OK, Messages.getString(
					MessageKeys.RESP_331_PASS_REQ, user));
		}
	}
}
