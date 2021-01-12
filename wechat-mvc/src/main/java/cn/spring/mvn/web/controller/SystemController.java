package cn.spring.mvn.web.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
//import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import cn.spring.mvn.comm.security.MD5Util;
import cn.spring.mvn.comm.util.CommUtil;
import cn.spring.mvn.system.entity.SysAuth;
import cn.spring.mvn.system.entity.SysRoleAuth;
import cn.spring.mvn.system.entity.SysRole;
import cn.spring.mvn.system.entity.SysUser;
import cn.spring.mvn.system.entity.SysUserRole;
import cn.spring.mvn.system.entity.service.SysRoleAuthService;
import cn.spring.mvn.system.entity.service.SysAuthService;
import cn.spring.mvn.system.entity.service.SysRoleService;
import cn.spring.mvn.system.entity.service.SysUserRoleService;
import cn.spring.mvn.system.entity.service.SysUserService;
/**
 * @author LiuTao @date 2018年5月1日 上午11:59:41
 * @ClassName: SystemController 
 * @Description: 系统的Controller
 */
@Controller("SystemController")
@RequestMapping(value = "auth")
@ResponseBody
@SessionAttributes("SysUser")
public class SystemController {
	private static final Logger LOGGER = Logger.getLogger(SystemController.class);
	private static final String PASSWD = MD5Util.md5EncryptString("123456");
	private static String AUTHTYPE = "2";//菜单权限类型
	private String[] strArray = {};//user用    有权限AuthCd数组
	@Autowired
	private SysUserRoleService sysUserRoleServiceImpl;
	@Autowired
	private SysRoleAuthService sysRoleAuthServiceImpl;
	@Autowired
	private SysAuthService sysAuthServiceImpl;
	@Autowired
	private SysUserService sysUserServiceImpl;
	@Autowired
	private SysRoleService sysRoleServiceImpl;
	//5.检查柜员信息
	/**
	 * @author LiuTao @date 2018年5月1日 下午1:35:12 
	 * @Title: sysUserInfo 
	 * @Description: TODO(前台获取session信息) 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/sysUserInfo", method=RequestMethod.GET)
	public Map<String, Object> sysUserInfo(@ModelAttribute("SysUser") SysUser sysUser) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		String userid = sysUser.getUserid();
		if (CommUtil.isNull(userid)) {
			resMap.put("ret", "error");
			resMap.put("msg", "您未登录,请登录");
			return resMap;
		}else {
			resMap.put("user", sysUser);
			resMap.put("ret", "success");
			resMap.put("msg", "成功");
		}
		return resMap;
	}
	//6.获取柜员菜单
	/**
	 * @author LiuTao @date 2018年5月7日 下午6:00:36 
	 * @Title: menuController 
	 * @Description: TODO(获取sysUser菜单权限) 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/menu")
	public Map<String, Object> menuController(@ModelAttribute("SysUser") SysUser sysUser) {
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setRegist_cd(sysUser.getRegistCd());
		sysUserRole.setAuth_type(AUTHTYPE);// 菜单权限为2
		sysUserRole.setUser_cd(sysUser.getUserid());
		/**得到柜员的角色      
		 * 一个柜员可能对应多个角色
		 */
		List<SysUserRole> sysUserRoleList = sysUserRoleServiceImpl.queryEntitiesByEntityParamMap(sysUserRole);
		List<SysRoleAuth> sysRoleAuthList = new ArrayList<SysRoleAuth>();
		SysRoleAuth sysRoleAuth = new SysRoleAuth();
		for (SysUserRole theSysUserRole : sysUserRoleList) {
			/**通过角色来查询 角色权限
			 * 一个角色可能会有多个 权限
			 */
			sysRoleAuth.setRegist_cd(theSysUserRole.getRegist_cd());
			sysRoleAuth.setAuth_type(theSysUserRole.getAuth_type());
			sysRoleAuth.setRole_cd(theSysUserRole.getRole_cd());
			sysRoleAuthList.addAll(sysRoleAuthServiceImpl.selectAllEntities(sysRoleAuth));
		}
		//权限去重复
		HashSet<SysRoleAuth> hashSet = new HashSet<SysRoleAuth>(sysRoleAuthList);
		sysRoleAuthList.clear();
		sysRoleAuthList.addAll(hashSet);
		int k = 0;
		strArray = new String[sysRoleAuthList.size()];
		for (SysRoleAuth theSysAuthRole : sysRoleAuthList) {
			strArray[k] = theSysAuthRole.getAuth_cd();//菜单编号
			k++;
		}
		// 查询所有菜单
		SysAuth sysAuth = new SysAuth();
		sysAuth.setRegist_cd(sysUser.getRegistCd());//机构号
		sysAuth.setAuth_type(AUTHTYPE);// 2 为菜单权限
		sysAuth.setRank(1);//从第一级开始取
		List<SysAuth> sysAuthList = new ArrayList<SysAuth>();
		sysAuthList.addAll(sysAuthServiceImpl.selectAllEntities(sysAuth));		
		Map<String, Object> sysAuthMap = new HashMap<String, Object>();
		sysAuthMap.put("menu", reGetMenu(sysAuth, sysAuthList, sysAuth.getRank(), true));
		LOGGER.info("---------菜单" + sysAuthMap.toString());
		return sysAuthMap;//返回的菜单内容
	}
	//7.柜员退出
	/**
	 * @author LiuTao @date 2018年5月1日 上午11:52:54 
	 * @Title: logout
	 * @Description: TODO(Describe) 
	 * @param request
	 * @param model
	 * @param user
	 */
	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request,Model model, @ModelAttribute("SysUser") SysUser sysUser) {
		String registCd = sysUser.getRegistCd();
		String userid = sysUser.getUserid();
		SysUser logoutSysUser = sysUserServiceImpl.selectOneByPrimeKey(registCd, userid);
		logoutSysUser.setUserst("0");
		sysUserServiceImpl.update(logoutSysUser);
		HttpSession session = request.getSession(false);  
		Enumeration<String> em = session.getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		 session.removeAttribute("SysUser");
		 session.invalidate();//session无效处理
		 model.asMap().clear();//清除model中的对象
	}
	/**
	 * @author LiuTao @date 2018年5月7日 下午7:56:41 
	 * @Title: toDoList 
	 * @Description: TODO(查询待办事件) 
	 * @param reqmap 查询条件输入
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/to-do-list")
	public Map<String, Object> toDoList(@RequestParam Map<String, Object> reqmap, @ModelAttribute("SysUser") SysUser sysUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		List <String> rolecdList  = new ArrayList<String>();
		
		map.putAll(reqmap);
		if (reqmap.get("q_subjtp") != null && reqmap.get("q_subjtp") != "") {
			map.put("subjtp", reqmap.get("q_subjtp"));
		}
		if (reqmap.get("q_emrgfg") != null && reqmap.get("q_emrgfg") != "") {
			map.put("emrgfg", reqmap.get("q_emrgfg"));
		}
		List<Map<String,String>> roleList = new ArrayList<Map<String,String>>();
		for(String s : rolecdList){
			Map<String,String> roleMap = new HashMap<String,String>();
			roleMap.put("recvrl", s);
			roleList.add(roleMap);
		}
	    map.put("roleList", roleList);
		map.put("userid", sysUser.getUserid()); // 设置操作柜员
		int length = Integer.parseInt(StringUtils.isNotEmpty((String) reqmap.get("length")) ? (String) reqmap.get("length") : "10", 10);
		int start = Integer.parseInt(StringUtils.isNotEmpty((String) reqmap.get("start")) ? (String) reqmap.get("start") : "1", 10);
		map.put("pageno", start / length + 1);// 当前页数
		map.put("pagesize", length); // 每页记录数
		Map<String, Object> resmap = new HashMap<String, Object>();
//		resmap = client.callClient("qrsubj", map);//需要查询待办事件表暂无
		resmap.put("retCode", "0000");
		if (resmap.get("retCode").toString().equals("0000")) {//空.toString报错了
			resmap.put("ret", "success");
			resmap.put("msg", "待办事项发送成功");
		} else {
			resmap.put("msg", resmap.get("retMsg").toString());
		}
		resmap.put("infos",resmap.get("infos") == null ? new ArrayList<Object>() : resmap.get("infos"));
		resmap.put("iTotalDisplayRecords", resmap.get("counts") == null ? "0" : resmap.get("counts"));
		resmap.put("iTotalRecords", resmap.get("counts") == null ? "0" : resmap.get("counts"));
		Map<String, Object> dmap = new HashMap<String, Object>();//
//		dmap = client.callClient("qdclam",map );
		dmap.put("wcouts", 0);
		resmap.put("wcouts",dmap.get("wcouts"));
		return resmap;
	}
	/**
	 * @author LiuTao @date 2018年5月29日 下午10:32:02 
	 * @Title: updatePassWord 
	 * @Description: TODO(Describe) 
	 * @param requestMap
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value = "/updatePassWord")
	public Map<String, Object> updatePassWord(@RequestBody Map<String, Object> requestMap, @ModelAttribute("SysUser") SysUser sysUser){
		Map<String, Object> resMap = new HashMap<String, Object>();
		SysUser theSysUser = sysUserServiceImpl.selectOneByPrimeKey(sysUser.getRegistCd(), sysUser.getUserid());
		String oldPasswd = requestMap.get("passwd").toString();
		String newPasswd = requestMap.get("nwpswd").toString();
		if(CommUtil.equal(theSysUser.getPasswd(), MD5Util.md5EncryptString(oldPasswd))){
			theSysUser.setPasswd(MD5Util.md5EncryptString(newPasswd));
			try {
				sysUserServiceImpl.saveOrUpdate(theSysUser);
				resMap.put("ret", "success");
				resMap.put("msg", "密码修改成功");
			} catch (Throwable e) {
				resMap.put("msg", e.getMessage());
			}
		}else {
			resMap.put("msg", "密码修改失败,原密码不正确");
		}
		return resMap;
	}
	/**---------------------------------------SysUser------------------------------------------------*/
	/**
	 * @author LiuTao @date 2018年6月5日 下午3:48:10 
	 * @Title: addSysUser 
	 * @Description: 新增柜员
	 * @param sysUserNew
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value = "/addSysUser")
	public Map<String, Object> addSysUser(@RequestBody SysUser sysUserNew, @ModelAttribute("SysUser") SysUser sysUser){
		Map<String, Object> rstMap = new HashMap<String, Object>();
		sysUserNew.setRegistCd(sysUser.getRegistCd());
		sysUserNew.setPasswd(PASSWD);
		sysUserNew.setStatus("1");
		try {
			sysUserServiceImpl.add(sysUserNew);
			rstMap.put("ret", "success");
			rstMap.put("msg", "新增柜员成功");
		} catch (Throwable e) {
			rstMap.put("msg", e.getMessage());
		}
		return rstMap;
	}
	/**
	 * @author LiuTao @date 2018年8月21日 下午12:40:08 
	 * @Title: showAllSysUser 
	 * @Description: TODO(Describe) 
	 * @param resMap
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value = "/showAllSysUser")
	public Map<String, Object> showAllSysUser(@RequestParam Map<String, Object> resMap, @ModelAttribute("SysUser") SysUser sysUser){
		Map<String, Object> rstMap = new HashMap<String, Object>();
		String hqlStr = "from SysUser where regist_cd = '"+ sysUser.getRegistCd()+"'";
		String appendStr = "";
		if (resMap.get("q_userid") != null && resMap.get("q_userid") != "") {
			appendStr = appendStr + " and userid = '" + resMap.get("q_userid") + "'";
		}
		if (resMap.get("q_userna") != null && resMap.get("q_userna") != "") {
			appendStr = appendStr + " and userna like '%" + resMap.get("q_userna") + "%'";
		}
		int page = Integer.parseInt((String) resMap.get("start"));
		int size = Integer.parseInt((String) resMap.get("length"));
		List<SysUser> sysUserList = sysUserServiceImpl.findAllByHqlPageSize(hqlStr+appendStr, page, size);
		List<SysUser> sysUserListCount = sysUserServiceImpl.findAllByHql(hqlStr+appendStr);
		rstMap.put("data", sysUserList);
		rstMap.put("iTotalDisplayRecords", sysUserListCount.size());
		rstMap.put("iTotalRecords", sysUserList.size());	
		return rstMap;
	}
	/**
	 * @author LiuTao @date 2018年6月6日 上午11:52:40 
	 * @Title: deleteSysUser 
	 * @Description: 注销柜员 
	 * @param user
	 * @param cuser
	 * @return
	 */
	@RequestMapping(value = "/deleteSysUser", method = { RequestMethod.DELETE })
	public Map<String, Object> deleteSysUser(@RequestBody SysUser user, @ModelAttribute("SysUser") SysUser cuser) {
		Map<String, Object> rstMap = new HashMap<String, Object>();
		SysUser offSysUser = sysUserServiceImpl.selectOneByPrimeKey(cuser.getRegistCd(), user.getUserid());
		offSysUser.setStatus("0");
		try {
			sysUserServiceImpl.saveOrUpdate(offSysUser);
			rstMap.put("ret", "success");
			rstMap.put("msg", "注销柜员成功");
		} catch (Exception e) {
			rstMap.put("msg", e.getMessage());
		}
		return rstMap;
	}
	/**
	 * @author LiuTao @date 2018年6月5日 下午4:22:51 
	 * @Title: updateSysUser 
	 * @Description: 更新柜员信息
	 * @param sysUserUp
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value = "/updateSysUser")
	public Map<String, Object> updateSysUser(@RequestBody SysUser sysUserUp, @ModelAttribute("SysUser") SysUser sysUser){
		Map<String, Object> rstMap = new HashMap<String, Object>();
		SysUser newUser = sysUserServiceImpl.selectOneByPrimeKey(sysUser.getRegistCd(), sysUserUp.getUserid());
		newUser.setUserna(sysUserUp.getUserna());
		newUser.setBrchno(sysUserUp.getBrchno());
		newUser.setMaxert(sysUserUp.getMaxert());
		newUser.setUserst(sysUserUp.getUserst());
		newUser.setUserlv(sysUserUp.getUserlv());
		try {
			sysUserServiceImpl.update(newUser);
			rstMap.put("ret", "success");
			rstMap.put("msg", "修改柜员成功");
		} catch (Exception e) {
			rstMap.put("msg", e.getMessage());
		}
		return rstMap;
	}
	/**
	 * @author LiuTao @date 2018年6月6日 上午11:20:38 
	 * @Title: updateSysUserPassWord 
	 * @Description: 重置柜员密码 
	 * @param ajaxSysUser
	 * @param sessionSysUser
	 * @return
	 */
	@RequestMapping(value = "/updateSysUserPassWord")
	public Map<String,Object> updateSysUserPassWord(@RequestBody SysUser ajaxSysUser, @ModelAttribute("SysUser") SysUser sessionSysUser){
		Map<String, Object> rstMap = new HashMap<String, Object>();
		SysUser updateSysUser = sysUserServiceImpl.selectOneByPrimeKey(sessionSysUser.getRegistCd(), ajaxSysUser.getUserid());
		updateSysUser.setPasswd(PASSWD);
		try {
			sysUserServiceImpl.saveOrUpdate(updateSysUser);
			rstMap.put("ret", "success");
			rstMap.put("msg", "修改柜员成功");
		} catch (Exception e) {
			rstMap.put("msg", e.getMessage());
		}
		return rstMap;
	}
	/**
	 * @author LiuTao @date 2018年5月30日 下午9:31:28 
	 * @Title: showAllSysUserRole 
	 * @Description: TODO(Describe) 
	 * @param reqMap
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value = "/showAllSysUserRole")
	public Map<String, Object> showAllSysUserRole(@RequestParam Map<String, Object> reqMap, @ModelAttribute("SysUser") SysUser sysUser){
		Map<String, Object> rstMap = new HashMap<String, Object>();
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setRegist_cd(sysUser.getRegistCd());
		sysUserRole.setUser_cd(reqMap.get("user_cd").toString());
		int page = Integer.parseInt((String) reqMap.get("start"));
		int size = Integer.parseInt((String) reqMap.get("length"));
		Map<String, Object> listWithCount = sysUserRoleServiceImpl.findAllByEntityPageSizeWithCount(sysUserRole, page, size);
		try {
			rstMap = CommUtil.transSrcMapToWebMap(listWithCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstMap;
	}
	/**
	 * @author LiuTao @date 2018年6月6日 下午2:49:16 
	 * @Title: deleteSysUserRole 
	 * @Description: 删除柜员角色
	 * @param SysRoleUser
	 * @return
	 */
	@RequestMapping(value = "/deleteSysUserRole", method = {RequestMethod.DELETE })
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, String> deleteSysUserRole(@RequestBody SysUserRole sysUserRole) {
		Map<String, String> rstMap = new HashMap<String, String>();
		try {
			sysUserRoleServiceImpl.deleteEntity(sysUserRole);
			rstMap.put("ret", "success");
			rstMap.put("msg", sysUserRole.getUser_cd() + "删除成功");
		} catch (Exception e) {
			rstMap.put("ret", "error");
			rstMap.put("msg", sysUserRole.getUser_cd() + "删除失败");
		}
		return rstMap;

	}
	/**
	 * @author LiuTao @date 2018年6月6日 下午3:50:57 
	 * @Title: addUserRole 
	 * @Description: TODO(Describe) 
	 * @param sysUserRole
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value = "/addSysUserRole")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, String> addSysUserRole(@RequestBody SysUserRole sysUserRole, @ModelAttribute("SysUser") SysUser sysUser) {
		/**
		 * 保存roleUser
		 */
		Map<String, String> rstMap = new HashMap<String, String>();
		// 判断权限是否存在
		SysUserRole newSysUserRole = new SysUserRole();
		newSysUserRole.setAuth_type(sysUserRole.getAuth_type());
		newSysUserRole.setRegist_cd(sysUser.getRegistCd());
		newSysUserRole.setRole_cd(sysUserRole.getRole_cd());
		newSysUserRole.setUser_cd(sysUserRole.getUser_cd());
		SysUserRole userRole = sysUserRoleServiceImpl.selectOneEntity(newSysUserRole);
		if (CommUtil.isNotNull(userRole)) {
			rstMap.put("ret", "error");
			rstMap.put("msg", "角色已存在,新增失败");
		}else {
			try {
				sysUserRoleServiceImpl.saveOrUpdate(newSysUserRole);
				rstMap.put("ret", "success");
				rstMap.put("msg", "角色新增成功");
			} catch (Exception e) {
				rstMap.put("ret", "error");
				rstMap.put("msg", "角色已存在,新增失败");
			}
		}
		return rstMap;
	}
	/**---------------------------------------SysRole------------------------------------------------*/
	/**
	 * @author LiuTao @date 2018年7月18日 下午3:25:54 
	 * @Title: showAllSysRole 
	 * @Description: 查询所有角色信息 sys_role表
	 * @param reqMap
	 * @return
	 */
	@RequestMapping(value="/showAllSysRole")
	public Map<String, Object> showAllSysRole(@RequestParam Map<String,Object> reqMap, @ModelAttribute("SysUser") SysUser sysUser){
	    Map<String, Object> rstMap = new HashMap<String, Object>();
		SysRole sysRole = new SysRole();
//		sysRole.setRegist_cd(sysUser.getRegistCd());
		if (reqMap.get("q_authType") != null && reqMap.get("q_authType") != "") {
			sysRole.setAuth_type(reqMap.get("q_authType").toString());
		}
		if (reqMap.get("q_roleCd") != null && reqMap.get("q_roleCd") != "") {
			sysRole.setRole_cd(reqMap.get("q_roleCd").toString());
		}
		if(reqMap.get("q_roleName")!=null&&reqMap.get("q_roleName")!=""){
			sysRole.setRole_name(reqMap.get("q_roleName").toString());
		}
		int page = Integer.parseInt((String) reqMap.get("start"));
		int size = Integer.parseInt((String) reqMap.get("length"));	
		Map<String, Object> listWithCount = sysRoleServiceImpl.findAllByEntityPageSizeWithCount(sysRole, page, size);
		try {
			rstMap = CommUtil.transSrcMapToWebMap(listWithCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstMap;
	}
	/**
	 * @author LiuTao @date 2018年8月22日 下午1:19:18 
	 * @Title: deleteSysRole 
	 * @Description: 删除角色信息 
	 * @param sysRole
	 * @return
	 */
	@RequestMapping(value="/showAllSysRole", method = {RequestMethod.DELETE})
	public Map<String, Object> deleteSysRole(@RequestBody SysRole sysRole){
	    Map<String, Object> rstMap = new HashMap<String, Object>();
		try {
			sysRoleServiceImpl.deleteEntity(sysRole);
			rstMap.put("ret", "success");
			rstMap.put("msg", sysRole.getRole_cd() + "删除成功");
		} catch (Exception e) {
			rstMap.put("ret", "error");
			rstMap.put("msg", sysRole.getRole_cd() + "删除失败");
		}
	    return rstMap;
	}
	/**
	 * @author LiuTao @date 2018年8月17日 下午5:11:17 
	 * @Title: addSysRole 
	 * @Description: 保存sysRole 
	 * @param sysRole
	 * @return
	 */
	@RequestMapping(value="/addSysRole")
	@Transactional(propagation = Propagation.REQUIRED)	
	public Map<String,String> addSysRole(@RequestBody SysRole sysRole){
		Map<String,String> rstMap=new HashMap<String, String>();
		SysRole addSysRole = new SysRole();
		try {
			addSysRole = sysRoleServiceImpl.saveEntity(sysRole);
		} catch (Throwable e) {
			rstMap.put("ret", "error");
			rstMap.put("msg", sysRole.getRole_cd() + "已存在,新增失败");
			return rstMap;
		}
		if(addSysRole == null){
			rstMap.put("ret", "error");
			rstMap.put("msg",  sysRole.getRole_cd() + "新增失败,请联系管理员");
			return rstMap;
		}
		rstMap.put("ret", "success");
		rstMap.put("msg", addSysRole.getRole_cd()+ "新增成功");
		return rstMap;		
	}
	/**
	 * @author LiuTao @date 2018年8月21日 下午3:08:08 
	 * @Title: updateSysRole 
	 * @Description: TODO(sys_role表具体什么作用不是很清楚呢!) 
	 * @param sysRole
	 * @return
	 */
	@RequestMapping(value="/updateSysRole")
	public Map<String, String> updateSysRole(@RequestBody SysRole sysRole){
		Map<String,String> rstMap=new HashMap<String, String>();
		SysRole updateSysRole = sysRoleServiceImpl.selectOneByPrimeKey(sysRole.getRegist_cd(), sysRole.getAuth_type(), sysRole.getRole_cd());
		if(CommUtil.isNotNull(updateSysRole)){
			updateSysRole.setRole_name(sysRole.getRole_name());
			try {
				sysRoleServiceImpl.saveOrUpdate(updateSysRole);
				rstMap.put("ret", "success");
				rstMap.put("msg", updateSysRole.getRole_cd() + "更新成功");
			} catch (Exception e) {
				rstMap.put("ret", "error");
				rstMap.put("msg", updateSysRole.getRole_cd() + "更新失败");
			}
		}else {
			rstMap.put("ret", "error");
			rstMap.put("msg", sysRole.getRole_cd() + "更新失败");
		}
		return rstMap;
	}
	/**
	 * @author LiuTao @date 2018年7月19日 上午10:19:49 
	 * @Title: showSysRoleAuth 
	 * @Description: TODO(Describe) 
	 * @param reqMap
	 * @return
	 */
	@RequestMapping(value = "/showSysRoleAuth")
	public Map<String, Object> showSysRoleAuth(@RequestParam Map<String,Object> reqMap){
		Map<String, Object> rstMap = new HashMap<String, Object>();
		SysRoleAuth sysRoleAuth=new SysRoleAuth();
		sysRoleAuth.setRegist_cd(reqMap.get("regist_cd").toString());
		sysRoleAuth.setRole_cd(reqMap.get("role_cd").toString());
		sysRoleAuth.setAuth_type(reqMap.get("auth_type").toString());
		if(reqMap.get("q_auth_cd")!=null&&reqMap.get("q_auth_cd")!=""){
			sysRoleAuth.setAuth_cd(reqMap.get("q_auth_cd").toString());
		}
		int page = Integer.parseInt((String) reqMap.get("start"));
		int size = Integer.parseInt((String) reqMap.get("length"));
		Map<String, Object> listWithCount = sysRoleAuthServiceImpl.findAllByEntityPageSizeWithCount(sysRoleAuth, page, size);
		try {
			rstMap = CommUtil.transSrcMapToWebMap(listWithCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstMap;
	}
	/**
	 * @author LiuTao @date 2018年8月22日 上午10:03:14 
	 * @Title: addSysRoleAuth 
	 * @Description: TODO(Describe) 
	 * @param sysRoleAuth
	 * @return
	 */
	@RequestMapping(value = "/addSysRoleAuth")
	@Transactional(propagation = Propagation.REQUIRED)	
	public Map<String,String> addSysRoleAuth(@RequestBody SysRoleAuth sysRoleAuth) {
		Map<String,String> rstMap=new HashMap<String, String>();
		SysRoleAuth addSysRoleAuth = new SysRoleAuth();
		try {
			addSysRoleAuth = sysRoleAuthServiceImpl.saveEntity(sysRoleAuth);
		} catch (Throwable e) {
			rstMap.put("ret", "error");
			rstMap.put("msg", sysRoleAuth.getRole_cd() + "已存在,新增失败");
			return rstMap;
		}
		if(addSysRoleAuth == null){
			rstMap.put("ret", "error");
			rstMap.put("msg",  sysRoleAuth.getRole_cd() + "新增失败,请联系管理员");
			return rstMap;
		}
		rstMap.put("ret", "success");
		rstMap.put("msg", addSysRoleAuth.getRole_cd()+ "新增成功");
		return rstMap;	

	}
	/**
	 * @author LiuTao @date 2018年8月22日 上午11:50:08 
	 * @Title: deleteSysRoleAuth 
	 * @Description: 删除sys_role_auth记录 
	 * @param reqMap
	 * @return
	 */
	@RequestMapping(value = "/showSysRoleAuth", method = {RequestMethod.DELETE})
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> deleteSysRoleAuth(@RequestBody SysRoleAuth sysRoleAuth){
		Map<String, Object> rstMap = new HashMap<String, Object>();
		try {
			sysRoleAuthServiceImpl.deleteEntity(sysRoleAuth);
			rstMap.put("ret", "success");
			rstMap.put("msg", sysRoleAuth.getAuth_cd() + "删除成功");
		} catch (Exception e) {
			rstMap.put("ret", "error");
			rstMap.put("msg", sysRoleAuth.getAuth_cd() + "删除失败");
		}
		return rstMap;
	}
	/**---------------------------------------SysAuth------------------------------------------------*/
	/**
	 * @author LiuTao @date 2018年6月24日 上午10:29:39 
	 * @Title: allSysAuth 
	 * @Description: TODO(查询所有的菜单) 
	 * @param reqMap
	 * @return
	 */
	@RequestMapping(value="/allSysAuth")
	public Map<String, Object> allSysAuth(@ModelAttribute("SysUser") SysUser sysUser){
		Map<String, Object> rstMap = new HashMap<String, Object>();
		SysAuth sysAuth = new SysAuth();
		sysAuth.setAuth_type(AUTHTYPE);
		sysAuth.setRank(1);
		sysAuth.setRegist_cd(sysUser.getRegistCd());
		List<SysAuth> sysAuthList = sysAuthServiceImpl.selectAllEntities(sysAuth);
		rstMap.put("menu", reGetMenu(sysAuth, sysAuthList, sysAuth.getRank(), true));
		return rstMap;		
	}
	/**
	 * @author LiuTao @date 2018年8月22日 下午9:18:33 
	 * @Title: updateSysAuth 
	 * @Description: TODO(Describe) 
	 * @param sysAuth
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value="/updateSysAuth")
	public Map<String, Object> updateSysAuth(@RequestBody SysAuth sysAuth, @ModelAttribute("SysUser") SysUser sysUser){
		Map<String, Object> rstMap = new HashMap<String, Object>();
		sysAuth.setRegist_cd(sysUser.getRegistCd());
		try {
			sysAuthServiceImpl.saveOrUpdate(sysAuth);
			rstMap.put("ret", "success");
			rstMap.put("msg", sysAuth.getAuth_cd() + "更新成功");
		} catch (Exception e) {
			rstMap.put("ret", "error");
			rstMap.put("msg", sysAuth.getAuth_cd() + "更新失败");
		}
		return rstMap;
	}
	/**
	 * @author LiuTao @date 2018年8月22日 下午9:19:06 
	 * @Title: addSysAuth 
	 * @Description: TODO(Describe) 
	 * @param sysAuth
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value="/addSysAuth")
	public Map<String, Object> addSysAuth(@RequestBody SysAuth sysAuth, @ModelAttribute("SysUser") SysUser sysUser){
		Map<String, Object> rstMap = new HashMap<String, Object>();
		sysAuth.setRegist_cd(sysUser.getRegistCd());
		try {
			sysAuthServiceImpl.saveEntity(sysAuth);
			rstMap.put("ret", "success");
			rstMap.put("msg", sysAuth.getAuth_cd() + "新增成功");
		} catch (Throwable e) {
			rstMap.put("ret", "error");
			rstMap.put("msg", sysAuth.getAuth_cd() + "新增失败");
		}
		return rstMap;
	}
	/**
	 * @author LiuTao @date 2018年8月22日 下午9:24:29 
	 * @Title: deleteSysAuth 
	 * @Description: TODO(Describe) 
	 * @param sysAuth
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value="/deleteSysAuth")
	public Map<String, Object> deleteSysAuth(@RequestBody SysAuth sysAuth, @ModelAttribute("SysUser") SysUser sysUser){
		Map<String, Object> rstMap = new HashMap<String, Object>();
		try {
			sysAuth.setRegist_cd(sysUser.getRegistCd());
			//1.删除sys_role_auth中的记录
			SysRoleAuth sysRoleAuth = new SysRoleAuth();
			sysRoleAuth.setRegist_cd(sysUser.getRegistCd());
			sysRoleAuth.setAuth_cd(sysAuth.getAuth_cd());
			sysRoleAuth.setAuth_type(sysAuth.getAuth_type());
			List<SysRoleAuth> sysRoleAuthList = sysRoleAuthServiceImpl.findAllByEntity(sysRoleAuth);
			sysRoleAuthServiceImpl.deleteEntities(sysRoleAuthList);
			//2.再删除sys_auth中的记录
			sysAuthServiceImpl.deleteEntity(sysAuth);
			rstMap.put("ret", "success");
			rstMap.put("msg", sysAuth.getAuth_cd() + "删除成功");
		} catch (Throwable e) {
			rstMap.put("ret", "error");
			rstMap.put("msg", sysAuth.getAuth_cd() + "删除失败");
		}
		return rstMap;
	}
	
	
	
	/**---------------------------------------分隔符------------------------------------------------*/
	
	/**
	 * @author LiuTao @date 2018年5月7日 下午7:46:26 
	 * @Title: reGetMenu 
	 * @Description: TODO(根据sysAuth查询子项) 
	 * @param entity 权限模板
	 * @param parentMenu 父级菜单权限
	 * @param rank 层级
	 * @param flag 是否控制权限 true控制权限 false不控制
	 * @return
	 */
	private List<SysAuth> reGetMenu(SysAuth entity, List<SysAuth> parentMenu, int rank, Boolean flag) {
		// 取1级菜单		
		List<SysAuth> removeList = new ArrayList<SysAuth>();// list 遍历元素时不允许删除元素,创建一个List用于储存删除的元素,遍历后集中集中删除
		/**
		 * 循环遍历这一级菜单,分别获取下一级级菜单
		 */
		for (SysAuth sysAuth : parentMenu) {// 循环处理父菜单
			/**
			 * 判断user是否拥有权限
			 * 无父级菜单权限,子菜单权限无效
			 */
			if (strInArray(sysAuth.getAuth_cd(), strArray) && flag) { 
				removeList.add(sysAuth);// 放入删除List中
				continue;
			}
			entity.setRank(rank + 1);// 取下一级菜单级菜单
			entity.setParent_auth_cd(sysAuth.getAuth_cd());// 设置父级cored
			List<SysAuth> childMenu = new ArrayList<SysAuth>();
			
			childMenu.addAll(sysAuthServiceImpl.selectAllEntities(entity));
			if (childMenu.size() > 0) {
				sysAuth.setChildren(reGetMenu(entity, childMenu, entity.getRank(), flag));// 递归处理
				sysAuth.setHaschild("Y");
			}
		}
		parentMenu.removeAll(removeList);
		return parentMenu;
	}
	/**
	 * @author LiuTao @date 2018年5月7日 下午7:45:42 
	 * @Title: strInArray 
	 * @Description: TODO(判断字符串数组是否包含字符串) 
	 * @param str
	 * @param strs
	 * @return
	 */
	public boolean strInArray(String str, String[] strs) {
		for (int i = 0; i < strs.length; i++) {
			if (str.equals(strs[i])) {
				return false;
			}
		}
		return true;
	}
}

