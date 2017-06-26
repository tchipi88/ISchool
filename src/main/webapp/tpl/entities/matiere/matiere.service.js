(function() {
    'use strict';
    angular
        .module('app')
        .factory('Matiere', Matiere);

    Matiere.$inject = ['$resource','DateUtils'];

    function Matiere ($resource,DateUtils) {
        var resourceUrl =  'api/matieres/:id';

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
