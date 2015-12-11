var BASE_URI= "http://127.0.0.1:8080/flatmates"
function linksToMap(links){
    var map= {};
    $.each(links,function(i,link){
        $.each(link.rels, function(j, rel){
            map[rel]=link;
        });
    });
    return map;
}

function loadAPI (complete){
    $.get(BASE_URI).done(function(data){
        var api = linksToMap(data.links);
        sessionStorage["api"]=JSON.stringify(api);
        complete();
    }).fail(function(data){
       console.log("Y voló");
    });
}

function login (loginid, password, complete){
    loadAPI(function(){
        var api = JSON.parse(sessionStorage.api);
        var uri = api.login.uri;
        $.post(uri, {
            login : loginid,
            password : password
            
        }).done(function(authToken){
            console.log("Done login");
            authToken.links=linksToMap(authToken.links);
            sessionStorage["auth-token"]=JSON.stringify(authToken);
            complete();
        }).fail(function(jqXHR, textStatus, errorThrown){
            console.log("Fail");
            var error = jqXHR.responseJSON;
            alert("Introduce un login y/o contraseña valida");
        });
    });
}

function logout (complete){
    
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var uri = authToken["links"]["logout"].uri;
    console.log(authToken.token);
    $.ajax({
        type : 'DELETE',
        url : uri,
        headers: {"X-Auth-Token":authToken.token}
    }).done(function(data){
        sessionStorage.removeItem("api");
        sessionStorage.removeItem("auth-token");
        complete();
    }).fail(function(){console.log("Hay algun error al logout")});
        
}

function loadStings(uri, complete){
     var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        type : 'GET',
        url : uri,
        headers: {"X-Auth-Token":authToken.token}
    }).done (function(data){
        data.links=linksToMap(data.links);
        complete(data);
    }).fail(function(){console.log("Algo no funciono futuro ingeniero");});
	
}

function crearMensaje(contenido, uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'POST',
        crossDomain: true,
        dataType: "json",
        data: { content: contenido },
        headers: {"X-Auth-Token":authToken.token}
        
        }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        console.log(data.links);
        console.log("Holi");
        window.location.reload();
        
        
    }).fail(function(){
        alert('ERROR');
    });
}

function mensajeCollection{
    
}

