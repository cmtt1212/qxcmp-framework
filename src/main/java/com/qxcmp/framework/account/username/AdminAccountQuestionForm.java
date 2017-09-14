package com.qxcmp.framework.account.username;

import com.qxcmp.framework.view.annotation.FormView;
import com.qxcmp.framework.view.annotation.FormViewField;
import com.qxcmp.framework.view.form.InputFiledType;
import lombok.Data;

/**
 * 用户密保设置表单
 *
 * @author aaric
 */
@FormView(caption = "设置密保")
@Data
public class AdminAccountQuestionForm {

    @FormViewField(label = "密保问题1", type = InputFiledType.SELECT, consumeCandidate = true, candidateTextIndex = "toString()", candidateValueIndex = "toString()")
    private String question1;

    @FormViewField(label = "我的答案", type = InputFiledType.TEXT)
    private String answer1;

    @FormViewField(label = "密保问题2", type = InputFiledType.SELECT, consumeCandidate = true, candidateTextIndex = "toString()", candidateValueIndex = "toString()")
    private String question2;

    @FormViewField(label = "我的答案", type = InputFiledType.TEXT)
    private String answer2;

    @FormViewField(label = "密保问题3", type = InputFiledType.SELECT, consumeCandidate = true, candidateTextIndex = "toString()", candidateValueIndex = "toString()")
    private String question3;

    @FormViewField(label = "我的答案", type = InputFiledType.TEXT)
    private String answer3;

}
