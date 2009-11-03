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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sh.zyh.ftpserver.impl.core.CommandException;
import cn.sh.zyh.ftpserver.impl.core.FileList;
import cn.sh.zyh.ftpserver.impl.core.ListedFile;
import cn.sh.zyh.ftpserver.impl.representation.Representation;

/**
 * This is the server data transfer process. It is responsible for transferring
 * files to and from the client. A separate data socket is created to transfer
 * the data.
 * <p>
 * Copyright &copy; 2005 <a href="http://xjftp.sourceforge.net/">XJFTP Team</a>.
 * All rights reserved. Use is subject to <a
 * href="http://xjftp.sourceforge.net/LICENSE.TXT">licence terms</a> (<a
 * href="http://www.apache.org/licenses/LICENSE-2.0.html">Apache License v2.0</a>)<br/>
 * <p>
 * Last modified: $Date: 2005/05/22 23:17:37 $, by $Author: mmcnamee $
 * <p>
 * 
 * @author Mark McNamee (<a
 *         href="mailto:mmcnamee@users.sourceforge.net">mmcnamee at
 *         users.sourceforge.net</a>)
 * @version $Revision: 1.5 $
 */
public class DataConnectionHandler extends AbstractDataConnectionHandler {
	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = "DataConnectionHandler";

	/**
	 * The debug logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(CLASS_NAME);

	/**
	 * Opens the data connection, reads the data according to the current
	 * transmission mode, representation type and structure, and writes it into
	 * the local file "path".
	 */
	public boolean receiveFile(String path) throws CommandException {
		try {
			File file = new File(path);
			if (file.exists()) {
				// remove the old one - overwrite is ok!
				// throw new CommandException(550, "File exists in that
				// location.");
				file.delete();
			}

			connectToUser();

			this.fos = new FileOutputStream(file);

			// Read file contents.
			//
			// this.controlHandler.reply(150, "Opening "
			// + this.representation.getName() + " mode data connection.");
			receiveFile(this.dataSocket, this.fos, this.representation);
			return true;
		} catch (ConnectException e) {
			throw new CommandException(425, "Can't open data connection.");
		} catch (IOException e) {
			throw new CommandException(550, "Can't write to file");
		} finally {
			terminateOutputStream();
			terminateDataConnection();
			terminatePassiveServerSocket();
		}
	}

	/**
	 * Reads the contents of the file from "in", and writes the data to the
	 * given socket using the specified representation.
	 */
	private void sendFile(InputStream in, Socket s,
			Representation representation) throws IOException {
		OutputStream out = representation.getOutputStream(s);
		byte buf[] = new byte[4096];
		int nread;
		while ((nread = in.read(buf)) > 0) {
			out.write(buf, 0, nread);
		}
		out.close();
	}

	/**
	 * Reads data from the given socket and converts it from the specified
	 * representation to local representation, writing the result to a file via
	 * "out".
	 */
	private void receiveFile(Socket s, OutputStream out,
			Representation representation) throws IOException {
		InputStream in = representation.getInputStream(s);
		byte buf[] = new byte[4096];
		int nread;
		while ((nread = in.read(buf, 0, 4096)) > 0) {
			out.write(buf, 0, nread);
		}
		in.close();
	}

	/**
	 * Opens the data connection reads the specified local file and writes it to
	 * the data socket using the current transmission mode, representation type
	 * and structure.
	 */
	public boolean sendFile(String path) throws CommandException {
		try {
			File file = new File(path);
			if (!file.isFile()) {
				throw new CommandException(550, "Not a plain file.");
			}

			this.fis = new FileInputStream(file);

			connectToUser();

			// Send file contents.
			//
			//this.controlHandler.reply(150, "Opening "
			//		+ this.representation.getName() + " mode data connection.");
			sendFile(this.fis, this.dataSocket, this.representation);
			return true;
		} catch (FileNotFoundException e) {
			throw new CommandException(550, "No such file.");
		} catch (ConnectException e) {
			throw new CommandException(425, "Can't open data connection.");
		} catch (IOException e) {
			throw new CommandException(553, "Not a regular file.");
		} finally {
			terminateInputStream();
			terminateDataConnection();
		}
	}

	public int sendNameList(FileList fileList) throws CommandException {
		return sendList(fileList, false);
	}

	public int sendList(FileList fileList) throws CommandException {
		return sendList(fileList, true);
	}

	public int sendList(FileList fileList, boolean longFormat)
			throws CommandException {
		int reply = 0;
		try {
			int numFiles = fileList != null ? fileList.size() : 0;

			connectToUser();
			//this.representation = getNewRepresentation(Representation.TYPE_ASCII);
			PrintWriter writer = new PrintWriter(this.representation
					.getOutputStream(this.dataSocket));

			// Send long file list.
			//this.controlHandler.reply(150, "Opening "
			//		+ this.representation.getName() + " mode data connection.");

			// Print the total number of files.
			writer.print("total " + numFiles + "\n");

			if (fileList != null) {
				Iterator iter = fileList.iterator();
				while (iter.hasNext()) {
					ListedFile curFile = (ListedFile) iter.next();
					if (longFormat) {
						writer.write(curFile.toFtpString());
					} else {
						writer.write(curFile.toFtpNameOnlyString());
					}
				}
			}
			writer.flush();

			//reply = this.controlHandler.reply(226, "Transfer complete.");
		} catch (ConnectException e) {
			throw new CommandException(425, "Can't open data connection.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException(550, "No such directory.");
		} finally {
			terminateDataConnection();
		}
		return reply;
	}

	public void abortTransfer() throws IOException {
		this.terminateInputStream();
		this.terminateOutputStream();
		this.terminateDataConnection();
		this.terminatePassiveServerSocket();
	}

}