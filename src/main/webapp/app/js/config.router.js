'use strict';
/**
 * Config for the router
 */
angular.module('app')
        .run(
                ['$rootScope', '$state', '$stateParams',
                    function ($rootScope, $state, $stateParams) {
                        $rootScope.$state = $state;
                        $rootScope.$stateParams = $stateParams;
                    }
                ]
                )
        .run(function ($rootScope, $state, $http) {
            $rootScope.$on("event:loginRequired", function (event, toState, toParams, fromState, fromParams) {
                $state.go('access.signin');
            });
        })
        .run(function ($rootScope, $state, $http) {
            $rootScope.$on("$stateChangeStart", function (event, toState, toParams, fromState, fromParams) {
                if (toState.authenticate) {
//                    $http.get('login')
//                            .success(function(response) {
//                                $rootScope.username = response.data;
//                            });
                }
            });
        })
// Configure the $httpProvider by adding our date transformer
//        .config(["$httpProvider", function ($httpProvider) {
//                $httpProvider.defaults.transformResponse.push(function (responseData) {
//                    convertDateStringsToDates(responseData);
//                    return responseData;
//                });
//            }])

        .config(
                ['$stateProvider', '$urlRouterProvider','JQ_CONFIG', 'MODULE_CONFIG',
                    function ($stateProvider, $urlRouterProvider,JQ_CONFIG, MODULE_CONFIG) {

                        $urlRouterProvider
                                .otherwise('/access/404');
                        $stateProvider
                                .state('app', {
                                    abstract: true,
                                    // url: '/app',
                                    templateUrl: 'tpl/app.html',
                                    authenticate: true,
                                    resolve: load(['ui.select','toaster', 'js/controllers/app.js'])
                                })
                                .state('app.home', {
                                    url: '/home',
                                    templateUrl: 'tpl/blank.html',
                                    authenticate: true
                                })


                                // table
                                .state('app.table', {
                                    url: '/list',
                                    template: '<div ui-view></div>',
                                    resolve: load(['smart-table','js/controllers/form.js'])
                                })
                                .state('app.table.list', {
                                    authenticate: true,
                                    url: '/:categorie',
                                    templateUrl: function ($stateParams) {
                                        return 'tpl/views/' + $stateParams.categorie + '_list.html';
                                    }
                                })


                                // form
                                .state('app.form', {
                                    url: '/form',
                                    template: '<div ui-view class="fade-in"></div>',
                                    authenticate: true
                                })
                                .state('app.form.elt', {
                                    url: '/:categorie/:id',
                                    templateUrl: function ($stateParams) {
                                        return 'tpl/views/' + $stateParams.categorie + '_form.html';
                                    },
                                    authenticate: true
                                })
                                .state('app.form.elt.relations', {
                                    authenticate: true,
                                    url: '/relations/:sfield',
                                    views: {
                                        "relationView": {
                                            templateUrl: 'tpl/relation.html',
                                            controller: 'listController'
                                        }
                                    },
                                    resolve: {
                                        initialData: function ($http, $stateParams) {
                                            var promise = $http({method: 'GET', url: $stateParams.categorie + '/' + $stateParams.id + '/relations/' + $stateParams.sfield}).success(function (data, status, headers, config) {
                                                return data;
                                            });
                                            return promise;
                                        },
                                        deps: ['$ocLazyLoad',
                                            function ($ocLazyLoad) {
                                                return $ocLazyLoad.load('datatables');
                                            }]

                                    }
                                })

                                // pages
                                .state('app.docs', {
                                    url: '/docs',
                                    templateUrl: 'tpl/docs.html',
                                    authenticate: true
                                })
                                .state('app.changepwd', {
                                    url: '/changepwd',
                                    templateUrl: 'tpl/page_changepwd.html',
                                    authenticate: true,
                                    resolve: {
                                        deps: ['uiLoad',
                                            function (uiLoad) {
                                                return uiLoad.load(['js/controllers/user.js']);
                                            }]
                                    }
                                })
                                .state('app.process', {
                                    url: '/process/:process',
                                    templateUrl: function (stateParams) {
                                        return 'tpl/' + stateParams.process + '.html';
                                    },
                                    authenticate: true

                                })
                                // others
                                .state('lockme', {
                                    url: '/lockme',
                                    templateUrl: 'tpl/page_lockme.html'
                                })
                                .state('access', {
                                    url: '/access',
                                    template: '<div ui-view class="fade-in-right-big smooth"></div>',
                                    authenticate: false,
                                    resolve: {
                                        deps: ['$ocLazyLoad',
                                            function ($ocLazyLoad) {
                                                return $ocLazyLoad.load('toaster').
                                                        then(function () {
                                                            return $ocLazyLoad.load('js/controllers/user.js');
                                                        }
                                                        );
                                            }]
                                    }
                                })
                                .state('access.signin', {
                                    url: '/signin',
                                    templateUrl: 'tpl/page_signin.html'
                                })
                                .state('access.changepwdexp', {
                                    url: '/changepwdexp',
                                    templateUrl: 'tpl/page_changepwdexp.html'
                                })
                                .state('access.forgotpwd', {
                                    url: '/forgotpwd',
                                    templateUrl: 'tpl/page_forgotpwd.html'
                                })
                                .state('access.404', {
                                    url: '/404',
                                    templateUrl: 'tpl/page_404.html',
                                    authenticate: true
                                })

                                ;
                        function load(srcs, callback) {
                            return {
                                deps: ['$ocLazyLoad', '$q',
                                    function ($ocLazyLoad, $q) {
                                        var deferred = $q.defer();
                                        var promise = false;
                                        srcs = angular.isArray(srcs) ? srcs : srcs.split(/\s+/);
                                        if (!promise) {
                                            promise = deferred.promise;
                                        }
                                        angular.forEach(srcs, function (src) {
                                            promise = promise.then(function () {
                                                if (JQ_CONFIG[src]) {
                                                    return $ocLazyLoad.load(JQ_CONFIG[src]);
                                                }
                                                angular.forEach(MODULE_CONFIG, function (module) {
                                                    if (module.name == src) {
                                                        name = module.name;
                                                    } else {
                                                        name = src;
                                                    }
                                                });
                                                return $ocLazyLoad.load(name);
                                            });
                                        });
                                        deferred.resolve();
                                        return callback ? promise.then(function () {
                                            return callback();
                                        }) : promise;
                                    }]
                            }
                        }
                    }
                ]
                );

