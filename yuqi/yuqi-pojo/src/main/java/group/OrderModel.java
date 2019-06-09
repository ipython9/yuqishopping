package group;

import com.yuqi.pojo.TbOrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * @Author:
 * @Description:
 * @Date: Create in 22:56 2019/5/10
 */
public class OrderModel implements Serializable {
    private String sellerId;//商家ID
    private String sellerName;//商家名称

    private List<Order> orderList;//订单明细集合
}
