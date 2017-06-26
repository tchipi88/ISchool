(function() {
    'use strict';
    angular
        .module('app')
        .factory('Classe', Classe);

    Classe.$inject = ['$resource','DateUtils'];

    function Classe ($resource,DateUtils) {
        var resourceUrl =  'api/classes/:id';

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
