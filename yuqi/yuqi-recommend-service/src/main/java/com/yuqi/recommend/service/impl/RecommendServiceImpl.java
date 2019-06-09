package com.yuqi.recommend.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuqi.mapper.*;
import com.yuqi.pojo.*;
import com.yuqi.recommend.service.RecommendService;
import entity.PageResult;
import group.Goods;
import group.RecommendModel;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import util.CreateCsvUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:
 * @Description:
 * @Date: Create in 19:59 2019/4/7
 */
@Service(timeout=50000)
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    private RecommendMapper recommendMapper;
    //创建数据库
    @Override
    public void createRecommendDataBase()throws IOException, TasteException  {
        //查找元数据
        List<RecommendModel> list = new ArrayList<>();
        List<TbOrder> tbOrders = tbOrderMapper.selectByExample(null);
        for (TbOrder t:tbOrders) {
            //获取商品id 和 用户评分
            TbOrderItemExample tbOrderItemExample = new TbOrderItemExample();
            TbOrderItemExample.Criteria criteria = tbOrderItemExample.createCriteria();
            criteria.andOrderIdEqualTo(t.getOrderId());
            TbOrderItem tbOrderItem = tbOrderItemMapper.selectByExample(tbOrderItemExample).get(0);
            RecommendModel recommendModel = new RecommendModel();

            recommendModel.setGoodsId(tbOrderItem.getGoodsId());

            if(t.getPostFee()!=null){
                recommendModel.setNum(Integer.parseInt(t.getPostFee()));
            }else
            {

                recommendModel.setNum(3);

            }


            TbUserExample tbUserExample = new TbUserExample();
            TbUserExample.Criteria criteria1 =tbUserExample.createCriteria();
            criteria1.andUsernameEqualTo(t.getUserId());
            TbUser tbUser = tbUserMapper.selectByExample(tbUserExample).get(0);
            recommendModel.setUserId(tbUser.getId());
            list.add(recommendModel);
        }



        //生成csv 文件
        CreateCsvUtil createCsvUtil = new CreateCsvUtil();
        String csvFilePath = "C:\\AppData\\user.csv";
        String[] csvHeaders = {"0", "0","0"};
        createCsvUtil.writeCSV(list,csvFilePath,csvHeaders);
         //推荐结果写入数据库
        CreateUserCF();
        //
    }

    //删除数据库
    @Override
    public void deleteRecommendDataBase() {
       RecommendExample recommendExample =new RecommendExample();
       RecommendExample.Criteria criteria = recommendExample.createCriteria();
       criteria.andCategoryidEqualTo(1);
       recommendMapper.deleteByExample(recommendExample);
    }

    @Override
    public List<Recommend> findAll(String name) {
        //查找用户id根据id  推荐用户
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria =  tbUserExample.createCriteria();
         criteria.andUsernameEqualTo(name);
         TbUser tbUser =  tbUserMapper.selectByExample(tbUserExample).get(0);
         //查询推荐的列表
        RecommendExample recommendExample = new RecommendExample();
        RecommendExample.Criteria criteria1 =recommendExample.createCriteria();
        criteria1.andUseridEqualTo(tbUser.getId());
        criteria1.andCategoryidEqualTo(1);
        List<Recommend> list = recommendMapper.selectByExample(recommendExample);
        return list ;
    }

    @Override
    public List<Recommend> createFindAll() {
        return null;
    }
    final static int NEIGHBORHOOD_NUM = 2;
    final static int RECOMMENDER_NUM = 3;
    public void CreateUserCF() throws IOException, TasteException {
        //获取数据集
        String file = "C:\\AppData\\user.csv";
        DataModel model = new FileDataModel(new File(file));
        //比较用户之间的相似度 EuclideanDistanceSimilarity基于欧式距离的相似度  皮尔徐
        UserSimilarity user = new EuclideanDistanceSimilarity(model);

//
//        //基于皮尔逊相关系数的相似度
//        UserSimilarity user1 = new PearsonCorrelationSimilarity(model);

        //明确最相似的一组用户 邻居的个数NEIGHBORHOOD_NUM
        NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(NEIGHBORHOOD_NUM, user, model);
        //userCF
        Recommender r = new GenericUserBasedRecommender(model, neighbor, user);

        //

        LongPrimitiveIterator iter = model.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            //为uid推荐商品 推荐个数RECOMMENDER_NUM
            List<RecommendedItem> list = r.recommend(uid, RECOMMENDER_NUM);
            System.out.printf("uid:%s", uid);
            for (RecommendedItem ritem : list) {
                String i = Long.toString(ritem.getItemID());
                if(!"".equals(i)) {
                    Recommend recommend = new Recommend();
                    //设置分类
                    recommend.setCategoryid(1);
                    //设置用户姓名
                    recommend.setUserid(uid);

                    System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
                    //设置推荐度
                    recommend.setSortOrder((int) ritem.getValue());
                    //查询商品信息
                    Goods goods =findOneGoods(ritem.getItemID());
                    //设置商品姓名
                    recommend.setTitle(goods.getGoods().getGoodsName());
                    //设置商品url
                    recommend.setGoodsid(goods.getGoods().getId());
                   recommend.setUrl("http://localhost:9104/" + goods.getGoods().getId() + ".html");
                    //设置商品图片
                    if (!"[]".equals(goods.getGoodsDesc().getItemImages())) {
                        String img = goods.getGoodsDesc().getItemImages();
                        System.out.println(img);
                        String s1 = img.substring(22, 100);
                        System.out.println(s1);
                        recommend.setPic(s1);
                    }
                    //设置商品价格
                    recommend.setPrice(goods.getGoods().getPrice());
                  recommendMapper.insert(recommend);
                }
            }
        }
    }

    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;
    @Autowired
    private TbItemMapper itemMapper;

    //获取商品具体信息
    public Goods findOneGoods(Long id){
        Goods goods=new Goods();
        //商品基本表
        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
        goods.setGoods(tbGoods);
        //商品扩展表
        TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(id);
        goods.setGoodsDesc(goodsDesc);

        //读取SKU列表

        TbItemExample example=new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        List<TbItem> itemList = itemMapper.selectByExample(example);
        goods.setItemList(itemList);
        return goods;
    }



























    /**
     * 查询全部
     */
    @Override
    public List<Recommend> findAll() {
        return recommendMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Recommend> page=   (Page<Recommend>) recommendMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(Recommend recommend) {
        recommendMapper.insert(recommend);
    }


    /**
     * 修改
     */
    @Override
    public void update(Recommend recommend){
        recommendMapper.updateByPrimaryKey(recommend);
    }

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    @Override
    public Recommend findOne(Long id){
        return recommendMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for(Long id:ids){
            recommendMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(Recommend recommend, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

//        RecommendExample example=new RecommendExample();
//        RecommendExample.Criteria criteria = example.createCriteria();
//        criteria.andIdIsNotNull();
//        if(recommend!=null){
//            if(recommend.getUserid()!=null){
//                criteria.andUseridEqualTo(recommend.getUserid());
//            }
//
//        }

        Page<Recommend> page= (Page<Recommend>)recommendMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Recommend> findListByUserId(Long userId) {

        RecommendExample example=new RecommendExample();
        RecommendExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userId);
        return recommendMapper.selectByExample(example);
    }




    @Autowired
   private ItemModelMapper itemModelMapper;

    //增加物品游览记录
    @Override
    public void add(ItemModel itemModel) throws IOException, TasteException {
        ItemModelExample itemModelExample = new ItemModelExample();
        ItemModelExample.Criteria criteria =itemModelExample.createCriteria();
        criteria.andGoodsidEqualTo(itemModel.getGoodsid());
        criteria.andUidEqualTo(itemModel.getUid());
        List<ItemModel> itemModels = itemModelMapper.selectByExample(itemModelExample);
        if(itemModels.size()>0){
            ItemModel itemModel1 = itemModels.get(0);
            itemModel1.setPf(itemModel1.getPf()+1);
            itemModelMapper.updateByPrimaryKey(itemModel1);
            deleteitemDataBase();
            createItemDataBase();
        }else
        {
            itemModelMapper.insert(itemModel);
            deleteitemDataBase();
            createItemDataBase();
        }


    }

   // 查询所有商品游览记录
    @Override
    public List<ItemModel> findAllItemModel()
        {
            return itemModelMapper.selectByExample(null);
    }


    //创建基于物品协同过滤的数据库
    @Override
    public void createItemDataBase() throws IOException, TasteException {
        //查找元数据
        List<RecommendModel> list = new ArrayList<>();
        List<ItemModel> itemModels = itemModelMapper.selectByExample(null);
        for (ItemModel t:itemModels) {
            //获取商品id 和 用户评分
            RecommendModel recommendModel = new RecommendModel();
            recommendModel.setGoodsId(t.getGoodsid());
            recommendModel.setNum(t.getPf());
            recommendModel.setUserId(t.getUid());
            list.add(recommendModel);
        }



        //生成csv 文件
        CreateCsvUtil createCsvUtil = new CreateCsvUtil();
        String csvFilePath = "C:\\AppData\\item.csv";
        String[] csvHeaders = {"0", "0","0"};
        createCsvUtil.writeCSV(list,csvFilePath,csvHeaders);
        //推荐结果写入数据库
        CreateItemCF();
        //

    }

    //删除基于物品协同过滤的推荐列表
    @Override
    public void deleteitemDataBase() {
        RecommendExample recommendExample =new RecommendExample();
        RecommendExample.Criteria criteria = recommendExample.createCriteria();
        criteria.andCategoryidEqualTo(2);
        recommendMapper.deleteByExample(recommendExample);
    }

    //基于物品的协同过滤推荐列表
    @Override
    public List<Recommend> findItemAll(String name) {
        //查找用户id根据id  推荐用户
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria =  tbUserExample.createCriteria();
        criteria.andUsernameEqualTo(name);
        TbUser tbUser =  tbUserMapper.selectByExample(tbUserExample).get(0);
        //查询物品推荐的列表
        RecommendExample recommendExample = new RecommendExample();
        RecommendExample.Criteria criteria1 =recommendExample.createCriteria();
        criteria1.andUseridEqualTo(tbUser.getId());
        criteria1.andCategoryidEqualTo(2);
        List<Recommend> list = recommendMapper.selectByExample(recommendExample);
        return list ;
    }


    public void CreateItemCF() throws IOException, TasteException {
        //获取数据集
        String file = "C:\\AppData\\item.csv";
        DataModel model = new FileDataModel(new File(file));
        //比较用户之间的相似度 EuclideanDistanceSimilarity基于欧式距离的相似度  皮尔徐
        ItemSimilarity itemSimilarity =new PearsonCorrelationSimilarity(model);
//
//        //基于皮尔逊相关系数的相似度
//        UserSimilarity user1 = new PearsonCorrelationSimilarity(model);
        //明确最相似的一组用户 邻居的个数NEIGHBORHOOD_NUM
        //userCF
        Recommender r = new GenericItemBasedRecommender(model,itemSimilarity);
        //
        LongPrimitiveIterator iter = model.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            //为uid推荐商品 推荐个数RECOMMENDER_NUM
            List<RecommendedItem> list = r.recommend(uid, RECOMMENDER_NUM);
            System.out.printf("uid:%s", uid);
            for (RecommendedItem ritem : list) {
                String i = Long.toString(ritem.getItemID());
                if(!"".equals(i)) {
                    Recommend recommend = new Recommend();
                    //设置分类
                    recommend.setCategoryid(2);

                    //设置用户姓名
                    recommend.setUserid(uid);
                    System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
                    //设置推荐度
                    recommend.setSortOrder((int) ritem.getValue());
                    //查询商品信息
                    Goods goods =findOneGoods(ritem.getItemID());
                    //设置商品姓名
                    recommend.setTitle(goods.getGoods().getGoodsName());
                    recommend.setGoodsid(goods.getGoods().getId());
                    //设置商品url
                    recommend.setUrl("http://localhost:9104/" + goods.getGoods().getId() + ".html");
                    //设置商品图片
                    if (!"[]".equals(goods.getGoodsDesc().getItemImages())) {
                        String img = goods.getGoodsDesc().getItemImages();
                        System.out.println(img);
                        String s1 = img.substring(22, 100);
                        System.out.println(s1);
                        recommend.setPic(s1);
                    }
                    //设置商品价格
                    recommend.setPrice(goods.getGoods().getPrice());
                    recommendMapper.insert(recommend);
                }
            }
        }
    }
}
