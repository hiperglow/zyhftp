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
import java.util.Enumeration;
import java.util.Stack;
import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.api.Configuration;

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
	 * Pad string object
	 */
	public static String pad(String src, char padChar, boolean rightPad,
			int totalLength) {

		if (src == null) {
			src = "";
		}
		int srcLength = src.length();
		if (srcLength >= totalLength) {
			return src;
		}

		int padLength = totalLength - srcLength;
		StringBuffer sb = new StringBuffer(padLength);
		for (int i = 0; i < padLength; ++i) {
			sb.append(padChar);
		}

		if (rightPad) {
			return src + sb.toString();
		}
		return sb.toString() + src;
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

	/**
	 * Creates a native absolute path from a path string sent from the client.
	 * The absolute path constructed will always be prefixed with baseDir. If
	 * ftpPath does not begin with a '/', the constructed path will also be
	 * relativee to currentDir.
	 */
	public static String createNativePath(String ftpPath, String curDir) {
		String path = null;
		if (ftpPath.charAt(0) == '/') {
			path = Configuration.ROOT_DIR + ftpPath;
		} else {
			path = Configuration.ROOT_DIR + curDir + "/" + ftpPath;
		}
		return path;
	}

	/**
	 * Resolves an FTP given by the client. Relative paths will be resolved
	 * relative to currentDir. '.' path segments will be removed, and '..' path
	 * segments will pop the previous segment of the path stack (if there is a
	 * previous segment).
	 */
	public static String resolvePath(String path, String curDir) {
		if (path.charAt(0) != '/') {
			// path = "/" + path; // mozilla etc are a bit rubbish!
			path = curDir + "/" + path;
		}

		// Stop recursing madness (../../../ etc!!)
		// Mozilla will still display ../../../ as it doesn't call to the server
		// to do PWD to work out where it is!
		if ("/../".equals(path)) {
			path = "/";
		}

		StringTokenizer pathSt = new StringTokenizer(path, "/");
		Stack<String> segments = new Stack<String>();
		while (pathSt.hasMoreTokens()) {
			String segment = pathSt.nextToken();
			if (segment.equals("..")) {
				if (!segments.empty()) {
					segments.pop();
				}
			} else if (segment.equals(".")) {
				// skip
			} else {
				segments.push(segment);
			}
		}

		StringBuffer pathBuf = new StringBuffer("/");
		Enumeration segmentsEn = segments.elements();
		while (segmentsEn.hasMoreElements()) {
			pathBuf.append(segmentsEn.nextElement());
			if (segmentsEn.hasMoreElements()) {
				pathBuf.append("/");
			}
		}

		return pathBuf.toString();
	}
}