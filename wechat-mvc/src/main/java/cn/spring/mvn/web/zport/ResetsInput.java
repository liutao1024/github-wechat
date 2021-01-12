package cn.spring.mvn.web.zport;
/**
 * @Author LiuTao @Date 2019年1月2日 下午8:11:21
 * @ClassName: ResetsInput 
 * @Description: TODO(Describe)
 */
public class ResetsInput {
	private String cropno;
	private String userid;
	private String passwd;
	private boolean ischck;
	
	public String getCropno() {
		return cropno;
	}
	public void setCropno(String cropno) {
		this.cropno = cropno;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public boolean getIschck() {
		return ischck;
	}
	public void setIschck(boolean ischck) {
		this.ischck = ischck;
	}
	
}
