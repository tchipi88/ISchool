(function() {
    'use strict';
    angular
        .module('app')
        .factory('Professeur', Professeur);

    Professeur.$inject = ['$resource','DateUtils'];

    function Professeur ($resource,DateUtils) {
        var resourceUrl =  'api/professeurs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
             'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                          data.dateRecrutement =DateUtils.convertLocalDateFromServer(data.dateRecrutement);
 data.dateNaissance =DateUtils.convertLocalDateFromServer(data.dateNaissance);
 data.dateDelivranceCNI =DateUtils.convertLocalDateFromServer(data.dateDelivranceCNI);

                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateRecrutement =DateUtils.convertLocalDateToServer(copy.dateRecrutement);
 copy.dateNaissance =DateUtils.convertLocalDateToServer(copy.dateNaissance);
 copy.dateDelivranceCNI =DateUtils.convertLocalDateToServer(copy.dateDelivranceCNI);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateRecrutement =DateUtils.convertLocalDateToServer(copy.dateRecrutement);
 copy.dateNaissance =DateUtils.convertLocalDateToServer(copy.dateNaissance);
 copy.dateDelivranceCNI =DateUtils.convertLocalDateToServer(copy.dateDelivranceCNI);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
