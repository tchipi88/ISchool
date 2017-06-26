(function () {
    'use strict';

    angular
            .module('app')
            .controller('CoursDialogController', CoursDialogController);

    CoursDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal', 'DataUtils', 'entity', 'Cours', 'Matiere', 'Professeur', 'Classe'];

    function CoursDialogController($timeout, $scope, $stateParams, $uibModalInstance, $uibModal, DataUtils, entity, Cours, Matiere, Professeur, Classe) {
        var vm = this;

        vm.cours = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.professeurs = Professeur.query();
        vm.classes = Classe.query();
        vm.matieres = Matiere.query();
        
        vm.checkMatiere=checkMatiere;



        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.cours.id !== null) {
                Cours.update(vm.cours, onSaveSuccess, onSaveError);
            } else {
                Cours.save(vm.cours, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('tkbrApp:coursUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        function checkMatiere(){
            
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
                        vm.cours[fieldName] = base64Data;
                        vm.cours[fieldName + 'ContentType'] = $file.type;
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
                vm.cours[fieldname] = item;
            }, function () {

            });
        };

    }
})();
