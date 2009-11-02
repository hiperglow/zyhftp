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

import java.util.Locale;
import java.util.ResourceBundle;

import cn.sh.zyh.ftpserver.impl.utils.StringUtils;


/**
 * I wrap up a {@link java.util.ResourceBundle} and allow resource values to be
 * obtained from the resource file(s).
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
 * @version $Revision: 1.4 $
 */
public class Messages {

	/** The Constant BUNDLE_NAME. */
	private static final String BUNDLE_NAME = "appresources";

	/** The Constant RESOURCE_BUNDLE. */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME, Locale.getDefault());

	/**
	 * Get the message for the corresponding key.
	 * 
	 * @param key
	 *            the message key
	 * 
	 * @return the response message
	 */
	public static String getString(final String key) {
		return RESOURCE_BUNDLE.getString(key);
	}

	/**
	 * Get the message for the corresponding key,and replace "{0}" with special
	 * message.
	 * 
	 * @param key
	 *            the message key
	 * @param para
	 *            the replacement
	 * 
	 * @return the response message
	 */
	public static String getString(final String key, final String para) {
		String text = getString(key);
		if (text.indexOf("{0}") != -1) {
			text = StringUtils.replaceAll(text, "{0}", para);
		}
		return text;
	}
}