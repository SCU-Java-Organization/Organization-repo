(function ($) {
    // 设置全局对象，方便后期更新比较
    var sheetParam = {};

    // 开启新签到
    function insertSheet() {
        sheetParam.className = $('#check-name').val();
        sheetParam.code = $('#check-code').val();
        // ajax请求后台
        $.ajax({
            url: "newSheet.do",
            type: "post",
            data: sheetParam,
            success: function (data) {
                window.alert("开启成功！");
            }
        });
    }

    // 查看签到情况
    function getAttendance() {
        var param = {};
        param.className = $('#check-info-name').val();
        $.ajax({
            url: "getAttendance.do",
            type: "post",
            data: param ,
            success: function (data) {
                var content = '';
                var errorMsg = '';
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        content += "<tr>";
                        content += "<td>" + data[i].className + "</td>";
                        content += "<td>" + data[i].stuName + "</td>";
                        content += "<td>" + data[i].stuNum + "</td>";
                        content += "<td>" + data[i].utilDate + "</td>";
                        content += "</tr>";
                    }
                    document.getElementById('attendanceinfo').innerHTML = content;
                    // $('#studentinfo').val(content);
                    return;
                } else {
                    errorMsg += "<tr>";
                    errorMsg += "<td>查询无结果</td>";
                    errorMsg += "</tr>"
                    document.getElementById('attendanceinfo').innerHTML = errorMsg;
                }
            }
        });
    }

    /** 页面onload事件 */
    function init() {
        $('#open-check').click(function () {
            insertSheet();
        });

        $('#show-check-info').click(function () {
            getAttendance();
        });
    }

    //初始化  这一步不要少哦
    $(init);
})(jQuery);
