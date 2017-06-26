(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('frais-scolarite', {
                parent: 'entity',
                        url: '/frais-scolarite?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/frais-scolarite/frais-scolarites.html',
                                controller: 'FraisScolariteController',
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
               
               
                .state('frais-scolarite.new', {
                parent: 'frais-scolarite',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/frais-scolarite/frais-scolarite-dialog.html',
                                controller: 'FraisScolariteDialogController',
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
                        $state.go('frais-scolarite', null, {reload: 'frais-scolarite'});
                        }, function () {
                        $state.go('frais-scolarite');
                        });
                        }]
                })
                .state('frais-scolarite.edit', {
                parent: 'frais-scolarite',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/frais-scolarite/frais-scolarite-dialog.html',
                                controller: 'FraisScolariteDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['FraisScolarite', function (FraisScolarite) {
                                return FraisScolarite.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('frais-scolarite', null, {reload: 'frais-scolarite'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('frais-scolarite.delete', {
                parent: 'frais-scolarite',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/frais-scolarite/frais-scolarite-delete-dialog.html',
                                controller: 'FraisScolariteDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['FraisScolarite', function (FraisScolarite) {
                                return FraisScolarite.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('frais-scolarite', null, {reload: 'frais-scolarite'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
