"use strict";

var boardServices = angular.module('services.boards', []);

boardServices.factory('boardService', ['$http', 'halClient', 'appSettings', function($http, halClient, appSettings){

    var getUrl = appSettings.apiBaseUri + "/board/";

    return {
        list: function (filter){
            return halClient.$get(getUrl);
        },

        get: function(id){
            return halClient.$get(getUrl + id);
        }
    }
}]);
