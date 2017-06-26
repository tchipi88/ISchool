(function() {
    'use strict';
    angular
        .module('app')
        .factory('Employe', Employe);

    Employe.$inject = ['$resource','DateUtils'];

    function Employe ($resource,DateUtils) {
        var resourceUrl =  'api/employes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
             'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                          data.dateEntree =DateUtils.convertLocalDateFromServer(data.dateEntree);
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
                     copy.dateEntree =DateUtils.convertLocalDateToServer(copy.dateEntree);
 copy.dateNaissance =DateUtils.convertLocalDateToServer(copy.dateNaissance);
 copy.dateDelivranceCNI =DateUtils.convertLocalDateToServer(copy.dateDelivranceCNI);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateEntree =DateUtils.convertLocalDateToServer(copy.dateEntree);
 copy.dateNaissance =DateUtils.convertLocalDateToServer(copy.dateNaissance);
 copy.dateDelivranceCNI =DateUtils.convertLocalDateToServer(copy.dateDelivranceCNI);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
