package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.QXCMPComponent;
import com.qxcmp.framework.web.view.containers.Container;
import com.qxcmp.framework.web.view.containers.Segment;
import com.qxcmp.framework.web.view.containers.Segments;
import com.qxcmp.framework.web.view.elements.Header;
import com.qxcmp.framework.web.view.elements.Icon;
import com.qxcmp.framework.web.view.elements.Image;
import com.qxcmp.framework.web.view.elements.button.*;
import com.qxcmp.framework.web.view.elements.label.Label;
import com.qxcmp.framework.web.view.elements.label.Labels;
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
            segments.getSegments().add(createHeaderSegment());
            //segments.getSegments().add(createLoginForm());
            segments.getSegments().add(createLabelSegment());
            segments.getSegments().add(createButtonSegment());
            container.getComponents().add(segments);
            return container;
        });
    }

    private Segment createHeaderSegment() {
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
        return headerSegment;
    }

    private Segment createLoginForm() {
        Segment segment = nextComponent(Segment.class);
        segment.getComponents().add(new QXCMPComponent("qxcmp/components/login") {
        });
        return segment;
    }

    private Segment createLabelSegment() {
        Segment segment = nextComponent(Segment.class);
        Header header = nextComponent(Header.class);
        header.setTitle("标签测试");
        segment.getComponents().add(header);

        Labels labels = nextComponent(Labels.class);
        for (int i = 0; i < 5; i++) {
            Label label = nextComponent(Label.class, "标签文本", "/admin");
            label.setColor(Color.values()[new Random().nextInt(Color.values().length)]);
            labels.getLabels().add(label);
        }
        segment.getComponents().add(labels);

        labels = nextComponent(Labels.class);
        labels.setTag(true);
        for (int i = 0; i < 5; i++) {
            Label label = nextComponent(Label.class, "标签文本", "/admin");
            label.setColor(Color.values()[new Random().nextInt(Color.values().length)]);
            labels.getLabels().add(label);
        }
        segment.getComponents().add(labels);

        labels = nextComponent(Labels.class);
        labels.setCircular(true);
        for (int i = 0; i < 5; i++) {
            Label label = nextComponent(Label.class, String.valueOf(i), "/admin");
            label.setColor(Color.values()[new Random().nextInt(Color.values().length)]);
            labels.getLabels().add(label);
        }
        segment.getComponents().add(labels);

        return segment;
    }

    private Segment createButtonSegment() {
        Segment segment = nextComponent(Segment.class);
        Header header = nextComponent(Header.class);
        header.setTitle("按钮测试");
        segment.getComponents().add(header);

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

        IconButton iconButton = nextComponent(IconButton.class, "download");
        iconButton.setColor(Color.values()[new Random().nextInt(Color.values().length)]);
        segment.getComponents().add(iconButton);

        LabeledButton labeledButton = nextComponent(LabeledButton.class, "按钮文本", "标签文本");
        labeledButton.setColor(Color.values()[new Random().nextInt(Color.values().length)]);
        segment.getComponents().add(labeledButton);

        labeledButton = nextComponent(LabeledButton.class, "按钮文本", "标签文本");
        labeledButton.setLeft(true);
        labeledButton.setColor(Color.values()[new Random().nextInt(Color.values().length)]);
        segment.getComponents().add(labeledButton);

        LabeledIconButton labeledIconButton = nextComponent(LabeledIconButton.class, "按钮文本", "user");
        labeledIconButton.setBasic(true);
        labeledIconButton.setColor(Color.values()[new Random().nextInt(Color.values().length)]);
        segment.getComponents().add(labeledIconButton);

        labeledIconButton = nextComponent(LabeledIconButton.class, "按钮文本", "user");
        labeledIconButton.setRightIcon(true);
        labeledIconButton.setColor(Color.values()[new Random().nextInt(Color.values().length)]);
        segment.getComponents().add(labeledIconButton);

        return segment;
    }
}
