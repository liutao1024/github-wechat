package org.spring.boot.wechat.dispatcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.boot.wechat.message.Article;
import org.spring.boot.wechat.message.Music;
import org.spring.boot.wechat.response.MusicMessage;
import org.spring.boot.wechat.response.NewsMessage;
import org.spring.boot.wechat.response.TextMessage;
import org.spring.boot.wechat.util.HttpUtils;
import org.spring.boot.wechat.util.WeChatUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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
		String content = map.get("Content");//
        if (msgType.equals(WeChatUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
        	LOGGER.info("==============这是文本消息！==============");
        	// 普通文本消息
        	TextMessage txtmsg = new TextMessage();
        	txtmsg.setToUserName(openid);
        	txtmsg.setFromUserName(mpid);
        	txtmsg.setCreateTime(new Date().getTime());
        	txtmsg.setMsgType(WeChatUtil.RESP_MESSAGE_TYPE_TEXT);
        	
        	StringBuffer sb = new StringBuffer();
			sb.append("欢迎关注史上最帅公众号：\n\n ");
			sb.append("1、歌德你好   \n\n ");
			sb.append("2、听首歌吧   \n\n ");
			sb.append("3、语音回复   \n\n ");
			sb.append("回复?调出主菜单哦哦   \n ");
			String context = sb.toString();
//        	txtmsg.setContent("你好,欢迎您的关注！严楚瑶");
        	txtmsg.setContent(context);
        	
        	if ("1".equals(content)) {
                txtmsg.setContent("歌德你好！");
            } else if ("2".equals(content)) {
                // Test test=new Test();
                // test.image();
                MusicMessage mucmsg = new MusicMessage();
                mucmsg.setToUserName(openid);
                mucmsg.setFromUserName(mpid);
                mucmsg.setCreateTime(new Date().getTime());
                mucmsg.setMsgType(WeChatUtil.RESP_MESSAGE_TYPE_MUSIC);

                Music music = new Music();
                HttpUtils util = new HttpUtils("thumb");
                String filepath = "F:\\MasterFUck\\未标题-1.jpg";
                Map<String, String> textMap = new HashMap<String, String>();
                textMap.put("name", "testname");
                Map<String, String> fileMap = new HashMap<String, String>();
                fileMap.put("userfile", filepath);
                String mediaidrs = util.formUpload(textMap, fileMap);
                System.out.println(mediaidrs);
                
               
                String mediaid =  (String)((JSONObject) JSON.parse(mediaidrs)).get("thumb_media_id");
                System.out.println(mediaid);
                music.setTitle("十年");
                music.setThumbMediaId(mediaid);
                music.setDescription("十年——陈奕迅");
                music.setMusicUrl("http://music.163.com/#/song?id=31877628");
                music.setHQMusicUrl("http://music.163.com/#/song?id=31877628");
                mucmsg.setMusic(music);
                return WeChatUtil.musicMessageToXml(mucmsg);
            } else if ("3".equals(content)) {
                txtmsg.setContent("语音回复！");
            } else if ("？".equals(content)) {
                txtmsg.setContent(context);
            } else {
                txtmsg.setContent("你好，欢迎来到gede博客！");
            }
        	
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
