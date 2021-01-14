package org.spring.boot.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.boot.wechat.util.WeChatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@RestController/*(value = "/home/path")*/
@Controller
public class HomeController {
	// 打印日志工具
	private Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	/**
	 * @Author LiuTao @Date 2021年1月13日 上午9:45:42 
	 * @Title: wechatCheck 
	 * @Description: 微信验证 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/wechatCheck")
	public void wechatCheck(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("-------------------------微信接口校验-------------------------");
		PrintWriter out = null;
		try {
			// 设置编码,不然接收到的消息乱码
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String signature = request.getParameter("signature");// 微信加密签名
			String timestamp = request.getParameter("timestamp");// 时间戳
			String nonce = request.getParameter("nonce");// 随机数
			String echostr = request.getParameter("echostr");// 随机字符串
			out = response.getWriter();
			if (WeChatUtil.checkSignature(signature, timestamp, nonce)) {
				out.print(echostr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
	
	@RequestMapping(value = "/sayHello")
	@ResponseBody
	public String sayHello() {
		 return "HomeController say: Hello!";
	}
}