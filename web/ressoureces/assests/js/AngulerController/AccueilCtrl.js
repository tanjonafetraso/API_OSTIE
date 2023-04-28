var App = angular.module("AceuilApp", ["ui.router", "oitozero.ngSweetAlert", "ngAnimate"]);
App.controller("AceuilAppCtrl", function ($http, $scope) {
    ViderCaheImageEmpreinte();
    chart();
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

    function chart() {
        google.charts.load('current', {'packages': ['corechart']});
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {
            var data = google.visualization.arrayToDataTable([
                ['Contry', 'Mhl'],
                ['Italy', 54.8],
                ['France', 48.6],
                ['Spain', 44.4],
                ['USA', 23.9],
                ['Argentina', 14.5]
            ]);

            var options = {
                title: 'World Wide Wine Production',
                is3D: true
            };

            var chart = new google.visualization.PieChart(document.getElementById('myChart'));
            chart.draw(data, options);
        }
    }
});

var App = angular.module("MyApp", ["ui.router", "oitozero.ngSweetAlert", "ngAnimate"]);
App.controller("maPageAceuil", function ($http, $scope) {
    var myTimmer;
    $scope.info0 = "Aucune Information prÃªte Ã  Ajouter vers la carte puce ";
    $scope.cacher = true;
    $scope.RechercheDansAPI = function () {
        $scope.ListesCollaborateurs = {};
        $http({
            method: "GET",
            url: "ActionRechercherDansAPI",
            params: {id: $scope.VarMatriculle},
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (resultat) {

            $scope.ListesCollaborateurs = resultat.data;
        });
    };
    $scope.Selectionner = function (collaborateur) {
        $scope.cacher = false;
        $scope.info0 = "Information prÃªte Ã  Ajouter vers la carte puce ";
        $scope.info1 = "Matriculle: ";
        $scope.info2 = "Type: ";
        $scope.info3 = "Nom: ";
        $scope.info4 = "Date de Naissance: ";
        $scope.info5 = "Doigt: ";
        $scope.collaborateurAjouter = collaborateur;


    };
    function runTimer() {

        myTimmer = setInterval(function () {

            $http({
                method: "GET",
                url: "ActionReadDataInCard",
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (resultat) {
                if (resultat.data === undefined || resultat.data === null || resultat.data === "") {
                } else {
                    $scope.DatainCarte = resultat.data;
                    $('#myModal').modal('show');
                    clearInterval(myTimmer);
                }
            });
        }, 1000);
    }
    $scope.Home=function () {
        clearInterval(myTimmer);
    };
    $scope.SacnnerCard = function () {      
        runTimer();
    };
    $scope.redemarrerTimmer = function () {
        runTimer();
    };
    $scope.AjouterVersCarte = function (matriculle, type, nom, dateDenaissaince, doigts) {

        $http({
            method: "GET",
            url: "ActionAjouterDansCarte",
            params: {id: matriculle, type: type, nom: nom, datedenaissaince: dateDenaissaince, doigts: doigts},
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (resultat) {
            if (resultat.data.success === 1) {
                swal('Terminal!', 'Terminal introuvable', 'error');
            } else if (resultat.data.success === 2) {
                swal('Card!', 'Card introuvable', 'error');
            } else if (resultat.data.success === 3) {
                swal('Authentification!', 'Erreur Authentification de la carte', 'error');
            } else {
                swal('Reussite!', 'Ecriture de la carte reussite', 'success');
                $scope.collaborateurAjouter = {};
                $scope.ListesCollaborateurs = {};
                $scope.info0 = "Aucune Information prÃªte Ã  Ajouter vers la carte puce";
                $scope.info1 = "";
                $scope.info2 = "";
                $scope.info3 = "";
                $scope.info4 = "";
                $scope.info5 = "";
                $scope.VarMatriculle = "";
                $scope.cacher = true;
            }
        });
    };


});