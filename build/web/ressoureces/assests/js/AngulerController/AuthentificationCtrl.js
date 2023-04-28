var App = angular.module("Authentification", ["ui.router", "oitozero.ngSweetAlert", "ngAnimate"]);
App.controller("CtrlAuthentification", function ($http, $scope) {
    $scope.Authentification = function () {
        $http({
            method: "GET",
            url: "AuthentificationAction",
            params: {Username: $scope.username, Password: $scope.password},
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (resultat) {
           // alert(resultat.data);
            if (resultat.data ===true) {
                $scope.lien = "Inscription";
                var a = document.querySelector("a");
                a.setAttribute("href", "Inscription");
                a.click();
            }else{
                 swal('Erreur !', 'Erreur d\' Authentification', 'error');
            }
        });
    };
});