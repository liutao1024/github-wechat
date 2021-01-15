package org.spring.boot.wechat.basic;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author LiuTao @Date 2021年1月15日 下午6:07:46
 * @ClassName: BasicUtil 
 * @Description: 基础工具类
 */
public class BasicUtil{
	private static final Logger LOGGER = LoggerFactory.getLogger(BasicUtil.class);
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat SIMPLEDATEFORMAT = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * @author LiuTao @date 2018年9月5日 下午8:05:23 
	 * @Title: recursionObjectToMap 
	 * @Description: TODO(利用递归调用将Object中的值全部进行获取) 
	 * @param timeFormatStr 格式化时间字符串默认
	 * @param obj  对象
	 * @param excludeFields 排除的属性
	 * @return
	 * @throws IllegalAccessException
	 */
//	public static Map<String, Object> recursionObjectToMap(String timeFormatStr, Object obj, String... excludeFields) throws IllegalAccessException {
//		Map<String, Object> map = new HashMap<>();
//		if (excludeFields.length != 0) {
//			List<String> list = Arrays.asList(excludeFields);
//			BasicReflection.getMapByReflecFormatObjectMapAndFields(timeFormatStr, obj, map, list);
//		} else {
//			BasicReflection.getMapByReflecFormatObjectMapAndFields(timeFormatStr, obj, map, null);
//		}
//		return map;
//	}
	
	/**
	 * @author LiuTao @date 2018年6月9日 下午1:01:49 
	 * @Title: toGetDateStrByDateStr 
	 * @Description: TODO(Describe) 
	 * @param srcDateStr
	 * @param num
	 * @return
	 */
	public static String getDateStrByDateStrAddDays(String srcDateStr, int num){
		try {
			Date date = SIMPLEDATEFORMAT.parse(srcDateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, num);
			date = calendar.getTime();
			return SIMPLEDATEFORMAT.format(date);
		} catch (Exception e) {
			System.out.println("日期:"+ srcDateStr +",格式不对");
		}
		return null;
	}
	
	/**
	 * @author LiuTao @date 2018年6月6日 下午1:37:25 
	 * @Title: getHqlByEntityNameAndParamMap 
	 * @Description: TODO(Describe) 
	 * @param entityName
	 * @param paramMap
	 * @return
	 */
	public static String getHqlByEntityNameAndParamMap(String entityName, Map<String, Object> paramMap){
		String rstSqlStr = "";
		if(isNotNull(entityName)){
			rstSqlStr = "from " + entityName;//from tableName 
			if(isNotNull(paramMap) && paramMap.size() > 0){
				String str =  " where";
				String appendStr  = "";
				for(Entry<String, Object> entry : paramMap.entrySet()){
					String keyStr = entry.getKey();
					Object value = entry.getValue();
					appendStr = appendStr + "and " + keyStr + " = '" + value + "' ";
				}
				//处理appendStr
				appendStr = sourceStrCastHeadStr("and", appendStr);
				rstSqlStr = rstSqlStr + str + appendStr; 
			}
		}
		return rstSqlStr;
	}
	
	/**
	 * @author LiuTao @date 2018年5月26日 下午9:25:32 
	 * @Title: getSqlStrByEntityNameAndParamMap 
	 * @Description: 根据传入的 EntityName和ParamMap组装一条hql Hibernat
	 * @param entityName
	 * @param paramMap
	 * @return
	 */
	public static String getSqlStrByEntityNameAndParamMap(String entityName, Map<String, Object> paramMap){
		String rstSqlStr = "";
		if(isNotNull(entityName)){
			rstSqlStr = "from " + entityName;//from tableName 
			if(isNotNull(paramMap) && paramMap.size() > 0){//传入的是一个new出来的空对象时需要排除
				String str =  " where";
				String appendStr  = "";
				for(Entry<String, Object> entry : paramMap.entrySet()){
					String keyStr = entry.getKey();
					appendStr = appendStr + "and " + keyStr + " = :" + keyStr + " ";
				}
				//处理appendStr
				appendStr = sourceStrCastHeadStr("and", appendStr);
				rstSqlStr = rstSqlStr + str + appendStr; 
			}
		}
		return rstSqlStr;
	}
	
