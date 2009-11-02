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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.net.io.FromNetASCIIInputStream;
import org.apache.commons.net.io.ToNetASCIIOutputStream;

/**
 * This class converts data streams to and from ASCII representation.
 * 
 * New lines are represented as &quot;\r\n&quot;.
 * <p>
 * Copyright &copy; 2005 <a href="http://xjftp.sourceforge.net/">XJFTP Team</a>.
 * All rights reserved. Use is subject to <a
 * href="http://xjftp.sourceforge.net/LICENSE.TXT">licence terms</a> (<a
 * href="http://www.apache.org/licenses/LICENSE-2.0.html">Apache License v2.0</a>)<br/>
 * <p>
 * Last modified: $Date: 2005/01/21 00:03:31 $, by $Author: mmcnamee $
 * <p>
 * 
 * @author Mark McNamee (<a
 *         href="mailto:mmcnamee@users.sourceforge.net">mmcnamee at
 *         users.sourceforge.net</a>)
 * @version $Revision: 1.2 $
 */
public class AsciiRepresentation extends Representation {

	/** The instance. */
	private static Representation instance = new AsciiRepresentation();

	/**
	 * The Constructor.
	 */
	private AsciiRepresentation() {
	}

	/**
	 * Gets the instance.
	 * 
	 * @return the instance
	 */
	public static Representation getInstance() {
		return instance;
	}

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
	 * @see cn.sh.zyh.ftpserver.impl.representation.Representation#getInputStream(java.net.Socket)
	 */
	public InputStream getInputStream(final Socket socket) throws IOException {
		return new FromNetASCIIInputStream(socket.getInputStream());
	}

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
	 * @see cn.sh.zyh.ftpserver.impl.representation.Representation#getOutputStream(java.net.Socket)
	 */
	public OutputStream getOutputStream(final Socket socket) throws IOException {
		return new ToNetASCIIOutputStream(socket.getOutputStream());
	}

	/**
	 * Gets the name of current transfer type.
	 * 
	 * @return "ASCII"
	 * @see cn.sh.zyh.ftpserver.impl.representation.Representation#getName()
	 */
	public String getName() {
		return "ASCII";
	}

	/**
	 * The size of special file.
	 * 
	 * @param file
	 *            the file
	 * 
	 * @return the size of file
	 * @throws IOException
	 *             the IO exception
	 * @see cn.sh.zyh.ftpserver.impl.representation.Representation#sizeOf(java.io.File)
	 */
	public long sizeOf(final File file) throws IOException {
		final InputStream in = new FileInputStream(file);
		long size = 0;
		try {
			int c;
			while ((c = in.read()) != -1) {
				// skip \r, only count the \n in the \r\n's!
				// this might not work on MAC!!
				if (c != '\r') {
					size++;
				}
			}
		} finally {
			in.close();
		}
		return size;
	}
}
