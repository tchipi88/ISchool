(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('notes-periode', {
                parent: 'entity',
                        url: '/notes-periode?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/notes-periode/notes-periodes.html',
                                controller: 'NotesPeriodeController',
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
               
               
                .state('notes-periode.new', {
                parent: 'notes-periode',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/notes-periode/notes-periode-dialog.html',
                                controller: 'NotesPeriodeDialogController',
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
                        $state.go('notes-periode', null, {reload: 'notes-periode'});
                        }, function () {
                        $state.go('notes-periode');
                        });
                        }]
                })
                .state('notes-periode.edit', {
                parent: 'notes-periode',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/notes-periode/notes-periode-dialog.html',
                                controller: 'NotesPeriodeDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['NotesPeriode', function (NotesPeriode) {
                                return NotesPeriode.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('notes-periode', null, {reload: 'notes-periode'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('notes-periode.delete', {
                parent: 'notes-periode',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/notes-periode/notes-periode-delete-dialog.html',
                                controller: 'NotesPeriodeDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['NotesPeriode', function (NotesPeriode) {
                                return NotesPeriode.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('notes-periode', null, {reload: 'notes-periode'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
