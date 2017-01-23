'use strict';

angular.module('Producer')

.factory('ProducerService',
	['$http', '$rootScope', '$cookieStore',
	function ($http, $rootScope, $cookieStore) {
		var service = {};

		service.getTopics = function(callback) {

			$rootScope.globals = $cookieStore.get('globals') || {};

			$http.get('http://localhost:9000/gettopics?username=' + $rootScope.globals.currentUser.username)
				.success(function(response) {
					callback(response);
				});
		};

		service.deleteTopic = function(topicName, callback) {
			$http.post('http://localhost:9000/deletetopic', { topicName: topicName, username: $rootScope.globals.currentUser.username })
								.success(function (response) {
										callback(response);
								});

		};

		service.addTopic = function(topicName, callback) {
			$http.post('http://localhost:9000/addtopic', { topicName: topicName, username: $rootScope.globals.currentUser.username })
								.success(function (response) {
										callback(response);
								});
		};

		return service;

	}]);
