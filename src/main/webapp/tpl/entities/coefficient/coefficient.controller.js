(function () {
    'use strict';

    angular
            .module('app')
            .controller('CoefficientController', CoefficientController);

    CoefficientController.$inject = ['$state', 'Coefficient', 'ParseLinks', 'AlertService', 'paginationConstants', 'Matiere'];

    function CoefficientController($state, Coefficient, ParseLinks, AlertService, paginationConstants, Matiere) {

        var vm = this;

        vm.loadAll = loadAll;
        vm.save = save;

        vm.matieres = Matiere.query();
        // loadAll();

        function loadAll() {
            Coefficient.query({
                matiere: vm.matiere.id
            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.coefficients = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function save() {
            vm.isSaving = true;
            Coefficient.save(vm.coefficients, onSaveSuccess, onSaveError);
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
