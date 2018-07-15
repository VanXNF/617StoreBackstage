package bean.vanxnf;

import java.util.ArrayList;
/**带图片参数 - 视图paramwithimage*/
public class ParamWithImage {
    /**带图片参数值 以及 值对应id*/
    private ArrayList<Param> value;
    /**参数值对应图片链接 以及 图片id*/
    private ArrayList<Image> image;

    public ArrayList<Param> getValue() {
        return value;
    }

    public void setValue(ArrayList<Param> value) {
        this.value = value;
    }

    public ArrayList<Image> getImage() {
        return image;
    }

    public void setImage(ArrayList<Image> image) {
        this.image = image;
    }
}