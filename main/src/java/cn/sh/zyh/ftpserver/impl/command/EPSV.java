package cn.sh.zyh.ftpserver.impl.command;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;
import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

public class EPSV implements ICommand {
	public static final String COMMAND_NAME = "EPSV";

	public void execute(String line, StringTokenizer st, FtpSession session) {
		final String arg = StringUtils.getNextToken(st, "");
		final InetAddress address = session.getClientSocket().getInetAddress();

		if (("1".equalsIgnoreCase(arg) && !(address instanceof Inet4Address))
				|| ("2".equalsIgnoreCase(arg) && (address instanceof Inet4Address))) {
			session.reply(522, "Network protocol mismatch, use (" + arg + ").");
		} else if ("ALL".equalsIgnoreCase(arg)) {
			session.setEpsvAll(true);
			session.reply(200, "EPSV ALL command successful.");
		} else if ("".equalsIgnoreCase(arg)) {
			session.setPassiveMode(true);
			try {
				int port = session.getDataHandler().listen();
				final String addrStr = "|||" + port + "|";
				final String response = "Entering Extended Passive Mode ("
						+ addrStr + ")";
				session.reply(229, response);
			} catch (final IOException e) {
				session.reply(500, "Exception during server socket creation");
			}
		} else {
			session.reply(ResponseCode.SYNTAX_ERROR, Messages.getString(
					MessageKeys.RESP_500_NOT_UNDERSTOOD, StringUtils
					.formatReplyMsg(COMMAND_NAME, line)));
		}
	}
}
