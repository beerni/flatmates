var BASE_URI= "http://0.0.0.0:8085/flatmates"
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
            authToken.links=linksToMap(authToken.links);
            sessionStorage["auth-token"]=JSON.stringify(authToken);
            complete();
        }).fail(function(jqXHR, textStatus, errorThrown){
            var error = JSON.parse(jqXHR.responseText);
            $("#vacios").text("");
            $("#vacios").append('<div class="alert alert-block alert-info"><p><span style="color:red">'+error.reason+'</span></p></div>');   
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
            var response = $.parseJSON(jqxhr.responseText);
            var lastfilename = response.filname;
            $('progress').toggle();
            window.location.replace('index.html');
        }).fail(function(jqXHR, textStatus) {
           var error = JSON.parse(jqXHR.responseText);
            $("#vacios2").text("");
            $("#vacios2").append('<div class="alert alert-block alert-info"><p><span style="color:red">'+error.reason+'</span></p></div>'); 
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
    $.ajax({
        type : 'DELETE',
        url : uri,
        headers: {"X-Auth-Token":authToken.token}
    }).done(function(data){
        sessionStorage.removeItem("api");
        sessionStorage.removeItem("auth-token");
        complete();
    }).fail(function(){
       });
        
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
        if (data!=undefined){
        data.links=linksToMap(data.links);
        if (data.userid==authToken.userid)
            {
                $("#btncambio").text("");
                $("#btncambio").append("<a href='grupo.html'><i class='icon-check icon-large'></i>Group</a>");
            }
        }
    }).fail(function(jqXHR, textStatus){
    });
}
function loadGrus(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        type : 'GET',
        url : uri,
        headers: {"X-Auth-Token":authToken.token}
    
    }).done(function(data, status, jqxhr){
        if (data!=""){
        data.links=linksToMap(data.links);
        if (data.userid==authToken.userid)
            {
                $("#btncambio").text("");
                $("#btncambio").append("<a href='grupo.html'><i class='icon-check icon-large'></i>Group</a>");
            }
        }
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
        loadGrupo2step(data.links.grupo.uri);
        //$("#message").html(html);
    }).fail(function(jqXHR, textStatus){
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
        sessionStorage["grupo"]=JSON.stringify(data.links);
        var grupoas = JSON.parse(sessionStorage["grupo"]);
        sessionStorage["grupoadmin"]=JSON.stringify(data);
        var admin = JSON.parse(sessionStorage["grupoadmin"]);
        $("#lblNombreGrupo").text('');
        $("#lblNombreGrupo").text(data.nombre);
        $("#info").text('');
        $("#tabla").text('');
        $("#info").text(data.info);
        
        if (authToken.userid != data.admin)
            {
                $("#addUser").text('');
                $("#addTask").text('');
                
            }
        var usuarios = data.usuarios;
          if(authToken.userid==data.admin)
                    $("#tablado").append("<th class = 'span2'>Delete user</th>");
         $.each(usuarios, function(i,v){
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
       console.log('Error');
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
             $("#message").append('<li><div><a href="#" onClick="getProfile(\''+v.links[5].uri+'\');" class="news-item-title">'+v.loginid+'</a><p class="news-item-preview">'+v.content+'</p></div></li>');
    
        });
    });
        console.log(mensajeCollection);
        var prev = this.Mensaje.links["prev"].uri;
           var next = this.Mensaje.links["next"].uri;
        if(mensajeCollection.pagbefore==0){
            $('#pagination').append(' <a onClick="loadStings(\'' + next + '\');" style = "cursor: pointer; cursor: hand;"><div class="span3"><button class="btn btn-box">Next</button></div></a>');
        }
        else if (mensajeCollection.pagbefore==mensajeCollection.pagtotal){
             $('#pagination').append(' <a onClick="loadStings(\'' + prev + '\');" style = "cursor: pointer; cursor: hand; "><div class="span3"><button class="btn btn-box">Previous</button></div></a>');
        }
        else{
        if(prev){
            $('#pagination').append(' <a onClick="loadStings(\'' + prev + '\');" style = "cursor: pointer; cursor: hand; "><div class="span3"><button class="btn btn-box">Previous</button></div></a>');
        }
        if(next){
           $('#pagination').append(' <a onClick="loadStings(\'' + next + '\');" style = "cursor: pointer; cursor: hand; "><div class="span3"><button class="btn btn-box">Next</button></div></a>');
        }
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
        window.location.reload();
        
        
    }).fail(function(){
        console.log('Error');
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
        sessionStorage["grupo"]=JSON.stringify(data.links);
        var grupoas = JSON.parse(sessionStorage["grupo"]);
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
	    window.location.replace("grupo.html");
        
        
    }).fail(function(data, status, jqxhr){
        var error = JSON.parse(data.responseText);
         $("#culebrilla").text("");
        $("#culebrilla").append("<p><span style='color:red'>"+error.reason+ ".</span></p>");    
    });
}

