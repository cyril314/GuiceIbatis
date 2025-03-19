package ${package}.dao;

import org.apache.ibatis.annotations.Mapper;
import ${package}.entity.${className}Entity;
import ${package}.base.BaseCrudDao;

/**
 * @AUTO ${comments}
 * @FILE ${className}DAO.java
 * @DATE ${datetime}
 * @Author ${author}
 */
@Mapper
public interface ${className}DAO extends BaseCrudDao<${className}Entity> {
	
}