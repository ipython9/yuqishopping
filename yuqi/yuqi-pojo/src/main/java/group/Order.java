package group;

import java.io.Serializable;
import java.math.BigDecimal;

public class Order  implements Serializable {
    //tborder
    //创建时间

    private String createTime;

    // 订单编号
    private String orderId;

    // 店铺名称
    private String sellerName;

    //tborderitem
    //商品图片

    private String picPath;

    //商品标题
    private String title;

    //价格
    private BigDecimal price;
    //规格
    private String spec;

    //付款状态
    private String status;

    private String loginName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    //数量
    private int num;

    //商品id
    private String  goodsId;

    private String receivename;

    private String receiveaddress;

    private String receivemobile;

    public String getReceivemobile() {
        return receivemobile;
    }

    public void setReceivemobile(String receivemonile) {
        this.receivemobile = receivemonile;
    }

    public String getReceivename() {
        return receivename;
    }

    public void setReceivename(String receivename) {
        this.receivename = receivename;
    }

    public String getReceiveaddress() {
        return receiveaddress;
    }

    public void setReceiveaddress(String receiveaddress) {
        this.receiveaddress = receiveaddress;
    }


    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getPicPath() {
        return picPath;
    }

    public String getTitle() {
        return title;
    }


    public String getSpec() {
        return spec;
    }

    public String getStatus() {
        return status;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setNum(int num) {
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
