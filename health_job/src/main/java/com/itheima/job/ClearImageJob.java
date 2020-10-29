package com.itheima.job;

import com.itheima.constant.RedisConst;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Slf4j
public class ClearImageJob {


	@Autowired
	JedisPool jedisPool;

	/**
	 * 步骤：
	 *  1. 获取所有的图片名称
	 *  2. 获取昨天的日期字符串
	 *  3.开始删除
	 *      3.1 删除昨天的图片
	 *      3.2 删除redis中的图片名称
	 */
	public void clearImageJob(){
		//获取所有的图片名称
		Set<String> imgNames  = jedisPool.getResource().smembers(RedisConst.SETMEAL_PIC_RESOURCES);
		/*//获取昨天的日期字符串
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date yesterday = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = sdf.format(yesterday);
		//开始删除
		for (String imgName : imgNames) {
			//如果以昨天的日期开头则可以删除
			if(imgName != null && imgName.startsWith(str)){
				//删除昨天的图片
				delQiniu(imgName);
				//删除redis中的图片名称
				jedisPool.getResource().srem(RedisConst.SETMEAL_PIC_RESOURCES, imgName);
				log.debug("删除了" + imgName + "图片!!");
			}
		}*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = sdf.format(new Date());

		for (String imgName : imgNames) {
			if(imgName != null && !imgName.startsWith(str)){
				delQiniu(imgName);
				jedisPool.getResource().srem(RedisConst.SETMEAL_PIC_RESOURCES, imgName);
				System.out.println("删除了" + imgName + "图片!!");
			}
		}
	}

	/**
	 * 删除七牛图片的方法
	 */
	public void delQiniu(String imgName){
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Region.region0());
		String key = imgName;
		Auth auth = Auth.create("6LLhkymg55mZ2RiAMdloHAr-nYbhYgY2eAI2yOWb", "ZaqnW4nGn930xrYmMUYD-4-f02MSZK2kmAL-eJEa");
		BucketManager bucketManager = new BucketManager(auth, cfg);
		try {
			bucketManager.delete("hm-31", key);
		} catch (QiniuException ex) {
			//如果遇到异常，说明删除失败
			System.err.println(ex.code());
			System.err.println(ex.response.toString());
		}

	}

}
