(function () {
    'use strict';

    angular
            .module('app')
            .controller('FraisScolariteDialogController', FraisScolariteDialogController);

    FraisScolariteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal', 'DataUtils', 'entity', 'FraisScolarite', 'Annee', 'Serie'];

    function FraisScolariteDialogController($timeout, $scope, $stateParams, $uibModalInstance, $uibModal, DataUtils, entity, FraisScolarite, Annee, Serie) {
        var vm = this;

        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        
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



        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            FraisScolarite.save(vm.fraisScolarites, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess(result) {
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
                        vm.fraisScolarite[fieldName] = base64Data;
                        vm.fraisScolarite[fieldName + 'ContentType'] = $file.type;
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
                        return entity;
                    }
                }
            }).result.then(function (item) {
                vm.fraisScolarite[fieldname] = item;
            }, function () {

            });
        };

    }
})();
