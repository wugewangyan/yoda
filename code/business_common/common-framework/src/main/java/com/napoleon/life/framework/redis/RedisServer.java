package com.napoleon.life.framework.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;

import com.napoleon.life.exception.CommonException;

/**
 * redis缓存操作 时间单位是秒 永久为null
 * 
 */
public class RedisServer {

	private JedisPool jedisPool; // 非切片连接池
	
	private ShardedJedisPool shardedJedisPool; // 切片连接池

	/**
	 * 创建事务
	 * 
	 * @author Zheng HongChen
	 * @date 2015年10月9日 上午10:36:27
	 * @return
	 */
	public Transaction startTransaction() {
		return jedisPool.getResource().multi();
	}

	/**
	 * 提交事务
	 * 
	 * @author Zheng HongChen
	 * @date 2015年10月9日 上午10:38:35
	 * @param tx
	 * @return
	 */
	public List<Object> commitTransaction(Transaction tx) {
		return tx.exec();
	}

	/**
	 * 创建分布式管道
	 * 
	 * @author Zheng HongChen
	 * @date 2015年10月9日 上午10:31:27
	 * @return
	 */
	public ShardedJedisPipeline getShardedPipeline() {
		return shardedJedisPool.getResource().pipelined();
	}

	/**
	 * 提交分布式管道
	 * 
	 * @author Zheng HongChen
	 * @date 2015年10月9日 上午10:33:59
	 * @return
	 */
	public List<Object> commitShardedPipeline(ShardedJedisPipeline pipeline) {
		return pipeline.syncAndReturnAll();
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 * @author 郑宏晨 2014年8月21日 下午3:38:49
	 * @version 1.0
	 */
	public String get(final String key, final Integer time) {
		String value = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.get(key);
			if (time != null) {
				shardedJedis.expire(key, time);
			}
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return value;
	}

	/**
	 * String 数据
	 * 
	 * @param
	 * @return
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-9-26 上午10:48:11
	 */
	public String set(final String key, final String value, final Integer time) {
		String result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.set(key, value);
			if (time != null) {
				shardedJedis.expire(key, time);
			}
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param
	 * @return
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-9-26 上午10:49:08
	 */
	public Long del(final String key) {
		Long result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.del(key);
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	/**
	 * 添加list
	 * 
	 * @date 2014年9月15日
	 * @auth 焦明星
	 * @param key
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public Long setList(String key, List<String> values, final Integer time) {
		Long result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();

			for (String value : values) {
				result = shardedJedis.rpush(key, value);
			}
			if (time != null) {
				shardedJedis.expire(key, time);
			}
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	/**
	 * 获取list
	 * 
	 * @date 2014年9月15日
	 * @auth 焦明星
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<String> getKeyList(String key) {
		List<String> result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			System.out.println(shardedJedis.lindex(key, 0));
			result = shardedJedis.lrange(key, 0, -1);
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	/**
	 * list头部插入单条数据
	 * 
	 * @param
	 * @return 插入后的数量
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-10-17 上午10:47:55
	 */
	public Long setListToFirst(String key, String value, final Integer time) {
		Long result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.lpush(key, value);
			if (time != null) {
				shardedJedis.expire(key, time);
			}
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	/**
	 * list头部插入单条数据
	 * 
	 * @param
	 * @return 插入后的数量
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-10-17 上午10:47:55
	 */
	public Long setListToFirst(String key, byte[] value, final Integer time) {
		Long result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.lpush(key.getBytes(), value);
			if (time != null) {
				shardedJedis.expire(key, time);
			}
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	/**
	 * 设置队列里面一个元素的值
	 * 
	 * @param
	 * @return value
	 * @author 汪亚波 2014-10-21 下午13:10:55
	 */
	public String setListValue(String key, int index, String value) {
		String result = null;
		ShardedJedis shardedJedis = null;
		try {

			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.lset(key, index, value);

		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	/**
	 * 获取一个元素，通过其索引列表
	 * 
	 * @param
	 * @return value
	 * @author 汪亚波 2014-10-21 下午13:11:47
	 */
	public String getListIndex(String key, int index){
		String result = null;
		ShardedJedis shardedJedis = null;
		try {

			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.lindex(key, index);

		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	/**
	 * list尾部插入单条数据
	 * 
	 * @param
	 * @return 插入后的数量
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-10-17 上午10:47:55
	 */
	public Long setListToLast(String key, String value, final Integer time) {
		Long result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.rpush(key, value);
			if (time != null) {
				shardedJedis.expire(key, time);
			}
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	/**
	 * 弹出头数据
	 * 
	 * @param
	 * @return 结果
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-10-17 上午10:47:55
	 */
	public String getListByFirst(String key) {
		String result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.lpop(key);
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	/**
	 * 弹出尾部数据
	 * 
	 * @param
	 * @return 结果
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-10-17 上午10:47:55
	 */
	public byte[] getListByLast(String key) {
		byte[] result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.rpop(key.getBytes());
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	/**
	 * 获取链表item个数
	 * 
	 * @param
	 * @return 结果
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-10-17 上午10:47:55
	 */
	public Long getListChildCount(String key) {
		Long result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.llen(key);
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	/**
	 * 删除数据 索引0开始 也可以为负值，-1表示尾部元素
	 * 
	 * @param
	 * @return 结果
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-10-17 上午10:47:55
	 */
	public String delList(String key, Long start, Long end, final Integer time) {
		String result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.ltrim(key, start, end);
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	// -----------------------------------------------------

	/**
	 * hash存储
	 * 
	 * @param
	 * @return
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-9-26 上午10:38:45
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String setHashMap(final String key, final Map params, final Integer time) {
		String result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			Map<String, String> kv = new HashMap<String, String>();
			Set<Entry> keys = params.entrySet();
			for (Entry e : keys) {
				if (e.getValue() != null) {
					kv.put(e.getKey().toString(), e.getValue().toString());
				}
			}
			result = shardedJedis.hmset(key, kv);
			if (time != null) {
				shardedJedis.expire(key, time);
			}
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	/**
	 * hash存储
	 * 
	 * @param
	 * @return
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-9-26 上午10:38:45
	 */
	public Long setHash(final String key, final String field, final String value, final Integer time) {
		Long result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			result = shardedJedis.hset(key, field, value);
			if (time != null) {
				shardedJedis.expire(key, time);
			}
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return result;
	}

	/**
	 * hash数据获取
	 * 
	 * @param
	 * @return
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-9-26 上午10:44:10
	 */
	public List<String> getHashMap(final String key, final Integer time, final String... fields) throws Exception {
		List<String> value = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.hmget(key, fields);
			if (time != null) {
				shardedJedis.expire(key, time);
			}
//			if(fields.length == 1) {
//				return value.get(0);
//			}
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return value;
	}

	/**
	 * hash数据获取
	 *
	 * @param
	 * @return
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-9-26 上午10:44:10
	 */
	public String getHash(final String key, final String field, final Integer time) {
		String value = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.hget(key, field);
			if (time != null) {
				shardedJedis.expire(key, time);
			}
		} catch (Exception e) {
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return value;
	}


	/**
	 * hash获取所有的key
	 * 
	 * @param
	 * @return
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-9-26 上午10:44:10
	 */
	public Set<String> getHashAllKey(final String key, final Integer time) {
		Set<String> value = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.hkeys(key);
			if (time != null) {
				shardedJedis.expire(key, time);
			}
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return value;
	}

	/**
	 * hash获取所有
	 * 
	 * @param
	 * @return
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-9-26 上午10:44:10
	 */
	public Map<String, String> getHashAll(final String key, final Integer time) {
		Map<String, String> value = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.hgetAll(key);
			if (time != null) {
				shardedJedis.expire(key, time);
			}
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return value;
	}

	/**
	 * 删除指定的多个field
	 * 
	 * @param
	 * @return
	 * @author 郑宏晨 QQ:39482609 Email:hongchen629@gmail.com 2014-9-26 下午4:16:46
	 */
	public Long delHashField(final String key, final String... fields) {
		Long value = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.hdel(key, fields);
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
			throw new CommonException(e);
		} finally {
			returnResource(shardedJedisPool, shardedJedis);
		}
		return value;
	}

	// -----------------------------------------------------

	/**
	 * 返还连接
	 * 
	 * @param pool
	 * @param redis
	 * @author 郑宏晨 2014年8月21日 下午3:52:43
	 * @version 1.0
	 */
	public void returnResource(ShardedJedisPool pool, ShardedJedis redis) {
		if (redis != null) {
			pool.returnResource(redis);
		}
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

}