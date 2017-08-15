import "./sass/qxcmp.scss";
import "./materialize/js/bin/materialize";
import "./js/dialog";
import "./js/captcha";
import "./js/quill";

$(document).ready(function () {
    $('textarea').trigger('autoresize');
    $('.button-collapse').sideNav();
    $('ul.tabs').tabs();
    $('select.qxcmp').material_select();
    $('#changeCaptcha').on('click', function () {
        var imgSrc = $("#captchaImage");
        var src = imgSrc.attr("src");
        imgSrc.attr("src", function () {

            var timestamp = (new Date()).valueOf();
            if ((src.indexOf("?") >= 0)) {
                src = src.substring(0, src.indexOf('?')) + "?timestamp=" + timestamp;
            } else {
                src = src + "?timestamp=" + timestamp;
            }
            return src;
        });
    });


    /*
     * Slider Initialize
     * */
    var slider = $('.slider');
    slider.slider({
        indicators: $(slider).attr('indicators') === 'true',
        height: parseInt($(slider).attr('height')) || 400,
        transition: parseInt($(slider).attr('transition')) || 500,
        interval: parseInt($(slider).attr('interval')) || 6000
    });

});