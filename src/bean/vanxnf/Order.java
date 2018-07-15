package bean.vanxnf;

/**单笔订单详情 - historyOrder*/
public class Order {

    private int commodityId;

    private String title;

    private int quantity;

    private double sumPrice;

    private String attrWithImage;

    private String attrWithoutImage;

    private int userId;

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
