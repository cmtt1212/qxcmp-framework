package com.qxcmp.framework.view.table;

import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewAction;
import com.qxcmp.framework.view.annotation.TableViewField;
import org.springframework.http.HttpMethod;

@TableView(customActions = {
        @TableViewAction(formMethod = HttpMethod.GET),
        @TableViewAction(isForm = true, urlEntityIndex = "username", urlPrefix = "/testTable5Plus/")
})
public class TestTable5 {

    @TableViewField
    private String username;

    @TableViewField
    private String email;

    @TableViewField
    private Integer age;

    public TestTable5() {
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
