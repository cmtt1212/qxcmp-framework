package com.qxcmp.framework.view;

import com.google.common.collect.Lists;
import com.qxcmp.framework.core.entity.EntityService;
import com.qxcmp.framework.config.SiteService;
import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.view.annotation.ListViewField;
import com.qxcmp.framework.view.annotation.TableViewField;
import com.qxcmp.framework.view.component.*;
import com.qxcmp.framework.view.dictionary.DictionaryView;
import com.qxcmp.framework.view.list.ListView;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.view.result.Result;
import com.qxcmp.framework.view.result.ResultNavigation;
import com.qxcmp.framework.view.support.*;
import com.qxcmp.framework.view.table.TableViewEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 模型和视图生成器
 *
 * @author aaric
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ModelAndViewBuilder {

    public static final String FORM_OBJECT = "object";

    private ApplicationContext applicationContext;

    private UserService userService;

    private SiteService siteService;

    private DictionaryViewHelper dictionaryViewHelper;

    private FormViewHelper formViewHelper;

    private ListViewHelper listViewHelper;

    private TableViewHelper tableViewHelper;


    private HttpServletRequest request;

    private HttpServletResponse response;

    private ModelAndView modelAndView = new ModelAndView();

    private List<Fragment> fragments = Lists.newArrayList();

    public ModelAndViewBuilder(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public ModelAndViewBuilder setViewName(String viewName) {
        modelAndView.setViewName(viewName);
        return this;
    }

    /**
     * 设置页面标题
     *
     * @param title 页面标题
     *
     * @return 生成器本身
     */
    public ModelAndViewBuilder setTitle(String title) {
        addObject("title", title + "_" + siteService.getTitle());
        return this;
    }

    public ModelAndViewBuilder setTitle(String title, String subTitle) {
        addObject("title", title + "_" + subTitle + "_" + siteService.getTitle());
        return this;
    }

    /**
     * 添加HTML基本元素
     *
     * @param element 元素本身
     *
     * @return 生成器本身
     */
    public ModelAndViewBuilder addElement(Element element) {

        int elementCount = Objects.nonNull(getObject("elementCount")) ? (int) getObject("elementCount") : 0;
        elementCount++;
        addObject("elementCount", elementCount);

        String boxModalName = "elementCount" + elementCount;
        addObject(boxModalName, element);
        addFragment("qxcmp/element", String.format("basic(${%s})", boxModalName));

        return this;
    }

    public ModelAndViewBuilder addElement(ElementType type, String content) {
        return addElement(type, ElementAlign.CENTER, Color.BLACK, content);
    }

    public ModelAndViewBuilder addElement(ElementType type, ElementAlign align, String content) {
        return addElement(type, align, Color.BLACK, content);
    }

    public ModelAndViewBuilder addElement(ElementType type, ElementAlign align, Color color, String content) {
        return addElement(Element.builder().type(type).align(align).color(color).content(content).build());
    }

    /**
     * 添加纯HTML内容
     *
     * @param html HTML内容
     *
     * @return 生成器本身
     */
    public ModelAndViewBuilder addHtml(String html) {

        int elementCount = Objects.nonNull(getObject("htmlCount")) ? (int) getObject("htmlCount") : 0;
        elementCount++;
        addObject("htmlCount", elementCount);

        String boxModalName = "htmlCount" + elementCount;
        addObject(boxModalName, html);
        addFragment("qxcmp/common", String.format("text(${%s})", boxModalName));

        return this;
    }

    public ModelAndViewBuilder setResult(String title) {
        return setResult(title, "", "");
    }

    public ModelAndViewBuilder setResult(String title, String description) {
        return setResult(title, "", description);
    }

    /**
     * 设置页面结果视图。顺序相关且唯一
     *
     * @param title       标题
     * @param subTitle    子标题
     * @param description 描述
     *
     * @return 生成器本身
     */
    public ModelAndViewBuilder setResult(String title, String subTitle, String description) {
        addObject(Result.builder().title(title).subTitle(subTitle).description(description).build());
        addFragment("qxcmp/result", "basic");
        return this;
    }

    public ModelAndViewBuilder setResultNavigation(ResultNavigation resultNavigation) {
        modelAndView.addObject(resultNavigation);
        addFragment("qxcmp/result", "navigation");
        return this;
    }

    public ModelAndViewBuilder setResultNavigation(Consumer<ResultNavigation.ResultNavigationBuilder> consumer) {
        ResultNavigation.ResultNavigationBuilder resultNavigationBuilder = ResultNavigation.builder();
        consumer.accept(resultNavigationBuilder);
        return setResultNavigation(resultNavigationBuilder.build());
    }

    /**
     * 设置结果导航视图。顺序相关且唯一
     *
     * @param links 名称-链接 组合对
     *
     * @return 生成器本身
     */
    public ModelAndViewBuilder setResultNavigation(String... links) {
        checkArgument(links.length % 2 == 0);

        ResultNavigation.ResultNavigationBuilder resultNavigationBuilder = ResultNavigation.builder();

        for (int i = 0; i < links.length; i += 2) {
            resultNavigationBuilder.link(Link.builder().title(links[i]).href(links[i + 1]).build());
        }

        return setResultNavigation(resultNavigationBuilder.build());
    }

    /**
     * 设置字典视图。顺序相关，可以重复添加
     *
     * @param dictionaryView 字典视图
     *
     * @return 生成器本身
     */
    public ModelAndViewBuilder addDictionaryView(DictionaryView dictionaryView) {

        int dictionaryCount = Objects.nonNull(getObject("dictionaryCount")) ? (int) getObject("dictionaryCount") : 0;
        dictionaryCount++;
        addObject("dictionaryCount", dictionaryCount);

        String boxModalName = "dictionaryCount" + dictionaryCount;
        addObject(boxModalName, dictionaryView);
        addFragment("qxcmp/dictionary", String.format("basic(${%s})", boxModalName));
        return this;
    }

    public ModelAndViewBuilder addDictionaryView(Consumer<DictionaryView.DictionaryViewBuilder> builderConsumer) {
        DictionaryView.DictionaryViewBuilder dictionaryViewBuilder = DictionaryView.builder();
        builderConsumer.accept(dictionaryViewBuilder);
        return addDictionaryView(dictionaryViewBuilder.build());
    }

    public ModelAndViewBuilder addDictionaryView(String name, Object object) {
        return addDictionaryView(dictionaryViewHelper.next(name, object));
    }

    public ModelAndViewBuilder addDictionaryView(Object object) {
        return addDictionaryView("", object);
    }

    /**
     * 设置表单视图。顺序相关且唯一
     *
     * @param form        表单对象
     * @param selectItems 下拉框选项
     *
     * @return 生成器本身
     */
    public ModelAndViewBuilder setFormView(Object form, List... selectItems) {
        modelAndView.addObject(formViewHelper.next(form, selectItems)).addObject("object", form);
        addFragment("qxcmp/form", "basic");
        return this;
    }

    /**
     * 设置列表视图。顺序相关且唯一
     *
     * @param listView 列表视图
     *
     * @return 生成器本身
     */
    public ModelAndViewBuilder addListView(ListView listView) {

        int listViewCount = Objects.nonNull(getObject("listViewCount")) ? (int) getObject("listViewCount") : 0;
        listViewCount++;
        addObject("listViewCount", listViewCount);

        String boxModalName = "listViewCount" + listViewCount;
        addObject(boxModalName, listView);
        addFragment("qxcmp/list", String.format("basic(${%s})", boxModalName));
        return this;
    }

    public ModelAndViewBuilder addListView(Consumer<ListView.ListViewBuilder> builderConsumer) {
        ListView.ListViewBuilder listViewBuilder = ListView.builder();
        builderConsumer.accept(listViewBuilder);
        return addListView(listViewBuilder.build());
    }

    public ModelAndViewBuilder addListView(Pageable pageable, EntityService entityService, HttpServletRequest request) {
        return addListView("", pageable, entityService, request);
    }

    @SuppressWarnings("unchecked")
    public ModelAndViewBuilder addListView(String name, Pageable pageable, EntityService entityService, HttpServletRequest request) {

        List<Field> fields = Lists.newArrayList();

        for (Field field : entityService.type().getDeclaredFields()) {
            ListViewField tableViewField = field.getAnnotation(ListViewField.class);

            if (Objects.nonNull(tableViewField)) {
                fields.add(field);
            }
        }

        String content = getSearchContent(request);

        return addListView(listViewHelper.nextList(name, entityService.type(), entityService.search(content, fields, pageable), stringStringMap -> stringStringMap.put("content", content)));
    }

    /**
     * 设置表格视图。顺序相关且唯一
     *
     * @param tableViewEntity 表格视图
     *
     * @return 生成器本身
     */
    public ModelAndViewBuilder setTableView(TableViewEntity tableViewEntity) {
        addObject(tableViewEntity);
        addFragment("qxcmp/table", "basic");
        return this;
    }

    public ModelAndViewBuilder setTableView(Pageable pageable, EntityService entityService) {
        return setTableView("", pageable, entityService);
    }

    @SuppressWarnings("unchecked")
    public ModelAndViewBuilder setTableView(String name, Pageable pageable, EntityService entityService) {

        List<Field> fields = Lists.newArrayList();

        for (Field field : entityService.type().getDeclaredFields()) {
            TableViewField tableViewField = field.getAnnotation(TableViewField.class);

            if (Objects.nonNull(tableViewField) && !tableViewField.disableSearch()) {
                fields.add(field);
            }
        }

        String content = getSearchContent(request);

        return setTableView(tableViewHelper.next(name, entityService.type(), entityService.search(content, fields, pageable), stringStringMap -> stringStringMap.put("content", content)));
    }

    /**
     * 添加导航栏视图。顺序无关，每个类型唯一
     *
     * @param activateItemName 激活的导航项目名称
     * @param type             导航栏类型
     * @param name             导航栏名称
     *
     * @return 生成器本身
     */
    public ModelAndViewBuilder addNavigation(String activateItemName, Navigation.Type type, String name) {


        return this;
    }

    public ModelAndViewBuilder addNavigation(Navigation.Type type, String name) {
        return addNavigation("", type, name);
    }

    /**
     * 添加分隔符。顺序相关，可重复添加
     *
     * @return 生成器本身
     */
    public ModelAndViewBuilder addDivider() {
        return addDivider(true);
    }

    public ModelAndViewBuilder addDivider(boolean showDivider) {
        addFragment("qxcmp/common", String.format("divider(${%s})", showDivider));
        return this;
    }

    /**
     * 向模型添加数据
     *
     * @param key    键
     * @param object 值
     *
     * @return 生成器本身
     */
    public ModelAndViewBuilder addObject(String key, Object object) {
        modelAndView.addObject(key, object);
        return this;
    }

    public ModelAndViewBuilder addObject(Object object) {
        modelAndView.addObject(object);
        return this;
    }

    /**
     * 添加自定义模板片段。顺序相关可多次添加
     *
     * @param fragmentFile 片段文件名称
     * @param fragmentName 片段名称
     *
     * @return 生成器本身
     */
    public ModelAndViewBuilder addFragment(String fragmentFile, String fragmentName) {
        fragments.add(new Fragment(fragmentFile, fragmentName));
        return this;
    }

    public Object getObject(String key) {
        return modelAndView.getModel().get(key);
    }

    public ModelAndView build() {
        addObject("fragments", fragments);
        applicationContext.publishEvent(new PostBuilderEvent(request, response, this));
        return modelAndView;
    }

    private String getSearchContent(HttpServletRequest request) {
        return StringUtils.isNotBlank(request.getParameter("content")) ? request.getParameter("content") : "";
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }

    @Autowired
    public void setDictionaryViewHelper(DictionaryViewHelper dictionaryViewHelper) {
        this.dictionaryViewHelper = dictionaryViewHelper;
    }

    @Autowired
    public void setFormViewHelper(FormViewHelper formViewHelper) {
        this.formViewHelper = formViewHelper;
    }

    @Autowired
    public void setListViewHelper(ListViewHelper listViewHelper) {
        this.listViewHelper = listViewHelper;
    }

    @Autowired
    public void setTableViewHelper(TableViewHelper tableViewHelper) {
        this.tableViewHelper = tableViewHelper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
