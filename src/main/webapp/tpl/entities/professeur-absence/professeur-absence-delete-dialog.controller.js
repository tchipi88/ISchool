(function() {
    'use strict';

    angular
        .module('app')
        .controller('ProfesseurAbsenceDeleteController',ProfesseurAbsenceDeleteController);

    ProfesseurAbsenceDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProfesseurAbsence'];

    function ProfesseurAbsenceDeleteController($uibModalInstance, entity, ProfesseurAbsence) {
        var vm = this;

        vm.professeurAbsence = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProfesseurAbsence.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
