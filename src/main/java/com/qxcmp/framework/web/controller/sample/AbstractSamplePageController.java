package com.qxcmp.framework.web.controller.sample;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.support.Color;

import java.util.Random;

public abstract class AbstractSamplePageController extends QXCMPController {

    public Color randomColor() {
        return Color.values()[new Random().nextInt(Color.values().length)];
    }
}
