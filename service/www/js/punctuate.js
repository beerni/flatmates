$(document).ready(function(){
    try{
   var authToken = JSON.parse(sessionStorage["auth-token"]); //Guardes a la variable authtoken tot 
console.log(authToken);
   var currentMessagesUri = authToken["links"]["create-group"].uri; 
    console.log(currentMessagesUri); 
   loadGrupo(currentMessagesUri);
    var tareas = JSON.parse(sessionStorage["tareaE"]);
    var Uri = tareas["links"]["Update"].uri; 
    console.log(tareas);
    getMyTa(Uri);
    }catch(e){
        window.location.replace('index.html');
    }
});
$("#adds").click(function(e){
     var tareas = JSON.parse(sessionStorage["tareaE"]);
    var Uri = tareas["links"]["Delete"].uri; 
   $("#points").val();
    points(Uri+/points/+$("#points").val());
});
                      
