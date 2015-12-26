$(document).ready(function(){
   var authToken = JSON.parse(sessionStorage["auth-token"]); //Guardes a la variable authtoken tot 
console.log(authToken);
   var currentMessagesUri = authToken["links"]["create-group"].uri; 
    console.log(currentMessagesUri); 
   loadGrupo(currentMessagesUri);
    var grupoas = JSON.parse(sessionStorage["grupo"]);
    var currentListaUri = grupoas["lista"].uri;
    loadLists(currentListaUri);
});
$("#add").click(function(e) {
	e.preventDefault();
    console.log("llega");
    var grupoas = JSON.parse(sessionStorage["grupo"]);
    console.log(grupoas);
    var currentListaUri = grupoas["lista"].uri;
    addItem($("#txtitem").val(),currentListaUri);
});