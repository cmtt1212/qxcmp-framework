package com.qxcmp.framework.spider;

import com.qxcmp.framework.spdier.SpiderPipeline;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TestPipeline extends SpiderPipeline {
    @Override
    protected boolean isValidTarget(Object target) {
        return false;
    }

    @Override
    protected Optional getOriginTarget(Object target) {
        return null;
    }

    @Override
    protected boolean isTargetChanged(Object target, Object origin) {
        return false;
    }

    @Override
    protected void newTarget(Object target) {

    }

    @Override
    protected void updateTarget(Object target, Object origin) {

    }

    @Override
    protected void dropTarget(Object target) {

    }
}
