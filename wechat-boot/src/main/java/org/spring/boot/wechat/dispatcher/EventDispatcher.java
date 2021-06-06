package org.spring.boot.wechat.dispatcher;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.boot.wechat.message.Image;
import org.spring.boot.wechat.response.ImageMessage;
import org.spring.boot.wechat.types.Event;
import org.spring.boot.wechat.types.Response;
import org.spring.boot.wechat.util.HttpUtils;
import org.spring.boot.wechat.util.WeChatUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @Author LiuTao @Date 2021年1月15日 下午5:58:40
 * @ClassName: EventDispatcher 
 * @Description: 微信事件分发器
 */
public class EventDispatcher {
	private static final Logger LOGGER = LoggerFactory.getLogger(EventDispatcher.class);
	/**
	 * @Author LiuTao @Date 2021年1月15日 下午5:59:32 
	 * @Title: processEvent 
	 * @Description: TODO(Describe) 
	 * @param map
	 * @return
	 */
    public static String processEvent(Map<String, String> map) {
    	String event = map.get("Event");// 微信事件类型
    	String response = null;
    	switch (event) {
		case Event.EVENT_TYPE_SUBSCRIBE:// 关注事件
        	LOGGER.info("==============这是关注事件！==============");
            String openid = map.get("FromUserName");// 用户openid
            String mpid = map.get("ToUserName");// 公众号原始ID
            // 登记关注用户列表
            ImageMessage imgmsg = new ImageMessage();
            imgmsg.setToUserName(openid);
            imgmsg.setFromUserName(mpid);
            imgmsg.setCreateTime(new Date().getTime());
            imgmsg.setMsgType(Response.image.toString());
            Image img = new Image();
            String filepath = "F:\\MasterFUck\\未标题-1.jpg";
            HttpUtils httpUtils = new HttpUtils();
            Map<String, String> textMap = new HashMap<String, String>();
            textMap.put("name", "testname");
            Map<String, String> fileMap = new HashMap<String, String>();
            fileMap.put("userfile", filepath);
            String mediaidrs = httpUtils.formUpload(textMap, fileMap);
            JSONObject jsonObject = (JSONObject)JSON.parse(mediaidrs);
    		String mediaid = jsonObject.getString("media_id");
            img.setMediaId(mediaid);
            imgmsg.setImage(img);
            response = WeChatUtil.imageMessageToXml(imgmsg);
			break;
		case Event.EVENT_TYPE_UNSUBSCRIBE:// 取消关注事件
        	LOGGER.info("==============这是取消关注事件！==============");
			break;
		case Event.EVENT_TYPE_SCAN:// 扫描二维码事件
        	LOGGER.info("==============这是扫描二维码事件！==============");
        	break;
		case Event.EVENT_TYPE_LOCATION:// 位置上报事件
        	LOGGER.info("==============这是位置上报事件！==============");
			break;
		case Event.EVENT_TYPE_CLICK:// 自定义菜单点击事件
        	LOGGER.info("==============这是自定义菜单点击事件！==============");
			break;
		case Event.EVENT_TYPE_VIEW:// 自定义菜单View事件
        	LOGGER.info("==============这是自定义菜单View事件！==============");
			break;
		default:
			break;
		}
    	return response;
    }
}
