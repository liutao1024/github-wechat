package cn.spring.mvn.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.spring.mvn.system.entity.SysUser;
import cn.spring.mvn.system.entity.service.SysUserService;

@Controller("HelloController")
@RequestMapping("/user")
public class HelloController {

	private static final Logger LOGGER = Logger.getLogger(HelloController.class);
	@Autowired
	private SysUserService sysUserServiceImpl;
	String message = "Welcome to Spring MVC!";
	/**
	 * @author LiuTao @date 2018年5月23日 上午10:08:15 
	 * @Title: showMessage 
	 * @Description: TODO(Describe) 
	 * @param name
	 * @return
	 */
	@RequestMapping("/hello")
    public ModelAndView showMessage(@RequestParam(value = "name", required = false, defaultValue = "Spring") String name) {
		//指定视图
        ModelAndView mv = new ModelAndView("/hellospring");
        //向视图中添加所要展示或使用的内容,将在页面中使用
        mv.addObject("message", message);
        mv.addObject("name", name);
        return mv;
    }
	
	/**
	 * @author LiuTao @date 2018年5月13日 下午9:37:42 
	 * @Title: showUserInfo 
	 * @Description: TODO(Describe) 
	 * @param modelMap
	 * @param userId
	 * @return
	 */
	@RequestMapping("/showInfo/{userId}")
	public String showUserInfo(ModelMap modelMap, @PathVariable String userId){
		LOGGER.info("查询用户：" + userId);
		SysUser userInfo = sysUserServiceImpl.selectOneByPrimeKey("", userId);
		modelMap.addAttribute("userInfo", userInfo);
		return "/showInfo";
	}
	
	/**
	 * @author LiuTao @date 2018年5月13日 下午9:37:46 
	 * @Title: showUserInfos 
	 * @Description: TODO(Describe) 
	 * @return
	 */
	@RequestMapping("/showInfos")
	public @ResponseBody List<SysUser> showUserInfos(){
		LOGGER.info("查询全部用户");
		List<SysUser> userInfos = sysUserServiceImpl.selectAll();
		return userInfos;
	}
}
