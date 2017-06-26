(function() {
    'use strict';

    angular
        .module('app')
        .controller('EleveConduiteDeleteController',EleveConduiteDeleteController);

    EleveConduiteDeleteController.$inject = ['$uibModalInstance', 'entity', 'EleveConduite'];

    function EleveConduiteDeleteController($uibModalInstance, entity, EleveConduite) {
        var vm = this;

        vm.eleveConduite = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EleveConduite.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
