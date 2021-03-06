(function() {
    'use strict';

    angular
        .module('app')
        .controller('EleveAbsenceDialogController', EleveAbsenceDialogController);

    EleveAbsenceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', 'EleveAbsence','ClasseEleve'];

    function EleveAbsenceDialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, EleveAbsence ,ClasseEleve) {
        var vm = this;

        vm.eleveAbsence = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.classeeleves = ClasseEleve.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.eleveAbsence.id !== null) {
                EleveAbsence.update(vm.eleveAbsence, onSaveSuccess, onSaveError);
            } else {
                EleveAbsence.save(vm.eleveAbsence, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:eleveAbsenceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        
        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
         vm.setMimage = function ($file, fieldName) {
                if ($file && $file.$error === 'pattern') {
                    return;
                }
                if ($file) {
                    DataUtils.toBase64($file, function (base64Data) {
                        $scope.$apply(function () {
                            vm.eleveAbsence[fieldName] = base64Data;
                            vm.eleveAbsence[fieldName + 'ContentType'] = $file.type;
                        });
                    });
                }
            };
            
            vm.zoomColumn = function (lookupCtrl,lookupTemplate, fieldname, entity) {
                $uibModal.open({
                    templateUrl: 'tpl/entities/'+lookupTemplate+'/'+lookupTemplate+'-dialog.html',
                    controller: lookupCtrl+'DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return entity;
                        }
                    }
                }).result.then(function(item) {
                        vm.eleveAbsence[fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();
