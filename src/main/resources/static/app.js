/**
 * Created by rubenperegrina on 7/12/16.
 */
$(function() {

    // display text in the output area
    function showOutput(text) {
        $("#output").text(text);
    }

    // load and display JSON sent by server for /players

    function loadData() {
        $.get("/players")
            .done(function(data) {
                showOutput(JSON.stringify(data, null, 2));
            })
            .fail(function( jqXHR, textStatus ) {
                showOutput( "Failed: " + textStatus );
            });
    }

    // handler for when user clicks add person

    function addPlayer() {

        var email = $("#emailName").val();
        var id = $("#idName").val();
        

        if (email, id) {
            postPlayer(email, id);
        }
    }

    // code to post a new player using AJAX
    // on success, reload and data from server

    function postPlayer(idName, emailName) {
        $.post({
                headers: {
                    'Content-Type': 'application/json'
                },
                dataType: "text",
                url: "/players",
                data: JSON.stringify({ "email": emailName, "name": idName })
            })
            .done(function( ) {
                showOutput( "Saved -- reloading");
                loadData();
            })
            .fail(function( jqXHR, textStatus ) {
                showOutput( "Failed: " + textStatus );
            });
    }

    $("#add_player").on("click", addPlayer);

    loadData();
});