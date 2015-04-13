"use strict";

var listDirectives = angular.module('directives.list', []);

listDirectives.directive('pagination', function (){
   
    return{
        restrict: 'E',
        scope: {
            totalItems: '@',
            currentPage: '@',
            previousText: '=previousText',
            nextText: '=nextText',
            firstText: '=firstText',
            lastText: '=lastText',
            pageSize: '@',
            pageChanged: '&'
        },
        templateUrl: 'js/directives/templates/pagination.tpl.html',
        link: function (scope, element, attrs) {   
            
            scope.startingAt = 0;
            var pageSize = 10;
            
            try{
                pageSize = parseInt(attrs.pageSize);
            }catch(err){
            }
            
            scope.totalPageSize = pageSize;
            
            if (attrs.currentPage){
                scope.startingAt = Math.floor(attrs.currentPage / pageSize) * pageSize;
            }
            
            scope.range = function(n) {
                return new Array(n);
            };
        },
        controller: ['$scope', function($scope){
            $scope.triggerPageChanged = function(page){
                
                $scope.currentPage = page;
                $scope.startingAt = Math.floor($scope.currentPage / $scope.totalPageSize) * $scope.totalPageSize;

                $scope.pageChanged()(page);
            }
        }]
    };
 
});

