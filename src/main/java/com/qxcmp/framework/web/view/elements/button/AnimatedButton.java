package com.qxcmp.framework.web.view.elements.button;

import com.qxcmp.framework.web.view.elements.Icon;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AnimatedButton extends AbstractButton {

    /**
     * 动效类型
     */
    private AnimatedType animatedType = AnimatedType.DEFAULT;

    /**
     * 可见文本
     */
    private String visibleText;

    /**
     * 隐藏本文
     */
    private String hiddenText;

    /**
     * 可见图标
     * <p>
     * 该属性设置以后会在可见文本前面追加图标
     */
    private Icon visibleIcon;

    /**
     * 隐藏图标
     * <p>
     * 该属性设置以后会在隐藏文本前面追加图标
     */
    private Icon hiddenIcon;

    public AnimatedButton() {
        super("animated");
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassName() + " animated");

        if (StringUtils.isNotBlank(animatedType.getClassName())) {
            stringBuilder.append(" ").append(animatedType.getClassName());
        }

        return stringBuilder.toString();
    }

    public enum AnimatedType {
        DEFAULT(""),
        VERTICAL("vertical"),
        FADE("fade");

        private String className;

        AnimatedType(String className) {
            this.className = className;
        }

        public String getClassName() {
            return className;
        }
    }
}
