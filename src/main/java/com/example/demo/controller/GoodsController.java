package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.bean.Goods;
import com.example.demo.utils.PageUtils;

@Controller
public class GoodsController {
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	//这个是从redis的list中取出数据展示到页面
	@RequestMapping("list")
	public String list(Model model ,
			@RequestParam(defaultValue="1")long cpage,
			@RequestParam(defaultValue="")String  orders
			) {
		
		/*List<Goods> goodsList = goodsService.getGoodsList();*/
		////RedisTemplate操作list集合的第二种方式
		BoundListOperations<String,Object> boundListOps = redisTemplate.boundListOps("goodsList");
		//使用分页工具类完成分页功能
		Map<String, Long> pageMap = PageUtils.pageUtils(boundListOps.size(), cpage);
		List<Object> range = boundListOps.range(pageMap.get("start"),pageMap.get("end"));
		//装载未排序的list集合
		model.addAttribute("goodsList", range);	
		model.addAttribute("pages", pageMap.get("pages"));
		model.addAttribute("cpage", pageMap.get("cpage"));
		
		return "list";
	}
	//这个是从redis的zset中取出数据展示到页面
		@RequestMapping("listZset")
		public String listZset(Model model ,
				@RequestParam(defaultValue="1")long cpage,
				@RequestParam(defaultValue="")String  orders
				) {
			
			/*List<Goods> goodsList = goodsService.getGoodsList();*/
			////RedisTemplate操作zset
			BoundZSetOperations<String,Object> boundZSetOps = redisTemplate.boundZSetOps("zsetGoods");
			//使用分页工具类完成分页功能
			Map<String, Long> pageMap = PageUtils.pageUtils(boundZSetOps.size(), cpage);
			//zset 查询  reverseRange是按照分数比重的倒序排序    range是按照分数的升序排序
			Set<Object> reverseRange = boundZSetOps.reverseRange(pageMap.get("start"),pageMap.get("end"));
			//装载未排序的list集合
			model.addAttribute("goodsList", reverseRange);	
			model.addAttribute("pages", pageMap.get("pages"));
			model.addAttribute("cpage", pageMap.get("cpage"));
			
			return "listSet";
		}

}
