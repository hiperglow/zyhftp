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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sh.zyh.ftpserver.impl.core.CommandException;
import cn.sh.zyh.ftpserver.impl.core.ListedFile;
import cn.sh.zyh.ftpserver.impl.core.MessageKeys;
import cn.sh.zyh.ftpserver.impl.core.Messages;
import cn.sh.zyh.ftpserver.impl.core.ResponseCode;

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
	 * 
	 * @param session
	 *            the session
	 * 
	 * @param configurationInfo
	 *            the configuration info
	 * 
	 * @param authInfo
	 *            the auth info.
	 * 
	 * @throws CommandException
	 *             the command exception
	 * @return true if the print operation is successful.
	 */
	public boolean receiveFile() throws CommandException {
		boolean response = false;
		try {
			connectToUser();

			final StringBuffer sb = new StringBuffer();
			sb.append("Opening ");
			sb.append(this.representation.getName());
			sb.append(" mode data connection");
			final String docName = new String("docname");
			if (!"".equals(docName)) {
				sb.append(" for '");
				sb.append(docName);
				sb.append("'");
			}
			sb.append(".");

			// this.controlHandler.reply(150, sb.toString());

			clientInStream = representation.getInputStream(dataSocket);

		} catch (final ConnectException e) {
			logger.warn(CLASS_NAME, "receiveFile", Messages
					.getString(MessageKeys.RESP_425_NOT_DATA_CONN));
			throw new CommandException(ResponseCode.CANT_OPEN_DATA_CONNECTION,
					Messages.getString(MessageKeys.RESP_425_NOT_DATA_CONN));
		} catch (final IOException e) {
			logger.warn(CLASS_NAME, "receiveFile", Messages
					.getString(MessageKeys.RESP_550_NOT_WRITE_TO_FILE));
			throw new CommandException(550, Messages
					.getString(MessageKeys.RESP_550_NOT_WRITE_TO_FILE));
		} finally {
			terminateClientInStream();
			terminateDataConnection();
			terminatePassiveServerSocket();
		}
		return response;
	}

	/**
	 * Opens the data connection reads the specified local file and writes it to
	 * the data socket using the current transmission mode, representation type
	 * and structure.
	 * 
	 * @param filename
	 *            the filename
	 * @param virtualFlie
	 *            the virtual flie
	 * 
	 * @throws CommandException
	 *             the command exception
	 */
	public void sendFile(final String filename, final String virtualFlie)
			throws CommandException {
		try {
			connectToUser();

			// Send file contents.
			//
			// this.controlHandler.reply(150, "Opening "
			// + this.representation.getName()
			// + " mode data connection for '" + filename + "'.");

			// TODO use ascii beacuse of txt file
			final OutputStream out = representation.getOutputStream(dataSocket);
			clientWriter = new PrintWriter(out);

			clientWriter.write(virtualFlie);
			clientWriter.flush();
		} catch (final ConnectException e) {
			logger.warn(CLASS_NAME, "sendFile", Messages
					.getString(MessageKeys.RESP_425_NOT_DATA_CONN));
			throw new CommandException(425, Messages
					.getString(MessageKeys.RESP_425_NOT_DATA_CONN));
		} catch (final IOException e) {
			logger.warn(CLASS_NAME, "sendFile", Messages
					.getString(MessageKeys.RESP_530_NOT_REGULAR_FILE)
					+ e);
			throw new CommandException(553, Messages
					.getString(MessageKeys.RESP_530_NOT_REGULAR_FILE));
		} finally {
			terminateClientWriter();
			terminateDataConnection();
		}
	}

	/**
	 * Send list.
	 * 
	 * @param filename
	 *            the filename
	 * @param longFormat
	 *            the long format
	 * @param authInfo
	 *            the auth info.
	 * 
	 * @throws CommandException
	 *             the command exception
	 */
	public void sendList(final String filename, final boolean longFormat)
			throws CommandException {
		try {
			connectToUser();
			// TODO AsciiRepresentation list
			clientWriter = new PrintWriter(representation
					.getOutputStream(this.dataSocket));

			final ListedFile listedFile = new ListedFile();

			if (!"/".equals(filename) && !filename.startsWith("-")
					&& !listedFile.isExist(filename)) {
				logger.warn(CLASS_NAME, "sendList", filename
						+ ": No such file or directory.");
				throw new CommandException(550, filename
						+ ": No such file or directory.");
			}

			// Send long file list.
			// this.controlHandler.reply(150, "Opening "
			// + this.representation.getName()
			// + " mode data connection for '" + filename + "'.");

			if (longFormat) {
				clientWriter.write(listedFile.getFtpString(filename));
			} else {
				clientWriter.write(listedFile.getFtpNameOnlyString(filename));
			}

			clientWriter.flush();

			// this.controlHandler.reply(ResponseCode.CLOSING_DATA_CONNECTION,
			// Messages.getString(MessageKeys.RESP_226_TRANSFER_COMPLETE));
		} catch (final ConnectException e) {
			logger.warn(CLASS_NAME, "sendList", Messages
					.getString(MessageKeys.RESP_425_NOT_DATA_CONN));
			throw new CommandException(425, Messages
					.getString(MessageKeys.RESP_425_NOT_DATA_CONN));
		} catch (final IOException e) {
			logger.warn(CLASS_NAME, "sendList", Messages
					.getString(MessageKeys.RESP_500_EXCEPTION));
			throw new CommandException(550, Messages
					.getString(MessageKeys.RESP_500_EXCEPTION));
		} finally {
			terminateClientWriter();
			terminateDataConnection();
		}
	}

	/**
	 * Abort transfer.
	 */
	public void abortTransfer() {
		this.terminateClientWriter();
		this.terminateClientInStream();
		this.terminateDataConnection();
		this.terminatePassiveServerSocket();
	}

}