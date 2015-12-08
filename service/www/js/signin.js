$("#form-signin").submit(function( event ) {
  event.preventDefault();
  login($("#username").val(), $("#password").val(), function(){
      console.log($("#username").val());
  	console.log("Todo correcto");
  	//window.location.replace('flatmates.html');
  });
});

/*$("#button_register").click(function(e) {
	e.preventDefault();
	window.location.replace("register.html");

});*/