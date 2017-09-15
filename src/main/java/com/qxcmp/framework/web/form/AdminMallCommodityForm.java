package com.qxcmp.framework.web.form;

import com.google.common.collect.Lists;
import com.qxcmp.framework.core.validation.Image;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;
import org.assertj.core.util.Sets;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 商品编辑表单
 * <p>
 * 用于创建或者修改商品
 *
 * @author aaric
 */
@FormView(caption = "商品详情", action = QXCMP_BACKEND_URL + "/mall/commodity", enctype = "multipart/form-data")
@Data
public class AdminMallCommodityForm {


    /**
     * 商品主键，由框架自动生成
     */
    @FormViewField(type = InputFiledType.HIDDEN)
    private Long id;


    @FormViewField(label = "商品封面", type = InputFiledType.FILE)
    @Image
    private MultipartFile coverFile;

    @FormViewField(label = "商品封面链接", type = InputFiledType.TEXT, required = false)
    private String cover;


    /**
     * 商品标题
     */
    @FormViewField(label = "商品名称", type = InputFiledType.TEXT)
    private String title;

    /**
     * 商品子标题
     */
    @FormViewField(label = "商品子标题", type = InputFiledType.TEXT)
    private String subTitle;

    /**
     * 商品原始价格 - 用于界面显示
     */
    @FormViewField(label = "折扣前价格 (单位为：分)", type = InputFiledType.NUMBER)
    private int originPrice;

    /**
     * 商品结算价格 - 用于结算订单
     */
    @FormViewField(label = "售价 (单位为：分)", type = InputFiledType.NUMBER)
    private int sellPrice;

    /**
     * 该商品是否支持点数兑换
     */
    @FormViewField(label = "是否支持平台点数兑换", type = InputFiledType.SWITCH, labelOn = "支持", labelOff = "不支持")
    private boolean enablePoints;

    /**
     * 商品兑换需要的点数
     * <p>
     * 当平台开启积分兑换功能，并且该商品支持积分兑换的时候，可以使用该积分来兑换商品
     */
    @FormViewField(label = "兑换需要点数", type = InputFiledType.NUMBER, required = false)
    private int points;

    /**
     * 商品描述信息
     */
    @FormViewField(label = "商品描述", type = InputFiledType.TEXTAREA)
    private String description;

    /**
     * 商品当前库存
     */
    @FormViewField(label = "商品库存", type = InputFiledType.NUMBER)
    private int inventory;

    /**
     * 商品销量
     */
    private int salesVolume;

    /**
     * 商品是否下架，如果商品下架以后，将不会出现在商品池中
     */
    @FormViewField(label = "商品是否下架", type = InputFiledType.SWITCH)
    private boolean soldOut;

    /**
     * 商品介绍相册
     */
    @FormViewField(label = "商品相册", type = InputFiledType.ALBUM)
    private Set<String> albums = Sets.newLinkedHashSet();

    @Image
    private MultipartFile albumsDefault;

    /**
     * 商品图文详情
     * <p>
     * 当前平台商品尽支持图片描述
     */
    @FormViewField(label = "商品图文介绍", type = InputFiledType.ALBUM)
    private List<String> details = Lists.newArrayList();

    @Image
    private MultipartFile detailsDefault;
}
