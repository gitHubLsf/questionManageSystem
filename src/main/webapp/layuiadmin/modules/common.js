layui.define(function(exports){
  var $ = layui.$
  ,layer = layui.layer
  ,laytpl = layui.laytpl
  ,setter = layui.setter
  ,view = layui.view
  ,admin = layui.admin;


  admin.events.logout = function(){
    admin.req({
      url: layui.setter.base + 'json/user/logout.js'
      ,type: 'get'
      ,data: {}
      ,done: function(res){

        admin.exit(function(){
          location.href = 'user/login.html';
        });
      }
    });
  };

  exports('common', {});
});