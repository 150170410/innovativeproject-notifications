'use strict';

angular.module('Authentication', []);
angular.module('Home', []);
angular.module('Register', []);
angular.module('Producer', []);
angular.module('Subscriber', []);

angular.module('Client', [
    'Authentication',
    'Home',
    'Register',
    'Producer',
    'Subscriber',
    'ngRoute',
    'ngCookies'
])
 
.config(['$routeProvider', function ($routeProvider) {

    $routeProvider
        .when('/login', {
            controller: 'LoginController',
            templateUrl: 'modules/authentication/views/login.html',
            hideMenus: true
        })
 
        .when('/', {
            controller: 'HomeController',
            templateUrl: 'modules/home/views/home.html'
        })

        .when('/register', {
            controller: 'RegisterController',
            templateUrl: 'modules/register/views/register.html',
            hideMenus: true
        })

        .when('/producer', {
            controller: 'ProducerController',
            templateUrl: 'modules/producer/views/producer.html',
        })

        .when('/subscriber', {
            controller: 'SubscriberController',
            templateUrl: 'modules/subscriber/views/subscriber.html'
        })
 
        .otherwise({ redirectTo: '/login' });
}])
 
.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
        }
 
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            if (($location.path() !== '/login' && !$rootScope.globals.currentUser) && $location.path() !== '/register') {
                $location.path('/login');
            }
        });
    }]);