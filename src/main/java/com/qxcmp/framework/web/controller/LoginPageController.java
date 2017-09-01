package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.containers.Col;
import com.qxcmp.framework.web.view.containers.Grid;
import com.qxcmp.framework.web.view.containers.Row;
import com.qxcmp.framework.web.view.elements.Header;
import com.qxcmp.framework.web.view.elements.Image;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.Alignment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPageController extends QXCMPController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return page(() -> {
            Grid grid = new Grid();
            grid.setContainer(true);
            grid.setStackable(true);

            Col headerCol = new Col();
            headerCol.getComponents().add(createHeaderSegment());

            Row headerRow = new Row();
            headerRow.getColumns().add(headerCol);

            Col formCol = new Col();
            formCol.getComponents().add(createLoginForm());

            Row formRow = new Row();
            formRow.getColumns().add(formCol);

            grid.getComponents().add(headerRow);
            grid.getComponents().add(formRow);
            return grid;
        });
    }

    private Segment createHeaderSegment() {
        Segment headerSegment = new Segment();
        Header header = new Header();
        Image image = new Image();
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
        Segment segment = new Segment();
        segment.getComponents().add(new AbstractComponent("qxcmp/components/login") {
        });
        return segment;
    }

}
