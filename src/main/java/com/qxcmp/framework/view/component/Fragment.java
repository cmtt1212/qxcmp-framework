package com.qxcmp.framework.view.component;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * UI渲染模板组件
 * @author aaric
 */
@Data
@AllArgsConstructor
public class Fragment {

    private String fragmentFile;

    private String fragmentName;
}
