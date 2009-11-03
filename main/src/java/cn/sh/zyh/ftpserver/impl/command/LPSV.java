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

public class LPSV implements ICommand {
	public static final String COMMAND_NAME = "LPSV";

	public void execute(String line, StringTokenizer st, FtpSession session) {
		if (session.isEpsvAll()) {
			session.reply(ResponseCode.REQUESTED_EPSV_ALL_501, Messages
					.getString(MessageKeys.RESP_501_AFTER_EPSV_ALL,
							COMMAND_NAME));
			return;
		}

		session.setPassiveMode(true);
		try {
			int port = session.getDataHandler().listen();
			final InetAddress ip = session.getClientSocket().getLocalAddress();
			String addrStr = ip.getHostAddress().replace('.', ',').replace(':',
					',') + ",2," + (port >> 8) + ',' + (port & 0xFF);
			if (ip instanceof Inet4Address) {
				addrStr = "4,4," + addrStr;
			} else {
				addrStr = "6,16," + addrStr;
			}
			final String response = "Entering Long Passive Mode (" + addrStr
					+ ")";
			session.reply(228, response);
		} catch (final IOException e) {
			session.reply(500, "Exception during server socket creation");
		}
	}
}
