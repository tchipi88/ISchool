(function() {
    'use strict';
    angular
        .module('app')
        .factory('ProfesseurAbsence', ProfesseurAbsence);

    ProfesseurAbsence.$inject = ['$resource','DateUtils'];

    function ProfesseurAbsence ($resource,DateUtils) {
        var resourceUrl =  'api/professeur-absences/:id';

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
            'update': { method:'PUT' }
        });
    }
})();
