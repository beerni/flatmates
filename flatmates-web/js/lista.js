$(document).ready(function(){
    try{
   var authToken = JSON.parse(sessionStorage["auth-token"]); //Guardes a la variable authtoken tot 
console.log(authToken);
   var currentMessagesUri = authToken["links"]["create-group"].uri; 
    console.log(currentMessagesUri); 
   loadGrupo(currentMessagesUri);
    var grupoas = JSON.parse(sessionStorage["grupo"]);
    var currentListaUri = grupoas["lista"].uri;
    loadLists(currentListaUri);
    }catch(e){
        window.location.replace('index.html');
    }
        
});
$("#add").click(function(e) {
	e.preventDefault();
    console.log("llega");
    var grupoas = JSON.parse(sessionStorage["grupo"]);
    console.log(grupoas);
    var currentListaUri = grupoas["lista"].uri;
    if(/^\s+|\s+$/.test($("#txtitem").val())||$("#txtitem").val()==""){
        $("#culebrilla").text("");   
        $("#culebrilla").append("<div class='alert alert-block alert-info'><p><span style='color:red'>Error writing the item. Hint: Be careful with de blank spaces at the beginning and all messages must finish with a dot or a letter, not a blank.</span></p></div>");
    }
    else
        addItem($("#txtitem").val(),currentListaUri);
});
$("#btnlogout").click(function(e){
    e.preventDefault();
    logout(function(){
        window.location.replace('index.html');
    });
})
