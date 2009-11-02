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
 * I hold a list of support FTP Commands. Not all of these may necessarily be
 * implemented
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
public class FTPCommand {

	/**
	 * The FTPCommand constructor.
	 */
	private FTPCommand() {
	}

	/** The FTPCommand ABOR. */
	public static final String ABOR = "ABOR";

	/** The FTPCommand ACCT. */
	public static final String ACCT = "ACCT";

	/** The FTPCommand ALLO. */
	public static final String ALLO = "ALLO";

	/** The FTPCommand APPE. */
	public static final String APPE = "APPE";

	/** The FTPCommand CDUP. */
	public static final String CDUP = "CDUP";

	/** The FTPCommand CWD. */
	public static final String CWD = "CWD";

	/** The FTPCommand DELE. */
	public static final String DELE = "DELE";

	/** The FTPCommand HELP. */
	public static final String HELP = "HELP";

	/** The FTPCommand LIST. */
	public static final String LIST = "LIST";

	/** The FTPCommand MAIL. */
	public static final String MAIL = "MAIL";

	/** The FTPCommand MDTM. */
	public static final String MDTM = "MDTM";

	/** The FTPCommand MKD. */
	public static final String MKD = "MKD";

	/** The FTPCommand MLFL. */
	public static final String MLFL = "MLFL";

	/** The FTPCommand MODE. */
	public static final String MODE = "MODE";

	/** The FTPCommand MRCP. */
	public static final String MRCP = "MRCP";

	/** The FTPCommand MRSQ. */
	public static final String MRSQ = "MRSQ";

	/** The FTPCommand MSAM. */
	public static final String MSAM = "MSAM";

	/** The FTPCommand MSND. */
	public static final String MSND = "MSND";

	/** The FTPCommand MSOM. */
	public static final String MSOM = "MSOM";

	/** The FTPCommand NLST. */
	public static final String NLST = "NLST";

	/** The FTPCommand NOOP. */
	public static final String NOOP = "NOOP";

	/** The FTPCommand PASS. */
	public static final String PASS = "PASS";

	/** The FTPCommand PASV. */
	public static final String PASV = "PASV";

	/** The FTPCommand PORT. */
	public static final String PORT = "PORT";

	/** The FTPCommand PWD. */
	public static final String PWD = "PWD";

	/** The FTPCommand QUIT. */
	public static final String QUIT = "QUIT";

	/** The FTPCommand REIN. */
	public static final String REIN = "REIN";

	/** The FTPCommand REST. */
	public static final String REST = "REST";

	/** The FTPCommand RETR. */
	public static final String RETR = "RETR";

	/** The FTPCommand RMD. */
	public static final String RMD = "RMD";

	/** The FTPCommand RNFR. */
	public static final String RNFR = "RNFR";

	/** The FTPCommand RNTO. */
	public static final String RNTO = "RNTO";

	/** The FTPCommand SITE. */
	public static final String SITE = "SITE";

	/** The FTPCommand SIZE. */
	public static final String SIZE = "SIZE";

	/** The FTPCommand STAT. */
	public static final String STAT = "STAT";

	/** The FTPCommand STOR. */
	public static final String STOR = "STOR";

	/** The FTPCommand STOU. */
	public static final String STOU = "STOU";

	/** The FTPCommand STRU. */
	public static final String STRU = "STRU";

	/** The FTPCommand SYS. */
	public static final String SYS = "SYS";

	/** The FTPCommand SYST. */
	public static final String SYST = "SYST";

	/** The FTPCommand TYPE. */
	public static final String TYPE = "TYPE";

	/** The FTPCommand USER. */
	public static final String USER = "USER";

	/** The FTPCommand XCUP. */
	public static final String XCUP = "XCUP";

	/** The FTPCommand XCWD. */
	public static final String XCWD = "XCWD";

	/** The FTPCommand XMKD. */
	public static final String XMKD = "XMKD";

	/** The FTPCommand XPWD. */
	public static final String XPWD = "XPWD";

	/** The FTPCommand XRMD. */
	public static final String XRMD = "XRMD";

	/** The FTPCommand EPRT. */
	public static final String EPRT = "EPRT";

	/** The FTPCommand LPRT. */
	public static final String COMMAND_NAME = "LPRT";

	/** The FTPCommand EPSV. */
	public static final String EPSV = "EPSV";

	/** The FTPCommand LPSV. */
	public static final String LPSV = "LPSV";

}
