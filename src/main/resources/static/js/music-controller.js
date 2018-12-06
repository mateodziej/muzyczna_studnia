(function () {

    var app = angular.module("ticketmajster");

    var MusicController = function ($scope, $rootScope, $location, $http, $timeout) {

        $rootScope.subpage = 0;

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
            if ($rootScope.musicDirty !== true) return;

            $rootScope.musicEventsWall.length = 0;
            //$rootScope.musicEventsLiked.length = 0;
            //$rootScope.musicEventsDisliked.length = 0;

            let wallUrl = "http://localhost:8080/api/events/music/wall?size=" + $rootScope.requestPageSize + "&page=0";

            console.log("getMusicEventsOnStart() wall url -> " + wallUrl);

            $http.get(wallUrl)
                .then(onWallSuccess);
            if ($rootScope.musicFirstStart === true) {
                let likedUrl = "http://localhost:8080/api/events/music/liked?size=" + $rootScope.requestPageSize + "&page=0";

                console.log("getMusicEvents() wall url -> " + likedUrl);

                $http.get(likedUrl)
                    .then(onLikedSuccess);

                let dislikedUrl = "http://localhost:8080/api/events/music/disliked?size=" + $rootScope.requestPageSize + "&page=0";

                console.log("getMusicEvents() wall url -> " + dislikedUrl);

                $http.get(dislikedUrl)
                    .then(onDislikedSuccess);

                let topUrl = "http://localhost:8080/api/events/popular/music/liked"

                $http.get(topUrl)
                    .then(onTopSuccess);

                $rootScope.musicFirstStart = false;


            }

            $rootScope.musicDirty = false;
        }

        var onWallSuccess = function (response) {
            console.log("music wall response data");
            console.log(response.data);
            $rootScope.musicEventsWall = $rootScope.musicEventsWall.concat(response.data.content);
            $rootScope.musicEventsWallCurrentPageNumber = response.data.number;
            $rootScope.musicEventsWallTotalPages = response.data.totalPages;
        }

        var onLikedSuccess = function (response) {
            $rootScope.musicEventsLiked = $rootScope.musicEventsLiked.concat(response.data.content);
            $rootScope.musicEventsLikedCurrentPageNumber = response.data.number;
            $rootScope.musicEventsLikedTotalPages = response.data.totalPages;
        }

        var onDislikedSuccess = function (response) {
            $rootScope.musicEventsDisliked = $rootScope.musicEventsDisliked.concat(response.data.content);
            $rootScope.musicEventsDislikedCurrentPageNumber = response.data.number;
            $rootScope.musicEventsDislikedTotalPages = response.data.totalPages;
        }

        var onTopSuccess = function (response) {
            $rootScope.musicEventsTop = response.data;
        }

        //getEventsOnStart();
        onStart();

        var getWallNextPage = function () {
            console.log("music wall next page click");
            if ($rootScope.musicEventsWallTotalPages == 0) return;
            if ($rootScope.musicEventsWallTotalPages == $rootScope.musicEventsWallCurrentPageNumber + 1) return;

            let wallUrl = "http://localhost:8080/api/events/music/wall?size=" + $rootScope.requestPageSize + "&page=" + ($rootScope.musicEventsWallCurrentPageNumber + 1);
            console.log("music wall next page url " + wallUrl);
            $http.get(wallUrl)
                .then(onWallSuccess);
        }

        $scope.getWallNextPage = getWallNextPage;

        var getLikedNextPage = function () {
            console.log("music liked next page click");
            if ($rootScope.musicEventsLikedTotalPages == 0) return;
            if ($rootScope.musicEventsLikedTotalPages == $rootScope.musicEventsLikedCurrentPageNumber + 1) return;

            let likedUrl = "http://localhost:8080/api/events/music/liked?size=" + $rootScope.requestPageSize + "&page=" + ($rootScope.musicEventsLikedCurrentPageNumber + 1);

            $http.get(likedUrl)
                .then(onLikedSuccess);
        }

        $scope.getLikedNextPage = getLikedNextPage;

        var getDislikedNextPage = function () {
            console.log("music disliked next page click");
            if ($rootScope.musicEventsDislikedTotalPages == 0) return;
            if ($rootScope.musicEventsDislikedTotalPages == $rootScope.musicEventsDislikedCurrentPageNumber + 1) return;

            let dislikedUrl = "http://localhost:8080/api/events/music/disliked?size=" + $rootScope.requestPageSize + "&page=" + ($rootScope.musicEventsDislikedCurrentPageNumber + 1);

            $http.get(dislikedUrl)
                .then(onDislikedSuccess);
        }

        $scope.getDislikedNextPage = getDislikedNextPage;

        var addLiked = function (ticketmasterId) {
            console.log("addLiked start tmId " + ticketmasterId);
            let index = -1;
            for (i = 0; i < $rootScope.musicEventsWall.length; i++) {
                if ($rootScope.musicEventsWall[i].storedEvent.ticketmasterId === ticketmasterId) {
                    index = i;
                    break;
                }
            }
            console.log("addLiked index val " + index);
            if (index === -1) return;
            let items = $rootScope.musicEventsWall.splice(index, 1);
            $rootScope.musicEventsLiked.push(items[0]);
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
            for (i = 0; i < $rootScope.musicEventsTop.length; i++) {
                if ($rootScope.musicEventsTop[i].storedEvent.ticketmasterId === ticketmasterId) {
                    index = i;
                    break;
                }
            }
            console.log("addLiked index val " + index);
            if (index === -1) return;
            let items = $rootScope.musicEventsTop.splice(index, 1);
            $rootScope.musicEventsLiked.push(items[0]);
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

        var onAddLikedSuccess = function () {
            console.log("muzyka - polubiono event");
        }

        $scope.addLiked = addLiked;

        var addDisliked = function (ticketmasterId) {
            let index = -1;
            for (i = 0; i < $rootScope.musicEventsWall.length; i++) {
                if ($rootScope.musicEventsWall[i].storedEvent.ticketmasterId === ticketmasterId) {
                    index = i;
                    break;
                }
            }
            if (index === -1) return;
            let items = $rootScope.musicEventsWall.splice(index, 1);
            $rootScope.musicEventsDisliked.push(items[0]);
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

        var addDislikedTop = function (ticketmasterId) {
            let index = -1;
            for (i = 0; i < $rootScope.musicEventsTop.length; i++) {
                if ($rootScope.musicEventsTop[i].storedEvent.ticketmasterId === ticketmasterId) {
                    index = i;
                    break;
                }
            }
            if (index === -1) return;
            let items = $rootScope.musicEventsTop.splice(index, 1);
            $rootScope.musicEventsDisliked.push(items[0]);
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
            console.log("muzyka - znielubiono event");
        }

        $scope.addDisliked = addDisliked;

        var addToWallFromDisliked = function (index) {
            let items = $rootScope.musicEventsDisliked.splice(index, 1);
            $rootScope.musicEventsWall.push(items[0]);
            console.log("wall item " + JSON.stringify(items[0]));
            let url = "http://localhost:8080/api/events/wall/" + items[0].storedEvent.ticketmasterId;
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
            console.log("muzyka - odnielubiono event");
        }

        $scope.addToWallFromDisliked = addToWallFromDisliked;

        var addToWallFromLiked = function (index) {
            let items = $rootScope.musicEventsLiked.splice(index, 1);
            $rootScope.musicEventsWall.push(items[0]);
            console.log("wall item " + JSON.stringify(items[0]));
            let url = "http://localhost:8080/api/events/wall/" + items[0].storedEvent.ticketmasterId;
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
            console.log("muzyka - odnielubiono event");
        }

        $scope.addToWallFromLiked = addToWallFromLiked;

        $scope.addLikedTop = addLikedTop;
        $scope.addDislikedTop = addDislikedTop;

    };

    app.controller("MusicController", MusicController);

}());