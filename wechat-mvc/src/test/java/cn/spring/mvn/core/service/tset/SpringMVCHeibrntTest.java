package cn.spring.mvn.core.service.tset;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.spring.mvn.basic.tools.BasicReflection;
import cn.spring.mvn.comm.security.MD5Util;
import cn.spring.mvn.comm.tools.FileTool;
import cn.spring.mvn.comm.util.CommUtil;
import cn.spring.mvn.comm.util.SpringContextUtil;
import cn.spring.mvn.socket.server.old.SocketHandlerImpl;
import cn.spring.mvn.system.entity.SysBatchTaskTimer;
import cn.spring.mvn.system.entity.service.SysBatchTaskTimerService;
//import cn.spring.mvn.task.TaskManager;
//import cn.spring.mvn.task.job.TaskJobGroup;
import cn.spring.mvn.system.entity.SysAuth;
import cn.spring.mvn.system.entity.SysDict;
import cn.spring.mvn.system.entity.SysRole;
import cn.spring.mvn.system.entity.SysRoleAuth;
import cn.spring.mvn.system.entity.SysUser;
import cn.spring.mvn.system.entity.service.SysAuthService;
import cn.spring.mvn.system.entity.service.SysRoleAuthService;
import cn.spring.mvn.system.entity.service.SysRoleService;
import cn.spring.mvn.system.entity.service.SysUserRoleService;
import cn.spring.mvn.system.entity.service.SysUserService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-hibernate.xml" })
public class SpringMVCHeibrntTest {
	private static final Logger LOGGER = Logger.getLogger(SpringMVCHeibrntTest.class);
	@Autowired
	private SysUserService sysUserServiceImpl;
	@Autowired
	private SysRoleService sysRoleServiceImpl;
	@Autowired
	private SysRoleAuthService sysRoleAuthServiceImpl;
	@Autowired
	private SysUserRoleService sysUserRoleServiceImpl;
	@Autowired 
	private SysAuthService sysAuthServiceImpl;
	@Autowired
	private SysBatchTaskTimerService systemBatchTaskDispathControlImpl;
	/**
	 * @Test
	 */
	
	
	@Test
	public void TestGetApplicationContext(){
		ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
		if(CommUtil.isNotNull(applicationContext)){
			System.out.println("111"+applicationContext.getId());
			SysAuthService s = applicationContext.getBean(SysAuthService.class);
			SysAuth entity = new SysAuth();
			entity.setRank(2);
			List<SysAuth> m = s.findAllByEntity(entity);
			for (SysAuth sysAuth : m) {
				System.out.println(sysAuth);
			}
		}else {
			System.out.println("applicationContext is null");
		}
	}
	
	
	@Test
	public void TestClient(){
		
	}
	
	
	@Test
	public void TestFindAllByEntity(){
		SysRoleAuth sysRoleAuth = new SysRoleAuth();
		sysRoleAuth.setRegist_cd("001");
		sysRoleAuth.setAuth_cd("203000011");
		sysRoleAuth.setAuth_type("2");
		List<SysRoleAuth> list = sysRoleAuthServiceImpl.findAllByEntity(sysRoleAuth);
		System.out.println(list.get(0));
	}
	
	@Test
	public void TestListSize(){
		SysRole s = sysRoleServiceImpl.selectOneByPrimeKey("001", "1", "120");
		System.out.println(s);
		s.setRole_name("浣犲ソ");
		sysRoleServiceImpl.saveOrUpdate(s);
	}

	
	@Test
	public void TestImpl(){
		Map<String, Object> requestMap = new HashMap<String, Object>();
		
		Map<String, Object> sysMap = new HashMap<String, Object>();
		Map<String, Object> commMap = new HashMap<String, Object>();
		Map<String, Object> srcMap = new HashMap<String, Object>();
		sysMap.put("cropno", "001");
		sysMap.put("servtp", "01");
		sysMap.put("servno", "02");
		
		commMap.put("corecd", "opcust");
		commMap.put("asktyp", "Q");//璇锋眰绫诲瀷:Q--鏌ヨ(杩斿洖鐨勬槸涓�涓猯ist),D--鎵ц(杩斿洖鐨勬槸涓�涓粨鏋�)
		
		srcMap.put("idtftp", "01");
		srcMap.put("idtfno", "511024199112030398");
		srcMap.put("custna", "娓ｆ福杈�");
		requestMap.put("sys", sysMap);
		requestMap.put("comm", commMap);
		requestMap.put("request", srcMap);		
		
		JSONObject obj = new JSONObject(requestMap);
		String str = SocketHandlerImpl.callInterface(obj.toString());
		System.out.println(str);
	}
	
	
	
	
	@Test
	public void TestSelectAllByPage(){
		String hqlStr = "from SysUser";
		int page = 10;
		int size = 10;
		Map<String, Object> map= sysUserServiceImpl.findAllByHqlPageSizeWithCount(hqlStr, page, size);
		@SuppressWarnings("unchecked")
		List<SysUser> list = (List<SysUser>) map.get("result");
		long count = (long) map.get("count");
		System.out.println(list.size());
		System.out.println(count);
		for (SysUser sysUser : list) {
			System.out.println(sysUser.getUserid());
		}
	}
	
