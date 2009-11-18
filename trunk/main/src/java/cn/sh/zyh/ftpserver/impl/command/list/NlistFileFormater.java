package cn.sh.zyh.ftpserver.impl.command.list;

public class NlistFileFormater implements FileFormater {
    private final static char[] NEWLINE = { '\r', '\n' };

    public String format(FtpFile file) {
        StringBuffer sb = new StringBuffer();
        sb.append(file.getName());
        sb.append(NEWLINE);

        return sb.toString();
    }
}
