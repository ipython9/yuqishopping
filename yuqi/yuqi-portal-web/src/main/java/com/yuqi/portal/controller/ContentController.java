package com.yuqi.portal.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.yuqi.pojo.Recommend;
import com.yuqi.recommend.service.RecommendService;
import com.yuqi.sellergoods.service.ContentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuqi.pojo.TbContent;



@RestController
@RequestMapping("/content")
public class ContentController {

	@Reference
	private ContentService contentService;
	@RequestMapping("/findByCategoryId")
	public List<TbContent> findByCategoryId(Long categoryId) {
		return contentService.findByCategoryId(categoryId);
	}

	@RequestMapping("/name")
	public Map findName() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("当前登录人：" + name);
		Map map = new HashMap();
		map.put("loginName", name);
		return map;
	}
	@Reference
	private RecommendService recommendService;
	@RequestMapping("/findContent")
	public List<Recommend> findContent(){
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		return  recommendService.findAll(name);
	}
}
