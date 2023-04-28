var App = angular.module("IdentificationApp", ["ui.router", "oitozero.ngSweetAlert", "ngAnimate"]);
App.controller("IdentificationAppCtrl", function ($http, $scope) {
    ViderCaheImageEmpreinte();
    function ViderCaheImageEmpreinte() {
        $http({
            method: "GET",
            url: "deleteEmpreintImageFtpAction",
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (resultat) {

        });
    }
    ;
});

