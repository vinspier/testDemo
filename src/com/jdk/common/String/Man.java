package com.jdk.common.String;

/**
 * @ClassName: Man
 * @Description:
 * @Author:
 * @Date: 2019/10/22 11:44
 * @Version V1.0
 **/
public class Man extends Human {
    private String diff;

    private Human wife;

    public Man() {
        super();
    }

    public Man(String head, String body, String foot) {
        super(head, body, foot);
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public Human getWife() {
        return wife;
    }

    public void setWife(Human wife) {
        this.wife = wife;
    }
}
