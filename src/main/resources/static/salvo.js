/**
 * Created by rubenperegrina on 16/12/16.
 */



jQuery(document).ready(function ($) {

    var url = "/api/games";

    $('#logout').hide();
    //Log In//////////////////////////////////////////////
    $('#login').on('click', function (event) {
        event.preventDefault();
        var data = 'username=' + $('#username').val() + '&password=' + $('#password').val();
        $.ajax({
            data: data,
            timeout: 1000,
            type: 'POST',
            url: '/api/login'


        }).done(function(data, textStatus, jqXHR) {

            alert('Welcome' + $('#username').val() + '!');
            $.getJSON(url, scoreGrid);
            $('.table').show();
            $('#loginform').hide();
            $('#logout').show();

        }).fail(function(jqXHR, textStatus, errorThrown) {
            alert('Booh! Wrong credentials, try again!');
        });
    });
    //Log Out//////////////////////////////////////////////
    $('#logout').on('click', function (event) {
        event.preventDefault();

        $.ajax({
            timeout: 1000,
            type: 'POST',
            url: '/api/logout'


        }).done(function(data, textStatus, jqXHR) {

            alert('Bye Bye' + $('#username').val() + '!');
            $('#loginform').show();
            $('#logout').hide();
            $('.table').hide();
            $(".head").empty();

        }).fail(function(jqXHR, textStatus, errorThrown) {
            alert('Check your conexion and try again bro!');
        });
    })
});



jQuery(document).ready(function ($) {

    var url = "/api/games";

    $('#logout').hide();

    //Sign /In/////////////////////////////////////////////
    $('#signin').on('click', function (event) {
        event.preventDefault();
        var data = 'username=' + $('#username').val() + '&password=' + $('#password').val();
        $.ajax({
            data: data,
            timeout: 1000,
            type: 'POST',
            url: '/api/players'


        }).done(function (data, textStatus, jqXHR) {

            alert('Welcome' + $('#username').val() + '!, you are a new user!');
            $.getJSON(url, scoreGrid);
            $('.table').show();
            $('#loginform').hide();
            $('#logout').show();

            login();

        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert('Booh! Wrong credentials, or user already exist!');
        });
    });


    function login() {
        var data = 'username=' + $('#username').val() + '&password=' + $('#password').val();
        $.ajax({
            data: data,
            timeout: 1000,
            type: 'POST',
            url: '/api/login'


        }).done(function(data, textStatus, jqXHR) {

            $.getJSON(url, scoreGrid);
            $('.table').show();
            $('#loginform').hide();
            $('#logout').show();

        }).fail(function(jqXHR, textStatus, errorThrown) {
            alert('Booh! Wrong credentials, try again!');
        });
    }
});




function scoreGrid(data) {

    console.log(data);
    var names = [];
    var scores = {};
    var won = {};
    var lost = {};
    var tied = {};

    $.each(data.games, function (index) {
        /*console.log(data[index].gamePlayers);*/

        $.each(data.games[index].gamePlayers, function (index2) {
            /*console.log(data[index].gamePlayers[index2].email);*/
            var email = data.games[index].gamePlayers[index2].email;
            var score = data.games[index].gamePlayers[index2].score;


            if ($.inArray(email, names) == -1) {
                names.push(email);

                if (score != undefined) {
                    scores[email] = score;
                }
            } else {
                if (score != undefined) {
                    scores[email] += score;

                }
            }
            switch (score) {
                case 0:
                    if (lost[email]) {

                        lost[email] += 1
                    } else {
                        lost[email] = 1
                    }
                    break;
                case 0.5:
                    if (tied[email]) {

                        tied[email] += 1
                    } else {
                        tied[email] = 1
                    }
                    break;
                case 1:
                    if (won[email]) {

                        won[email] += 1
                    } else {
                        won[email] = 1
                    }
            }
        })

    })
    /*console.log(names);
    console.log(scores);
    console.log(won);
    console.log(lost);
    console.log(tied);*/

    var tittle = ["Name", "Total", "Won", "Lost", "Tied"];

    $(".scores").empty();

    var row = $("<tr class = 'row' ></tr>")
    $.each(tittle, function (index) {

        row.append("<td class='cells info'>" + tittle[index] + "</td>");

        $(".head").append(row);
    })

    $.each(names, function (index) {

        var row = $("<tr class = 'row' ></tr>")

        if (scores[names[index]] == undefined) {
            scores[names[index]] = "-";
        }
        if (won[names[index]] == undefined) {
            won[names[index]] = "-";
        }
        if (lost[names[index]] == undefined) {
            lost[names[index]] = "-";
        }
        if (tied[names[index]] == undefined) {
            tied[names[index]] = "-";
        }


        row.append("<td class='cells'>" + names[index] + "</td>");
        row.append("<td class='cells'>" + scores[names[index]] + "</td>");
        row.append("<td class='cells'>" + won[names[index]] + "</td>");
        row.append("<td class='cells'>" + lost[names[index]] + "</td>");
        row.append("<td class='cells'>" + tied[names[index]] + "</td>");

        $(".scores").append(row);
    })
}


