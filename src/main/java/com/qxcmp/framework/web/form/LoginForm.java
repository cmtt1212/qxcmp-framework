package com.qxcmp.framework.web.form;

import com.qxcmp.framework.web.view.annotation.form.Form;
import lombok.Data;

@Form
@Data
public class LoginForm {

    private String username;

    private String password;
}
