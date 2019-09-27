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
                var content = '';
                var errorMsg = '';
                if (data.name != null) {
                    content += "<tr>";
                    content += "<td>" + data.name + "</td>";
                    content += "<td>" + data.stuNum + "</td>";
                    content += "<td>" + data.sex + "</td>";
                    content += "<td>" + data.institution + "</td>";
                    content += "<td>" + data.major + "</td>";
                    content += "<td>" + data.role.role + "</td>";
                    content += "<td><img src='../../uploadImg/rick.jpg' style='width: 50px'/></td>"
                    content += "</tr>";
                    //文本区域中显示学生信息
                    document.getElementById('studentinfo').innerHTML = content;
                    showUpdateArea();
                    //在修改信息form中显示信息
                    $('#update-no').val(data.stuNum);
                    $('#update-name').val(data.name);
                    $('#update-institution').val(data.institution);
                    $('#update-major').val(data.major);

                }
                else{
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
                var content = '';
                var errorMsg = '';
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        content += "<tr>";
                        content += "<td>" + data[i].name + "</td>";
                        content += "<td>" + data[i].stuNum + "</td>";
                        content += "<td>" + data[i].sex + "</td>";
                        content += "<td>" + data[i].institution + "</td>";
                        content += "<td>" + data[i].major + "</td>";
                        content += "<td>" + data[i].role.role + "</td>";
                        content += "<td><img src='../../uploadImg/rick.jpg' style='width: 50px'/></td>"
                        content += "</tr>";
                    }
                    document.getElementById('studentinfo').innerHTML = content;
                    // $('#studentinfo').val(content);
                    return;
                } else {
                    errorMsg += "<tr>";
                    errorMsg += "<td>查询无结果</td>";
                    errorMsg += "</tr>"
                    document.getElementById('studentinfo').innerHTML = errorMsg;
                }
            }
        });
    }

    //更新学生信息
    function updateStudent() {
        //trim() ：去掉前后空格
        studentParam.stuNum = $.trim($('#update-no').val());
        studentParam.name = $.trim($('#update-name').val());
        studentParam.sex = $('input:radio[name="update-sex"]:checked').val();
        studentParam.institution = $('#update-institution').val();
        studentParam.major = $('#update-major').val();
        var role = $('#update-role').val();
        var roleID = 0;
        switch (role) {
            case '普通会员':
                roleID = 1;
                break;
            case '协会学员':
                roleID = 2;
                break;
            case '403成员':
                roleID = 3;
                break;
            case '208+成员':
                roleID = 4;
                break;
            case '副会长':
                roleID = 5;
                break;
            case '会长':
                roleID = 6;
                break;
            case '指导老师':
                roleID = 7;
                break;
        }
        studentParam.roleID = roleID;
        if(!checkData(studentParam)){
            window.alert("请填写所有信息！");
            return;
        }
        //判断信息有无更新

        // ajax请求后台
        $.ajax({
            url: "updateStudent.do",
            type: "post",
            data: studentParam,
            success: function (data) {
                if (data > 0)
                    window.alert(" 更新成功！");
                else
                    window.alert("更新失败，请核对学号！");
            }
        });
    }

    // 删除学生信息
    function deleteStudent() {
        studentParam.name = $('#delete-name').val();
        if(studentParam.name == null){
            window.alert("输入不能为空！");
            return;
        }
        // ajax请求后台
        $.ajax({
            url: "deleteStudent.do",
            type: "post",
            data: studentParam,
            success: function (data) {
                console.log()
                var content = '';
                if (data != 0)
                    content = '成功删除' + data + '条数据！';
                else
                    content = '未查询到姓名为 ' + studentParam.name + ' 的成员！';
                window.alert(content);
            }
        });
    }

    // 新增学生信息
    function addStudent() {
        studentParam.name = $('#insert-name').val();
        studentParam.stuNum = $('#insert-no').val();
        studentParam.sex = $('input:radio[name="insert-sex"]:checked').val();
        studentParam.institution = $('#insert-institution').val();
        studentParam.major = $('#insert-major').val();
        var role = $('#insert-role').val();
        var roleID = 0;
        switch (role) {
            case '普通会员':
                roleID = 1;
                break;
            case '协会学员':
                roleID = 2;
                break;
            case '403成员':
                roleID = 3;
                break;
            case '208+成员':
                roleID = 4;
                break;
            case '副会长':
                roleID = 5;
                break;
            case '会长':
                roleID = 6;
                break;
            case '指导老师':
                roleID = 7;
                break;
        }
        studentParam.roleID = roleID;
        if(!checkData(studentParam)){
            window.alert("请填写所有信息！");
            return;
        }
        // ajax请求后台
        $.ajax({
            url: "addStudent.do",
            type: "post",
            data: studentParam,
            success: function (data) {
                console.log()
                var content = '';
                if (data != 0)
                    content = '成功插入' + data + '条数据！';
                else
                    content = '插入失败！';
                window.alert(content);
            }
        });
    }

    function checkData(studenParam) {
        console.log("CHECK!");
        return !(studenParam.name == null || studenParam.stuNum == null || studenParam.sex == null || studenParam.institution == null
        || studenParam.major == null || studenParam.roleID == null);
    }

    function showUpdateArea() {
        document.getElementById("insert-area").style.display = "none";
        document.getElementById("delete-area").style.display = "none";
        document.getElementById("update-area").style.display = "block";
    }

    function showDeleteArea() {
        document.getElementById("insert-area").style.display = "none";
        document.getElementById("update-area").style.display = "none";
        document.getElementById("delete-area").style.display = "block";
    }

    function showInsertArea() {
        document.getElementById("delete-area").style.display = "none";
        document.getElementById("update-area").style.display = "none";
        document.getElementById("insert-area").style.display = "block";
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

        $('#update').click(function () {
            showUpdateArea();
        })

        $('#delete').click(function () {
            showDeleteArea();
        })

        $('#insert').click(function () {
            showInsertArea();
        })

        $('#deleteStudent').click(function () {
            deleteStudent();
        })

        $('#insertStudent').click(function () {
            addStudent();
        })
    }

    //初始化  这一步不要少哦
    $(init);
})(jQuery);
