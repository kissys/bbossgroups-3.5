package com.frameworkset.sqlexecutor;

import java.util.HashMap;

import com.frameworkset.common.poolman.ConfigSQLExecutor;
import com.frameworkset.util.ListInfo;
/**
 * 
 * <p>Title: ApplyService.java</p>
 *
 * <p>Description: 分页查询功能实例（针对新的分页接口进行测试）
 * 3.6.0之后的版本ConfigSQLExecutor/SQLExecutor/PreparedDBUtil三个持久层组件中增加了一组分页接口，
 * 这组接口和之前的分页接口的区别是增加了一个totalsize参数，也就是说可以通过totalsize参数从外部传入总记录数，
 * 这样在分页方法内部无需执行总记录数查询操作，以便提升系统性能
 * 
 * </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 * @Date 2012-10-18 下午5:06:43
 * @author biaoping.yin
 * @version 1.0
 */
public class ApplyService {

	private com.frameworkset.common.poolman.ConfigSQLExecutor executor = new ConfigSQLExecutor("com/frameworkset/sqlexecutor/purchaseApply.xml");
	
	/*******************************以bean方式传递查询条件开始*******************************/
	public ListInfo queryMaterailListInfoFirstStyleBean(int offset, int pagesize ,PurchaseApplyCondition condition) throws Exception {
		
		//执行分页查询，queryMaterialList对应分页查询语句，
		//根据sql语句在分页方法内部执行总记录数查询操作，这种风格使用简单，效率相对较低
		//condition参数保存了查询条件
		return executor.queryListInfoBean(HashMap.class, "queryMaterialList", offset, pagesize,condition);
	}
	
	public ListInfo queryMaterailListInfoSecondStyleBean(int offset, int pagesize ,PurchaseApplyCondition condition) throws Exception {
		//执行总记录查询并存入totalSize变量中，queryCountMaterialList对应一个优化后的总记录查询语句
		//condition参数保存了查询条件
		long totalSize = executor.queryObjectBean(long.class, "queryCountMaterialList", condition);
		//执行总记分页查询，queryMaterialList对应分页查询语句，通过totalsize参数从外部传入总记录数，
		//这样在分页方法内部无需执行总记录数查询操作，以便提升系统性能，这种风格使用简单，效率相对第一种风格较高，但是要额外配置总记录数查询sql
		//condition参数保存了查询条件
		return executor.queryListInfoBean(HashMap.class, "queryMaterialList", offset, pagesize,totalSize ,condition);
	}
	
	
	public ListInfo queryMaterailListInfoThirdStyleBean(int offset, int pagesize ,PurchaseApplyCondition condition) throws Exception {
		//根据sql语句和外部传入的总记录数sql语句进行分页，这种风格使用简单，效率最高，但是要额外配置总记录数查询sql
		ListInfo list = executor.queryListInfoBeanWithDBName(HashMap.class, "bspf","queryMaterialList", 0, 10,"queryCountMaterialList" ,condition);
		return list;
	}
	/*******************************以bean方式传递查询条件结束*******************************/
	
	/*******************************以传统绑定变量方式传递查询条件开始*******************************/
	public ListInfo queryMaterailListInfoFirstStyle(int offset, int pagesize ) throws Exception {
		
		//执行分页查询，queryMaterialList对应分页查询语句，
		//根据sql语句在分页方法内部执行总记录数查询操作，这种风格使用简单，效率相对较低
		//condition参数保存了查询条件
		return executor.queryListInfo(HashMap.class, "queryMaterialListbindParam", offset, pagesize);
	}
	
	public ListInfo queryMaterailListInfoSecondStyle(int offset, int pagesize ) throws Exception {
		//执行总记录查询并存入totalSize变量中，queryCountMaterialList对应一个优化后的总记录查询语句
		//condition参数保存了查询条件
		long totalSize = executor.queryObject(long.class, "queryCountMaterialListbindParam");
		//执行总记分页查询，queryMaterialList对应分页查询语句，通过totalsize参数从外部传入总记录数，
		//这样在分页方法内部无需执行总记录数查询操作，以便提升系统性能，这种风格使用简单，效率相对第一种风格较高，但是要额外配置总记录数查询sql
		//condition参数保存了查询条件
		return executor.queryListInfoWithTotalsize(HashMap.class, "queryMaterialListbindParam", offset, pagesize,totalSize );
	}
	
	
	public ListInfo queryMaterailListInfoThirdStyle(int offset, int pagesize ) throws Exception {
		//根据sql语句和外部传入的总记录数sql语句进行分页，这种风格使用简单，效率最高，但是要额外配置总记录数查询sql
		ListInfo list = executor.queryListInfoWithDBName2ndTotalsizesql(HashMap.class, "bspf","queryMaterialListbindParam", 0, 10,"queryCountMaterialListbindParam" );
		return list;
	}
	/*******************************以传统绑定变量方式传递查询条件结束*******************************/
}
