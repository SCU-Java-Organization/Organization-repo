(function ($) {
    //设置全局对象，方便后期更新比较
    var studentParam = {};
    var STU_NUM = '';
    var personalInfo = {};

    //根据学号查找学生
    function searchByNo() {
        studentParam = {};
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
        studentParam = {};
        studentParam.stuNum = personalInfo.stuNum;
        studentParam.name = $.trim($('#update-name').val());
        studentParam.sex = $('#update-sex').val();
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
        studentParam = {};
        studentParam.name = personalInfo.name;
        if(confirm("退出协会后将删除你的个人信息（包括账号)，确定退出吗？")){
            // ajax请求后台
            $.ajax({
                url: "deleteStudent.do",
                type: "post",
                data: studentParam,
                success: function (data) {
                    var content = '未知错误，请稍后重试！';
                    if (data != 0)
                        content = '您已退出协会！';
                    window.alert(content);
                    var url = window.location.href;
                    url = url.split('?')[0];
                    url = url.replace('/student/personal.html',
                        '/login/login.html');
                    window.location.href = url;
                }
            });
        }
    }

    function checkData(studentParam) {
        console.log(studentParam);
        console.log("CHECK! " + studentParam.institution == '');
        return studentParam.name != ''
            && studentParam.stuNum != ''
            && studentParam.sex != ''
            && studentParam.institution != ''
            && studentParam.major != ''
            && studentParam.roleID != '';
    }

    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  // 匹配目标参数
        if (r != null) return unescape(r[2]); return null; // 返回参数值
    }

    function funcUrlDel(name){
        var loca = window.location;
        var baseUrl = loca.origin + loca.pathname + "?";
        var query = loca.search.substr(1);
        if (query.indexOf(name)>-1) {
            var obj = {}
            var arr = query.split("&");
            for (var i = 0; i < arr.length; i++) {
                arr[i] = arr[i].split("=");
                obj[arr[i][0]] = arr[i][1];
            };
            delete obj[name];
            var url = baseUrl + JSON.stringify(obj).replace(/[\"\{\}]/g,"").replace(/\:/g,"=").replace(/\,/g,"&");
            return url
        };
    }

    // 更改密码
    function resetPassword() {
        var param = {};
        param.oldPsw = $('#old-psw').val();
        param.newPsw = $('#new-psw').val();
        param.checkPsw = $('#new-psw-check').val();
        if(param.newPsw != param.checkPsw){
            window.alert("两次密码不一致！");
            $('#new-psw').val('');
            $('#new-psw-check').val('');
            return;
        }

        // ajax请求后台
        $.ajax({
            url: "resetPassword.do",
            type: "post",
            data: param,
            success: function (data) {
                if (data > 0)
                    window.alert(" 更新密码成功！");
                else
                    window.alert("更新密码失败，请核对原始密码！");
            }
        });
    }

    // 课堂签到
    function attend() {
        var param = {};
        param.stuNum = personalInfo.stuNum;
        param.className = $('#check-name').val();
        param.code = $('#check-code').val();
        param.stuName = personalInfo.name;
        // ajax请求后台
        $.ajax({
            url: "attend.do",
            type: "post",
            data: param,
            success: function (data) {
                if(data == 1)
                    window.alert("签到成功！");
                else
                    window.alert("签到失败！");
            }
        });
    }

    function showUpdateArea() {
        document.getElementById("reset-area").style.display = "none";
        document.getElementById("check-area").style.display = "none";
        document.getElementById("update-area").style.display = "block";
    }

    function showCheckArea(){
        document.getElementById("reset-area").style.display = "none";
        document.getElementById("update-area").style.display = "none";
        document.getElementById("check-area").style.display = "block";
    }

    function showResetArea(){
        document.getElementById("update-area").style.display = "none";
        document.getElementById("check-area").style.display = "none";
        document.getElementById("reset-area").style.display = "block";
    }

    $( function () {
        STU_NUM = getUrlParam('stuNum');
        studentParam.stuNum = STU_NUM;
        $.ajax({
            url: "getStudentByStuNum.do",
            type: "post",
            data: studentParam,
            success: function (data) {
                if (data.name != null) {
                    personalInfo = data;
                    $('#myName').text(personalInfo.name + '同学');
                    $('#update-no').val( personalInfo.stuNum);
                    $('#update-name').val( personalInfo.name);
                    $('#update-sex').val( personalInfo.sex);
                    $('#update-institution').val( personalInfo.institution);
                    $('#update-major').val( personalInfo.major);
                    $('#update-role').val( personalInfo.role.role);
                }
            }
        });
    })

    /** 页面onload事件 */
    function init() {
        $('#searchByNo').click(function () {
            searchByNo();
        });

        $('#searchStudents').click(function () {
            searchStudents();
        });

        $('#update').click(function () {
            showUpdateArea();
        });

        $('#updateStudent').click(function () {
            updateStudent();
        });

        $('#delete').click(function () {
            deleteStudent();
        });

        $('#check').click(function () {
            showCheckArea();
        });

        $('#reset').click(function () {
            showResetArea();
        });

        $('#check-btn').click(function () {
            attend();
        });

        $('#reset-btn').click(function () {
            resetPassword();
        });
    }

    //初始化  这一步不要少哦
    $(init);
})(jQuery);
