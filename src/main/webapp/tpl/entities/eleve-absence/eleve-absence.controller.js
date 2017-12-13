(function () {
    'use strict';

    angular
            .module('app')
            .controller('EleveAbsenceController', EleveAbsenceController);

    EleveAbsenceController.$inject = ['$http','$state', 'DataUtils', 'EleveAbsence', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams','Classe'];

    function EleveAbsenceController($http,$state, DataUtils, EleveAbsence, ParseLinks, AlertService, paginationConstants, pagingParams,Classe) {

        var vm = this;

        vm.loadAll = loadAll;
        vm.save = save;

          loadAll();


        function loadAll() {
            
             $http.get("api/classess")
        .success(function(data) {
            vm.classes = data;
        });
         if (pagingParams.classe) {
            EleveAbsence.query({
                classe: vm.classe.id,
                numseq: vm.numseq
            }, onSuccess, onError);
        }
            function onSuccess(data, headers) {
                vm.eleveAbsences = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function save() {
            vm.isSaving = true;
            EleveAbsence.save(vm.eleveAbsences, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess(result) {

            vm.isSaving = false;
            vm.edit = false;
            vm.notes = result;
        }

        function onSaveError(error) {
            vm.isSaving = false;
            AlertService.error(error.data.message);
        }



       
    }
})();