/*function scoreGrid(data) {
 for(var i = 0; i<data.length; i++) {
 console.log(data);
 /!*var length1 = data[i].gamePlayers[0];*!/

 var Player1 = data[i].gamePlayers[i];
 var Player2 = data[i].gamePlayers[i];
 console.log(Player1.email);
 console.log(Player2.email);
 console.log(Player1.score);
 console.log(Player2.score);

 if(Player1.score == 1) {
 var win = [player] = 1
 }
 console.log(win);
 /!*console.log("1---" + Player1.email + "-" + Player1.score);
 console.log("2---" + Player2.email + "-" + Player2.score);*!/
 /!*console.log("Score 1, 2---" + Player1.score + "-" + Player2.score);*!/
 }

 console.log(Player2.score);

 for(var j = 0; j<data.length; j++) {

 var Player1 = data[i].gamePlayers[j];
 var Player2 = data[i].gamePlayers[j];


 }

 var players = Player1.email + Player2.email;
 var scores = Player1.score + Player2.score;


 var tittle = [" ", "Name", "Total", "Won", "Lost", "Tied"];

 scoreGrid();*/
/*$(function() {

 console.log("Getting the JSON");

 $.getJSON(url, jsongames);

 console.log("JSON is coming...");
 });*/


/*function jsongames(data) {

 console.log(data);

 var gameslist = document.getElementById("gameslist");

 var list = "<ol>";

 for (var i = 0; i<data.length; i++) {

 var Player1 = data[i].gamePlayers[0];
 var Player2 = data[i].gamePlayers[1];
 /!*console.log("1---" + Player1.email + "-" + Player1.score);
 console.log("2---" + Player2.email + "-" + Player2.score);*!/

 if(Player2) {
 list += "<li>" + data[i].id +"-" + new Date(data[i].creation).toLocaleString()
 + "-" + Player1.email + "-" + Player1.id + "-" + Player1.player + "-" + "Score " + Player1.score
 +  " VS " + Player2.email + "-" + Player2.id + "-" + Player2.player + "-" + "Score " + Player2.score + "</li>";
 }else {
 list += "<li>" + data[i].id +"-" + new Date(data[i].creation).toLocaleString()
 + "-" + Player1.email + "-" + Player1.id + "-" + Player1.player + "-" + "Score " + Player1.score + "</li>";
 }
 }

 list += "</ol>";
 gameslist.innerHTML = list;
 }*/

/*$(function() {
 $.getJSON(url, scoreGrid);
 });

 function scoreGrid(data) {
 for(var i = 0; i<data.length; i++) {

 /!*var length1 = data[i].gamePlayers[0];*!/

 var Player1 = data[i].gamePlayers[0];
 var Player2 = data[i].gamePlayers[1];
 /!*console.log("1---" + Player1.email + "-" + Player1.score);
 console.log("2---" + Player2.email + "-" + Player2.score);*!/
 console.log("Score 1, 2---" + Player1.score + "-" + Player2.score);

 }*/

/*for (var i = 0; i<length1.length; i++) {

 /!*console.log("data[i].gamePlayers[0].email " + data[i].gamePlayers[0].email);
 console.log("data[i].gamePlayers[i].email " + data[i].gamePlayers[i].email);
 console.log("data[i].gamePlayers[0] " + data[i].gamePlayers[0]);*!/

 var player = length1.email;
 var emaillength = length1.email.length;
 }*/

/*var players = Player1.email + Player2.email;
 var scores = Player1.score + Player2.score;
 console.log(players);
 console.log(scores);

 var tittle = [" ", "Name", "Total", "Won", "Lost", "Tied"];*/
/*var player = data[i].gamePlayers[0].email;*/

/*$(".scores").empty();

 for (var i = 0; i < (5 + 1); i++) {
 var row = $("<tr class='rows'></tr>");

 for (var j = 0; j < (5 + 1); j++)

 if (i == 0) {

 row.append("<td class='cells heads'>" + tittle[j] + "</td>");

 } else {

 if (j == 0) {

 row.append("<td class='cells heads'>" + players[i - 1] + "</td>");
 row.append("<td class='cells heads'>" + scores[i - 1] + "</td>");

 } else {

 row.append("<td class='cells' id='" + players[i-1] + scores[i-1] + tittle[j] + "'></td>");

 }

 }
 $(".scores").append(row);
 }

 }*/

/*
 scoreGrid();*/
