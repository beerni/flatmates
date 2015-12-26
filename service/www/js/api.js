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

function registrarUsuario (formdata){
    loadAPI(function(){
        var api = JSON.parse(sessionStorage.api);
        var uri=api.user.uri;
        $.ajax({
            url: uri,
		    type: 'POST',
            xhr: function(){
                var myXhr=$.ajaxSettings.xhr();
                if(myXhr.upload){
                    myXhr.upload.addEventListener('progress',progressHandlingFunction,false);
                }
                return myXhr;
            },
            crossDomain: true,
            data: formdata,
            cache: false,
		    contentType: false,
            processData: false
        }).done(function(data, status,jqxhr){
            console.log('YE');
            var response = $.parseJSON(jqxhr.responseText);
            var lastfilename = response.filname;
            alert('Todo OK');
            $('progress').toggle();
            window.location.replace('index.html');
        }).fail(function(jqXHR, textStatus) {
            alert('textStatus');
        });
        
    });
}

function progressHandlingFunction(e){
    if(e.lengthComputable){
        $('progress').attr({value:e.loaded,max:e.total});
    }
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
        $("#estoseve").text("");
        
        $("#estoseve").append("<div class='alert alert-block alert-info'><p>You have to be login for see this page.</p></div>");

    });
}
function loadGru(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        type : 'GET',
        url : uri,
        headers: {"X-Auth-Token":authToken.token}
    
    }).done(function(data, status, jqxhr){
        if (data.grupoid!=null)
        {
        data.links=linksToMap(data.links);
        console.log('estoooo');
        console.log(data);
        if (data.userid==authToken.userid)
            {
                $("#btncambio").text("");
                $("#btncambio").append("<a href='grupo.html'><i class='icon-check icon-large'></i>Grupo</a>");
            }
        }
        //$("#message").html(html);
    }).fail(function(jqXHR, textStatus){
    });
}
function loadGrupo(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        type : 'GET',
        url : uri,
        headers: {"X-Auth-Token":authToken.token}
    
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        console.log('data.links');
        console.log(data.links.grupo.uri);
        loadGrupo2step(data.links.grupo.uri);
        //$("#message").html(html);
    }).fail(function(jqXHR, textStatus){
        alert('heeee');
    });
}
function loadGrupo2step(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        type : 'GET',
        url : uri,
        headers: {"X-Auth-Token":authToken.token}
    
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        console.log('data.links');
        console.log(data);
        sessionStorage["grupo"]=JSON.stringify(data.links);
        var grupoas = JSON.parse(sessionStorage["grupo"]);
        console.log(grupoas);

        $("#lblNombreGrupo").text('');
        $("#lblNombreGrupo").text(data.nombre);
        $("#info").text('');
        $("#tabla").text('');
        $("#info").text(data.info);
        
        if (authToken.userid != data.admin)
            {
                $("#addUser").text('');
            }
        var usuarios = data.usuarios;
          if(authToken.userid==data.admin)
                    $("#tablado").append("<th class = 'span2'>Delete user</th>");
         $.each(usuarios, function(i,v){
                console.log(v.links[1].uri);
                if(authToken.userid==v.userid){
                    if (authToken.userid==data.admin){
                        $("#btnIrse").text('');                        
                         $("#btnIrse").append('<a href="#" onClick="todosforaGrupo(\''+grupoas["grupo"].uri+'\');"><i class="icon-remove-sign icon-large"></i> Delete group</a>');
                    }
                    else{
                            $("#btnIrse").text(''); 
                            $("#btnIrse").append('<a href="#" onClick="todosforaGrupo(\''+v.links[1].uri+'\');"><i class="icon-remove-sign icon-large"></i>Leave the group</a>');
                    }
                }
                if(authToken.userid==data.admin){
                    if(authToken.userid==v.userid){
                    $("#tabla").append('<tr><td>'+v.loginid+'</td><td>'+v.puntos+'</td><td><a href="#" onClick="todosforaGrupo(\''+v.links[1].uri+'\');" class="btn btn-small btn-danger"><i class="btn-icon-only icon-remove"></i></a></td></tr>');
                    }
                    else{
                    $("#tabla").append('<tr><td>'+v.loginid+'</td><td>'+v.puntos+'</td><td><a href="#" onClick="foraGrupo(\''+v.links[1].uri+'\');" class="btn btn-small btn-danger"><i class="btn-icon-only icon-remove"></i></a></td></tr>');
                    }
                }
                else{
                 $("#tabla").append("<tr><td>"+v.loginid+"</td><td>"+v.puntos+"</td></tr>");
                }
         });
        
        
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
            
            $('#pagination').append(' <a onClick="loadStings(\'' + prev + '\');" style = "cursor: pointer; cursor: hand; "><div class="span3"><button class="btn btn-box">Previous</button></div></a>');
        }
        
        var next = this.Mensaje.links["next"].uri;
        console.log(this.Mensaje.links["prev"].uri);
        if(next){
           $('#pagination').append(' <a onClick="loadStings(\'' + next + '\');" style = "cursor: pointer; cursor: hand; "><div class="span3"><button class="btn btn-box">Next</button></div></a>');
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
        
        
    }).fail(function(data, status, jqxhr){
        var error = JSON.parse(data.responseText);
         $("#vacios").text("");
        $("#vacios").append("<p><span style='color:red'>"+error.reason+ ".</span></p>");   
    });
}

