package com.qxcmp.framework.domain;

import java.util.List;

/**
 * 行政区服务
 *
 * @author aaric
 */
public interface DistrictService {

    /**
     * 获取所有省级行政区
     *
     * @return 省级行政区
     */
    List<District> getAllProvince();

    /**
     * 获取行政区的下级行政区
     *
     * @param id 行政区编号
     *
     * @return 下级行政区
     */
    List<District> getInferiors(String id);
}
