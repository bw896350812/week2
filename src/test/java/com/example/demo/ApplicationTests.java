package com.example.demo;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.bean.Goods;
import com.tzh.utils.IOToFileUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
	
	//在测试类中注入redisTemplate  变量的名称要和redisTemplate一模一样
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void contextLoads() {
	}
	/***
	 * 将数据导入到redis的list中
	 */
	@Test
	public void testList() {
		//1.先使用工具类解析test.txt文件/1012_boot_demo_redis-week2/src/test/java/test.txt
		//将文件的每一行数据解析之后装入到list集合中，每一行数据就是一个goods对象
		List<String> readFileByLinesList = IOToFileUtils.readFileByLinesList(System.getProperty("user.dir")+"/src/test/java/test.txt");
		
		BoundListOperations<String,Object> boundListOps = redisTemplate.boundListOps("goodsList");
		
		//测试数据读取是否成功
		for (String string : readFileByLinesList) {
			//System.out.println(string);
			//根据|切割字符串
			String[] split = string.split("\\|");
			
			Goods goods = new Goods();
			goods.setGid(Integer.parseInt(split[0]));
			goods.setGname(split[1]);
			goods.setPrice(Double.parseDouble(split[2]));
			goods.setPercent(Double.parseDouble(split[3]));
			
			/*完成页面显示功
			能，必须分页且倒着显示，如第一页为 106、105、104……，最后一页
			为……3，2，1。
			倒着显示  106 105 104  存的时候使用leftPush存入redis
			*/
			
			Long leftPush = boundListOps.leftPush(goods);
			System.out.println(leftPush);
			
		}
	}
	
	//同样的解析方法往zset中也存一份
	@Test
	public void testZset() {
		//1.先使用工具类解析test.txt文件/1012_boot_demo_redis-week2/src/test/java/test.txt
		//将文件的每一行数据解析之后装入到list集合中，每一行数据就是一个goods对象
		List<String> readFileByLinesList = IOToFileUtils.readFileByLinesList(System.getProperty("user.dir")+"/src/test/java/test.txt");
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		BoundZSetOperations<String, Object> boundZSetOps = redisTemplate.boundZSetOps("zsetGoods");
		
		//测试数据读取是否成功
		for (String string : readFileByLinesList) {
			//System.out.println(string);
			//根据|切割字符串
			String[] split = string.split("\\|");
			
			Goods goods = new Goods();
			goods.setGid(Integer.parseInt(split[0]));
			goods.setGname(split[1]);
			goods.setPrice(Double.parseDouble(split[2]));
			goods.setPercent(Double.parseDouble(split[3]));
			
			//往有序集合zset中添加数据时候需要附加分数权重   goods.getPercent()百分比为分数权重
			Boolean add = boundZSetOps.add(goods, goods.getPercent());
			System.out.println(add);
		}
	}

}
