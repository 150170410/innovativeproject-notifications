'use strict';

angular.module('Home')

.factory('HomeService',
	['$http',
	function ($http) {
		var service = {};

		service.GetMessage = function(callback) {
			$http.get('http://localhost:9000/getmessage')
				.success(function(response) {
					callback(response);
				});
		};

		return service;

	}]); 
