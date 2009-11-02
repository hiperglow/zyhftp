package cn.sh.zyh.ftpserver.impl;

import java.util.StringTokenizer;

public interface ICommand {
	public void execute(final String line, final StringTokenizer st,
			final FtpSession session);
}
