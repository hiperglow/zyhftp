package cn.sh.zyh.ftpserver.impl.command;

import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;
import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

public class LPRT implements ICommand {
	public static final String COMMAND_NAME = "LPRT";

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

		final int dataPort = getLprtPort(st);
		if (dataPort == 0) {
			session.reply(ResponseCode.SYNTAX_ERROR, Messages.getString(
					MessageKeys.RESP_500_NOT_UNDERSTOOD, StringUtils
							.formatReplyMsg(COMMAND_NAME, line)));
		} else {
			session.setDataPort(dataPort);
			session.setPassiveMode(false);
			session.reply(ResponseCode.COMMAND_OK, Messages.getString(
					MessageKeys.RESP_200_COMMAND_OK, COMMAND_NAME));
		}
	}

	/**
	 * Get port number from LPRT command. <br>
	 * for example: LPRT 6,16,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,2,193,160 <br>
	 * 
	 * @param st
	 *            command line contained port number
	 * @return port number
	 */
	private int getLprtPort(final StringTokenizer st) {
		// get param of LPRT command
		final String param = StringUtils.getNextToken(st, "");
		if ("".equals(param)) {
			return 0;
		}

		// split param with ','
		final StringTokenizer tokenizer = new StringTokenizer(param, ",");
		if (getNextIntValue(tokenizer) != 6) {
			return 0;
		}

		// skip ipv6 address
		final int count = getNextIntValue(tokenizer);
		if (count == -1 || count > tokenizer.countTokens()) {
			return 0;
		}
		for (int i = 0; i < count; i++) {
			tokenizer.nextToken();
		}

		// get port
		final int portNo = getNextIntValue(tokenizer);
		if (portNo == -1 || portNo != tokenizer.countTokens()) {
			return 0;
		}
		int port = 0;
		for (int i = 0; i < portNo; i++) {
			final int p = getNextIntValue(tokenizer);
			if (p == -1) {
				return 0;
			}
			port = port | p << (portNo - i - 1) * 8;
		}

		return port;
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
