package com.fit.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * DAO支持类实现
 */
public interface BaseCrudDao<T> {

	/**
	 * SQL语句查询数据
	 */
	public List<T> querySQL(@Param(value = "SqlSelect") String SqlSelect);

	/**
	 * 获取单条数据
	 * 
	 * @param id
	 */
	public T get(Integer id);

	/**
	 * 获取单条数据
	 * 
	 * @param entity
	 */
	public T get(T entity);

	/**
	 * 查询数据列表
	 */
	public List<T> findList(Object id);

	/**
	 * 查询数据列表
	 */
	public List<T> findList(Map<String, Object> map);

	/**
	 * 分页查询所有实体
	 * 
	 * @param page
	 *            分页条件
	 * @return Page 分页查询结果,附带结果列表及所有查询时的参数.<br>
	 *         可通过page.getResult()获取.
	 */
	public List<T> findList(@Param(value = "pager") Pager<T> pager);

	/**
	 * 查询总数量
	 */
	public int findCount();

	/**
	 * 插入数据
	 * 
	 * @param entity
	 */
	public int save(T entity);

	/**
	 * 更新数据
	 * 
	 * @param entity
	 */
	public int update(T entity);

	/**
	 * 删除数据
	 * 
	 * @param id
	 * @see public int delete(T entity)
	 */
	public int delete(Integer id);

	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * 
	 * @param entity
	 */
	public int delete(T entity);

}