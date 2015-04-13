"use strict";

var settings = angular.module("inscrum.settings", []);

settings.constant("appSettings", {
    apiBaseUri: "/api",
    apiAuthenticationUri: "/api/oauth/token",
    appBaseUri: '/',
});

settings.constant("storageKeys", {
    authenticationKey: "USER_AUTHENTICATION",
    profileKey: "PROFILE_DATA",
    storeKey: "STORE_DATA"
});