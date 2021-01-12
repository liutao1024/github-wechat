package cn.spring.mvn.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import cn.spring.mvn.comm.util.CommUtil;
import cn.spring.mvn.core.entity.Customer;
import cn.spring.mvn.core.entity.service.CustomerService;
import cn.spring.mvn.system.entity.SysUser;
/**
 * @author LiuTao @date 2018年6月9日 下午10:21:54
 * @ClassName: CustController 
 * @Description: 客户相关页面Controller
 */
@Controller("CustController")
@RequestMapping(value = "cust")
@ResponseBody
@SessionAttributes("SysUser")
public class CustController {
	@Autowired
	private CustomerService customerServiceImpl;
	/**
	 * @author LiuTao @date 2018年6月9日 下午7:21:05 
	 * @Title: getCust 
	 * @Description: TODO(Describe) 
	 * @param reqMap
	 * @param sysUser
	 * @return
	 */
	@RequestMapping(value ="/custinfo")
	public Map<String,Object> getCustInfo(@RequestParam Map<String, Object> reqMap, @ModelAttribute("SysUser") SysUser sysUser){
		Map<String, Object> rstMap = new HashMap<String, Object>();
		Customer customer = new Customer();
		customer.setCustno(reqMap.get("custno").toString());
		customer.setCustna(reqMap.get("custna").toString());
		customer.setIdtftp(reqMap.get("idtftp").toString());
		customer.setIdtfno(reqMap.get("idtfno").toString());
		int page = Integer.parseInt((String) reqMap.get("start"));
		int size = Integer.parseInt((String) reqMap.get("length"));
		Map<String, Object> listWithCount = customerServiceImpl.selectMapWithCountAndListByEntityAndPageSize(customer, page, size);
		try {
			rstMap = CommUtil.transSrcMapToWebMap(listWithCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rstMap;
	}
	/**
	 * @author LiuTao @date 2018年6月10日 下午8:50:47 
	 * @Title: updateCustUser 
	 * @Description: 更新客户信息
	 * @param reqMap
	 * @return
	 */
	@RequestMapping(value = "/update")
	public  Map<String, Object>  updateCustUser(@RequestBody Map<String, Object> reqMap){
		Map<String, Object> rspMap = new HashMap<String, Object>();
		String idtftp = (String) reqMap.get("idtftp");
		String idtfno = (String) reqMap.get("idtfno");
		//校验为空
		Customer customer = new Customer();
		customer.setIdtftp(idtftp);
		customer.setIdtfno(idtfno);
		customer = customerServiceImpl.selectOneEntity(customer);
		try {
			customer.setTeleno((String) reqMap.get("teleno"));
			customer.setCustst((String) reqMap.get("custst"));
			customer.setAddres((String) reqMap.get("addres"));
			customer.setCustno((String) reqMap.get("custno"));
			customer.setCustna((String) reqMap.get("custna"));
			customerServiceImpl.updateEntity(customer);
			rspMap.put("retCode", "0000");
		} catch (Exception e) {
			e.printStackTrace();
			rspMap.put("retCode", "101");
			rspMap.put("retMsg", e.getMessage());
		}
		return rspMap;
	}
}
