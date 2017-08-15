package com.qxcmp.framework.view.table;

import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewField;

@TableView
public class TestTable2 {

    @TableViewField
    private String username;

    @TableViewField
    private String email;

    @TableViewField
    private Integer age;

    public TestTable2() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
