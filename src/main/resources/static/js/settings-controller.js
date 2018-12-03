(function () {

    var app = angular.module("ticketmajster");

    var SettingsController = function ($scope, $rootScope, $location, $http) {

        $rootScope.subpage = 2;

        var data = "";
        var config = {
            headers: {
                'Content-Type': 'application/json;charset=utf-8;'
            }
        };

        var getAuth = function () {
            let authUrl = "http://localhost:8080/api/auth";
            console.log("getAuth");
            $http.get(authUrl)
                .then(onGetAuthSuccess, onGetAuthFailure);

            $.get("/api/auth", function (data, status) {
                console.log(data);
                $scope.auth = data;
            });
        };

        getAuth();

        var getProfileData = function () {
            let artistsUrl = "http://localhost:8080/api/artists";
            $http.get(artistsUrl)
                .then(onGetArtistsSuccess);

            let tagsUrl = "http://localhost:8080/api/tags";
            $http.get(tagsUrl)
                .then(onGetTagsSuccess);

            let userUrl = "http://localhost:8080/api/users";
            $http.get(userUrl)
                .then(onGetUserSuccess);

        };

        var onGetArtistsSuccess = function (response) {
            $scope.artists = response.data;
        };

        var onGetAuthSuccess = function (response) {
            $scope.auth = response.data;
            console.log("auth response" + response.data);
        };

        var onGetAuthFailure = function (response) {
            console.log("Get auth error " + JSON.stringify(response));
        };

        var onGetTagsSuccess = function (response) {
            $scope.tags = response.data;
        };

        var onGetUserSuccess = function (response) {
            $scope.user = response.data;
        };

        var addArtist = function () {
            try {
                $scope.addArtistName = $scope.addArtistName.trim();
            } catch (e) {
                console.log("Błąd add Artist trim");
            }

            if ($scope.addArtistName === "") return;
            let addArtistUrl = "http://localhost:8080/api/artists/" + $scope.addArtistName.toLowerCase();
            $http.post(addArtistUrl, data, config)
                .then(onAddArtistSuccess);
        };

        var onAddArtistSuccess = function () {
            makeDirtyAndSearch();
            $scope.addArtistName = "";
            getProfileData();
        };

        $scope.addArtist = addArtist;

        var removeArtist = function (index) {
            let removedArtists = $scope.artists.splice(index, 1);
            let url = "http://localhost:8080/api/artists/" + removedArtists[0].name;
            $http.delete(url, data, config)
                .then(onRemoveArtistSuccess);
        };

        var onRemoveArtistSuccess = function (response) {
            makeDirtyAndSearchNew();
            getProfileData();
        };

        $scope.removeArtist = removeArtist;

        var addTag = function () {
            try {
                $scope.addTagName = $scope.addTagName.trim();
            } catch (e) {
                console.log("Błąd add Tag trim");
            }

            if ($scope.addTagName === "") return;
            let addTagUrl = "http://localhost:8080/api/tags/" + $scope.addTagName.toLowerCase();
            $http.post(addTagUrl, data, config)
                .then(onAddTagSuccess);
        };

        var onAddTagSuccess = function () {
            makeDirtyAndSearch();
            $scope.addTagName = "";
            getProfileData();
        };

        $scope.addTag = addTag;

        var removeTag = function (index) {
            let removedTags = $scope.tags.splice(index, 1);
            let url = "http://localhost:8080/api/tags/" + removedTags[0].name;
            $http.delete(url, data, config)
                .then(onRemoveTagSuccess);
        };

        var onRemoveTagSuccess = function (response) {
            makeDirtyAndSearchNew();
            getProfileData();
        };

        $scope.removeTag = removeTag;

        var updateCity = function () {
            closeCityModal();
            if ($scope.updateCityName.trim() === "") return;
            let cityName = $scope.updateCityName.trim();
            let url = "http://localhost:8080/api/users";
            let userData = {
                city: cityName
            };

            $http.put(url, userData, config)
                .then(onUpdateCitySuccess);
        };

        var closeCityModal = function () {
            //document.getElementById("city-modal").style.visibility = "hidden";
            $("#city-modal").hide(200);
        };

        var openCityModal = function () {
            //document.getElementById("city-modal").style.visibility = "visible";
            $("#city-modal").show(200);
        };

        var closeLastfmModal = function () {
            //document.getElementById("city-modal").style.visibility = "hidden";
            $("#lastfm-modal").hide(200);
        };

        var openLastfmModal = function () {
            //document.getElementById("city-modal").style.visibility = "visible";
            $("#lastfm-modal").show(200);
        };

        var onUpdateCitySuccess = function () {
            makeDirtyAndSearchNew();
            $scope.updateCityName = "";
            getProfileData();
        };

        $scope.updateCity = updateCity;

        var updateLastFm = function () {
            closeLastfmModal();
            if ($scope.updateLastFmName.trim() === "") return;
            let lastFmName = $scope.updateLastFmName.trim();
            let url = "http://localhost:8080/api/users";
            let userData = {
                lastFmUsername: lastFmName
            };

            $http.put(url, userData, config)
                .then(onUpdateLastFmSuccess);
        };

        var onUpdateLastFmSuccess = function () {
            makeDirtyAndSearch();
            $scope.updateLastFmName = "";
            getProfileData();
        };

        $scope.updateLastFm = updateLastFm;
        $scope.openCityModal = openCityModal;
        $scope.closeCityModal = closeCityModal;

        $scope.openLastfmModal = openLastfmModal;
        $scope.closeLastfmModal = closeLastfmModal;

        var makeDirtyAndSearch = function () {
            $rootScope.search();
            $rootScope.musicDirty = true;
            $rootScope.otherDirty = true;
        };

        var makeDirtyAndSearchNew = function () {
            $rootScope.searchNew();
            $rootScope.musicDirty = true;
            $rootScope.otherDirty = true;
        };

        getProfileData();
        closeCityModal();
        closeLastfmModal();

    };

    app.controller("SettingsController", SettingsController);

}());