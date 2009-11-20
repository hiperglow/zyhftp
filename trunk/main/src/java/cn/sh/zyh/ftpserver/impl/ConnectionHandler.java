package cn.sh.zyh.ftpserver.impl;

import java.io.IOException;
import java.net.Socket;
import java.util.Locale;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sh.zyh.ftpserver.api.Configuration;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;

public class ConnectionHandler extends Thread {
	private final Logger logger = LoggerFactory.getLogger("ConnectionHandler");

	private FtpSession session = null;

	/** The client we are talking to. */
	private Socket clientSocket;

	public ConnectionHandler(final Socket clientSock) {
		this.clientSocket = clientSock;
	}

	/**
	 * Runs the protocol interpreter for a client.
	 */
	public void run() {
		try {
			// add to ConnectionManager
			ConnectionManager.getInstance().addConnection(this);
			session = new FtpSession(clientSocket);

			// process commands from client
			loopCommands();
		} catch (final IOException e) {
			logger.warn("Loop command failed: " + e.getMessage());
		} finally {
			this.clean();
		}
	}

	/**
	 * Cleans the resouces.
	 * 
	 */
	private void clean() {
		session.clean();
		// remove from ConnectionManager
		ConnectionManager.getInstance().removeConnection(this);
	}

	/**
	 * The loops on input from the client socket, reading each command and using
	 * the reflection API to invoke the appropriate command handler method for
	 * that command.
	 * 
	 * @throws IOException
	 *             the IO exception
	 */
	private void loopCommands() throws IOException {
		// welcome message
		final String serverString = Configuration.SERVER_NAME;
		session.reply(ResponseCode.SERVICE_READY, Messages.getString(
				MessageKeys.RESP_220_READY, serverString));

		String line = null;
		// read command from client
		while ((line = session.getReader().readLine()) != null) {

			final StringTokenizer st = new StringTokenizer(line);
			final String command = st.nextToken().toUpperCase(
					Locale.getDefault());
			// process command
			executeCommand(command, line, st);
			if (session.isClose()) {
				break;
			}
		}
	}

	/**
	 * Call command method.
	 * 
	 * @param cmd
	 *            the cmd
	 * @param line
	 *            the line
	 * @param st
	 *            the string tokenizer.
	 * 
	 * @throws CommandException
	 *             the command exception
	 */
	public void executeCommand(final String cmd, final String line,
			final StringTokenizer st) {
		logger.debug("Receive command <" + line + "> from "
				+ session.getClientIP());

		final ICommand abstractCmd = CommandFactory.GetInstance().getCommand(
				cmd);
		if (null != abstractCmd) {
			abstractCmd.execute(line, st, session);
		} else {
			session.replyUnimplemented(cmd.toUpperCase());
			if (logger.isDebugEnabled()) {
				logger.debug("No such command: " + cmd.toUpperCase());
			}
		}
	}
}