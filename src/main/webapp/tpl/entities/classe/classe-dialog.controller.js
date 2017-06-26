(function () {
    'use strict';

    angular
            .module('app')
            .controller('ClasseDialogController', ClasseDialogController);

    ClasseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal', 'DataUtils', 'entity', 'Classe', 'Serie'];

    function ClasseDialogController($timeout, $scope, $stateParams, $uibModalInstance, $uibModal, DataUtils, entity, Classe, Serie) {
        var vm = this;

        vm.clear = clear;
        vm.save = save;
        vm.series = Serie.query();



        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            Classe.save(vm.series, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess(result) {
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }




      

       

     
    }
})();
