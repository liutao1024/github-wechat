package cn.spring.mvn.web.test;

import cn.spring.mvn.comm.util.CommUtil;
import cn.spring.mvn.system.entity.SysUser;
import cn.spring.mvn.web.zport.SavusrInput;

//import cn.spring.mvn.basic.ibatis.IBatisTParam;

//import org.junit.Test;
//
//import cn.spring.mvn.core.loan.Loan;
public class WebTest {
	

//	@Test
//	public void Test001(){
//		Loan.print();
//	}

	public static void main(String[] args) {
		Test002();
	}
	
	private static void Test002() {
//		Student s = new Student();
//		s.setName("liutao");
//		IBatisTParam<Student> i = new IBatisTParam<Student>(s, 1, 2, "", "");
//		System.out.println(i);
		SavusrInput input = new SavusrInput();
		input.setOpetyp("A");
		input.setUserid("10001");
		input.setRegistCd("001");
		SysUser sysUser = new SysUser();
		String str = new String("1213");
		String sb = new String();
		try {
			CommUtil.copyProperties(input, sysUser);
			CommUtil.copyProperties(str, sb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sysUser);
	}
}
