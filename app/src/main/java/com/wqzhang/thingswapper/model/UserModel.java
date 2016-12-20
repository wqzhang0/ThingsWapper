package com.wqzhang.thingswapper.model;

import java.util.Date;

/**
 * Created by wqzhang on 16-12-19.
 */

public class UserModel {
    private int id;
    private String name;
    private String account;
    private String password;
    private String email;
    private Date createDate;
    private boolean isSynchronize;

    public UserModel() {
    }

    public UserModel(int id, String name, String account, String password, String email, Date createDate) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.password = password;
        this.email = email;
        this.createDate = createDate;
        this.isSynchronize = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isSynchronize() {
        return isSynchronize;
    }

    public void setSynchronize(boolean synchronize) {
        isSynchronize = synchronize;
    }
}
