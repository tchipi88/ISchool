(function() {
    'use strict';

    angular
        .module('app')
        .controller('FraisScolariteDeleteController',FraisScolariteDeleteController);

    FraisScolariteDeleteController.$inject = ['$uibModalInstance', 'entity', 'FraisScolarite'];

    function FraisScolariteDeleteController($uibModalInstance, entity, FraisScolarite) {
        var vm = this;

        vm.fraisScolarite = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FraisScolarite.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
