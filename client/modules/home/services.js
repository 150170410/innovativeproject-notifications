'use strict';

angular.module('Home')

.factory('HomeService',
	['$http', '$rootScope', '$cookieStore',
	function ($http, $rootScope, $cookieStore) {
		var service = {};

		service.GetMessage = function(callback) {

			$rootScope.globals = $cookieStore.get('globals') || {};

			$http.get('http://localhost:9000/getmessage?username=' + $rootScope.globals.currentUser.username)
				.success(function(response) {
					callback(response);
				});
		};

		return service;

	}]); 
