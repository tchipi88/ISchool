(function () {
    'use strict';

    angular
            .module('app')
            .controller('CompteAnalytiqueController', CompteAnalytiqueController);

    CompteAnalytiqueController.$inject = ['$http','$state', 'DataUtils', 'CompteAnalytique', 'CompteAnalytiqueSearch', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'Classe'];

    function CompteAnalytiqueController($http,$state, DataUtils, CompteAnalytique, CompteAnalytiqueSearch, ParseLinks, AlertService, paginationConstants, pagingParams, Classe) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.clear = clear;
        vm.search = search;
        vm.searchByClasse = searchByClasse;
        vm.loadAll = loadAll;
        vm.print = print;
        vm.searchQuery = pagingParams.search;
        vm.currentSearch = pagingParams.search;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        vm.currentClasse = pagingParams.classe;
        vm.classe = pagingParams.classe;
        vm.classes = Classe.query();

        loadAll();

        function loadAll() {
            if (pagingParams.search) {
                CompteAnalytiqueSearch.query({
                    query: pagingParams.search,
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            } else {
                if (pagingParams.classe) {
                    CompteAnalytique.query({
                        page: pagingParams.page - 1,
                        size: vm.itemsPerPage,
                        sort: sort(),
                        classe: pagingParams.classe.id
                    }, onSuccess, onError);
                }
            }
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
                vm.compteAnalytiques = data;
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
                search: vm.currentSearch,
                classe: vm.currentClasse
            });
        }

        function search(searchQuery) {
            if (!searchQuery) {
                return vm.clear();
            }
            vm.links = null;
            vm.page = 1;
            vm.predicate = '_score';
            vm.reverse = false;
            vm.currentSearch = searchQuery;
            vm.currentClasse=null;
            vm.transition();
        }
        function searchByClasse() {
            vm.links = null;
            vm.page = 1;
            vm.predicate = 'id';
            vm.reverse = false;
            vm.currentClasse = vm.classe;
            vm.currentSearch=null;
            vm.transition();
        }

        function clear() {
            vm.links = null;
            vm.page = 1;
            vm.predicate = 'id';
            vm.reverse = true;
            vm.currentSearch = null;
            vm.transition();
        }
        
        function getFileNameFromHeader(header) {
            if (!header)
                return null;
            var result = header.split(";")[1].trim().split("=")[1];
            return result.replace(/"/g, '');
        }

        function print() {
            $http({
                method: 'GET',
                url: 'api/compte-analytiques-print/' + vm.classe.id,
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
