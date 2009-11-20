package cn.sh.zyh.ftpserver.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;
import cn.sh.zyh.ftpserver.impl.dataconnection.DataConnectionHandler;

public class FtpSession {
	/** A Reader for reading from the client socket. */
	private BufferedReader reader;

	/** A Writer for writing to the client socket. */
	private PrintWriter writer;

	/** Stores the username of the user. */
	private String username = null;

	/** Stores the password of the user. */
	private String password = null;

	/** Is exec "EPSV all" command. */
	private boolean isEpsvAll = false;

	/** Login FLG in the ftp service. */
	private boolean isLogin = false;

	/** The debug logger instance. */
	private final Logger logger = LoggerFactory.getLogger("FtpSession");

	/** The client we are talking to. */
	private Socket clientSocket;

	private boolean isClose = false;

	private String dataType4Trans = "A";

	private int dataPort = 0;

	private boolean isPassiveMode = false;
	
	private String currentDirectory = "/";

	/**
	 * The data transfer process responsible for transferring files to and from
	 * the user.
	 */
	private DataConnectionHandler dataHandler;

	public FtpSession(final Socket clientSock) throws IOException {
		this.clientSocket = clientSock;
		reader = new BufferedReader(new InputStreamReader(clientSocket
				.getInputStream()));
		writer = new PrintWriter(new OutputStreamWriter(clientSocket
				.getOutputStream()), true);

		dataHandler = new DataConnectionHandler();
	}

	public void cleanUser() {
		if (isLogin) {
			this.isLogin = false;
		}
		this.username = null;
		this.password = null;
	}

	public void clean() {
		cleanUser();

		// close data connection
		if (dataHandler != null) {
			this.dataHandler.abortTransfer();
		}

		try {
			if (this.clientSocket != null && !this.clientSocket.isClosed()) {
				clientSocket.shutdownInput();
			}

			// close client connection
			if (writer != null) {
				writer.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (this.clientSocket != null) {
				this.clientSocket.close();
			}
		} catch (final IOException e) {
			logger.warn("Failed in closing clientSocket");
		}
	}

	/**
	 * Writes a reply line to the client socket.
	 * 
	 * @param text
	 *            the text
	 * @param code
	 *            the code
	 */
	public void reply(final int code, final String text) {
		final String repString = code + " " + text;
		this.writer.println(repString);

		if (logger.isDebugEnabled()) {
			logger.debug("Reply to client: " + repString);
		}
	}

	/**
	 * Reply unimplemented command.
	 * 
	 * @param command
	 *            the command string.
	 */

	public void replyUnimplemented(final String command) {
		reply(ResponseCode.SYNTAX_ERROR, Messages.getString(
				MessageKeys.RESP_500_NOT_SUPPORTED, command));
	}

	public String getClientIP() {
		return clientSocket.getInetAddress().getHostAddress();
	}
	
	public String getHostIP() {
		return clientSocket.getLocalAddress().getHostAddress();
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public DataConnectionHandler getDataHandler() {
		return dataHandler;
	}

	public void setDataHandler(DataConnectionHandler dataHandler) {
		this.dataHandler = dataHandler;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public boolean isEpsvAll() {
		return isEpsvAll;
	}

	public void setEpsvAll(boolean isEpsvAll) {
		this.isEpsvAll = isEpsvAll;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void hasLogin() {
		this.isLogin = true;
	}

	public boolean isClose() {
		return isClose;
	}

	public void close() {
		this.isClose = true;
	}

	public String getDataType4Trans() {
		if("A".equals(dataType4Trans)) {
			return "ASCII";
		} else {
			return "BINARY";
		}
	}

	public void setDataType4Trans(final String dataType4Trans) {
		this.dataType4Trans = dataType4Trans;
	}

	public int getDataPort() {
		return dataPort;
	}

	public void setDataPort(int dataPort) {
		this.dataPort = dataPort;
	}

	public boolean isPassiveMode() {
		return isPassiveMode;
	}

	public void setPassiveMode(boolean isPassiveMode) {
		this.isPassiveMode = isPassiveMode;
	}

	public String getCurrentDir() {
		return currentDirectory;
	}

	public void setCurrentDir(String currentDirectory) {
		this.currentDirectory = currentDirectory;
	}
}
