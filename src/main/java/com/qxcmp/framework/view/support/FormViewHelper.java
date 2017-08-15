package com.qxcmp.framework.view.support;

import com.google.common.base.CaseFormat;
import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.annotation.FormViewListField;
import com.qxcmp.framework.view.form.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link FormViewEntity} 生成工具类
 *
 * @author aaric
 * @see FormViewEntity
 */
@Component
public class FormViewHelper {

    private static final String DEFAULT_SUBMIT_ICON = "save";
    private static final String DEFAULT_CAPTCHA_IMAGE_URL = "/api/captcha/image";
    private static final String DEFAULT_CAPTCHA_PHONE_URL = "/api/captcha/phone";
    private static final String DEFAULT_CANDIDATE_INDEX = "id";

    /**
     * 生成{@link com.qxcmp.framework.view.table.TableViewEntity}
     *
     * @param formObject 表单对象
     * @param candidates 选择框对象集合
     *
     * @return 解析对象后的 {@link com.qxcmp.framework.view.table.TableViewEntity}
     */
    public FormViewEntity next(Object formObject, List... candidates) {
        FormViewEntity formViewEntity = new FormViewEntity();

        String formName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, formObject.getClass().getSimpleName()).replaceAll("-form", "").replaceAll("-", ".");

        FormView formView = formObject.getClass().getAnnotation(FormView.class);
        checkNotNull(formView, "No FormView definition in " + formObject.getClass().getName());

        configFormView(formViewEntity, formView, formName);

        configFormViewField(formViewEntity, formObject, formName, candidates);

