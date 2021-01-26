package org.spring.boot.wechat.util;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class AddressUtils {
	private final static Logger logger = LoggerFactory.getLogger(AddressUtils.class);
	public static void main(String[] args) {
		/**
		 * aliyun,阿里云服务器停了不能获取了
		 */
		String address = getAliyunAddress("116.33032", "39.91333");
		System.out.println("地址信息：" + address);
		JSONObject jsonObject = JSONObject.parseObject(address);
		JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("addrList"));
		JSONObject j_2 = JSONObject.parseObject(jsonArray.getString(0));
		String allAdd = j_2.getString("admName");
		String arr[] = allAdd.split(",");
		System.out.println("省：" + arr[0] + "\n市：" + arr[1] + "\n区：" + arr[2]);
		
		
		/**
		 * 高德
		 */
//        String add = getAdd("104.064109","30.54252");
        String add = getGeocodeAddress("104.066573", "30.54305");
//        String add = getAdd("121.3466440", "31.2990170");
        logger.info(add);
	}

	/**
     *	高德根据经纬度获取省市区
     * @param log
     * @param lat
     * @return
     */
    public static String getGeocodeAddress(String lat, String log){
        //lat 小  log  大
        //参数解释: 纬度,经度 采用高德API可参考高德文档https://lbs.amap.com/
        String key = "c93b940cdd57eb726fd61baf1275d00e";
        String urlString = "https://restapi.amap.com/v3/geocode/regeo?location="+lat+","+log+"&extensions=base&batch=false&roadlevel=0&key="+key;
        String res = "";
        try {
            URL url = new URL(urlString);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line+"\n";
            }
            in.close();
            //解析结果
           JSONObject jsonObject = JSONObject.parseObject(res);
			logger.info(jsonObject.toJSONString());
			JSONObject jsonObject1 = jsonObject.getJSONObject("regeocode");
			res =jsonObject1.getString("formatted_address");
        } catch (Exception e) {
            logger.error("获取地址信息异常{}",e.getMessage());
           return null;
        }
        return res;
    }

	/**
	 * aliyu
	 */
	public static String getAliyunAddress(String lat, String log) {
		// lat 小 log 大
		// 参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)
		String urlString = "http://gc.ditu.aliyun.com/regeocoding?l=" + lat + "," + log + "&type=010";
		String res = "";
		try {
			URL url = new URL(urlString);
			java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			java.io.BufferedReader in = new java.io.BufferedReader(
					new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				res += line + "\n";
			}
			in.close();
		} catch (Exception e) {
			System.out.println("error in wapaction,and e is " + e.getMessage());
		}
		System.out.println(res);
		return res;
	}
}
