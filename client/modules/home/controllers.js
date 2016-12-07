'use strict';
 
angular.module('Home')
 
.controller('HomeController',
    ['$scope', 'HomeService',
    function ($scope, HomeService) {
      
    	$scope.getMessage = function() {
    		HomeService.GetMessage(function(response) {
                $scope.notifications = [];
                $scope.notificationsSelected = [];
                for (var i = 0; i < response.length; ++i)
                {
                    var notif = { id: response[i].id, message: response[i].message };
                    $scope.notifications.push(notif);
                }
    		}); 
    	};

    	$scope.notifications = [
    		
    	];

    	$scope.notificationsSelected = [
            
        ];

    	$scope.checkAll = function() {
    		$scope.notificationsSelected = $scope.notifications.map(function(item) { return item.id; });
    	};

    	$scope.uncheckAll = function() {
    		$scope.notificationsSelected = [];
    	};
    }]);