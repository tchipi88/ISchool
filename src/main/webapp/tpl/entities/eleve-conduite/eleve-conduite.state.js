(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('eleve-conduite', {
                parent: 'entity',
                        url: '/eleve-conduite?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/eleve-conduite/eleve-conduites.html',
                                controller: 'EleveConduiteController',
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
               
               
                .state('eleve-conduite.new', {
                parent: 'eleve-conduite',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/eleve-conduite/eleve-conduite-dialog.html',
                                controller: 'EleveConduiteDialogController',
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
                        $state.go('eleve-conduite', null, {reload: 'eleve-conduite'});
                        }, function () {
                        $state.go('eleve-conduite');
                        });
                        }]
                })
                .state('eleve-conduite.edit', {
                parent: 'eleve-conduite',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/eleve-conduite/eleve-conduite-dialog.html',
                                controller: 'EleveConduiteDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['EleveConduite', function (EleveConduite) {
                                return EleveConduite.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('eleve-conduite', null, {reload: 'eleve-conduite'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('eleve-conduite.delete', {
                parent: 'eleve-conduite',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/eleve-conduite/eleve-conduite-delete-dialog.html',
                                controller: 'EleveConduiteDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['EleveConduite', function (EleveConduite) {
                                return EleveConduite.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('eleve-conduite', null, {reload: 'eleve-conduite'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
