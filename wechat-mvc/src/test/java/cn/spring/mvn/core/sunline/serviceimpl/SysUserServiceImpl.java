package cn.spring.mvn.core.sunline.serviceimpl;

import org.springframework.stereotype.Service;

import cn.spring.mvn.core.sunline.domain.SysUser;
import cn.spring.mvn.core.sunline.service.SysUserService;

@Service("SysUserService")
public class SysUserServiceImpl implements SysUserService {
	@Override
	public SysUser login(SysUser user) {
		System.out.println(user.getPasswd());
		// Map<String, Object> recvMsg=service.callSerive("socket",
		// "{'userid':'"+user.getUserid()+"','passwd':'"+user.getPasswd()+"','pswdfg':'"+user.getPswdfg()+"'}");

		return user;
	}

}
