/**
 * Created by rubenperegrina on 2/1/17.
 */

/*****CREATE SHIPS*********************/
jQuery(document).ready(function ($) {

    $('#undo').hide();
    $('#salvoesbutton').hide();
    var templocations = [];
    var sizeship;
    var currentship;
    var salvoesloc = [];

    var clickActivado = false;

    /**********Start Functions************/
    initialfunctions();

    /***********Checkifshipspost*********/
    function checkifshipspost() {


        var nn = getParameterByName('gp');
        $.getJSON('/api/game_view/' + nn, function (data) {

            if (data.ships[0]) {
                $('.salvoes').show();
                $('.infoships').hide();
            } else {
                $('.salvoes').hide();
                $('.infoships').show();
            }
        })
    }

    /**********Get cell id with mouse over and
     *  check the orientation position of a ship
     **************************************/
    function showtempships() {
        $(".grid").on('mouseover', 'td', function (e) {
            removeselectship();
            var mousecell = e.target.id;

            if (clickActivado) {

                if (checkorientation()) {
                    templocations = horizontal(mousecell, sizeship);
                } else {
                    templocations = vertical(mousecell, sizeship);
                }
            }

            paintselectship();
        })
    }

    /*********Print Red cell when try to put
     *  a new ship in a cell ship**********/
    function printredcellshipocuped() {

        var value = false;

        templocations.forEach(function (cell) {
            var hasClass = $("#" + cell).hasClass("ship");

            if (hasClass) {
                value = true;
                return false;
            }
        });
        return value;
    }

    /**********Check Ship Size*************/
    function checkshipsize() {
        $('.ships').on('click', function () {
            position = $(this).attr("data-index");
            sizeship = ships[position].shiplength;
            currentship = $(this).attr("data-index");
        })
    }

    /*********Select one aviable ship******/
    function shipselect() {

        $('.ships').on('click', function (e) {

            $(this).addClass("shipclick");
            $('.ships').not(e.target).removeClass("shipclick");
            clickActivado = true;
        })
    }

    /*********Check Ship Orientation******/
    function checkorientation() {

        if ($("#horizontal").is(":checked") == true) {
            return true;
        } else {
            return false;
        }
    }

    /*********Paint Select Ship***********/
    function paintselectship() {
        templocations.forEach(function (id) {
            $('#' + id).addClass("tempship");
        })
    }

    /********Remove Select Ship**********/
    function removeselectship() {
        templocations.forEach(function (id) {
            $('#' + id).removeClass("tempship");
        })
    }

    /********Print Select Ship in the Grid**/
    function paintgridship() {

        $(".grid").on('click', 'td', function (cell) {
            var hasClass = $(this).hasClass("ship");

            if (!printredcellshipocuped()) {

                templocations.forEach(function (cell) {
                    $("#" + cell).addClass("ship");
                    $("#" + cell).removeClass("tempship");
                    $('.ships').removeClass('shipclick');
                    $('#undo').show();
                })
            } else {
                /*$("#" + cell).addClass("tempwrongship");*/
                $("#" + cell).removeClass("tempship");
                $('.ships').removeClass('shipclick');
            }


            sendshiplocations(cell);
            clickActivado = false;
            templocations = [];
            $('ul').find("[data-index='" + currentship + "']")
                .hide()
                .removeClass('shipclick')
        })
    }

    /*******Undo all ships in the grid and put aviableships**/
    function undoships() {
        $('#undo').on('click', function () {
            ships[currentship].locations = [];
            window.location.reload();
        })
    }

    /*******Send Ship locations to JSON****/
    function sendshiplocations(cell) {

        templocations.forEach(function (cell) {
            ships[currentship].locations.push(cell);
        })
        console.log(templocations);
    }

    /*********Put Select Ship in Horizontal***/
    function horizontal(mousecell, sizeship) {

        var letter = mousecell.substr(0, 1);
        var number = Number(mousecell.substr(1, 2));
        var shiplocations = [];

        for (i = 0; i < sizeship; i++) {
            if ((number + i) <= 10) {
                shiplocations.push(letter + (number + i))
            }
        }

        if (shiplocations.length == sizeship) {
            return shiplocations;
        } else {
            return [];
        }
    }

    /*********Put Select Ship in Vertical***/
    function vertical(mousecell, sizeship) {
        var letter = mousecell.substr(0, 1);
        var number = Number(mousecell.substr(1, 2));
        var letterposition = letters.indexOf(letter);
        var shiplocations = [];


        for (i = 0; i < sizeship; i++) {
            if ((letterposition + i) < 10) {
                shiplocations.push(letters[letterposition + i] + number)
            }
        }

        if (shiplocations.length == sizeship) {
            return shiplocations;
        } else {
            return [];
        }

    }


    var ships = [
        {
            ship: "carriership",
            locations: [],
            shiplength: "5"

        },
        {
            ship: "battleship",
            locations: [],
            shiplength: "4"
        },
        {
            ship: "submarineship",
            locations: [],
            shiplength: "3"
        },
        {
            ship: "destroyership",
            locations: [],
            shiplength: "3"
        },
        {
            ship: "patrolboatship",
            locations: [],
            shiplength: "2"
        },
    ];


    /**********Send Ship Locations to Print in the Grid**/
        $('#createships').on('click', function (event) {
            event.preventDefault();
            var nn = getParameterByName('gp');
            $.post({
                timeout: 1000,
                url: '/api/games/players/' + nn + '/ships',
                contentType: 'application/json',
                data: JSON.stringify(ships)


            }).done(function () {
                alert('The Ship has created!');
                window.location.reload();


            }).fail(function () {
                alert('The Ship doesnt create!');
            });
        });

    /**********Print Salvo in Salvo Grid***************/
    function printsalvoinsalvogrid () {

        $('.salvoes .cells').on('click', function () {

            if(salvoesloc.length > 2) {
                alert('You must send  Salvoes!');

            }else if($(this).hasClass('salvo')) {
                alert('You have fired en this location!');

            }else if($(this).hasClass('salvocell')) {
                alert('You have fired en this location, try another!');

            }else if($(this).hasClass('heads')) {
                alert('I don´t bet for you');
            }else {
                $(this).addClass("salvo");
                salvoesloc.push(this.id);
            }
            console.log(salvoesloc);
            $('#salvoesbutton').show();
        })
    }


    /**********Send Salvo Locations to have a request**/
    $('#salvoesbutton').on('click', function (event) {

        if(salvoesloc.length < 3) {
            alert('You must send 3 Salvoes!');
        }else if(salvoesloc.length == 3) {

        event.preventDefault();
        var nn = getParameterByName('gp');
        $.post({
            timeout: 1000,
            url: '/api/games/players/' + nn + '/salvos',
            contentType: 'application/json',
            data: JSON.stringify({'locations':salvoesloc})


        }).done(function () {
            alert('Your Salvoes has send!');
            window.location.reload();


        }).fail(function () {
            alert('Something wrong!');
        });
        }
    });

    /**********Call initial functions******************/
    function initialfunctions() {
        checkshipsize();
        showtempships();
        shipselect();
        paintgridship();
        checkifshipspost();
        printredcellshipocuped();
        undoships();
        printsalvoinsalvogrid();
    }
});


