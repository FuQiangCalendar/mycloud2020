package com.atguigu.springcloud.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreditorInfo {

	private Integer id;
	
	private String groupname;
	
	private String remotefilepath;
}
