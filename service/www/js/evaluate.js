
("#imageForm").submit(function(e){
    $('progress').toggle();
    var formData = new FormData();
    formData.append('imagen', $('#inputFile')[0].files[0]);  
    updateTarea(formData);
}
                      
function updateTarea (formdata){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    console.log(authToken);
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
            processData: false
        }).done(function(data, status,jqxhr){
            console.log('YE');
            var response = $.parseJSON(jqxhr.responseText);
            var lastfilename = response.filname;
            alert('Todo OK');
            $('progress').toggle();
            window.location.reload();
        }).fail(function(jqXHR, textStatus) {
            alert('textStatus');
        });
}