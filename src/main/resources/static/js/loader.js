(function(){
    $(document).ready(function(){
        $(window).scroll(updateLoader);
        updateLoader();
    });

    var updateLoader = function () {
        var h = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);
        if ($(document).scrollTop() > h - 70) {
            $(".courtain").addClass("courtain-fixed");
        } else {
            $(".courtain").removeClass("courtain-fixed");
        }
    }
})();