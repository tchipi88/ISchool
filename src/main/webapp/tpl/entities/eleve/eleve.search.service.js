(function() {
    'use strict';

    angular
        .module('app')
        .factory('EleveSearch', EleveSearch);

    EleveSearch.$inject = ['$resource'];

    function EleveSearch($resource) {
        var resourceUrl =  'api/_search/eleves/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
