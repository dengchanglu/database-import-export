/**
 * Created by perfection on 17-3-1.
 */
var login = function(account,pwd){
    http({
        method:'post',
        url:'192.168.2.38:8081/database-import-export/data/login',
        data:{account:account,pwd:pwd},
        success:function (res) {
            console.log(res);
        }
    })
};
var register = function(mobile,account,pwd,pwda){

};

http({
    method:'post',
    url:'http://192.168.2.38:8080/database-import-export/data/saveAuthenticationPhoneData',
    data:{path:"/home/perfection/dataSourceTest/手机号段-20170301-358215-全新版.xlsx"},
    success:function (res) {
        console.log(res);
    }
});