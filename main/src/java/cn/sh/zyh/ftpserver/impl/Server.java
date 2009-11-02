package cn.sh.zyh.ftpserver.impl;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sh.zyh.ftpserver.api.Configuration;

public class Server {
	/** The ServerSocket on port 21. */
	private ServerSocket serverSocket;

	private final Logger logger = LoggerFactory.getLogger("Server");

	/**
	 * Stop FTP server.
	 * 
	 * @return true if stop FTP server successful
	 */
	public void stop() {
		try {
			if (this.serverSocket != null) {
				this.serverSocket.close();
			}
		} catch (final IOException e) {
		}
	}

	/**
	 * Run FTP server, waiting for connections from client.
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void start() {
		try {
			// create ServerSocket for port 21
			this.serverSocket = new ServerSocket(Configuration.PORT);

			// listen port 21 and create connection
			while (true) {
				final Socket clientSocket = this.serverSocket.accept();

				if (!checkConnection(clientSocket)) {
					logger.debug("Current connections > 20.");

					clientSocket.close();
					continue;
				}

				handleClient(clientSocket);
			}
		} catch (final BindException e) {
			logger.error("FTPServer Cannot bind to port " + Configuration.PORT);
		} catch (final IOException e) {
			logger.error("Socket IOException in accept().");
		} finally {
			// shut down thread pool
			ConnectionManager.getInstance().shutDownExecutor();

			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (final IOException e) {
					logger.error("IOException in close serverSocket.");
				}
			}
		}
	}

	/**
	 * Check if can add connection.
	 * 
	 * @param clientSocket
	 *            the client socket
	 * 
	 * @return true, if check connection successful
	 */
	private boolean checkConnection(final Socket clientSocket) {
		// if connection can not be added, close clientSocket
		if (!ConnectionManager.getInstance().canAddConnection()) {
			return false;
		}
		return true;
	}

	/**
	 * Start the client connection.
	 * 
	 * @param clientSocket
	 *            the client connection
	 */
	private void handleClient(final Socket clientSocket) {
		// if (logger.isDebugEnabled()) {
		final StringBuffer sb = new StringBuffer("Client ");
		sb.append(clientSocket.getInetAddress().getHostAddress());
		sb.append(':');
		sb.append(clientSocket.getPort());
		sb.append(" connected.");
		// clientSocket.getLocalAddress().getHostAddress()
		// clientSocket.getLocalPort()
		logger.debug(sb.toString());
		// }

		// start connection
		ConnectionManager.getInstance().executeConnection(clientSocket);
	}
}