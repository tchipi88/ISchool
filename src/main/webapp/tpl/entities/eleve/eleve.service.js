(function () {
    'use strict';
    angular
            .module('app')
            .factory('Eleve', Eleve);

    Eleve.$inject = ['$resource', 'DateUtils'];

    function Eleve($resource, DateUtils) {
        var resourceUrl = 'api/eleves/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateNaissance = DateUtils.convertLocalDateFromServer(data.dateNaissance);
                        data.dateDelivranceCNI = DateUtils.convertLocalDateFromServer(data.dateDelivranceCNI);

                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateNaissance = DateUtils.convertLocalDateToServer(copy.dateNaissance);
                    copy.dateDelivranceCNI = DateUtils.convertLocalDateToServer(copy.dateDelivranceCNI);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateNaissance = DateUtils.convertLocalDateToServer(copy.dateNaissance);
                    copy.dateDelivranceCNI = DateUtils.convertLocalDateToServer(copy.dateDelivranceCNI);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
