/**
 * @author Virginia
 *
 */
package com.maCarthy.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import browser.Chrome;
import browser.Chrome2;

@Controller
public class TestController {
//	@RequestMapping("/video")
	@ResponseBody
	public String toVideo(){
		Chrome obj = new Chrome();
		obj.init();
		String path = obj.OneVideo("http://www.xmjl-gy.com/");
    	return path;
	}
	
//	@RequestMapping("/test")
	@ResponseBody
	public String toTest(HttpServletRequest request){
		String saveVideo = request.getParameter("saveVideo");
		String chrome = request.getParameter("chrome");
		String chromeDriver = request.getParameter("chromeDriver");
		String url = request.getParameter("url");
		System.out.println(saveVideo+"\t"+chrome+"\t"+chromeDriver+"\t"+url);
		
		Chrome2 obj = new Chrome2();
		obj.init(chrome, chromeDriver);
		String path = obj.OneVideo(saveVideo, "http://www.xmjl-gy.com/");
		
		return path;
	}
}