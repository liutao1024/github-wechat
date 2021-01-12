package cn.spring.mvn.core.service.tset;

import org.junit.Test;

import cn.spring.mvn.basic.util.BasicUtil;

public class JustTest {
	
	
	@Test
	public void Test0004(){
		String s = "20180707";
		String t = BasicUtil.getDateStrByDateStrAddDays(s, 0);
		System.out.println(s);
		System.out.println(t);
	}
	
	@Test
	public void Test0003(){
		System.out.println(JustTest.class);
		JustTest s = new JustTest();
		System.out.println(s.getClass());
	}
	
	@Test
	public void Test0002(){
		boolean a = false;
		System.out.println(a);
	}
	
	@Test
	public void Test0001(){
		String charSet = System.getProperty("file.encoding");
		System.out.println(charSet);
	}
	
	
}
