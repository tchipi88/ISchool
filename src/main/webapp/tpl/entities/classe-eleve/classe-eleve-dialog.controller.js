(function () {
    'use strict';

    angular
            .module('app')
            .controller('ClasseEleveDialogController', ClasseEleveDialogController);

    ClasseEleveDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DateUtils', 'DataUtils', 'entity', 'ClasseEleve', 'EleveSearch', 'Annee', 'Classe'];

    function ClasseEleveDialogController($timeout, $scope, $stateParams, $uibModalInstance, $uibModal,DateUtils, DataUtils, entity, ClasseEleve, EleveSearch, Annee, Classe) {
        var vm = this;

        vm.classeEleve = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.classes = Classe.query();
        vm.search = search;



        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }


        function search(query) {
            if (query  && query.length>=4) {
                EleveSearch.query({query: query},
                        function (data) {
                            vm.eleves = data;
                        });
            }
        }

        function save() {
            vm.isSaving = true;
            if (vm.classeEleve.id !== null) {
                ClasseEleve.update(vm.classeEleve, onSaveSuccess, onSaveError);
            } else {
                ClasseEleve.save(vm.classeEleve, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('tkbrApp:classeEleveUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }




        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.setMimage = function ($file, fieldName) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function (base64Data) {
                    $scope.$apply(function () {
                        vm.classeEleve[fieldName] = base64Data;
                        vm.classeEleve[fieldName + 'ContentType'] = $file.type;
                    });
                });
            }
        };

        vm.zoomColumn = function (lookupCtrl, lookupTemplate, fieldname, entity) {
            $uibModal.open({
                templateUrl: 'tpl/entities/' + lookupTemplate + '/' + lookupTemplate + '-dialog.html',
                controller: lookupCtrl + 'DialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                         entity.dateNaissance = DateUtils.convertLocalDateFromServer(entity.dateNaissance);
                        return entity;
                    }
                }
            }).result.then(function (item) {
                vm.classeEleve[fieldname] = item;
            }, function () {

            });
        };

    }
})();
