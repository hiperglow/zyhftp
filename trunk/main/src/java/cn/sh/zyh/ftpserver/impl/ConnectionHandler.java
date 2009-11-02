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
			try {
				// process command
				executeCommand(command, line, st);
			} catch (final CommandException e) {
				session.reply(e.getCode(), e.getText());
			}

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
			final StringTokenizer st) throws CommandException {
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

		/*
		 * if (FTPCommand.ABOR.equals(cmd)) { handleAbor(); } else if
		 * (FTPCommand.CWD.equals(cmd) || FTPCommand.XCWD.equals(cmd)) {
		 * handleCwd(st); } else if (FTPCommand.HELP.equals(cmd)) {
		 * handleHelp(st); } else if (FTPCommand.LIST.equals(cmd)) {
		 * handleList(st); } else if (FTPCommand.NLST.equals(cmd)) {
		 * handleNlst(st); } else if (FTPCommand.PASS.equals(cmd)) {
		 * handlePass(st); } else if (FTPCommand.PASV.equals(cmd)) {
		 * handlePasv(); } else if (FTPCommand.PWD.equals(cmd) ||
		 * FTPCommand.XPWD.equals(cmd)) { handlePwd(); } else if
		 * (FTPCommand.PORT.equals(cmd)) { handlePort(st); } else if
		 * (FTPCommand.QUIT.equals(cmd)) { handleQuit(); } else if
		 * (FTPCommand.RETR.equals(cmd)) { handleRetr(st); } else if
		 * (FTPCommand.STOR.equals(cmd) || FTPCommand.APPE.equals(cmd) ||
		 * FTPCommand.STOU.equals(cmd)) { handleStor(st); } else if
		 * (FTPCommand.TYPE.equals(cmd)) { handleType(st); } else if
		 * (FTPCommand.USER.equals(cmd)) { handleUser(st); } else if
		 * (FTPCommand.EPSV.equals(cmd)) { handleEpsv(st); } else if
		 * (FTPCommand.EPRT.equals(cmd)) { handleEprt(st); } else if
		 * (FTPCommand.LPSV.equals(cmd)) { handleLpsv(); } else if
		 * (FTPCommand.LPRT.equals(cmd)) { handleLprt(st); } else if
		 * (FTPCommand.SYST.equals(cmd)) { handleSyst(); } else if
		 * (FTPCommand.NOOP.equals(cmd)) { handleNoop(); } else if
		 * (FTPCommand.CDUP.equals(cmd)) { // same to "CWD /" handleCwd(null); }
		 * else { replyUnimplemented(cmd.toUpperCase());
		 * 
		 * if (logger.isDebugEnabled()) { logger.debug(CLASS_NAME,
		 * "callCommandMethod", "No such command: " + cmd.toUpperCase()); } }
		 */
	}
	/*
	 * private void handleSyst() { reply(215, "UNIX TYPE: L8 Version:
	 * BSD-199506"); }
	 * 
	 */
	/*
	 * private void handleNoop() { reply(ResponseCode.COMMAND_OK,
	 * Messages.getString( MessageKeys.RESP_200_COMMAND_OK, FTPCommand.NOOP)); }
	 * 
	 */
	/*
	 * private void handleCwd(final StringTokenizer st) { if
	 * (isAuthModeLoginSuccess && this.userAuthInfo.isAdminMode()) {
	 * reply(ResponseCode.COMMAND_NOT_IMPLEMENTED_FOR_PARAMETER, Messages
	 * .getString(MessageKeys.RESP_504_NOT_USE_COMMAND, FTPCommand.CWD)); }
	 * 
	 * final String newOption = getNextToken(st, "/"); if
	 * (newOption.equals("/")) { cdOption = ""; } else { cdOption =
	 * this.optionBuilder.truncatePrintOption(newOption); } // init the cd
	 * option value of the option builder.
	 * this.optionBuilder.setCdOption(this.cdOption);
	 * 
	 * if (logger.isDebugEnabled()) { logger.debug(CLASS_NAME, "handleCwd", "CWD
	 * Print option is :" + newOption); }
	 * 
	 * reply(ResponseCode.REQUESTED_FILE_ACTION_OK, Messages.getString(
	 * MessageKeys.RESP_200_COMMAND_OK, FTPCommand.CWD)); }
	 * 
	 *//**
		 * Handle PORT command.
		 * 
		 * @param st
		 *            the string tokenizer.
		 */
	/**
		 * Handle PASV command.
		 * 
		 * @throws CommandException
		 *             the command exception
		 */
	/*
	 * private void handlePasv() throws CommandException {
	 * 
	 * if (isEpsvAll) { reply(ResponseCode.REQUESTED_EPSV_ALL_501,
	 * Messages.getString( MessageKeys.RESP_501_AFTER_EPSV_ALL,
	 * FTPCommand.PASV)); return; }
	 * 
	 * this.dataHandler.setPassive(true); int port; try { port =
	 * this.dataHandler.listen(); } catch (final IOException e) {
	 * logger.warn(CLASS_NAME, "handlePasv", e.getMessage()); throw new
	 * CommandException(ResponseCode.SYNTAX_ERROR, "Exception during server
	 * socket creation"); } // send connection info to client final String
	 * addrStr = clientSocket.getLocalAddress().getHostAddress() .replace('.',
	 * ',') + ',' + (port >> 8) + ',' + (port & 0xFF);
	 * 
	 * reply(ResponseCode.ENTERING_PASSIVE_MODE, Messages.getString(
	 * MessageKeys.RESP_227_PASV_MODE, addrStr)); }
	 * 
	 *//**
		 * Handle TYPE command.
		 * 
		 * @param st
		 *            the string tokenizer.
		 */
	/*
	 * private void handleType(final StringTokenizer st) { final String arg =
	 * st.nextToken().toUpperCase(Locale.getDefault()); if (arg.length() != 1) {
	 * reply(ResponseCode.SYNTAX_ERROR, "TYPE: invalid argument '" + arg + "'");
	 * return; } final char code = arg.charAt(0); if (Representation.CHAR_ASCII !=
	 * code && Representation.CHAR_BINARY != code) {
	 * reply(ResponseCode.SYNTAX_ERROR, "TYPE: invalid argument '" + arg + "'");
	 * return; }
	 * 
	 * if (Representation.CHAR_ASCII == code) {
	 * this.dataHandler.setRepresentation(AsciiRepresentation .getInstance()); }
	 * else { this.dataHandler.setRepresentation(BinaryRepresentation
	 * .getInstance()); } reply(ResponseCode.COMMAND_OK, Messages.getString(
	 * MessageKeys.RESP_200_TYPE_SET, arg)); }
	 * 
	 *//**
		 * Handle RETR command.
		 * 
		 * @param st
		 *            the string tokenizer.
		 * 
		 * @throws CommandException
		 *             the command exception
		 */
	/*
	 * private void handleRetr(final StringTokenizer st) throws CommandException {
	 * final String filename = getNextToken(st, "");
	 * 
	 * if ((this.userAuthInfo.isAuthMode() && !this.userAuthInfo
	 * .isLoginSuccess()) || (this.isAuthModeLoginSuccess &&
	 * this.userAuthInfo.isAdminMode() && "prnlog" .equals(filename))) {
	 * reply(ResponseCode.REQUESTED_ACTION_NOT_TAKEN_550, Messages
	 * .getString(MessageKeys.RESP_550_NO_SUCH_FILE, filename)); return; } else {
	 * final String virtualFile = this.requestHandler.getVirtualFile( filename,
	 * this.userAuthInfo); if (null == virtualFile) {
	 * reply(ResponseCode.REQUESTED_ACTION_NOT_TAKEN_550, Messages
	 * .getString(MessageKeys.RESP_550_NO_SUCH_FILE, filename)); return; } else {
	 * this.dataHandler.sendFile(filename, virtualFile); reply(226, Messages
	 * .getString(MessageKeys.RESP_226_TRANSFER_COMPLETE)); } } }
	 * 
	 *//**
		 * Handle STOR command.
		 * 
		 * @param line
		 *            the print option from the put user command.
		 * 
		 * @throws CommandException
		 *             the command exception
		 */
	/*
	 * private void handleStor(final StringTokenizer st) throws CommandException {
	 * 
	 * String newOption = getNextToken(st, "");
	 * 
	 * final FTPPrintConfigurationInfo configurationInfo = new
	 * FTPPrintConfigurationInfo(); Session session = null; // when print option
	 * has not set, use cd option if
	 * (!this.optionBuilder.checkPrintOption(newOption)) { // set DocumentName
	 * as file name configurationInfo.setDocumentName(newOption); newOption =
	 * this.cdOption; } else { configurationInfo.setDocumentName(""); newOption =
	 * this.optionBuilder.truncatePrintOption(newOption); } // get the session
	 * which is used to excute the print operation. if (isAuthModeLoginSuccess) {
	 * session = this.userAuthInfo.getLoginSession(); } else { session =
	 * this.userAuthInfo.getOperationSession(); }
	 * 
	 * if (this.userAuthInfo.isAuthMode()) { newOption =
	 * this.optionBuilder.getPrintOption(newOption); } // set the print job
	 * preference. this.setPrintConfigruationInfo(configurationInfo, newOption);
	 * 
	 * if (this.dataHandler.receiveFile(session, configurationInfo,
	 * this.userAuthInfo)) { reply(226, Messages
	 * .getString(MessageKeys.RESP_226_TRANSFER_COMPLETE)); return; }
	 * 
	 * if (!(this.userAuthInfo.isAuthMode() && this.userAuthInfo.isAdminMode())) {
	 * reply(ResponseCode.REQUESTED_ACTION_NOT_TAKEN_550, "Requested action not
	 * taken"); } }
	 * 
	 *//**
		 * Set the print configruation info.
		 * 
		 * @param configurationInfo
		 *            the print configuration info.
		 * @param superOption
		 *            the super option
		 */
	/*
	 * private void setPrintConfigruationInfo( final FTPPrintConfigurationInfo
	 * configurationInfo, final String superOption) {
	 * configurationInfo.setClientAddr(clientSocket.getInetAddress());
	 * configurationInfo.setClientUser(username); // Gets the ftp spool setting
	 * from the UP&SP ui layer.
	 * configurationInfo.setDaemonSpoolEnable(SpUpConfigManager.getInstance()
	 * .isEnableSpool()); configurationInfo.setJobName("ftpjob");
	 * configurationInfo.setJobSize(0); configurationInfo.setSpareInfo("");
	 * configurationInfo.setSuperOption(superOption);
	 * configurationInfo.setSuperOptionSize((short) superOption.length()); }
	 * 
	 */
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
	 *//**
		 * Handle PWD command.
		 */
	/*
	 * private void handlePwd() { String newOption = this.cdOption;
	 * 
	 * if (!"".equals(newOption)) { newOption =
	 * this.optionBuilder.getPrintOptionWithoutAuth(); }
	 * 
	 * reply(ResponseCode.PATHNAME_CREATED, Messages.getString(
	 * MessageKeys.RESP_257_PWD, newOption)); }
	 * 
	 *//**
		 * Handle LIST command.
		 * 
		 * @param st
		 *            the string tokenizer.
		 * 
		 * @throws CommandException
		 *             the command exception
		 */
	/*
	 * private void handleList(final StringTokenizer st) throws CommandException {
	 * String filename = "/"; if (st.hasMoreTokens()) { filename =
	 * st.nextToken(); } this.dataHandler.sendList(filename, true,
	 * this.userAuthInfo); }
	 * 
	 *//**
		 * Handle NLST command.
		 * 
		 * @param st
		 *            the string tokenizer.
		 * 
		 * @throws CommandException
		 *             the command exception
		 */
	/*
	 * private void handleNlst(final StringTokenizer st) throws CommandException {
	 * String filename = "/"; if (st.hasMoreTokens()) { filename =
	 * st.nextToken(); }
	 * 
	 * this.dataHandler.sendList(filename, false, this.userAuthInfo); } // ipv6
	 * cmd
	 */
