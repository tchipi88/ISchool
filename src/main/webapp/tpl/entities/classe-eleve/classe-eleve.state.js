(function () {
    'use strict';
    angular
            .module('app')
            .config(stateConfig);
    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider) {
        $stateProvider
                .state('classe-eleve', {
                    parent: 'entity',
                    url: '/classe-eleve?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/classe-eleve/classe-eleves.html',
                            controller: 'ClasseEleveController',
                            controllerAs: 'vm'}
                    },
                    params: {
                        page: {
                            value: '1',
                            squash: true
                        },
                        sort: {
                            value: 'eleve,asc',
                            squash: true
                        },
                        search: null,
                        classe: null

                    },
                    resolve: {
                        pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                                return {
                                    page: PaginationUtil.parsePage($stateParams.page),
                                    sort: $stateParams.sort,
                                    predicate: PaginationUtil.parsePredicate($stateParams.sort),
                                    ascending: PaginationUtil.parseAscending($stateParams.sort),
                                    search: $stateParams.search,
                                    classe: $stateParams.classe
                                };
                            }]
                    }
                })


                .state('classe-eleve.new', {
                    parent: 'classe-eleve',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/classe-eleve/classe-eleve-dialog.html',
                                controller: 'ClasseEleveDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: function () {
                                        return {
                                            classe: $stateParams.classe
                                        };
                                    }
                                }
                            }).result.then(function (result) {
                                $state.go('classe-eleve', {classe:result.classe}, {reload: 'classe-eleve'});
                            }, function () {
                                $state.go('classe-eleve');
                            });
                        }]
                })
                .state('classe-eleve.edit', {
                    parent: 'classe-eleve',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/classe-eleve/classe-eleve-dialog.html',
                                controller: 'ClasseEleveDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['ClasseEleve', function (ClasseEleve) {
                                            return ClasseEleve.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('classe-eleve', null, {reload: 'classe-eleve'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('classe-eleve.delete', {
                    parent: 'classe-eleve',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/classe-eleve/classe-eleve-delete-dialog.html',
                                controller: 'ClasseEleveDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['ClasseEleve', function (ClasseEleve) {
                                            return ClasseEleve.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('classe-eleve', null, {reload: 'classe-eleve'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
