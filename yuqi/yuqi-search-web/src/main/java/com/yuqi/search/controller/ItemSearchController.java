package com.yuqi.search.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuqi.search.service.ItemSearchService;

@RestController
@RequestMapping("/itemsearch")
public class ItemSearchController {
	
	@Reference
	private ItemSearchService itemSearchService;
	
	@RequestMapping("/search")
	public Map search(@RequestBody Map searchMap){
		
		return itemSearchService.search(searchMap);
		
	}
	@RequestMapping("/name")
	public Map findName(){
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("当前登录人："+name);
		Map map = new HashMap();
		map.put("loginName",name);
		return map;
	}

}
