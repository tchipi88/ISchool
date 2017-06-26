(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('caisse-encaissement', {
                parent: 'entity',
                        url: '/caisse-encaissement?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/caisse-encaissement/caisse-encaissements.html',
                                controller: 'CaisseEncaissementController',
                                controllerAs: 'vm'  }
                        },
                        params: {
                        page: {
                        value: '1',
                                squash: true
                        },
                                sort: {
                                value: 'id,asc',
                                        squash: true
                                },
                                search: null
                        },
                        resolve: {
                        pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                        return {
                        page: PaginationUtil.parsePage($stateParams.page),
                                sort: $stateParams.sort,
                                predicate: PaginationUtil.parsePredicate($stateParams.sort),
                                ascending: PaginationUtil.parseAscending($stateParams.sort),
                                search: $stateParams.search
                        };
                        }]
                        }
                })
               
               
                .state('caisse-encaissement.new', {
                parent: 'caisse-encaissement',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-encaissement/caisse-encaissement-dialog.html',
                                controller: 'CaisseEncaissementDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: function () {
                                return {

                                };
                                }
                                }
                        }).result.then(function () {
                        $state.go('caisse-encaissement', null, {reload: 'caisse-encaissement'});
                        }, function () {
                        $state.go('caisse-encaissement');
                        });
                        }]
                })
                .state('caisse-encaissement.edit', {
                parent: 'caisse-encaissement',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-encaissement/caisse-encaissement-dialog.html',
                                controller: 'CaisseEncaissementDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['CaisseEncaissement', function (CaisseEncaissement) {
                                return CaisseEncaissement.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('caisse-encaissement', null, {reload: 'caisse-encaissement'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('caisse-encaissement.delete', {
                parent: 'caisse-encaissement',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-encaissement/caisse-encaissement-delete-dialog.html',
                                controller: 'CaisseEncaissementDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['CaisseEncaissement', function (CaisseEncaissement) {
                                return CaisseEncaissement.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('caisse-encaissement', null, {reload: 'caisse-encaissement'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
