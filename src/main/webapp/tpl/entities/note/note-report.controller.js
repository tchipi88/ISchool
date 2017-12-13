(function () {
    'use strict';

    angular
            .module('app')
            .controller('NoteReportController', NoteReportController);

    NoteReportController.$inject = ['$http', '$state', 'DataUtils', 'Note', 'ParseLinks', 'AlertService', 'paginationConstants', 'Classe', 'Matiere'];

    function NoteReportController($http, $state, DataUtils, Note, ParseLinks, AlertService, paginationConstants, Classe, Matiere) {

        var vm = this;


        vm.loadAllSeq = loadAllSeq;
        vm.loadAllTri = loadAllTri;
        vm.changeMode = changeMode;
        vm.printBulletinSeq = printBulletinSeq;
        vm.printBulletinTri = printBulletinTri;
        vm.printBulletinAnnuel = printBulletinAnnuel;
        vm.mode = "S";


loadData();

 function loadData() {
        $http.get("api/classess")
        .success(function(data) {
            vm.classes = data;
        });
        
        $http.get("api/matieress")
        .success(function(data) {
            vm.matieres = data;
        });
        
        
 }

        function changeMode() {
            vm.pv = null;
        }


        function loadAllSeq() {
            vm.pv = null;
            $http.get('api/pvSeq/' + vm.classe.id + '/' + vm.numseq)
                    .success(function (data) {
                        vm.pv = data;
                    });
        }
        function loadAllTri() {
            vm.pv = null;
            $http.get('api/pvTri/' + vm.classe.id + '/' + vm.numtri)
                    .success(function (data) {
                        vm.pv = data;
                    });
        }
        function getFileNameFromHeader(header) {
            if (!header)
                return null;
            var result = header.split(";")[1].trim().split("=")[1];
            return result.replace(/"/g, '');
        }
        function printBulletinSeq() {
            $http({
                method: 'GET',
                url: 'api/bulletin-seq-classe/' + vm.numseq + '/' + vm.classe.id,
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
        function printBulletinTri() {
            $http({
                method: 'GET',
                url: 'api/bulletin-tri-classe/' + vm.numtri + '/' + vm.classe.id,
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
        function printBulletinAnnuel() {
            $http({
                method: 'GET',
                url: 'api/bulletin-annuel-classe/' + vm.classe.id,
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
       



    }
})();
