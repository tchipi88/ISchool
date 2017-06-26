(function() {
    'use strict';
    angular
        .module('app')
        .factory('EleveConduite', EleveConduite);

    EleveConduite.$inject = ['$resource','DateUtils'];

    function EleveConduite ($resource,DateUtils) {
        var resourceUrl =  'api/eleve-conduites/:id';

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
