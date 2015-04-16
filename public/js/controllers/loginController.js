"use strict";

var loginControllers = angular.module("controllers.login", []);


loginControllers.controller("loginCtrl",
    ['$scope', '$rootScope', '$location', 'authenticationService', 'authService', 'localStorageService', 'storageKeys', 'appSettings',
    function($scope, $rootScope, $location, authenticationService, authService, localStorageService, storageKeys, appSettings) {
       
    $scope.error = false;
    $scope.selectedUser = 0;
    $scope.validatedStore = false;
    $scope.users = [];
       
    $scope.credentials = {
        username: "",
        password: ""
    };
    
    $scope.setUser = function (id, username){
        $scope.selectedUser = id;
        $scope.credentials.username = username;
    };
    
    $scope.login = function(redirect){

        authenticationService.login($scope.credentials)
            .then(
                function (response) { 
                    $scope.error = false;
//                    userService.profile().then(function(resource) {
//                        var profile = {
//                            id: resource.id,
//                            firstName: resource.firstName,
//                            lastName: resource.lastName,
//                            roles: resource.roles
//                        };
//
//                        localStorageService.set(storageKeys.profileKey, profile);
//                        $rootScope.$broadcast('event:profile-updated', profile);
//                    });
                    
                    authService.loginConfirmed($scope.credentials.username, function(config) {
                        
                        if(!config.headers["Authorization"]) {
                            var authData = localStorageService.get(storageKeys.authenticationKey);
                            console.log('Bearer not on original request; adding it [' + authData.accessToken + ']');
                            config.headers["Authorization"] = "Bearer " + authData.accessToken;
                        }
                       
                        return config;
                    });
                    
                    if (redirect) window.location = appSettings.appBaseUri;
                },
                function (status) { 
                    console.log("status: " + status);
                    $scope.error = true;
                }         
            );      
    }
           
}]);


loginControllers.controller("logoutCtrl",['$scope', '$location', 'localStorageService',
    function($scope, $location, localStorageService) {
    
    $scope.logout = function(){
    alert("hee");
        localStorageService.clearAll();
        $location.path("/");
    };
}]);


loginControllers.controller("loginProfileCtrl",['$scope', '$rootScope', '$location', 'localStorageService', 'storageKeys', 'filterFilter',
    function($scope, $rootScope, $location, localStorageService, storageKeys, filterFilter) {
    
    $scope.profile = {
        id: 0,
        firstName: "",
        lastName: "",
        roles: []
    };
    var profile = localStorageService.get(storageKeys.profileKey);
    
    if (profile) $scope.profile = profile;
    
    $rootScope.$on('event:profile-updated', function(event, data) {
        $scope.profile = data;
    });
    
    $scope.hasUserRole = function(role){
        var found = filterFilter($scope.profile.roles, role);
        return (found.length > 0);
    };
}]);