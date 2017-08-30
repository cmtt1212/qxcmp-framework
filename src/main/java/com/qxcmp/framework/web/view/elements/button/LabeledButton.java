package com.qxcmp.framework.web.view.elements.button;

import com.qxcmp.framework.web.view.elements.label.Label;
import com.qxcmp.framework.web.view.support.Color;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LabeledButton extends AbstractButton {

    private boolean left;

    private AbstractButton button;

    private Label label;

    public LabeledButton() {
        super("label");
    }

    public LabeledButton(String buttonText, String labelText) {
        this();
        Button button = new Button();
        button.setText(buttonText);
        this.button = button;
        this.label = new Label(labelText);
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
            stringBuilder.append(" left");
        }

        return stringBuilder.toString();
    }
}
