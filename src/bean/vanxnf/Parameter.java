package bean.vanxnf;

import java.util.ArrayList;

/**单个商品属性与对应值 复合型*/
public class Parameter {
    /**属性(attribute)列表 id、带图属性标记与对应值*/
    private ArrayList<Attribute> attrs;
    /**无图的属性列表*/
    private ArrayList<ParamWithoutImage> params;
    /**带图片的属性列表*/
    private ArrayList<ParamWithImage> imageParams;

    public ArrayList<Attribute> getAttrs() {
        return attrs;
    }

    public void setAttrs(ArrayList<Attribute> attrs) {
        this.attrs = attrs;
    }

    public ArrayList<ParamWithoutImage> getParams() {
        return params;
    }

    public void setParams(ArrayList<ParamWithoutImage> params) {
        this.params = params;
    }

    public ArrayList<ParamWithImage> getImageParams() {
        return imageParams;
    }

    public void setImageParams(ArrayList<ParamWithImage> imageParams) {
        this.imageParams = imageParams;
    }
}


