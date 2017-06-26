(function() {
    'use strict';

    angular
        .module('app')
        .controller('AnneeDeleteController',AnneeDeleteController);

    AnneeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Annee'];

    function AnneeDeleteController($uibModalInstance, entity, Annee) {
        var vm = this;

        vm.annee = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Annee.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
