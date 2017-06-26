(function () {
    'use strict';
    angular
            .module('app')
            .config(stateConfig);
    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider) {
        $stateProvider
                .state('reglement', {
                    parent: 'entity',
                    url: '/reglement?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/reglement/reglements.html',
                            controller: 'ReglementController',
                            controllerAs: 'vm'}
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


                .state('reglement.new', {
                    parent: 'reglement',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/reglement/reglement-dialog.html',
                                controller: 'ReglementDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: function () {
                                        return {
                                            dateVersement: new Date(), modePaiement: 'ESPECES',motif:'ECOLAGE'
                                        };
                                    }
                                }
                            }).result.then(function () {
                                $state.go('reglement', null, {reload: 'reglement'});
                            }, function () {
                                $state.go('reglement');
                            });
                        }]
                })
                .state('reglement.edit', {
                    parent: 'reglement',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/reglement/reglement-dialog.html',
                                controller: 'ReglementDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['Reglement', function (Reglement) {
                                            return Reglement.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('reglement', null, {reload: 'reglement'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('reglement.delete', {
                    parent: 'reglement',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/reglement/reglement-delete-dialog.html',
                                controller: 'ReglementDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['Reglement', function (Reglement) {
                                            return Reglement.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('reglement', null, {reload: 'reglement'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
