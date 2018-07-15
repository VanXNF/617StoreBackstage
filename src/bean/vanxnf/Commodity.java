package bean.vanxnf;

import java.util.ArrayList;

/**单个商品 - 表commodity + 视图mainpicture*/
public class Commodity {

    private int id;

    private String title;

    private double originalPrice;

    private double discountPrice;

    private String quickReview;

    private String overview;

    private int saleVolume;

    private String date;

    private ArrayList<Image> mainImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getQuickReview() {
        return quickReview;
    }

    public void setQuickReview(String quickReview) {
        this.quickReview = quickReview;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getSaleVolume() {
        return saleVolume;
    }

    public void setSaleVolume(int saleVolume) {
        this.saleVolume = saleVolume;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Image> getMainImage() {
        return mainImage;
    }

    public void setMainImage(ArrayList<Image> mainImage) {
        this.mainImage = mainImage;
    }
}
