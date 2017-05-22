app.controller("FormController",
        function FormController($timeout, $scope, $modalInstance, $http, toaster, ErrorService, CrudService, row, formmodel, DateUtils, DataUtils) {
            $scope.categorie = formmodel.data.categorie;
            $scope.errorService = ErrorService;
            $scope.errorService.clear();
            $scope.tsoftitem = row;
            $scope.formmodel = formmodel.data;
            $scope.byteSize = DataUtils.byteSize;
            $scope.openFile = DataUtils.openFile;

            $timeout(function () {
                angular.element('.form-group:eq(1)>input').focus();
            });

            $scope.save = function () {
                $scope.errorService.setwaiting();
                angular.forEach($scope.formmodel.datecolumns, function (value, key) {
                    $scope.tsoftitem[value.name] = DateUtils.convertLocalDateToServer($scope.tsoftitem[value.name]);
                    ;
                });
                CrudService.save({categorie: $scope.categorie}, $scope.tsoftitem,
                        function (data) {
                            toaster.pop("success", "Success", "Operation Effectuée avec Succès");
                            $scope.errorService.setSucces();
                            $modalInstance.close(data);
                        });
            };

            $scope.closemodal = function () {
                $modalInstance.dismiss('cancel');
            };

            $scope.setMimage = function ($file, fieldName) {
                if ($file && $file.$error === 'pattern') {
                    return;
                }
                if ($file) {
                    DataUtils.toBase64($file, function (base64Data) {
                        $scope.$apply(function () {
                            $scope.tsoftitem[fieldName] = base64Data;
                            $scope.tsoftitem[fieldName + 'ContentType'] = $file.type;
                        });
                    });
                }
            };

            //autocompletion
            $scope.getItems = function (val, subcategorie, name) {
                if (val.length < 4)
                    return;
                return $http.get('autocomplete/' + subcategorie, {
                    params: {
                        cval: val
                    }
                }).then(function (res) {
                    var items = [];
                    angular.forEach(res.data, function (item) {
                        items.push(item);
                    });
                    $scope.formmodel.selects[name] = items;
                    return items;
                });
            };
        });