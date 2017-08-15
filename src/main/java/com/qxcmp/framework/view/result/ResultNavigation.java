package com.qxcmp.framework.view.result;

import com.qxcmp.framework.view.component.Link;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

/**
 * 结果导航
 * <p>
 * 用于结果页面导航
 *
 * @author aaric
 */
@Data
@Builder
public class ResultNavigation {

    @Singular
    private List<Link> links;
}
