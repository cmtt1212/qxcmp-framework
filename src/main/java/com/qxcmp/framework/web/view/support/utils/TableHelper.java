package com.qxcmp.framework.web.view.support.utils;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.annotation.table.EntityTable;
import com.qxcmp.framework.web.view.annotation.table.TableField;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.modules.pagination.Pagination;
import com.qxcmp.framework.web.view.modules.table.*;
import com.qxcmp.framework.web.view.support.Alignment;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
@RequiredArgsConstructor
public class TableHelper {

    public <T> com.qxcmp.framework.web.view.modules.table.EntityTable convert(String tableName, Class<T> tClass, Page<T> tPage) {
        checkNotNull(tableName);

        final com.qxcmp.framework.web.view.modules.table.EntityTable table = new com.qxcmp.framework.web.view.modules.table.EntityTable();

        EntityTable entityTable = Arrays.stream(tClass.getDeclaredAnnotationsByType(EntityTable.class)).filter(annotation -> StringUtils.equals(annotation.name(), tableName)).findAny().orElseThrow(() -> new IllegalStateException("No EntityTable definition"));

        configEntityTable(table, entityTable, tClass);

        renderTableContent(table, entityTable, tClass, tPage);

        return table;
    }


    private <T> void configEntityTable(com.qxcmp.framework.web.view.modules.table.EntityTable table, EntityTable entityTable, Class<T> tClass) {

        if (StringUtils.isNotBlank(entityTable.value())) {
            table.setTableHeader(new PageHeader(HeaderType.H2, entityTable.value()).setDividing());
        }

        table.setEntityIndex(entityTable.entityIndex());

        if (StringUtils.isNotBlank(entityTable.action())) {
            table.setAction(entityTable.action());
        } else {
            table.setAction(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, tClass.getSimpleName()));
        }

        if (!StringUtils.endsWith(table.getAction(), "/")) {
            table.setAction(table.getAction() + "/");
        }

        table.setMultiple(entityTable.multiple());
        table.setCelled(entityTable.celled());
        table.setBasic(entityTable.basic());
        table.setVeryBasic(entityTable.veryBasic());
        table.setSingleLine(entityTable.singleLine());
        table.setFixed(entityTable.fixed());
        table.setSelectable(entityTable.selectable());
        table.setStriped(entityTable.striped());
        table.setInverted(entityTable.inverted());
        table.setCollapsing(entityTable.collapsing());
        table.setPadded(entityTable.padded());
        table.setVeryPadded(entityTable.veryPadded());
        table.setCompact(entityTable.compact());
        table.setVeryCompact(entityTable.veryCompact());
        table.setColumnCount(entityTable.columnCount());
        table.setStackDevice(entityTable.stackDevice());
        table.setColor(entityTable.color());
        table.setSize(entityTable.size());

        Arrays.stream(entityTable.tableActions()).forEach(tableAction -> {
            EntityTableAction entityTableAction = new EntityTableAction();

            entityTableAction.setTitle(tableAction.value());
            entityTableAction.setAction(table.getAction() + tableAction.action());
            entityTableAction.setForm(tableAction.isForm());
            entityTableAction.setMethod(tableAction.method());
            entityTableAction.setTarget(tableAction.target());
            entityTableAction.setSupportMultiple(tableAction.supportMultiple());

            table.getTableActions().add(entityTableAction);
        });

