(function () {
    $(document).ready(function () {
        $(window).scroll(updateBtns);

        $('body').on('click', '#wall-f-btn', function () {
            console.log("wall-f-btn click()");
            $("html, body").stop(true, false);
            $('html, body').animate({
                scrollTop: $("#go-to-wall").offset().top - 66
            }, 800);
        });

        $('body').on('click', '#liked-f-btn', function () {
            console.log("liked-f-btn click()");
            $("html, body").stop(true, false);
            $('html, body').animate({
                scrollTop: $("#go-to-liked").offset().top - 66
            }, 800);
        });

        $('body').on('click', '#disliked-f-btn', function () {
            console.log("disliked-f-btn click()");
            $("html, body").stop(true, false);
            $('html, body').animate({
                scrollTop: $("#go-to-disliked").offset().top - 66
            }, 800);
        });

        $(window).bind("mousewheel", function () {
            $("html, body").stop(true, false);
        });
    });

    var updateBtns = function () {
        var h = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);
        if ($(document).scrollTop() > h - 70) {
            $(".btns-fixed").removeClass("btns-hide");
        } else {
            $(".btns-fixed").addClass("btns-hide");
        }
    }
})();