(function () {
    'use strict';

    angular
            .module('app')
            .controller('TimetableController', TimetableController);

    TimetableController.$inject = ['toaster', '$http', '$state', '$uibModal', 'Timetable', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'Professeur', 'Classe'];

    function TimetableController(toaster, $http, $state, $uibModal, Timetable, ParseLinks, AlertService, paginationConstants, pagingParams, Professeur, Classe) {

        var vm = this;

        vm.edtClasse = edtClasse;
        vm.edtProf = edtProf;
        
        vm.printEDTClasse = printEDTClasse;
        vm.printEDTProf = printEDTProf;


       
        vm.generateEdt = generateEdt;
        
             loadData();

 function loadData() {
        $http.get("api/classess")
        .success(function(data) {
            vm.classes = data;
        });
        
        $http.get("api/professeurss")
        .success(function(data) {
            vm.profs = data;
        });
 }


        vm.form = function (entity) {
            $uibModal.open({
                templateUrl: 'tpl/entities/timetable/timetable-dialog.html',
                controller: 'TimetableDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return entity;
                    }
                }
            }).result.then(function () {
                edtClasse();
            }, function () {

            });
        };


        function generateEdt() {
            var r = confirm("Confirm ?");
            if (r === true) {
                $http.get('api/timetablesRun')
                        .success(function (data) {
                            toaster.pop("success", "Success", "Generation Effectuée avec Succès");
                        });
            }
            ;
        }




        function edtClasse() {
            vm.prof = null;
            $http.get('api/getTimetableC/' + vm.classe.id)
                    .success(function (data) {
                        vm.timetables = data;
                    });
        }
        ;
        function edtProf() {
            vm.classe = null;
            $http.get('api/getTimetableP/' + vm.prof.id)
                    .success(function (data) {
                        vm.timetables = data;
                    });
        }
         function getFileNameFromHeader(header) {
            if (!header)
                return null;
            var result = header.split(";")[1].trim().split("=")[1];
            return result.replace(/"/g, '');
        }

        function printEDTClasse() {
            $http({
                method: 'GET',
                url: 'api/printTimetableC/' + vm.classe.id,
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
        function printEDTProf() {
            $http({
                method: 'GET',
                url: 'api/printTimetableP/' + vm.prof.id,
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
