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
package cn.sh.zyh.ftpserver.impl.utils;

import java.io.IOException;
import java.net.ServerSocket;

import cn.sh.zyh.ftpserver.api.Configuration;


/**
 * I am responsible for assigning out data ports for FTP-DATA connections
 * (passive and active). I am not a singleton myself, but I should only be
 * accessed via the SpringFramework through the
 * {@link cn.sh.zyh.ftpserver.impl.Server#getPortTracker()}
 * method call.
 * <p>
 * Copyright &copy; 2005 <a href="http://xjftp.sourceforge.net/">XJFTP Team</a>.
 * All rights reserved. Use is subject to <a
 * href="http://xjftp.sourceforge.net/LICENSE.TXT">licence terms</a> (<a
 * href="http://www.apache.org/licenses/LICENSE-2.0.html">Apache License v2.0</a>)<br/>
 * <p>
 * Last modified: $Date: 2005/01/21 00:03:32 $, by $Author: mmcnamee $
 * <p>
 * 
 * @author Mark McNamee (<a
 *         href="mailto:mmcnamee@users.sourceforge.net">mmcnamee at
 *         users.sourceforge.net</a>)
 * @version $Revision: 1.2 $
 */
public final class PortTracker {

	/** The instance. */
	private static PortTracker instance = new PortTracker();

	/** The port. */
	private int port;

	/**
	 * The Constructor.
	 */
	private PortTracker() {
		this.port = Configuration.MIN_DATA_PORT;
	}

	/**
	 * Gets the PortTracker instance.
	 * 
	 * @return the PortTracker instance
	 */
	public static PortTracker getInstance() {
		return instance;
	}

	/**
	 * Gets the port.
	 * 
	 * @return the port
	 */
	public synchronized int getPort() {

		// Check the port is available on the local server!
		while (!available(this.port)) {
			this.port++; // keep trying the next one until you find a
			// free port!
			if (this.port > Configuration.MAX_DATA_PORT) {
				this.port = Configuration.MIN_DATA_PORT; // reset and recycle!
			}
		}
		return this.port;
	}

	/**
	 * If port available.
	 * 
	 * @param p
	 *            the port
	 * 
	 * @return true if port available
	 */
	private boolean available(final int p) {
		ServerSocket ss;
		try {
			ss = new ServerSocket(p);
			ss.close();
		} catch (final IOException e) {
			return false;
		}
		return true;
	}
}