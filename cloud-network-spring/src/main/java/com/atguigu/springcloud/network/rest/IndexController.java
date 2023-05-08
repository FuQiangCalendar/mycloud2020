package com.atguigu.springcloud.network.rest;

import cn.hutool.core.util.RandomUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {
 
 @GetMapping("/index")
 public ModelAndView index(){
  ModelAndView mav=new ModelAndView("socket");
  mav.addObject("uid", RandomUtil.randomNumbers(6));
  return mav;
 }

 @GetMapping("/indexImg")
 public ModelAndView indexImg(){
  ModelAndView mav=new ModelAndView("indexImg");
  mav.addObject("uid", RandomUtil.randomNumbers(6));
  return mav;
 }
 
}