(function() {
    'use strict';
    angular
        .module('app')
        .factory('Note', Note);

    Note.$inject = ['$resource','DateUtils'];

    function Note ($resource,DateUtils) {
        var resourceUrl =  'api/notes/:id';

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
