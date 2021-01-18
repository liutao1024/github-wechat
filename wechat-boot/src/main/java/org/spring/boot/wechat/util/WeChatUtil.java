package org.spring.boot.wechat.util;

import java.io.InputStream;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.spring.boot.wechat.message.Article;
import org.spring.boot.wechat.response.ImageMessage;
import org.spring.boot.wechat.response.MusicMessage;
import org.spring.boot.wechat.response.NewsMessage;
import org.spring.boot.wechat.response.TextMessage;
import org.spring.boot.wechat.response.VideoMessage;
import org.spring.boot.wechat.response.VoiceMessage;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @Author LiuTao @Date 2021年1月15日 上午9:21:43
 * @ClassName: WeChatUtil
 * @Description: TODO(Describe)
 */
public class WeChatUtil {
	// 与接口配置信息中的 Token要一致
	private static String token = "Liutao";

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
		String[] arr = new String[] {token, timestamp, nonce};
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
	 * @Description: 散列加密
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

	/**
	 * @Author LiuTao @Date 2021年1月15日 上午9:28:21
	 * @Title: checkSignature2
	 * @Description: 验证签名
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature2(String signature, String timestamp, String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将 token,timestamp,nonce 三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行 sha1 加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		content = null;
		// 将 sha1 加密后的字符串可与 signature 对比,标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * @Author LiuTao @Date 2021年1月15日 上午9:28:05
	 * @Title: byteToStr
	 * @Description: 将字节数组转换为十六进制字符串
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * @Author LiuTao @Date 2021年1月15日 上午9:27:53
	 * @Title: byteToHexStr
	 * @Description: 将字节转换为十六进制字符串
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}

	/************************************ 微信消息处理 **********************************************/
	/**
	 * 返回消息类型:文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型:音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型:图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 返回消息类型:图片
	 */
	public static final String RESP_MESSAGE_TYPE_Image = "image";

	/**
	 * 返回消息类型:语音
	 */
	public static final String RESP_MESSAGE_TYPE_Voice = "voice";

	/**
	 * 返回消息类型:视频
	 */
	public static final String RESP_MESSAGE_TYPE_Video = "video";

	/**
	 * 请求消息类型:文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型:图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型:链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型:地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型:音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型:视频
	 */
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

	/**
	 * 请求消息类型:推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型:subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型:unSubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	
	/**
	 * 事件类型:scan(扫描二维码)
	 */
	public static final String EVENT_TYPE_SCAN = "SCAN";
	
	/**
	 * 事件类型:CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 事件类型:VIEW(自定义菜单 URl 视图)
	 */
	public static final String EVENT_TYPE_VIEW = "VIEW";

	/**
	 * 事件类型:LOCATION(上报地理位置事件)
	 */
	public static final String EVENT_TYPE_LOCATION = "LOCATION";

	/**
	 * @Author LiuTao @Date 2021年1月15日 上午10:09:02
	 * @Title: parseXml
	 * @Description: 解析微信服务器发来的xml报文
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();
		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());
		// 释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}
	
	/**
	 * 对象到xml处理
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;
				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
	
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream(new DomDriver("utf-8"));
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	public static String imageMessageToXml(ImageMessage imageMessage) {
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	public static String voiceMessageToXml(VoiceMessage voiceMessage) {
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}

	public static String videoMessageToXml(VideoMessage videoMessage) {
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}

	public static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

}
