(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseEncaissementController', CaisseEncaissementController);

    CaisseEncaissementController.$inject = ['$filter', 'DataUtils', 'CaisseEncaissement',  'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function CaisseEncaissementController($filter, DataUtils, CaisseEncaissement,  ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;
        vm.loadPage = loadPage;
        vm.transition = transition;


        vm.itemsPerPage = 20;
        vm.page = 1;


        vm.fromDate = new Date();
        vm.toDate = new Date();

        vm.onChangeDate = onChangeDate;

        vm.onChangeDate();
        
         function onChangeDate() {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')(vm.fromDate, dateFormat);
            var toDate = $filter('date')(vm.toDate, dateFormat);

            CaisseEncaissement.query({page: vm.page - 1, size: 20, fromDate: fromDate, toDate: toDate}, function (result, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.caisseEncaissements = result;
                vm.page = pagingParams.page;
            });
        }


       function loadPage(page) {
            vm.page = page;
            vm.onChangeDate();
        }

        function transition() {
            vm.onChangeDate();
        }

    }
})();
