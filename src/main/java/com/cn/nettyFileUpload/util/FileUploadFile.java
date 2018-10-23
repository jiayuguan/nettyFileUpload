package com.cn.nettyFileUpload.util;

import java.io.File;
import java.io.Serializable;

public class FileUploadFile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private File file ;//文件
	private String file_md5 ;//文件名
	private int startPos ;//文件起始位置
	private byte[] bytes ;//文件数组
	private int endPos ;//文件结束位置
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFile_md5() {
		return file_md5;
	}
	public void setFile_md5(String file_md5) {
		this.file_md5 = file_md5;
	}
	public int getStartPos() {
		return startPos;
	}
	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public int getEndPos() {
		return endPos;
	}
	public void setEndPos(int endPos) {
		this.endPos = endPos;
	}
}
