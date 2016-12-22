/**
 * Created by rubenperegrina on 16/12/16.
 */

var url = "/rest/games";

$(function() {

    console.log("Getting the JSON");

    $.getJSON(url, jsongames);

    console.log("JSON is coming...");
});


function jsongames(data) {

    console.log(data);

    var gameslist = document.getElementById("gameslist");

    var list = "<ol>";

    for (var i = 0; i<data.length; i++) {
        
        var Player1 = data[i].gamePlayers[0];
        var Player2 = data[i].gamePlayers[1];

            list += "<li>" + data[i].id +"-" + new Date(data[i].creation).toLocaleString()
                + "-" + Player1.email + "-" + Player1.id + "-" + Player1.player
                +  " VS " + Player2.email + "-" + Player2.id + "-" + Player2.player + "</li>";
    }

    list += "</ol>";
    gameslist.innerHTML = list;
}