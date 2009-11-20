package cn.sh.zyh.ftpserver.impl;

import java.io.IOException;
import java.net.Socket;
import java.util.Locale;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sh.zyh.ftpserver.api.Configuration;
import cn.sh.zyh.ftpserver.impl.core.CommandException;
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
	
	/**
	 * Handle HELP command.
	 * 
	 * @param st
	 *            the argument string.
	 */
	/*
	 * private void handleHelp(final StringTokenizer st) { final String arg =
	 * getNextToken(st, ""); if ("".equals(arg)) { final StringBuffer sb = new
	 * StringBuffer(); sb .append("214-The following commands are recognized (*
	 * unimplemented):"); sb.append(Configuration.NEWLINE); sb .append(" USER
	 * PORT TYPE MLFL* MRCP* DELE* SYST RMD* STOU");
	 * sb.append(Configuration.NEWLINE); sb .append(" PASS LPRT STRU* MAIL*
	 * ALLO* CWD STAT* XRMD* SIZE*"); sb.append(Configuration.NEWLINE); sb
	 * .append(" ACCT* EPRT MODE* MSND* REST* XCWD HELP PWD MDTM*");
	 * sb.append(Configuration.NEWLINE); sb .append(" SMNT* PASV RETR MSOM*
	 * RNFR* LIST NOOP XPWD"); sb.append(Configuration.NEWLINE); sb .append("
	 * REIN* LPSV STOR MSAM* RNTO* NLST MKD* CDUP");
	 * sb.append(Configuration.NEWLINE); sb .append(" QUIT EPSV APPE MRSQ* ABOR
	 * SITE* XMKD* XCUP*"); this.writer.println(sb.toString());
	 * 
	 * reply(214, "Direct comments to ftp-bugs@" +
	 * clientSocket.getLocalAddress().getHostName() + "."); } else {
	 * handleHelpWithArgu(arg); } }
	 * 
	 *//**
		 * Handle HELP command with the arguments.
		 * 
		 * @param command
		 *            the argument string.
		 */
	/*
	 * private void handleHelpWithArgu(final String command) { final
	 * FTPCommandHelpDefine ftpCommandList = new FTPCommandHelpDefine(); final
	 * List commandList = ftpCommandList.getFtpCommandList(); for (int i = 0; i <
	 * commandList.size(); i++) { final String[] arrary = (String[])
	 * commandList.get(i); if (arrary[0].equalsIgnoreCase(command) &&
	 * ("OK").equalsIgnoreCase(arrary[1])) { final StringBuffer sb = new
	 * StringBuffer(); sb.append("Syntax: "); sb.append(arrary[0]);
	 * sb.append(arrary[2]); reply(214, sb.toString()); return; } else if
	 * (arrary[0].equalsIgnoreCase(command) &&
	 * ("NG").equalsIgnoreCase(arrary[1])) { final StringBuffer sb = new
	 * StringBuffer(); sb.append(arrary[0]); sb.append("\t");
	 * sb.append(arrary[2]); sb.append("; unimplemented."); reply(214,
	 * sb.toString()); return; } else { continue; } } reply(502, "Unknown
	 * command " + command.toUpperCase() + "."); }
	 * 
	 */
}