(function () {
    'use strict';

    angular
            .module('app')
            .controller('CoursController', CoursController);

    CoursController.$inject = ['$state', '$http', 'Cours', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'Professeur', 'Classe'];

    function CoursController($state, $http, Cours, ParseLinks, AlertService, paginationConstants, pagingParams, Professeur, Classe) {

        var vm = this;


        vm.loadAll = loadAll;


        vm.profs = Professeur.query();
        vm.classes = Classe.query();
        vm.save = save;



        function loadAll() {
            $http.get('api/coursss/' + vm.classe.id)
                    .success(function (data) {
                        vm.courss = data;
                    });

        }



        function save() {
            vm.isSaving = true;
            Cours.save(vm.courss, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess(result) {
            vm.isSaving = false;
            vm.edit = false;
            loadAll();
            AlertService.success("");
        }

        function onSaveError(error) {
            vm.isSaving = false;
            AlertService.error(error.data.message);
        }

    }
})();
