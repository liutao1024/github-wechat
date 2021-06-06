package org.spring.boot.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.boot.wechat.basic.CommUtil;
import org.spring.boot.wechat.dispatcher.EventDispatcher;
import org.spring.boot.wechat.dispatcher.MsgDispatcher;
import org.spring.boot.wechat.types.MsgType;
import org.spring.boot.wechat.util.WeChatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author LiuTao @Date 2021年1月14日 下午9:48:38
 * @ClassName: HomeController 
 * @Description: TODO(Describe)
 * ngrok:ba837a37f7a3fe68
 * https://www.cnblogs.com/gede/p/10906229.html
 */
@Controller
public class HomeController {
	// 打印日志工具
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	/**
	 * @Author LiuTao @Date 2021年1月13日 上午9:45:42 
	 * @Title: wechatCheck 
	 * @Description: 微信验证 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/wechatCheckOld")
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
				out.print(CommUtil.isNull(echostr)? "好的" : echostr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
	/**
	 * @Author LiuTao @Date 2021年1月16日 上午11:19:55 
	 * @Title: sayHello 
	 * @Description: TODO(Describe) 
	 * @return
	 */
	@RequestMapping(value = "/sayHello")
	@ResponseBody
	public String sayHello() {
		 return "HomeController say: Hello!";
	}
	/**
	 * @Author LiuTao @Date 2021年1月15日 上午9:30:50 
	 * @Title: doGet 
	 * @Description: TODO(Describe) 
	 * @param request
	 * @param response
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 */
	@RequestMapping(value = "/wechatCheck", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request,  HttpServletResponse response,
            @RequestParam(value = "signature", required = true)String signature,
            @RequestParam(value = "timestamp", required = true)String timestamp,
            @RequestParam(value = "nonce", required = true)String nonce,
            @RequestParam(value = "echostr", required = true)String echostr) {
		LOGGER.info("----------wechatCheck-------");
        try {
            if (WeChatUtil.checkSignature(signature, timestamp, nonce)) {
                PrintWriter out = response.getWriter();
                out.print(echostr);
                out.close();
            } else {
                LOGGER.info("这里存在非法请求！");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

	/**
	 * @Author LiuTao @Date 2021年1月15日 上午9:30:14 
	 * @Title: doPost 
	 * @Description: 接收微信端消息并做分发处理
	 */
    @RequestMapping(value = "/wechatCheck", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request,HttpServletResponse response) {
    	LOGGER.info("-------------这是post方法！---------------");
    	LOGGER.info("===================微信接口接入===================");
    	PrintWriter printWriter = null;
		try {
			Map<String, String> map = WeChatUtil.parseXml(request);
			String msgType = map.get("MsgType");
			if (msgType.equals(MsgType.REQ_MESSAGE_TYPE_EVENT)) {
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				String msgrsp = EventDispatcher.processEvent(map); // 进入事件处理
				printWriter = response.getWriter();
				printWriter.print(msgrsp);
			} else {
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				String msgrsp = MsgDispatcher.processMessage(map); // 进入消息处理
				printWriter = response.getWriter();
				printWriter.print(msgrsp);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}finally{
			printWriter.close();
		}
		
    }
    /**
     * @Author LiuTao @Date 2021年1月19日 上午11:16:37 
     * @Title: hello 
     * @Description: TODO(Describe) 
     * @return
     */
    @RequestMapping("/hello")
	public String hello(){
		return "hello";
	}
}