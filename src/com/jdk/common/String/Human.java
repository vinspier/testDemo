package com.jdk.common.String;

/**
 * @ClassName: Human
 * @Description:
 * @Author:
 * @Date: 2019/10/22 11:44
 * @Version V1.0
 **/
public class Human {

    private String head;
    private String body;
    private String foot;

    public Human() {
    }

    public Human(String head, String body, String foot) {
        this.head = head;
        this.body = body;
        this.foot = foot;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot) {
        this.foot = foot;
    }
}
