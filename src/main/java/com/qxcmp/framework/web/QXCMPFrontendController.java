package com.qxcmp.framework.web;

import com.qxcmp.framework.web.page.AbstractFrontendPage;
import com.qxcmp.framework.web.page.MobilePage;

/**
 * 页面路由器基类
 *
 * @author aaric
 */
public abstract class QXCMPFrontendController extends AbstractQXCMPController {

    @Override
    protected AbstractFrontendPage page() {
        return applicationContext.getBean(MobilePage.class, request, response);
    }
}
