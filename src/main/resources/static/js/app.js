(function () {

    var app = angular.module("ticketmajster", ["ngRoute"]);

    app.config(function ($routeProvider) {
        $routeProvider
            .when("/koncerty", {
                templateUrl: "/templates/_music.html",
                controller: "MusicController"
            })
            .when("/wydarzenia", {
                templateUrl: "/templates/_other.html",
                controller: "OtherController"
            })
            .when("/profil", {
                templateUrl: "/templates/_profile.html",
                controller: "SettingsController"
            })
            .otherwise({ redirectTo: "/koncerty" });
    });

    app.directive('onEnter', function () {
        return function (scope, element, attrs) {
            element.bind("keydown keypress", function (event) {
                if (event.which === 13) {
                    scope.$apply(function () {
                        scope.$eval(attrs.onEnter);
                    });

                    event.preventDefault();
                }
            });
        };
    });

    app.filter('imgMinWidth', function () {
        return function (items, minWidth) {
            var filtered = [];

            if (minWidth === undefined) {
                return items;
            }

            angular.forEach(items, function (item) {
                if (minWidth <= item.width) {
                    filtered.push(item);
                }
            });

            return filtered;
        };
    });

    app.filter('retUniqKeywords', function () {
        return function (items, sampleKeyword) {
            var filteredKeywords = [];
            angular.forEach(items, function (item) {
                let add = true;
                angular.forEach(filteredKeywords, function (keyword) {
                    if (keyword === item.keyword) {
                        add = false;
                    }
                });
                if (add === true) filteredKeywords.push(item.keyword);
            });

            return filteredKeywords;
        }
    });

    app.filter('byKeyword', function () {
        return function (items, keyword) {
            var filtered = [];
            angular.forEach(items, function (item) {
                if (item.keyword === keyword) {
                    filtered.push(item);
                }
            });
            return filtered;
        }
    });

    app.run(function ($rootScope, $http) {

        var getAuth = function () {
            $http.get("http://localhost:8080/api/auth")
                .then(onAuthSuccess, onaAuthFailure);
        }

        var onAuthSuccess = function (response) {
            $rootScope.username = response.data.name;
        }

        var onaAuthFailure = function () {
            $rootScope.authError = true;
        }

        $rootScope.requestPageSize = 5;
        $rootScope.musicEventsWallCurrentPageNumber = 0;
        $rootScope.musicEventsWallTotalPages = 0;

        $rootScope.musicEventsLikedCurrentPageNumber = 0;
        $rootScope.musicEventsLikedTotalPages = 0;

        $rootScope.musicEventsDislikedCurrentPageNumber = 0;
        $rootScope.musicEventsDislikedTotalPages = 0;

        $rootScope.musicEventsWall = [];
        $rootScope.musicEventsLiked = [];
        $rootScope.musicEventsDisliked = [];
        $rootScope.musicEventsTop = [];
        $rootScope.otherEventsTop = [];

        $rootScope.otherEventsWallCurrentPageNumber = 0;
        $rootScope.otherEventsWallTotalPages = 0;

        $rootScope.otherEventsLikedCurrentPageNumber = 0;
        $rootScope.otherEventsLikedTotalPages = 0;

        $rootScope.otherEventsDislikedCurrentPageNumber = 0;
        $rootScope.otherEventsDislikedTotalPages = 0;

        $rootScope.otherEventsWall = [];
        $rootScope.otherEventsLiked = [];
        $rootScope.otherEventsDisliked = [];

        $rootScope.musicDirty = true;
        $rootScope.otherDirty = true;

        var search = function () {
            $http.get("http://localhost:8080/api/search")
                .then(function () {
                    console.log("Search...");
                });
        };

        var searchNew = function () {
            $http.get("http://localhost:8080/api/search/new")
                .then(function () {
                    console.log("Search new...");
                });
        };

        $rootScope.search = search;
        $rootScope.searchNew = searchNew;
        $rootScope.musicFirstStart = true;
        $rootScope.otherFirstStart = true;

        search();
        getAuth();
    });

}());