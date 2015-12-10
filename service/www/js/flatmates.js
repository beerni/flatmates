/*$(function(){
   var authToken = JSON.parse(sessionStorage["auth-token"]); //Guardes a la variable authtoken tot 
   var currentMessagesUri = authToken["links"]["current-messages"].uri; //Obtens la uri dels missatges amb el que tens a links i a current-messages; el current-messages es el rel del HATEOAS
    console.log(currentMessagesUri); //Uri obtenida
   loadStings(currentMessagesUri, function(stings){
      $("#messages-list").empty(); //ME llegan los stings
       var response = stings;
       console.log(response); 
       $.each(response, function (i,v){
           var fichero = v;
        $.each(fichero, function(i,v){ //Uno por uno!
            console.log(v)    
        });
       });
     
      //processStingCollection(stings);
  });
});
/*function processStingCollection(stings){
  $.each(stings["stings"], function(i,sting){
      sting.links=linksToMap(sting.links);
      console.log(sting.links);
      var edit = sting.userid ==JSON.parse(sessionStorage["auth-token"]).userid;
      $("#stings-list").append(listItemHTML(sting.links["self"].uri, sting.subject, sting.creator, edit));
      if(i==0)
        $("#buttonUpdate").click(function(){alert("I don't do anything, implement me!")});
      if(i==lastIndex){
        $('#formPrevious').attr('action', sting["links"].previous.uri);
      }
  });

}*/
  $("#btnhome").click(function(e) {
	e.preventDefault();
	window.location.replace("flatmates.html");

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

var cont = 30; 
setInterval(function contador(){
    if (cont == 0)
        {
            window.location.reload();
        }
    document.getElementById("demo").innerHTML = cont;
    cont--;
    
}, 1000);

