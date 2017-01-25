'use strict';

angular.module('Producer')

.controller('ProducerController',
    ['$scope', 'ProducerService',
    function ($scope, ProducerService) {

    	$scope.getTopics = function() {
    		ProducerService.getTopics(function(response) {
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
        ProducerService.addTopic($scope.newTopic, function(response) {
  				if (response == "Success") {
  					$scope.newTopic = ''
  				} else {
  					$scope.error = "error occured";
  					$scope.dataLoading = false;
  				}
  			});

  		}

  		$scope.deleteTopic= function(index) {
        ProducerService.deleteTopic($scope.topics[index].name, function(response) {
  				if (response == "Success") {
  					$scope.newTopic = ''
  				} else {
  					$scope.error = "error occured";
  					$scope.dataLoading = false;
  				}
  			});
  			$scope.topics.splice(index, 1);
  		}

      var getUsername = function() {
            return ProducerService.GetUsername();
      };

      $scope.username = getUsername();
    }]);
