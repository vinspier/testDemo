package jdk1;

/**
 * @ClassName: A
 * @Description:
 * @Author:
 * @Date: 2020/5/14 11:51
 * @Version V1.0
 **/
public class A {
    private String uuid;
    private String name;
    private B b;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "A{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", b=" + b +
                '}';
    }
}
