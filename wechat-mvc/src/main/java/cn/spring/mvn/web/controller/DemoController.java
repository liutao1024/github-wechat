package cn.spring.mvn.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import tk.mybatis.mapper.entity.Example;
//import tk.mybatis.mapper.entity.Example.Criteria;
import cn.spring.mvn.basic.ibatis.IBatisTParam;
import cn.spring.mvn.core.credit.entity.service.CoreCreditEntityService;
import cn.spring.mvn.core.deposit.entity.service.CoreDepositEntityService;
import cn.spring.mvn.core.entity.service.AccountService;
import cn.spring.mvn.core.entity.service.ProductService;
import cn.spring.mvn.core.fund.entity.service.CoreFundEntityService;
import cn.spring.mvn.core.loan.entity.Student;
import cn.spring.mvn.core.loan.entity.dao.StudentDao;
import cn.spring.mvn.core.loan.entity.service.StudentService;
import cn.spring.mvn.socket.server.SocketOperatorImpl;
import cn.spring.mvn.system.entity.SysTransactionInformation;
import cn.spring.mvn.system.entity.service.SysTransactionInformationService;
//import cn.spring.mvn.basic.ibatis.IBatisTResult;
/**
 * @author LiuTao @date 2018年11月9日 下午8:03:53
 * @ClassName: DemoController 
 * @Description: MyBatis 的疯狂探索
 */
@Controller("DemoController")
@RequestMapping(value="/test")
public class DemoController {
	@Autowired
	private SysTransactionInformationService s;
	@Autowired
	private AccountService a;
	@Autowired
	private ProductService p;
	@Autowired
	private CoreDepositEntityService d;
	@Autowired
	private CoreFundEntityService f;
	@Autowired
	private CoreCreditEntityService l;
	@Autowired
	private StudentService st;
	@Autowired
	private StudentDao sd;
	
	/**
	 * @author LiuTao @date 2018年11月13日 下午4:36:14 
	 * @Title: Test006 
	 * @Description: 测试IBatisTParam的运用
	 * @param request
	 * @param response
	 */
	@RequestMapping("/test006")
	public void Test006(HttpServletRequest request, HttpServletResponse response) {
		Student t = new Student();
//		t.setAge(28);
//		t.setAge(32);
		t.setBirth(new Date());
		t.setId(911L);
		t.setName("测试用户001");
//		t.setNo("910");
//		t.setPhone("979497772");
		t.setSex('X');
		List<Student> ts = new ArrayList<Student>();
		ts.add(t);
		IBatisTParam<Student> iBatisParam = new IBatisTParam<Student>(t);
		iBatisParam.setPage(0);
		iBatisParam.setSize(5);
		iBatisParam.setOrderColumn("age");
//		IBatisTResult<Student> iBatisResult = //st.selectEntitiesWithCount(t);
//		List<Student> list = 
//		Integer i =
//		Long lg = 
				
				
//		st.insertEntity(t);
//		st.insertEntities(ts);
//		st.insertEntityByCondition(iBatisParam);
//		
//		st.deleteEntity(t);
//		st.deleteEntities(ts);
//		st.deleteEntityByCondition(iBatisParam);
//		
//		st.updateEntity(t);
//		st.updateEntities(ts);
//		st.updateEntityByCondition(iBatisParam);
		
//		st.selectEntities(t);
//		st.selectEntitiesCount(t);
//		st.selectEntitiesWithCount(t);		
//		st.selectEntitiesWithCountByCondition(iBatisParam);
//		st.selectEntitiesWithCountByTK(iBatisParam);
//		st.selectOneEntity(t);
//		st.selectPageEntitiesWithCount(t, 3, 10);
//		st.selectPageEntitiesWithCountByCondition(iBatisParam);
//		st.selectPageEntitiesWithCountByTK(iBatisParam);

		System.out.println("++++++++++++++++++");
//		Long count = iBatisResult.getCount();
//		System.out.println(count);
//		List<Student> ls = iBatisResult.getResultList();
//		for (Student student : ls) {
//			System.out.println(student);
//		}
	}
	/**
	 * @author LiuTao @date 2018年11月12日 下午8:44:36 
	 * @Title: Test005 
	 * @Description: 测试自己设计的 BINGO
	 * @param request
	 * @param response
	 */
	@RequestMapping("/test005")
	public void Test005(HttpServletRequest request, HttpServletResponse response) {
		Student student = new Student();
		student.setAge(28);
		List<Student> list = st.selectEntities(student);
		for (Student entity : list) {
			System.out.println(entity);
		}
		student.setBirth(new Date());
		student.setId(119L);
		student.setName("杨过");
		student.setNo("9925");
		student.setPhone("18982598359");
		student.setSex('M');
		st.insertEntity(student);
	}
	
