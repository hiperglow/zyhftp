package cn.sh.zyh.ftpserver.impl.command.list;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryLister {
	public String listFiles(final String path, final FileFormater formater) {

		StringBuffer sb = new StringBuffer();

		// get all the file objects
		List<FtpFile> files = getFiles(path);
		if (files != null) {
			sb.append(traverseFiles(files, formater));
		}

		return sb.toString();
	}

	/**
	 * Get the file list. Files will be listed in alphabetlical order.
	 */
	private List<FtpFile> getFiles(final String path) {
		List<FtpFile> files = null;
		FtpFile virtualFile = new FtpFile(path, new File(path));
		if (virtualFile.isFile()) {
			files = new ArrayList<FtpFile>();
			files.add(virtualFile);
		} else {
			files = virtualFile.listFiles();
		}
		return files;
	}

	private String traverseFiles(final List<FtpFile> files,
			final FileFormater formater) {
		StringBuffer sb = new StringBuffer();

		sb.append(traverseFiles(files, formater, true));
		sb.append(traverseFiles(files, formater, false));

		return sb.toString();
	}

	private String traverseFiles(final List<FtpFile> files,
			final FileFormater formater, boolean matchDirs) {
		StringBuffer sb = new StringBuffer();
		for (FtpFile file : files) {
			if (file == null) {
				continue;
			}

			if (file.isDirectory() == matchDirs) {
				sb.append(formater.format(file));
			}
		}

		return sb.toString();
	}
}
