package org.spring.boot.wechat.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class HttpPostUploadUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpPostUploadUtil.class);
	@Value("${weixin.appid}")
	private static String appid;
	
	@Value("${weixin.appSecret}")
	private static String appSecret;
	
	@Value("${weixin.tokenUrl}")
	private static String tokenUrl;
	
	@Value("${weixin.mediaUrl}")
	private static String mediaUrl;
	
	public String urlStr;

	public HttpPostUploadUtil() {
//		"http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="
		urlStr = mediaUrl.trim() + "access_token" + "&type=image";
	}

	/**
	 * 上传图片
	 * @param urlStr
	 * @param textMap
	 * @param fileMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String formUpload(Map<String, String> textMap, Map<String, String> fileMap) {
		String res = "";
//		http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=
		String imageUrl = mediaUrl.trim() + "access_token" + "&type=image";
		HttpURLConnection conn = null;
		String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符
		try {
			URL url = new URL(imageUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());
			// text
			if (textMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator<?> iter = textMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					strBuf.append("\r\n");
					strBuf.append("--");
					strBuf.append(BOUNDARY);
					strBuf.append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"");
					strBuf.append(inputName);
					strBuf.append("\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}

			// file
			if (fileMap != null) {
				Iterator<?> iter = fileMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					File file = new File(inputValue);
					String filename = file.getName();
					String contentType = new MimetypesFileTypeMap().getContentType(file);
					if (filename.endsWith(".jpg")) {
						contentType = "image/jpg";
					}
					if (contentType == null || contentType.equals("")) {
						contentType = "application/octet-stream";
					}

					StringBuffer strBuf = new StringBuffer();
					strBuf.append("\r\n");
					strBuf.append("--");
					strBuf.append(BOUNDARY);
					strBuf.append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"");
					strBuf.append(inputName);
					strBuf.append("\"; filename=\"");
					strBuf.append(filename);
					strBuf.append("\"\r\n");
					strBuf.append("Content-Type:");
					strBuf.append(contentType);
					strBuf.append("\r\n\r\n");

					out.write(strBuf.toString().getBytes());

					DataInputStream in = new DataInputStream(new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = in.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					in.close();
				}
			}

			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();

			// 读取返回数据
			StringBuffer strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line);
				strBuf.append("\n");
			}
			res = strBuf.toString();
			reader.close();
			reader = null;
		} catch (Exception e) {
			LOGGER.info("----发送POST请求出错----" + imageUrl);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return res;
	}
}
