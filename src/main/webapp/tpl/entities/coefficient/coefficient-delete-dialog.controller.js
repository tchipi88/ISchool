(function() {
    'use strict';

    angular
        .module('app')
        .controller('CoefficientDeleteController',CoefficientDeleteController);

    CoefficientDeleteController.$inject = ['$uibModalInstance', 'entity', 'Coefficient'];

    function CoefficientDeleteController($uibModalInstance, entity, Coefficient) {
        var vm = this;

        vm.coefficient = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Coefficient.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
