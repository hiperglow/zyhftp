package cn.sh.zyh.ftpserver.api;

/**
 * Configuration of ftp server.
 * 
 * @author zhouyuhui
 * @version 1.0.0
 */
public class Configuration {

	/** The FTP Server Name. */
	public final static String SERVER_NAME = "ZYH FTP server 1.0.0";

	/** The length for time out. */
	public final static int TIME_OUT = 300;

	/** The size of upload bufffer. */
	public final static int BUFFER_SIZE = 4096;

	/** The maximum connectionsnumber. */
	public final static int MAX_CONNECTION_NUMBER = 20;

	/** The port of FTP server for user connection. */
	public final static int PORT = 21;

	/** The port of FTP server for data connection. */
	public final static int LOCAL_PORT = 20;

	/** The minimum port for passive mode. */
	public final static int MIN_DATA_PORT = 1025;

	/** The maximum port for passive mode. */
	public final static int MAX_DATA_PORT = 65535;

	/** The new line constant. */
	public final static String NEWLINE = "\n";

	/**
	 * The internal constructor, do not use directly. Use method getInstance
	 * instead.
	 */
	private Configuration() {
	}
}
