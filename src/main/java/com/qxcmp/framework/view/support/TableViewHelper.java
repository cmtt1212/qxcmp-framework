package com.qxcmp.framework.view.support;

import com.google.common.base.CaseFormat;
import com.qxcmp.framework.view.annotation.TableView;
import com.qxcmp.framework.view.annotation.TableViewAction;
import com.qxcmp.framework.view.annotation.TableViewField;
import com.qxcmp.framework.view.table.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link TableViewEntity} 生成工具类
 *
 * @author aaric
 * @see TableViewEntity
 */
@Component
@RequiredArgsConstructor
public class TableViewHelper {

    private static final String DEFAULT_ENTITY_INDEX = "id";
    private static final String DEFAULT_SEARCH_PARA = "content";
    private static final String DEFAULT_SORT_PARA = "sort";
    private static final String DEFAULT_ACTION_TITLE = "操作";
    private static final String DEFAULT_ACTION_SEARCH = "搜索";
    private static final String DEFAULT_ACTION_CREATE = "新建";
    private static final String DEFAULT_ACTION_CREATE_SUFFIX = "new";
    private static final String DEFAULT_ACTION_READ = "查看";
    private static final String DEFAULT_ACTION_UPDATE = "编辑";
    private static final String DEFAULT_ACTION_UPDATE_SUFFIX = "/edit";
    private static final String DEFAULT_ACTION_DELETE = "删除";
    private static final String DEFAULT_ACTION_DELETE_SUFFIX = "/delete";
    private static final String DEFAULT_ACTION_SORT = "排序";

    private final PaginationHelper paginationHelper;

    public <T> TableViewEntity<T> next(Class<T> tClass, Page<T> tPage) {
        return next("", tClass, tPage);
    }

    /**
     * 生成 {@link TableViewEntity}
     *
     * @param tableName 表格标识名称
     * @param tClass    元数据存放类
     * @param tPage     表格数据分页信息
     * @param <T>       表格集合对象类型
     *
     * @return 解析后的 {@link TableViewEntity}
     */
    public <T> TableViewEntity<T> next(String tableName, Class<T> tClass, Page<T> tPage) {
        return next(tableName, tClass, tPage, stringStringMap -> {
        });
    }

    /**
     * 生成 {@link TableViewEntity}
     *
     * @param tableName                表格标识名称
     * @param tClass                   元数据存放类
     * @param tPage                    表格数据分页信息
     * @param paginationQueryParameter 表格分页组件额外查询参数
     * @param <T>                      表格集合对象类型
     *
     * @return 解析后的 {@link TableViewEntity}
     */
    public <T> TableViewEntity<T> next(String tableName, Class<T> tClass, Page<T> tPage, Consumer<Map<String, String>> paginationQueryParameter) {
        TableViewEntity<T> tableViewEntity = new TableViewEntity<>();

        String tableViewName = StringUtils.isEmpty(tableName) ? "" : tableName;
        String tableViewEntityClassName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, tClass.getSimpleName());

        /*
        * 获取实体表格视图注解
        * */
        TableView tableView = getTableView(tClass, tableViewName);
        checkNotNull(tableView, String.format("No TableView definition in %s", tClass.getName()));

        /*
        * 设置表格视图对象
        * 1. 设置基本配置
        * 2. 设置内容和分页
        * 3. 设置表头和字段
        *
        * 顺序不能改变
        * */
        configTableView(tableViewEntity, tableView, tableViewName, tableViewEntityClassName);

        tableViewEntity.setPagination(paginationHelper.next(tPage, tableViewEntity.getActionUrlPrefix(), paginationQueryParameter));
        tableViewEntity.setItems(tPage.getContent());

        configTableViewField(tableViewEntity, tClass, tableViewName, tableViewEntityClassName);

