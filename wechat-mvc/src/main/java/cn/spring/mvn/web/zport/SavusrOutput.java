package cn.spring.mvn.web.zport;

import java.util.List;

import cn.spring.mvn.system.entity.SysUser;

public class SavusrOutput {
	private int count;
	private List<SysUser> infos;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<SysUser> getInfos() {
		return infos;
	}
	public void setInfos(List<SysUser> infos) {
		this.infos = infos;
	}
}
