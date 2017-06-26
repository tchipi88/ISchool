(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('caisse-decaissement', {
                parent: 'entity',
                        url: '/caisse-decaissement?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/caisse-decaissement/caisse-decaissements.html',
                                controller: 'CaisseDecaissementController',
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
               
               
                .state('caisse-decaissement.new', {
                parent: 'caisse-decaissement',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-decaissement/caisse-decaissement-dialog.html',
                                controller: 'CaisseDecaissementDialogController',
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
                        $state.go('caisse-decaissement', null, {reload: 'caisse-decaissement'});
                        }, function () {
                        $state.go('caisse-decaissement');
                        });
                        }]
                })
                .state('caisse-decaissement.edit', {
                parent: 'caisse-decaissement',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-decaissement/caisse-decaissement-dialog.html',
                                controller: 'CaisseDecaissementDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['CaisseDecaissement', function (CaisseDecaissement) {
                                return CaisseDecaissement.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('caisse-decaissement', null, {reload: 'caisse-decaissement'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('caisse-decaissement.delete', {
                parent: 'caisse-decaissement',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-decaissement/caisse-decaissement-delete-dialog.html',
                                controller: 'CaisseDecaissementDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['CaisseDecaissement', function (CaisseDecaissement) {
                                return CaisseDecaissement.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('caisse-decaissement', null, {reload: 'caisse-decaissement'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
