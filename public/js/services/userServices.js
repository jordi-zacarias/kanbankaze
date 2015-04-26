"use strict";

var taskServices = angular.module('services.users', []);

taskServices.factory('userService', ['$http', 'halClient', 'appSettings', function($http, halClient, appSettings){

    var userUrl = appSettings.apiBaseUri + "/user";

    return {
        list: function (){
            return halClient.$get(userUrl);
        }
    }
}]);
