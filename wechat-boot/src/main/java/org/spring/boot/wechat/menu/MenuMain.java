package org.spring.boot.wechat.menu;

import org.spring.boot.wechat.util.HttpUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class MenuMain {
	/**
	 * @Author LiuTao @Date 2021年1月19日 上午11:04:18 
	 * @Title: createMenu 
	 * @Description: 创建自定义菜单
	 */
	public void createMenu() {
		Click cbt = new Click();
		cbt.setKey("image");
		cbt.setName("回复图片");
		cbt.setType("click");

		View vbt = new View();
		vbt.setUrl("https://blog.csdn.net/loverGTO");
		vbt.setName("CSDN");
		vbt.setType("view");

		JSONArray sub_button = new JSONArray();
		sub_button.add(cbt);
		sub_button.add(vbt);

		JSONObject buttonOne = new JSONObject();
		buttonOne.put("name", "菜单");
		buttonOne.put("sub_button", sub_button);

		JSONArray button = new JSONArray();
		button.add(vbt);
		button.add(buttonOne);
		button.add(cbt);

		JSONObject menujson = new JSONObject();
		menujson.put("button", button);
		System.out.println(menujson);

		// 这里为请求接口的url+号后面的是token,这里就不做过多对token获取的方法解释
		String url = HttpUtils.getProperty("weixin.menueUrl") + HttpUtils.get("access_token");

		try {
			String rs = HttpUtils.sendPostBuffer(url, menujson.toJSONString());
			System.out.println(rs);
		} catch (Exception e) {
			System.out.println("请求错误！");
		}
	}
}
