$(document).ready(function(){
    getUser();
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
    console.log(incorrectPassInput);
    if($('#InputNewPass2').val() != $('#InputNewPass').val())
        alert('Los nuevos passwords no coinciden');
    else{
        changePassword($('#InputNewPass').val(),$('#InputOldPass').val(), function(){
        console.log("Password cambiado");
        window.location.reload();
    });}    
});