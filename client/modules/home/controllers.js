'use strict';
 
angular.module('Home')
 
.controller('HomeController',
    ['$scope', 'HomeService', '$timeout',
    function ($scope, HomeService, $timeout) {
      
    	var getMessage = function() {
    		HomeService.GetMessage(function(response) {
                $scope.notifications = [];
                for (var i = 0; i < response.length; ++i)
                {
                    var notif = { id: response[i].id, message: response[i].message };
                    $scope.notifications.push(notif);
                }
                timer = $timeout(getMessage, 5000);
    		}); 
    	};

        $scope.$on('$routeChangeStart', function(next, current) { 
            $timeout.cancel(timer);        
        });

        var timer = $timeout(getMessage, 0);

    	$scope.notifications = [
    		
    	];

        $scope.setAsRead = function(message) {
            HomeService.SetAsRead(message.id, function(response) {
                $timeout.cancel(timer);
                getMessage();
            });
        };

    }]);