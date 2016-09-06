/**
 * @author Virginia
 *
 */
package com.maCarthy.video.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import browser.Chrome;
import browser.Chrome2;

@Controller
@RequestMapping("/video")
public class VideoController {
	@RequestMapping("/server")
	@ResponseBody
	public String toVideo(HttpServletRequest request){
		String url = request.getParameter("url");
		Chrome obj = new Chrome();
		obj.init();
		String path = obj.OneVideo(url);
    	return path;
	}
	
	@RequestMapping("/local")
	@ResponseBody
	public String toTest(HttpServletRequest request){
		String saveVideo = request.getParameter("saveVideo");
		String chrome = request.getParameter("chrome");
		String chromeDriver = request.getParameter("chromeDriver");
		String url = request.getParameter("url");
		System.out.println(saveVideo+"\t"+chrome+"\t"+chromeDriver+"\t"+url);
		
		Chrome2 obj = new Chrome2();
		obj.init(chrome, chromeDriver);
		String path = obj.OneVideo(saveVideo, url);
		
		return path;
	}
}