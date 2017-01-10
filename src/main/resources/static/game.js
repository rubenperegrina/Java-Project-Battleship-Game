/**
 * Created by rubenperegrina on 2/1/17.
 */



var numbers = [" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];
var letters = [ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];
var playerid = 0;

function drawGrid(cells) {
    $(".grid").empty();

    for (var i = 0; i < (cells + 1); i++) {
        var row = $("<tr class='rows'></tr>");

        for (var j = 0; j < (cells + 1); j++)

            if (i == 0) {

                row.append("<td class='cells heads'>" + numbers[j] + "</td>");

            } else {

                if (j == 0) {

                    row.append("<td class='cells heads'>" + letters[i - 1] + "</td>");

                } else {

                    row.append("<td class='cells' id='" + letters[i-1] + numbers[j] + "'></td>");

                }

            }
        $(".grid").append(row);
    }

}

drawGrid(10);

drawShips();

function getParameterByName(name, url) {
    if (!url) {
        url = window.location.href;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function drawShips () {


     var nn = getParameterByName('gp');


    $.getJSON('/api/game_view/' + nn, function(data) {


        $.each(data.ships, function (ship) {

            var location = data.ships[ship].location;
            console.log(data.ships[ship].location);

            $.each(location, function (cell) {


            $('#' + location[cell]).addClass("ship")
            })
        })

        $.each(data.gamePlayers, function(player) {

            var email = data.gamePlayers[player].player.email;
            var id = data.gamePlayers[player].id;

            if (nn == id) {

                $("#names").append(email + "(You)");
                playerid = data.gamePlayers[player].player.id;
            }
            else {

                $("#names").append(email);
            }

        })

        //SALVOES//////////////////////////////////////////////////////////////////////////////////

        $.each(data.salvoes, function (salvo) {


            $.each(data.salvoes[salvo], function (cell) {

                $.each(data.salvoes[salvo][cell].locations, function(cell2) {

                    console.log(data.salvoes[salvo][cell].player);
                    console.log(data.gamePlayers[0].player.id);

                    var salvoCells = data.salvoes[salvo][cell].locations[cell2];

                    if (data.salvoes[salvo][cell].player == playerid) {
                        $("#" + salvoCells + "S").addClass("salvocell");
                        $("#" + salvoCells + "S").html(data.salvoes[salvo][cell].turn);

                    } else {
                        $("#" + salvoCells).addClass("salvocell");
                        $("#" + salvoCells).html(data.salvoes[salvo][cell].turn);
                    }
                        if ($("#" + salvoCells).hasClass("ship") && $("#" + salvoCells).hasClass("salvocell") ) {
                            $("#" + salvoCells).addClass("shoot");
                        }
                })

            })
        })

    } )
}

//SALVOES//////////////////////////////////////////////////////////////////////////////////

function drawSalvoesGrid(cells) {
    $(".salvoes").empty();

    for (var i = 0; i < (cells + 1); i++) {
        var row = $("<tr class='rows'></tr>");

        for (var j = 0; j < (cells + 1); j++)

            if (i == 0) {

                row.append("<td class='cells heads'>" + numbers[j] + "</td>");

            } else {

                if (j == 0) {

                    row.append("<td class='cells heads'>" + letters[i - 1] + "</td>");

                } else {

                    row.append("<td class='cells' id='" + letters[i-1] + numbers[j] + "S" + "'></td>");

                }

            }
        $(".salvoes").append(row);
    }

}
drawSalvoesGrid(10);

/*
drawSalvoes();
*/

function getParameterByName(name, url) {
    if (!url) {
        url = window.location.href;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

/*function drawSalvoes () {


    var nn = getParameterByName('gp');


    $.getJSON('/api/game_view/' + nn, function(data) {





        $.each(data.gamePlayers, function(player) {

            var email = data.gamePlayers[player].player.email;
            var id = data.gamePlayers[player].id;

            if (nn == id) {

                $("#salvonames").append(email + "(You)");
            }
            else {

                $("#salvonames").append(email);
            }

        })

    } )
}*/
