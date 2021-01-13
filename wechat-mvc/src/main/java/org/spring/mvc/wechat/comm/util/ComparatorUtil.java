package org.spring.mvc.wechat.comm.util;

import java.lang.reflect.Method;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class ComparatorUtil implements Comparator<Object>{
	private int order;//1:升序,-1:降序
	private String[] sortArray;//用于排序的属性数组
	private Comparator<Object> comparator;//排序规则
	/**
	 * <p>Title: </p> 
	 * <p>Description: </p> 
	 * @param sortArray
	 * @param order
	 */
	public ComparatorUtil(String[] sortArray, int order) {
		this.comparator = Collator.getInstance();//排序规则
		this.sortArray = sortArray;
		this.order = order;
	}
	/**
	 * <p>Title: </p> 
	 * <p>Description: </p> 
	 * @param desiredLocale
	 * @param sortArray
	 * @param order
	 */
	public ComparatorUtil(Locale locale, String[] sortArray, int order) {
		if(CommUtil.isNull(locale)){
			this.comparator = Collator.getInstance();//排序规则
		}else {
			this.comparator = Collator.getInstance(locale);//排序规则
//			this.comparator = Collator.getInstance(Locale.CHINA);//排序规则,中文时可以不写
		}
		this.sortArray = sortArray;
		this.order = order;
	}

	/**
	 * 定义排序规则 如果按照不止一个属性进行排序 这按照属性的顺序进行排序
	 * 类似sql order by 即只要比较出同位置的属性就停止
	 */
	@Override
	public int compare(Object object1, Object object2) {
		for (int i = 0; i < sortArray.length; i++) {
			Object obj1 = getFieldValueByFieldName(object1, sortArray[i]);
			Object obj2 = getFieldValueByFieldName(object2, sortArray[i]);
			if (obj1 instanceof Integer && obj2 instanceof Integer) {
				int value1 = Integer.parseInt(obj1.toString());
				int value2 = Integer.parseInt(obj2.toString());
				if (value1 > value2) {
					return 1 * this.order;
				} else if (value1 < value2) {
					return -1 * this.order;
				}
			} else {
				int result = comparator.compare(obj1, obj2);
				if (result != 0) {
					return result * this.order;
				}
			}
		}
		return -1 * this.order;
	}

	/**
	 * 通过反射,获取属性值
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private Object getFieldValueByFieldName(Object object, String fieldName) {
		Object value = null;
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();//get方法属性首字母大写处理
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = object.getClass().getMethod(getter, new Class[] {});
			value = method.invoke(object, new Object[] {});
		} catch (Exception e) {
			System.out.println("属性不存在");
			value = null;
		}
		return value;
	}
	/**
	 * @Author LiuTao @Date 2019年1月30日 上午11:28:46 
	 * @Title: addMethod 
	 * @Description: 自定义的匿名内部类根据自己需求完成对Object的比较排序
	 * 				此处做的是定义的json数据的排序
	 */
	@SuppressWarnings("unused")
	private void addMethod(){
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		Collections.sort(jsonList, new Comparator<JSONObject>() {
            // You can change "Name" with "ID" if you want to sort by ID
            private static final String KEY_NAME = "fullName";
            private Comparator<Object> comparator = Collator.getInstance(java.util.Locale.CHINA);
            private int order = 1;
            @Override
            public int compare(JSONObject objectA, JSONObject objectB) {
                try {
                    // 这里是a/b需要处理的业务,需要根据你的规则进行修改。
                    Object objA = objectA.getString(KEY_NAME);
                    Object objB = objectB.getString(KEY_NAME);
    				int result = comparator.compare(objA, objB);
    				if (result != 0) {
    					return result * this.order;
    				}
                } catch (JSONException e) {
                    // do something
                	e.printStackTrace();
                }
                return -1 * this.order;
                // if you want to change the sort order, simply use the following:
                // return -valA.compareTo(valB);
            }
        });
	}
	/**
	 * @Author LiuTao @Date 2019年1月30日 下午12:48:49 
	 * @Title: main 
	 * @Description: TODO(Describe) 
	 * @param args
	 */
	public static void main(String[] args) {
		List<Object> objectList = new ArrayList<Object>(); 
		String[] sortArray = new String[]{"fullName"};
		int order = 1;//1:升序,-1:降序
		ComparatorUtil comparatorUtil = new ComparatorUtil(sortArray, order);
		Collections.sort(objectList, comparatorUtil);
	}
}

