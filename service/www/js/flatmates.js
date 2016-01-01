$(document).ready(function(){
    try{
   var authToken = JSON.parse(sessionStorage["auth-token"]); //Guardes a la variable authtoken tot 
   var currentMessagesUri = authToken["links"]["current-messages"].uri; //Obtens la uri dels missatges amb el que tens a links i a current-messages; el current-messages es el rel del HATEOAS
    console.log(currentMessagesUri); //Uri obtenida
   loadStings(currentMessagesUri);
    var currentGrupoUri = authToken["links"]["create-group"].uri; 
    loadGru(currentGrupoUri);
        $("#tabla").text("");
        }catch (e){
        $("#estoseve").text("");

        $("#estoseve").append("<div class='alert alert-block alert-info'><p><span style='color:red'>You must login to see this page.</span></p></div>");
        }/*, function(stings){
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
      e.preventDefault();
      console.log("estonova");
      if(/^\s+|\s+$/.test($("#txtmensaje").val())||$("#txtmensaje").val()=="")
        {
         $("#culebrilla").text("");   
         $("#culebrilla").append("<div class='alert alert-block alert-info'><p><span style='color:red'>You must to write something.</span></p></div>");
        }
      else
        crearMensaje($("#txtmensaje").val(), currentMessagesUri);
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

$('#form-buscar').submit(function(e){
    e.preventDefault();
    buscarUsers($("#searchUsers").val());
})


var cont = 1000; 
setInterval(function contador(){
    try{
    if (cont == 0)
        {
            window.location.reload();
        }
    document.getElementById("demo").innerHTML = cont;
    cont--;
    }
    catch (e){
    }
}, 1000);

