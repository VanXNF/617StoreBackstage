package bean.vanxnf;

/**单条属性 - 表attribute*/
public class Attribute {
    /**属性id*/
    private int id;
    /**属性值*/
    private String attribute;
    /**带图属性标记，1为带图属性，0为无图属性*/
    private int imageFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public int getImageFlag() {
        return imageFlag;
    }

    public void setImageFlag(int imageFlag) {
        this.imageFlag = imageFlag;
    }
}
