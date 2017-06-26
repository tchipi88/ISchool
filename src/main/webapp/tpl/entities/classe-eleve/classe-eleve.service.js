(function() {
    'use strict';
    angular
        .module('app')
        .factory('ClasseEleve', ClasseEleve);

    ClasseEleve.$inject = ['$resource','DateUtils'];

    function ClasseEleve ($resource,DateUtils) {
        var resourceUrl =  'api/classe-eleves/:id';

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
