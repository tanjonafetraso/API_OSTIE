var App = angular.module("Inscription", ["ui.router", "oitozero.ngSweetAlert", "ngAnimate"]);
App.controller("InscriptionCtrl", function ($http, $scope) {
    //variable globale
    $scope.doigtsLibre = ["POUCE_G", "INDEX_G", "MAJEUR_G", "ANNULAIRE_G", "AURICULAIRE_G", "POUCE_D", "INDEX_D", "MAJEUR_D", "ANNULAIRE_D", "AURICULAIRE_D"];
    resetData();
    getSize();
    $scope.TerminerBoutton = true;
    var progressBar = $('.progress-bar');
    var isAction = false;
    var cpt = 0;
    var timerEntrer;
    var timerSeconde;
    var timeProgressBar;
    var isCapture = false;
    var seconde = 0;

    function getSize() {
        $http({
            method: "GET",
            url: "getsizeAction",
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (resultat) {

            // alert(resultat.data.sizeUtilisateur);
            var xValues = ["Utilisateurs:" + resultat.data.sizeUtilisateur, "Empreintes:" + resultat.data.sizeEmpreintes];
            var yValues = [resultat.data.sizeUtilisateur, resultat.data.sizeEmpreintes];
            var barColors = [
                "#1fa055",
                "#357ab7"

            ];

            new Chart("myChart", {
                type: "pie",
                data: {
                    labels: xValues,
                    datasets: [{
                            backgroundColor: barColors,
                            data: yValues
                        }]
                },
                options: {
                    title: {
                        display: true,
                        text: "Nombre total d'utilisateur et d'empreinte dans la base de données"
                    }
                }
            });
        });
    }

    //loading page
    $('#myModal').modal({backdrop: 'static', keyboard: false});
  //  $('#myModal1').modal({backdrop: 'static', keyboard: false});
    timerEntrer = setInterval(function () {
        $('#myModal').modal('show');
        cpt++;
        if (cpt > 1) {
            $('#myModal').modal('hide');
            clearInterval(timerEntrer);
        }
    }, 1000);

    //lancerSecondeIdentification 
    function lancerSecondeIdentification() {
        timerSeconde = setInterval(function () {
            $scope.secondIndentification = seconde;
            seconde++;
        }, 1000);
    }

    //stoperSecondeIdentification
    function stoperSecondeIdentification() {
        clearInterval(timerSeconde);
    }


    //progress Bar
    function showProgressBar(score) {
        var scoreMax = score;
        if (score > 0) {
            scoreMax = score + 10;
        }
        if (score > 100) {
            scoreMax = 100;
        }
        var progress = -1;
        timeProgressBar = setInterval(function () {
            $scope.score = progress;
            progress++;

            progressBar.css('width', progress + '%');
            progressBar.attr('aria-valuenow', progress + '%');
            if (progress === scoreMax) {
                clearInterval(timeProgressBar);
                $scope.TerminerBoutton = false;

            }
        }, 20);
    }

    //select BDD
    $scope.connexionBaseDBB = function () {
        $('#myModal').modal('show');
        $http({
            method: "GET",
            url: "ConnexionDataBASE",
            params: {NameDBB: $scope.data.singleSelect},
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (resultat) {
            $('#myModal').modal('hide');
            if (resultat.data === 1) {
                swal("Connexion", "Connexion établie", "success");
                getSize();
            } else {
                swal("Erreur", "Echec de connexion ", "error");
            }

        });
    };

    //identification terminer
    $scope.resetIdentification = function () {
        $scope.identifierDom = false;
        $scope.TerminerBoutton = true;
        progressBar.css('width', 0 + '%');
        progressBar.attr('aria-valuenow', 0 + '%');
        seconde = 0;
        $scope.secondIndentification = 0;
        resetData();
    };

    //Action d'identification
    $scope.Identifier = function () {

        if (isCapture === true) {
            $scope.identifierDom = true;
            lancerSecondeIdentification();
            $('#myModal').modal('show');
            $http({
                method: "GET",
                url: "IdentifierControllerAction",
                params: {Aff_Id: $scope.AFF_ID},
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (resultat) {
                stoperSecondeIdentification();
                seconde = 0;
                showProgressBar(resultat.data.score);
                $('#myModal').modal('hide');
            });
        } else {
            swal("ERREUR", "Pas d'empreinte digitale", "error");
        }
    };

    getEmpreinte();
    getDecissionByAPI();


    //Action evoyer AFF_ID
    $scope.SendAffId = function () {
        if ($scope.AFF_ID !== "") {
            $('#myModal').modal('show');
            $http({
                method: "GET",
                url: "sendAffIDAction",
                params: {Aff_Id: $scope.AFF_ID},
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (resultat) {

            });
        } else {
            swal("ERREUR", "Veuillez remplir l'AFF_ID", "error");
        }
    };

    //Action reset Data
    function resetData() {
        $scope.AFF_ID = "";
        document.getElementById("peselect").selectedIndex = -1;

        $http({
            method: "POST",
            url: "resetDataAction",
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function () {

        });
    }

    //Action de l'inscription
    $scope.writeEmpreinteAff_Id = function () {
        if (isCapture === true) {
            $('#myModal1').modal('show');
            $http({
                method: "GET",
                url: "writeEmpreinteAff_IdAction",
                params: {Doigt: $scope.data.singleSelect},
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (resultat) {
                $('#myModal1').modal('hide');
                if (resultat.data === 1) {

                    swal("INSCRIPTION", "Inscription bien réussie", "success");
                    getSize();
                    isAction = false;
                    $scope.doigtsLibre = ["POUCE_G", "INDEX_G", "MAJEUR_G", "ANNULAIRE_G", "AURICULAIRE_G", "POUCE_D", "INDEX_D", "MAJEUR_D", "ANNULAIRE_D", "AURICULAIRE_D"];
                    resetData();
                } else if (resultat.data === 2) {

                    swal("ERREUR", "Il faut envoyer l'AFF_ID avant l'inscription", "error");
                    isAction = false;
                    $scope.doigtsLibre = ["POUCE_G", "INDEX_G", "MAJEUR_G", "ANNULAIRE_G", "AURICULAIRE_G", "POUCE_D", "INDEX_D", "MAJEUR_D", "ANNULAIRE_D", "AURICULAIRE_D"];
                    resetData();
                } else {
                    swal("ERREUR", "Opération non effectuée", "error");
                }
            });
        } else {
            swal("ERREUR", "Pas d'empreinte digitale", "error");
        }
    };

    //recuper et afficher l'mpreinte 
    function getEmpreinte() {
        setInterval(function () {
            $http({
                method: "GET",
                url: "getEmpreinte/API",
                headers: {
                    'Content-type': 'application/json'
                }
            }).then(function (resultat) {
                var image = document.getElementById("idImage");
                if (resultat.data.dataEmpreinte !== undefined && resultat.data.dataEmpreinte !== "" && resultat.data.dataEmpreinte !== null) {

                    image.setAttribute("src", "data:image/bmp;base64," + resultat.data.dataEmpreinte);
                    isCapture = true;
                } else {
                    image.setAttribute("src", "");
                    isCapture = false;
                }
            });
        }, 1000);
    }

    //recuper la decisione de l'API soit inscription soit identification
    function getDecissionByAPI() {
        setInterval(function () {
            $http({
                method: "GET",
                url: "getDecissionByAPIAction",
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (resultat) {
                console.log(resultat.data.deccisionAction)
                if ((resultat.data.deccisionAction !== undefined && resultat.data.deccisionAction !== '' && resultat.data.deccisionAction !== null) && (isAction === false)) {
                    $('#myModal').modal('hide');
                    swal("Ce collaborateur doit s'inscrire", " ", "info");
                    isAction = true;
                    if (resultat.data.doigtLibre.length > 0) {
                        var doigts = resultat.data.doigtLibre.split(',');
                        var listeDoigtLibre = [];

                        for (var i = 0, max = doigts.length; i < max - 1; i++) {
                            listeDoigtLibre.push(doigts[i]);
                        }
                        $scope.doigtsLibre = listeDoigtLibre;
                    }
                }
            });

        }, 3000);
    }
    ;
});