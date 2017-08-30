package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.QXCMPComponent;
import com.qxcmp.framework.web.view.containers.Container;
import com.qxcmp.framework.web.view.containers.Segment;
import com.qxcmp.framework.web.view.containers.Segments;
import com.qxcmp.framework.web.view.elements.Header;
import com.qxcmp.framework.web.view.elements.Icon;
import com.qxcmp.framework.web.view.elements.Image;
import com.qxcmp.framework.web.view.elements.button.AnimatedButton;
import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.AnchorTarget;
import com.qxcmp.framework.web.view.support.Color;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

@Controller
public class LoginPageController extends QXCMPController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return page(() -> {
            Container container = nextComponent(Container.class);
            Segments segments = nextComponent(Segments.class);
            Segment headerSegment = nextComponent(Segment.class);
            Header header = nextComponent(Header.class);
            Image image = nextComponent(Image.class);
            image.setSource(qxcmpConfiguration.getLogo());
            image.setUrl("/");
            image.setUrlTarget(AnchorTarget.BLANK.getValue());
            header.setIconHeader(true);
            header.setTitle(qxcmpConfiguration.getTitle());
            header.setAlignment(Alignment.CENTER);
            header.setImage(image);
            headerSegment.getComponents().add(header);
            Segment formSegment = nextComponent(Segment.class);
            formSegment.getComponents().add(new QXCMPComponent("qxcmp/components/login") {
            });
            Segment buttonSegment = createButtonSegment();
            segments.getSegments().add(headerSegment);
            segments.getSegments().add(formSegment);
            segments.getSegments().add(buttonSegment);
            container.getComponents().add(segments);
            return container;
        });
    }

    private Segment createButtonSegment() {
        Segment segment = nextComponent(Segment.class);

        Button button = nextComponent(Button.class);
        button.setText("测试按钮");
        button.setColor(Color.values()[new Random().nextInt(Color.values().length)]);
        segment.getComponents().add(button);

        AnimatedButton animatedButton = nextComponent(AnimatedButton.class);
        animatedButton.setColor(Color.values()[new Random().nextInt(Color.values().length)]);
        animatedButton.setVisibleText("下载");
        animatedButton.setHiddenText("点击下载");
        segment.getComponents().add(animatedButton);

        animatedButton = nextComponent(AnimatedButton.class);
        animatedButton.setColor(Color.values()[new Random().nextInt(Color.values().length)]);
        animatedButton.setVisibleText("下载");
        Icon icon = nextComponent(Icon.class);
        icon.setIcon("download");
        animatedButton.setHiddenIcon(icon);
        animatedButton.setAnimatedType(AnimatedButton.AnimatedType.VERTICAL);
        segment.getComponents().add(animatedButton);

        animatedButton = nextComponent(AnimatedButton.class);
        animatedButton.setColor(Color.values()[new Random().nextInt(Color.values().length)]);
        animatedButton.setHiddenText("下载");
        icon = nextComponent(Icon.class);
        icon.setIcon("download");
        animatedButton.setVisibleIcon(icon);
        animatedButton.setAnimatedType(AnimatedButton.AnimatedType.FADE);
        segment.getComponents().add(animatedButton);

        animatedButton = nextComponent(AnimatedButton.class);
        animatedButton.setColor(Color.values()[new Random().nextInt(Color.values().length)]);
        animatedButton.setVisibleText("下载");
        icon = nextComponent(Icon.class);
        icon.setIcon("download");
        animatedButton.setVisibleIcon(icon);
        animatedButton.setHiddenText("隐藏内容");
        animatedButton.setAnimatedType(AnimatedButton.AnimatedType.FADE);
        segment.getComponents().add(animatedButton);

        animatedButton = nextComponent(AnimatedButton.class);
        animatedButton.setColor(Color.values()[new Random().nextInt(Color.values().length)]);
        animatedButton.setVisibleText("可见内容");
        icon = nextComponent(Icon.class);
        icon.setIcon("download");
        animatedButton.setHiddenIcon(icon);
        animatedButton.setHiddenText("隐藏内容");
        animatedButton.setAnimatedType(AnimatedButton.AnimatedType.FADE);
        segment.getComponents().add(animatedButton);

        return segment;
    }
}
