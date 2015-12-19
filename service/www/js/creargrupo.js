$("#btn-crear").click(function(e) {
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var currentMessagesUri = authToken["links"]["create-group"].uri;
        console.log(currentMessagesUri); 
     e.preventDefault();
    if(($("#name").val())=='')
        alert("Tienes que escribir un nombre");
    else
    crearGrupo($("#name").val(),
     $("#Info").val(),currentMessagesUri);
});
  $("#btnhome").click(function(e) {
	e.preventDefault();
	window.location.replace("flatmates.html");

});

