package com.yuqi.search.service.impl;

import com.yuqi.mapper.TbGoodsDescMapper;
import com.yuqi.mapper.TbGoodsMapper;
import com.yuqi.mapper.TbItemCatMapper;
import com.yuqi.mapper.TbItemMapper;
import com.yuqi.pojo.TbGoods;
import com.yuqi.pojo.TbGoodsDesc;
import com.yuqi.pojo.TbItem;
import com.yuqi.pojo.TbItemExample;
import com.yuqi.pojo.TbItemExample.Criteria;
import com.yuqi.search.service.ItemPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemPageServiceImpl implements ItemPageService {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Value("${pagedir}")
	private String pagedir;
	
	
	@Autowired
	private TbGoodsMapper goodsMapper;
	
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public boolean genItemHtml(Long goodsId) {
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		try {
			Template template = configuration.getTemplate("item.ftl");//创建数据模型
			Map dataModel=new HashMap<>();
			TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);	//1.商品主表数据
			dataModel.put("goods", goods);
			TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);	//2.商品扩展表数据
			dataModel.put("goodsDesc", goodsDesc);
			String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();//3.读取商品分类
			String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
			String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
			dataModel.put("itemCat1", itemCat1);
			dataModel.put("itemCat2", itemCat2);
			dataModel.put("itemCat3", itemCat3);
			TbItemExample example=new TbItemExample();//4.读取SKU列表
			Criteria criteria = example.createCriteria();
			criteria.andGoodsIdEqualTo(goodsId);//SPU ID
			criteria.andStatusEqualTo("1");//状态有效			
			example.setOrderByClause("is_default desc");//按是否默认字段进行降序排序，目的是返回的结果第一条为默认SKU
			List<TbItem> itemList = itemMapper.selectByExample(example);
			dataModel.put("itemList", itemList);
			Writer out=new FileWriter(pagedir+goodsId+".html");
			template.process(dataModel, out);//输出
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteItemHtml(Long[] goodsIds) {
		try {
			for(Long goodsId:goodsIds){
				new File(pagedir+goodsId+".html").delete();		
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}

}
