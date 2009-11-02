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
 * This exception is thrown if there is an error handling any user command.
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
public class CommandException extends Exception {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The reply code to send to the user.
	 */
	private int code;

	/**
	 * The message to send to the user.
	 */
	private String text;

	/**
	 * The CommandException constructor.
	 * 
	 * @param code
	 *            the reply code
	 * @param text
	 *            the associated error message
	 */
	public CommandException(final int code, final String text) {
		super(code + " " + text);
		this.code = code;
		this.text = text;
	}

	/**
	 * Gets the reply code.
	 * 
	 * @return the reply code
	 */
	public int getCode() {
		return this.code;
	}

	/**
	 * Gets the reply message.
	 * 
	 * @return the reply message
	 */
	public String getText() {
		return this.text;
	}
}