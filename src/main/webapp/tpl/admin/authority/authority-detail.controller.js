(function() {
    'use strict';

    angular
        .module('app')
        .controller('AuthorityDetailController', AuthorityDetailController);

    AuthorityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Authority' ];

    function AuthorityDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Authority ) {
        var vm = this;

        vm.authority = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:authorityUpdate', function(event, result) {
            vm.authority = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
