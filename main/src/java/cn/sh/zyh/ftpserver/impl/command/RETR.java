package cn.sh.zyh.ftpserver.impl.command;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;
import cn.sh.zyh.ftpserver.impl.dataconnection.DataConnectionHandler;
import cn.sh.zyh.ftpserver.impl.representation.AsciiRepresentation;
import cn.sh.zyh.ftpserver.impl.representation.BinaryRepresentation;
import cn.sh.zyh.ftpserver.impl.representation.Representation;
import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

public class RETR implements ICommand {
	public static final String COMMAND_NAME = "RETR";

	/** The debug logger instance. */
	private final Logger logger = LoggerFactory.getLogger("RETR");

	public void execute(String line, StringTokenizer st, FtpSession session) {
		if (!session.isLogin()) {
			session.reply(ResponseCode.NOT_LOGGED_IN, Messages
					.getString(MessageKeys.RESP_503_USER_FIRST));
			return;
		}

		String path = StringUtils.getNextToken(st, "");
		path = StringUtils.createNativePath(path, session.getCurrentDir());
		final File file = new File(path);
		if (!file.exists() || !file.isFile()) {
			session.reply(ResponseCode.CODE_550_FILE_NOT_FOUND, Messages
					.getString(MessageKeys.RESP_550_FILE_NOT_FOUND));

			return;
		}

		logger.debug("Client retrive file :" + path);

		// connect to client
		DataConnectionHandler handler = session.getDataHandler();
		if (session.isPassiveMode()) {
			try {
				handler.waitForClient();
			} catch (IOException e) {
				// TODO reply message to client
				session.reply(501, "Error in data connection.");
				return;
			}
		} else {
			try {
				handler.connectToClient(session.getClientIP(), session
						.getDataPort());
			} catch (IOException e) {
				// TODO reply message to client
				session.reply(501, "Error in data connection.");
				return;
			}
		}

		// reply message to client
		session.reply(ResponseCode.CODE_150_OPEN_DATA_CHANNEL, "Opening "
				+ session.getDataType4Trans() + " mode data connection.");

		Representation representation = null;
		if ("ASCII".equals(session.getDataType4Trans())) {
			representation = AsciiRepresentation.getInstance();
		} else {
			representation = BinaryRepresentation.getInstance();
		}

		// send file
		try {
			handler.sendFile(representation, file);
			session.reply(226, "Transfer complete.");
		} catch (IOException e) {
			session.reply(550, "Requested action not taken");
		}
	}

}
