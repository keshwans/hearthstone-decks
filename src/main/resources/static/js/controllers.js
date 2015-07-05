var hearthstoneApp = angular.module('hearthstoneApp', [ 'angucomplete',
		'ngTagsInput' ])

hearthstoneApp.controller('IndexController', [
		'$scope',
		'$http',
		function($scope, $http) {

			$http.get('/api/cards').success(function(data) {
				$scope.cards = data;
			})

			$scope.diff = function() {
				var cards = [];
				if ($scope.searchCards != null) {
					cards = $scope.searchCards.map(function(card) {
						return card.name
					});
				}
				$http.get('/api/diffs', {
					params : {
						collection : $scope.collection,
						cards : cards
					}
				}).success(function(data) {
					$scope.diffs = data;
				})
			}

			$scope.updateCollection = function() {
				console.log("updateCollection: " + $scope.collection);
				$scope.diff();
			}

			$scope.getCardsMatchingName = function(query) {
				console.log("getCardsMatchingName: " + query);
				return $scope.cards.filter(function(card) {
					return card.name.toLowerCase().startsWith(
							query.toLowerCase());
				});
			}

			$scope.collection = "";
			$scope.diff();

		} ]);