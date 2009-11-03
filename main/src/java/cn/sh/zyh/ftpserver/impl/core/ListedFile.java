/*
 * Copyright 2005 XJFTP Team (http://xjftp.sourceforge.net/)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.sh.zyh.ftpserver.impl.core;

import java.util.Date;

import cn.sh.zyh.ftpserver.impl.utils.StringUtils;

/**
 * I represent a single file on a server. My toString() method
 * prints out the details of me using the FTP compatible unix-style format.
 * <p>
 * Copyright &copy; 2005 <a href="http://xjftp.sourceforge.net/">XJFTP Team</a>.
 * All rights reserved. Use is subject to <a href="http://xjftp.sourceforge.net/LICENSE.TXT">licence terms</a> (<a href="http://www.apache.org/licenses/LICENSE-2.0.html">Apache License v2.0</a>)<br/>
 * <p>
 * Last modified: $Date: 2005/01/21 00:03:30 $, by $Author: mmcnamee $
 * <p>
 * @author Mark McNamee (<a href="mailto:mmcnamee@users.sourceforge.net">mmcnamee at users.sourceforge.net</a>)
 * @version $Revision: 1.2 $
 */
public final class ListedFile {

    // If we send \n (Unix format) then the FTP client expects the listing 
    // to be in UNIX format also, which works nicely!!
    public final static String NEWLINE  = "\n";

    //"-rw-rw-rw-   1 user     group           0 000 00 00:00 INVOICE-11223344.xml"
    
    private final String permissions;
    private final boolean isFile;
    private final String user;
    private final String group;
    private final String length;
    private final String date;
    private final String name;
    
    public ListedFile(String name) {
        this(null, true, "user", "group", "1", null, name);
    }
    
    public ListedFile(String name, String length) {
        this(null, true, "user", "group", length, null, name);
    }
    
    public ListedFile(final String permissions, final String name, final boolean isFile, final String user, final String group, final String length, final Date date) {
        this(permissions, isFile, user, group, length, StringUtils.formatUnixFileListing(date), name);
    }
    
    public ListedFile(final String permissions, final boolean isFile, final String user, final String group, final String length, final String date, final String name) {
        super();
        
        if (permissions == null || permissions.length() != 9) {
            this.permissions = "rw-------";
        } else {
            this.permissions = permissions;
        }
        
        this.isFile = isFile;
        this.user = user;
        this.group = group;
        this.length = length;
        
        if (date == null || date.length() != 12) {
            this.date = StringUtils.formatUnixFileListing(new Date());
        } else {
            this.date = date;
        }
        
        this.name = name;
    }

    public String getDate() {
        return this.date;
    }
    
    public String getGroup() {
        return this.group;
    }
    
    public boolean isFile() {
        return this.isFile;
    }
    
    public String getLength() {
        return this.length;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPermissions() {
        return this.permissions;
    }
    
    public String getUser() {
        return this.user;
    }
    
    public String toString() {
        return toFtpString();
    }

    /**
     * Return the list of this file in Unix format (fixed length delimited)
     * "-rw-rw-rw-   1 user     group           0 000 00 00:00 INVOICE-11223344.xml"
     */
    public String toFtpString() {
        StringBuffer sb = new StringBuffer();
        sb.append(isFile() ? '-' : 'd');
        sb.append(this.permissions);
        sb.append("   ");
        sb.append(isFile() ? '1' : '3');
        sb.append(' ');
        sb.append(StringUtils.pad(this.user, ' ', true, 8));
        sb.append(' ');
        sb.append(StringUtils.pad(this.group, ' ', true, 8));
        sb.append(' ');
        sb.append(StringUtils.pad(this.length, ' ', false, 8));
        sb.append(' ');
        sb.append(this.date);
        sb.append(' ');
        sb.append(this.name);
        sb.append(NEWLINE);
        return sb.toString();        
    }
    
    public String toFtpNameOnlyString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.name);
        sb.append(NEWLINE);
        return sb.toString();        
    }
    
    public String simpleToString() {
        return this.name + NEWLINE;
    }
    
}
