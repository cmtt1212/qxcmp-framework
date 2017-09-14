package com.qxcmp.framework.view.nav;

import com.qxcmp.framework.user.User;
import com.qxcmp.framework.view.component.AnchorTarget;

import java.util.List;

/**
 * 导航栏服务
 *
 * @author aaric
 */
public interface NavigationService {

    /**
     * 获取平台中指定类型指定名称的导航栏
     * <p>
     * 如果导航栏不存在则根据顺序创建该导航栏
     *
     * @param type  导航栏类型
     * @param name  导航栏名称
     * @param order 导航栏顺序
     *
     * @return 导航栏
     */
    Navigation get(Navigation.Type type, String name, int order);

    /**
     * 获取指定类型排序后的导航栏
     *
     * @param type 导航栏类型
     *
     * @return 排序后的导航栏
     */
    List<Navigation> findByType(Navigation.Type type);

    /**
     * 根据用户权限过滤导航栏
     *
     * @param navigationList 要过滤的导航栏
     * @param user           用户
     *
     * @return 过滤后的导航栏
     */
    List<Navigation> filterByUser(List<Navigation> navigationList, User user);

    /**
     * 向导航栏中添加项目，如果项目不存在则创建以后添加
     *
     * @param navigation 要添加的导航栏
     * @param title      项目名称
     * @param image      项目图片
     * @param icon       项目图标
     * @param link       项目链接
     * @param target     链接打开方式
     * @param order      项目顺序
     * @param privileges 项目显示所需权限，不填则默认显示
     */
    void add(Navigation navigation, String title, String image, String icon, String link, AnchorTarget target, int order, String... privileges);
}
