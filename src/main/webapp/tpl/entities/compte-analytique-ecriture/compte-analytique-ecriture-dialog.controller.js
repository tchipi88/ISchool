(function() {
    'use strict';

    angular
        .module('app')
        .controller('CompteAnalytiqueEcritureDialogController', CompteAnalytiqueEcritureDialogController);

    CompteAnalytiqueEcritureDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', 'CompteAnalytiqueEcriture','CompteAnalytique'];

    function CompteAnalytiqueEcritureDialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, CompteAnalytiqueEcriture ,CompteAnalytique) {
        var vm = this;

        vm.compteAnalytiqueEcriture = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.comptes = CompteAnalytique.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.compteAnalytiqueEcriture.id !== null) {
                CompteAnalytiqueEcriture.update(vm.compteAnalytiqueEcriture, onSaveSuccess, onSaveError);
            } else {
                CompteAnalytiqueEcriture.save(vm.compteAnalytiqueEcriture, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:compteAnalytiqueEcritureUpdate', result);
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
                            vm.compteAnalytiqueEcriture[fieldName] = base64Data;
                            vm.compteAnalytiqueEcriture[fieldName + 'ContentType'] = $file.type;
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
                        vm.compteAnalytiqueEcriture[fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();
