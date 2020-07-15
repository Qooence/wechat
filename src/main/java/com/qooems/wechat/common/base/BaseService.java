package com.qooems.wechat.common.base;


import tk.mybatis.mapper.entity.Example;

import java.util.List;


public interface BaseService<T>{

	/**
	 * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
	 * @param key
	 * @return
	 */
	T selectByPrimaryKey(Object key);
	
	/**
	 * 根据主键更新实体全部字段，实体字段为null值时也会被更新null
	 * @param entity
	 * @return
	 */
	int updateByPrimaryKey(T entity);
	
	/**
	 * 根据主键字段进行删除，方法参数必须包含完整的主键属性
	 * @return
	 */
	int deleteByPrimaryKey(Object key);

	/**
	 * 保存一个实体，null的属性不会保存，会使用数据库默认值
	 * @param entity
	 * @return
	 */
	int insertSelective(T entity);
	
	/**
	 * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
	 * @param entity
	 * @return
	 */
	T selectOne(T entity);

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号
	 * @param entity
	 * @return
	 */
	List<T> select(T entity);

	/**
	 * 根据ids查询（uuid）
	 * @param ids
	 * @return
	 */
	List<T> selectByUUIds(String ids);

	/**
	 * 查询全部结果
	 * @return
	 */
	List<T> selectAll();

	
	/**
	 * 根据主键更新属性不为null的值
	 * @param entity
	 * @return
	 */
	int updateByPrimaryKeySelective(T entity);



	/**
	 * 根据ids删除(自增id)
	 * @param ids
	 * @return
	 */
	int deleteByIds(String ids);


	/**
	 * 根据ids删除（uuid）
	 * @param ids
	 * @return
	 */
	int deleteByUUIds(String ids);

	/**
	 * 根据实体属性作为条件进行删除，查询条件使用等号
	 *
	 * @param entity
	 * @return
	 */
	int delete(T entity);

	/**
	 * 根据Example条件进行查询
	 */
	List<T> selectByExample(Object example);
	
	/**
	 * 根据实体中的属性查询总数，查询条件使用等号
	 * @param entity
	 * @return
	 */
	int selectCount(T entity);


	/**
	 * 批量插入
	 * @param recordList
	 * @return
	 */
	int insertListByUUID(List<T> recordList);

	/**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
     *
     * @param recordList
     * @return
     */
	public int insertList(List<T> recordList);
	
	/**
	 * 查询所有
	 */
	public List<T> findAll();

    /**
     * 根据Example条件进行查询总数
     *
     * @param example
     * @return
     */
    int selectCountByExample(Example example);
}
