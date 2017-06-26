(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseMouvementDeleteController',CaisseMouvementDeleteController);

    CaisseMouvementDeleteController.$inject = ['$uibModalInstance', 'entity', 'CaisseMouvement'];

    function CaisseMouvementDeleteController($uibModalInstance, entity, CaisseMouvement) {
        var vm = this;

        vm.caisseMouvement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CaisseMouvement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
