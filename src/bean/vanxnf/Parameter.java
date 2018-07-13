package bean.vanxnf;

import java.util.ArrayList;

public class Parameter {

    private ArrayList<Integer> id;

    private ArrayList<String> attrs;

    private ArrayList<ParamWithoutImage> params;

    /**带图片属性个数*/
    private int imageFlag = 0;

    /**带图片的属性*/
    private ParamWithImage imageParams;

    public ArrayList<Integer> getId() {
        return id;
    }

    public void setId(ArrayList<Integer> id) {
        this.id = id;
    }

    public ArrayList<String> getAttrs() {
        return attrs;
    }

    public void setAttrs(ArrayList<String> attrs) {
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

    public ParamWithImage getImageParams() {
        return imageParams;
    }

    public void setImageParams(ParamWithImage imageParams) {
        this.imageParams = imageParams;
    }
}


