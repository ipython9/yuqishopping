package group;

import java.io.Serializable;

/**
 * @Author:
 * @Description:
 * @Date: Create in 14:54 2019/4/6
 */
public class RecommendModel implements Serializable {
    private Long userId;
    private Long goodsId;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