        Arrays.stream(entityTable.rowActions()).forEach(rowAction -> {
            EntityTableRowAction entityTableRowAction = new EntityTableRowAction();

            entityTableRowAction.setTitle(rowAction.value());
            entityTableRowAction.setAction(table.getAction() + table.getEntityIndex() + "/" + rowAction.action());
            entityTableRowAction.setForm(rowAction.isForm());
            entityTableRowAction.setMethod(rowAction.method());
            entityTableRowAction.setTarget(rowAction.target());

            table.getRowActions().add(entityTableRowAction);
        });
    }

    private <T> void renderTableContent(com.qxcmp.framework.web.view.modules.table.EntityTable table, EntityTable entityTable, Class<T> tClass, Page<T> tPage) {
        final List<EntityTableField> entityTableFields = Lists.newArrayList();

        for (Field field : tClass.getDeclaredFields()) {
            Arrays.stream(field.getDeclaredAnnotationsByType(TableField.class)).filter(tableField -> StringUtils.isBlank(tableField.name()) || StringUtils.equals(tableField.name(), entityTable.name())).findAny().ifPresent(tableField -> {
                EntityTableField entityTableField = new EntityTableField();

                entityTableField.setField(field.getName());
                entityTableField.setTitle(tableField.value());
                entityTableField.setDescription(tableField.description());
                entityTableField.setOrder(tableField.order());
                entityTableField.setFieldSuffix(tableField.fieldSuffix());
                entityTableField.setEnableUrl(tableField.enableUrl());
                entityTableField.setUrlPrefix(tableField.urlPrefix());
                entityTableField.setUrlEntityIndex(tableField.urlEntityIndex());
                entityTableField.setUrlSuffix(tableField.urlSuffix());

                entityTableFields.add(entityTableField);
            });
        }

        entityTableFields.sort(Comparator.comparingInt(EntityTableField::getOrder));

        renderTableHeader(table, entityTableFields);

        renderTableBody(table, entityTableFields, tPage);

        renderTableFooter(table, entityTableFields, tPage);
    }

    private void renderTableHeader(com.qxcmp.framework.web.view.modules.table.EntityTable table, List<EntityTableField> entityTableFields) {
        final TableHeader tableHeader = new TableHeader();
        final TableRow tableRow = new TableRow();
        entityTableFields.forEach(entityTableField -> {
            final TableHead tableHead = new TableHead();
            tableHead.setContent(entityTableField.getTitle());
            tableRow.addCell(tableHead);
        });
        tableHeader.addRow(tableRow);
        table.setHeader(tableHeader);
    }

    private <T> void renderTableBody(com.qxcmp.framework.web.view.modules.table.EntityTable table, List<EntityTableField> entityTableFields, Page<T> tPage) {
        final TableBody tableBody = new TableBody();

        tPage.getContent().forEach(t -> {
            final TableRow tableRow = new TableRow();

            entityTableFields.forEach(entityTableField -> {
                final TableData tableData = new TableData();

                renderTableCell(tableData, entityTableField, t);

                tableRow.addCell(tableData);
            });

            tableBody.addRow(tableRow);
        });

        table.setBody(tableBody);
    }

    private <T> void renderTableCell(TableData tableData, EntityTableField entityTableField, T t) {
        final BeanWrapperImpl beanWrapper = new BeanWrapperImpl(t);
        tableData.setContent(beanWrapper.getPropertyValue(entityTableField.getField()).toString());
    }

    private <T> void renderTableFooter(com.qxcmp.framework.web.view.modules.table.EntityTable table, List<EntityTableField> entityTableFields, Page<T> tPage) {
        final TableFooter tableFooter = new TableFooter();
        final TableRow tableRow = new TableRow();
        final TableHead tableHead = new TableHead();

        int colSpan = entityTableFields.size();

        if (table.isMultiple()) {
            ++colSpan;
        }

        if (!table.getRowActions().isEmpty()) {
            ++colSpan;
        }

        tableHead.setColSpan(colSpan);
        tableHead.setAlignment(Alignment.CENTER);
        tableHead.setComponent(new Pagination("", tPage.getNumber() + 1, (int) tPage.getTotalElements(), tPage.getSize()).setShowSizeChanger().setShowQuickJumper().setShowTotal());

        tableRow.addCell(tableHead);
        tableFooter.addRow(tableRow);
        table.setFooter(tableFooter);
    }
}
