package org.simple.framework.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.simple.framework.util.CollectionUtil;
import org.simple.framework.util.PropsUtil;
public class DatabaseHelper {

	private static Logger log = LoggerFactory.getLogger(DatabaseHelper.class);
	
	

	private static final QueryRunner runner = new QueryRunner();
	private static final ThreadLocal<Connection> holder = new ThreadLocal<Connection>();
	private static final BasicDataSource source ;
	
	static
	{
		 
		String filename="config.properties";
		Properties  conf = PropsUtil.loadProps(filename);
		String driver = conf.getProperty("jdbc.driver");
		String url = conf.getProperty("jdbc.url");
		String user = conf.getProperty("jdbc.user");
		String pass = conf.getProperty("jdbc.pass");
		source = new BasicDataSource();
		source.setDriverClassName(driver);
		source.setUrl(url);
		source.setUsername(user);
		source.setPassword(pass);
	 
	}
	
	
	public static Connection getConnection()
	{
		Connection conn =holder.get();
		if(conn ==null)
		{
			
		try {
			conn = source.getConnection();
			
		} catch (SQLException e) {
			log.error("get connection failure ",e);
		}finally
		{
			holder.set(conn);
		}
		}
		
		return conn;
	}
	 
	/**
	 * close conn
	 * @param conn
	 */
	public static void closeConn()
	{
		Connection conn =holder.get();
		
		if(conn !=null )
		{
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("close connection failure", e);
			}finally
			{
				holder.remove();
			}
			
		}
	}
	
	/**
	 * 查询实体列表
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> List<T> queryEntityList(Class<T> entityClass,String sql,Object... params)
	{
		List<T> entity ;
		Connection conn=getConnection();
		try {
			entity = runner.query(conn,sql, new BeanListHandler<T>(entityClass),params);
		} catch (SQLException e) {
			log.error("query entity list failure",e);
			throw new RuntimeException(e);
		}finally
		{
			closeConn();
		}
		
		return entity;
	}
	
	
	
	
	
	/**
	 * 查询实体列表
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> List<T> queryEntityList(String sql,Class<T> entityClass)
	{
		List<T> entity ;
		Connection conn = getConnection();
		try {
			entity = runner.query(conn,sql,new BeanListHandler<T>(entityClass));
		} catch (SQLException e) {
			log.error("query entity list failure",e);
			throw new RuntimeException(e);
		}finally
		{
			closeConn();
		}
		
		return entity;
	}
	
	
	/**
	 * 查询单个实体
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> T queryEntity(String sql,Class<T> entityClass)
	{
		T entity ;
		Connection conn = getConnection();
		try {
			entity = runner.query(conn,sql,new BeanHandler<T>(entityClass));
		} catch (SQLException e) {
			log.error("query entity list failure",e);
			throw new RuntimeException(e);
		}finally
		{
			closeConn();
		}
		
		return entity;
	}
	
	/**
	 * 获取sql文件
	 * 初始化测试库
	 * @param filePath
	 */
	public static void exeSqlFile(String filePath)
	{
		
	   InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String sql ;
		try {
			while((sql=br.readLine())!=null)
			{
				DatabaseHelper.exeUpdate(sql);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	/**
	 * 查询map映射集合
	 * 通过输入sql,动态参数,返回一个列,值对应映射
	 * 多表查询使用
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static  List<Map<String,Object>> executeQuery(String sql,Object...params)
	{
		List<Map<String,Object>> result ;
		Connection conn = getConnection();
		try {
			result = runner.query(conn, sql, new MapListHandler(), params);
		} catch (SQLException e) {
			log.error("query entity list failure",e);
			throw new RuntimeException(e);
		}finally
		{
			closeConn();
		}
		
		return result;
	}
	
	public static  <T>  T query(String sql,Object...params){
		T result ;
		Connection conn = getConnection();
		try {
			result = runner.query(conn, sql, new ScalarHandler<T>(), params);
		} catch (SQLException e) {
			log.error("query entity list failure",e);
			throw new RuntimeException(e);
		}finally
		{
			closeConn();
		}
		
		return result;
	}
	
	public static <T> List<T>  queryList(String sql,Object...params){
		List<T> result;
		Connection conn = getConnection();
		try {
			result = runner.query(conn, sql, new ColumnListHandler<T>(), params);
		} catch (SQLException e) {
			log.error("query entity list failure",e);
			throw new RuntimeException(e);
		}finally
		{
			closeConn();
		}
		
		return result;
	
	}
	
	public static <T> T[] queryArray(String sql,Object...params){
		T[] result;
		Connection conn = getConnection();
		try {
			result =  (T[]) runner.query(conn, sql, new ArrayHandler(), params);
		} catch (SQLException e) {
			log.error("query entity list failure",e);
			throw new RuntimeException(e);
		}finally
		{
			closeConn();
		}
		
		return result;
	
	
	}
	
	public static <T> Set<T> querySet(String sql,Object...params){
		Collection<T> list = queryList(sql,params);
		return new LinkedHashSet<T>(list);
	}
		
	/**
	 * 通用执行update方法
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int exeUpdate(String sql,Object...params)
	{
		int rows =0;
		Connection conn = getConnection();
		try {
			rows = runner.update(conn, sql, params);
		} catch (SQLException e) {
			log.error("execute update failure",e);
			throw new RuntimeException(e);
			
		}finally
		{
			closeConn();
		}
		
		return rows;
	}
	
	
	/**
	 * 通用执行update方法
	 * 没有参数的方法
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int exeUpdate(String sql)
	{
		int rows =0;
		Connection conn = getConnection();
		try {
			rows = runner.update(conn, sql);
		} catch (SQLException e) {
			log.error("execute update failure",e);
			throw new RuntimeException(e);
			
		}finally
		{
			closeConn();
		}
		
		return rows;
	}
	
	/**
	 * 实体新增操作
	 * 插入实体
	 * @param entityClass
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean insertEntity(Class<T> entityClass,Map<String,Object>fieldMap)
	{
		if(CollectionUtil.isEmpty(fieldMap))
		{
			log.error("can not insert entity:fieldMap is emtpy");
			return false;
		}
		
		String sql = "INSERT INTO "+getTableName(entityClass);
		StringBuffer columns = new StringBuffer("(");
		StringBuffer values = new StringBuffer("(");
		Set  set = fieldMap.entrySet();
		Iterator iter = set.iterator();
		while(iter.hasNext())
		{
			Map.Entry obj = (Entry)iter.next();
			Object key = obj.getKey();
			columns.append(key).append(",");
			values.append("?").append(",");
		}
		columns.deleteCharAt(columns.length()-1).append(")");
		values.deleteCharAt(values.length()-1).append(")");
		sql += columns.toString()+" VALUES "+ values.toString();
		Object[] params = fieldMap.values().toArray();
		return exeUpdate(sql, params)== 1;
	}
	
	
	
	/**
	 * 实体更新操作
	 * @param entityClass
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean updateEntity(Class<T> entityClass,long id,Map<String,Object>fieldMap)
	{
		if(CollectionUtil.isEmpty(fieldMap))
		{
			log.error("can not update entity:fieldMap is emtpy");
			return false;
		}
		
		String sql = "UPDATE "+getTableName(entityClass)+" SET ";
		StringBuffer columns = new StringBuffer("(");
		Set  set = fieldMap.entrySet();
		Iterator iter = set.iterator();
		while(iter.hasNext())
		{
			Map.Entry obj = (Entry)iter.next();
			Object key = obj.getKey();
			columns.append(key).append("=?, ");
		}
		columns.deleteCharAt(columns.length()-1).append(")");
		sql += columns.toString() + " WHERE id=?";
		List<Object> list = new ArrayList<Object>();
		list.addAll(fieldMap.values());
		list.add(id);
		Object[] params = list.toArray();
		return exeUpdate(sql, params)== 1;
	}
	
	/**
	 * 删除实体
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public static <T> boolean deleteEntity(Class<T> entityClass,long id)
	{
		String sql = "DELETE FROM "+getTableName(entityClass)+" WHERE ID =?";
		return exeUpdate(sql, id)==1;
		
	}
	
	
	/**
	 * 返回表名
	 * @param entityClass
	 * @return
	 */
	public static String getTableName(Class<?> entityClass){
		return entityClass.getSimpleName();
	}
	
	/**
	 * 开启事务
	 */
	public static void beginTransaction()
	{
		Connection conn = getConnection();
		if( conn != null )
		{
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				log.error("begin transaction failure !");
			}finally
			{
				holder.set(conn);
			}
		}
	}
	
	public static void commitTransaction()
	{
		Connection conn = getConnection();
		if(conn !=null )
		{
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				log.error("commit transaction failure !");
			}finally
			{
				holder.remove();
			}
		}
	}
	
	public static void rollabackTransaction()
	{
		Connection conn = getConnection();
		if(conn !=null )
		{
			try {
				conn.rollback();
				conn.close();
			} catch (SQLException e) {
				log.error("commit transaction failure !");
			}finally
			{
				holder.remove();
			}
		}
	}
	
	public static BasicDataSource getDataSource(){
		return source;
	}
	
}
