package ch.alexandrahauri.kiosk.business;

/**
 * Customer Class, contains name and age
 *
 * @author: Alexandra
 * @since: 22.06.2018
 **/
public class Customer {
    private String name;
    private Integer age;

    public Customer(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }
}
