package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.core.QXCMPConfiguration;
import com.qxcmp.framework.web.BasicErrorController;
import com.qxcmp.framework.web.view.FrontendPage;
import com.qxcmp.framework.web.view.elements.grid.AbstractGrid;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.header.IconHeader;
import com.qxcmp.framework.web.view.elements.html.P;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.views.Overview;
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
        String text = parseStatusCode(status);
        String path = errors.get("path").toString();
        String message = errors.get("message").toString();

        if (path.startsWith(QXCMPConfiguration.QXCMP_BACKEND_URL)) {
            return page().addComponent(getDefaultErrorPageContent(status, text, message)).build();
        } else {
            return new FrontendPage(request, response).addComponent(getDefaultErrorPageContent(status, text, message)).build();
        }
    }

    private AbstractGrid getDefaultErrorPageContent(HttpStatus status, String text, String message) {
        return new VerticallyDividedGrid().setTextContainer().setAlignment(Alignment.CENTER).setVerticallyPadded().setColumnCount(ColumnCount.ONE)
                .addItem(new Col().addComponent(new Overview(new IconHeader(status.toString(), new Icon("warning circle")).setSubTitle(text)).addComponent(new P(message))));
    }

    private String parseStatusCode(HttpStatus status) {
        switch (status) {
            case CONTINUE:
                break;
            case SWITCHING_PROTOCOLS:
                break;
            case PROCESSING:
                break;
            case CHECKPOINT:
                break;
            case OK:
                break;
            case CREATED:
                break;
            case ACCEPTED:
                break;
            case NON_AUTHORITATIVE_INFORMATION:
                break;
            case NO_CONTENT:
                break;
            case RESET_CONTENT:
                break;
            case PARTIAL_CONTENT:
                break;
            case MULTI_STATUS:
                break;
            case ALREADY_REPORTED:
                break;
            case IM_USED:
                break;
            case MULTIPLE_CHOICES:
                break;
            case MOVED_PERMANENTLY:
                break;
            case FOUND:
                break;
            case MOVED_TEMPORARILY:
                break;
            case SEE_OTHER:
                break;
            case NOT_MODIFIED:
                break;
            case USE_PROXY:
                break;
            case TEMPORARY_REDIRECT:
                break;
            case PERMANENT_REDIRECT:
                break;
            case BAD_REQUEST:
                break;
            case UNAUTHORIZED:
                break;
            case PAYMENT_REQUIRED:
                break;
            case FORBIDDEN:
                break;
            case NOT_FOUND:
                break;
            case METHOD_NOT_ALLOWED:
                break;
            case NOT_ACCEPTABLE:
                break;
            case PROXY_AUTHENTICATION_REQUIRED:
                break;
            case REQUEST_TIMEOUT:
                break;
            case CONFLICT:
                break;
            case GONE:
                break;
            case LENGTH_REQUIRED:
                break;
            case PRECONDITION_FAILED:
                break;
            case PAYLOAD_TOO_LARGE:
                break;
            case REQUEST_ENTITY_TOO_LARGE:
                break;
            case URI_TOO_LONG:
                break;
            case REQUEST_URI_TOO_LONG:
                break;
            case UNSUPPORTED_MEDIA_TYPE:
                break;
            case REQUESTED_RANGE_NOT_SATISFIABLE:
                break;
            case EXPECTATION_FAILED:
                break;
            case I_AM_A_TEAPOT:
                break;
            case INSUFFICIENT_SPACE_ON_RESOURCE:
                break;
            case METHOD_FAILURE:
                break;
            case DESTINATION_LOCKED:
                break;
            case UNPROCESSABLE_ENTITY:
                break;
            case LOCKED:
                break;
            case FAILED_DEPENDENCY:
                break;
            case UPGRADE_REQUIRED:
                break;
            case PRECONDITION_REQUIRED:
                break;
            case TOO_MANY_REQUESTS:
                break;
            case REQUEST_HEADER_FIELDS_TOO_LARGE:
                break;
            case UNAVAILABLE_FOR_LEGAL_REASONS:
                break;
            case INTERNAL_SERVER_ERROR:
                break;
            case NOT_IMPLEMENTED:
                break;
            case BAD_GATEWAY:
                break;
            case SERVICE_UNAVAILABLE:
                break;
            case GATEWAY_TIMEOUT:
                break;
            case HTTP_VERSION_NOT_SUPPORTED:
                break;
            case VARIANT_ALSO_NEGOTIATES:
                break;
            case INSUFFICIENT_STORAGE:
                break;
            case LOOP_DETECTED:
                break;
            case BANDWIDTH_LIMIT_EXCEEDED:
                break;
            case NOT_EXTENDED:
                break;
            case NETWORK_AUTHENTICATION_REQUIRED:
                break;
        }

        return "网页叔叔搭乘航班去追寻诗和远方了";
    }
}