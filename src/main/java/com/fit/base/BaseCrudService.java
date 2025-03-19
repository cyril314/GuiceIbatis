package com.fit.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.mybatis.guice.transactional.Transactional;

/**
 * Service基类
 */
public abstract class BaseCrudService<D extends BaseCrudDao<T>, T extends BaseEntity<T>> {

	/**
	 * 持久层对象
	 */
	@Inject
	protected D dao;

	/**
	 * 根据SQL语句查询结果集
	 * 
	 * @param SqlSelect
	 *            SQL查询语句
	 */
	public List<T> querySQL(String SqlSelect) {
		return dao.querySQL(SqlSelect);
	}

	/**
	 * 获取单条数据
	 * 
	 * @param id
	 * @return
	 */
	public T get(Integer id) {
		return dao.get(id);
	}

	/**
	 * 获取单条数据
	 * 
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return dao.get(entity);
	}

	/**
	 * 查询列表数据
	 * 
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}

	/**
	 * 查询列表数据
	 * 
	 * @param map
	 * @return
	 */
	public List<T> findList(Map<String, Object> map) {
		return dao.findList(map);
	}

	public Pager<T> findPage(Pager<T> pager, T entity) {
		entity.setPager(pager);
		pager.setList(dao.findList(pager));
		pager.setRecords(dao.findCount());
		return pager;
	}

	/**
	 * 获取总数量
	 */
	public int findCount() {
		return dao.findCount();
	}

	/**
	 * 保存数据（插入或更新）
	 * 
	 * @param entity
	 */
	@Transactional
	public void save(T entity) {
		if (null == entity.getId()) {
			if (entity.getCreatdate() == null) {
				entity.setCreatdate(new Date());
			}
			dao.save(entity);
		} else {
			dao.update(entity);
		}
	}

	/**
	 * 更新数据
	 * 
	 * @param entity
	 */
	@Transactional
	public int update(T entity) {
		return dao.update(entity);
	}

	/**
	 * 删除数据
	 * 
	 * @param entity
	 */
	@Transactional
	public int delete(T entity) {
		return dao.delete(entity);
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 */
	@Transactional
	public int delete(Integer id) {
		return dao.delete(id);
	}

}
