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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * I am a list of {@link net.sourceforge.xjftp.core.ListedFile} objects. I am
 * retrieved by the list command on a {@link net.sourceforge.xjftp.api.FtpRequestHandler}.
 * <p>
 * Copyright &copy; 2005 <a href="http://xjftp.sourceforge.net/">XJFTP Team</a>.
 * All rights reserved. Use is subject to <a href="http://xjftp.sourceforge.net/LICENSE.TXT">licence terms</a> (<a href="http://www.apache.org/licenses/LICENSE-2.0.html">Apache License v2.0</a>)<br/>
 * <p>
 * Last modified: $Date: 2005/01/24 23:30:08 $, by $Author: mmcnamee $
 * <p>
 * @author Mark McNamee (<a href="mailto:mmcnamee@users.sourceforge.net">mmcnamee at users.sourceforge.net</a>)
 * @version $Revision: 1.3 $
 */
public final class FileList {

    private final List files = new ArrayList();
    
    public FileList() {
        super();
    }

    public void add(ListedFile file) {
        this.files.add(file);
    }
    
    public void addAll(Collection moreListedFiles) {
        this.files.addAll(moreListedFiles);
    }
    
    public Iterator iterator() { 
        return this.files.iterator();
    }
    
    public List getFiles() {
        return this.files;
    }
    
    public int size() {
        return this.files.size();
    }
    
    public ListedFile[] toArray() {
        return (ListedFile[]) this.files.toArray(new ListedFile[this.files.size()]);
    }
    
}
