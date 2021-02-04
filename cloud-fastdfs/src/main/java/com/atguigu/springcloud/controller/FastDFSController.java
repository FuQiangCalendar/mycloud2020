package com.atguigu.springcloud.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.springcloud.entity.CreditorInfo;
import com.atguigu.springcloud.fastdfs.FastDFSClient;
import com.atguigu.springcloud.fastdfs.FastDFSFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/fastdfs")
@Slf4j
@Api(tags = "fastDFS操作文件")
public class FastDFSController {
	
	@PostMapping("/upload")
	@ApiOperation(value = "fastDFS--上传文件", notes = "fastDFS--上传文件")
	public @ResponseBody String upload(@RequestParam("id") Integer id, @RequestParam("fileName") MultipartFile file){
	    //原来文件上传是将文件写到本地或者远程服务器的某个目录下
	    //现在的文件上传是将文件上传到fastdfs文件服务器上
	    //1表示上传失败  0表示成功
	    int result = 1;
	    //abc.txt -->txt
	    String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1);
	    try {
	    	
	        String[] uploadArray = FastDFSClient.fileUpload(file.getBytes(),fileExt);
	        if(uploadArray != null && uploadArray.length ==2){
	            //文件上传到fastDFS成功  ,将合同文件路径更新到债权记录中
	            CreditorInfo creditorInfo = new CreditorInfo();
	            creditorInfo.setId(id);
	            creditorInfo.setGroupname(uploadArray[0]);
	            creditorInfo.setRemotefilepath(uploadArray[1]);
	            log.info(creditorInfo.toString());
	            String path=FastDFSClient.getTrackerUrl()+uploadArray[0]+ "/"+uploadArray[1];
	            log.info(path);
	            /*int updateRow = creditorService.updateCreditorInfo(creditorInfo);
	            //数据库更新成功
	            if(updateRow > 0){
	                result = 0;
	            }*/
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return "<script>window.parent.uploadOK('"+result+"')</script>";
	}
	
	@GetMapping("/download")
	@ApiOperation(value = "fastDFS--下载文件", notes = "fastDFS--下载文件")
	public ResponseEntity<byte[]> download(@RequestParam("groupname") String groupname, @RequestParam("remotefilepath") String remotefilepath){
	    //根据债权id获取 债权对象
	    /*CreditorInfo creditorInfo = creditorInfoService.getCreditorInfoById(id);
	    String extName = creditorInfo.getRemotefilepath().substring(creditorInfo.getRemotefilepath().indexOf("."));
	    byte [] fileBytes = FastDFSClient.fileDownload(creditorInfo.getGroupname(),creditorInfo.getRemotefilepath());*/
		String extName = remotefilepath.substring(remotefilepath.indexOf("."));
	    byte [] fileBytes = FastDFSClient.fileDownload(groupname, remotefilepath);

	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);//流类型
	    httpHeaders.setContentDispositionFormData("attachment",System.currentTimeMillis() + extName);

	    ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(fileBytes,httpHeaders, HttpStatus.OK);
	    return responseEntity;
	}
	
	@RequestMapping("/fileDelete")
	@ApiOperation(value = "fastDFS--删除文件", notes = "fastDFS--删除文件")
	public @ResponseBody String fileDelete(@RequestParam("groupname") String groupname, @RequestParam("remotefilepath") String remotefilepath){
	    int result = 1;
	    try {
//	        result = creditorService.deleteContract(id);
	    	/**
	         * 注意：事务控制的数据库，所以我们先对数据库进行更新，在操作FastDFS
	         * 如果操作FastDFS失败了，那么对数据库的操作回滚
	         */
	        //更新数据库债权表的合同路径及组
//	        int updateRow = creditorInfoMapper.updateConstractById(id);
//	        if(updateRow > 0){
	            //如果数据库更新成功，那么删除FastDFS上的文件
	            int num = FastDFSClient.fileDelete(groupname, remotefilepath);
	            if(num == 0){
	                //如果删除成功，那么将整个操作结果设置为0，表示成功
	                result = 0;
	            }else{
	                //如果删除FastDFS上的文件失败，我们抛出一个运行异常，回滚事务
	                throw new RuntimeException("FastDFS文件删除失败");
	            }
//	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return String.valueOf(result);
	}
}
