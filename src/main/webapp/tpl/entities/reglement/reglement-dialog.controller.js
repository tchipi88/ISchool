(function () {
    'use strict';

    angular
            .module('app')
            .controller('ReglementDialogController', ReglementDialogController);

    ReglementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DateUtils', 'DataUtils', 'entity', 'Reglement', 'EleveSearch', 'Caisse'];

    function ReglementDialogController($timeout, $scope, $stateParams, $uibModalInstance, $uibModal,DateUtils, DataUtils, entity, Reglement, EleveSearch, Caisse) {
        var vm = this;

        vm.reglement = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.caisses = Caisse.query();
         vm.search = search;



        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.reglement.id !== null) {
                alert('Mise à jour interdite');
              //  Reglement.update(vm.reglement, onSaveSuccess, onSaveError);
            } else {
                Reglement.save(vm.reglement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('tkbrApp:reglementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }
        function search(query) {
            if (query && query.length >= 4) {
                EleveSearch.query({query: query},
                        function (data) {
                            vm.eleves = data;
                        });
            }
        }


        vm.datePickerOpenStatus.dateVersement = false;


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
                        vm.reglement[fieldName] = base64Data;
                        vm.reglement[fieldName + 'ContentType'] = $file.type;
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
                vm.reglement[fieldname] = item;
            }, function () {

            });
        };

    }
})();