function addGrupo(name, uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'POST',
        crossDomain: true,
        dataType: "json",
        data: { loginid: name},
        headers: {"X-Auth-Token":authToken.token}
        
        }).done(function(data, status, jqxhr){
        //data.links=linksToMap(data.links);
        console.log("Holi");
	    window.location.replace("grupo.html");
        
        
    }).fail(function(data, status, jqxhr){
        alert('ERROR');
    
        console.log(data);
        console.log(xhr.statusText);
        console.log(textstatus);
    });
}

function foraGrupo(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);

    console.log("llega");
    console.log(uri);
    $.ajax({
        url: uri,
        type: 'DELETE',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token":authToken.token}
        
        }).done(function(data, status, jqxhr){
        //data.links=linksToMap(data.links);
        console.log("Holi");
	    window.location.replace("grupo.html");
        
        
    }).fail(function(xhr, textstatus){
        alert('ERROR');
        alert(xhr.status);
    });
}
function todosforaGrupo(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);

    console.log("llega");
    console.log(uri);
    $.ajax({
        url: uri,
        type: 'DELETE',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token":authToken.token}
        
        }).done(function(data, status, jqxhr){
        //data.links=linksToMap(data.links);
        console.log("Holi");
	    window.location.replace("flatmates.html");
        
        
    }).fail(function(xhr, textstatus){
        alert('ERROR');
        alert(xhr.status);
    });
}

function changePassword(newPass, oldPass){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var uri = authToken["links"]["user-profile"].uri;
    var changepassUri = uri+'-password';
    var userid = authToken.userid;
      objeto = {
          "id": userid,
          "oldPassword": oldPass,
          "password" : newPass
      }
      var data = JSON.stringify(objeto);
    console.log(data);
    $.ajax({
        url: changepassUri,
        type: 'PUT',
        crossDomain: true,
        contentType: "application/vnd.dsa.flatmates.user+json",
        dataType: "json",
        data: data, /*{
            id: userid,
            oldPassword: oldPass,
            password: newPass
        },*/
        headers: {"X-Auth-Token" : authToken.token
                  //"Content-Type" : application/vnd.dsa.flatmates.user+json
                 }
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        console.log(data.links);
        console.log("Holi");
    }).fail(function(){
        alert("ERROR");
    });
    
}
function changeDetails(info, fullname, email){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var uri = authToken["links"]["user-profile"].uri;
    var userid = authToken.userid;
    console.log(uri);
    objeto = {
        "id": userid,
        "email":email,
        "fullname":fullname,
        "info":info
    }
    
    var data = JSON.stringify(objeto);
    $.ajax({
        url: uri,
        type:'PUT',
        crossDomain: true,
        contentType: "application/vnd.dsa.flatmates.user+json",
        dataType: "json",
        data: data,
        headers: {"X-Auth-Token" : authToken.token}
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        console.log('Cambiado correctamente');
    }).fail(function(){
        alert('Algo no ha hecho bien mi señoria');
    });
}
function getUser(){
    console.log('Arribo??');
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var uri = authToken["links"]["user-profile"].uri;
    var userid = authToken.userid;
    var getuserURI = uri;
    console.log(getuserURI);
    $.ajax({
        url: getuserURI,
        type: 'GET',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token" : authToken.token}
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        console.log("TODO BIEN");
        var filename=data.filename;
        $("#img_src").text('');
        console.log("/images/"+filename);
        $("#img_src").append('<img src="images/'+filename+'" class="img-rounded img-responsive" />');
    }).fail(function(){
        alert("ERROR");
    });
}

