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

import java.util.Date;

import cn.sh.zyh.ftpserver.api.Configuration;
import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

/**
 * I represent a single file on a server. My toString() method prints out the
 * details of me using the FTP compatible unix-style format.
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
public final class ListedFile {

	/**
	 * The file list.
	 */
	private final String[] fileList = { "help", "info", "prnlog", "stat" };

	/**
	 * The file list with admin.
	 */
	private final String[] fileListAdmin = { "help", "info", "stat" };

	/**
	 * Instantiates a new listed file.
	 * 
	 * @param authInfo
	 *            the auth info
	 */
	public ListedFile() {
	}

	/**
	 * Checks if filename is exist.
	 * 
	 * @param filename
	 *            the filename
	 * 
	 * @return true, if is exist
	 */
	public boolean isExist(final String filename) {
		String[] virtualList = null;

		virtualList = fileList;

		for (int i = 0; i < virtualList.length; i++) {
			if (virtualList[i].equals(filename)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the ftp string.
	 * 
	 * @param filename
	 *            the filename
	 * 
	 * @return the ftp string
	 */
	public String getFtpString(final String filename) {
		StringBuffer sb = new StringBuffer();
		sb.append("-r--r--r-- root root 200 ");
		sb.append(StringUtils.formatUnixFileListing(new Date()));// date
		sb.append(' ');
		final String pre = sb.toString();
		sb = new StringBuffer();
		String[] virtualList = null;

		virtualList = fileList;

		if ("/".equals(filename) || filename.startsWith("-")) {
			for (int i = 0; i < virtualList.length; i++) {
				sb.append(pre);
				sb.append(virtualList[i]);
				sb.append(Configuration.NEWLINE);
			}

		} else {
			sb.append(pre);
			sb.append(filename);
			sb.append(Configuration.NEWLINE);
		}

		return sb.toString();
	}

	/**
	 * Gets the ftp name only string.
	 * 
	 * @param filename
	 *            the filename.
	 * 
	 * @return the ftp name only string
	 */
	public String getFtpNameOnlyString(final String filename) {
		final StringBuffer sb = new StringBuffer();
		String[] virtualList = null;

		if ("/".equals(filename) || filename.startsWith("-")) {
			for (int i = 0; i < virtualList.length; i++) {
				sb.append(virtualList[i]);
				sb.append(Configuration.NEWLINE);
			}
		} else {
			sb.append(filename);
			sb.append(Configuration.NEWLINE);
		}
		return sb.toString();
	}
}