	/**
	 * @author LiuTao @date 2018年11月9日 下午7:52:19 
	 * @Title: Test004 
	 * @Description: 对TKMapper接口中Example的探索 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/test004")
	public void Test004(HttpServletRequest request, HttpServletResponse response){
		/**
		 * 1.Example的方法参数不能是实体类必须是Example类.
		 * 2.PrimaryKey的方法是根据实体类entity的属性上的注解@Id进行匹配的
		 */
		Student student = new Student();
		Example example = new Example(Student.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andAllEqualTo("");
		RowBounds rowBounds = new RowBounds(4, 2);//new RowBounds(int ofset, int limit),offset 表示第几条开始,limit表示显示几条
		sd.deleteByExample(example);
		sd.selectByRowBounds(student, rowBounds);
		
	}
	/**
	 * @author LiuTao @date 2018年11月9日 下午7:51:15 
	 * @Title: Test003 
	 * @Description: 对TKMapper接口的探索
	 * @param request
	 * @param response
	 */
	@RequestMapping("/test003")
	public void Test003(HttpServletRequest request, HttpServletResponse response){
		Student student = new Student();
//		student.setId(911L);
//		student.setNo("9528");
//		student.setName("删除我");
		student.setAge(28);
//		student.setSex('F');
//		student.setPhone("15928435556");
		/**
		 * 1.Example的方法参数不能是实体类必须是Example类.
		 * 2.PrimaryKey的方法是根据实体类entity的属性上的注解@Id进行匹配的
		 */
//		Example example = new Example(Student.class);
//		Example.Criteria criteria = example.createCriteria();
//		RowBounds rowBounds = new RowBounds(4, 2);//new RowBounds(int ofset, int limit),offset 表示第几条开始,limit表示显示几条
		
//		int r1 = sd.insert(student);//保存一个实体,null的属性也会保存,不会使用数据库默认值
//		int r2 = sd.insertSelective(student);//保存一个实体,null的属性不会保存,会使用数据库默认值
//		boolean r3 = sd.existsWithPrimaryKey(student);//根据主键字段判断是否存在
//		List<Student> r4 = sd.selectAll();//查询全部结果,select(null)方法能达到同样的效果
//		List<Student> r5 = sd.select(student);//根据实体中的属性值进行查询,查询条件使用等号
//		Student r6 = sd.selectOne(student);//根据实体中的属性进行查询,只能有一个返回值,有多个结果是抛出异常,查询条件使用等号
//		Student r7 = sd.selectOneByExample(example);//根据Example条件进行查询,只能有一个返回值,有多个结果是抛出异常(Expected one result (or null) to be returned by selectOne(), but found: 17)
		
//		List<Student> r8 = sd.selectByExample(example);//根据Example条件进行查询,这个查询支持通过Example类指定查询列,通过selectProperties方法指定查询列
//		Student r9 = sd.selectByPrimaryKey(student);//根据主键字段进行查询,方法参数必须包含完整的主键属性,查询条件使用等号
//		List<Student> r10 = sd.selectByRowBounds(student, rowBounds);//根据实体属性和RowBounds进行分页查询(实质上是内存分页[sql执行后返回的所有复合条件的记录但是在这些记录中按RowBounds的offset和limit进行分页返回])
//		List<Student> r11 = sd.selectByExampleAndRowBounds(example, rowBounds);//根据example条件和RowBounds进行分页查询
//		int r12 = sd.selectCount(student);//根据实体中的属性查询总数,查询条件使用等号
//		int r13 = sd.selectCountByExample(example);//根据Example条件进行查询总数
//		int r14 = sd.updateByExample(student, example);//根据Example条件更新实体record包含的全部属性,null值会被更新
//		int r15 = sd.updateByExampleSelective(student, example);//根据Example条件更新实体record包含的不是null的属性值
//		int r16 = sd.updateByPrimaryKey(student);//根据主键更新实体全部字段,student的null值会被更新
//		int r17 = sd.updateByPrimaryKeySelective(student);//根据主键更新属性不为null的值
//		
//		int r18 = sd.delete(student);//根据实体属性作为条件进行删除,查询条件使用等号,
//		int r19 = sd.deleteByExample(example);//根据Example条件删除数据
//		int r20 = sd.deleteByPrimaryKey(student);//根据主键字段进行删除,方法参数必须包含完整的主键属性
		
		
		//20181116
//		st.selectEntitiesByTK(t);
//		st.selectEntitiesByTKExample(t);
//		Example example = new Example(student.getClass());
//		Example.Criteria criteria = example.createCriteria();
//		//
//		example.and();
//		example.and(criteria);
//		example.clear();
//		example.createCriteria();
//		example.equals(obj);
//		example.excludeProperties(arg0);
//		example.getCountColumn();
//		example.getDynamicTableName();
//		example.getEntityClass();
//		example.getOrderByClause();
//		example.getOredCriteria();
//		example.getSelectColumns();
//		example.isDistinct();
//		example.isForUpdate();
//		example.or();
//		example.or(criteria);
//		example.orderBy(property);
//		example.selectProperties(arg0);
//		example.setCountProperty(property);
//		example.setDistinct(distinct);
//		example.setForUpdate(forUpdate);
//		example.setOrderByClause(orderByClause);
//		example.setTableName(tableName);
//		
//		//and
//		criteria.andAllEqualTo(arg0);
//		criteria.andBetween(property, value1, value2);
//		criteria.andCondition(condition);
//		criteria.andCondition(condition, value);
//		criteria.andEqualTo(arg0);
//		criteria.andEqualTo(property, value);
//		criteria.andGreaterThan(property, value);
//		criteria.andGreaterThanOrEqualTo(property, value);
//		criteria.andIn(property, values);
//		criteria.andIsNotNull(property);
//		criteria.andIsNull(property);
//		criteria.andLessThan(property, value);
//		criteria.andLessThanOrEqualTo(property, value);
//		criteria.andLike(property, value);
//		criteria.andNotBetween(property, value1, value2);
//		criteria.andNotEqualTo(property, value);
//		criteria.andNotIn(property, values);
//		criteria.andNotLike(property, value);
//		//
//		criteria.orAllEqualTo(arg0);
//		criteria.orBetween(property, value1, value2);
//		criteria.orCondition(condition);
//		criteria.orCondition(condition, value);
//		criteria.orEqualTo(arg0);
//		criteria.orEqualTo(property, value);
//		criteria.orGreaterThan(property, value);
//		criteria.orGreaterThanOrEqualTo(property, value);
//		criteria.orIn(property, values);
//		criteria.orIsNotNull(property);
//		criteria.orIsNull(property);
//		criteria.orLessThan(property, value);
//		criteria.orLessThanOrEqualTo(property, value);
//		criteria.orLike(property, value);
//		criteria.orNotBetween(property, value1, value2);
//		criteria.orNotEqualTo(property, value);
//		criteria.orNotIn(property, values);
//		criteria.orNotLike(property, value);
//		//
//		
//		st.selectEntitiesByTKExampleCriteria(example);
//		st.selectEntitiesByTKRowBounds(t, 0, 5);
//		st.selectEntitiesByTKExampleRowBounds(t, 0, 5);
		
		List<Student> list = st.selectStudents();
		for (Student stu : list) {
			System.out.println(stu);
		}
	}
	
	
	@RequestMapping("/test002")
	public void Test002(HttpServletRequest request, HttpServletResponse response){
//		CoreMain entity = new CoreMain();
//		entity.setId("1002");
//		entity.setName("渣渣灰");
//		m.insertEntity(entity);
//		List<CoreMain> list = m.selectEntityList("1002");
//		for (CoreMain coreMain : list) {
//			System.out.println(coreMain);
//		}
//		List<CoreMain> thlist = m.selectAll(entity); 
//		for (CoreMain coreMain : thlist) {
//			System.out.println(coreMain);
//		}
	}
	/**
	 * @author LiuTao @date 2018年11月9日 下午8:32:43 
	 * @Title: Test001 
	 * @Description: Hibernat的测试 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/test001")
	public void Test001(HttpServletRequest request, HttpServletResponse response){
		SysTransactionInformation sti = new SysTransactionInformation();
		List<SysTransactionInformation> list = s.findAll(sti);
		for (SysTransactionInformation sysTransactionInformation : list) {
			System.out.println(sysTransactionInformation.getSerialNumber());
		}
	}
	/**
	 * @author LiuTao @date 2018年11月9日 下午8:33:28 
	 * @Title: Test000 
	 * @Description: SocketOperatorImpl的测试 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/test000")
	public void Test000(HttpServletRequest request, HttpServletResponse response){
		String str = "{"+
				"\"sys_req\":{"+
								"\"servtp\":\"MGR\","+
								"\"servno\":\"02\","+
								"\"servdt\":\"20181016\","+
								"\"servtm\":\"20:49:32:42\","+
								"\"servsq\":\"201810161120398\","+
								"\"tranbr\":\"01\","+
								"\"tranus\":\"10001\","+
								"\"trandt\":\"\","+
								"\"trantm\":\"\","+
								"\"transq\":\"\","+
								"\"status\":\"\","+
								"\"mesage\":\"\""+
							"},"+
				"\"comm_req\":{"+
								"\"corpno\":\"001\","+
								"\"prcscd\":\"qrcust\""+
							"},"+
				"\"input\":{"+
								"\"custna\":\"刘涛\""+
							"}"+
				"}";
		try {
//			Map<String, Object> map = new HashMap<String, Object>();
			String s = SocketOperatorImpl.call(str, "127.0.0.1");
//			response.s
			System.out.println("==================="+s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
