package com.yuqi.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuqi.pojo.Recommend;
import com.yuqi.recommend.service.ItemService;
import com.yuqi.recommend.service.RecommendService;
import entity.PageResult;
import entity.Result;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @Author:
 * @Description:
 * @Date: Create in 21:00 2019/4/7
 */
@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Reference
    private RecommendService recommendService;
    @RequestMapping("/create")
    public String create() throws IOException, TasteException {
        recommendService.deleteRecommendDataBase();
        recommendService.createRecommendDataBase();
        return "成功";
    }
    @RequestMapping("/createitem")
    public String createitem() throws IOException, TasteException {
        recommendService.deleteitemDataBase();
        recommendService.createItemDataBase();
        return "成功";
    }

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    public List<Recommend> findAll(){
        return recommendService.findAll();
    }


    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult  findPage(int page,int rows){

        return recommendService.findPage(page, rows);
    }

    /**
     * 增加
     * @param contentCategory
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Recommend contentCategory){
        try {
            recommendService.add(contentCategory);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     * @param contentCategory
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Recommend contentCategory){
        try {
            recommendService.update(contentCategory);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Recommend findOne(Long id){
        return recommendService.findOne(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long [] ids){
        try {
            recommendService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     * @param
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Recommend recommend, int page, int rows  ){
        return recommendService.findPage(recommend,page, rows);
    }

    @Reference
    private ItemService itemService;
    @RequestMapping("/searchitem")
    public PageResult searchitem(@RequestBody Recommend recommend, int page, int rows  ){
        return itemService.findPage(recommend,page, rows);
    }
}
