package cn.sh.zyh.ftpserver.impl.command;

import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;
import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

public class EPRT implements ICommand {
	public static final String COMMAND_NAME = "EPRT";

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

		final int dataPort = getEprtPort(st);
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

	/**
	 * Get port number from EPRT command. <br>
	 * EPRT command example: <br>
	 * EPRT |1|132.235.1.2|6275|<br>
	 * EPRT |2|1080::8:800:200C:417A|5282|<br>
	 * 
	 * @param st
	 * @return
	 */
	private int getEprtPort(final StringTokenizer st) {
		// get param of LPRT command
		final String param = StringUtils.getNextToken(st, "");
		if ("".equals(param)) {
			return 0;
		}

		// split param with ','
		final StringTokenizer tokenizer = new StringTokenizer(param, "|");
		if (tokenizer.countTokens() != 3) {
			return 0;
		}

		// skip 2 tokens
		tokenizer.nextToken();
		tokenizer.nextToken();

		final int port = getNextIntValue(tokenizer);
		return port;
	}

	private int getNextIntValue(final StringTokenizer st) {
		int value = -1;
		if (st.hasMoreTokens()) {
			try {
				value = Integer.parseInt(st.nextToken());
			} catch (NumberFormatException ex) {
				// ignore exception, error value
			}
		}
		return value;
	}

}
