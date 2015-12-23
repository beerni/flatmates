$("#form-signin").submit(function(event){
    event.preventDefault();
    login($("#inputLoginid").val(),$("#inputPassword").val(), function(){
        console.log("TODO CORRECTO");
        window.location.replace('flatmates.html');
    });
});