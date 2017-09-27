package com.qxcmp.framework.core.web;

import com.qxcmp.framework.web.QXCMPBackendController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/tools")
public class AdminToolsPageController extends QXCMPBackendController {
}
