(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseController', CaisseController);

    CaisseController.$inject = ['$http','$state', '$filter','DataUtils', 'Caisse',  'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function CaisseController($http,$state,$filter, DataUtils, Caisse,  ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.clear = clear;
        vm.loadAll = loadAll;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.openCalendar = openCalendar;
         vm.datePickerOpenStatus = {};

         vm.datePickerOpenStatus.dateJournal = false;
         vm.datePickerOpenStatus.paiementReportDateStart = false;
         vm.datePickerOpenStatus.paiementReportDateEnd = false;

         vm.printPaymentPeriod = printPaymentPeriod;
         vm.printJournalCaisse = printJournalCaisse;

         vm.paiementReportDateStart = new Date();
         vm.paiementReportDateEnd = new Date();
         vm.dateJournal = new Date();


        loadAll();



        function loadAll () {
                Caisse.query({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.caisses = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
            });
        }

          function openCalendar (date) {
                   vm.datePickerOpenStatus[date] = true;
              }


        function clear() {
            vm.links = null;
            vm.page = 1;
            vm.predicate = 'id';
            vm.reverse = true;
            vm.transition();
        }


       function printPaymentPeriod() {
        var dateFormat = 'yyyy-MM-dd';
        var paiementReportDateStart = $filter('date')(vm.paiementReportDateStart, dateFormat);
        var paiementReportDateEnd = $filter('date')(vm.paiementReportDateEnd, dateFormat);

        $http({
            method: 'GET',
            url: 'api/printPaymentPeriod?dateDebut='+paiementReportDateStart+'&dateFin='+paiementReportDateEnd+'&modePaiement='+vm.paiementReportModePaiement,
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


    function printJournalCaisse() {
         var dateFormat = 'yyyy-MM-dd';
          var dateJournal = $filter('date')(vm.dateJournal, dateFormat);
                $http({
                    method: 'GET',
                    url: 'api/printJournalCaisse?dateJour='+dateJournal,
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

              function getFileNameFromHeader(header) {
                        if (!header)
                            return null;
                        var result = header.split(";")[1].trim().split("=")[1];
                        return result.replace(/"/g, '');
                    }



    }
})();
