angular.module('resourceApp', ['ngResource'])
 .factory('CrudService', ['$resource', function($resource) {
 return $resource(':categorie/:id');
}]);