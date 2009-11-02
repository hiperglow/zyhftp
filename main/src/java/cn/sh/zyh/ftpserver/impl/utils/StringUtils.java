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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * I contain a load of helper methods for String manipulation, including
 * conversion to hex, replacements, capitalisation, null-safe blank checks
 * padding and many more.
 * <p>
 * Copyright &copy; 2005 <a href="http://xjftp.sourceforge.net/">XJFTP Team</a>.
 * All rights reserved. Use is subject to <a
 * href="http://xjftp.sourceforge.net/LICENSE.TXT">licence terms</a> (<a
 * href="http://www.apache.org/licenses/LICENSE-2.0.html">Apache License v2.0</a>)<br/>
 * <p>
 * Last modified: $Date: 2005/01/22 17:18:14 $, by $Author: mmcnamee $
 * <p>
 * 
 * @author Mark McNamee (<a
 *         href="mailto:mmcnamee@users.sourceforge.net">mmcnamee at
 *         users.sourceforge.net</a>)
 * @version $Revision: 1.5 $
 */
public class StringUtils {

	/**
	 * Format the Date type.
	 * 
	 * @param d
	 *            the date
	 * 
	 * @return the date string after formatted
	 */
	public static String formatUnixFileListing(final Date d) {
		final SimpleDateFormat df = new SimpleDateFormat("MMM dd HH:mm");
		return df.format(d);
	}

	/**
	 * Java 1.3 compliant method to allow occurences of one string to be
	 * replaced by another in a source string.
	 * 
	 * @param source
	 *            The source string containing zero or more occurences of the
	 *            string <code>toReplace</code>
	 * @param toReplace
	 *            the string to replace
	 * @param replacement
	 *            the replacement string to substitute in the place of
	 *            <code>toReplace</code>
	 * 
	 * @return String the source string with all occurences of
	 *         <code>toReplace</code> replaced with <code>replacement</code>
	 */
	public static String replaceAll(String source, final String toReplace,
			final String replacement) {
		if (source != null && source.indexOf(toReplace) != -1) {
			final StringBuffer sb = new StringBuffer();
			int ix = -1;
			while ((ix = source.indexOf(toReplace)) >= 0) {
				sb.append(source.substring(0, ix)).append(replacement);
				source = source.substring(ix + toReplace.length());
			}
			if (source.length() >= 1) {
				sb.append(source);
			}
			return sb.toString();
		}
		return source;
	}

	/**
	 * Replase error char.
	 * 
	 * @param source
	 *            the source
	 * 
	 * @return the string
	 */
	public static String replaseErrorChar(final String source) {
		final char[] username = source.toCharArray();
		for (int i = 0; i < source.length(); i++) {
			if (username[i] <= 0x1f) {
				username[i] = ' ';
			}
		}
		return String.valueOf(username);
	}

	/**
	 * Gets the next token.
	 * 
	 * @param st
	 *            the st
	 * @param defaultVal
	 *            the default val
	 * 
	 * @return the next token
	 */
	public static String getNextToken(final StringTokenizer st,
			final String defaultVal) {
		if (st != null && st.hasMoreTokens()) {
			return st.nextToken();
		}
		return defaultVal;
	}

	public static String formatReplyMsg(final String cmd, final String line) {
		final int index = line.indexOf(" ");
		String param = "";
		if (index != -1) {
			param = line.substring(index);
		}

		return cmd + param;
	}
}