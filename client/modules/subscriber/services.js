'use strict';

angular.module('Subscriber')

.factory('SubscriberService',
	['$http', '$rootScope', '$cookieStore',
	function ($http, $rootScope, $cookieStore) {
		var service = {};

		service.getProducers = function(callback) {

			$rootScope.globals = $cookieStore.get('globals') || {};

			$http.get('http://localhost:9000/producers')
				.success(function(response) {
					callback(response);
				});
		};

		service.getSubscribedTopics = function(username, callback) {

			$rootScope.globals = $cookieStore.get('globals') || {};

			$http.get('http://localhost:9000/subscribedtopics?producer=' + username + '&subscriber=' + $rootScope.globals.currentUser.username)
				.success(function(response) {
					callback(response);
				});
		};

		service.getNotSubscribedTopics = function(username, callback) {

			$rootScope.globals = $cookieStore.get('globals') || {};

			$http.get('http://localhost:9000/notsubscribedtopics?producer=' + username + '&subscriber=' + $rootScope.globals.currentUser.username)
				.success(function(response) {
					callback(response);
				});
		};

		service.subscribe = function(topicName, callback) {
			$http.post('http://localhost:9000/subscribe', { topicName: topicName, username: $rootScope.globals.currentUser.username })
								.success(function (response) {
										callback(response);
								});

		};

		service.unsubscribe = function(topicName, callback) {
			$http.post('http://localhost:9000/unsubscribe', { topicName: topicName, username: $rootScope.globals.currentUser.username })
								.success(function (response) {
										callback(response);
								});
		};

		return service;

	}]);
