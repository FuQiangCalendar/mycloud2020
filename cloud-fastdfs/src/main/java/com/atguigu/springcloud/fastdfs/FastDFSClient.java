/*
package com.atguigu.springcloud.fastdfs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class FastDFSClient {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(FastDFSClient.class);

	static {
		try {
			//config/fdfs_client.conf
			String filePath = new ClassPathResource("config/fdfs_client.conf").getFile().getAbsolutePath();;
			ClientGlobal.init(filePath);
		} catch (Exception e) {
			logger.error("FastDFS Client Init Fail!",e);
		}
	}
	
	//上传文件的方法
	public static String[] fileUpload(byte[] fileBytes,String fileExt){
	    String [] uploadArray = null;
	    try {
	        //1. 获取StorageClient对象
	        StorageClient storageClient = getTrackerClient();
	        //2.上传文件  第一个参数：本地文件路径 第二个参数：上传文件的后缀 第三个参数：文件信息
	        uploadArray = storageClient.upload_file(fileBytes,fileExt,null);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (MyException e) {
	        e.printStackTrace();
	    } finally {
//	        closeFastDFS();
	    }
	    return uploadArray;
	}
	
	
	*/
/**
	 * 
	 * @Title:	closeFastDFS
	 * @Description:	关闭FastDFS客户端连接
	 * @param:	
	 * @return:	void
	 * @author:	FuQiang
	 * @date:	2021年2月4日 下午1:58:55
	 * @throws
	 *//*

	public static void closeFastDFS () {
		StorageClient storageClient = null;
		
        try {
        	//1. 获取StorageClient对象
			storageClient = getTrackerClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (storageClient != null) {
				try {
					storageClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String[] upload(FastDFSFile file) {
		logger.info("File Name: " + file.getName() + "File Length:" + file.getContent().length);

		NameValuePair[] meta_list = new NameValuePair[1];
		meta_list[0] = new NameValuePair("author", file.getAuthor());

		long startTime = System.currentTimeMillis();
		String[] uploadResults = null;
		StorageClient storageClient=null;
		try {
			storageClient = getTrackerClient();
			uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
		} catch (IOException e) {
			logger.error("IO Exception when uploadind the file:" + file.getName(), e);
		} catch (Exception e) {
			logger.error("Non IO Exception when uploadind the file:" + file.getName(), e);
		}
		logger.info("upload_file time used:" + (System.currentTimeMillis() - startTime) + " ms");

		if (uploadResults == null && storageClient!=null) {
			logger.error("upload file fail, error code:" + storageClient.getErrorCode());
		}
		String groupName = uploadResults[0];
		String remoteFileName = uploadResults[1];

		logger.info("upload file successfully!!!" + "group_name:" + groupName + ", remoteFileName:" + " " + remoteFileName);
		return uploadResults;
	}

	public static FileInfo getFile(String groupName, String remoteFileName) {
		try {
			StorageClient storageClient = getTrackerClient();
			return storageClient.get_file_info(groupName, remoteFileName);
		} catch (IOException e) {
			logger.error("IO Exception: Get File from Fast DFS failed", e);
		} catch (Exception e) {
			logger.error("Non IO Exception: Get File from Fast DFS failed", e);
		}
		return null;
	}
	
	//下载文件的方法
	public static byte[] fileDownload(String group,String remoteFile){
	    byte[] fileBytes = null;
	    try {
	        //1. 获取StorageClient对象
	        StorageClient storageClient = getTrackerClient();
	        //2.下载文件 返回0表示成功，其它均表示失败
	        fileBytes = storageClient.download_file(group,remoteFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (MyException e) {
	        e.printStackTrace();
	    } finally {
	        closeFastDFS();
	    }
	    return fileBytes;
	}
	
	public static InputStream downFile(String groupName, String remoteFileName) {
		try {
			StorageClient storageClient = getTrackerClient();
			byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
			InputStream ins = new ByteArrayInputStream(fileByte);
			return ins;
		} catch (IOException e) {
			logger.error("IO Exception: Get File from Fast DFS failed", e);
		} catch (Exception e) {
			logger.error("Non IO Exception: Get File from Fast DFS failed", e);
		}
		return null;
	}
	
	//删除文件的方法
	public static int fileDelete(String group ,String remoteFile){
	    int num = 1;
	    try {
	        //1. 获取StorageClient对象
	        StorageClient storageClient = getTrackerClient();
	        //2.删除文件 返回0表示成功，其它均表示失败
	        num = storageClient.delete_file(group,remoteFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (MyException e) {
	        e.printStackTrace();
	    } finally {
	        closeFastDFS();
	    }
	    return num; 
	}
	
	public static void deleteFile(String groupName, String remoteFileName)
			throws Exception {
		StorageClient storageClient = getTrackerClient();
		int i = storageClient.delete_file(groupName, remoteFileName);
		logger.info("delete file successfully!!!" + i);
	}

	public static StorageServer[] getStoreStorages(String groupName)
			throws IOException, MyException {
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getTrackerServer();
		return trackerClient.getStoreStorages(trackerServer, groupName);
	}

	public static ServerInfo[] getFetchStorages(String groupName,
												String remoteFileName) throws IOException, MyException {
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getTrackerServer();
		return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
	}

	public static String getTrackerUrl() throws IOException {
		return "http://"+getTrackerServer().getInetSocketAddress().getHostString()+":"+ClientGlobal.getG_tracker_http_port()+"/";
	}

	private static StorageClient getTrackerClient() throws IOException {
		TrackerServer trackerServer = getTrackerServer();
		StorageClient storageClient = new StorageClient(trackerServer, null);
		return  storageClient;
	}

	private static TrackerServer getTrackerServer() throws IOException {
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getTrackerServer();
		return  trackerServer;
	}
}*/
