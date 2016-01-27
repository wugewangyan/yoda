package com.napoleon.life.common.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("rawtypes")
public class GenericDaoDefault<E extends Entity> extends SqlSessionDaoSupport implements GenericDao<E> {

	/** save. */
    public static final String INSERT_STATEMENT = "insert";
    /** update */
    public static final String UPDATE_STATEMENT = "update";
    /** remove */
    public static final String DELETE_STATEMENT = "delete";
    /** get single result */
    public static final String GET_STATEMENT = "get";
	
	protected Class entityClass;
	private String simpleClassName;
	
	public GenericDaoDefault(){
		this.entityClass = getGenericClass(this.getClass(), 0);
		this.simpleClassName = this.entityClass.getSimpleName();
	}
	
	@Override
	@Autowired(required = false)
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	public int add(E entity) {
		return this.getSqlSession().insert(this.getStatementId(INSERT_STATEMENT), entity);
	}

	public int update(E entity) {
		return super.getSqlSession().update(this.getStatementId(UPDATE_STATEMENT), entity);
	}

	@SuppressWarnings("unchecked")
	public int update(String sql, Object... args) {
		int rows = 0;
		if(args == null || args.length == 0){
			rows = super.getSqlSession().update(this.getStatementId(sql));
		}else if(args.length == 1){
			rows = super.getSqlSession().update(this.getStatementId(sql), args[0]);
		}else{
			Map map = new HashMap();
			for(int i = 0; i < args.length; i++){
				map.put(i + "", args[i]);
			}
			rows = super.getSqlSession().update(this.getStatementId(sql), map);
		}
	
		return rows;
	}

	public int delete(Serializable id) {
		return super.getSqlSession().delete(this.getStatementId(DELETE_STATEMENT), id);
	}

	@SuppressWarnings("unchecked")
	public int delete(String sql, Object... args) {
		int rows = 0;
        if (args == null || args.length == 0) {
            rows = super.getSqlSession().delete(getStatementId(sql));
        } else if (args.length == 1) {
            rows = super.getSqlSession().delete(getStatementId(sql), args[0]);
        } else {
            Map map = new HashMap();
            for (int i = 0; i < args.length; i++) {
                map.put(i + "", args[i]);
            }
            rows = super.getSqlSession().delete(getStatementId(sql), map);
        }
        return rows;
	}

	public int delete(E entity) {
		return this.delete(entity.getId());
	}

	public E get(Serializable id) {
		return super.getSqlSession().selectOne(this.getStatementId(GET_STATEMENT), id);
	}

	@SuppressWarnings("unchecked")
	public List query(String sql, Object... args) {
		List result = null;
		if(args == null || args.length == 0){
			result = super.getSqlSession().selectList(this.getStatementId(sql));
		}else if(args.length == 1){
			result = super.getSqlSession().selectList(this.getStatementId(sql), args[0]);
		}else{
			Map map = new HashMap();
			for(int i = 0; i < args.length; i++){
				map.put(i + "", args[i]);
			}
			result = super.getSqlSession().selectList(this.getStatementId(sql), map);
		}
		return result != null ? result : new ArrayList();
	}

	@SuppressWarnings("unchecked")
	public Object queryOne(String sql, Object... args) {
		Object ob = null;
        if (args == null || args.length == 0) {
            ob = super.getSqlSession().selectOne(getStatementId(sql));
        } else if (args.length == 1) {
            ob = super.getSqlSession().selectOne(getStatementId(sql), args[0]);
        } else {
            Map map = new HashMap();
            for (int i = 0; i < args.length; i++) {
                map.put(i + "", args[i]);
            }
            ob = super.getSqlSession().selectOne(getStatementId(sql), map);
        }
        return ob;
	}

	@SuppressWarnings("unchecked")
	public Map getMap(String sql, Object... args) {
		Map result = null;
        if (args == null || args.length == 0) {
            result = (Map) super.getSqlSession().selectOne(getStatementId(sql));
        } else if (args.length == 1) {
            result = (Map) super.getSqlSession().selectOne(getStatementId(sql), args[0]);
        } else {
            Map map = new HashMap();
            for (int i = 0; i < args.length; i++) {
                map.put(i + "", args[i]);
            }
            result = (Map) super.getSqlSession().selectOne(getStatementId(sql), map);
        }
        return result;
	}
	
	private static Class getGenericClass(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

            if ((params != null) && (params.length > index)) {
                return (Class) params[index];
            }
        }
        return null;
    }
	
	protected String getStatementId(String postfix) {
        return this.simpleClassName + "." + postfix;
    }
	
}
