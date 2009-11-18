package cn.sh.zyh.ftpserver.impl.command.list;

import java.util.Arrays;

import cn.sh.zyh.ftpserver.impl.utils.DateUtils;

public class ListFileFormater implements FileFormater {

	private final static char DELIM = ' ';

	private final static char[] NEWLINE = { '\r', '\n' };

	/**
	 * @see FileFormater#format(FtpFile)
	 */
	public String format(FtpFile file) {
		StringBuffer sb = new StringBuffer();
		sb.append(getPermission(file));
		sb.append(DELIM);
		sb.append(DELIM);
		sb.append(DELIM);
		sb.append(String.valueOf(file.getLinkCount()));
		sb.append(DELIM);
		sb.append(file.getOwnerName());
		sb.append(DELIM);
		sb.append(file.getGroupName());
		sb.append(DELIM);
		sb.append(getLength(file));
		sb.append(DELIM);
		sb.append(getLastModified(file));
		sb.append(DELIM);
		sb.append(file.getName());
		sb.append(NEWLINE);

		return sb.toString();
	}

	/**
	 * Get size
	 */
	private String getLength(FtpFile file) {
		String initStr = "            ";
		long sz = 0;
		if (file.isFile()) {
			sz = file.getSize();
		}
		String szStr = String.valueOf(sz);
		if (szStr.length() > initStr.length()) {
			return szStr;
		}
		return initStr.substring(0, initStr.length() - szStr.length()) + szStr;
	}

	/**
	 * Get last modified date string.
	 */
	private String getLastModified(FtpFile file) {
		return DateUtils.getUnixDate(file.getLastModified());
	}

	/**
	 * Get permission string.
	 */
	private char[] getPermission(FtpFile file) {
		char permission[] = new char[10];
		Arrays.fill(permission, '-');

		permission[0] = file.isDirectory() ? 'd' : '-';
		permission[1] = file.isReadable() ? 'r' : '-';
		permission[2] = file.isWritable() ? 'w' : '-';
		permission[3] = file.isDirectory() ? 'x' : '-';
		return permission;
	}
}
