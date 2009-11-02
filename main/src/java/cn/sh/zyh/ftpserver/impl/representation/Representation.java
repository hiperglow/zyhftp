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
package cn.sh.zyh.ftpserver.impl.representation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * I wrap up the logic for writing data to and reading data from the client
 * sockets. If the <code>MODE</code> of the session is set to ASCII (A), then
 * I will optionally perform Carriage Return/Line Feed conversions. This will
 * depend on other classes configured in Spring!
 * <p>
 * Copyright &copy; 2005 <a href="http://xjftp.sourceforge.net/">XJFTP Team</a>.
 * All rights reserved. Use is subject to <a
 * href="http://xjftp.sourceforge.net/LICENSE.TXT">licence terms</a> (<a
 * href="http://www.apache.org/licenses/LICENSE-2.0.html">Apache License v2.0</a>)<br/>
 * <p>
 * Last modified: $Date: 2005/01/22 17:18:21 $, by $Author: mmcnamee $
 * <p>
 * 
 * @author Mark McNamee (<a
 *         href="mailto:mmcnamee@users.sourceforge.net">mmcnamee at
 *         users.sourceforge.net</a>)
 * @version $Revision: 1.3 $
 */
public abstract class Representation {

	/** The ascii type constant. */
	public static final String TYPE_ASCII = "A";

	/** The binary type contant. */
	public static final String TYPE_BINARY = "I";

	/**
	 * Gets the input stream of special socket.
	 * 
	 * @param socket
	 *            the socket
	 * 
	 * @return the input stream
	 * 
	 * @throws IOException
	 *             the IO exception
	 */
	public abstract InputStream getInputStream(Socket socket)
			throws IOException;

	/**
	 * Gets the output of special stream.
	 * 
	 * @param socket
	 *            the socket
	 * 
	 * @return the output stream
	 * 
	 * @throws IOException
	 *             the IO exception
	 */
	public abstract OutputStream getOutputStream(Socket socket)
			throws IOException;

	/**
	 * Gets the name of current transfer type.
	 * 
	 * @return the name("ASCII" or "BINARY")
	 */
	public abstract String getName();

	/**
	 * The size of special file.
	 * 
	 * @param file
	 *            the file
	 * 
	 * @return the size of file
	 * 
	 * @throws IOException
	 *             the IO exception
	 */
	public abstract long sizeOf(File file) throws IOException;
}
