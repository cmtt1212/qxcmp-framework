package com.qxcmp.framework.web;

import com.qxcmp.framework.web.view.BackendPage;
import com.qxcmp.framework.web.view.Page;

public class QXCMPBackendController extends QXCMPController {

    @Override
    protected BackendPage page() {
        return applicationContext.getBean(BackendPage.class, request, response);
    }
}
