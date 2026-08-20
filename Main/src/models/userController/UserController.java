package models.userController;

import models.Persistent;
import models.annotations.GetClass;
import models.annotations.GetFields;

@GetClass("TB_CLIENT")
public class UserController implements Persistent {

    @GetFields(name = "name")
    private String name;
    @GetFields(name = "age")
    private int age;
    @GetFields(name = "code")
    private String code;
    @GetFields(name = "email")
    private String email;
    @GetFields(name = "adress")
    private String adress;
    @GetFields(name = "number")
    private String number;
    @GetFields(name = "permission")
    private String permission;

    public UserController(String name, int age,  String code, String email, String adress, String number,
                          String permission) {
        this.name = name;
        this.code = code;
        this.email = email;
        this. adress = adress;
        this.number = number;
        this.permission = permission;
        this.age = age;
    }
    public UserController(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public Class getClassType() {
        return this.getClass();
    }
}
