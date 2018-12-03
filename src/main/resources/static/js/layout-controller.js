(function () {

    var app = angular.module("ticketmajster");

    var LayoutController = function ($scope, $rootScope, $location) {

        $rootScope.subpage = 0;

        $rootScope.$watch('subpage', function () {
            $(".logo-item").removeClass("header-item-active");
            switch ($rootScope.subpage) {
                case 0:
                    $("#h-btn-0").addClass("header-item-active");
                    break;
                case 1:
                    $("#h-btn-1").addClass("header-item-active");
                    break;
                case 2:
                    $("#h-btn-2").addClass("header-item-active");
                    break;
            }
            $(this).addClass("header-item-active");
        });

    };

    app.controller("LayoutController", LayoutController);

}());