'use strict';

angular.module('Register')

.controller('RegisterController',
	['$scope', '$rootScope', '$location', 'RegisterService',
	function ($scope, $rootScope, $location, RegisterService) {

		$scope.register = function() {
			$scope.dataLoading = true;
			RegisterService.Register($scope.username, $scope.password, $scope.email, function(response) {
				if (response == "Success") {
					$location.path('/login');
				} else {
					$scope.error = "Something went wrong. Please try again later.";
					$scope.dataLoading = false;
				}
			});
		};
	}]);