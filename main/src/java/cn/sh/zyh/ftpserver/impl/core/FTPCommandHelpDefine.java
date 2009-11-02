///////////////////////////////////////////////////////////////////
// Copyright:2009 by RICOH COMPANY, LTD. All Rights Reserved
///////////////////////////////////////////////////////////////////
package cn.sh.zyh.ftpserver.impl.core;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class FtpCommandSupportDefine.
 * 
 * @author LianhaoMa
 * @version 1.0
 */
public class FTPCommandHelpDefine {

	/**
	 * The user.
	 */
	private final String[] user = new String[] { "USER", "OK", " <sp> username" };

	/**
	 * The pass.
	 */
	private final String[] pass = new String[] { "PASS", "OK", " <sp> password" };

	/**
	 * The acct.
	 */
	private final String[] acct = new String[] { "ACCT", "NG",
			" (specify account)" };

	/**
	 * The smnt.
	 */
	private final String[] smnt = new String[] { "SMNT", "NG",
			" (structure mount)" };

	/**
	 * The rein.
	 */
	private final String[] rein = new String[] { "REIN", "NG",
			" (reinitialize server state)" };

	/**
	 * The quit.
	 */
	private final String[] quit = new String[] { "QUIT", "OK",
			" (terminate service)" };

	/**
	 * The port.
	 */
	private final String[] port = new String[] { "PORT", "OK",
			" <sp> b0, b1, b2, b3, b4" };

	/**
	 * The lprt.
	 */
	private final String[] lprt = new String[] { "LPRT", "OK",
			" <sp> af, hal, h1, h2, h3,..., pal, p1, p2..." };

	/**
	 * The eprt.
	 */
	private final String[] eprt = new String[] { "EPRT", "OK",
			" <sp> |af|addr|port|" };

	/**
	 * The pasv.
	 */
	private final String[] pasv = new String[] { "PASV", "OK",
			" (set server in passive mode)" };

	/**
	 * The lpsv.
	 */
	private final String[] lpsv = new String[] { "LPSV", "OK",
			" (set server in passive mode)" };

	/**
	 * The epsv.
	 */
	private final String[] epsv = new String[] { "EPSV", "OK", " [<sp> af|ALL]" };

	/**
	 * The type.
	 */
	private final String[] type = new String[] { "TYPE", "OK",
			" <sp> [ A | I ]" };

	/**
	 * The stru.
	 */
	private final String[] stru = new String[] { "STRU", "NG",
			" (specify file structure)" };

	/**
	 * The mode.
	 */
	private final String[] mode = new String[] { "MODE", "NG",
			" (specify transfer mode)" };

	/**
	 * The retr.
	 */
	private final String[] retr = new String[] { "RETR", "OK",
			" <sp> file-name" };

	/**
	 * The stor.
	 */
	private final String[] stor = new String[] { "STOR", "OK",
			" <sp> file-name" };

	/**
	 * The appe.
	 */
	private final String[] appe = new String[] { "APPE", "OK",
			" <sp> file-name" };

	/**
	 * The mlfl.
	 */
	private final String[] mlfl = new String[] { "MLFL", "NG", " (mail file)" };

	/**
	 * The mail.
	 */
	private final String[] mail = new String[] { "MAIL", "NG",
			" (mail to user)" };

	/**
	 * The msnd.
	 */
	private final String[] msnd = new String[] { "MSND", "NG",
			" (mail send to terminal)" };

	/**
	 * The msom.
	 */
	private final String[] msom = new String[] { "MSOM", "NG",
			" (mail send to terminal or mailbox)" };

	/**
	 * The msam.
	 */
	private final String[] msam = new String[] { "MSAM", "NG",
			" (mail send to terminal or mailbox)" };

	/**
	 * The mrsq.
	 */
	private final String[] mrsq = new String[] { "MRSQ", "NG",
			" (mail recipient scheme question)" };

	/**
	 * The mrcp.
	 */
	private final String[] mrcp = new String[] { "MRCP", "NG",
			" (mail recipient)" };

	/**
	 * The allo.
	 */
	private final String[] allo = new String[] { "ALLO", "NG",
			" allocate storage (vacuously)" };

	/**
	 * The rest.
	 */
	private final String[] rest = new String[] { "REST", "NG",
			" <sp> offset (restart command)" };

	/**
	 * The rnfr.
	 */
	private final String[] rnfr = new String[] { "RNFR", "NG",
			" <sp> file-name" };

	/**
	 * The rnto.
	 */
	private final String[] rnto = new String[] { "RNTO", "NG",
			" <sp> file-name" };

	/**
	 * The abor.
	 */
	private final String[] abor = new String[] { "ABOR", "OK",
			" (abort operation)" };

	/**
	 * The dele.
	 */
	private final String[] dele = new String[] { "DELE", "NG",
			" <sp> file-name" };

	/**
	 * The cwd.
	 */
	private final String[] cwd = new String[] { "CWD", "OK",
			" [ <sp> directory-name ]" };