        return tableViewEntity;
    }

    private <T> TableView getTableView(Class<T> tClass, String tableViewName) {
        for (TableView tableView : tClass.getDeclaredAnnotationsByType(TableView.class)) {
            if (tableView.name().equals(tableViewName)) {
                return tableView;
            }
        }

        return null;
    }

    private <T> void configTableView(TableViewEntity<T> tableViewEntity, TableView tableView, String tableViewName, String tableViewEntityClassName) {

        /*
        * 设置表格标题
        * 1. 如果注解有值，则使用注解的值
        * 2. 如果表格标识名称不为空，则用默认值加上表格标识名称
        * 3. 否则使用默认值
        * */
        if (StringUtils.isNotEmpty(tableView.caption())) {
            tableViewEntity.setCaption(tableView.caption());
        } else {
            tableViewEntity.setCaption("未知表格");
        }

        /*
        * 设置表格实体索引
        * 1. 如果注解有值，则使用注解的值
        * 2. 否则使用默认值
        * */
        if (StringUtils.isNotEmpty(tableView.entityIndex())) {
            tableViewEntity.setEntityIndex(tableView.entityIndex());
        } else {
            tableViewEntity.setEntityIndex(DEFAULT_ENTITY_INDEX);
        }

        /*
        * 设置表格实体URL前缀
        * 1. 如果注解有值，则使用注解的值
        * 2. 否则使用默认值
        * */
        if (StringUtils.isNotEmpty(tableView.actionUrlPrefix())) {
            tableViewEntity.setActionUrlPrefix(tableView.actionUrlPrefix());
        } else {
            tableViewEntity.setActionUrlPrefix(String.format("/%s/", tableViewEntityClassName));
        }

        tableViewEntity.setDisableAction(tableView.disableAction());

        /*
        * 设置表格操作列文本
         * 1. 如果注解有值，则使用注解的值
         * 2. 否则使用默认值
        * */
        if (StringUtils.isNotEmpty(tableView.actionColumnTitle())) {
            tableViewEntity.setActionColumnTitle(tableView.actionColumnTitle());
        } else {
            tableViewEntity.setActionColumnTitle(DEFAULT_ACTION_TITLE);
        }

        createTableViewSearchAction(tableViewEntity, tableView);

        createTableViewSortAction(tableViewEntity, tableView);

        createTableViewCreateAction(tableViewEntity, tableView);

        createTableViewFindAction(tableViewEntity, tableView);

        createTableViewUpdateAction(tableViewEntity, tableView);

        createTableViewRemoveAction(tableViewEntity, tableView);

        createTableViewCustomAction(tableViewEntity, tableView, tableViewEntityClassName);
    }

    private <T> void createTableViewSearchAction(TableViewEntity<T> tableViewEntity, TableView tableView) {
        TableViewQueryParaActionEntity searchAction = new TableViewQueryParaActionEntity();
        tableViewEntity.setSearchAction(searchAction);

        if (StringUtils.isNotEmpty(tableView.searchAction().title())) {
            searchAction.setTitle(tableView.searchAction().title());
        } else {
            searchAction.setTitle(DEFAULT_ACTION_SEARCH);
        }

        searchAction.setDisabled(tableView.searchAction().disabled());
        searchAction.setForm(true);
        searchAction.setDisableUrlEntityIndex(true);
        searchAction.setFormMethod(HttpMethod.GET);

        if (StringUtils.isNotEmpty(tableView.searchAction().queryPara())) {
            searchAction.setQueryParameter(searchAction.getQueryParameter());
        } else {
            searchAction.setQueryParameter(DEFAULT_SEARCH_PARA);
        }

        if (StringUtils.isNotEmpty(tableView.searchAction().urlEntityIndex())) {
            searchAction.setUrlEntityIndex(tableView.searchAction().urlEntityIndex());
        } else {
            searchAction.setUrlEntityIndex(tableViewEntity.getEntityIndex());
        }

        if (StringUtils.isNotEmpty(tableView.searchAction().urlPrefix())) {
            searchAction.setUrlPrefix(tableView.searchAction().urlPrefix());
        } else {
            searchAction.setUrlPrefix(tableViewEntity.getActionUrlPrefix());
        }

        searchAction.setUrlSuffix(tableView.searchAction().urlSuffix());
        searchAction.setShowDialog(false);
    }

    private <T> void createTableViewSortAction(TableViewEntity<T> tableViewEntity, TableView tableView) {
        TableViewSortActionEntity sortAction = new TableViewSortActionEntity();
        tableViewEntity.setSortAction(sortAction);

        if (StringUtils.isNotEmpty(tableView.sortAction().title())) {
            sortAction.setTitle(tableView.sortAction().title());
        } else {
            sortAction.setTitle(DEFAULT_ACTION_SORT);
        }

        sortAction.setDisabled(tableView.sortAction().disabled());
        sortAction.setForm(false);
        sortAction.setDisableUrlEntityIndex(true);
        sortAction.setFormMethod(HttpMethod.GET);
        sortAction.setShowDialog(false);

        if (StringUtils.isNotEmpty(tableView.sortAction().urlEntityIndex())) {
            sortAction.setUrlEntityIndex(tableView.sortAction().urlEntityIndex());
        } else {
            sortAction.setUrlEntityIndex(tableViewEntity.getEntityIndex());
        }

        if (StringUtils.isNotEmpty(tableView.sortAction().urlPrefix())) {
            sortAction.setUrlPrefix(tableView.sortAction().urlPrefix());
        } else {
            sortAction.setUrlPrefix(tableViewEntity.getActionUrlPrefix());
        }

        if (StringUtils.isNotEmpty(tableView.sortAction().queryPara())) {
            sortAction.setQueryParameter(sortAction.getQueryParameter());
        } else {
            sortAction.setQueryParameter(DEFAULT_SORT_PARA);
        }

        sortAction.setUrlSuffix(tableView.sortAction().urlSuffix());
    }

    private <T> void createTableViewCreateAction(TableViewEntity<T> tableViewEntity, TableView tableView) {

        if (tableView.createAction().disabled()) {
            return;
        }

        TableViewActionEntity createAction = new TableViewActionEntity();
        tableViewEntity.getTableActions().add(createAction);

        if (StringUtils.isNotEmpty(tableView.createAction().title())) {
            createAction.setTitle(tableView.createAction().title());
        } else {
            createAction.setTitle(DEFAULT_ACTION_CREATE);
        }

        createAction.setDisabled(tableView.createAction().disabled());
        createAction.setForm(false);
        createAction.setDisableUrlEntityIndex(true);
        createAction.setFormMethod(tableView.createAction().formMethod());
        createAction.setShowDialog(false);

        if (StringUtils.isNotEmpty(tableView.createAction().urlTarget()) && tableView.createAction().urlTarget().equalsIgnoreCase("_blank")) {
            createAction.setUrlTarget("_blank");
        } else {
            createAction.setUrlTarget("_self");
        }

        if (StringUtils.isNotEmpty(tableView.createAction().urlEntityIndex())) {
            createAction.setUrlEntityIndex(tableView.createAction().urlEntityIndex());
        } else {
            createAction.setUrlEntityIndex(tableViewEntity.getEntityIndex());
        }

        if (StringUtils.isNotEmpty(tableView.createAction().urlPrefix())) {
            createAction.setUrlPrefix(tableView.createAction().urlPrefix());
        } else {
            createAction.setUrlPrefix(tableViewEntity.getActionUrlPrefix());
        }

        if (StringUtils.isNotEmpty(tableView.createAction().urlSuffix())) {
            createAction.setUrlSuffix(tableView.createAction().urlSuffix());
        } else {
            createAction.setUrlSuffix(DEFAULT_ACTION_CREATE_SUFFIX);
        }

    }

    private <T> void createTableViewFindAction(TableViewEntity<T> tableViewEntity, TableView tableView) {

        TableViewActionEntity findAction = new TableViewActionEntity();
        tableViewEntity.getRowActions().add(findAction);

        if (StringUtils.isNotEmpty(tableView.findAction().title())) {
            findAction.setTitle(tableView.findAction().title());
        } else {
            findAction.setTitle(DEFAULT_ACTION_READ);
        }

        findAction.setDisabled(tableView.findAction().disabled());
        findAction.setForm(false);
        findAction.setDisableUrlEntityIndex(false);
        findAction.setFormMethod(tableView.findAction().formMethod());
        findAction.setShowDialog(false);

        if (StringUtils.isNotEmpty(tableView.findAction().urlTarget()) && tableView.findAction().urlTarget().equalsIgnoreCase("_blank")) {
            findAction.setUrlTarget("_blank");
        } else {
            findAction.setUrlTarget("_self");
        }

        if (StringUtils.isNotEmpty(tableView.findAction().urlEntityIndex())) {
            findAction.setUrlEntityIndex(tableView.findAction().urlEntityIndex());
        } else {
            findAction.setUrlEntityIndex(tableViewEntity.getEntityIndex());
        }

        if (StringUtils.isNotEmpty(tableView.findAction().urlPrefix())) {
            findAction.setUrlPrefix(tableView.findAction().urlPrefix());
        } else {
            findAction.setUrlPrefix(tableViewEntity.getActionUrlPrefix());
        }

        findAction.setUrlSuffix(tableView.findAction().urlSuffix());
    }

    private <T> void createTableViewUpdateAction(TableViewEntity<T> tableViewEntity, TableView tableView) {
        TableViewActionEntity updateAction = new TableViewActionEntity();
        tableViewEntity.getRowActions().add(updateAction);

        if (StringUtils.isNotEmpty(tableView.updateAction().title())) {
            updateAction.setTitle(tableView.updateAction().title());
        } else {
            updateAction.setTitle(DEFAULT_ACTION_UPDATE);
        }

        updateAction.setDisabled(tableView.updateAction().disabled());
        updateAction.setForm(false);
        updateAction.setDisableUrlEntityIndex(false);
        updateAction.setFormMethod(tableView.updateAction().formMethod());
        updateAction.setShowDialog(false);

        if (StringUtils.isNotEmpty(tableView.updateAction().urlTarget()) && tableView.updateAction().urlTarget().equalsIgnoreCase("_blank")) {
            updateAction.setUrlTarget("_blank");
        } else {
            updateAction.setUrlTarget("_self");
        }

        if (StringUtils.isNotEmpty(tableView.updateAction().urlEntityIndex())) {
            updateAction.setUrlEntityIndex(tableView.updateAction().urlEntityIndex());
        } else {
            updateAction.setUrlEntityIndex(tableViewEntity.getEntityIndex());
        }

        if (StringUtils.isNotEmpty(tableView.updateAction().urlPrefix())) {
            updateAction.setUrlPrefix(tableView.updateAction().urlPrefix());
        } else {
            updateAction.setUrlPrefix(tableViewEntity.getActionUrlPrefix());
        }

        if (StringUtils.isNotEmpty(tableView.updateAction().urlSuffix())) {
            updateAction.setUrlSuffix(tableView.updateAction().urlSuffix());
        } else {
            updateAction.setUrlSuffix(DEFAULT_ACTION_UPDATE_SUFFIX);
        }
    }

    private <T> void createTableViewRemoveAction(TableViewEntity<T> tableViewEntity, TableView tableView) {
        TableViewActionEntity removeAction = new TableViewActionEntity();
        tableViewEntity.getRowActions().add(removeAction);

        if (StringUtils.isNotEmpty(tableView.removeAction().title())) {
            removeAction.setTitle(tableView.removeAction().title());
        } else {
            removeAction.setTitle(DEFAULT_ACTION_DELETE);
        }

        removeAction.setDisabled(tableView.removeAction().disabled());
        removeAction.setForm(true);
        removeAction.setDisableUrlEntityIndex(false);
        removeAction.setFormMethod(tableView.removeAction().formMethod());
        removeAction.setShowDialog(tableView.removeAction().showDialog());
        removeAction.setDialogTitle(tableView.removeAction().dialogTitle());
        removeAction.setDialogDescription(tableView.removeAction().dialogDescription());

        if (StringUtils.isNotEmpty(tableView.removeAction().urlEntityIndex())) {
            removeAction.setUrlEntityIndex(tableView.removeAction().urlEntityIndex());
        } else {
            removeAction.setUrlEntityIndex(tableViewEntity.getEntityIndex());
        }

        if (StringUtils.isNotEmpty(tableView.removeAction().urlPrefix())) {
            removeAction.setUrlPrefix(tableView.removeAction().urlPrefix());
        } else {
            removeAction.setUrlPrefix(tableViewEntity.getActionUrlPrefix());
        }

        if (StringUtils.isNotEmpty(tableView.removeAction().urlSuffix())) {
            removeAction.setUrlSuffix(tableView.removeAction().urlSuffix());
        } else {
            removeAction.setUrlSuffix(DEFAULT_ACTION_DELETE_SUFFIX);
        }
    }

    private <T> void createTableViewCustomAction(TableViewEntity<T> tableViewEntity, TableView tableView, String tableViewEntityClassName) {

        for (TableViewAction tableViewAction : tableView.customActions()) {

            TableViewActionEntity actionEntity = new TableViewActionEntity();

            if (StringUtils.isNotEmpty(tableViewAction.title())) {
                actionEntity.setTitle(tableViewAction.title());
            }

            if (StringUtils.isNotEmpty(tableViewAction.urlEntityIndex())) {
                actionEntity.setUrlEntityIndex(tableViewAction.urlEntityIndex());
            } else {
                actionEntity.setUrlEntityIndex(tableViewEntity.getEntityIndex());
            }

            if (StringUtils.isNotEmpty(tableViewAction.urlPrefix())) {
                actionEntity.setUrlPrefix(tableViewAction.urlPrefix());
            } else {
                actionEntity.setUrlPrefix(tableViewEntity.getActionUrlPrefix());
            }

            actionEntity.setDisabled(tableViewAction.disabled());
            actionEntity.setForm(tableViewAction.isForm());
            actionEntity.setDisableUrlEntityIndex(tableViewAction.disableUrlEntityIndex());
            actionEntity.setFormMethod(tableViewAction.formMethod());
            actionEntity.setUrlSuffix(tableViewAction.urlSuffix());
            actionEntity.setShowDialog(tableViewAction.showDialog());
            actionEntity.setDialogTitle(tableViewAction.dialogTitle());
            actionEntity.setDialogDescription(tableViewAction.dialogDescription());

            if (StringUtils.isNotEmpty(tableViewAction.urlTarget()) && tableViewAction.urlTarget().equalsIgnoreCase("_blank")) {
                actionEntity.setUrlTarget("_blank");
            } else {
                actionEntity.setUrlTarget("_self");
            }

            switch (tableViewAction.type()) {
                case TABLE:
                    tableViewEntity.getTableActions().add(actionEntity);
                    break;
                case ROW:
                    tableViewEntity.getRowActions().add(actionEntity);
                    break;
                default:
                    throw new RuntimeException("Invalid action type");
            }
        }
    }

    private <T> void configTableViewField(TableViewEntity<T> tableViewEntity, Class<T> tClass, String tableViewName, String tableViewEntityClassName) {
        List<TableViewHeadEntity> tableViewHeadEntities = new ArrayList<>();
        List<TableViewFieldEntity> tableViewFieldEntities = new ArrayList<>();

        for (Field field : tClass.getDeclaredFields()) {
            for (TableViewField tableViewField : field.getDeclaredAnnotationsByType(TableViewField.class)) {
                if (StringUtils.isEmpty(tableViewField.name()) || tableViewField.name().equals(tableViewName)) {
                    String fieldName = field.getName();

                    TableViewHeadEntity tableViewHeadEntity = new TableViewHeadEntity();
                    TableViewFieldEntity tableViewFieldEntity = new TableViewFieldEntity();

                    tableViewHeadEntity.setField(fieldName);

                    if (StringUtils.isNotEmpty(tableViewField.title())) {
                        tableViewHeadEntity.setTitle(tableViewField.title());
                    } else {
                        tableViewHeadEntity.setTitle(String.format("table.%s.head.%s.title", tableViewEntityClassName, fieldName));
                    }

                    if (StringUtils.isNotEmpty(tableViewField.description())) {
                        tableViewHeadEntity.setDescription(tableViewField.description());
                    } else {
                        tableViewHeadEntity.setDescription(String.format("table.%s.head.%s.description", tableViewEntityClassName, fieldName));
                    }

                    if (tableViewEntity.getSortAction().isDisabled()) {
                        tableViewHeadEntity.setDisableSort(true);
                    } else {
                        tableViewHeadEntity.setDisableSort(tableViewField.disableSort());
                    }

                    tableViewHeadEntity.setDirection(tableViewField.sortDirection());

                    String sortUrlPrefix = tableViewField.sortUrlPrefix();

                    if (StringUtils.isEmpty(sortUrlPrefix)) {
                        sortUrlPrefix = tableViewEntity.getActionUrlPrefix();
                    }

                    String sortUrlQueryPara = tableViewField.sortUrlQueryPara();

                    if (StringUtils.isEmpty(tableViewField.sortUrlQueryPara())) {
                        sortUrlQueryPara = DEFAULT_SORT_PARA;
                    }

                    tableViewHeadEntity.setSortUrl(String.format("%s%s?%s=%s,%s", sortUrlPrefix, tableViewField.sortUrlSuffix(), sortUrlQueryPara, fieldName, tableViewHeadEntity.getDirection()));
                    tableViewHeadEntity.setOrder(tableViewField.order());

                    tableViewFieldEntity.setField(fieldName);
                    tableViewFieldEntity.setFieldSuffix(tableViewField.fieldSuffix());
                    tableViewFieldEntity.setI18n(tableViewField.useI18n());
                    tableViewFieldEntity.setCollection(tableViewField.isCollection());
                    tableViewFieldEntity.setImage(tableViewField.isImage());
                    tableViewFieldEntity.setMaxCollectionCount(tableViewField.maxCollectionCount());

                    if (StringUtils.isNotEmpty(tableViewField.collectionEntityIndex())) {
                        tableViewFieldEntity.setCollectionEntityIndex(tableViewField.collectionEntityIndex());
                    } else {
                        tableViewFieldEntity.setCollectionEntityIndex(DEFAULT_ENTITY_INDEX);
                    }

                    if (StringUtils.isNotEmpty(tableViewField.urlTarget()) && tableViewField.urlTarget().equalsIgnoreCase("_blank")) {
                        tableViewFieldEntity.setUrlTarget("_blank");
                    } else {
                        tableViewFieldEntity.setUrlTarget("_self");
                    }

                    tableViewFieldEntity.setUrlEnabled(tableViewField.urlEnabled());

                    if (StringUtils.isNotEmpty(tableViewField.urlEntityIndex())) {
                        tableViewFieldEntity.setUrlEntityIndex(tableViewField.urlEntityIndex());
                    } else {
                        tableViewFieldEntity.setUrlEntityIndex(DEFAULT_ENTITY_INDEX);
                    }

                    if (StringUtils.isNotEmpty(tableViewField.urlPrefix())) {
                        tableViewFieldEntity.setUrlPrefix(tableViewField.urlPrefix());
                    } else {
                        tableViewFieldEntity.setUrlPrefix(tableViewEntity.getActionUrlPrefix());
                    }

                    tableViewFieldEntity.setUrlSuffix(tableViewField.urlSuffix());
                    tableViewFieldEntity.setOrder(tableViewField.order());

                    tableViewHeadEntities.add(tableViewHeadEntity);
                    tableViewFieldEntities.add(tableViewFieldEntity);
                    break;
                }
            }
        }

        tableViewHeadEntities.sort(Comparator.comparing(TableViewHeadEntity::getOrder));
        tableViewFieldEntities.sort(Comparator.comparing(TableViewFieldEntity::getOrder));

        tableViewEntity.setHeads(tableViewHeadEntities);
        tableViewEntity.setFields(tableViewFieldEntities);
    }
}
