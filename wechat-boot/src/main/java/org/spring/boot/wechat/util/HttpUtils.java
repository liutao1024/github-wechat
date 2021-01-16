package org.spring.boot.wechat.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.activation.MimetypesFileTypeMap;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author LiuTao @Date 2021年1月15日 下午2:50:51
 * @ClassName: HttpUtils 
 * @Description: TODO(Describe)
 */
@SuppressWarnings("deprecation")
public class HttpUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

	/**
	 * @Description: http get请求共用方法
	 * @param @param reqUrl
	 * @param @param params
	 * @param @return
	 * @param @throws Exception
	 */
	@SuppressWarnings("resource")
	public static String sendGet(String reqUrl, Map<String, String> params) throws Exception {
		InputStream inputStream = null;
		HttpGet request = new HttpGet();
		try {
			String url = buildUrl(reqUrl, params);
			HttpClient client = new DefaultHttpClient();
			request.setHeader("Accept-Encoding", "gzip");
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			inputStream = response.getEntity().getContent();
			String result = getJsonStringFromGZIP(inputStream);
			return result;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			request.releaseConnection();
		}
	}
	/**
	 * @Author LiuTao @Date 2021年1月15日 下午3:03:27 
	 * @Title: sendPost 
	 * @Description: http发送post请求
	 * @param reqUrl
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static String sendPost(String reqUrl, Map<String, String> params) throws Exception {
		try {
			Set<String> set = params.keySet();
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (String key : set) {
				list.add(new BasicNameValuePair(key, params.get(key)));
			}
			if (list.size() > 0) {
				try {
					HttpClient client = new DefaultHttpClient();
					HttpPost request = new HttpPost(reqUrl);
					request.setHeader("Accept-Encoding", "gzip");
					request.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
					HttpResponse response = client.execute(request);
					InputStream inputStream = response.getEntity().getContent();
					try {
						String result = getJsonStringFromGZIP(inputStream);
						return result;
					} finally {
						inputStream.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new Exception("网络连接失败,请连接网络后再试");
				}
			} else {
				throw new Exception("参数不全,请稍后重试");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("发送未知异常");
		}
	}
	/**
	 * @Author LiuTao @Date 2021年1月15日 下午3:03:33 
	 * @Title: sendPostBuffer 
	 * @Description: TODO(Describe) 
	 * @param urls
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String sendPostBuffer(String urls, String params) throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost(urls);
		StringEntity se = new StringEntity(params, HTTP.UTF_8);
		request.setEntity(se);
		// 发送请求
		@SuppressWarnings("resource")
		HttpResponse httpResponse = new DefaultHttpClient().execute(request);
		// 得到应答的字符串,这也是一个 JSON 格式保存的数据
		String retSrc = EntityUtils.toString(httpResponse.getEntity());
		request.releaseConnection();
		return retSrc;
	}
	/**
	 * @Author LiuTao @Date 2021年1月15日 下午3:03:39 
	 * @Title: sendXmlPost 
	 * @Description: 发送报文xml格式的post请求,
	 * @param urlStr
	 * @param xmlInfo
	 * @return
	 */
	public static String sendXmlPost(String urlStr, String xmlInfo) {
		// xmlInfo xml具体字符串
		try {
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");
			OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
			out.write(new String(xmlInfo.getBytes("utf-8")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String lines = "";
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				lines = lines + line;
			}
			return lines; // 返回请求结果
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "fail";
	}
	/**
	 * @Author LiuTao @Date 2021年1月15日 下午3:03:46 
	 * @Title: getJsonStringFromGZIP 
	 * @Description: TODO(Describe) 
	 * @param is
	 * @return
	 */
	private static String getJsonStringFromGZIP(InputStream is) {
		String jsonString = null;
		try {
			BufferedInputStream bis = new BufferedInputStream(is);
			bis.mark(2);
			// 取前两个字节
			byte[] header = new byte[2];
			int result = bis.read(header);
			// reset输入流到开始位置
			bis.reset();
			// 判断是否是GZIP格式
			int headerData = getShort(header);
			// Gzip 流 的前两个字节是 0x1f8b
			if (result != -1 && headerData == 0x1f8b) {
				// LogUtil.i("HttpTask", " use GZIPInputStream  ");
				is = new GZIPInputStream(bis);
			} else {
				// LogUtil.d("HttpTask", " not use GZIPInputStream");
				is = bis;
			}
			InputStreamReader reader = new InputStreamReader(is, "utf-8");
			char[] data = new char[100];
			int readSize;
			StringBuffer sb = new StringBuffer();
			while ((readSize = reader.read(data)) > 0) {
				sb.append(data, 0, readSize);
			}
			jsonString = sb.toString();
			bis.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	
	/**
	 * @Author LiuTao @Date 2021年1月15日 下午2:54:01 
	 * @Title: getShort 
	 * @Description: 排序
	 * @param data
	 * @return
	 */
	private static int getShort(byte[] data) {
		return (data[0] << 8) | data[1] & 0xFF;
	}

	/**
	 * @Author LiuTao @Date 2021年1月16日 下午2:13:32 
	 * @Title: buildUrl 
	 * @Description: 构建get方式的url
	 * @param reqUrl  基础的url地址
	 * @param params  查询参数
	 * @return 构建好的url
	 */
	public static String buildUrl(String reqUrl, Map<String, String> params) {
		StringBuilder query = new StringBuilder();
		Set<String> set = params.keySet();
		for (String key : set) {
			query.append(String.format("%s=%s&", key.trim(), params.get(key).trim()));
		}
		return reqUrl.trim() + "?" + query.toString().trim();
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
//		String urlStr;
		String res = "";
//		mediaUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=";
		RedisUtil redis = SpringUtil.getBean(RedisUtil.class);
		String access_token = (String) redis.get("access_token");
		LOGGER.info(access_token);
//		String access_token = (String) redisUtil.get("access_token");
//		String imageUrl = mediaUrl.trim() + "access_token" + "&type=image";
		String imageUrl = "https://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=" + access_token + "&type=image";
		LOGGER.info(imageUrl);
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
