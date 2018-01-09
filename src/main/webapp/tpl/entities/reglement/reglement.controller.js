(function() {
    'use strict';

    angular
        .module('app')
        .controller('ReglementController', ReglementController);

    ReglementController.$inject = ['$filter', '$http', 'Reglement',  'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function ReglementController($filter, $http, Reglement,  ParseLinks, AlertService, paginationConstants, pagingParams) {

       var vm = this;
        vm.loadPage = loadPage;
        vm.transition = transition;
        vm.printReglement=printReglement;


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

            Reglement.query({page: vm.page - 1, size: 20, fromDate: fromDate, toDate: toDate}, function (result, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.reglements = result;
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
        function getFileNameFromHeader(header) {
            if (!header)
                return null;
            var result = header.split(";")[1].trim().split("=")[1];
            return result.replace(/"/g, '');
        }
         function printReglement(id) {
            $http({
                method: 'GET',
                url: 'api/reglements-print/' + id,
                responseType: 'arraybuffer',
                transformResponse: function (data, headersGetter, status) {
                    var file = null;
                    if (data) {
                        file = new Blob([data], {
                            type: 'octet/stream' //or whatever you need, should match the 'accept headers' above
                        });
                    }

                    //server should sent content-disposition header
                    var fileName = getFileNameFromHeader(headersGetter('content-disposition'));
                    var result = {
                        blob: file,
                        fileName: fileName
                    };

                    return {
                        response: result
                    };
                },
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}  // set the headers so angular passing info as form data (not request payload)
            })
                    .success(function (data) {
                        saveAs(data.response.blob, data.response.fileName);
                    });
        }
        ;

    }
})();
