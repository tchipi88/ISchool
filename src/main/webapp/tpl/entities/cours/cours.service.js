(function() {
    'use strict';
    angular
        .module('app')
        .factory('Cours', Cours);

    Cours.$inject = ['$resource','DateUtils'];

    function Cours ($resource,DateUtils) {
        var resourceUrl =  'api/courss/:id';

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
