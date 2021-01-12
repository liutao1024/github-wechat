package cn.spring.mvn.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import cn.spring.mvn.comm.security.MD5Util;
import cn.spring.mvn.comm.util.CommUtil;
import cn.spring.mvn.system.entity.SysUser;
import cn.spring.mvn.system.entity.service.SysUserService;

@Controller("LoginController")
@ResponseBody
@RequestMapping("/home/path")
@SessionAttributes("SysUser")
public class LoginController {
	@Autowired
	private SysUserService sysUserServiceImpl;
	//2重置登录状态
	/**
	 * @author LiuTao @date 2018年5月23日 上午10:12:36 
	 * @Title: reset 
	 * @Description: TODO(Describe) 
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value="/reset")
	public Map<String, Object> reset(@RequestBody SysUser sysUser) {
		Map<String, Object> resMap = new HashMap<String, Object>();//
		String cropno = sysUser.getRegistCd();
		String userid = sysUser.getUserid();
		String passwd = MD5Util.md5EncryptString(sysUser.getPasswd());
		//是否需要校验密码
		boolean checkPasswdFlag = true;
		SysUser resetSysUser = sysUserServiceImpl.selectOneByPrimeKey(cropno, userid);
		if(!CommUtil.isNull(resetSysUser) || !CommUtil.equal("1", resetSysUser.getStatus())){
			if(checkPasswdFlag){//
				if(CommUtil.equal(passwd, resetSysUser.getPasswd())){//
					resetSysUser.setErrort(0);
					resetSysUser.setUserst("0");
					sysUserServiceImpl.update(resetSysUser);
					resMap.put("ret", "success");
					resMap.put("msg", "状态重置成功");
				}else {
					resMap.put("ret", "error");
					resMap.put("msg", "状态重置失败,密码不正确");
				}
			}else {//
				resetSysUser.setErrort(0);
				resetSysUser.setUserst("0");
				sysUserServiceImpl.update(resetSysUser);
				resMap.put("ret", "success");
				resMap.put("msg", "状态重置成功");
			}
		}else {
			resMap.put("ret", "error");
			resMap.put("msg", "状态重置失败,账户:" + userid + "不存在,或未启用");
		}
		return resMap;//返回
	}
	
	//3.登录
	/**
	 * @author LiuTao @date 2018年5月23日 上午10:12:43 
	 * @Title: loginCheckController 
	 * @Description: TODO(Describe) 
	 * @param sysUser
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/loginCheck")
	public Map<String, Object> loginCheck(@RequestBody SysUser sysUser, Model model){
		Map<String, Object> resMap = new HashMap<String, Object>();
		String cropno = sysUser.getRegistCd();
		String userid = sysUser.getUserid();
		String passwd = MD5Util.md5EncryptString(sysUser.getPasswd());
		boolean checkPasswdFlag = true;
		String ret = "success";
		String msg = "登录成功";
		SysUser checkSysUser = sysUserServiceImpl.selectOneByPrimeKey(cropno, userid);
		if(CommUtil.isNotNull(checkSysUser) && CommUtil.equal(checkSysUser.getUserst(), "0") 
				&& CommUtil.compare(checkSysUser.getMaxert(), checkSysUser.getErrort()) >= 0 && CommUtil.equal("1", checkSysUser.getStatus())){//账户存在未登录且密码错误次数不大于最大错误次数状态为启用状态
			if(checkPasswdFlag){
				if(CommUtil.equal(passwd, checkSysUser.getPasswd())){
					checkSysUser.setErrort(0);
					checkSysUser.setUserst("1");
					ret = "success";
					msg = "登录成功";
				}else {
					int time = checkSysUser.getErrort() + 1;//密码错误次数加1
					checkSysUser.setErrort(time);
					ret = "error";
					msg = "登录失败,密码错误:" + time + "次";
				}
			}else {
				checkSysUser.setErrort(0);
				checkSysUser.setUserst("1");
				ret = "success";
				msg = "登陆成功";
			}
			sysUserServiceImpl.update(checkSysUser);
		}else {
			ret = "error";
			msg = "登录失败";
		}
		resMap.put("ret", ret);
		resMap.put("msg", msg);
		model.addAttribute("SysUser", checkSysUser);//写入session
		return resMap;//返回
	}
}
