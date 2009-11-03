package cn.sh.zyh.ftpserver.impl.core;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

public class RequestHandler {
	public FileList listRequested(String filePath, String filter)
			throws IOException {
		File dir = new File(filePath);

		String fileNames[] = dir.list();
		int numFiles = fileNames != null ? fileNames.length : 0;

		FileList fileList = new FileList();
		for (int i = 0; i < numFiles; i++) {
			String fileName = fileNames[i];

			File file = new File(dir, fileName);
			Date date = new Date(file.lastModified());

			long size = file.length();
			String sizeStr = Long.toString(size);
			StringUtils.pad(sizeStr, ' ', true, 8);

			fileList.add(new ListedFile(null, fileName, file.isFile(), "",
					"nogroup", sizeStr, date));
		}

		return fileList;
	}
}
