(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('compte-analytique-ecriture', {
                parent: 'entity',
                        url: '/compte-analytique-ecriture?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/compte-analytique-ecriture/compte-analytique-ecritures.html',
                                controller: 'CompteAnalytiqueEcritureController',
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
               
               
                .state('compte-analytique-ecriture.new', {
                parent: 'compte-analytique-ecriture',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/compte-analytique-ecriture/compte-analytique-ecriture-dialog.html',
                                controller: 'CompteAnalytiqueEcritureDialogController',
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
                        $state.go('compte-analytique-ecriture', null, {reload: 'compte-analytique-ecriture'});
                        }, function () {
                        $state.go('compte-analytique-ecriture');
                        });
                        }]
                })
                .state('compte-analytique-ecriture.edit', {
                parent: 'compte-analytique-ecriture',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/compte-analytique-ecriture/compte-analytique-ecriture-dialog.html',
                                controller: 'CompteAnalytiqueEcritureDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['CompteAnalytiqueEcriture', function (CompteAnalytiqueEcriture) {
                                return CompteAnalytiqueEcriture.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('compte-analytique-ecriture', null, {reload: 'compte-analytique-ecriture'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('compte-analytique-ecriture.delete', {
                parent: 'compte-analytique-ecriture',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/compte-analytique-ecriture/compte-analytique-ecriture-delete-dialog.html',
                                controller: 'CompteAnalytiqueEcritureDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['CompteAnalytiqueEcriture', function (CompteAnalytiqueEcriture) {
                                return CompteAnalytiqueEcriture.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('compte-analytique-ecriture', null, {reload: 'compte-analytique-ecriture'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
