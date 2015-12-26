$(document).ready(function(){
    try{
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var currentGrupoUri = authToken["links"]["create-group"].uri;
    console.log(currentGrupoUri);
    console.log("hola");
    loadGru(currentGrupoUri);
    getUser();
    }catch(e){
        console.log(e);
    }
});
                  
var incorrectPassInput;
$('#InputNewPass2').on('input', function() {
        if($('#InputNewPass2').val() == $('#InputNewPass').val()){
            $("#InputNewPass2").css({ background: "#FFFFFF"});
            $("#InputNewPass").css({ background: "#FFFFFF"});
            incorrectPassInput=false;
        }else{
            $("#InputNewPass2").css({ background: "#FFD700"});
            $("#InputNewPass").css({ background: "#FFD700"});
            incorrectPassInput=true;
        }
    });
$("#form-pass").submit(function(event){
    event.preventDefault();
    if($('#InputNewPass2').val() != $('#InputNewPass').val())
        alert('Los nuevos passwords no coinciden');
    else{
        changePassword($('#InputNewPass').val(),$('#InputOldPass').val(), function(){
        console.log("Password cambiado");
        window.location.reload();
    });}    
});

$('#form-datails').submit(function(event){
    event.preventDefault();
    changeDetails($('#InputInfo').val(),$('#InputFullname').val(),$('#InputEmail').val(), function(){
        window.location.reload();
    });
});



$("#btnlogout").click(function(e){
    e.preventDefault();
    logout(function(){
        window.location.replace('index.html');
    });
})