function foraGrupo(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'DELETE',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token":authToken.token}
        
        }).done(function(data, status, jqxhr){
        //data.links=linksToMap(data.links);
	    window.location.replace("grupo.html");
        
        
    }).fail(function(xhr, textstatus){
        console.log(xhr.status);
      
    });
}
function todosforaGrupo(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);

    $.ajax({
        url: uri,
        type: 'DELETE',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token":authToken.token}
        
        }).done(function(data, status, jqxhr){
        //data.links=linksToMap(data.links);
	    window.location.replace("flatmates.html");
        
        
    }).fail(function(xhr, textstatus){
        console.log(xhr.status);
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
        $("#culebrilla").text("");   
        $("#culebrilla").append("<div class='alert alert-block alert-info'><p><span style='color:green'>Password changed</span></p></div>");
        $("#InputOldPass").val("");
        $("#InputNewPass").val("");
        $("#InputNewPass2").val("");
    }).fail(function(){
        $("#culebrilla").text("");   
         $("#culebrilla").append("<div class='alert alert-block alert-info'><p><span style='color:red'>Your actual password is not this</span></p></div>");
    });
    
}
function loadList(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'GET',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token" : authToken.token}
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        var listas = data.listaCompras;
        if(listas.lenght==0){
           $('#listacomprar').text("");
        $.each(listas, function(i,v){
            if(v.hecho==false){
            $('#listacomprar').append('<li><div><p class="news-item-preview">'+v.item+'</p></div></li>');
            }
        });}
    }).fail(function(jqXHR, textStatus) {
        var error = JSON.parse(jqXHR.responseText);
        console.log(error.reason);
    });
}
function addItem(item, uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'POST',
        crossDomain: true,
        dataType: "json",
        data: { item: item},
        headers: {"X-Auth-Token":authToken.token}
        
        }).done(function(data, status, jqxhr){
        //data.links=linksToMap(data.links);
	    window.location.replace("lista.html");
        
        
    }).fail(function(data, status, jqxhr){
        console.log('ERROR');
    });
}
function addTask(task, uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'POST',
        crossDomain: true,
        dataType: "json",
        data: {tarea: task},
        headers: {"X-Auth-Token":authToken.token}
        
        }).done(function(data, status, jqxhr){
        //data.links=linksToMap(data.links);
	    window.location.replace("tareas.html");
        
        
    }).fail(function(data, status, jqxhr){
        console.log('ERROR');
    
        console.log(data);
        //console.log(xhr.statusText);
        console.log(textstatus);

    });
}
function loadLists(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'GET',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token" : authToken.token}
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        var listas = data.listaCompras;
        $('#Todo').text("");
        $('#Done').text("");

        $.each(listas, function(i,v){
             if(v.hecho==false){
                $('#Todo').append('<li><div><p class="news-item-preview ">'+v.item+'</p><div align="right"><a href="#" onClick="listaHecho(\''+v.links[0].uri+'\');" class="btn btn-mini btn-info" ><i class="btn-icon-only icon-ok"></i></a></div></div></li>');
             }
            else{
                $('#Done').append('<li><div><p class="news-item-preview">'+v.item+'</p><div align="right"><a href="#" onClick="DeleteItem(\''+v.links[1].uri+'\');" class="btn btn-mini btn-danger" ><i class="btn-icon-only icon-remove"></i></a></div></div></li>')
            }
        });
    }).fail(function(){
        console.log("ERROR");
    });
}
function loadTarea(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'GET',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token" : authToken.token}
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        var tareas = data.tareas;
        $('#Task').text("");
        var admin = JSON.parse(sessionStorage["grupoadmin"]);
        $.each(tareas, function(i,v){
            if(admin.admin == authToken.userid){
                if(v.hecho==false){
                if(v.userid==null){
                $('#Task').append('<li><div><p class="news-item-preview ">'+v.tarea+'</p><div align="right"><a href="#" onClick="TareaAdd(\''+v.links[0].uri+'\');" class="btn btn-mini btn-info" ><i class="btn-icon-only icon-plus"></i></a><a href="#" onClick="DeleteTask(\''+v.links[1].uri+'\');" class="btn btn-mini btn-danger" ><i class="btn-icon-only icon-remove"></i></a></div></div></li>');
                }
                else{
                    if(v.userid==authToken.userid){
                     $('#Task').append('<li><div><p class="news-item-preview ">'+v.tarea+'</p><div align="right"><button onClick="getMyTarea(\''+v.links[2].uri+'\');" class="btn btn-info btn-mini" id="add"><i class= "icon-pencil"></i>Upload task</button><a href="#" onClick="DeleteTask(\''+v.links[1].uri+'\');" class="btn btn-mini btn-danger" ><i class="btn-icon-only icon-remove"></i></a></div></div></li>');
                    }
                    else{
                    $('#Task').append('<li><div><a class="news-item-title">This task is being doing by another user</a><p class="news-item-preview ">'+v.tarea+'</p><div align="right"><a href="#" onClick="DeleteTask(\''+v.links[1].uri+'\');" class="btn btn-mini btn-danger" ><i class="btn-icon-only icon-remove"></i></a></div></div></li>');
                    }
                }
             }
            else{
                if(v.userid==authToken.userid){
                 $('#Task').append('<li><div><p class="news-item-preview ">'+v.tarea+'</p><p class="news-item-preview ">Points: '+v.puntos+'</p><div align="right"><a href="#" onClick="DeleteTask(\''+v.links[1].uri+'\');" class="btn btn-mini btn-danger" ><i class="btn-icon-only icon-remove"></i></a></div></div></li>');
                }
                else{
                  $('#Task').append('<li><div><p class="news-item-preview ">'+v.tarea+'</p><div align="right"><button href="#" onClick="getTareaE(\''+v.links[2].uri+'\');" class="btn btn-info btn-mini"><i class= "icon-ok"></i> Evaluate</button><a href="#" onClick="DeleteTask(\''+v.links[1].uri+'\');" class="btn btn-mini btn-danger" ><i class="btn-icon-only icon-remove"></i></a></div></div></li>');
                }
            }
            }
            else{
            if(v.hecho==false){
                if(v.userid==null){
                $('#Task').append('<li><div><p class="news-item-preview ">'+v.tarea+'</p><div align="right"><a href="#" onClick="TareaAdd(\''+v.links[0].uri+'\');" class="btn btn-mini btn-info" ><i class="btn-icon-only icon-plus"></i></a></div></div></li>');
                }
                else{
                    if(v.userid==authToken.userid){
                     $('#Task').append('<li><div><p class="news-item-preview ">'+v.tarea+'</p><div align="right"><button onClick="getMyTarea(\''+v.links[2].uri+'\');" class="btn btn-info btn-mini" id="add"><i class= "icon-pencil"></i>Upload task</button></div></div></li>');
                    }
                    else{
                    $('#Task').append('<li><div><a class="news-item-title">This task is being doing by another user</a><p class="news-item-preview ">'+v.tarea+'</p><div align="right"></div></div></li>');
                    }
                }
             }
            else{
                if(v.userid==authToken.userid){
                 $('#Task').append('<li><div><p class="news-item-preview ">'+v.tarea+'</p><p class="news-item-preview ">Points: '+v.puntos+'</p><div align="right"></div></div></li>');
                }
                else{
                  $('#Task').append('<li><div><p class="news-item-preview ">'+v.tarea+'</p><div align="right"><button href="#" onClick="getTareaE(\''+v.links[2].uri+'\');" class="btn btn-info btn-mini"><i class= "icon-ok"></i> Evaluate</button></div></div></li>');
                }
            }
        }
        });
    }).fail(function(){
         console.log("ERROR");
    });
}
function TareaAdd(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'POST',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token":authToken.token}
        
        }).done(function(data, status, jqxhr){
        //data.links=linksToMap(data.links);
	    window.location.replace("tareas.html");
        
    }).fail(function(xhr, textstatus){
        
         console.log(xhr.status);
    });
}
function listaHecho(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'POST',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token":authToken.token}
        
        }).done(function(data, status, jqxhr){
        //data.links=linksToMap(data.links);
	    window.location.replace("lista.html");
        
    }).fail(function(xhr, textstatus){
        
         console.log(xhr.status);
    });
}
function points(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'POST',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token":authToken.token}
        
        }).done(function(data, status, jqxhr){
        //data.links=linksToMap(data.links);
	    window.location.replace("tareas.html");   
    }).fail(function(jqXHR, textStatus, errorThrown){    
        console.log("Fail");
        var error = JSON.parse(jqXHR.responseText); 
        $("#culebrilla").text("");
        $("#culebrilla").append('<div class="alert alert-block alert-info"><p><span style="color:red">'+error.reason+'</span></p></div>');   

    });
}
function DeleteTask(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'DELETE',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token":authToken.token}
        
        }).done(function(data, status, jqxhr){
        //data.links=linksToMap(data.links);
	    window.location.replace("tareas.html");
        
    }).fail(function(xhr, textstatus){
         console.log(xhr.status);
    });
}
function DeleteItem(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'DELETE',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token":authToken.token}
        
        }).done(function(data, status, jqxhr){
        //data.links=linksToMap(data.links);
	    window.location.replace("lista.html");
        
    }).fail(function(xhr, textstatus){
    
     console.log(xhr.status);
    });
}
function changeDetails(info, fullname, email){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var uri = authToken["links"]["user-profile"].uri;
    var userid = authToken.userid;
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
         $("#culebrilla2").text("");   
         $("#culebrilla2").append("<div class='alert alert-block alert-info'><p><span style='color:green'>Details changed correctly</span></p></div>");
        $("#InputFullname").val("");
        $("#InputEmail").val("");
        $("#InputInfo").val("");
    }).fail(function(){
         $("#culebrilla2").text("");   
         $("#culebrilla2").append("<div class='alert alert-block alert-info'><p><span style='color:red'>Something was wrong at change yout details</span></p></div>");
    });
}
function updateTarea (formdata, uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
     //var uri=api.user.uri;
        $.ajax({
            url: uri,
		    type: 'PUT',
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
            processData: false,
             headers: {"X-Auth-Token" : authToken.token}
        }).done(function(data, status,jqxhr){
            var response = $.parseJSON(jqxhr.responseText);
            var lastfilename = response.filname;
            $('progress').toggle();
            window.location.reload();
        }).fail(function(jqXHR, textStatus) {
            var error = JSON.parse(jqXHR.responseText);
            $("#culebrilla").text("");   
            $("#culebrilla").append("<div class='alert alert-block alert-info'><p><span style='color:red'>"+error.reason+"</span></p></div>");
            
        });
}
function getMyTarea(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'GET',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token" : authToken.token}
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        sessionStorage["tarea"]=JSON.stringify(data);
        window.location.replace("uptask.html")
        
    }).fail(function(){
         console.log("ERROR");
    });
}
function getTareaE(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'GET',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token" : authToken.token}
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);;
        sessionStorage["tareaE"]=JSON.stringify(data);
        window.location.replace("punctuate.html");
        
    }).fail(function(){
         console.log("ERROR");
    });
}
function getMyT(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'GET',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token" : authToken.token}
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        $('#lbltarea').text(data.tarea);
        console.log(data.image);
        if(data.image!=null){
        $('#vacios2').text('');
        var filename =data.image;
        var a = "images/"+filename+".png";
        $("#img_src").text('');
        $("#img_src").append('<img src="'+a+'" class="img-rounded img-responsive" />');
        
        }
            
    }).fail(function(){
         console.log("ERROR");
    });
}
function getMyTa(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'GET',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token" : authToken.token}
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        var filename=data.image;
        $("#img").text('');
        var a = "images/"+filename+".png";
        $("#img").append('<img src="'+a+'" class="img-rounded img-responsive" />');
        $('#lbltarea').text(data.tarea);
        
    }).fail(function(){
         console.log("ERROR");
    });
}
function getUser(){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var uri = authToken["links"]["user-profile"].uri;
    var userid = authToken.userid;
    var getuserURI = uri;
    $.ajax({
        url: getuserURI,
        type: 'GET',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token" : authToken.token}
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        var filename=data.filename;
        $("#img_src").text('');
        $("#img_src").append('<img src="images/'+filename+'" class="img-rounded img-responsive" />');
    }).fail(function(){
         console.log("ERROR");
    });
}
function getProfile(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'GET',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token" : authToken.token}
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        sessionStorage["profile"]=JSON.stringify(data);
        window.location.replace("myProfile.html")
    }).fail(function(){
         console.log("ERROR");
    });
}

function buscarUsers(login){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var uri = authToken["links"]["home"].uri;
    var uri_user = uri+"users/by-raiz-"+login;
    var userid = authToken.userid;
    $("#tabla").text('');
    $.ajax({
        url: uri_user,
        type: 'GET',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token" : authToken.token}
    }).done(function(data, status, jqxhr){
        var usuarios = data.users;
        $("#info").text("");
        if(data.users.length==0){
            $("#info").append('<div class="alert alert-block alert-info"><p><span style="color:red">We do not find any user that contains this words. </span></p></div>');
        }
        else{
        $.each(usuarios,function(i,v){

           $("#tabla").append('<tr><td><a href="#" onClick="getProfile(\''+v.links[4].uri+'\');">'+v.loginid+'</a></td><td>'+ v.tareas+'</td><td>'+v.puntos+'</td></tr>');
        });
        }
    }).fail(function(){
         console.log('Error');
    });
   
}

