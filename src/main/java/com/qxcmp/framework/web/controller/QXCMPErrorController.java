package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.core.QXCMPConfiguration;
import com.qxcmp.framework.web.BasicErrorController;
import com.qxcmp.framework.web.view.FrontendPage;
import com.qxcmp.framework.web.view.elements.divider.Divider;
import com.qxcmp.framework.web.view.elements.grid.AbstractGrid;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.header.IconHeader;
import com.qxcmp.framework.web.view.elements.html.P;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 平台后端错误页面路由
 * <p>
 * 用户处理平台后端遇到的所有错误消息，并生成错误消息页面
 *
 * @author aaric
 */
@Controller
public class QXCMPErrorController extends BasicErrorController {
    @Override
    protected ModelAndView getErrorPage(HttpServletRequest request, Map<String, Object> errors) {

        HttpStatus status = HttpStatus.valueOf(Integer.parseInt(errors.get("status").toString()));
        String path = errors.get("path").toString();
        String message = errors.get("message").toString();

        if (path.startsWith(QXCMPConfiguration.QXCMP_BACKEND_URL)) {
            return errorPage(status.toString(), "网页叔叔搭乘航班去追寻诗和远方了", message).build();
        } else {
            return new FrontendPage(request, response).addComponent(getDefaultErrorPageContent(status, message)).build();
        }
    }

    private AbstractGrid getDefaultErrorPageContent(HttpStatus status, String message) {
        return new VerticallyDividedGrid().setTextContainer().setAlignment(Alignment.CENTER).setVerticallyPadded().setColumnCount(ColumnCount.ONE)
                .addItem(new Col().addComponent(new Segment()
                        .addComponent(new IconHeader(status.toString(), new Icon("warning circle")).setSubTitle("网页叔叔搭乘航班去追寻诗和远方了"))
                        .addComponent(new Divider())
                        .addComponent(new P(message))
                ));
    }
}