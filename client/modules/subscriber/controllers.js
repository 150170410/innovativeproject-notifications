'use strict';

angular.module('Subscriber')

.controller('SubscriberController',
    ['$scope', 'SubscriberService',
    function ($scope, SubscriberService) {

    	$scope.getProducers = function() {
    		SubscriberService.getProducers(function(response) {
                $scope.producers = [];
                for (var i = 0; i < response.length; ++i)
                {
                    var producer = { id: response[i].id, name: response[i].name, subscribedTopics: [], notSubscribedTopics: [] };
                    $scope.producers.push(producer);
                }
    		});
    	};


    	$scope.producers = [

    	];


    	$scope.getTopics = function(producer) {
    		SubscriberService.getTopics(producer.name, function(response) {
                for (var i = 0; i < response.length; ++i)
                {
                    var topic = { id: response[i].id, name: response[i].name };
                    producer.topics.push(topic);
                }
    		});
    	};

    	$scope.getSubscribedTopics = function(producer) {
        producer.subscribedTopics =  [];
    		SubscriberService.getSubscribedTopics(producer.name, function(response) {
                for (var i = 0; i < response.length; ++i)
                {
                    var topic = { id: response[i].id, name: response[i].name };
                    producer.subscribedTopics.push(topic);
                }
    		});
    	};

    	$scope.getNotSubscribedTopics = function(producer) {
        producer.notSubscribedTopics = [];
    		SubscriberService.getNotSubscribedTopics(producer.name, function(response) {
                for (var i = 0; i < response.length; ++i)
                {
                    var topic = { id: response[i].id, name: response[i].name };
                    producer.notSubscribedTopics.push(topic);
                }
    		});
    	};


     	$scope.subscribe = function(producer, topic, index) {
        	SubscriberService.subscribe(topic.name, function(response) {
  				if (response == "Success") {
  					producer.subscribedTopics.push(topic);
  					producer.notSubscribedTopics.splice(index, 1);
  				} else {
  					$scope.error = "error occured";
  					$scope.dataLoading = false;
  				}
  			});
  		}

     	$scope.unsubscribe = function(producer, topic, index) {
        	SubscriberService.unsubscribe(topic.name, function(response) {
  				if (response == "Success") {
  					producer.notSubscribedTopics.push(topic);
  					producer.subscribedTopics.splice(index, 1);
  				} else {
  					$scope.error = "error occured";
  					$scope.dataLoading = false;
  				}
  			});
  		}

      var getUsername = function() {
            return SubscriberService.GetUsername();
        };

        $scope.username = getUsername();
    }]);
