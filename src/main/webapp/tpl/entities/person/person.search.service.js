(function() {
    'use strict';

    angular
        .module('app')
        .factory('PersonSearch', PersonSearch);

    PersonSearch.$inject = ['$resource'];

    function PersonSearch($resource) {
        var resourceUrl =  'api/_search/persons/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
