$(document).ready(function(){
    try{
   var authToken = JSON.parse(sessionStorage["auth-token"]); //Guardes a la variable authtoken tot 
console.log(authToken);
   var currentMessagesUri = authToken["links"]["create-group"].uri; 
    console.log(currentMessagesUri); 
   loadGrupo(currentMessagesUri);
    var grupoas = JSON.parse(sessionStorage["grupo"]);
    var currentTareaUri = grupoas["tarea"].uri;
    console.log(currentTareaUri);
    loadTarea(currentTareaUri);
    }catch(e){
        window.location.replace('index.html');
    }
});
$("#add").click(function(e) {
	e.preventDefault();
    console.log("llega");
    var grupoas = JSON.parse(sessionStorage["grupo"]);
    var currentTareaUri = grupoas["tarea"].uri;
     if(/^\s+|\s+$/.test($("#txttask").val())||$("#txttask").val()==""){
        $("#culebrilla").text("");   
        $("#culebrilla").append("<div class='alert alert-block alert-info'><p><span style='color:red'>Error writing the item. Hint: Be careful with de blank spaces at the beginning and all messages must finish with a dot or a letter, not a blank.</span></p></div>");
    }
    else
    addTask($("#txttask").val(),currentTareaUri);
});
$("#btnlogout").click(function(e){
    e.preventDefault();
    logout(function(){
        window.location.replace('index.html');
    });
})