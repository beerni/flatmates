$(document).ready(function(){
    try{
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var currentGrupoUri = authToken["links"]["create-group"].uri;
    console.log(currentGrupoUri);
    console.log("hola");
    loadGru(currentGrupoUri);
    var profile = JSON.parse(sessionStorage["profile"]);
    console.log(profile);
    var filename = profile.filename;
    $("#img_src").text('');
    $("#full").text('');
    $("#full").text(profile.fullname);
    $("#lbllogin").text('');
    $("#lbllogin").text(profile.loginid);
    $("#e").text('');
    $("#e").text(profile.email);
    $("#bio").text('');
    $("#bio").text(profile.info);
    $("#point").text('');
    $("#point").text(profile.puntos);
    $("#task").text('');
    $("#task").text(profile.tareas);
    $("#sexo").text('');
    $("#sexo").text(profile.sexo);
    $("#img_src").append('<img src="images/'+filename+'" class="img-rounded img-responsive" />');
    }catch(e){
        console.log(e);
    }
});
$("#btnlogout").click(function(e){
    e.preventDefault();
    logout(function(){
        window.location.replace('index.html');
    });
})
