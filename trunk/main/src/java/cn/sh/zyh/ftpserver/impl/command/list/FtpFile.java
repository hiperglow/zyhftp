package cn.sh.zyh.ftpserver.impl.command.list;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class FtpFile {
	private String fileName;

	private File file;

	public FtpFile(final String fileName, final File file) {
		this.fileName = fileName;
		this.file = file;
	}

	/**
	 * Get short name.
	 */
	public String getName() {
		// root - the short name will be '/'
		if (fileName.equals("/")) {
			return "/";
		}

		// strip the last '/'
		String shortName = fileName;
		int filelen = fileName.length();
		if (shortName.charAt(filelen - 1) == '/') {
			shortName = shortName.substring(0, filelen - 1);
		}

		// return from the last '/'
		int slashIndex = shortName.lastIndexOf('/');
		if (slashIndex != -1) {
			shortName = shortName.substring(slashIndex + 1);
		}
		return shortName;
	}

	/**
	 * Is a hidden file?
	 */
	public boolean isHidden() {
		return file.isHidden();
	}

	/**
	 * Is it a directory?
	 */
	public boolean isDirectory() {
		return file.isDirectory();
	}

	/**
	 * Is it a file?
	 */
	public boolean isFile() {
		return file.isFile();
	}

	/**
	 * Does this file exists?
	 */
	public boolean doesExist() {
		return file.exists();
	}

	/**
	 * Get file size.
	 */
	public long getSize() {
		return file.length();
	}

	/**
	 * Get file owner.
	 */
	public String getOwnerName() {
		return "ftp";
	}

	/**
	 * Get group name
	 */
	public String getGroupName() {
		return "ftp";
	}

	/**
	 * Get link count
	 */
	public int getLinkCount() {
		return file.isDirectory() ? 3 : 1;
	}

	/**
	 * Get last modified time.
	 */
	public long getLastModified() {
		return file.lastModified();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean setLastModified(long time) {
		return file.setLastModified(time);
	}

	/**
	 * Check read permission.
	 */
	public boolean isReadable() {
		return file.canRead();
	}

	/**
	 * Check file write permission.
	 */
	public boolean isWritable() {
		if (file.exists()) {
			return file.canWrite();
		}
		return true;
	}

	/**
	 * Has delete permission.
	 */
	public boolean isRemovable() {
		// root cannot be deleted
		if ("/".equals(fileName)) {
			return false;
		}

		if (file.exists()) {
			return file.canWrite();
		}
		return false;
	}
	
    /**
     * Get full name.
     */
    public String getAbsolutePath() {

        // strip the last '/' if necessary
        String fullName = fileName;
        int filelen = fullName.length();
        if ((filelen != 1) && (fullName.charAt(filelen - 1) == '/')) {
            fullName = fullName.substring(0, filelen - 1);
        }

        return fullName;
    }

	/**
	 * List files. If not a directory or does not exist, null will be returned.
	 */
	public List<FtpFile> listFiles() {
		// is a directory
		if (!file.isDirectory()) {
			return null;
		}

		// directory - return all the files
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}

		// make sure the files are returned in order
		Arrays.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return f1.getName().compareTo(f2.getName());
			}
		});

		// get the virtual name of the base directory
		String virtualFileStr = getAbsolutePath();
		if (virtualFileStr.charAt(virtualFileStr.length() - 1) != '/') {
			virtualFileStr += '/';
		}

		// now return all the files under the directory
		FtpFile[] virtualFiles = new FtpFile[files.length];
		for (int i = 0; i < files.length; ++i) {
			File fileObj = files[i];
			String fileName = virtualFileStr + fileObj.getName();
			virtualFiles[i] = new FtpFile(fileName, fileObj);
		}

		return Collections.unmodifiableList(Arrays.asList(virtualFiles));
	}
}
