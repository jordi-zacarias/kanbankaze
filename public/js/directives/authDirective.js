"use strict";

app.directive('showLogin', ['$rootScope', '$route', function($rootScope, $route) {

    return {
        restrict: 'C',
        link: function(scope, element, attrs) {              
            var login = $(element).find('#login-holder');
            var loginError = $(element).find('#login-error');

            var username = $(element).find('#username');
            var password = $(element).find('#password');
            var content = $(element).find('#container');
 
            login.hide();
            loginError.hide();
            
            scope.$on('event:auth-loginRequired', function(event, data) {
                console.log('showing login form');
                content.hide();            
                localStorage.clear();
                login.show();
            });
            scope.$on('event:auth-loginFailed', function(event, data) {
                console.log('showing login error message');
                
                loginError.show();
                password.val('');
                password.focus();                
            });
            scope.$on('event:auth-loginConfirmed', function(event, data) {
                console.log('hiding login form');
                                    
                content.show();
                login.hide();
                
                username.val('');
                password.val('');
            });
            scope.$on('event:auth-not-loginRequired', function(event, data) {
            	content.show();
                login.hide();
                
                username.val('');
                password.val('');
            });
        }
    }
}]);



