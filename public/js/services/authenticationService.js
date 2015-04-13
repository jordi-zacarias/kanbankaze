"use strict";

var authenticationServiceModule = angular.module('service.authentication', []);

authenticationServiceModule.factory('authenticationService', ['$http', '$q', 'appSettings', 'storageKeys', 'localStorageService', function (
    $http, $q, appSettings, storageKeys, localStorageService) {

        var post = function (clientId, data) {
            var deferred = $q.defer();

            $http
                .post(appSettings.apiAuthenticationUri, data, {headers: {"Content-Type": "application/x-www-form-urlencoded"}})
                .success(function (response, status) {
                    if (status !== 200) {
                        deferred.reject(status);
                    }
                    else {
                        var expiresAt = new Date();
                        expiresAt.setSeconds(expiresAt.getSeconds() + response.expires_in);

                        var authData = {
                            clientId: clientId,
                            tokenType: response.token_type,
                            accessToken: response.access_token,
                            refreshToken: response.refresh_token,
                            expiresAt: expiresAt,
                            currentlyRefreshing: false
                        };

                        localStorageService.set(storageKeys.authenticationKey, authData);

                        deferred.resolve(response);
                    }
                })
                .error(function (error, status) {
                    console.debug("Login error: " + error + " | status: " + status);
                    deferred.reject(status);
                });

            return deferred.promise;
        };
        
        return{
            login: function(credentials){

                var data = "client_id=web-application&client_secret=secret&grant_type=password&username=" + credentials.username + "&password=" + credentials.password;
                return post(credentials.username, data);
            },
            
            refresh: function(){
                var authData = localStorageService.get(storageKeys.authenticationKey);
                if (authData != null){
                    var data = "client_id=" + authData.clientId + "&grant_type=refresh_token&refresh_token=" + authData.refreshToken;
                    return post(authData.clientId, data);
                }
            }
        };

    }]);


