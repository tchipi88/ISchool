(function() {
    'use strict';
    angular
        .module('app')
        .factory('Coefficient', Coefficient);

    Coefficient.$inject = ['$resource','DateUtils'];

    function Coefficient ($resource,DateUtils) {
        var resourceUrl =  'api/coefficients/:id';

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
