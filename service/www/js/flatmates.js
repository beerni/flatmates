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