$(document).ready(function(){
    try{
   var authToken = JSON.parse(sessionStorage["auth-token"]); //Guardes a la variable authtoken tot 
console.log(authToken);
   var currentMessagesUri = authToken["links"]["create-group"].uri; 
    console.log(currentMessagesUri); 
   loadGrupo(currentMessagesUri);
    var grupoas = JSON.parse(sessionStorage["grupo"]);
    var currentListaUri = grupoas["lista"].uri;
    loadList(currentListaUri);   
    }catch(e){
        window.location.replace('index.html');
    }
});
$("#add").click(function(e) {
	e.preventDefault();
    console.log("llega");
    var grupoas = JSON.parse(sessionStorage["grupo"]);
    console.log(grupoas);
    var currentGrupoUru = grupoas["grupo"].uri;
    addGrupo($("#txtuser").val(),currentGrupoUru);

});
$("#delete").click(function(e) {
	e.preventDefault();
    console.log("llega");
    var grupoas = JSON.parse(sessionStorage["grupo"]);
    console.log(grupoas);
    //var currentGrupoUru = grupoas["grupo"].uri;
    //addGrupo($("#txtuser").val(),currentGrupoUru);

});

$("#btnlogout").click(function(e){
    e.preventDefault();
    logout(function(){
        window.location.replace('index.html');
    });
})
