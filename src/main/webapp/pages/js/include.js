// var APP_PATH="http:/127.0.0.1:8080/travel_war";
var APP_PATH = "/travel_war";

$(function () {
    $.get("header.html",function (data) {
        $("#header").html(data);
    });
    $.get("footer.html",function (data) {
        $("#footer").html(data);
    });
});

//登录信息显示
$(function () {
    //向服务器获取登录用户信息
    $.get(APP_PATH+"/user/queryLoginUser.do",function (res) {
        if (res.flag){
            //隐藏登录div的内容
            $("#login_out").hide();
            $("#login").show();
            //显示登录用户 欢迎信息。
            $("#loginMsg").html("欢迎回来，"+res.data.name);
        }else{
            //如果没有登录，显示登录和注册的div
            $("#login_out").show();
            $("#login").hide();
        }
    })
})

function loginOut() {
    //登出处理
    //请求log out
    $.get(APP_PATH+"/user/logout.do",function (res) {
        console.log(res);
        if (res.flag){
            //显示登录和注册的div,隐藏欢迎信息div
            $("#login_out").show();
            $("#login").hide();
            location.href=APP_PATH+"/pages/login.html";
        }else {
            alert("注销失败！"+res.message);
        }
    })
}