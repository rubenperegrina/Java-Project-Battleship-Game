/**
 * Created by rubenperegrina on 16/12/16.
 */

var url = "/api/games";


jQuery(document).ready(function ($) {

    getCookies();


    //Create Game/////////////////////////////////////////////
    $('#creategame').on('click', function (event) {
        event.preventDefault();
        $.ajax({
            timeout: 1000,
            type: 'POST',
            url: '/api/games'


        }).done(function (data, textStatus, jqXHR) {
            var nn = jqXHR.responseJSON.gpid;
            alert('The Game has created!');
            window.location.replace("/game.html?gp=" + nn );


        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert('The Game doesn tcreate!');
        });
    });
});


jQuery(document).ready(function ($) {



    $('#logout').hide();
    $('#creategame').hide();
    //Log In//////////////////////////////////////////////


    $('#login').on('click', function (event) {
        var loginmessage = ('Welcome' + $('#username').val() + '!');
        login(loginmessage);
    });


    //Log Out//////////////////////////////////////////////
    $('#logout').on('click', function (event) {
        event.preventDefault();

        $.ajax({
            timeout: 1000,
            type: 'POST',
            url: '/api/logout'


        }).done(function (data, textStatus, jqXHR) {

            alert('Bye Bye' + $('#username').val() + '!');
            $('#logout').hide();
            $('#creategame').hide();
            $('.table').hide();
            $('.gamelist').hide();
            $(".head").empty();
            $(".gamelist").empty();
            $('.login-wrap').show();
            $('body').addClass('image1');

        }).fail(function (jqXHR, textStatus, errorThrown) {
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
        var data = 'username=' + $('#username1').val() + '&password=' + $('#password1').val();
        $.ajax({
            data: data,
            timeout: 1000,
            type: 'POST',
            url: '/api/players'


        }).done(function (data, textStatus, jqXHR) {

            var signinmessage = ('Welcome ' + $('#username1').val() + '!, you are a new user!');
            $.getJSON(url, scoreGrid);
            login(signinmessage);

        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert('Booh! Wrong credentials, or user already exist!');
        });
    });
});


function scoreGrid(data) {

    var names = [];
    var scores = {};
    var won = {};
    var lost = {};
    var tied = {};

    $.each(data.games, function (index) {

        $.each(data.games[index].Players, function (index2) {

            var email = data.games[index].Players[index2].name;
            var score = data.games[index].Players[index2].score;


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

    $.each(data.games, function (index) {


        var li = $("<li class='singleGame'></li>");
        var url;

        li.append("<span class='td'>" + 'Game: ' + data.games[index].id + "" + "</span>");

        $.each(data.games[index].Players, function (index2) {
            /*li.append("<span class='td'>" + 'Gp Id: ' + data.games[index].Players[index2].gpid + "/ " + "</span>");*/
           /* li.append("<span class='td'>" + 'Player Id: ' + data.games[index].Players[index2].id + "/ " + "</span>");*/
            li.append("<span> " + data.games[index].Players[index2].name + "</span>");

            if(data.games[index].Players.length == 1) {
                var button = $('<button/>', {
                    text: 'JOIN',
                    id: 'btn' + data.games[index].id,
                    class: 'btn btn-primary',
                    click: function() {
                        url2 = 'api/game/' + data.games[index].id + '/players';
                        $.ajax({
                            timeout: 1000,
                            type: 'POST',
                            url: url2

                        }).done(function(data, textStatus, jqXHR) {
                            gpDestination = jqXHR.responseJSON.gpid;
                            url = "game.html?gp=" + gpDestination;
                            window.location.replace(url);

                        }).fail(function() {
                            alert('Something fail!');
                        })
                    }
                })
            }

            if (data.player[0].id == data.games[index].Players[index2].id) {
                url = 'game.html?gp=' + data.games[index].Players[index2].gpid;
            }else {
                li.append(button);
            }
        })


            if (url != undefined) {

                var playButton = $('<button/>', {
                    text: 'PLAY',
                    id: 'play_game' + data.games[index].id,
                    class: 'btn btn-warning',
                    click: function() {
                        window.location.replace(url);
                    }
                });

                li.append(playButton);
                $(".gamelist")
                    .append(li)

            } else {
                $(".gamelist")
                    .append(li)
            }
    })
}


function setCookies() {

    var username = $('#username').val();
    var password = $('#password').val();
    // set cookies to expire in 14 days
    $.cookie('username', username, {expires: 14});
    $.cookie('password', password, {expires: 14});
    $.cookie('remember', true, {expires: 14});

}

function getCookies() {

    var remember = $.cookie('remember');

    if (remember == 'true') {
        var username = $.cookie('username');
        var password = $.cookie('password');
        // autofill the fields
        $('#username').attr("value", username);
        $('#password').attr("value", password);
    }

}

function login(welcomemessage) {

    event.preventDefault();
    var data = 'username=' + $('#username').val() + '&password=' + $('#password').val();
    $.ajax({
        data: data,
        timeout: 1000,
        type: 'POST',
        url: '/api/login'


    }).done(function (data, textStatus, jqXHR) {

        alert(welcomemessage);
        $.getJSON(url, scoreGrid);
        $('.table').show();
        $('.gamelist').show();
        $('#logout').show();
        $('#creategame').show();
        $('.login-wrap').hide();
        $('body').removeClass('image1');
        $('body').addClass('image2');

    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert('Booh! Wrong credentials, try again!');
    });
}