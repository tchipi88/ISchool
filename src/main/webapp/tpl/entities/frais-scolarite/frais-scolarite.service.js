(function() {
    'use strict';
    angular
        .module('app')
        .factory('FraisScolarite', FraisScolarite);

    FraisScolarite.$inject = ['$resource','DateUtils'];

    function FraisScolarite ($resource,DateUtils) {
        var resourceUrl =  'api/frais-scolarites/:id';

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
            'save': { method:'POST' , isArray: true}
        });
    }
})();
