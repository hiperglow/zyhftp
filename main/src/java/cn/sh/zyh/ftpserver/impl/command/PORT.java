package cn.sh.zyh.ftpserver.impl.command;

import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;
import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

public class PORT implements ICommand {
	public static final String COMMAND_NAME = "PORT";

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

		final int dataPort = getPortPort(st);
		if (dataPort == 0) {
			session.reply(ResponseCode.SYNTAX_ERROR, Messages.getString(
					MessageKeys.RESP_500_NOT_UNDERSTOOD, StringUtils
							.formatReplyMsg(COMMAND_NAME, line)));
		} else {
			session.setDataPort(dataPort);
			// this.dataHandler.setPassive(false);
			session.reply(ResponseCode.COMMAND_OK, Messages.getString(
					MessageKeys.RESP_200_COMMAND_OK, COMMAND_NAME));
		}
	}

	private int getPortPort(final StringTokenizer st) {
		// get param of LPRT command
		final String param = StringUtils.getNextToken(st, "");
		if ("".equals(param)) {
			return 0;
		}
		// split param with ','
		final StringTokenizer tokenizer = new StringTokenizer(param, ",");
		if (tokenizer.countTokens() != 3) {
			return 0;
		}
		st.nextToken();
		final int p1 = getNextIntValue(tokenizer);
		final int p2 = getNextIntValue(tokenizer);
		if (p1 == -1 || p2 == -1) {
			return 0;
		}

		return (p1 << 8) | p2;
	}

	private int getNextIntValue(final StringTokenizer st) {
		int value = -1;
		if (st.hasMoreTokens()) {
			try {
				value = Integer.parseInt(st.nextToken());
				if (value < 0 || value > 255) {
					return -1;
				}
			} catch (NumberFormatException ex) {
				// ignore exception, error value
			}
		}
		return value;
	}
}
