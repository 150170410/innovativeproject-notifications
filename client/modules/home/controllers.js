'use strict';
 
angular.module('Home')
 
.controller('HomeController',
    ['$scope', 'HomeService',
    function ($scope, HomeService) {
      
    	$scope.getmessage = function() {
    		HomeService.GetMessage(function(response) {
				$scope.message = response;
    		}); 
    	};

    }]);