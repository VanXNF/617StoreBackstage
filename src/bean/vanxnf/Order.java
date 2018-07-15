package bean.vanxnf;

/**单个商品交易详情 - 视图historyOrder*/
public class Order {

    /**商品id*/
    private int commodityId;
    /**商品标题*/
    private String title;
    /**购买数量*/
    private int quantity;
    /**本商品合计金额*/
    private double sumPrice;
    /**带图片的属性选择值*/
    private String attrWithImage;
    /**无图片的属性选择值*/
    private String attrWithoutImage;
    /**购买本商品的用户id*/
    private int userId;
    /**本次交易订单id*/
    private int paymentId;

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getAttrWithImage() {
        return attrWithImage;
    }

    public void setAttrWithImage(String attrWithImage) {
        this.attrWithImage = attrWithImage;
    }

    public String getAttrWithoutImage() {
        return attrWithoutImage;
    }

    public void setAttrWithoutImage(String attrWithoutImage) {
        this.attrWithoutImage = attrWithoutImage;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
}