	/**
	 * 注意:转化map集合的key 例如 属性名 xXxx(tNode)类型 Eclipse自动生成get set方法第一个字母是不会大写的
	 * @return 
	 */
	public static String convertKey(String attributeName, String prefix) {
		// 将属性名第一个字母大写然后进行拼接
		return prefix.concat(attributeName.substring(0, 1).toUpperCase().concat(attributeName.substring(1)));
	}
	
	/**
	 * 将Object类型的值,转换成bean对象属性里对应的类型值
	 * @param value  Object对象值
	 * @param fieldType 属性的类型
	 * @return 转换后的值
	 */
	public static Object convertValueTypeForJava(Object object, Class<?> fieldClazz) {
		Object retVal = null;
		if (Long.class.getName().equals(fieldClazz.getName()) || long.class.getName().equals(fieldClazz.getName())) {
			retVal = Long.parseLong(object.toString());
		} else if (Integer.class.getName().equals(fieldClazz.getName()) || int.class.getName().equals(fieldClazz.getName())) {
			retVal = Integer.parseInt(object.toString());
		} else if (Float.class.getName().equals(fieldClazz.getName()) || float.class.getName().equals(fieldClazz.getName())) {
			retVal = Float.parseFloat(object.toString());
		} else if (Double.class.getName().equals(fieldClazz.getName()) || double.class.getName().equals(fieldClazz.getName())) {
			retVal = Double.parseDouble(object.toString());
		} else if (Boolean.class.getName().equals(fieldClazz.getName()) || boolean.class.getName().equals(fieldClazz.getName())) {
			retVal = Boolean.parseBoolean(object.toString());
		} else if (Character.class.getName().equals(fieldClazz.getName()) || char.class.getName().equals(fieldClazz.getName())) {
			retVal = object.toString().charAt(0);//20181112
		} else if(Date.class.getName().equals(fieldClazz.getName())){
			retVal = strConvertDate(object.toString());
		} else if(String.class.getName().equals(fieldClazz.getName())){
			retVal = object.toString();
		}
		return retVal;
	}
	/**
	 * @author LiuTao @date 2018年11月13日 下午3:43:27 
	 * @Title: convertValueTypeForDB 
	 * @Description: TODO(Describe) 
	 * @param value
	 * @param fieldClazz
	 * @return
	 */
	public static Object convertValueTypeForDB(Object object, Class<?> fieldClazz) {
		Object retVal = null;
		if (Long.class.getName().equals(fieldClazz.getName()) || long.class.getName().equals(fieldClazz.getName())) {
			retVal = Long.parseLong(object.toString());
		} else if (Integer.class.getName().equals(fieldClazz.getName()) || int.class.getName().equals(fieldClazz.getName())) {
			retVal = Integer.parseInt(object.toString());
		} else if (Float.class.getName().equals(fieldClazz.getName()) || float.class.getName().equals(fieldClazz.getName())) {
			retVal = Float.parseFloat(object.toString());
		} else if (Double.class.getName().equals(fieldClazz.getName()) || double.class.getName().equals(fieldClazz.getName())) {
			retVal = Double.parseDouble(object.toString());
		} else if (Boolean.class.getName().equals(fieldClazz.getName()) || boolean.class.getName().equals(fieldClazz.getName())) {
			retVal = Boolean.parseBoolean(object.toString());
		} else if (Character.class.getName().equals(fieldClazz.getName()) || char.class.getName().equals(fieldClazz.getName())) {
			retVal = "'"+object.toString().charAt(0)+"'";//20181112
		} else if(Date.class.getName().equals(fieldClazz.getName())){
			retVal = "'"+dateConvertStr(object)+"'";
		} else if(String.class.getName().equals(fieldClazz.getName())){
			retVal = "'"+object.toString()+"'";
		}
		return retVal;
	}
	/**
	 * String类型转Date
	 * @param date
	 * @return
	 */
	public static Date strConvertDate(String dateStr){
        Date parse = null;
		try {
			parse = SIMPLE_DATE_FORMAT.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parse;
	}
	
	/**
	 * @author LiuTao @date 2018年11月13日 下午4:17:30 
	 * @Title: dateConvertStr 
	 * @Description: TODO(Describe) 
	 * @param dateStr
	 * @return
	 */
	public static String dateConvertStr(Object object){
		String dateStr = null;
		try {
			dateStr = SIMPLE_DATE_FORMAT.format(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	
	/**
	 * @author LiuTao @date 2018年6月2日 下午9:50:24 
	 * @Title: makeNeedString 
	 * @Description: 获取需要的字符串
	 * @param srcStr
	 * @param flag
	 * @param from
	 * @param to
	 * @return
	 */
	public static String sourceStrCastItemFromTo(String srcStr, boolean flag, int from, int to){
		String oneStr = srcStr.substring(0, from);
		String twoStr = srcStr.substring(from, to);
		String threeStr = srcStr.substring(to,srcStr.length());
		if(flag)
			twoStr = twoStr.toUpperCase();
		else 
			twoStr = twoStr.toLowerCase();
		return oneStr+twoStr+threeStr;
	}
	
	
	/**
	 * @author LiuTao @date 2018年5月22日 下午9:28:48 
	 * @Title: isNull 
	 * @Description: TODO(判断对象是否为空)  还有种情况是object = "null"
	 * @param object
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNull(Object object){
//		boolean rstBoolean = false;
//		if(null == object){
//			rstBoolean = true;
//		}else if("".equals(object)){
//			rstBoolean = true;
//		}
//		return rstBoolean;
		boolean b = false;
		if(object == null)
			b = true;
		if(object instanceof CharSequence)
			b = ((CharSequence) object).length() == 0;
		if (object instanceof Collection) 
			b = ((Collection) object).isEmpty();
		if(object instanceof Map)
			b = ((Map) object).isEmpty();
		if(object instanceof Object[]){
			Object[] objects = (Object[]) object;
			if(objects.length == 0){
				b = true;
			}else {
				boolean nb = false;
				for(int i = 0; i < objects.length; i++){
					if(isNull(objects[i])){
						nb = true;
						break;
					}
				}
				b = nb;
			}
		}
		return b;
	}
	/**
	 * @author LiuTao @date 2018年5月22日 下午11:06:35 
	 * @Title: isNotNull 
	 * @Description: TODO(判断对象是否为空) 
	 * @param object
	 * @return
	 */
	public static boolean isNotNull(Object object){
		return !isNull(object);
	}
	
	/**
	 * @author LiuTao @date 2018年5月22日 下午9:48:39 
	 * @Title: isEqual 
	 * @Description: TODO(判断对象是否是同一个对象) 
	 * @param objectOne
	 * @param objectTwo
	 * @return
	 */
	public static boolean equals(Object objectOne, Object objectTwo) {
		boolean rstBoolean = false;
		if(objectOne == objectTwo)//是否同一对象
			rstBoolean = true;
		if(objectOne.equals(objectTwo))//值是否相等
			rstBoolean = true;
		return rstBoolean;
	}
	
	/**
	 * @author LiuTao @date 2018年5月26日 下午9:18:06 
	 * @Title: dealWith 
	 * @Description: 若srcStr是以headStr开头的将开头的headStr去掉返回
	 * @param headStr
	 * @param srcStr
	 * @return
	 */
	public static String sourceStrCastHeadStr(String headStr, String srcStr){
		if(srcStr.indexOf(headStr) == 0)
			srcStr = srcStr.substring(headStr.length());
		return srcStr;
	}
	/**
	 * @Author LiuTao @Date 2018年11月16日 上午10:13:11 
	 * @Title: takeTheFirstOfSourceMap 
	 * @Description: 获取SourceMap中的第一对key和value 
	 * @param srcMap
	 * @return
	 */
	public static Map<String, Object> takeTheFirstOfSourceMap(Map<String, Object> srcMap){
		Map<String, Object> desMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : srcMap.entrySet()) {
			desMap.put(entry.getKey(), entry.getValue());
			break;
		}
		return desMap;
	}
	/**
	 * @author LiuTao @date 2018年11月6日 下午9:08:58 
	 * @Title: setPageSizeToParamMap 
	 * @Description: TODO(Describe) 
	 * @param page
	 * @param size
	 * @param paramMap
	 */
	public static void setPageSizeToParamMap(int page, int size, Map<String, Object> paramMap) {
		paramMap.put("page", page);
		paramMap.put("size", size);
	}
	
	/**
	 * @author LiuTao @date 2018年11月12日 下午3:33:23 
	 * @Title: presentHumpNamedToUnderScoreString 
	 * @Description: 将驼峰式命名的字符串转换为下划线小写方式.如果转换前的驼峰式命名的字符串为空,则返回空字符串
	 * 					例如:HelloWorld->HELLO_WORLD或者->hello_world 
	 * @param humpName 转换前的驼峰式命名的字符串
	 * @param UOL  大写或小写标志 true大写/false小写
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String presentHumpNamedToUnderScoreString(String humpName, boolean UOL) {
		String result = "";
		StringBuilder sb = new StringBuilder();
	    if(humpName != null && humpName.length() > 0) {
	        // 将第一个字符处理成大写
	    	sb.append(humpName.substring(0,1));
	        // 循环处理其余字符
	        for(int i = 1; i < humpName.length(); i++) {
	            String s = humpName.substring(i, i + 1);
	            // 在大写字母前添加下划线
	            if(s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {//是否为数字
	            	sb.append("_");
	            }
	            // 其他字符直接转成大写
	            sb.append(s);
	        }
	    }
	    if(UOL)
	    	result = sb.toString().toUpperCase();
	    else 
	    	result = sb.toString().toLowerCase();
	    return result;
	}

	/**
	 * @author LiuTao @date 2018年6月4日 下午8:18:10 
	 * @Title: getSubFileNameListByFilePath 
	 * @Description: 递归查找指定目录下的类文件的全路径 
     * @param baseFile 查找文件的入口 
     * @param fileList 保存已经查找到的文件集合 
	 */
    public static List<String> getSubFileNameListByFilePath(File file, List<String> fileList){ 
        if(file.isDirectory()){  
            File[] files = file.listFiles();  
            for(File tmpFile : files){  
            	getSubFileNameListByFilePath(tmpFile, fileList);  
            }  
        }else {
        	String filePath = file.getPath();  
        	System.out.println(filePath);
        	if(filePath.endsWith(".java")){  
        		String name1 = filePath.substring(filePath.indexOf("java")+5, filePath.length());  
        		String name2 = name1.replaceAll("\\\\", ".");  
        		String name3 = name2.substring(0, name2.lastIndexOf(".java"));  
        		fileList.add(name3);  
        	}  
		}  
        return fileList;
    } 
    /**
     * @author LiuTao @date 2019年7月19日 下午4:49:04 
     * @Title: copProperties 
     * @Description: TODO(Describe) 
     * @param source
     * @param destin
     * @throws Exception
     */
    public static void copyProperties(Object source, Object destin) throws Exception{
    	// 获取属性
		BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(), java.lang.Object.class);
		PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();
		BeanInfo destinBean = Introspector.getBeanInfo(destin.getClass(), java.lang.Object.class);
		PropertyDescriptor[] destinProperty = destinBean.getPropertyDescriptors();
		try {
			for (int i = 0; i < sourceProperty.length; i++) {
				for (int j = 0; j < destinProperty.length; j++) {
					if (sourceProperty[i].getName().equals(destinProperty[j].getName())) {
						// 调用source的getter方法和destin的setter方法
						destinProperty[j].getWriteMethod().invoke(destin,
								sourceProperty[i].getReadMethod().invoke(source));
						break;
					}
				}
			}
		} catch (Exception exception) {
			throw new Exception("属性复制失败:" + exception.getMessage());
		}
    }
    /**
     * @Author LiuTao @Date 2020年6月9日 下午1:54:33 
     * @Title: nvl 
     * @Description: object为空时,返回defaultObject,否则返回object 
     * @param object
     * @param defaultObj
     * @return
     */
    public static Object nvl(Object object, Object defaultObj){
    	if(isNull(object))
    		return defaultObj;
    	else
    		return object;
    }
    /**
     * @author LiuTao @date 2018年11月14日 下午3:48:41 
     * @Title: main 
     * @Description: TODOTEST
     * @param args
     */
    public static void main(String[] args) {
		Date date = new Date();
		Object o = convertValueTypeForDB(date, Date.class);
		LOGGER.info(o.toString());
	}
}
