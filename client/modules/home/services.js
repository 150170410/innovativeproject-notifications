'use strict';

angular.module('Home')

.factory('HomeService',
	['$http', '$rootScope', '$cookieStore',
	function ($http, $rootScope, $cookieStore) {
		var service = {};

		service.GetUsername = function() {
			$rootScope.globals = $cookieStore.get('globals') || {};
			return $rootScope.globals.currentUser.username;
		};

		service.GetMessage = function(callback) {

			$rootScope.globals = $cookieStore.get('globals') || {};

			$http.get('http://localhost:9000/message?username=' + $rootScope.globals.currentUser.username)
				.success(function(response) {
					callback(response);
				});
		};

		service.SetAsRead = function(id, callback) {
			$rootScope.globals = $cookieStore.get('globals') || {};

			$http.post('http://localhost:9000/removemessage', { username: $rootScope.globals.currentUser.username, id: id })
                .success(function (response) {
                    callback(response);
                });
		};

		service.SetAllAsRead = function(callback) {
			$rootScope.globals = $cookieStore.get('globals') || {};

			$http.post('http://localhost:9000/removeallmessages', { username: $rootScope.globals.currentUser.username })
				.success(function (response) {
					callback(response);
				});
		};

		return service;

	}]); 
