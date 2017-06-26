(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('professeur-absence', {
                parent: 'entity',
                        url: '/professeur-absence?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/professeur-absence/professeur-absences.html',
                                controller: 'ProfesseurAbsenceController',
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
               
               
                .state('professeur-absence.new', {
                parent: 'professeur-absence',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/professeur-absence/professeur-absence-dialog.html',
                                controller: 'ProfesseurAbsenceDialogController',
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
                        $state.go('professeur-absence', null, {reload: 'professeur-absence'});
                        }, function () {
                        $state.go('professeur-absence');
                        });
                        }]
                })
                .state('professeur-absence.edit', {
                parent: 'professeur-absence',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/professeur-absence/professeur-absence-dialog.html',
                                controller: 'ProfesseurAbsenceDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['ProfesseurAbsence', function (ProfesseurAbsence) {
                                return ProfesseurAbsence.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('professeur-absence', null, {reload: 'professeur-absence'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('professeur-absence.delete', {
                parent: 'professeur-absence',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/professeur-absence/professeur-absence-delete-dialog.html',
                                controller: 'ProfesseurAbsenceDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['ProfesseurAbsence', function (ProfesseurAbsence) {
                                return ProfesseurAbsence.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('professeur-absence', null, {reload: 'professeur-absence'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
