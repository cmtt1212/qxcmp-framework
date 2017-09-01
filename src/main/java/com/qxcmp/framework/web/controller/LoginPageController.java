package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.containers.Col;
import com.qxcmp.framework.web.view.containers.Grid;
import com.qxcmp.framework.web.view.containers.Row;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.elements.Header;
import com.qxcmp.framework.web.view.elements.Image;
import com.qxcmp.framework.web.view.elements.menu.Menu;
import com.qxcmp.framework.web.view.support.Alignment;
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
            Grid grid = nextComponent(Grid.class);
            grid.setContainer(true);
            grid.setStackable(true);

            Col headerCol = nextComponent(Col.class);
            headerCol.getComponents().add(createHeaderSegment());

            Row headerRow = nextComponent(Row.class);
            headerRow.getColumns().add(headerCol);

            Col menuCol = nextComponent(Col.class);
            menuCol.getComponents().add(createMenu());

            Row menuRow = nextComponent(Row.class);
            menuRow.getColumns().add(menuCol);

            grid.getComponents().add(headerRow);
            grid.getComponents().add(menuRow);
            return grid;
        });
    }

    private Menu createMenu() {
        Menu menu = nextComponent(Menu.class);


        return menu;
    }

    private Segment createHeaderSegment() {
        Segment headerSegment = nextComponent(Segment.class);
        Header header = nextComponent(Header.class);
        Image image = nextComponent(Image.class);
        image.setSource(qxcmpConfiguration.getLogo());
        image.setUrl("/");
        header.setIconHeader(true);
        header.setTitle(qxcmpConfiguration.getTitle());
        header.setAlignment(Alignment.CENTER);
        header.setImage(image);
        headerSegment.getComponents().add(header);
        return headerSegment;
    }

    private Segment createLoginForm() {
        Segment segment = nextComponent(Segment.class);
        segment.getComponents().add(new AbstractComponent("qxcmp/components/login") {
        });
        return segment;
    }

    private Color randomColor() {
        return Color.values()[new Random().nextInt(Color.values().length)];
    }
}
