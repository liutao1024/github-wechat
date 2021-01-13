package org.spring.boot.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("HomeController")
@RequestMapping(value = "/home/path")
public class HomeController {
	// 打印日志工具
//	public static final Logger LOGGER = Logger.getLogger(HomeController.class);

	/**
	 * @Author LiuTao @Date 2021年1月13日 上午9:45:42 
	 * @Title: wechatCheck 
	 * @Description: 微信验证 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/wechatCheck")
	public void wechatCheck(HttpServletRequest request, HttpServletResponse response) {
//		LOGGER.info("-------------------------微信接口校验-------------------------");
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
			if (checkSignature(signature, timestamp, nonce)) {
				out.print(echostr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * @Author LiuTao @Date 2021年1月12日 下午5:13:09
	 * @Title: checkSignature
	 * @Description: TODO(Describe)
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		String token = "Liutao";
		String[] arr = new String[] { token, timestamp, nonce };
		// 排序
		Arrays.sort(arr);
		// 生成字符串
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		// sha1加密
		String temp = getSha1(content.toString());
		return temp.equals(signature);
	}

	/**
	 * @Author LiuTao @Date 2021年1月12日 下午5:13:00
	 * @Title: getSha1
	 * @Description: TODO(Describe)
	 * @param str
	 * @return
	 */
	public static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}
}