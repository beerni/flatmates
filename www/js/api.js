var API_BASE_URL = "http://127.0.0.1:8080/flatmates"

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
function login(loginid, password, complete){
	loadAPI(function(){
		var api = JSON.parse(sessionStorage.api);
		var uri = api.login.uri;
		$.post(uri,
			{
				loginid: loginid,
				password: password
			}).done(function(authToken){
				authToken.links = linksToMap(authToken.links);
				sessionStorage["auth-token"] = JSON.stringify(authToken);
				complete();
			}).fail(function(jqXHR, textStatus, errorThrown){
				var error = jqXHR.responseJSON;
				alert(error.reason);
			}
		);
	});
}