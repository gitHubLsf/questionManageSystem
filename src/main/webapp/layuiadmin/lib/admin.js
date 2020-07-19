layui.define('view', function(exports){
  var $ = layui.jquery
  ,laytpl = layui.laytpl
  ,element = layui.element
  ,setter = layui.setter
  ,view = layui.view
  ,device = layui.device()
  
  ,$win = $(window), $body = $('body')
  ,container = $('#'+ setter.container)
  
  ,SHOW = 'layui-show', HIDE = 'layui-hide', THIS = 'layui-this', DISABLED = 'layui-disabled', TEMP = 'template'
  ,APP_BODY = '#LAY_app_body', APP_FLEXIBLE = 'LAY_app_flexible'
  ,FILTER_TAB_TBAS = 'layadmin-layout-tabs'
  ,APP_SPREAD_SM = 'layadmin-side-spread-sm', TABS_BODY = 'layadmin-tabsbody-item'
  ,ICON_SHRINK = 'layui-icon-shrink-right', ICON_SPREAD = 'layui-icon-spread-left'
  ,SIDE_SHRINK = 'layadmin-side-shrink', SIDE_MENU = 'LAY-system-side-menu'

  ,admin = {
    v: '1.2.1 std'
    ,req: view.req
    ,exit: view.exit

    ,escape: function(html){
      return String(html || '').replace(/&(?!#?[a-zA-Z0-9]+;)/g, '&amp;')
      .replace(/</g, '&lt;').replace(/>/g, '&gt;')
      .replace(/'/g, '&#39;').replace(/"/g, '&quot;');
    }

    ,on: function(events, callback){
      return layui.onevent.call(this, setter.MOD_NAME, events, callback);
    }

    ,sendAuthCode: function(options){
      options = $.extend({
        seconds: 60
        ,elemPhone: '#LAY_phone'
        ,elemVercode: '#LAY_vercode'
      }, options);

      var seconds = options.seconds
      ,btn = $(options.elem)
      ,token = null
      ,timer, countDown = function(loop){
        seconds--;
        if(seconds < 0){
          btn.removeClass(DISABLED).html('获取验证码');
          seconds = options.seconds;
          clearInterval(timer);
        } else {
          btn.addClass(DISABLED).html(seconds + '秒后重获');
        }

        if(!loop){
          timer = setInterval(function(){
            countDown(true);
          }, 1000);
        }
      };
      
      options.elemPhone = $(options.elemPhone);
      options.elemVercode = $(options.elemVercode);

      btn.on('click', function(){
        var elemPhone = options.elemPhone
        ,value = elemPhone.val();

        if(seconds !== options.seconds || $(this).hasClass(DISABLED)) return;

        if(!/^1\d{10}$/.test(value)){
          elemPhone.focus();
          return layer.msg('请输入正确的手机号')
        };
        
        if(typeof options.ajax === 'object'){
          var success = options.ajax.success;
          delete options.ajax.success;
        }
        
        admin.req($.extend(true, {
          url: '/auth/code'
          ,type: 'get'
          ,data: {
            phone: value
          }
          ,success: function(res){
            layer.msg('验证码已发送至你的手机，请注意查收', {
              icon: 1
              ,shade: 0
            });
            options.elemVercode.focus();
            countDown();
            success && success(res);
          }
        }, options.ajax));
      });
    }

    ,screen: function(){
      var width = $win.width()
      if(width > 1200){
        return 3;
      } else if(width > 992){
        return 2;
      } else if(width > 768){
        return 1;
      } else {
        return 0;
      }
    }

    ,sideFlexible: function(status){
      var app = container
      ,iconElem =  $('#'+ APP_FLEXIBLE)
      ,screen = admin.screen();

      if(status === 'spread'){
        iconElem.removeClass(ICON_SPREAD).addClass(ICON_SHRINK);

        if(screen < 2){
          app.addClass(APP_SPREAD_SM);
        } else {
          app.removeClass(APP_SPREAD_SM);
        }
        
        app.removeClass(SIDE_SHRINK)
      } else {
        iconElem.removeClass(ICON_SHRINK).addClass(ICON_SPREAD);

        if(screen < 2){
          app.removeClass(SIDE_SHRINK);
        } else {
          app.addClass(SIDE_SHRINK);
        }
        
        app.removeClass(APP_SPREAD_SM)
      }
      
      layui.event.call(this, setter.MOD_NAME, 'side({*})', {
        status: status
      });
    }

    ,popup: view.popup

    ,popupRight: function(options){
      //layer.close(admin.popup.index);
      return admin.popup.index = layer.open($.extend({
        type: 1
        ,id: 'LAY_adminPopupR'
        ,anim: -1
        ,title: false
        ,closeBtn: false
        ,offset: 'r'
        ,shade: 0.1
        ,shadeClose: true
        ,skin: 'layui-anim layui-anim-rl layui-layer-adminRight'
        ,area: '300px'
      }, options));
    }

    ,theme: function(options){
      var theme = setter.theme
      ,local = layui.data(setter.tableName)
      ,id = 'LAY_layadmin_theme'
      ,style = document.createElement('style')
      ,styleText = laytpl([
        '.layui-side-menu,'
        ,'.layadmin-pagetabs .layui-tab-title li:after,'
        ,'.layadmin-pagetabs .layui-tab-title li.layui-this:after,'
        ,'.layui-layer-admin .layui-layer-title,'
        ,'.layadmin-side-shrink .layui-side-menu .layui-nav>.layui-nav-item>.layui-nav-child'
        ,'{background-color:{{d.color.main}} !important;}'

        ,'.layui-nav-tree .layui-this,'
        ,'.layui-nav-tree .layui-this>a,'
        ,'.layui-nav-tree .layui-nav-child dd.layui-this,'
        ,'.layui-nav-tree .layui-nav-child dd.layui-this a'
        ,'{background-color:{{d.color.selected}} !important;}'

        ,'.layui-layout-admin .layui-logo{background-color:{{d.color.logo || d.color.main}} !important;}'

        ,'{{# if(d.color.header){ }}'
          ,'.layui-layout-admin .layui-header{background-color:{{ d.color.header }};}'
          ,'.layui-layout-admin .layui-header a,'
          ,'.layui-layout-admin .layui-header a cite{color: #f8f8f8;}'
          ,'.layui-layout-admin .layui-header a:hover{color: #fff;}'
          ,'.layui-layout-admin .layui-header .layui-nav .layui-nav-more{border-top-color: #fbfbfb;}'
          ,'.layui-layout-admin .layui-header .layui-nav .layui-nav-mored{border-color: transparent; border-bottom-color: #fbfbfb;}'
          ,'.layui-layout-admin .layui-header .layui-nav .layui-this:after, .layui-layout-admin .layui-header .layui-nav-bar{background-color: #fff; background-color: rgba(255,255,255,.5);}'
          ,'.layadmin-pagetabs .layui-tab-title li:after{display: none;}'
        ,'{{# } }}'
      ].join('')).render(options = $.extend({}, local.theme, options))
      ,styleElem = document.getElementById(id);

      if('styleSheet' in style){
        style.setAttribute('type', 'text/css');
        style.styleSheet.cssText = styleText;
      } else {
        style.innerHTML = styleText;
      }
      style.id = id;
      
      styleElem && $body[0].removeChild(styleElem);
      $body[0].appendChild(style);
      $body.attr('layadmin-themealias', options.color.alias);

      local.theme = local.theme || {};
      layui.each(options, function(key, value){
        local.theme[key] = value;
      });
      layui.data(setter.tableName, {
        key: 'theme'
        ,value: local.theme
      }); 
    }

    ,initTheme: function(index){
      var theme = setter.theme;
      index = index || 0;
      if(theme.color[index]){
        theme.color[index].index = index;
        admin.theme({
          color: theme.color[index]
        });
      }
    }

    ,tabsPage: {}

    ,tabsBody: function(index){
      return $(APP_BODY).find('.'+ TABS_BODY).eq(index || 0);
    }

    ,tabsBodyChange: function(index, options){
      options = options || {};
      
      admin.tabsBody(index).addClass(SHOW).siblings().removeClass(SHOW);
      events.rollPage('auto', index);

      layui.event.call(this, setter.MOD_NAME, 'tabsPage({*})', {
        url: options.url
        ,text: options.text
      });
    }

    ,resize: function(fn){
      var router = layui.router()
      ,key = router.path.join('-');
      
      if(admin.resizeFn[key]){
        $win.off('resize', admin.resizeFn[key]);
        delete admin.resizeFn[key];
      }
      
      if(fn === 'off') return;
      
      fn(), admin.resizeFn[key] = fn;
      $win.on('resize', admin.resizeFn[key]);
    }
    ,resizeFn: {}
    ,runResize: function(){
      var router = layui.router()
      ,key = router.path.join('-');
      admin.resizeFn[key] && admin.resizeFn[key]();
    }
    ,delResize: function(){
      this.resize('off');
    }

    ,closeThisTabs: function(){
      if(!admin.tabsPage.index) return;
      $(TABS_HEADER).eq(admin.tabsPage.index).find('.layui-tab-close').trigger('click');
    }

    ,fullScreen: function(){
      var ele = document.documentElement
      ,reqFullScreen = ele.requestFullScreen || ele.webkitRequestFullScreen 
      || ele.mozRequestFullScreen || ele.msRequestFullscreen;      
      if(typeof reqFullScreen !== 'undefined' && reqFullScreen) {
        reqFullScreen.call(ele);
      };
    }

    ,exitScreen: function(){
      var ele = document.documentElement
      if (document.exitFullscreen) {  
        document.exitFullscreen();  
      } else if (document.mozCancelFullScreen) {  
        document.mozCancelFullScreen();  
      } else if (document.webkitCancelFullScreen) {  
        document.webkitCancelFullScreen();  
      } else if (document.msExitFullscreen) {  
        document.msExitFullscreen();  
      }
    }

  };

  var events = admin.events = {
    flexible: function(othis){
      var iconElem = othis.find('#'+ APP_FLEXIBLE)
      ,isSpread = iconElem.hasClass(ICON_SPREAD);
      admin.sideFlexible(isSpread ? 'spread' : null);
    }

    ,refresh: function(){
      var ELEM_IFRAME = '.layadmin-iframe'
      ,length = $('.'+ TABS_BODY).length;
      
      if(admin.tabsPage.index >= length){
        admin.tabsPage.index = length - 1;
      }
      
      var iframe = admin.tabsBody(admin.tabsPage.index).find(ELEM_IFRAME);
      iframe[0].contentWindow.location.reload(true);
    }

    ,serach: function(othis){
      othis.off('keypress').on('keypress',function(e){
        if(!this.value.replace(/\s/g, '')) return;
        if(e.keyCode === 13){
          var href = othis.attr('lay-action')
          ,text = othis.attr('lay-text') || '搜索';
          
          href = href + this.value;
          text = text + ' <span style="color: #FF5722;">'+ admin.escape(this.value) +'</span>';

          layui.index.openTabsPage(href, text);

          events.serach.keys || (events.serach.keys = {});
          events.serach.keys[admin.tabsPage.index] = this.value;
          if(this.value === events.serach.keys[admin.tabsPage.index]){
            events.refresh(othis);
          }

          this.value = '';
        }       
      });
    }

    ,message: function(othis){
      othis.find('.layui-badge-dot').remove();
    }

    ,theme: function(){
      admin.popupRight({
        id: 'LAY_adminPopupTheme'
        ,success: function(){
          view(this.id).render('system/theme')
        }
      });
    }

    ,note: function(othis){
      var mobile = admin.screen() < 2
      ,note = layui.data(setter.tableName).note;
      
      events.note.index = admin.popup({
        title: '便签'
        ,shade: 0
        ,offset: [
          '41px'
          ,(mobile ? null : (othis.offset().left - 250) + 'px')
        ]
        ,anim: -1
        ,id: 'LAY_adminNote'
        ,skin: 'layadmin-note layui-anim layui-anim-upbit'
        ,content: '<textarea placeholder="内容"></textarea>'
        ,resize: false
        ,success: function(layero, index){
          var textarea = layero.find('textarea')
          ,value = note === undefined ? '便签中的内容会存储在本地，这样即便你关掉了浏览器，在下次打开时，依然会读取到上一次的记录。是个非常小巧实用的本地备忘录' : note;
          
          textarea.val(value).focus().on('keyup', function(){
            layui.data(setter.tableName, {
              key: 'note'
              ,value: this.value
            });
          });
        }
      })
    }

    ,fullscreen: function(othis){
      var SCREEN_FULL = 'layui-icon-screen-full'
      ,SCREEN_REST = 'layui-icon-screen-restore'
      ,iconElem = othis.children("i");
      
      if(iconElem.hasClass(SCREEN_FULL)){
        admin.fullScreen();
        iconElem.addClass(SCREEN_REST).removeClass(SCREEN_FULL);
      } else {
        admin.exitScreen();
        iconElem.addClass(SCREEN_FULL).removeClass(SCREEN_REST);
      }
    }

    ,about: function(){
      admin.popupRight({
        id: 'LAY_adminPopupAbout'
        ,success: function(){
          view(this.id).render('system/about');
        }
      });
    }

    ,more: function(){
      admin.popupRight({
        id: 'LAY_adminPopupMore'
        ,success: function(){
          view(this.id).render('system/more');
        }
      });
    }

    ,back: function(){
      history.back();
    }

    ,setTheme: function(othis){
      var index = othis.data('index')
      ,nextIndex = othis.siblings('.layui-this').data('index');
      
      if(othis.hasClass(THIS)) return;
      
      othis.addClass(THIS).siblings('.layui-this').removeClass(THIS);
      admin.initTheme(index);
    }

    ,rollPage: function(type, index){
      var tabsHeader = $('#LAY_app_tabsheader')
      ,liItem = tabsHeader.children('li')
      ,scrollWidth = tabsHeader.prop('scrollWidth')
      ,outerWidth = tabsHeader.outerWidth()
      ,tabsLeft = parseFloat(tabsHeader.css('left'));

      if(type === 'left'){
        if(!tabsLeft && tabsLeft <=0) return;

        var  prefLeft = -tabsLeft - outerWidth; 

        liItem.each(function(index, item){
          var li = $(item)
          ,left = li.position().left;
          
          if(left >= prefLeft){
            tabsHeader.css('left', -left);
            return false;
          }
        });
      } else if(type === 'auto'){
        (function(){
          var thisLi = liItem.eq(index), thisLeft;
          
          if(!thisLi[0]) return;
          thisLeft = thisLi.position().left;

          if(thisLeft < -tabsLeft){
            return tabsHeader.css('left', -thisLeft);
          }

          if(thisLeft + thisLi.outerWidth() >= outerWidth - tabsLeft){
            var subLeft = thisLeft + thisLi.outerWidth() - (outerWidth - tabsLeft);
            liItem.each(function(i, item){
              var li = $(item)
              ,left = li.position().left;

              if(left + tabsLeft > 0){
                if(left - tabsLeft > subLeft){
                  tabsHeader.css('left', -left);
                  return false;
                }
              }
            });
          }
        }());
      } else {
        liItem.each(function(i, item){
          var li = $(item)
          ,left = li.position().left;

          if(left + li.outerWidth() >= outerWidth - tabsLeft){
            tabsHeader.css('left', -left);
            return false;
          }
        });
      }      
    }

    ,leftPage: function(){
      events.rollPage('left');
    }

    ,rightPage: function(){
      events.rollPage();
    }

    ,closeThisTabs: function(){
      var topAdmin = parent === self ? admin : parent.layui.admin;
      topAdmin.closeThisTabs();
    }

    ,closeOtherTabs: function(type){
      var TABS_REMOVE = 'LAY-system-pagetabs-remove';
      if(type === 'all'){
        $(TABS_HEADER+ ':gt(0)').remove();
        $(APP_BODY).find('.'+ TABS_BODY+ ':gt(0)').remove();

        $(TABS_HEADER).eq(0).trigger('click');
      } else {
        $(TABS_HEADER).each(function(index, item){
          if(index && index != admin.tabsPage.index){
            $(item).addClass(TABS_REMOVE);
            admin.tabsBody(index).addClass(TABS_REMOVE);
          }
        });
        $('.'+ TABS_REMOVE).remove();
      }
    }

    ,closeAllTabs: function(){
      events.closeOtherTabs('all');
      //location.hash = '';
    }

    ,shade: function(){
      admin.sideFlexible();
    }

    ,im: function(){
      admin.popup({
        id: 'LAY-popup-layim-demo'
        ,shade: 0
        ,area: ['800px', '300px']
        ,title: '面板外的操作示例'
        ,offset: 'lb'
        ,success: function(){
          layui.view(this.id).render('layim/demo').then(function(){
            layui.use('im');
          });
        }
      })
    }
  };

  !function(){
    var local = layui.data(setter.tableName);
    if(local.theme){
      admin.theme(local.theme);
    } else if(setter.theme){
      admin.initTheme(setter.theme.initColorIndex);
    }

    if(!('pageTabs' in layui.setter)) layui.setter.pageTabs = true;

    if(!setter.pageTabs){
      $('#LAY_app_tabs').addClass(HIDE);
      container.addClass('layadmin-tabspage-none');
    }


    if(device.ie && device.ie < 10){
      view.error('IE'+ device.ie + '下访问可能不佳，推荐使用：Chrome / Firefox / Edge 等高级浏览器', {
        offset: 'auto'
        ,id: 'LAY_errorIE'
      });
    }
    
  }();
  

  element.on('tab('+ FILTER_TAB_TBAS +')', function(data){
    admin.tabsPage.index = data.index;
  });

  admin.on('tabsPage(setMenustatus)', function(router){
    var pathURL = router.url, getData = function(item){
      return {
        list: item.children('.layui-nav-child')
        ,a: item.children('*[lay-href]')
      }
    }
    ,sideMenu = $('#'+ SIDE_MENU)
    ,SIDE_NAV_ITEMD = 'layui-nav-itemed'

    ,matchMenu = function(list){
      list.each(function(index1, item1){
        var othis1 = $(item1)
        ,data1 = getData(othis1)
        ,listChildren1 = data1.list.children('dd')
        ,matched1 = pathURL === data1.a.attr('lay-href');
        
        listChildren1.each(function(index2, item2){
          var othis2 = $(item2)
          ,data2 = getData(othis2)
          ,listChildren2 = data2.list.children('dd')
          ,matched2 = pathURL === data2.a.attr('lay-href');
          
          listChildren2.each(function(index3, item3){
            var othis3 = $(item3)
            ,data3 = getData(othis3)
            ,matched3 = pathURL === data3.a.attr('lay-href');
            
            if(matched3){
              var selected = data3.list[0] ? SIDE_NAV_ITEMD : THIS;
              othis3.addClass(selected).siblings().removeClass(selected); //标记选择器
              return false;
            }
            
          });

          if(matched2){
            var selected = data2.list[0] ? SIDE_NAV_ITEMD : THIS;
            othis2.addClass(selected).siblings().removeClass(selected); //标记选择器
            return false
          }
          
        });
        
        if(matched1){
          var selected = data1.list[0] ? SIDE_NAV_ITEMD : THIS;
          othis1.addClass(selected).siblings().removeClass(selected); //标记选择器
          return false;
        }
        
      });
    }

    sideMenu.find('.'+ THIS).removeClass(THIS);

    if(admin.screen() < 2) admin.sideFlexible();

    matchMenu(sideMenu.children('li'));
  });

  element.on('nav(layadmin-system-side-menu)', function(elem){
    if(elem.siblings('.layui-nav-child')[0] && container.hasClass(SIDE_SHRINK)){
      admin.sideFlexible('spread');
      layer.close(elem.data('index'));
    };
    admin.tabsPage.type = 'nav';
  });

  element.on('nav(layadmin-pagetabs-nav)', function(elem){
    var dd = elem.parent();
    dd.removeClass(THIS);
    dd.parent().removeClass(SHOW);
  });

  var setThisRouter = function(othis){
    var layid = othis.attr('lay-id')
    ,attr = othis.attr('lay-attr')
    ,index = othis.index();
    
    admin.tabsBodyChange(index, {
      url: attr
    });
  }
  ,TABS_HEADER = '#LAY_app_tabsheader>li';

  $body.on('click', TABS_HEADER, function(){
    var othis = $(this)
    ,index = othis.index();
    
    admin.tabsPage.type = 'tab';
    admin.tabsPage.index = index;

    setThisRouter(othis);
  });

  element.on('tabDelete('+ FILTER_TAB_TBAS +')', function(obj){
    var othis = $(TABS_HEADER+ '.layui-this');
    
    obj.index && admin.tabsBody(obj.index).remove();
    setThisRouter(othis);

    admin.delResize();
  });

  $body.on('click', '*[lay-href]', function(){
    var othis = $(this)
    ,href = othis.attr('lay-href')
    ,text = othis.attr('lay-text')
    ,router = layui.router();
    
    admin.tabsPage.elem = othis;

    var topLayui = parent === self ? layui : top.layui;
    topLayui.index.openTabsPage(href, text || othis.text());
  });
  

  $body.on('click', '*[layadmin-event]', function(){
    var othis = $(this)
    ,attrEvent = othis.attr('layadmin-event');
    events[attrEvent] && events[attrEvent].call(this, othis);
  });
  

  $body.on('mouseenter', '*[lay-tips]', function(){
    var othis = $(this);
    
    if(othis.parent().hasClass('layui-nav-item') && !container.hasClass(SIDE_SHRINK)) return;
    
    var tips = othis.attr('lay-tips')
    ,offset = othis.attr('lay-offset') 
    ,direction = othis.attr('lay-direction')
    ,index = layer.tips(tips, this, {
      tips: direction || 1
      ,time: -1
      ,success: function(layero, index){
        if(offset){
          layero.css('margin-left', offset + 'px');
        }
      }
    });
    othis.data('index', index);
  }).on('mouseleave', '*[lay-tips]', function(){
    layer.close($(this).data('index'));
  });
  

  var resizeSystem = layui.data.resizeSystem = function(){
    //layer.close(events.note.index);
    layer.closeAll('tips');
    
    if(!resizeSystem.lock){
      setTimeout(function(){
        admin.sideFlexible(admin.screen() < 2 ? '' : 'spread');
        delete resizeSystem.lock;
      }, 100);
    }
    
    resizeSystem.lock = true;
  }
  $win.on('resize', layui.data.resizeSystem);

  exports('admin', admin);
});