	@Test
	public void TestQuartz(){
//		String jobName = "娴嬭瘯";
//		long time = 2000;
//		TaskManager.addJobByTime(TaskJobGroup.class, jobName, "triggerGroupName", "jobGroupName", time);
	}
	
	@Test
	public void TestTask(){
		String TRIGGER_GROUP_NUMBER = "10001";
		String JOB_EXECUTE_FLAG = "Y";
		String hqlStr_Flage_Y = "from SystemBatchTaskDispathControl where TRIGGER_GROUP_NUMBER = '" + TRIGGER_GROUP_NUMBER + "' and JOB_EXECUTE_FLAG = '"+ JOB_EXECUTE_FLAG +"'" ;
		List<SysBatchTaskTimer> systemBatchTaskDispathControlList = 
				systemBatchTaskDispathControlImpl.findAllByHql(hqlStr_Flage_Y);
		System.out.println(systemBatchTaskDispathControlList.size());
		for (SysBatchTaskTimer systemBatchTaskDispathControl : systemBatchTaskDispathControlList) {
			String jobGroupClazzName = "cn.spring.mvc.global.comm.batch.task.impl." + systemBatchTaskDispathControl.getJobClass();
			String jobGroupMethodName = systemBatchTaskDispathControl.getJobMethod();
			System.out.println("jobGroupClassName:"+jobGroupClazzName);
			System.out.println("jobGroupMethodName:"+jobGroupMethodName);
			//鍚屾牱閫氳繃鍙戝皠鎵惧埌绫昏皟鐢ㄥ搴旂殑鏂规硶
			@SuppressWarnings("rawtypes")
			Class[] classes = {};
			Object[] objects = {};
			try {
				BasicReflection.executeMethodByReflectClassNameAndMethodName(jobGroupClazzName, jobGroupMethodName, classes, objects);
				// TODO:鐧昏鎵ц鎴愬姛淇℃伅
			} catch (Exception e) {
				// TODO:寮傚父淇℃伅
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void TestToGetAMap(){
		SysDict object = new SysDict();
		object.setDictType("U_DDSDF");
		Map<String, Object> map = BasicReflection.getMapByReflectWithOutNullValueObject(object);
		System.out.println(map);
	}
	
	@Test
	public void TestAnnotation(){
		SysDict object = new SysDict();
		
		BasicReflection.getAttributeColumnAnnotationMapByReflectObject(object);
		BasicReflection.getAttributeAnnotationsByReflectObject(object);
		BasicReflection.getClassAnnotationListByReflectObject(object);
		BasicReflection.getMethodAnnotationsByReflectObject(object);
	}
	
	
	
	@Test
	public void TestToGet(){
		try {
			SysDict object = new SysDict();
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());  
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
			for (PropertyDescriptor property : propertyDescriptors) {  
				String key = property.getName();  
				// 杩囨护class灞炴��  
				if (!key.equals("class")) {  
					// 寰楀埌property瀵瑰簲鐨刧etter鏂规硶  
					Method getter = property.getReadMethod();  
					Object value = getter.invoke(object); 
					if(CommUtil.isNotNull(value)){
						System.out.println("key"+ key+",value"+value); 
					}
				}  
				
			}  
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Test
	public void TestSysDict(){
		Class<SysDict> c = SysDict.class;
		Field[]  f = c.getDeclaredFields();
		for (Field field : f) {
			boolean b = field.isAnnotationPresent(Column.class);
			if(b){
				Column x = field.getAnnotation(Column.class);
				System.out.println(field.getDeclaringClass());
				System.out.println("columnDefinition:" + x.columnDefinition());
				System.out.println("insertable:"+x.insertable());
				System.out.println("length"+x.length());
				System.out.println("name"+x.name());
				System.out.println("nullable"+x.nullable());
				System.out.println("precision"+x.precision());
				System.out.println("scale"+x.scale());
				System.out.println("table"+x.table());
				System.out.println("toString"+x.toString());
				System.out.println("unique"+x.unique());
				System.out.println("updatable"+x.updatable());
				System.out.println("annotationType"+x.annotationType());
				System.out.println("getClass"+x.getClass());
			}
		}
		Method[] arr = c.getMethods();
		AnnotatedType[] m = c.getAnnotatedInterfaces();
		for(AnnotatedType y : m){
			System.out.println(y);
		}
		Annotation[] n = c.getAnnotations();
		for (Annotation annotation : n) {
			System.out.println(annotation);
		}
		TypeVariable<Class<SysDict>>[] o = c.getTypeParameters();
		for (TypeVariable<Class<SysDict>> typeVariable : o) {
			System.out.println(typeVariable.getTypeName());
		}
//		c.getAnnotationsByType(String.class);
		for(Method a : arr){
			if(a.isAnnotationPresent(Column.class)){
				Column x = a.getAnnotation(Column.class);
				System.out.println("columnDefinition:" + x.columnDefinition());
				System.out.println("insertable:"+x.insertable());
				System.out.println("length"+x.length());
				System.out.println("name"+x.name());
				System.out.println("nullable"+x.nullable());
				System.out.println("precision"+x.precision());
				System.out.println("scale"+x.scale());
				System.out.println("table"+x.table());
				System.out.println("toString"+x.toString());
				System.out.println("unique"+x.unique());
				System.out.println("updatable"+x.updatable());
				System.out.println("annotationType"+x.annotationType());
				System.out.println("getClass"+x.getClass());
			}
		}
	}
	
	@Test
	public void TestSysRole(){
//		String hql = "from SysAuthRole";
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("role_cd", "3221");
//		List<SysAuthRole> list = sysAuthRoleServiceImpl.find(hql);
//		for (SysAuthRole sysRole : list) {
//			System.out.println(sysRole.toString());
//		}
		
	}
	
	@Test
	public void addSysUser() {
		String num = UUID.randomUUID().toString();
		SysUser sysUser = new SysUser();
		sysUser.setRegistCd("001");
		sysUser.setUserid("10001");
		sysUser.setUserna("鍙ゅぉ涔�");
		sysUser.setPasswd(MD5Util.md5EncryptString("123456"));
		sysUser.setUserst("0");
		sysUser.setErrort(0);
		try {
			sysUser = sysUserServiceImpl.add(sysUser);
			LOGGER.info("-----0-----SUCCESS");
		} catch (Throwable e) {
			LOGGER.info("-----1-----" + e.getMessage());
		}
		LOGGER.info(num + JSON.toJSONString(sysUser));
	}
	
	@Test
	public void delet() {
		SysUser sysUser = sysUserServiceImpl.selectOneByPrimeKey("001", "709422963");
		sysUserServiceImpl.delete(sysUser);
	}
	
	@Test
	public void selectOne(){
		SysUser sysUser = sysUserServiceImpl.selectOneByPrimeKey("002", "709422963");
		System.out.println(sysUser.getUserid() + ", " + sysUser.getUserna() + ", " + sysUser.getPasswd());
	}
	
	@Test
	public void findAll(){
		List<SysUser> sysUserList = sysUserServiceImpl.selectAll();
		for(SysUser sysUser : sysUserList){
			System.out.println(sysUser.getUserid() + ", " + sysUser.getUserna() + ", " + sysUser.getPasswd());
		}
	}
	
	@Test
	public void count(){
		System.out.println(sysUserServiceImpl.count());
	}
	
	@Test
	public void update(){
		SysUser sysUser = sysUserServiceImpl.selectOneByPrimeKey("001", "709422963");
		sysUser.setUserna("寮犲杈�");
		sysUser.setPasswd(MD5Util.md5EncryptString("654321"));
		sysUserServiceImpl.update(sysUser);
	}
	@Test
	public void unZip(){
		try {
			FileTool.unZipToFile("D:\\usr\\files\\ht\\report\\txtReport\\temp\\201804.zip", "D:\\usr\\files\\ht\\report\\txtReport\\temp\\unzip\\");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void readFiles() {
		String srcFileStr = "D:\\usr\\files\\ht\\report\\txtReport\\temp\\unzip\\";
		delWithDataFile(srcFileStr, "GBK");
	}
	/**
	 * @author LiuTao @date 2018骞�5鏈�14鏃� 涓婂崍10:54:18 
	 * @Title: delWithDataFile 
	 * @Description: TODO(Describe) 
	 * @param srcFileStr
	 */
	public void delWithDataFile(String srcFileStr, String charSet) {
		 File srcFile = new File(srcFileStr);
		 if(srcFile.exists()){
			 if(srcFile.isFile()){
				 BufferedReader bufferedReader = null;
				 InputStreamReader inputStreamReader = null;
				 try {
					 inputStreamReader = new InputStreamReader(new FileInputStream(srcFileStr), charSet);
					 bufferedReader = new BufferedReader(inputStreamReader);
					//浠ヨ涓哄崟浣嶈鍙栨枃浠跺唴瀹�,涓�娆¤涓�鏁磋
					String tempString = null;
					int line = 1;
					// 涓�娆¤鍏ヤ竴琛�,鐩村埌璇诲叆null涓烘枃浠剁粨鏉�
					while ((tempString = bufferedReader.readLine()) != null) {
						// 鏄剧ず琛屽彿
						System.out.println("line " + line + ": " + tempString);
						line++;
					}
					bufferedReader.close();
				 } catch (IOException e) {
					e.printStackTrace();
				 } finally {
					if (bufferedReader != null) {
						try {
							bufferedReader.close();
						} catch (IOException e1) {
						}
					}
				}
			 }else if(srcFile.isDirectory()){
				 File[] fileArr = srcFile.listFiles();
				 for(int i = 0; i < fileArr.length; i ++){
					 File str = fileArr[i];
					 delWithDataFile(str.toString(), charSet);
				 }
			 }
		 }
	}
}
