(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('caisse-mouvement', {
                parent: 'entity',
                        url: '/caisse-mouvement?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/caisse-mouvement/caisse-mouvements.html',
                                controller: 'CaisseMouvementController',
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
               
               
                .state('caisse-mouvement.new', {
                parent: 'caisse-mouvement',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-mouvement/caisse-mouvement-dialog.html',
                                controller: 'CaisseMouvementDialogController',
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
                        $state.go('caisse-mouvement', null, {reload: 'caisse-mouvement'});
                        }, function () {
                        $state.go('caisse-mouvement');
                        });
                        }]
                })
                .state('caisse-mouvement.edit', {
                parent: 'caisse-mouvement',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-mouvement/caisse-mouvement-dialog.html',
                                controller: 'CaisseMouvementDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['CaisseMouvement', function (CaisseMouvement) {
                                return CaisseMouvement.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('caisse-mouvement', null, {reload: 'caisse-mouvement'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('caisse-mouvement.delete', {
                parent: 'caisse-mouvement',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-mouvement/caisse-mouvement-delete-dialog.html',
                                controller: 'CaisseMouvementDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['CaisseMouvement', function (CaisseMouvement) {
                                return CaisseMouvement.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('caisse-mouvement', null, {reload: 'caisse-mouvement'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
