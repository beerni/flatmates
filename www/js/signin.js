$( "#form-signin" ).submit(function( event ) {
  event.preventDefault();
  login($("#loginid").val(), $("#password").val(), function(){
  	console.log("change");
  	window.location.replace('flatmates.html');
  });
});

$("#button_register").click(function(e) {
	e.preventDefault();
	window.location.replace("register.html");
});