/******************Log Out****************************/
$('#logout').on('click', function (event) {
    event.preventDefault();

    $.ajax({
        timeout: 1000,
        type: 'POST',
        url: '/api/logout'


    }).done(function (data, textStatus, jqXHR) {

        alert('Bye Bye!');
        window.location.replace("/games.html");

    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert('Check your conexion and try again bro!');
    });
})


var numbers = [" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];
var letters = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];
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

                    row.append("<td class='cells' id='" + letters[i - 1] + numbers[j] + "'></td>");

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

function drawShips() {


    var nn = getParameterByName('gp');


    $.getJSON('/api/game_view/' + nn, function (data) {


        $.each(data.ships, function (ship) {

            var location = data.ships[ship].location;
            $.each(location, function (cell) {


                $('#' + location[cell]).addClass("ship")
            })
        })

        $.each(data.gamePlayers, function (player) {

            var email = data.gamePlayers[player].player.name;
            var id = data.gamePlayers[player].player.id;
            var gpid = data.gamePlayers[player].id;
            console.log(nn);
            console.log(id);

            if (nn == gpid) {

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

                $.each(data.salvoes[salvo][cell].locations, function (cell2) {


                    var salvoCells = data.salvoes[salvo][cell].locations[cell2];
                    console.log(salvoCells);

                    if (data.salvoes[salvo][cell].player == playerid) {



                        $("#" + salvoCells + "S").addClass("salvocell");
                        $("#" + salvoCells + "S").html(data.salvoes[salvo][cell].turn);

                    } else {
                        $("#" + salvoCells).addClass("salvocell");
                        $("#" + salvoCells).html(data.salvoes[salvo][cell].turn);
                    }
                    if ($("#" + salvoCells).hasClass("ship") && $("#" + salvoCells).hasClass("salvocell")) {
                        $("#" + salvoCells).addClass("shoot");
                    }
                })

            })
        })

    })
}

/*****************SALVOES*****************************/

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

                    row.append("<td class='cells' id='" + letters[i - 1] + numbers[j] + "S" + "'></td>");

                }

            }
        $(".salvoes").append(row);
    }

}
drawSalvoesGrid(10);


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

