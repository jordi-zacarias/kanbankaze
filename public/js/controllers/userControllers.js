"use strict";

var userControllers = angular.module("controllers.user", []);


userControllers.controller("teamListCtrl",
    ['$scope', 'userService', function($scope, userService) {

    $scope.users = [];

    $scope.close = function(){
        var asideClass = "aside-in";
        var container = $("#container");

        container.removeClass( asideClass );
    }

    userService.list().then( function (userlist){
        $scope.users = userlist.items;
    });

}]);

userControllers.controller("teamManagementCtrl",
    ['$scope', function($scope){

    $scope.activeTab = 0;

    $scope.setActiveTab = function(index){
        $scope.activeTab = index;
    }

    $scope.tabs = [
        {
            name: "Teams",
            disabled: false,
            template: "assets/js/views/admin/teams.tpl.html"
        },
        {
            name: "Users",
            disabled: false,
            template: "assets/js/views/admin/users.tpl.html"
        }
    ];
}]);