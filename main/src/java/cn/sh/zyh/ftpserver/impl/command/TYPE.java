package cn.sh.zyh.ftpserver.impl.command;

import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;
import cn.sh.zyh.ftpserver.impl.representation.Representation;
import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

public class TYPE implements ICommand {
	public static final String COMMAND_NAME = "TYPE";

	public void execute(String line, StringTokenizer st, FtpSession session) {
		if (!session.isLogin()) {
			session.reply(ResponseCode.NOT_LOGGED_IN, Messages
					.getString(MessageKeys.RESP_503_USER_FIRST));
			return;
		}

		if (st.countTokens() > 1) {
			session.reply(ResponseCode.SYNTAX_ERROR, Messages.getString(
					MessageKeys.RESP_500_NOT_UNDERSTOOD, StringUtils
							.formatReplyMsg(COMMAND_NAME, line)));
			return;
		}

		final String type = StringUtils.getNextToken(st, "");

		if (Representation.TYPE_ASCII.equalsIgnoreCase(type)) {
			session.setDataType4Trans(Representation.TYPE_ASCII);
			session.reply(ResponseCode.COMMAND_OK, Messages.getString(
					MessageKeys.RESP_200_TYPE_SET, Representation.TYPE_ASCII));
		} else if (Representation.TYPE_BINARY.equalsIgnoreCase(type)) {
			session.setDataType4Trans(Representation.TYPE_BINARY);
			session.reply(ResponseCode.COMMAND_OK, Messages.getString(
					MessageKeys.RESP_200_TYPE_SET, Representation.TYPE_BINARY));
		} else {
			session.reply(ResponseCode.SYNTAX_ERROR, Messages.getString(
					MessageKeys.RESP_500_NOT_UNDERSTOOD, StringUtils
							.formatReplyMsg(COMMAND_NAME, line)));
		}
	}
}
