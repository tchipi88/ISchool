(function() {
    'use strict';

    angular
        .module('app')
        .controller('CompteAnalytiqueEcritureDeleteController',CompteAnalytiqueEcritureDeleteController);

    CompteAnalytiqueEcritureDeleteController.$inject = ['$uibModalInstance', 'entity', 'CompteAnalytiqueEcriture'];

    function CompteAnalytiqueEcritureDeleteController($uibModalInstance, entity, CompteAnalytiqueEcriture) {
        var vm = this;

        vm.compteAnalytiqueEcriture = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CompteAnalytiqueEcriture.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
