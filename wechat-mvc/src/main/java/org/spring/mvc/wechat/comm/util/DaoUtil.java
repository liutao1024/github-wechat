package org.spring.mvc.wechat.comm.util;

import java.io.Serializable;
import java.util.List;

public interface DaoUtil<T, PK extends Serializable> {
	/* @author LiuTao @date 2018年5月23日 下午10:02:32 
	 * @Description: 新增/保存接口
	 */
	T save(T entity);
	void saveOrUpdate(T entity);
	/* @author LiuTao @date 2018年5月23日 下午10:02:35 
	 * @Description: 删除接口 
	 * @param spec
	 */
	void delete(PK PKEntity);
	void delete(List<T> entities);
	
	/* @author LiuTao @date 2018年5月23日 下午10:02:32 
	 * @Description: 查询接口
	 */
	T load(PK PKEntity);
	T get(PK PKEntity);
	List<T> findAll();
	/* @author LiuTao @date 2018年5月23日 下午10:02:32 
	 * @Description: 更新接口 
	 * @param entity
	 */
	void update(T entity);
	void update(Iterable<T> entities);
	/* @author LiuTao @date 2018年5月23日 下午10:02:32 
	 * @Description: 统计数量接口
	 */
	long count();
	/* @author LiuTao @date 2018年5月23日 下午10:02:38 
	 * @Description: 持久化接口
	 */
	void persist(T entity);
	/* @author LiuTao @date 2018年5月23日 下午10:02:38 
	 * @Description:去持久化接口
	 */
	void flush();
	
}
