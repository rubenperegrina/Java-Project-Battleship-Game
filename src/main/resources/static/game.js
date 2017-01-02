/**
 * Created by rubenperegrina on 2/1/17.
 */

/*
 drawGrid();

 function drawGrid() {

 var gridhtml = document.getElementById("gridship");

 var leters = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];
 var numbers = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];

 var table = "<table><tbody>";

 for (var i = 0; i < numbers.length; i++) {

 table += "<th>" + numbers[i] + "</th>";

 for (var j =1; j < leters.length;j++) {

 table += "<td>" + leters[i] + "</td>";
 }
 }
 table += "</tbody></table>";
 gridhtml.innerHTML = table;
 }
 */


/*drawGrid();

 function drawGrid()
 {
 for (var i = 1; i <= 100; i++) {

 if (i < 11) {
 $(".table").prepend("<span class='aTops'>" + Math.abs(i - 11) + "</span>");
 $(".grid").append("<li class='points Casilla' id= " + i + "><span class='hole'></li>");
 } else {
 $(".grid").append("<li class='points Casilla' id= " + i + "'><span class='hole'></li>");
 }
 if (i == 11) {
 $(".table").prepend("<span class='aTops hidezero'>" + Math.abs(i - 11) + "</span>");
 }
 if (i > 90) {
 $(".table").append("<span class='aLeft'>" +
 String.fromCharCode(97 + (i - 91)).toUpperCase() + "</span>");
 }
 }
 }*/


/*gridship();

function gridship() {

    var gridhtml = document.getElementById("gridship");

    var numbers = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];
    var leters = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];

    /!*var tabla = "<table><tbody><th> </th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th><th>10</th>";*!/
    for (var j = 1; j < numbers.length; j++) {
        var tabla = "<table><thead><th>" + numbers[j] + "</th></thead>"
    }

    for (var i = 0; i < leters.length; i++) {

        tabla += "<tbody><tr>";

        tabla += "<td>" + leters[i] + "</td>"

        tabla += "</tr>"

    }
    tabla += "</tbody></table>"

    gridhtml.innerHTML = tabla;

}*/



var numbers = [" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];
var letters = [ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];


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