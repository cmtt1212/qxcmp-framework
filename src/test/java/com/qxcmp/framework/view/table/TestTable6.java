package com.qxcmp.framework.view.table;

import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewAction;
import com.qxcmp.framework.view.annotation.TableViewField;
import org.springframework.data.domain.Sort;

@TableView(caption = "testCaption", entityIndex = "username", actionUrlPrefix = "/test/", actionColumnTitle = "testActionTitle",
        searchAction = @TableViewAction(
                title = "testSearchActionTitle"
        ),
        createAction = @TableViewAction(
                title = "testCreateActionTitle"
        ),
        findAction = @TableViewAction(
                title = "testReadActionTitle"
        ),
        updateAction = @TableViewAction(
                title = "testUpdateActionTitle"
        ),
        removeAction = @TableViewAction(
                title = "testDeleteActionTitle"
        ),
        sortAction = @TableViewAction(
                title = "testSortActionTitle"
        ))
public class TestTable6 {

    @TableViewField(
            title = "testUsernameTitle",
            description = "testUserDesc",
            sortDirection = Sort.Direction.DESC,
            urlTarget = "_blank",
            collectionEntityIndex = "username",
            urlEntityIndex = "testEntityIndex",
            urlPrefix = "/testUrlPrefix/"
    )
    private String username;

    @TableViewField(
            title = "testUsernameTitle",
            description = "testUserDesc",
            sortDirection = Sort.Direction.DESC,
            urlTarget = "_blank",
            collectionEntityIndex = "username",
            urlEntityIndex = "testEntityIndex",
            urlPrefix = "/testUrlPrefix/"
    )
    private String email;

    @TableViewField(
            title = "testUsernameTitle",
            description = "testUserDesc",
            sortDirection = Sort.Direction.DESC,
            urlTarget = "_blank",
            collectionEntityIndex = "username",
            urlEntityIndex = "testEntityIndex",
            urlPrefix = "/testUrlPrefix/"
    )
    private Integer age;

    public TestTable6() {
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
