package com.qooems.excel.common.base;

import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

//	protected static Logger logger = Logger.getLogger(BaseServiceImpl.class);

	@Autowired
	private BaseMapper<T> baseMapper;


	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseServiceImpl(){
		ParameterizedType pt=(ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}

	/**
	 * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
	 * @param key
	 * @return
	 */
	public T selectByPrimaryKey(Object key) {
		return baseMapper.selectByPrimaryKey(key);
	}

	/**
	 * 保存一个实体，null的属性不会保存，会使用数据库默认值
	 * @param entity
	 * @return
	 */
	public int insertSelective(T entity) {
		return baseMapper.insertSelective(entity);
	}

	/**
	 * 根据主键更新实体全部字段，实体字段为null值时也会被更新null
	 * @param entity
	 * @return
	 */
	public int updateByPrimaryKey(T entity) {
		return baseMapper.updateByPrimaryKey(entity);
	}

	/**
	 * 根据主键字段进行删除，方法参数必须包含完整的主键属性
	 * @return
	 */
	public int deleteByPrimaryKey(Object key) {
		return baseMapper.deleteByPrimaryKey(key);
	}

	/**
	 * 根据实体属性作为条件进行删除，查询条件使用等号
	 *
	 * @param entity
	 * @return
	 */
	public int delete(T entity) {
		return baseMapper.delete(entity);
	}

	/**
	 * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
	 * @param entity
	 * @return
	 */
	public T selectOne(T entity) {
		return baseMapper.selectOne(entity);
	}

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号
	 * @param entity
	 * @return
	 */
	public List<T> select(T entity) {
		return baseMapper.select(entity);
	}

	/**
	 * 根据ids查询（uuid）
	 * @param ids
	 * @return
	 */
	public List<T> selectByUUIds(String ids){
		Example example = new Example(clazz);
		List<String> idList = new ArrayList<>();
		for (String id : ids.split(",")) {
			idList.add(id);
		}
		example.createCriteria().andIn("id", idList);

		return baseMapper.selectByExample(example);
	}

	/**
	 * 查询全部结果
	 * @return
	 */
	public List<T> selectAll() {
		return baseMapper.selectAll();
	}
	


	/**
	 * 根据主键更新属性不为null的值
	 * @param entity
	 * @return
	 */
	public int updateByPrimaryKeySelective(T entity) {
		return baseMapper.updateByPrimaryKeySelective(entity);
	}


	/**
	 * 根据ids删除（自增id）
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String ids) {
		return baseMapper.deleteByIds(ids);
	}


	/**
	 * 根据ids删除（uuid）
	 * @param ids
	 * @return
	 */
	public int deleteByUUIds(String ids) {
		Example example = new Example(clazz);
		List<String> idList = new ArrayList<>();
		for (String id : ids.split(",")) {
			idList.add(id);
		}
		example.createCriteria().andIn("id", idList);

		return baseMapper.deleteByExample(example);
	}

	
	/**
	 * 根据Example条件进行查询
	 */
	public List<T> selectByExample(Object example){
		return baseMapper.selectByExample(example);
	}
	
	/**
	 * 根据实体中的属性查询总数，查询条件使用等号
	 * @param entity
	 * @return
	 */
	public int selectCount(T entity){
		return baseMapper.selectCount(entity);
	}


	/**
	 * 批量插入
	 * @param recordList
	 * @return
	 */
	@Override
	public int insertListByUUID(List<T> recordList) {
		int n = 0;
		for(T record : recordList){
			insertSelective(record);
			n++;
		}
		return n;
	}

	/**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
     *
     * @param recordList
     * @return
     */
	@Override
	public int insertList(List<T> recordList) {
		return baseMapper.insertList(recordList);

	}
	
	/**
	 * 查询所有
	 */
	@Override
	public List<T> findAll(){
		return baseMapper.selectAll();
	}

    @Override
    public int selectCountByExample(Example example) {
        return baseMapper.selectCountByExample(example);
    }
}
