package org.spring.boot.wechat.basic;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;

/**
 * @author LiuTao @date 2018年5月25日 下午1:03:27
 * @ClassName: CommUtil 
 * @Description: 公共工具类/反射运用
 */
public class CommUtil extends BasicUtil{//继承自BasicUtil
	private static final Logger LOGGER = LoggerFactory.getLogger(CommUtil.class);
	public final static String SORT="ASC";
	public final static String DOT = ".";
	public final static String ACROSS = "-";
	public final static String JOB = ".job.";
	public final static String JOBIMPL = ".job.impl.";
	public final static String ZPORT = ".zport.";
	public final static String ZPORTIMPL = ".zport.impl.";
	/**
	 * @author LiuTao @date 2018年11月8日 下午12:31:02 
	 * @Title: formatMapForWeb 
	 * @Description: 为WEB端返回分页查询的map 
	 * @param list
	 * @param count
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> formatMapForWeb(List<?> list, long count, int page, int size) throws Exception{
		Map<String,Object> rstMap=new HashMap<String, Object>();
		try {
//			if(list.isEmpty()){//data主体
//				rstMap.put("iDisplayStart", page);
//				rstMap.put("iDisplayLength", size);
//				rstMap.put("iColumns", list.size());
//				rstMap.put("sEcho", System.currentTimeMillis());
//				rstMap.put("data", list);
//				rstMap.put("iTotalDisplayRecords", count);
//				rstMap.put("iTotalRecords", count);
//				rstMap.put("customActionMessage", "暂无返回数据数据");
//				rstMap.put("customActionStatus", "ok");
//			}else {
				rstMap.put("iDisplayStart", page);
				rstMap.put("iDisplayLength", size);
				rstMap.put("iColumns", list.size());
				rstMap.put("sEcho", System.currentTimeMillis());
				rstMap.put("data", list);
				rstMap.put("iTotalDisplayRecords", count);
				rstMap.put("iTotalRecords", count);
//			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return rstMap;	
	}
	/**
	 * @author LiuTao @date 2018年6月6日 下午1:12:45 
	 * @Title: transSrcMapToWebMap 
	 * @Description: 将带count的Map转换成web页面需要的Map 
	 * @param srcMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> transSrcMapToWebMap(Map<String, Object> srcMap) throws Exception{
		Map<String,Object> rstMap=new HashMap<String, Object>();
		try {
			List<T> list = (List<T>) srcMap.get("result");
			long count = (long) srcMap.get("count");
			int page = (int) srcMap.get("page");
			int size = (int) srcMap.get("size");
//			if(list.isEmpty()){//data主体
//				rstMap.put("iDisplayStart", page);
//				rstMap.put("iDisplayLength", size);
//				rstMap.put("iColumns", list.size());
//				rstMap.put("sEcho", System.currentTimeMillis());
//				rstMap.put("data", list);
////				rstMap.put("iTotalDisplayRecords", count);
////				rstMap.put("iTotalRecords", count);
//				rstMap.put("customActionMessage", "暂无返回数据数据");
//				rstMap.put("customActionStatus", "ok");
//			}else {
				rstMap.put("iDisplayStart", page);
				rstMap.put("iDisplayLength", size);
				rstMap.put("iColumns", list.size());
				rstMap.put("sEcho", System.currentTimeMillis());
				rstMap.put("data", list);
				rstMap.put("iTotalDisplayRecords", count);
				rstMap.put("iTotalRecords", count);
//			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return rstMap;		
	}
	
	/**
	 * @author LiuTao @date 2018年5月30日 下午9:22:16 
	 * @Title: getClassName 
	 * @Description: TODO(Describe) 
	 * @param object
	 * @return
	 */
	public static String getClassName(Object object){
		String objectName = object.getClass().getName();
		String[] strArray = objectName.split(".");
		String className = strArray[strArray.length - 1];
		return className;
	}
	
	
	/**
	 * @author LiuTao @date 2018年5月26日 下午9:47:49 
	 * @Title: transFormObjetToMap 
	 * @Description: 将实体类转换成Map
	 * @param object
	 * @return
	 */
	public static Map<String, Object> transFormObjetToMap(Object object){
		if (object == null) {  
	        return null;  
	    }  
	    Map<String, Object> rstMap = new HashMap<String, Object>();  
	    try {  
	        BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());  
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
	        for (PropertyDescriptor property : propertyDescriptors) {  
	            String key = property.getName();  
	            // 过滤class属性  
	            if (!key.equals("class")) {  
	                // 得到property对应的getter方法  
	                Method getter = property.getReadMethod();  
	                Object value = getter.invoke(object); 
	                if(isNotNull(value)){
	                	rstMap.put(key, value);  
	                }
	            }  
	        }  
	    } catch (Exception e) {  
	    	LOGGER.info("类转换成map时遇到:" + e.getMessage());  
	    }  
	    return rstMap;  
	}
	
	
	//entity是否可以直接获取到Cloum注解中的name直接传一个Entity进来就匹配上对应的值
	public <T> T getSqlStrByEntity(T t){
//		Class class = t.getClass();
//		object.getM
		return null;
	}
	/**
	 * @author LiuTao @date 2018年6月2日 下午6:00:40 
	 * @Title: getSortBySort 
	 * @Description: 根据sort对list进行排序 
	 * @param list
	 * @param sort
	 * @return
	 */
