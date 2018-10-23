package com.cn.nettyFileUpload.listener;

import java.io.File;


public class FileListenerImpl implements FileListener {
 
	public void fileChanged(File file) {
		System.out.println(" 文件 [ " + file.getName() + " ] 改变 At: "
				+ new java.util.Date());
	}
}