var regexIso8601 = /^(\d{4}|\+\d{6})(?:-(\d{2})(?:-(\d{2})(?:T(\d{2}):(\d{2}):(\d{2})\.(\d{1,})(Z|([\-+])(\d{2}):(\d{2}))?)?)?)?$/;

var ISO_DATE_REGEXP = /\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d:[0-5]\d\.\d+([+-][0-2]\d:[0-5]\d|Z)/;

var iso8601regex = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2}(?:\.\d*)?)Z$/;

function convertDateStringsToDates(input) {
    // Ignore things that aren't objects.
    if (typeof input !== "object")
        return input;

    for (var key in input) {
        if (!input.hasOwnProperty(key))
            continue;

        var value = input[key];
        var match;
        // console.log(value);
        // Check for string properties which look like dates.
        // TODO: Improve this regex to better match ISO 8601 date strings.
        if (typeof value === "string" && (match = value.match(iso8601regex))) {
            // Assume that Date.parse can parse ISO 8601 strings, or has been shimmed in older browsers to do so.
            var milliseconds = Date.parse(match[0]);
            if (!isNaN(milliseconds)) {
                input[key] = new Date(milliseconds);
            }
        } else if (typeof value === "object") {
            // Recurse into object
            convertDateStringsToDates(value);
        }
    }
}