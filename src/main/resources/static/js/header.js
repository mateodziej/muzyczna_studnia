(function () {
    $(document).ready(function () {
        $(window).scroll(updateHeader);

        $(".logo-item").click(function (e) {
            console.log(e);
            $(".logo-item").removeClass("header-item-active");
            $(this).addClass("header-item-active");
        });

        updateHeader();
    });

    var updateHeader = function () {
        if ($(document).scrollTop() > 200) {
            $("#header").addClass("header-min");
            $("#logo").addClass("logo-min");
            $(".logo-item").addClass("logo-item-min");
        } else {
            $("#header").removeClass("header-min");
            $("#logo").removeClass("logo-min");
            $(".logo-item").removeClass("logo-item-min");
        }
    }

}());