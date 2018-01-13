(function () {
    'use strict';

    angular
            .module('app')
            .controller('CoefficientController', CoefficientController);

    CoefficientController.$inject = ['$http', 'Matiere','Serie','Coefficient', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function CoefficientController($http,Matiere,Serie, Coefficient, ParseLinks, AlertService, paginationConstants) {

        var vm = this;

        vm.loadAll = loadAll;
        vm.loadAllBySerie = loadAllBySerie;
        vm.save = save;



        loadData();

        function loadData() {
            vm.matieres=  Matiere.query({
                  page:0,
                  size:200
              });
               vm.series=  Serie.query({
                  page:0,
                  size:200
              });
        }
        // loadAll();

        function loadAll() {
            vm.serie=null;
            Coefficient.query({
                valeur: vm.matiere.id
            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.coefficients = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
        function loadAllBySerie() {
            vm.matiere=null;
            Coefficient.query({
                valeur: vm.serie.id
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
            if(vm.serie) loadAllBySerie();
            else loadAll();
            AlertService.success("");
        }

        function onSaveError(error) {
            vm.isSaving = false;
            AlertService.error(error.data.message);
        }





    }
})();
