(function() {
    'use strict';
    angular
        .module('app')
        .factory('Authority', Authority);

    Authority.$inject = ['$resource'];

    function Authority ($resource) {
        var resourceUrl =  'api/authoritys/:id';

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
