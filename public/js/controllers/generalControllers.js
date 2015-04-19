"use strict";

var generalControllers = angular.module("controllers.general", []);


generalControllers.controller("navbarCtrl",
    ['$scope', '$rootScope', function($scope, $rootScope) {

   $scope.toggleAside = function(){

        var asideClass = "aside-in";
        var container = $("#container");

        if (container.hasClass( asideClass )) container.removeClass( asideClass );
        else container.addClass( asideClass );
   };

}]);