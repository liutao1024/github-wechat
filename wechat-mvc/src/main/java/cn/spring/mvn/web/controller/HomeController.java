package cn.spring.mvn.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("HomeController")
@RequestMapping(value="/home/path")//一个@RequestMapping添加多个value值value={"auth","boot-path"}
public class HomeController {
	//打印日志工具
	public static final Logger LOGGER = Logger.getLogger(HomeController.class);
	/**
	 * 不写在LoginController中是因为LoginController中用@ResponseBody注解导致页面显示的不是想要的登录页面,
	 * 而是字符串login
	 * @author LiuTao @date 2018年5月1日 上午10:44:13 
	 * @Title: loginController 
	 * @Description: TODO(登录页面Controller) 
	 * @return
	 */
	//1.登录首页
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/login/login");
		return modelAndView;
	}
	
	//4.进入index界面
	/**
	 * @author LiuTao @date 2018年5月1日 上午11:17:04 
	 * @Title: index 
	 * @Description: TODO(登录成功后首页显示) 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/login/index");
		return modelAndView;
	}
	
}

