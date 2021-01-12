package cn.spring.mvn.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.spring.mvn.system.entity.SysAuth;
import cn.spring.mvn.system.entity.SysDict;
import cn.spring.mvn.system.entity.SysRole;
import cn.spring.mvn.system.entity.service.SysAuthService;
import cn.spring.mvn.system.entity.service.SysDictService;
import cn.spring.mvn.system.entity.service.SysRoleService;
/**
 * @author LiuTao @date 2018年5月30日 下午8:49:35
 * @ClassName: CommController 
 * @Description: 公共业务Controller
 */
@Controller("CommController")
@ResponseBody
public class CommController {
	@Autowired
	private SysDictService sysDictServiceImpl;
	@Autowired
	private SysRoleService sysRoleServiceImpl;
	@Autowired
	private SysAuthService sysAuthServiceImpl;
	
	/**
	 * @author LiuTao @date 2018年6月5日 下午2:40:13 
	 * @Title: dispathTriggerTask 
	 * @Description: 触发批量任务
	 * @param jobId
	 */
	@RequestMapping(value = "/task/{jobId}")
	public void dispathTriggerTask(@PathVariable("jobId") String jobId){
		
	}
	/**
	 * @author LiuTao @date 2018年5月30日 下午5:43:36 
	 * @Title: toErrorPage 
	 * @Description: 报错页面跳转
	 * @param number
	 * @return
	 */
	@RequestMapping(value = "/error/{number}")
	public ModelAndView toErrorPage(@PathVariable("number") String number) {
		StringBuffer path = new StringBuffer();
		path.append("/error/error_inner_").append(number);
		ModelAndView modelAndView = new ModelAndView(path.toString());
		return modelAndView;
	}
	/**
	 * @author LiuTao @date 2018年5月30日 下午5:56:28 
	 * @Title: toPage 
	 * @Description: 控制菜单页面跳转 
	 * @param model 模块
	 * @param page 页面
	 * @return
	 */
	@RequestMapping(value = "/menuUrl/{model}/{page}")
	public ModelAndView toPage(@PathVariable("model") String model, @PathVariable("page") String page){
		String mavStr = "/" + model + "/" + page;
		ModelAndView modelAndView = new ModelAndView(mavStr);
		return modelAndView;
	}
	/**
	 * @author LiuTao @date 2018年5月30日 下午8:58:56 
	 * @Title: toGetDict 
	 * @Description: 根据字典类型获取字典 
	 * @param dictType
	 * @return
	 */
	@RequestMapping(value = "/dict")
	public List<SysDict> toGetDictsByDictType(@RequestBody SysDict sysDict){
		List<SysDict> sysDictList = sysDictServiceImpl.selectAllByDictType(sysDict.getDictType());
		return sysDictList;
	}
	/**
	 * @author LiuTao @date 2018年8月21日 上午11:57:32 
	 * @Title: toGetDictsByDictUrl 
	 * @Description: TODO(Describe) 
	 * @param dictUrl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/dict/{dictUrl}")
	public <T> List<T> toGetDictsByDictUrl(@PathVariable("dictUrl") String dictUrl){
		List<T> dictList = new ArrayList<T>();
		switch (dictUrl) {
		case "role"://需要从其他表中取的枚举值的字典类型
			dictList = (List<T>) sysRoleServiceImpl.findAll(new SysRole());
			break;
		case "auth":
			dictList = (List<T>) sysAuthServiceImpl.findAll(new SysAuth());
			break;
			
		default://配在sys_dict表中的枚举值
			dictList = (List<T>) sysDictServiceImpl.selectAllByDictType(dictUrl);
			break;
		}
		
		return dictList;
	}
	
}
