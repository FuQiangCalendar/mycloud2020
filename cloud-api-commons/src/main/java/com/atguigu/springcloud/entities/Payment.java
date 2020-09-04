package com.atguigu.springcloud.entities;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author MrFu
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class Payment implements Serializable{
	
	@ApiParam("主键")
	private Long id;
	
	@ApiParam("单号")
	private String serial;
}
