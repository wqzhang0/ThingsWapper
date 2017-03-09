package com.wqzhang.thingswapper.util.net.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wqzhang on 17-3-8.
 */
public class UserDTO implements Serializable {
    private Long id;
    private String name;
    private String account;
    private String password;
    private String email;
    private Date createDate;
    private boolean synchronize;
    private int version;


    public UserDTO() {
    }

    public UserDTO(String name, String account, String password, String email) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.email = email;
        this.createDate = null;
        this.synchronize = false;
        this.version = -1;
    }

    public UserDTO(Long id, String name, String account, String password, String email, Date createDate, boolean synchronize) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.password = password;
        this.email = email;
        this.createDate = createDate;
        this.synchronize = synchronize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return synchronize;
    }

    public void setSynchronize(boolean synchronize) {
        this.synchronize = synchronize;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

}

