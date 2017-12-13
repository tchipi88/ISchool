(function () {
    'use strict';

    angular
            .module('app')
            .controller('CoursController', CoursController);

    CoursController.$inject = ['$state', '$http', 'Cours', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'Professeur', 'Classe'];

    function CoursController($state, $http, Cours, ParseLinks, AlertService, paginationConstants, pagingParams, Professeur, Classe) {

        var vm = this;


        vm.loadAll = loadAll;


        
       
        vm.save = save;
        
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

        function loadAll() {
            $http.get('api/coursss/' + vm.classe.id)
                    .success(function (data) {
                        vm.courss = data;
                    });
                    
                 

        }



        function save() {
            vm.isSaving = true;
            Cours.save(vm.courss, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess(result) {
            vm.isSaving = false;
            vm.edit = false;
            loadAll();
            AlertService.success("");
        }

        function onSaveError(error) {
            vm.isSaving = false;
            AlertService.error(error.data.message);
        }

    }
})();
