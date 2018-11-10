package iot.zjt.vertx.demo.core.eventbus;

/**
 * @author Mr Dk.
 */

public class Entity {

    private String name;
    private int age;
    private double price;

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}