var MF = function() {
    var handleCanvasSize = function() {
        var viewHeight = $(window).height();
        var viewWidth = $(window).width();
        var toolbarHeight = $('.toolbar').outerHeight(true);
        var sidebarWidth = $('#messageEndpointList').outerWidth(true);
// setTimeout(function(){alert( $('.page-content')[0].offsetWidth- $('.page-content')[0].scrollWidth)},1000)
        var height = viewHeight - toolbarHeight;
        var width = viewWidth - sidebarWidth;
        $('.page-content').css({'height':height,'width':width});
    }
    var handleSidebar = function() {
        var menu = $('.page-sidebar-menu');        
        if (menu.parent('.slimScrollDiv').size() === 1) { // destroy existing instance before updating the height
            menu.slimScroll({
                destroy: true
            });
            menu.removeAttr('style');
        }
         menu.slimScroll({
                size: '7px',                
                opacity: .3,
                height: $(window).height() - $('.toolbar').outerHeight(true),
                allowPageScroll: false,
                disableFadeOut: false
            });

    }
    return {
        //main function
        init: function() {
            handleCanvasSize();
            handleSidebar();
            $(window).resize(function(event) {
                handleCanvasSize();
                handleSidebar();
            });
        },


    };

}();
