(function () {
    'use strict';
    angular
            .module('app')
            .factory('Timetable', Timetable);

    Timetable.$inject = ['$resource', 'DateUtils'];

    function Timetable($resource, DateUtils) {
        var resourceUrl = 'api/timetables/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {method: 'PUT'}
        });
    }
})();
