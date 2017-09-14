package com.qxcmp.framework.web;

import com.qxcmp.framework.web.view.FrontendPage;

/**
 * 页面路由器基类
 *
 * @author aaric
 */
public abstract class QXCMPController extends AbstractQXCMPController {

    @Override
    protected FrontendPage page() {
        return applicationContext.getBean(FrontendPage.class, request, response);
    }
}
