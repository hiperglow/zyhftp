package cn.sh.zyh.ftpserver.api;

import cn.sh.zyh.ftpserver.impl.Server;

/**
 * FTP Server API, to operate ftp server.
 * 
 * @author zhouyuhui
 * @version 1.0.0
 */
public class FtpServer {
	private Server server;
	
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}

	/**
	 * Start the ftp server.
	 */
	public void start() {
		server = new Server();
		server.start();
	}

	/**
	 * Stop the ftp server.
	 */
	public void stop() {
		if (null != server) {
			server.stop();
		}
	}
}
