$(document).ready(function(){
   var authToken = JSON.parse(sessionStorage["auth-token"]); //Guardes a la variable authtoken tot 
   var currentMessagesUri = authToken["links"]["current-messages"].uri; //Obtens la uri dels missatges amb el que tens a links i a current-messages; el current-messages es el rel del HATEOAS
    console.log(currentMessagesUri); //Uri obtenida
   loadStings(currentMessagesUri);/*, function(stings){
      $("#message").empty(); //ME llegan los stings
      var response = stings;
       console.log(response); 
       $.each(response, function (i,v){
           var fichero = v;
        $.each(fichero, function(i,v){ //Uno por uno!
             console.log(v);
            if(v.content != undefined)
             $("#message").append("<li><div><a href='#' class='news-item-title'>"+v.loginid+"</a><p class='news-item-preview'>"+v.content+".</p></div></li>");
        });
           
    });
  });*/
});



  $("#crearMensaje").click(function(e) {
   var authToken = JSON.parse(sessionStorage["auth-token"]); //Guardes a la variable authtoken tot 
   var currentMessagesUri = authToken["links"]["current-messages"].uri;
   event.preventDefault();
    if(($("#txtmensaje").val())=='')
        alert("Tienes que enviar algun mensaje culebrilla");
      else
        crearMensaje($("#txtmensaje").val(), currentMessagesUri);
});
  $("#btnhome").click(function(e) {
	e.preventDefault();
	window.location.replace("flatmates.html");

});

$("#btnnuevogrupo").click(function(e) {
	e.preventDefault();
	window.location.replace("creargrupo.html");

});

$("#btnreload").click(function(e) {
	e.preventDefault();
	window.location.reload();

});

$("#btnlogout").click(function(e){
    e.preventDefault();
    logout(function(){
        window.location.replace('index.html');
    });
})

$("#btnPerfil").click(function(e){
    e.preventDefault();
    window.location.replace('editProfile.html');
})

var cont = 1000; 
setInterval(function contador(){
    if (cont == 0)
        {
            window.location.reload();
        }
    document.getElementById("demo").innerHTML = cont;
    cont--;
    
}, 1000);

