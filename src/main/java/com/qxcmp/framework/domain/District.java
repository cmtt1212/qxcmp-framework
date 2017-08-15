package com.qxcmp.framework.domain;

import lombok.Builder;
import lombok.Data;

/**
 * 行政区
 *
 * @author aaric
 */
@Data
@Builder
public class District {

    /**
     * 行政区编号
     */
    private String id;

    /**
     * 行政区级别 <li>0 - 国家</li> <li>1 - 省级行政区</li> <li>2 - 地级行政区</li> <li>3 - 县级行政区</li>
     */
    private int level;

    /**
     * 行政区名称
     */
    private String name;
}
