package com.cn.nettyFileUpload.listener;

import java.util.*;

import org.apache.log4j.Logger;

import com.cn.nettyFileUpload.util.FileUploadFile;
import com.cn.nettyFileUpload.worker.FileUploadClient;

import java.io.File;
import java.lang.ref.WeakReference;
 
public class FileMonitor {
 
	FileUploadFile uploadFile = new FileUploadFile();
	
    Logger logger = Logger.getLogger(FileMonitor.class);
    
	private Timer timer;
 
	private HashMap files_; 
 
	private Collection listeners; // of WeakReference(FileListener)
 
	public FileMonitor(long pollingInterval) {
		files_ = new HashMap();
		listeners = new ArrayList();
		timer = new Timer(true);
		timer.schedule(new FileMonitorNotifier(), 0, pollingInterval);
	}
 
	public void stop() {
		timer.cancel();
	}
 
	public void addFile(File file) {
		if (!files_.containsKey(file)) {
			if(file.isDirectory()) {
				for(File fileChild:file.listFiles()) {
					long modifiedTime = file.exists() ? file.lastModified() : -1;
					files_.put(fileChild, new Long(modifiedTime));
				}
			}
		}
	}
 
	public void removeFile(File file) {
		files_.remove(file);
	}
 
	public void addListener(FileListener fileListener) {
		for (Iterator i = listeners.iterator(); i.hasNext();) {
			WeakReference reference = (WeakReference) i.next();
			FileListener listener = (FileListener) reference.get();
			if (listener == fileListener)
				return;
		}
		listeners.add(new WeakReference(fileListener));
	}
 
	public void removeListener(FileListener fileListener) {
		for (Iterator i = listeners.iterator(); i.hasNext();) {
			WeakReference reference = (WeakReference) i.next();
			FileListener listener = (FileListener) reference.get();
			if (listener == fileListener) {
				i.remove();
				break;
			}
		}
	}
 
	private class FileMonitorNotifier extends TimerTask {
		public void run() {
			int port = 9991;
			
			Collection files = new ArrayList(files_.keySet());
 
			for (Iterator i = files.iterator(); i.hasNext();) {
				File file = (File) i.next();
				logger.info(file.getName());
				long lastModifiedTime = ((Long) files_.get(file)).longValue();
				long newModifiedTime = file.exists() ? file.lastModified() : -1;
 
				if (newModifiedTime != lastModifiedTime) {
 
					files_.put(file, new Long(newModifiedTime));
 
					for (Iterator j = listeners.iterator(); j.hasNext();) {
						WeakReference reference = (WeakReference) j.next();
						FileListener listener = (FileListener) reference.get();
 
						// Remove from list if the back-end object has been GC'd
						if (listener == null) {
							j.remove();
						}
						else {
							/*System.out.println();
							listener.fileChanged(file);*/
	//						File file = new File("D:\\file");// d:/source.rar,D:/2014work/apache-maven-3.5.0-bin.tar.gz
							String fileMd5 = file.getName();// 文件名
							uploadFile.setFile(file);
							uploadFile.setFile_md5(fileMd5);
							uploadFile.setStartPos(0);// 文件开始位置
							try {
								new FileUploadClient().connect(port, "127.0.0.1",uploadFile);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
 
 
}
