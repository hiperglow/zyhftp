package cn.sh.zyh.ftpserver.impl.command.list;

public interface FileFormater {

	/**
	 * Format the file
	 * 
	 * @param file
	 *            The {@link FtpFile}
	 * @return The formated string based on the {@link FtpFile}
	 */
	String format(FtpFile file);
}
