package cn.sh.zyh.ftpserver.impl.command;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.command.list.DirectoryLister;
import cn.sh.zyh.ftpserver.impl.command.list.NlistFileFormater;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;
import cn.sh.zyh.ftpserver.impl.dataconnection.DataConnectionHandler;
import cn.sh.zyh.ftpserver.impl.representation.AsciiRepresentation;
import cn.sh.zyh.ftpserver.impl.representation.BinaryRepresentation;
import cn.sh.zyh.ftpserver.impl.representation.Representation;
import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

public class NLST implements ICommand {
	public static final String COMMAND_NAME = "NLST";

	private final Logger logger = LoggerFactory.getLogger("NLST");

	/**
	 * NLST comand = dir (user command) <br>
	 * The detail executing result:<br>
	 * >ls -> list the detail information of current directory.<br>
	 * >ls - -> reply '501 Syntax error' message.<br>
	 * >ls -asf -> list the detail information of current directory.<br>
	 * >ls test -> list the detail information of specified directory.<br>
	 * >ls asf -> reply '550 Directory not found.'.<br>
	 */
	public void execute(String line, StringTokenizer st, FtpSession session) {
		// get specifid directory to list
		String requestedPath = "";
		final int tokenCount = st.countTokens();
		final String curDir = session.getCurrentDirectory();

		if (tokenCount == 0) {
			requestedPath = StringUtils.createNativePath(curDir, curDir);
		} else {
			final String firstParam = st.nextToken();
			if ("-".equals(firstParam)) {
				session.reply(ResponseCode.CODE_501_SYNTAX_ERROR, Messages
						.getString(MessageKeys.RESP_501_SYNTAX_ERROR));
				return;
			} else if (firstParam.startsWith("-")) {
				requestedPath = StringUtils.createNativePath(curDir, curDir);
			} else {
				requestedPath = StringUtils
						.createNativePath(firstParam, curDir);
			}

			if (!doesExist(requestedPath)) {
				session.reply(ResponseCode.CODE_550_DIR_NOT_FOUND, Messages
						.getString(MessageKeys.RESP_550_DIR_NOT_FOUND));
				return;
			}
		}

		logger.debug("Specified path: " + requestedPath);

		// list files of specified path
		final String filesInfo = new DirectoryLister().listFiles(requestedPath,
				new NlistFileFormater());

		logger.debug("Specified path info: \n" + filesInfo);

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

		// send info
		handler.sendData(representation, filesInfo);

		session.reply(226, "Transfer complete.");
	}

	private boolean doesExist(final String path) {
		final File file = new File(path);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}
}
