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

function loadStings(uri){
    $('#message').text(''); 
    $('#pagination').text('');
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        type : 'GET',
        url : uri,
        headers: {"X-Auth-Token":authToken.token}
    
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        console.log('data.links');
        console.log(data.links);
        var response = data;
        var mensajeCollection = new MensajeCollection(response);
        
        var html = mensajeCollection.toHTML();
        //$("#message").html(html);
    }).fail(function(jqXHR, textStatus){
        alert('heeee');
    });
}

function MensajeCollection (mensajeCollection){ 
    this.Mensaje = mensajeCollection;
    var instance = this;
    
    this.toHTML = function(){
        var html = '';
        $.each(this.Mensaje, function(i,v){
            var mensaje = v;
        $.each(mensaje, function(i,v){
           
            if(v.content != undefined)
             $("#message").append("<li><div><a href='#' class='news-item-title'>"+v.loginid+"</a><p class='news-item-preview'>"+v.content+".</p></div></li>");
    
        });
    });

        console.log(this.Mensaje.links["next"].uri);
        var prev = this.Mensaje.links["prev"].uri;
        if(prev){
            
            $('#pagination').append(' <a onClick="loadStings(\'' + prev + '\');" style = "cursor: pointer; cursor: hand; ">[Prev]</a>');
        }
        
        var next = this.Mensaje.links["next"].uri;
        console.log(this.Mensaje.links["prev"].uri);
        if(next){
            $('#pagination').append(' <a onClick="loadStings(\'' + next + '\');" style = "cursor: pointer; cursor: hand; ">[Next]</a>');
        }
        return html;
    }
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

function crearGrupo(name, info, uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'POST',
        crossDomain: true,
        dataType: "json",
        data: { nombre: name,
              info: info},
        headers: {"X-Auth-Token":authToken.token}
        
        }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        console.log(data.links);
        console.log("Holi");
	    window.location.replace("grupo.html");
        
        
    }).fail(function(){
        alert('ERROR');
    });

}

