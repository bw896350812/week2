package com.example.demo.utils;

import java.util.HashMap;
import java.util.Map;

public class PageUtils {
	public static Map<String,Long> pageUtils(Long size,long cpage){
		Map<String,Long> map = new HashMap<>();
		//分页单位
		int pageSize = 4;
		//总页数
		long page = size%pageSize>0?1:0 ;
		long pages = page + size/pageSize  ;
		//当cpage小于1 的时候为1 
		cpage = cpage<1?1:cpage;
		//当cpage大于总页数的时候等于总页数
		cpage = cpage >pages?pages:cpage;

		//起始位置
		long start = (cpage-1)*pageSize;
		long end = start+pageSize-1;
		map.put("cpage", cpage);
		map.put("pages", pages);
		map.put("start", start);
		map.put("end", end);
		return map;
	}

}