//	public static <T> List<T> getSortBySort(List<T> list, Sort sort){
//		return null;
//	} 
	
     
	
	public <T> T getFunction(T t){
		return t;
	}
	/**
	 * @author LiuTao @date 2018年10月17日 上午10:10:52 
	 * @Title: getObject 
	 * @Description: 前者为空返回后者  三目运算
	 * @param obj
	 * @param jacObj
	 * @return
	 */
	public static Object getObject(Object obj, Object jacObj){
		return null == obj ? jacObj : obj;
	}
	/**
	 * @author LiuTao @date 2018年5月22日 下午11:06:35 
	 * @Title: isNotNull 
	 * @Description: TODO(判断对象是否为空) 
	 * @param object
	 * @return
	 */
//	public static boolean isNotNull(Object object){
//		return !isNull(object);
//	}
	/**
	 * @author LiuTao @date 2018年5月22日 下午9:48:39 
	 * @Title: equal 
	 * @Description: TODO(判断对象是否是同一个对象) 
	 * @param objectOne
	 * @param objectTwo
	 * @return
	 */
	public static boolean equal(Object objectOne, Object objectTwo) {
		boolean rstBoolean = false;
		if(objectOne == objectTwo){//是否同一对象
			rstBoolean = true;
		}else if(objectOne.equals(objectTwo)){//值是否相等
			rstBoolean = true;
		}
		return rstBoolean;
	}
	/**
	 * @Author LiuTao @Date 2019年1月2日 下午8:20:46 
	 * @Title: notEqual 
	 * @Description: TODO(Describe) 
	 * @param objectOne
	 * @param objectTwo
	 * @return
	 */
	public static boolean notEqual(Object objectOne, Object objectTwo){
		return !equal(objectOne, objectTwo);
	}
	/**
	 * @author LiuTao @date 2018年5月22日 下午11:15:33 
	 * @Title: compare 
	 * @Description: TODO(比较两个整数的大小) 
	 * @param a
	 * @param b
	 * @return   a > b 返回 1,a < b 返回-1,a = b 返回0
	 */
	public static int compare(int a, int b) {
		int i = 0;
		if(a > b){
			i = 1;
		}else if(a < b){
			i = -1;
		}
		return i;
	}
	
	/**
	 * @author LiuTao @date 2018年11月6日 下午3:57:29 
	 * @Title: getListWithCountByPageSize 
	 * @Description: TODO(Describe) 
	 * @param page
	 * @param size
	 * @param count
	 * @param list
	 * @return
	 */
	public static Map<String, Object> getListWithCountByPageSize(List<?> list, long count, int page, int size) {
		Map<String, Object> rstMap = new HashMap<String, Object>();
		rstMap.put("result", list);
		rstMap.put("count", count);
		rstMap.put("page", page);
		rstMap.put("size", size);
		return rstMap;
	}
	
	/**
	 * @author LiuTao @date 2018年6月2日 下午6:00:40 
	 * @Title: getSortBySort 
	 * @Description: 根据sort对list进行排序 
	 * @param list
	 * @param sort
	 * @return
	 */
	public static <T> List<T> getSortBySort(List<T> list, Sort sort){
		return null;
	}
	/**
	 * @author LiuTao @date 2019年2月1日 下午4:21:09 
	 * @Title: isEnum 
	 * @param clazz
	 * @param object
	 * @return
	 */
	public static boolean isInEnum(Class<?> clazz, Object object){
		return  clazz.isEnum();
	}
	/**
	 * @author LiuTao @date 2019年2月1日 下午4:22:31 
	 * @param <T>
	 * @Title: toEnum 
	 * @param clazz
	 * @param object
	 * @return
	 */
//	public static <T> Class<T> toEnum(Class<?> clazz, Object object){
////		clazz.valueOf(clazz, object);
////		return  clazz.isEnum();
//		return (Class<T>) clazz.cast(object);
//	}
}
