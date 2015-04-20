"use strict";

var userControllers = angular.module("controllers.user", []);


userControllers.controller("teamListCtrl",
    ['$scope', function($scope) {

    $scope.users = [
        {
            id: 1,
            name: 'Scrum Master',
            avatar: '/assets/images/profile/scrumMaster_128x128.png',
            profile: 'Development',
        },
        {
            id: 2,
            name: 'Jordi Zacarias',
            avatar: '/assets/images/user.png',
            profile: 'Development',
        },
        {
            id: 3,
            name: 'Developer 1',
            avatar: '/assets/images/profile/dev_128x128.png',
            profile: 'Development',
        },
        {
            id: 4,
            name: 'QA Engineer',
            avatar: '/assets/images/profile/qa_128x128.png',
            profile: 'Development',
        },
        {
            id: 5,
            name: 'Product Owner',
            avatar: '/assets/images/profile/productOwner_128x128.png',
            profile: 'Product',
        },
    ];

    $scope.onDragComplete= function(data, evt){

    }
}]);