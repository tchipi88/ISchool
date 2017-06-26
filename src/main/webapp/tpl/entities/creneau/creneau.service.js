(function() {
    'use strict';
    angular
        .module('app')
        .factory('Creneau', Creneau);

    Creneau.$inject = ['$resource','DateUtils'];

    function Creneau ($resource,DateUtils) {
        var resourceUrl =  'api/creneaus/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                         data.heureDebut = DateUtils.convertLocalTimeFromServer(data.heureDebut);
                         data.heureFin = DateUtils.convertLocalTimeFromServer(data.heureFin);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.heureDebut = DateUtils.convertLocalTimeToServer(copy.heureDebut);
                    copy.heureFin = DateUtils.convertLocalTimeToServer(copy.heureFin);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.heureDebut = DateUtils.convertLocalTimeToServer(copy.heureDebut);
                    copy.heureFin = DateUtils.convertLocalTimeToServer(copy.heureFin);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
