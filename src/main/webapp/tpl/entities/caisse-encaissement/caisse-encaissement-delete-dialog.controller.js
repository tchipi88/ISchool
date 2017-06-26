(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseEncaissementDeleteController',CaisseEncaissementDeleteController);

    CaisseEncaissementDeleteController.$inject = ['$uibModalInstance', 'entity', 'CaisseEncaissement'];

    function CaisseEncaissementDeleteController($uibModalInstance, entity, CaisseEncaissement) {
        var vm = this;

        vm.caisseEncaissement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CaisseEncaissement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
