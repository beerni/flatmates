$(document).ready(function(){
   var authToken = JSON.parse(sessionStorage["auth-token"]); //Guardes a la variable authtoken tot 
console.log(authToken);
   var currentMessagesUri = authToken["links"]["create-group"].uri; 
    console.log(currentMessagesUri); 
   loadGrupo(currentMessagesUri);
});
 $("#btnhome").click(function(e) {
	e.preventDefault();
	window.location.replace("flatmates.html");

});

