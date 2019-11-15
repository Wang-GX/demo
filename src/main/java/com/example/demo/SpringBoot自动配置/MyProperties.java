package com.example.demo.SpringBoot自动配置;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "game.config")
public class MyProperties {

    //人物
    private String personName;
    private Integer sex = 1;
    //测试默认配置，测试OK！
    //TODO：根据SpringJavaConfig配置Bean中的描述，通过@Value(直接赋值和@Value的道理是一样的)和前缀同时注入属性值时，最终使用的是通过前缀注入的值，如果前缀没有注入值，那么就使用@Value注入的值，也就是所谓的默认值。
    private Integer age;
    //武器
    private String weaponName;
    private Integer attackPower;
    //防具
    private String clothsName;
    private Integer defensivePower;


    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public Integer getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(Integer attackPower) {
        this.attackPower = attackPower;
    }

    public String getClothsName() {
        return clothsName;
    }

    public void setClothsName(String clothsName) {
        this.clothsName = clothsName;
    }

    public Integer getDefensivePower() {
        return defensivePower;
    }

    public void setDefensivePower(Integer defensivePower) {
        this.defensivePower = defensivePower;
    }
}
