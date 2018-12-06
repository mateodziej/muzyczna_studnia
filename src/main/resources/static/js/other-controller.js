(function () {

    var app = angular.module("ticketmajster");

    var OtherController = function ($scope, $rootScope, $location, $http, $timeout) {

        $rootScope.subpage = 1;

        var onStart = function () {
            let delay = 1500;
            setupLoader(delay);
            $timeout(getEventsOnStart, delay);
        }

        var setupLoader = function(delay){
            if($rootScope.musicDirty === true){
                enableLoader();
                $timeout(disableLoader, delay);
            }
        }

        var enableLoader = function(){
            $scope.loader = true;
            console.log("enable loader");
        }

        var disableLoader = function(){
            $scope.loader = false;
            console.log("disable loader");
        }

        var getEventsOnStart = function () {
            if ($rootScope.otherDirty !== true) return;

            $rootScope.otherEventsWall.length = 0;
            //$rootScope.otherEventsLiked.length = 0;
            //$rootScope.otherEventsDisliked.length = 0;

            let wallUrl = "http://localhost:8080/api/events/other/wall?size=" + $rootScope.requestPageSize + "&page=0";

            console.log("getotherEvents() wall url -> " + wallUrl);

            $http.get(wallUrl)
                .then(onWallSuccess);
            if ($rootScope.otherFirstStart === true) {
                let likedUrl = "http://localhost:8080/api/events/other/liked?size=" + $rootScope.requestPageSize + "&page=0";

                console.log("getotherEvents() wall url -> " + likedUrl);

                $http.get(likedUrl)
                    .then(onLikedSuccess);

                let dislikedUrl = "http://localhost:8080/api/events/other/disliked?size=" + $rootScope.requestPageSize + "&page=0";

                console.log("getotherEvents() wall url -> " + dislikedUrl);

                $http.get(dislikedUrl)
                    .then(onDislikedSuccess);

                let topUrl = "http://localhost:8080/api/events/popular/other/liked"

                $http.get(topUrl)
                    .then(onTopSuccess);

                $rootScope.otherFirstStart = false;
            }

            $rootScope.otherDirty = false;
        }

        var onTopSuccess = function (response) {
            $rootScope.otherEventsTop = response.data;
        }

        var onWallSuccess = function (response) {
            $rootScope.otherEventsWall = $rootScope.otherEventsWall.concat(response.data.content);
            $rootScope.otherEventsWallCurrentPageNumber = response.data.number;
            $rootScope.otherEventsWallTotalPages = response.data.totalPages;
        }

        var onLikedSuccess = function (response) {
            console.log("other liked response");
            console.log(response.data);
            $rootScope.otherEventsLiked = $rootScope.otherEventsLiked.concat(response.data.content);
            $rootScope.otherEventsLikedCurrentPageNumber = response.data.number;
            $rootScope.otherEventsLikedTotalPages = response.data.totalPages;
        }

        var onDislikedSuccess = function (response) {
            $rootScope.otherEventsDisliked = $rootScope.otherEventsDisliked.concat(response.data.content);
            $rootScope.otherEventsDislikedCurrentPageNumber = response.data.number;
            $rootScope.otherEventsDislikedTotalPages = response.data.totalPages;
        }

        //getEventsOnStart();
        onStart();

        var getWallNextPage = function () {
            console.log("other wall next page click");
            if ($rootScope.otherEventsWallTotalPages == 0) return;
            if ($rootScope.otherEventsWallTotalPages == $rootScope.otherEventsWallCurrentPageNumber + 1) return;

            let wallUrl = "http://localhost:8080/api/events/other/wall?size=" + $rootScope.requestPageSize + "&page=" + ($rootScope.otherEventsWallCurrentPageNumber + 1);

            $http.get(wallUrl)
                .then(onWallSuccess);
        }

        $scope.getWallNextPage = getWallNextPage;

        var getLikedNextPage = function () {
            console.log("other liked next page click");
            if ($rootScope.otherEventsLikedTotalPages == 0) return;
            if ($rootScope.otherEventsLikedTotalPages == $rootScope.otherEventsLikedCurrentPageNumber + 1) return;

            let likedUrl = "http://localhost:8080/api/events/other/liked?size=" + $rootScope.requestPageSize + "&page=" + ($rootScope.otherEventsLikedCurrentPageNumber + 1);

            $http.get(likedUrl)
                .then(onLikedSuccess);
        }

        $scope.getLikedNextPage = getLikedNextPage;

        var getDislikedNextPage = function () {
            console.log("other disliked next page click");
            if ($rootScope.otherEventsDislikedTotalPages == 0) return;
            if ($rootScope.otherEventsDislikedTotalPages == $rootScope.otherEventsDislikedCurrentPageNumber + 1) return;

            let dislikedUrl = "http://localhost:8080/api/events/other/disliked?size=" + $rootScope.requestPageSize + "&page=" + ($rootScope.otherEventsDislikedCurrentPageNumber + 1);

            $http.get(dislikedUrl)
                .then(onDislikedSuccess);
        }

        $scope.getDislikedNextPage = getDislikedNextPage;

        var addLiked = function (ticketmasterId) {
            console.log("addLiked start tmId " + ticketmasterId);
            let index = -1;
            for (i = 0; i < $rootScope.otherEventsWall.length; i++) {
                if ($rootScope.otherEventsWall[i].storedEvent.ticketmasterId === ticketmasterId) {
                    index = i;
                    break;
                }
            }
            console.log("addLiked index val " + index);
            if (index === -1) return;
            let items = $rootScope.otherEventsWall.splice(index, 1);
            $rootScope.otherEventsLiked.push(items[0]);
            console.log("liked item " + JSON.stringify(items[0]));
            let url = "http://localhost:8080/api/events/liked/" + items[0].storedEvent.ticketmasterId;
            var data = "";
            var config = {
                headers: {
                    'Content-Type': 'application/json;charset=utf-8;'
                }
            }
            $http.put(url, data, config)
                .then(onAddLikedSuccess);
        }

        var addLikedTop = function (ticketmasterId) {
            console.log("addLikedTop start tmId " + ticketmasterId);
            let index = -1;
            for (i = 0; i < $rootScope.otherEventsTop.length; i++) {
                if ($rootScope.otherEventsTop[i].storedEvent.ticketmasterId === ticketmasterId) {
                    index = i;
                    break;
                }
            }
            console.log("addLiked index val " + index);
            if (index === -1) return;
            let items = $rootScope.otherEventsTop.splice(index, 1);
            $rootScope.otherEventsLiked.push(items[0]);
            console.log("liked item " + JSON.stringify(items[0]));
            let url = "http://localhost:8080/api/events/liked/" + items[0].storedEvent.ticketmasterId;
            var data = "";
            var config = {
                headers: {
                    'Content-Type': 'application/json;charset=utf-8;'
                }
            }
            $http.put(url, data, config)
                .then(onAddLikedSuccess);
        }

        var addDislikedTop = function (ticketmasterId) {
            let index = -1;
            for (i = 0; i < $rootScope.otherEventsTop.length; i++) {
                if ($rootScope.otherEventsTop[i].storedEvent.ticketmasterId === ticketmasterId) {
                    index = i;
                    break;
                }
            }
            if (index === -1) return;
            let items = $rootScope.otherEventsTop.splice(index, 1);
            $rootScope.otherEventsDisliked.push(items[0]);
            console.log("disliked item " + JSON.stringify(items[0]));
            let url = "http://localhost:8080/api/events/disliked/" + items[0].storedEvent.ticketmasterId;
            var data = "";
            var config = {
                headers: {
                    'Content-Type': 'application/json;charset=utf-8;'
                }
            }
            $http.put(url, data, config)
                .then(onAddDislikedSuccess);
        }

        var onAddLikedSuccess = function () {
            console.log("wydarzenia - polubiono event");
        }

        $scope.addLiked = addLiked;

        var addDisliked = function (ticketmasterId) {
            let index = -1;
            for (i = 0; i < $rootScope.otherEventsWall.length; i++) {
                if ($rootScope.otherEventsWall[i].storedEvent.ticketmasterId === ticketmasterId) {
                    index = i;
                    break;
                }
            }
            if (index === -1) return;
            let items = $rootScope.otherEventsWall.splice(index, 1);
            $rootScope.otherEventsDisliked.push(items[0]);
            console.log("disliked item " + JSON.stringify(items[0]));
            let url = "http://localhost:8080/api/events/disliked/" + items[0].storedEvent.ticketmasterId;
            var data = "";
            var config = {
                headers: {
                    'Content-Type': 'application/json;charset=utf-8;'
                }
            }
            $http.put(url, data, config)
                .then(onAddDislikedSuccess);
        }

        var onAddDislikedSuccess = function () {
            console.log("wydarzenia - znielubiono event");
        }

        $scope.addDisliked = addDisliked;

        var addToWallFromDisliked = function (index) {
            let items = $rootScope.otherEventsDisliked.splice(index, 1);
            $rootScope.otherEventsWall.push(items[0]);
            console.log("wall item " + JSON.stringify(items[0]));
            let url = "http://localhost:8080/api/events/wall/" + items[0].ticketmasterId;
            var data = "";
            var config = {
                headers: {
                    'Content-Type': 'application/json;charset=utf-8;'
                }
            }
            $http.put(url, data, config)
                .then(onAddToWallFromDisliked);
        }

        var onAddToWallFromDisliked = function () {
            console.log("wydarzenia - odnielubiono event");
        }

        $scope.addToWallFromDisliked = addToWallFromDisliked;

        var addToWallFromLiked = function (index) {
            let items = $rootScope.otherEventsLiked.splice(index, 1);
            $rootScope.otherEventsWall.push(items[0]);
            console.log("wall item " + JSON.stringify(items[0]));
            let url = "http://localhost:8080/api/events/wall/" + items[0].ticketmasterId;
            var data = "";
            var config = {
                headers: {
                    'Content-Type': 'application/json;charset=utf-8;'
                }
            }
            $http.put(url, data, config)
                .then(onAddToWallFromLiked);
        }

        var onAddToWallFromLiked = function () {
            console.log("wydarzenia - odnielubiono event");
        }

        $scope.addToWallFromLiked = addToWallFromLiked;

        $scope.addLikedTop = addLikedTop;
        $scope.addDislikedTop = addDislikedTop;

    };

    app.controller("OtherController", OtherController);

}());