/**
		 * Handle EPSV command.
		 * 
		 * @throws CommandException
		 *             the command exception
		 */
	/*
	 * private void handleEpsv(final StringTokenizer st) throws CommandException {
	 * final String arg = getNextToken(st, ""); if (("1".equalsIgnoreCase(arg) &&
	 * !(clientSocket.getInetAddress() instanceof Inet4Address)) ||
	 * ("2".equalsIgnoreCase(arg) && (clientSocket.getInetAddress() instanceof
	 * Inet4Address))) { reply(522, "Network protocol mismatch, use (" + arg +
	 * ")."); return; } else if ("ALL".equalsIgnoreCase(arg)) { isEpsvAll =
	 * true; reply(200, FTPCommand.EPSV + " ALL command successful."); return; }
	 * else if ("".equalsIgnoreCase(arg)) { this.dataHandler.setPassive(true);
	 * int port; try { port = this.dataHandler.listen(); } catch (final
	 * IOException e) { throw new CommandException(500, "Exception during server
	 * socket creation"); } // send connection info to client final String
	 * addrStr = "|||" + port + "|"; final String response = "Entering Extended
	 * Passive Mode (" + addrStr + ")"; reply(229, response); return; } else {
	 * reply(ResponseCode.SYNTAX_ERROR, Messages.getString(
	 * MessageKeys.RESP_500_NOT_UNDERSTOOD, FTPCommand.EPSV + " " + arg)); } }
	 * 
	 *//**
		 * Handle LPSV command.
		 * 
		 * @throws CommandException
		 *             the command exception
		 */
	/*
	 * private void handleLpsv() throws CommandException {
	 * 
	 * if (isEpsvAll) { reply(ResponseCode.REQUESTED_EPSV_ALL_501,
	 * Messages.getString( MessageKeys.RESP_501_AFTER_EPSV_ALL,
	 * FTPCommand.LPSV)); return; }
	 * 
	 * this.dataHandler.setPassive(true); int port; try { port =
	 * this.dataHandler.listen(); } catch (final IOException e) {
	 * logger.warn(CLASS_NAME, "handleLpsv", e.getMessage()); throw new
	 * CommandException(500, "Exception during server socket creation"); } //
	 * send connection info to client final InetAddress ip =
	 * this.clientSocket.getLocalAddress(); String addrStr =
	 * ip.getHostAddress().replace('.', ',') .replace(':', ',') + ",2," + (port >>
	 * 8) + ',' + (port & 0xFF); if (ip instanceof Inet4Address) { addrStr =
	 * "4,4," + addrStr; } else { addrStr = "6,16," + addrStr; } final String
	 * response = "Entering Long Passive Mode (" + addrStr + ")"; reply(228,
	 * response); }
	 * 
	 */

}