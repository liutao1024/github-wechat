package cn.spring.mvn.web.zport;


public class SavusrInput {
	
	private String opetyp;//维护类型:A-新增,D-删除,Q-查询,U-更新
	private String registCd;//机构代码
	private String userid ;//用户ID
	private String userna;//用户名称
	private String passwd;//密码
	private String brchno;//所属部门
	private int errort;//密码错误次数   密码输错最多错误5次
	private int maxert;//密码最大错误次数
	private String userst;//登录状态  0--未登录,1--已登录
	private String status;//状态  0--未启用,1--已启用
	private String userlv;//柜员等级
	
	public String getOpetyp() {
		return opetyp;
	}
	public void setOpetyp(String opetyp) {
		this.opetyp = opetyp;
	}
	public String getRegistCd() {
		return registCd;
	}
	public void setRegistCd(String registCd) {
		this.registCd = registCd;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserna() {
		return userna;
	}
	public void setUserna(String userna) {
		this.userna = userna;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getBrchno() {
		return brchno;
	}
	public void setBrchno(String brchno) {
		this.brchno = brchno;
	}
	public int getErrort() {
		return errort;
	}
	public void setErrort(int errort) {
		this.errort = errort;
	}
	public int getMaxert() {
		return maxert;
	}
	public void setMaxert(int maxert) {
		this.maxert = maxert;
	}
	public String getUserst() {
		return userst;
	}
	public void setUserst(String userst) {
		this.userst = userst;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserlv() {
		return userlv;
	}
}
