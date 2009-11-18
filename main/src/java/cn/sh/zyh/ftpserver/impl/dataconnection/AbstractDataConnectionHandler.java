package cn.sh.zyh.ftpserver.impl.dataconnection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sh.zyh.ftpserver.api.Configuration;
import cn.sh.zyh.ftpserver.impl.utils.PortTracker;

public abstract class AbstractDataConnectionHandler {
	/** Class name. */
	private static final String CLASS_NAME = "AbstractDataConnectionHandler";

	/** The passive server socket. */
	private ServerSocket passiveServerSocket;

	/** The passive server socket. */
	protected Socket dataSocket;

	/** The flag of listening in passive mode. */
	private boolean listening = false;

	/** The debug logger instance. */
	private final Logger logger = LoggerFactory.getLogger(CLASS_NAME);

	public int listen() throws IOException {
		final int port = assignPassiveListeningPort();
		passiveServerSocket = new ServerSocket(port);
		passiveServerSocket.setSoTimeout(Configuration.TIME_OUT * 1000);
		listening = true;

		return port;
	}

	/**
	 * wait for client's connection.
	 */
	public Socket waitForClient() throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("Passive mode, awaiting data connection from client");
		}
		
//		if(passiveServerSocket == null) {
//			//TODO
//		}

		dataSocket = passiveServerSocket.accept();
		dataSocket.setKeepAlive(true);
		dataSocket.setSoTimeout(Configuration.TIME_OUT * 1000);

		if (logger.isDebugEnabled()) {
			final StringBuffer sb = new StringBuffer("C:");
			sb.append(dataSocket.getInetAddress().getHostAddress());
			sb.append(':');
			sb.append(dataSocket.getPort());
			sb.append(" to me on:");
			sb.append(dataSocket.getLocalAddress().getHostAddress());
			sb.append(':');
			sb.append(dataSocket.getLocalPort());
			logger.debug("New Passive Data Connection from " + sb.toString());
		}

		return dataSocket;
	}

	/**
	 * Connect to client.
	 * 
	 * @throws IOException
	 *             the IO exception
	 */
	public Socket connectToClient(final String dataHost, final int dataPort)
			throws IOException {
		// Connect to the FTP Client Application if the data port has been
		// specified
		if (dataPort <= 0) {
			logger.warn("Can't establish data connection: no PORT specified.");
			return null;
		}

		dataSocket = new Socket();
		dataSocket.setReuseAddress(true);
		dataSocket.connect(new InetSocketAddress(dataHost, dataPort));
		dataSocket.setKeepAlive(true);
		dataSocket.setSoTimeout(Configuration.TIME_OUT * 1000);

		logger.debug("Client: ip " + dataHost + " port " + dataHost);

		return dataSocket;
	}

	/**
	 * Terminate data connection.
	 */
	protected void closeDataConnection() {
		if (dataSocket != null) {
			if (dataSocket.isConnected()) {
				try {
					dataSocket.close();
				} catch (final IOException e) {
					logger.warn("IOException in close dataSocket.");
				}
			}
			dataSocket = null;
		}
	}

	/**
	 * Terminate passive server socket.
	 */
	protected void closePassiveServerSocket() {
		if (this.passiveServerSocket != null) {
			if (this.passiveServerSocket.isBound()
					|| !this.passiveServerSocket.isClosed()) {
				try {
					this.passiveServerSocket.close();
				} catch (final IOException e) {
					logger.warn("IOException in close Passive ServerSocket.");
				}
			}
			this.passiveServerSocket = null;
		}
	}

	/**
	 * Assign passive listening port.
	 * 
	 * @return the int
	 */
	private int assignPassiveListeningPort() {
		// If for some reason the tidy up from the last connection failed,
		// ensure it's done this time!
		if (this.listening) {
			closePassiveServerSocket();
		}
		return PortTracker.getInstance().getPort();
	}
}
