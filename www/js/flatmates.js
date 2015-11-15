$("#btnhome").click(function(e) {
	e.preventDefault();
	window.location.reload();

});

$("#btnreload").click(function(e) {
	e.preventDefault();
	window.location.reload();

});


  var cont = 30; 
setInterval(function contador(){
    if (cont == 0)
        {
            window.location.reload();
        }
    document.getElementById("demo").innerHTML = cont;
    cont--;
    
}, 1000);