'use strict';

angular.module('Register')

.factory('RegisterService',
	['Base64', '$http', '$cookieStore', '$rootScope', '$timeout',
	function (Base64, $http, $cookieStore, $rootScope, $timeout) {
		var service = {};

		service.Register = function(username, password, email, callback) {
			$http.post('http://localhost:9000/registerclient', { username: username, password: password, email: email})
                .success(function (response) {
                    callback(response);
                });
		};

		return service;
	}]);