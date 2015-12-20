$("#btn-crear").click(function(e) {
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var currentMessagesUri = authToken["links"]["create-group"].uri;
        console.log(currentMessagesUri); 
     e.preventDefault();
    if(/^\s+|\s+$/.test($("#name").val())||$("#name").val()=="")
        {
         $("#vacios").text("");
        $("#vacios").append("<p><span style='color:red'>Los campos con asterisco son obligatorios.</span></p>");   
        }
    else
     crearGrupo($("#name").val(),
     $("#Info").val(),currentMessagesUri);
});
  $("#btnhome").click(function(e) {
	e.preventDefault();
	window.location.replace("flatmates.html");

});
