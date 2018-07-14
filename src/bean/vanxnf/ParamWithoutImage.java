package bean.vanxnf;

import java.util.ArrayList;

public class ParamWithoutImage {

    private String key;
    //parameter_id
    private ArrayList<Integer> ids;

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
