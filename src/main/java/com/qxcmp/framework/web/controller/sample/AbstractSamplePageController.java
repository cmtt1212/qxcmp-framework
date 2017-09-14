package com.qxcmp.framework.web.controller.sample;

import com.qxcmp.framework.web.QXCMPFrontendController;
import com.qxcmp.framework.web.view.support.Color;

import java.util.Random;

public abstract class AbstractSamplePageController extends QXCMPFrontendController {

    public Color randomColor() {
        return Color.values()[new Random().nextInt(Color.values().length)];
    }
}