        return formViewEntity;
    }

    private void configFormView(FormViewEntity formViewEntity, FormView formView, String formName) {

        if (StringUtils.isNotEmpty(formView.caption())) {
            formViewEntity.setCaption(formView.caption());
        } else {
            formViewEntity.setCaption("未命名表单");
        }

        if (StringUtils.isNotEmpty(formView.description())) {
            formViewEntity.setDescription(formView.description());
        } else {
            formViewEntity.setDescription("暂无描述");
        }

        formViewEntity.setClassName(formView.className());

        if (StringUtils.equals("$SELF", formView.action())) {
            formViewEntity.setAction("");
        } else if (StringUtils.isNotEmpty(formView.action())) {
            formViewEntity.setAction(formView.action());
        } else {
            formViewEntity.setAction("/" + formName.replaceAll("\\.", "/"));
        }

        formViewEntity.setEnctype(formView.enctype());
        formViewEntity.setMethod(formView.method());

        if (StringUtils.isNotEmpty(formView.submitTitle())) {
            formViewEntity.setSubmitTitle(formView.submitTitle());
        } else {
            formViewEntity.setSubmitTitle("提交");
        }

        if (StringUtils.isNotEmpty(formView.submitIcon())) {
            formViewEntity.setSubmitIcon(formView.submitIcon());
        } else {
            formViewEntity.setSubmitIcon(DEFAULT_SUBMIT_ICON);
        }

        formViewEntity.setDisableSubmitIcon(formView.disableSubmitIcon());
        formViewEntity.setDisableSubmitTitle(formView.disableSubmitTitle());
        formViewEntity.setDisableCaption(formView.disableCaption());
        formViewEntity.setShowDialog(formView.showDialog());
        formViewEntity.setDialogTitle(formView.dialogTitle());
        formViewEntity.setDialogDescription(formView.dialogDescription());
    }

    private void configFormViewField(FormViewEntity formViewEntity, Object formObject, String formName, List[] candidates) {

        int candidateIndex = 0;

        for (Field field : formObject.getClass().getDeclaredFields()) {
            FormViewField formViewField = field.getDeclaredAnnotation(FormViewField.class);

            if (Objects.nonNull(formViewField)) {
                String fieldName = field.getName();

                FormViewFieldEntity formViewFieldEntity = calculateFieldEntityType(formViewField);

                formViewFieldEntity.setField(fieldName);
                formViewFieldEntity.setClassName(formViewField.className());

                if (StringUtils.isNotEmpty(formViewField.label())) {
                    formViewFieldEntity.setLabel(formViewField.label());
                } else {
                    formViewFieldEntity.setLabel("标签");
                }

                if (StringUtils.isNotEmpty(formViewField.description())) {
                    formViewFieldEntity.setDescription(formViewField.description());
                } else {
                    formViewFieldEntity.setDescription("暂无描述");
                }

                if (StringUtils.isNotEmpty(formViewField.placeholder())) {
                    formViewFieldEntity.setPlaceholder(formViewField.placeholder());
                } else {
                    formViewFieldEntity.setPlaceholder("请输入");
                }

                formViewFieldEntity.setAccept(formViewField.accept());

                if (StringUtils.isNotEmpty(formViewField.alt())) {
                    formViewFieldEntity.setAlt(formViewField.alt());
                } else {
                    formViewFieldEntity.setAlt(formViewFieldEntity.getLabel());
                }

                formViewFieldEntity.setStep(formViewField.step());
                formViewFieldEntity.setMax(formViewField.max());
                formViewFieldEntity.setMin(formViewField.min());
                formViewFieldEntity.setMaxLength(formViewField.maxLength());

                formViewFieldEntity.setAutoFocus(formViewField.autoFocus());
                formViewFieldEntity.setReadOnly(formViewField.readOnly());
                formViewFieldEntity.setRequired(formViewField.required());
                formViewFieldEntity.setDisabled(formViewField.disabled());
                formViewFieldEntity.setCandidateI18n(formViewField.candidateI18n());

                if (formViewField.consumeCandidate()) {
                    formViewFieldEntity.setCandidates(candidates[candidateIndex]);
                    candidateIndex++;
                } else if (formViewField.enumCandidate()) {
                    formViewFieldEntity.setCandidates(Arrays.asList(formViewField.enumCandidateClass().getEnumConstants()));
                    formViewFieldEntity.setCandidateValueIndex("toString()");
                    formViewFieldEntity.setCandidateTextIndex("toString()");
                }

                if (Collection.class.isAssignableFrom(field.getType()) || TypeUtils.isArrayType(field.getType())) {
                    formViewFieldEntity.setMultiple(true);
                }

                if (StringUtils.isEmpty(formViewFieldEntity.getCandidateValueIndex())) {
                    if (StringUtils.isNotEmpty(formViewField.candidateValueIndex())) {
                        formViewFieldEntity.setCandidateValueIndex(formViewField.candidateValueIndex());
                    } else {
                        formViewFieldEntity.setCandidateValueIndex(DEFAULT_CANDIDATE_INDEX);
                    }
                }

                if (StringUtils.isEmpty(formViewFieldEntity.getCandidateTextIndex())) {
                    if (StringUtils.isNotEmpty(formViewField.candidateTextIndex())) {
                        formViewFieldEntity.setCandidateTextIndex(formViewField.candidateTextIndex());
                    } else {
                        formViewFieldEntity.setCandidateTextIndex(DEFAULT_CANDIDATE_INDEX);
                    }
                }


                formViewFieldEntity.setOrder(formViewField.order());

                if (formViewFieldEntity instanceof SwitchFieldEntity) {
                    SwitchFieldEntity switchFieldEntity = (SwitchFieldEntity) formViewFieldEntity;

                    if (StringUtils.isNotEmpty(formViewField.labelOn())) {
                        switchFieldEntity.setLabelOn(formViewField.labelOn());
                    } else {
                        switchFieldEntity.setLabelOn("启用");
                    }

                    if (StringUtils.isNotEmpty(formViewField.labelOff())) {
                        switchFieldEntity.setLabelOff(formViewField.labelOff());
                    } else {
                        switchFieldEntity.setLabelOff("禁用");
                    }
                } else if (formViewFieldEntity instanceof CaptchaFieldEntity) {
                    CaptchaFieldEntity captchaFieldEntity = (CaptchaFieldEntity) formViewFieldEntity;

                    if (StringUtils.isNotEmpty(formViewField.url())) {
                        captchaFieldEntity.setUrl(formViewField.url());
                    } else {
                        switch (formViewField.captchaType()) {
                            case IMAGE:
                                captchaFieldEntity.setUrl(DEFAULT_CAPTCHA_IMAGE_URL);
                                break;
                            case PHONE:
                                captchaFieldEntity.setUrl(DEFAULT_CAPTCHA_PHONE_URL);
                                break;
                        }
                    }

                    captchaFieldEntity.setCaptchaType(formViewField.captchaType());
                } else if (formViewFieldEntity instanceof TextAreaEntity) {
                    TextAreaEntity textAreaEntity = (TextAreaEntity) formViewFieldEntity;
                    textAreaEntity.setRows(formViewField.rows());
                } else if (formViewFieldEntity instanceof LabelFieldEntity) {
                    formViewFieldEntity.setReadOnly(true);
                } else if (formViewFieldEntity instanceof ListFieldEntity) {
                    ListFieldEntity listFieldEntity = (ListFieldEntity) formViewFieldEntity;
                    FormViewListField formViewListField = formViewField.listFiled();
                    for (String s : formViewListField.fields()) {
                        listFieldEntity.getListField().add(s);
                    }
                    for (String s : formViewListField.labels()) {
                        listFieldEntity.getListFieldLabel().add(s);
                    }

                    listFieldEntity.setRawType(formViewListField.isRawType());
                } else if (formViewFieldEntity instanceof AlbumFieldEntity) {
                    AlbumFieldEntity albumFieldEntity = (AlbumFieldEntity) formViewFieldEntity;

                    if (StringUtils.isNotEmpty(formViewField.albumFieldName())) {
                        albumFieldEntity.setAlbumFieldName(formViewField.albumFieldName());
                    } else {
                        albumFieldEntity.setAlbumFieldName(fieldName + "Default");
                    }
                }

                formViewEntity.getFields().add(formViewFieldEntity);
            }
        }

        formViewEntity.getFields().sort(Comparator.comparing(FormViewFieldEntity::getOrder));
    }

    private FormViewFieldEntity calculateFieldEntityType(FormViewField formViewField) {

        if (formViewField.consumeCandidate() || formViewField.enumCandidate()) {
            return new SelectFieldEntity();
        }

        switch (formViewField.type()) {
            case AUTO:
                throw new IllegalStateException("Unsupported form field type: " + formViewField.type().getName());
            case CAPTCHA:
                return new CaptchaFieldEntity();
            case FILE:
                return new FileFieldEntity();
            case HIDDEN:
                return new HiddenFieldEntity();
            case LABEL:
                return new LabelFieldEntity();
            case NUMBER:
                return new NumberFieldEntity();
            case PASSWORD:
                return new PasswordFieldEntity();
            case SELECT:
                return new SelectFieldEntity();
            case DATE:
                return new DateFieldEntity();
            case TIME:
                return new TimeFieldEntity();
            case DATETIME:
                return new DateTimeFieldEntity();
            case SWITCH:
                return new SwitchFieldEntity();
            case TEXT:
                return new TextFieldEntity();
            case TEXTAREA:
                return new TextAreaEntity();
            case LIST:
                return new ListFieldEntity();
            case ALBUM:
                return new AlbumFieldEntity();
            case HTML:
                return new HtmlFieldEntity();
            case DISTRICT:
                return new DistrictFieldEntity();
        }

        throw new IllegalStateException("Unsupported form field type: " + formViewField.type().getName());
    }
}
