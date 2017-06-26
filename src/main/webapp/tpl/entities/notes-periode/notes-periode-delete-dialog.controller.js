(function() {
    'use strict';

    angular
        .module('app')
        .controller('NotesPeriodeDeleteController',NotesPeriodeDeleteController);

    NotesPeriodeDeleteController.$inject = ['$uibModalInstance', 'entity', 'NotesPeriode'];

    function NotesPeriodeDeleteController($uibModalInstance, entity, NotesPeriode) {
        var vm = this;

        vm.notesPeriode = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            NotesPeriode.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
