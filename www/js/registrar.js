var API_BASE_URL = "http://localhost:8080/flatmates"

$(document).ready(function(){
    var url = API_BASE_URL + '/users';
    $("#signup").click(function(e){
        registrar(url);
    });
    
});

function linksToMap(links){
	var map = {};
	$.each(links, function(i, link){
		$.each(link.rels, function(j, rel){
			map[rel] = link;
		});
	});

	return map;
}

function loadAPI(complete){
	$.get(API_BASE_URL)
		.done(function(data){
			var api = linksToMap(data.links);
			sessionStorage["api"] = JSON.stringify(api);
			complete();
		})
		.fail(function(data){
		});
}
function registrar (url){
    usuario = {
        "loginid" : $('#InputNick').val(),
        "password": $('#InputPassword').val(),
        "email" : $('#InputEmail').val(),
        "fullname" : $('#InputName').val(),
        "sexo" : $('#Female').val(),
        "info": $('#InputMessage').val(),
        //"imagen"
    }
    
    var data = JSON.stringify(usuario);
    $.ajax({
        url : url,
        type : 'POST',
        crossDomain : true,
        contentType : "application/vnd.dsa.flatmates.user+json",
        dataType : 'json',
        data : data,
    }).done(function (data, status, jqxhr){
        console.log("Registrado");
    }).fail(function(){
        console.log("ERROR");
    });
}