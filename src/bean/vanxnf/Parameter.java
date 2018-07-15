package bean.vanxnf;

import java.util.ArrayList;

public class Parameter {

    private ArrayList<Attribute> attrs;

    private ArrayList<ParamWithoutImage> params;

    /**带图片属性个数*/
    private int imageFlag = 0;

    /**带图片的属性*/
    private ArrayList<ParamWithImage> imageParams;
//    private ParamWithImage imageParams;

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

    public int getImageFlag() {
        return imageFlag;
    }

    public void setImageFlag(int imageFlag) {
        this.imageFlag = imageFlag;
    }

    public ArrayList<ParamWithImage> getImageParams() {
        return imageParams;
    }

    public void setImageParams(ArrayList<ParamWithImage> imageParams) {
        this.imageParams = imageParams;
    }
}


