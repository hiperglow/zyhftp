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
package cn.sh.zyh.ftpserver.impl.core;

/**
 * I hold a list of integer response codes so the names are logical in the
 * code!!
 * <p>
 * Copyright &copy; 2005 <a href="http://xjftp.sourceforge.net/">XJFTP Team</a>.
 * All rights reserved. Use is subject to <a
 * href="http://xjftp.sourceforge.net/LICENSE.TXT">licence terms</a> (<a
 * href="http://www.apache.org/licenses/LICENSE-2.0.html">Apache License v2.0</a>)<br/>
 * <p>
 * Last modified: $Date: 2005/01/21 00:03:30 $, by $Author: mmcnamee $
 * <p>
 * 
 * @author Mark McNamee (<a
 *         href="mailto:mmcnamee@users.sourceforge.net">mmcnamee at
 *         users.sourceforge.net</a>)
 * @version $Revision: 1.2 $
 */
public class ResponseCode {

	/**
	 * The Constructor.
	 */
	private ResponseCode() {
	}

	/**
	 * 125 Data connection already open; transfer starting.
	 */
	public static final int CONNECTION_OPEN_TRANSFER_STARTING = 125;

	/**
	 * 150 File status okay; about to open data connection.
	 */
	public static final int CODE_150_OPEN_DATA_CHANNEL = 150;

	/**
	 * 200 Command okay.
	 */
	public static final int COMMAND_OK = 200;

	/**
	 * 202 Command not implemented, superfluous at this site.
	 */
	public static final int COMMAND_UNIMPLEMENTED = 202;

	/**
	 * 220 Service ready for new user.
	 */
	public static final int SERVICE_READY = 220;

	/**
	 * Service closing control connection. Logged out if appropriate.
	 */
	public static final int CLOSING_CONTROL_CONNECTION = 221;

	/**
	 * 221 Goodbye to user.
	 */
	public static final int GOODBYE = 221;

	/**
	 * Closing data connection. Requested file action successful (for example,
	 * file transfer or file abort).
	 */
	public static final int CLOSING_DATA_CONNECTION = 226;

	/**
	 * 227 Entering Passive Mode (h1,h2,h3,h4,p1,p2).
	 */
	public static final int ENTERING_PASSIVE_MODE = 227;

	/**
	 * 230 User logged in, proceed.
	 */
	public static final int USER_LOGGED_IN_PROCEED = 230;

	/**
	 * 250 Requested file action okay, completed.
	 */
	public static final int REQUESTED_FILE_ACTION_OK = 250;

	/**
	 * 257 "PATHNAME" created.
	 */
	public static final int PATHNAME_CREATED = 257;

	/**
	 * 331 User name okay, need password.
	 */
	public static final int USER_NAME_OK = 331;

	/**
	 * 332 Need account for login.
	 */
	public static final int NEED_ACCOUNT = 332;

	/**
	 * 421 Service not available, closing control connection. This may be a
	 * reply to any command if the service knows it must shut down.
	 */
	public static final int SERVICE_NOT_AVAILABLE = 421;

	/**
	 * 425 Can't open data connection.
	 */
	public static final int CANT_OPEN_DATA_CONNECTION = 425;

	/**
	 * 426 Connection closed; transfer aborted.
	 */
	public static final int CONN_CLOSED_TRANSFER_ABORTED = 426;

	/**
	 * 450 Requested file action not taken. File unavailable (e.g., file busy).
	 */
	public static final int REQUESTED_FILE_ACTION_NOT_TAKEN = 450;

	/**
	 * 451 Requested action aborted: local error in processing.
	 */
	public static final int LOCAL_ERROR_IN_PROCESSING = 451;

	/**
	 * 452 Requested action not taken. Insufficient storage space in system.
	 */
	public static final int REQUESTED_ACTION_NOT_TAKEN_452 = 452;

	/**
	 * 500 Syntax error, command unrecognized. This may include errors such as
	 * command line too long.
	 */
	public static final int SYNTAX_ERROR = 500;

	/**
	 * 501 Syntax error in parameters or arguments.
	 */
	public static final int CODE_501_SYNTAX_ERROR = 501;
	
	/**
	 * 550 Directory not found
	 */
	public static final int CODE_550_DIR_NOT_FOUND = 550;
	
	/**
	 * 550 File not found
	 */
	public static final int CODE_550_FILE_NOT_FOUND = 550;

	/**
	 * 502 Command not implemented.
	 */
	public static final int COMMAND_NOT_IMPLEMENTED = 502;

	/**
	 * 503 Bad sequence of commands.
	 */
	public static final int BAD_SEQUENCE_OF_COMMANDS = 503;

	/**
	 * 504 Command not implemented for that parameter.
	 */
	public static final int COMMAND_NOT_IMPLEMENTED_FOR_PARAMETER = 504;

	/**
	 * 521 Directory already exists.
	 */
	public static final int DIR_ALREADY_EXISTS = 521;

	/**
	 * 530 Not logged in.
	 */
	public static final int NOT_LOGGED_IN = 530;

	/**
	 * 532 Need account for storing files.
	 */
	public static final int NEED_ACCOUNT_FOR_STORING_FILES = 532;

	/**
	 * 550 Requested action not taken. File unavailable (e.g., file not found,
	 * no access).
	 */
	public static final int REQUESTED_ACTION_NOT_TAKEN_550 = 550;

	/**
	 * 552 Requested file action aborted. Exceeded storage allocation (for
	 * current directory or dataset).
	 */
	public static final int REQUESTED_FILE_ACTION_ABORTED = 552;

	/**
	 * 553 Requested action not taken. File name not allowed.
	 */
	public static final int REQUESTED_ACTION_NOT_TAKEN_553 = 553;
	
	/**
	 * 501 The command is disallowed after EPSV ALL.
	 */
	public static final int REQUESTED_EPSV_ALL_501 = 501;

}
