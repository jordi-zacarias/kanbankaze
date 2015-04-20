"use strict";

var app = angular.module('inscrumApp', [
    'ngRoute',
    'LocalStorageModule',
    'angular-hal',
    'http-auth-interceptor',
    'ng-sortable',
    'ngDraggable',
    'mgcrea.ngStrap',
    
    'inscrum.settings',

    'services.boards',
    'services.tasks',
    'service.authentication',

    'directives.common',
    'directives.list',

    'controllers.general',
    'controllers.board',
    'controllers.login',
    'controllers.task',
    'controllers.user'
]);

app.config(['$routeProvider', '$locationProvider', '$httpProvider', 'localStorageServiceProvider', function($routeProvider, $locationProvider, $httpProvider, localStorageServiceProvider) {
           
    $httpProvider.interceptors.push('authService');
       
    localStorageServiceProvider
        .setPrefix('inscrumApp')
        .setStorageType('localStorage');    
   

    $routeProvider.when('/board', {
        templateUrl:'assets/js/views/board.tpl.html'
    });
    
    $routeProvider.otherwise({
       redirectTo: '/board'
    });    

}]);

