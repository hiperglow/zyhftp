package cn.sh.zyh.ftpserver.impl.command;

import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.FTPCommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;

public class LPRT implements ICommand {
	public static final String COMMAND_NAME = "LPRT";

	public void execute(String line, StringTokenizer st, FtpSession session) {
		if (session.isEpsvAll()) {
			session.reply(ResponseCode.REQUESTED_EPSV_ALL_501, Messages
					.getString(MessageKeys.RESP_501_AFTER_EPSV_ALL,
							FTPCommand.LPRT));
			return;
		}

		final String portStr = st.nextToken(); // port final int lastDelimIdx =
		portStr.lastIndexOf(',', portStr.lastIndexOf(',') - 1);
		final StringTokenizer portst = new StringTokenizer(portStr.substring(
				lastDelimIdx + 1, portStr.length()), ",");
		final int p1 = Integer.parseInt(portst.nextToken());
		final int p2 = Integer.parseInt(portst.nextToken());
		final int dataPort = (p1 << 8) | p2;

		final String dataHost = session.getClientIP();

		this.dataHandler.setDataPort(dataHost, dataPort);
		this.dataHandler.setPassive(false);

		session.reply(ResponseCode.COMMAND_OK, Messages.getString(
				MessageKeys.RESP_200_COMMAND_OK, FTPCommand.LPRT));

	}

	/**
	 * Get port number from LPRT 6,16,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,2,193,160
	 * 
	 * @param line
	 * @return
	 */
	private String getLprtPort(final String line) {
		
		
		
		return "";
	}

}
