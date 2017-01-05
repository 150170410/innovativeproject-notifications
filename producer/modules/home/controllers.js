'use strict';

angular.module('Home')

.controller('HomeController',
    ['$scope', 'HomeService',
    function ($scope, HomeService) {

    	$scope.getTopics = function() {
    		HomeService.getTopics(function(response) {
                $scope.topics = [];
                for (var i = 0; i < response.length; ++i)
                {
                    var notif = { id: response[i].id, name: response[i].name };
                    $scope.topics.push(notif);
                }
    		});
    	};

    	$scope.topics = [

    	];


      $scope.addTopic = function() {
        var notif = { id: 0, name: $scope.newTopic };
  		  $scope.topics.push(notif);
        HomeService.addTopic($scope.newTopic, function(response) {
  				if (response == "Success") {
  					$scope.newTopic = ''
  				} else {
  					$scope.error = "error occured";
  					$scope.dataLoading = false;
  				}
  			});

  		}

  		$scope.deleteTopic= function(index) {
        HomeService.deleteTopic($scope.topics[index].name, function(response) {
  				if (response == "Success") {
  					$scope.newTopic = ''
  				} else {
  					$scope.error = "error occured";
  					$scope.dataLoading = false;
  				}
  			});
  			$scope.topics.splice(index, 1);
  		}
    }]);
