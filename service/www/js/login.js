$("#form-signin").submit(function(event){
    event.preventDefault();
    console.log('Hola');
    login($("#inputLoginid").val(),$("#inputPassword").val(), function(){
        console.log("TODO CORRECTO");
        window.location.replace('flatmates.html');
    });
});

$("#registrarse_btn").click(function(event){
    event.preventDefault();
    window.location.replace('register.html');
})