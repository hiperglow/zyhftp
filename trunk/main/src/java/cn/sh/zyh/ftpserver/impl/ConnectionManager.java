///////////////////////////////////////////////////////////////////
// Copyright:2009 by RICOH COMPANY, LTD. All Rights Reserved
///////////////////////////////////////////////////////////////////
package cn.sh.zyh.ftpserver.impl;

import java.net.Socket;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;
import cn.sh.zyh.ftpserver.api.Configuration;

/**
 * Manager the connection to FTP Server, and only 20 connections are allowed to
 * connect FTP Server.
 * 
 * @author
 * @version 1.0.0
 */
public class ConnectionManager {
	/**
	 * The ConnectionManager Instance.
	 */
	private static ConnectionManager instance = new ConnectionManager();

	/**
	 * The list of connections.
	 */
	private ArrayList<ConnectionHandler> connections = null;

	/**
	 * The thread pool of connections.
	 */
	private PooledExecutor executor = null;

	/**
	 * The debug logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger("ConnectionManager");

	/**
	 * The ConnectionManager constructor.
	 * 
	 */
	private ConnectionManager() {
		connections = new ArrayList<ConnectionHandler>(
				Configuration.MAX_CONNECTION_NUMBER);
		executor = new PooledExecutor();
	}

	/**
	 * Execute the connection in the thread pool.
	 * 
	 * @param clientSocket
	 *            client connection
	 */
	public void executeConnection(final Socket clientSocket) {
		try {
			executor.execute(new ConnectionHandler(clientSocket));
		} catch (final InterruptedException e) {
			logger.warn("Start connection thread failed");
		}
	}

	/**
	 * Shutdown the thread pool of connections.
	 */
	public void shutDownExecutor() {
		if (logger.isDebugEnabled()) {
			logger.debug("Close thread pool");
		}
		executor.shutdownNow();
		executor = null;
	}

	/**
	 * Get the instance of ConnectionManager.
	 * 
	 * @return the instance of ConnectionManager
	 */
	public static ConnectionManager getInstance() {
		return instance;
	}

	/**
	 * Add the connection into connection list.
	 * 
	 * @param con
	 *            connection
	 * 
	 * @return true if add connection successful
	 */
	public synchronized void addConnection(final ConnectionHandler con) {
		connections.add(con);
	}

	/**
	 * Remove the connection from connection list.
	 * 
	 * @param con
	 *            connection
	 */
	public synchronized void removeConnection(final ConnectionHandler con) {
		connections.remove(con);
	}

	/**
	 * Check if can add connections.
	 * 
	 * @return true, if can add connection
	 */
	public synchronized boolean canAddConnection() {
		return (connections.size() < Configuration.MAX_CONNECTION_NUMBER);
	}
}
