(function() {
    'use strict';

    angular
        .module('app')
        .factory('AuthoritySearch', AuthoritySearch);

    AuthoritySearch.$inject = ['$resource'];

    function AuthoritySearch($resource) {
        var resourceUrl =  'api/_search/authoritys/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
