(function() {
    'use strict';

    angular
        .module('app')
        .controller('EleveAbsenceDeleteController',EleveAbsenceDeleteController);

    EleveAbsenceDeleteController.$inject = ['$uibModalInstance', 'entity', 'EleveAbsence'];

    function EleveAbsenceDeleteController($uibModalInstance, entity, EleveAbsence) {
        var vm = this;

        vm.eleveAbsence = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EleveAbsence.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
