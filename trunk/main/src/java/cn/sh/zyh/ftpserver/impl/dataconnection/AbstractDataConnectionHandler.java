///////////////////////////////////////////////////////////////////
// Copyright:2009 by RICOH COMPANY, LTD. All Rights Reserved
///////////////////////////////////////////////////////////////////
/*
 * Copyright 2005 XJFTP Team (http://xjftp.sourceforge.net/)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.sh.zyh.ftpserver.impl.dataconnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sh.zyh.ftpserver.api.Configuration;
import cn.sh.zyh.ftpserver.impl.ConnectionHandler;
import cn.sh.zyh.ftpserver.impl.core.CommandException;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.representation.AsciiRepresentation;
import cn.sh.zyh.ftpserver.impl.representation.Representation;
import cn.sh.zyh.ftpserver.impl.utils.PortTracker;

/**
 * I hold any common methods and member variables which will always be required
 * on a {@link cn.sh.zyh.ftpserver.impl.dataconnection.DataConnectionHandler}.
 * I can be sub-classes if you really need to override something at this level.
 * <p>
 * Copyright &copy; 2005 <a href="http://xjftp.sourceforge.net/">XJFTP Team</a>.
 * All rights reserved. Use is subject to <a
 * href="http://xjftp.sourceforge.net/LICENSE.TXT">licence terms</a> (<a
 * href="http://www.apache.org/licenses/LICENSE-2.0.html">Apache License v2.0</a>)<br/>
 * <p>
 * Last modified: $Date: 2005/05/22 23:17:38 $, by $Author: mmcnamee $
 * <p>
 * 
 * @author Mark McNamee (<a
 *         href="mailto:mmcnamee@users.sourceforge.net">mmcnamee at
 *         users.sourceforge.net</a>)
 * @version $Revision: 1.6 $
 */
public abstract class AbstractDataConnectionHandler {
	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = "AbstractDataConnectionHandler";

	/** The ServerPI that uses this DTP. */
	protected ConnectionHandler controlHandler;

	/** The host of the data socket. */
	protected String dataHost;

	/** The port of the data socket. */
	protected int dataPort = -1;

	/** The passive server socket. */
	protected ServerSocket passiveServerSocket;

	/** The data socket. */
	protected Socket dataSocket;

	/** The client writer. */
	protected OutputStream fos = null;

	/** The client in stream. */
	protected InputStream fis = null;

	/** The flag of passive mode. */
	protected boolean passive = false;

	/** The flag of listening in passive mode. */
	protected boolean listening = false;

	/**
	 * The representation being used for transmission. The initial
	 * representation type is ASCII.
	 */
	protected Representation representation = AsciiRepresentation.getInstance();

	/**
	 * The debug logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(CLASS_NAME);

	/**
	 * Sets the representation type used for transmission.
	 * 
	 * @param representation
	 *            the representation
	 */
	public void setRepresentation(final Representation representation) {
		this.representation = representation;
	}

	/**
	 * Sets the data port for an active transmission.
	 * 
	 * @param port
	 *            the port number to connect to.
	 * @param host
	 *            the host name to connect to.
	 */
	public void setDataPort(final String host, final int port) {
		this.dataHost = host;
		this.dataPort = port;
	}

	/**
	 * Sets the flag of passive mode to be true.
	 * 
	 * @param isPassive
	 *            the flag of passive mode
	 */
	public void setPassive(final boolean isPassive) {
		this.passive = isPassive;
	}

	/**
	 * Listen in passive mode.
	 * 
	 * @return the int
	 * 
	 * @throws IOException
	 *             the IO exception
	 */
	public int listen() throws IOException {
		final int port = assignPassiveListeningPort();

		if (logger.isDebugEnabled()) {
			logger.debug(CLASS_NAME, "listen",
					"Creating new ServerSocket on port " + port);
		}

		this.passiveServerSocket = new ServerSocket(port);
		this.passiveServerSocket.setSoTimeout(Configuration.TIME_OUT * 1000);
		this.listening = true;
		return port;
	}

	/**
	 * Terminate client input stream.
	 */
	protected void terminateInputStream() {
		if (this.fis != null) {
			try {
				if (this.dataSocket != null && !this.dataSocket.isClosed()) {
					this.dataSocket.shutdownInput();
				}
				this.fis.close();
			} catch (final IOException e) {
				logger.warn(CLASS_NAME, "terminateClientInStream",
						"Exception in close connection");
			}
		}
	}

	/**
	 * Terminate passive server socket.
	 */
	protected void terminatePassiveServerSocket() {
		if (this.passiveServerSocket != null) {
			if (this.passiveServerSocket.isBound()
					|| !this.passiveServerSocket.isClosed()) {
				try {
					this.passiveServerSocket.close();
				} catch (final IOException e) {
					logger.warn(CLASS_NAME, "terminatePassiveServerSocket",
							"Exception in close connection");
				}
			}
			this.passiveServerSocket = null;
		}
	}

	/**
	 * wait for client's connection.
	 */
	public void waitForClient() throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug(CLASS_NAME, "connectToUser",
					"In passive mode, awaiting data connection from client");
		}

		this.dataSocket = this.passiveServerSocket.accept();

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

		dataSocket.setKeepAlive(true);
		dataSocket.setSoTimeout(Configuration.TIME_OUT * 1000);
	}

	/**
	 * Connect to client.
	 * 
	 * @throws CommandException
	 *             the command exception
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

		final Socket dataSocket = new Socket();
		dataSocket.setReuseAddress(true);
		dataSocket.connect(new InetSocketAddress(dataHost, dataPort));
		dataSocket.setKeepAlive(true);
		dataSocket.setSoTimeout(Configuration.TIME_OUT * 1000);

		return dataSocket;
	}

	/**
	 * Terminate data connection.
	 */
	protected void terminateDataConnection() {
		if (this.dataSocket != null) {
			if (this.dataSocket.isConnected()) {
				try {
					this.dataSocket.close();
				} catch (final IOException e) {
					logger.warn(CLASS_NAME, "terminateDataConnection",
							"Exception in close connection");
				}
			}
			this.dataSocket = null;
			terminatePassiveServerSocket();
		}
	}

	/**
	 * Terminate client writer.
	 */
	protected void terminateOutputStream() {
		if (this.fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fos = null;
		}
	}

	/**
	 * Assign passive listening port.
	 * 
	 * @return the int
	 */
	protected int assignPassiveListeningPort() {
		// If for some reason the tidy up from the last connection failed,
		// ensure it's done this time!
		if (this.listening) {
			terminatePassiveServerSocket();
		}
		return PortTracker.getInstance().getPort();
	}
}
