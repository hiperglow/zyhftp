package cn.sh.zyh.ftpserver.impl.command;

import java.util.List;
import java.util.StringTokenizer;

import cn.sh.zyh.ftpserver.api.Configuration;
import cn.sh.zyh.ftpserver.impl.FtpSession;
import cn.sh.zyh.ftpserver.impl.ICommand;
import cn.sh.zyh.ftpserver.impl.core.FTPCommandHelpDefine;
import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

public class HELP implements ICommand {

	public static final String COMMAND_NAME = "HELP";

	public void execute(String line, StringTokenizer st, FtpSession session) {

		final String arg = StringUtils.getNextToken(st, "");
		if ("".equals(arg)) {
			final StringBuffer sb = new StringBuffer();
			sb.append("214-The following commands are recognized (unimplemented):");
			sb.append(Configuration.NEWLINE);
			sb.append(" USER PORT TYPE MLFL MRCP DELE SYST RMD STOU");
			sb.append(Configuration.NEWLINE);
			sb.append(" PASS LPRT STRU MAIL ALLO CWD STAT XRMD SIZE");
			sb.append(Configuration.NEWLINE);
			sb.append(" ACCT EPRT MODE MSND REST XCWD HELP PWD MDTM");
			sb.append(Configuration.NEWLINE);
			sb.append(" SMNT PASV RETR MSOM RNFR LIST NOOP XPWD");
			sb.append(Configuration.NEWLINE);
			sb.append(" REIN LPSV STOR MSAM RNTO NLST MKD CDUP");
			sb.append(Configuration.NEWLINE);
			sb.append(" QUIT EPSV APPE MRSQ ABOR SITE XMKD XCUP");

			session.sendMessage(sb.toString());
			session.reply(214, "Direct comments to ftp-bugs@"
					+ session.getClientSocket().getLocalAddress().getHostName()
					+ ".");
		} else {
			handleHelpWithArgu(arg, session);
		}
	}

	private void handleHelpWithArgu(final String command, FtpSession session) {
		final FTPCommandHelpDefine ftpCommandList = new FTPCommandHelpDefine();
		final List commandList = ftpCommandList.getFtpCommandList();
		for (int i = 0; i < commandList.size(); i++) {
			final String[] arrary = (String[]) commandList.get(i);
			if (arrary[0].equalsIgnoreCase(command)
					&& ("OK").equalsIgnoreCase(arrary[1])) {
				final StringBuffer sb = new StringBuffer();
				sb.append("Syntax: ");
				sb.append(arrary[0]);
				sb.append(arrary[2]);
				session.reply(214, sb.toString());
				return;
			} else if (arrary[0].equalsIgnoreCase(command)
					&& ("NG").equalsIgnoreCase(arrary[1])) {
				final StringBuffer sb = new StringBuffer();
				sb.append(arrary[0]);
				sb.append("\t");
				sb.append(arrary[2]);
				sb.append("; unimplemented.");
				session.reply(214, sb.toString());
				return;
			} else {
				continue;
			}
		}
		session.reply(502, "Unknown command " + command.toUpperCase() + ".");
	}

}
