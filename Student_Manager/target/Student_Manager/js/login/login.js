(function ($) {
    var loginParam = {};
    var signUpParam = {};
    //登录
    function signIn() {
        // studentParam.stuNum = $('#keyvalue').val();
        loginParam.username =  $('#username').val();
        loginParam.password =  $('#password').val();
        // ajax请求后台
        $.ajax({
            url: "signIn.do",
            type: "post",
            data: loginParam,
            success: function (data) {
                if(data[1] != 'error'){
                    window.alert('成功登陆！');
                    var url = window.location.href;
                    if(data[1] == '5' || data[1] == '6' || data[1] == '7'){
                        url = url.replace('/login/login.html',
                            '/student/admin.html?stuNum=' + data[0] + '&role=' + data[1]);
                    }
                    else{
                        url = url.replace('/login/login.html',
                            '/student/personal.html?stuNum=' + data[0] + '&role=' + data[1]);
                    }

                    window.location.href = url;
                }
                else
                    window.alert('登陆失败！');
            }
        });
    }

    //注册
    function signUp() {
        signUpParam.stuNum =  $('#signup-username').val();
        signUpParam.password =  '123456';
        signUpParam.major = $('#signup-major').val();
        signUpParam.name = $('#signup-name').val();
        signUpParam.institution = $('#signup-institution').val();
        signUpParam.sex = $('#signup-sex').val();
        var role = $('#signup-role').val();
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
        signUpParam.roleID = roleID;
        // ajax请求后台
        $.ajax({
            url: "signUp.do",
            type: "post",
            data: signUpParam,
            success: function (data) {
                if(data == 1){
                    window.alert('成功注册！');
                    var url = window.location.href;
                    url = url.replace('/signUp.html',
                        '/login.html');
                    window.location.href = url;
                }
                else
                    window.alert("重新检查你的学号和姓名！");
            }
        });
    }

    function toSignUp() {
        var url = window.location.href;
        url = url.replace('/login.html',
            '/signUp.html');
        window.location.href = url;
    }

    function toLogin() {
        var url = window.location.href;
        url = url.replace('/signUp.html',
            '/login.html');
        window.location.href = url;
    }
    /** 页面onload事件 */
    function init() {
        $('#login').click(function () {
            signIn();
        });

        $('#signup').click(function () {
            signUp();
        });

        $('#login-signup').click(function () {
            toSignUp();
        });

        $('#toLogin').click(function () {
            toLogin();
        });
    }
    //初始化
    $(init);
})(jQuery);
