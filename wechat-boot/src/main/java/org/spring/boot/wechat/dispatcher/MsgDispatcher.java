package org.spring.boot.wechat.dispatcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.boot.wechat.entity.Article;
import org.spring.boot.wechat.response.NewsMessage;
import org.spring.boot.wechat.response.TextMessage;
import org.spring.boot.wechat.util.WeChatUtil;

/**
 * @Author LiuTao @Date 2021年1月15日 下午5:59:00
 * @ClassName: MsgDispatcher 
 * @Description: 微信消息分发器
 */
public class MsgDispatcher {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgDispatcher.class);
    /**
     * @Author LiuTao @Date 2021年1月15日 下午5:59:22 
     * @Title: processMessage 
     * @Description: TODO(Describe) 
     * @param map
     * @return
     */
	public static String processMessage(Map<String, String> map) {
    	String msgType = map.get("MsgType");// 消息类型
		String openid = map.get("FromUserName"); // 用户openid
		String mpid = map.get("ToUserName"); // 公众号原始ID
        if (msgType.equals(WeChatUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
        	LOGGER.info("==============这是文本消息！==============");
        	// 普通文本消息
        	TextMessage txtmsg = new TextMessage();
        	txtmsg.setToUserName(openid);
        	txtmsg.setFromUserName(mpid);
        	txtmsg.setCreateTime(new Date().getTime());
        	txtmsg.setMsgType(WeChatUtil.RESP_MESSAGE_TYPE_TEXT);
        	txtmsg.setContent("你好,欢迎您的关注！严楚瑶");
        	return WeChatUtil.textMessageToXml(txtmsg);
        }
        if (msgType.equals(WeChatUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
        	LOGGER.info("==============这是图片消息！==============");
			NewsMessage newmsg = new NewsMessage();
			newmsg.setToUserName(openid);
			newmsg.setFromUserName(mpid);
			newmsg.setCreateTime(new Date().getTime());
			newmsg.setMsgType(WeChatUtil.RESP_MESSAGE_TYPE_NEWS);
			Article article = new Article();
			article.setTitle("图文消息1"); // 图文消息标题
			article.setDescription("这是图文消息1"); // 图文消息的描述
			article.setPicUrl("https://i.loli.net/2019/05/26/5cea3d137aa1469348.jpg"); // 图文消息图片地址
			article.setUrl("https://blog.csdn.net/loverGTO"); // 图文url链接
			List<Article> list = new ArrayList<Article>();
			list.add(article); // 这里发送的是单图文,如果需要发送多图文则在这里list中加入多个Article即可！
			newmsg.setArticleCount(list.size());
			newmsg.setArticles(list);
			return WeChatUtil.newsMessageToXml(newmsg);
        }
        if (msgType.equals(WeChatUtil.REQ_MESSAGE_TYPE_LINK)) { // 链接消息
        	LOGGER.info("==============这是链接消息！==============");
        }
        if (msgType.equals(WeChatUtil.REQ_MESSAGE_TYPE_LOCATION)) { // 位置消息
        	LOGGER.info("==============这是位置消息！==============");
        }
        if (msgType.equals(WeChatUtil.REQ_MESSAGE_TYPE_VIDEO)) { // 视频消息
        	LOGGER.info("==============这是视频消息！==============");
        }
        if (msgType.equals(WeChatUtil.REQ_MESSAGE_TYPE_VOICE)) { // 语音消息
        	LOGGER.info("==============这是语音消息！==============");
        }
        return null;
    }
}
