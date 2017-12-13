(function () {
    'use strict';

    angular
            .module('app')
            .controller('NoteController', NoteController);

    NoteController.$inject = ['$http', '$state', 'DataUtils', 'Note', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'Classe', 'Matiere'];

    function NoteController($http, $state, DataUtils, Note, ParseLinks, AlertService, paginationConstants, pagingParams, Classe, Matiere) {

        var vm = this;


        vm.loadAll = loadAll;
        vm.printReleve = printReleve;
        vm.save = save;

        vm.loadmatiere = loadmatiere;

             loadData();

 function loadData() {
        $http.get("api/classess")
        .success(function(data) {
            vm.classes = data;
        });
        
        
 }


        function loadAll() {
            Note.query({
                classe: vm.classe.id,
                matiere: vm.matiere.id,
                numseq: vm.numseq
            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.notes = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }


        function loadmatiere() {
            $http.get('api/matiereclasse/' + vm.classe.serie.id)
                    .success(function (data) {
                        vm.numseq = null;
                        vm.matiere = null;
                        vm.matieres = data;
                    });
        }




     
        function save() {
            vm.isSaving = true;
            Note.save(vm.notes, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess(result) {

            vm.isSaving = false;
            vm.edit = false;
            vm.notes = result;
            AlertService.success("");
        }

        function onSaveError(error) {
            vm.isSaving = false;
            AlertService.error(error.data.message);
        }
        
        
        function getFileNameFromHeader(header) {
            if (!header)
                return null;
            var result = header.split(";")[1].trim().split("=")[1];
            return result.replace(/"/g, '');
        }

        function printReleve() {
            $http({
                method: 'GET',
                url: 'api/printReleve/' + vm.classe.id,
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
