(function() {
    'use strict';

    angular
        .module('app')
        .controller('AuthorityDeleteController',AuthorityDeleteController);

    AuthorityDeleteController.$inject = ['$uibModalInstance', 'entity', 'Authority'];

    function AuthorityDeleteController($uibModalInstance, entity, Authority) {
        var vm = this;

        vm.authority = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Authority.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
