(function() {
    'use strict';
    angular
        .module('app')
        .factory('CompteAnalytiqueEcriture', CompteAnalytiqueEcriture);

    CompteAnalytiqueEcriture.$inject = ['$resource','DateUtils'];

    function CompteAnalytiqueEcriture ($resource,DateUtils) {
        var resourceUrl =  'api/compte-analytique-ecritures/:id';

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
