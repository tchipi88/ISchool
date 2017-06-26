(function() {
    'use strict';
    angular
        .module('app')
        .factory('EleveAbsence', EleveAbsence);

    EleveAbsence.$inject = ['$resource','DateUtils'];

    function EleveAbsence ($resource,DateUtils) {
        var resourceUrl =  'api/eleve-absences/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'save': { method:'POST', isArray: true }
        });
    }
})();
