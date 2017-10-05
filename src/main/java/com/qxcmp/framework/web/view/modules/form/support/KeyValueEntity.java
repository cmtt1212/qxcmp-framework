package com.qxcmp.framework.web.view.modules.form.support;

import lombok.Data;

/**
 * 用于动态列表字段和哈希表的转换
 *
 * @author Aaric
 * @see com.qxcmp.framework.web.view.annotation.form.DynamicField
 */
@Data
public class KeyValueEntity {

    private String key;

    private String value;
}
