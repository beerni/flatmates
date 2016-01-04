$(document).ready(function(){
    try{
   var authToken = JSON.parse(sessionStorage["auth-token"]); //Guardes a la variable authtoken tot 
console.log(authToken);
   var currentMessagesUri = authToken["links"]["create-group"].uri; 
    console.log(currentMessagesUri); 
   loadGrupo(currentMessagesUri);
    var tareas = JSON.parse(sessionStorage["tarea"]);
    var Uri = tareas["links"]["Update"].uri; 
    console.log(tareas);
    getMyT(Uri);
    }catch(e){
        window.location.replace('index.html');
    }
});

$("#add").click(function(e){
    $('progress').toggle();
    var formData = new FormData();
    var tareas = JSON.parse(sessionStorage["tarea"]);
    var Uri = tareas["links"]["Update"].uri; 
    formData.append('imagen', $('#inputFile')[0].files[0]);  
    updateTarea(formData, Uri);
});
                      
