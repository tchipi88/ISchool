(function() {
    'use strict';

    angular
        .module('app')
        .controller('CreneauDeleteController',CreneauDeleteController);

    CreneauDeleteController.$inject = ['$uibModalInstance', 'entity', 'Creneau'];

    function CreneauDeleteController($uibModalInstance, entity, Creneau) {
        var vm = this;

        vm.creneau = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Creneau.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
