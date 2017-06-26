(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseDecaissementDeleteController',CaisseDecaissementDeleteController);

    CaisseDecaissementDeleteController.$inject = ['$uibModalInstance', 'entity', 'CaisseDecaissement'];

    function CaisseDecaissementDeleteController($uibModalInstance, entity, CaisseDecaissement) {
        var vm = this;

        vm.caisseDecaissement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CaisseDecaissement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
