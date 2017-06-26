(function () {
    'use strict';

    angular
            .module('app')
            .controller('FraisScolariteController', FraisScolariteController);

    FraisScolariteController.$inject = ['$state', 'DataUtils', 'FraisScolarite', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function FraisScolariteController($state, DataUtils, FraisScolarite, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;

        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            FraisScolarite.query({

            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.fraisScolarites = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

       




    }
})();
