/**
 * 短信验证码JS组件
 *
 * 实现版本： Materialize CSS
 *
 * Created by aaric on 2017/7/9.
 */

$(document).ready(function () {
    let $captchaButton = $("#getPhoneCaptcha");

    $captchaButton.click(function () {
        $captchaButton.addClass("disabled");
        $captchaButton.text("正在发送短信");

        $.ajax("/api/captcha/phone", {
            type: "POST",
            data: {
                phone: $("#phone").val()
            },
            success: function () {
                Materialize.toast("短信发送成功", 3000);
                let duration = 60;

                let timer = setInterval(function () {
                    $captchaButton.text(`${duration}秒后可重发`);
                    duration--;
                    if (duration === 0) {
                        clearInterval(timer);
                        $captchaButton.text("重新发送");
                        $captchaButton.removeClass("disabled");
                    }
                }, 1000);
            },
            error: function (requset) {
                $captchaButton.text("重新发送");

                Materialize.toast(`短信发送失败，原因: ${requset.responseText}`, 3000, "", function () {
                    $captchaButton.removeClass("disabled");
                });
            }
        });
    });
});
