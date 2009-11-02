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
 * I contain key values used for looking up messages from the resources file(s).
 * This allows messages to be shared across several sections of code, and
 * refactored much more easily than if Strings are scattered throughout the
 * code.
 * <p>
 * Copyright &copy; 2005 <a href="http://xjftp.sourceforge.net/">XJFTP Team</a>.
 * All rights reserved. Use is subject to <a
 * href="http://xjftp.sourceforge.net/LICENSE.TXT">licence terms</a> (<a
 * href="http://www.apache.org/licenses/LICENSE-2.0.html">Apache License v2.0</a>)<br/>
 * <p>
 * Last modified: $Date: 2005/01/27 00:18:19 $, by $Author: mmcnamee $
 * <p>
 * 
 * @author Mark McNamee (<a
 *         href="mailto:mmcnamee@users.sourceforge.net">mmcnamee at
 *         users.sourceforge.net</a>)
 * @version $Revision: 1.1 $
 */
public class MessageKeys {

	/**
	 * The MessageKeys constructor.
	 */
	private MessageKeys() {
	}

	/** The Constant RESP_ACCESS_DENIED. */
	public static final String RESP_ACCESS_DENIED = "response.access.denied";

	/** The Constant RESP_200_NOOP_OK. */
	public static final String RESP_200_NOOP_OK = "response.200.noop.successfull";

	/** The Constant RESP_200_PORT_OK. */
	public static final String RESP_200_COMMAND_OK = "response.200.command.successful";

	/** The Constant RESP_200_TYPE_SET. */
	public static final String RESP_200_TYPE_SET = "response.200.type.set.to";

	/** The Constant RESP_215_SYST. */
	public static final String RESP_215_SYST = "response.215.syst.response";

	/** The Constant RESP_220_READY. */
	public static final String RESP_220_READY = "response.220.service.ready";

	/** The Constant RESP_221_BYE. */
	public static final String RESP_221_BYE = "response.221.goodbye";

	/** The Constant RESP_226_TRANSFER_COMPLETE. */
	public static final String RESP_226_TRANSFER_COMPLETE = "response.226.transfer.complete";

	/** The Constant RESP_226_ABORT_OK. */
	public static final String RESP_226_ABORT_OK = "response.226.abort.successful";

	/** The Constant RESP_227_PASV_MODE. */
	public static final String RESP_227_PASV_MODE = "response.227.passive.successful";

	/** The Constant RESP_230_LOGGED_IN. */
	public static final String RESP_230_LOGGED_IN = "response.230.user.logged.in";

	/** The Constant RESP_250_DELE_OK. */
	public static final String RESP_250_DELE_OK = "response.250.dele.successful";

	/** The Constant RESP_250_RMD_OK. */
	public static final String RESP_250_RMD_OK = "response.250.rmd.command.successful";

	/** The Constant RESP_257_DIR_CREATED. */
	public static final String RESP_257_DIR_CREATED = "response.257.dir.created";

	/** The Constant RESP_257_PWD. */
	public static final String RESP_257_PWD = "response.257.pwd.current.dir.echo";

	/** The Constant RESP_331_PASS_REQ. */
	public static final String RESP_331_PASS_REQ = "response.331.password.required";

	/** The Constant RESP_426_TRANSFER_CLOSED. */
	public static final String RESP_426_TRANSFER_CLOSED = "response.426.transfer.closed.at.client.request";

	/** The Constant RESP_500_NOT_UNDERSTOOD. */
	public static final String RESP_500_NOT_UNDERSTOOD = "response.500.command.not.understood";

	/** The Constant RESP_500_EXCEPTION. */
	public static final String RESP_500_EXCEPTION = "response.500.exception.calling.command";

	/** The Constant RESP_550_ERROR_LISTING. */
	public static final String RESP_550_ERROR_LISTING = "response.550.error.listing.files";

	/** The Constant RESP_501_OPTS_RESP. */
	public static final String RESP_501_OPTS_RESP = "response.501.opts.response";

	/** The Constant RESP_503_USER_FIRST. */
	public static final String RESP_503_USER_FIRST = "response.503.login.with.user.first";

	/** The Constant RESP_500_NOT_SUPPORTED. */
	public static final String RESP_500_NOT_SUPPORTED = "response.500.command.not.supported";

	/** The Constant RESP_530_NOT_LOGGED_IN. */
	public static final String RESP_530_NOT_LOGGED_IN = "response.530.command.not.logged.in";

	/** The Constant RESP_421_SESSION_FULL. */
	public static final String RESP_421_SESSION_FULL = "response.421.SESSION.FULL";

	/** The Constant RESP_550_COULD_NOT_DELE. */
	public static final String RESP_550_COULD_NOT_DELE = "response.550.could.not.dele";

	/** The Constant RESP_550_NO_SUCH_DIR. */
	public static final String RESP_550_NO_SUCH_DIR = "response.550.no.such.dir";

	/** The Constant RESP_550_NOT_A_DIR. */
	public static final String RESP_550_NOT_A_DIR = "response.550.not.a.dir";

	/** The Constant RESP_421_TIMEOUT. */
	public static final String RESP_421_TIMEOUT = "response.421.timeout";

	/** The Constant RESP_425_NOT_DATA_CONN. */
	public static final String RESP_425_NOT_DATA_CONN = "response.425.could.not.open.data.connection";

	/** The Constant RESP_550_NOT_WRITE_TO_FILE. */
	public static final String RESP_550_NOT_WRITE_TO_FILE = "response.550.could.not.write.to.file";

	/** The Constant RESP_530_NOT_REGULAR_FILE. */
	public static final String RESP_530_NOT_REGULAR_FILE = "response.530.not.regular.file";

	/** The Constant RESP_500_NOT_PORT_SPECI. */
	public static final String RESP_500_NOT_PORT_SPECI = "response.500.not.port.specified";
	
	/** The Constant RESP_500_NOT_PORT_SPECI. */
	public static final String RESP_501_AFTER_EPSV_ALL = "response.501.after.epsv.all";
	
	/** The Constant RESP_504_NOT_USE_COMMAND. */
	public static final String RESP_504_NOT_USE_COMMAND = "response.504.not.use.command";
	
	/** The Constant RESP_550_PERMISSION_DENIED. */
	public static final String RESP_550_PERMISSION_DENIED = "response.550.permission.denied";
	
	/** The Constant RESP_550_NO_SUCH_FILE. */
	public static final String RESP_550_NO_SUCH_FILE = "response.550.no.such.file";
	
}