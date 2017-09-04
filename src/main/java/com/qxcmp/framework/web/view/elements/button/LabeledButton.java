package com.qxcmp.framework.web.view.elements.button;

import com.qxcmp.framework.web.view.elements.label.Label;
import com.qxcmp.framework.web.view.support.Color;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 带标签的按钮
 *
 * @author Aaric
 */
@Getter
@Setter
public class LabeledButton extends AbstractButton {

    private boolean left;

    private AbstractButton button;

    private Label label;

    public LabeledButton(String buttonText, String labelText) {
        Button button = new Button();
        button.setText(buttonText);
        this.button = button;
        this.label = new Label(labelText);
    }

    @Override
    public String getFragmentName() {
        return "label";
    }

    @Override
    public void setColor(Color color) {
        if (Objects.nonNull(button)) {
            button.setColor(color);
        }

        if (Objects.nonNull(label)) {
            label.setColor(color);
        }
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassName()).append(" labeled");

        if (left) {
            stringBuilder.append(" left labeled");
        }

        return stringBuilder.toString();
    }
}
