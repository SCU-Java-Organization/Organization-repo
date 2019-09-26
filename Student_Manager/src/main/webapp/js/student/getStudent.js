(function ($) {
    //设置全局对象，方便后期更新比较
    var studentParam = {};

    //根据学号查找学生
    function searchByNo() {
        studentParam.stuNum = $('#keyvalue').val();
        // ajax请求后台
        $.ajax({
            url: "getStudentByStuNum.do",
            type: "post",
            data: studentParam,
            success: function (data) {
                var content = "<tr>" + "<td>学号</td>" + "<td>姓名</td>" + "<td>性别</td>";
                if (data != null) {
                    content += "<tr>";
                    content += "<td>" + data.stuNum + "</td>";
                    content += "<td>" + data.name + "</td>";
                    content += "<td>" + data.sex + "</td>";
                    content += "</tr>";
                    //文本区域中显示学生信息
                    document.getElementById('studentinfo').innerHTML = content;
                    //在修改信息form中显示信息
                    $('#no').val(data.stuNum);
                    $('#name').val(data.name);
                }
                else{
                    var errorMsg = "<tr>" + "<td>学号</td>" + "<td>姓名</td>" + "<td>性别</td>";
                    errorMsg += "<tr>";
                    errorMsg += "<td>查询无结果</td>";
                    errorMsg += "</tr>"
                    document.getElementById('studentinfo').innerHTML = errorMsg;
                }
            }
        });
    }

    //查找所有学生
    function searchStudents() {
        // ajax请求后台
        $.ajax({
            url: "getAllStudents.do",
            type: "get",
            // data: {},
            success: function (data) {
                // console.log(data.length);
                // console.log(data[0].name);
                var content = "<tr>" + "<td>学号</td>" + "<td>姓名</td>" + "<td>性别</td>";
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        content += "<tr>";
                        content += "<td>" + data[i].stuNum + "</td>";
                        content += "<td>" + data[i].name + "</td>";
                        content += "<td>" + data[i].sex + "</td>";
                        content += "</tr>";
                    }
                    document.getElementById('studentinfo').innerHTML = content;
                    // $('#studentinfo').val(content);
                    return;
                } else {
                    $('#studentinfo').val("null，请检查搜索条件是否正确！");
                    return;
                }
            }
        });
    }

//更新学生信息
    function updateStudent() {
        //trim() ：去掉前后空格
        var no = $.trim($('#no').val());
        var name = $.trim($('#name').val());
        var sex = $('input:radio[name="sex"]:checked').val();
        //客户端判断
        if (!validate.input.StringEmptyValidator(no, "学号")) {
            return;
        }
        if (!validate.input.StringEmptyValidator(name, "姓名")) {
            return;
        }

        //判断信息有无更新
        var isUpdate = false;
        if (studentParam.no != no) {
            console.log("学号有变动")
            isUpdate = true;
        }
        if (studentParam.name != name) {
            console.log("姓名有变动")
            isUpdate = true;
        }
        if (studentParam.sex != sex) {
            console.log("性别有变动");
            isUpdate = true;
        }
        if (isUpdate) {
            console.log("学生信息有变动，提交更新。");
            newStudent = {};
            newStudent.no = no;
            newStudent.name = name;
            newStudent.sex = sex;
            // ajax请求后台
            $.ajax({
                url: "updateStudent.do",
                type: "post",
                data: newStudent,
                success: function (data) {
                    if (data.msg == "success") {
                        alert(studentParam.no + " 更新成功！");
                    }
                    else if (data.msg == "fail") {
                        alert(studentParam.no + " 更新失败！");
                    }
                    return;
                }
            });
        }
        else {
            alert("信息没有变动，不需要更新");
            return;
        }
    }

    /** 页面onload事件 */
    function init() {
        $('#searchByNo').click(function () {
            searchByNo();
        });

        $('#searchStudents').click(function () {
            searchStudents();
        });

        $('#updateStudent').click(function () {
            updateStudent();
        });
    }

    //初始化  这一步不要少哦
    $(init);
})(jQuery);
