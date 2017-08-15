/**
 * 为所有表单添加确认对话框
 *
 * 实现版本： Materialize CSS
 *
 * Created by aaric on 2017/7/9.
 */

$(document).ready(function () {
    $("form").each(function () {
        $(this).submit(function (e) {

            var showDialog = $(this).attr("show-dialog") === 'true';
            var title = $(this).attr("title") || "您确定要执行该操作？";
            var desc = $(this).attr("description") || "执行的操作可以在审计日志中看到";

            if (showDialog) {
                var confirmModal;
                var existModal = $("#qxcmp-confirm-dialog");
                if (existModal.length > 0) {
                    confirmModal = existModal;
                } else {
                    confirmModal = $('<div id="qxcmp-confirm-dialog" class="modal"></div>');

                    var modalContent = $('<div class="modal-content"></div>').append('<h4>' + title + '</h4>').append('<p>' + desc + '</p>');
                    var modalAction = $('<div class="modal-footer"></div>')
                        .append('<a href="#!" class="primary-text modal-action modal-close waves-effect waves-green btn-flat">确认</a>')
                        .append('<a href="#!" class="grey-text modal-action modal-close waves-effect waves-green btn-flat">取消</a>');

                    confirmModal.append(modalContent).append(modalAction);
                    $("body").append(confirmModal);
                }

                $(confirmModal).modal({
                    dismissible: false,
                    complete: function (form, event) {
                        if (event && event.target.text === "确认") {
                            e.target.submit();
                        }
                    }
                }).modal("open");

                e.preventDefault();
            }

        });
    })
});
