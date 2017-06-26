(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('eleve-absence', {
                parent: 'entity',
                        url: '/eleve-absence?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/eleve-absence/eleve-absences.html',
                                controller: 'EleveAbsenceController',
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
               
               
                .state('eleve-absence.new', {
                parent: 'eleve-absence',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/eleve-absence/eleve-absence-dialog.html',
                                controller: 'EleveAbsenceDialogController',
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
                        $state.go('eleve-absence', null, {reload: 'eleve-absence'});
                        }, function () {
                        $state.go('eleve-absence');
                        });
                        }]
                })
                .state('eleve-absence.edit', {
                parent: 'eleve-absence',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/eleve-absence/eleve-absence-dialog.html',
                                controller: 'EleveAbsenceDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['EleveAbsence', function (EleveAbsence) {
                                return EleveAbsence.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('eleve-absence', null, {reload: 'eleve-absence'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('eleve-absence.delete', {
                parent: 'eleve-absence',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/eleve-absence/eleve-absence-delete-dialog.html',
                                controller: 'EleveAbsenceDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['EleveAbsence', function (EleveAbsence) {
                                return EleveAbsence.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('eleve-absence', null, {reload: 'eleve-absence'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