	/**
	 * The xcwd.
	 */
	private final String[] xcwd = new String[] { "XCWD", "OK",
			" [ <sp> directory-name ]" };

	/**
	 * The list.
	 */
	private final String[] list = new String[] { "LIST", "OK",
			" [ <sp> path-name ]" };

	/**
	 * The nlst.
	 */
	private final String[] nlst = new String[] { "NLST", "OK",
			" [ <sp> path-name ]" };

	/**
	 * The site.
	 */
	private final String[] site = new String[] { "SITE", "NG",
			" site-cmd [ <sp> arguments ]" };

	/**
	 * The syst.
	 */
	private final String[] syst = new String[] { "SYST", "NG",
			" (get type of operating system)" };

	/**
	 * The stat.
	 */
	private final String[] stat = new String[] { "STAT", "NG",
			" [ <sp> path-name ]" };

	/**
	 * The help.
	 */
	private final String[] help = new String[] { "HELP", "OK",
			" [ <sp> <string> ]" };

	/**
	 * The noop.
	 */
	private final String[] noop = new String[] { "NOOP", "OK", "" };

	/**
	 * The mkd.
	 */
	private final String[] mkd = new String[] { "MKD", "NG",
			" [ <sp> path-name ]" };

	/**
	 * The xmkd.
	 */
	private final String[] xmkd = new String[] { "XMKD", "NG",
			" [ <sp> path-name ]" };

	/**
	 * The rmd.
	 */
	private final String[] rmd = new String[] { "RMD", "NG",
			" [ <sp> path-name ]" };

	/**
	 * The xrmd.
	 */
	private final String[] xrmd = new String[] { "XRMD", "NG",
			" [ <sp> path-name ]" };

	/**
	 * The pwd.
	 */
	private final String[] pwd = new String[] { "PWD", "OK",
			" (return current directory)" };

	/**
	 * The xpwd.
	 */
	private final String[] xpwd = new String[] { "XPWD", "NG",
			" (return current directory)" };

	/**
	 * The cdup.
	 */
	private final String[] cdup = new String[] { "CDUP", "OK",
			" (change to parent directory)" };

	/**
	 * The xcup.
	 */
	private final String[] xcup = new String[] { "XCUP", "NG",
			" (change to parent directory)" };

	/**
	 * The stou.
	 */
	private final String[] stou = new String[] { "STOU", "OK",
			" <sp> file-name" };

	/**
	 * The size.
	 */
	private final String[] size = new String[] { "SIZE", "NG",
			" <sp> file-name" };

	/**
	 * The mdtm.
	 */
	private final String[] mdtm = new String[] { "MDTM", "NG",
			" <sp> file-name" };

	/**
	 * The ftp command list.
	 */
	private List ftpCommandList = new ArrayList();

	/**
	 * Gets the ftp command list.
	 * 
	 * @return the ftp command list
	 */
	public List getFtpCommandList() {
		ftpCommandList.add(user);
		ftpCommandList.add(pass);
		ftpCommandList.add(acct);
		ftpCommandList.add(smnt);
		ftpCommandList.add(rein);
		ftpCommandList.add(quit);
		ftpCommandList.add(port);
		ftpCommandList.add(lprt);
		ftpCommandList.add(eprt);
		ftpCommandList.add(pasv);
		ftpCommandList.add(lpsv);
		ftpCommandList.add(epsv);
		ftpCommandList.add(type);
		ftpCommandList.add(stru);
		ftpCommandList.add(mode);
		ftpCommandList.add(retr);
		ftpCommandList.add(stor);
		ftpCommandList.add(appe);
		ftpCommandList.add(mlfl);
		ftpCommandList.add(mail);
		ftpCommandList.add(msnd);
		ftpCommandList.add(msom);
		ftpCommandList.add(msam);
		ftpCommandList.add(mrsq);
		ftpCommandList.add(mrcp);
		ftpCommandList.add(allo);
		ftpCommandList.add(rest);
		ftpCommandList.add(rnfr);
		ftpCommandList.add(rnto);
		ftpCommandList.add(abor);
		ftpCommandList.add(dele);
		ftpCommandList.add(cwd);
		ftpCommandList.add(xcwd);
		ftpCommandList.add(list);
		ftpCommandList.add(nlst);
		ftpCommandList.add(site);
		ftpCommandList.add(syst);
		ftpCommandList.add(stat);
		ftpCommandList.add(help);
		ftpCommandList.add(noop);
		ftpCommandList.add(mkd);
		ftpCommandList.add(rmd);
		ftpCommandList.add(xrmd);
		ftpCommandList.add(pwd);
		ftpCommandList.add(xpwd);
		ftpCommandList.add(cdup);
		ftpCommandList.add(xcup);
		ftpCommandList.add(stou);
		ftpCommandList.add(xmkd);
		ftpCommandList.add(size);
		ftpCommandList.add(mdtm);
		return ftpCommandList;
	}

}
