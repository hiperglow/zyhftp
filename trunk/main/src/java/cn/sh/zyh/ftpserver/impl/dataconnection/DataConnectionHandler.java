package cn.sh.zyh.ftpserver.impl.dataconnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sh.zyh.ftpserver.api.Configuration;
import cn.sh.zyh.ftpserver.impl.representation.Representation;

public class DataConnectionHandler extends AbstractDataConnectionHandler {
	/** Class name. */
	private static final String CLASS_NAME = "DataConnectionHandler";

	/** The debug logger instance. */
	private final Logger logger = LoggerFactory.getLogger(CLASS_NAME);

	/** FileOutputStream used for writing received data to a local file! */
	protected FileOutputStream fos = null;

	/** FileInputStream used for reading local files to send to the client! */
	protected FileInputStream fis = null;

	/**
	 * Opens the data connection, reads the data according to the current
	 * transmission mode, representation type and structure, and writes it into
	 * the local file "path".
	 */
	public void receiveFile(final Representation representation, final File file)
			throws IOException {
		InputStream in = null;
		try {
			fos = new FileOutputStream(file);

			in = representation.getInputStream(dataSocket);
			byte buf[] = new byte[Configuration.BUFFER_SIZE];
			int nread;
			while ((nread = in.read(buf, 0, 4096)) > 0) {
				fos.write(buf, 0, nread);
			}
			in.close();
		} // throw new CommandException(550, "Can't write to file");
		finally {
			closeInputStream(in);
			closeOutputStream(fos);
			closeDataConnection();
			closePassiveServerSocket();
		}
	}

	public void sendData(final Representation representation, final String data) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(representation.getOutputStream(dataSocket));
			writer.write(data);
			writer.flush();
		} catch (IOException e) {
			logger.debug("IOException in send file list to client.");
		} finally {
			if (writer != null) {
				writer.close();
				writer = null;
			}
			closeDataConnection();
			closePassiveServerSocket();
		}
	}

	/**
	 * Opens the data connection reads the specified local file and writes it to
	 * the data socket using the current transmission mode, representation type
	 * and structure.
	 */
	public void sendFile(final Representation representation, final File file)
			throws IOException {
		OutputStream out = null;
		try {
			fis = new FileInputStream(file);
			out = representation.getOutputStream(dataSocket);
			byte buf[] = new byte[Configuration.BUFFER_SIZE];
			int nread;
			while ((nread = fis.read(buf)) > 0) {
				out.write(buf, 0, nread);
			}

		}
		// throw new CommandException(553, "Not a regular file.");
		finally {
			closeOutputStream(out);
			closeInputStream(fis);
			closeDataConnection();
		}
	}

	public void abortTransfer() {
		this.closeInputStream(fis);
		this.closeOutputStream(fos);
		this.closeDataConnection();
		this.closePassiveServerSocket();
	}

	/**
	 * Terminate client input stream.
	 */
	private void closeInputStream(InputStream fis) {
		if (fis != null) {
			try {
				if (dataSocket != null && !dataSocket.isClosed()) {
					dataSocket.shutdownInput();
				}
				fis.close();
				fis = null;
			} catch (final IOException e) {
				logger.warn("IOException in close InputStream.");
			}
		}
	}

	/**
	 * Terminate client writer.
	 */
	private void closeOutputStream(OutputStream fos) {
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				logger.warn("IOException in close OutputStream.");
			}
			fos = null;
		}
	}

}