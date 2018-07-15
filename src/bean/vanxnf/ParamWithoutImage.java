package bean.vanxnf;

import java.util.ArrayList;
/**无图片参数 - 视图paramwithoutimage*/
public class ParamWithoutImage {
    /**属性值*/
    private String key;
    /**参数值 对应 id列表*/
    private ArrayList<Integer> ids;
    /**参数值列表*/
    private ArrayList<String> value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }

    public ArrayList<String> getValue() {
        return value;
    }

    public void setValue(ArrayList<String> value) {
        this.value = value;
    }
}
