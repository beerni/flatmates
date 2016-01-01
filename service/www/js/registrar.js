$("#imageForm").submit(function(e){
    var login= $("#usernamesignup").val();
    var password=$("#passwordsignup").val();
    var fullname=$("#fullnamesignup").val();
    var email=$("#emailsignup").val();
    var info=$("#descriptionsignup").val();
    var sexo =$("#check11").val();
    e.preventDefault();
    $('progress').toggle();
    //var formData = new FormData($('imageForm')[0]);
    var formData = new FormData();
    formData.append('login', login);
    formData.append('password', password);
    formData.append('email', email);
    formData.append('info', info);
    formData.append('fullname', fullname);
    formData.append('sexo', sexo);
    formData.append('imagen', $('#inputFile')[0].files[0]);    
    console.log(formData);
    registrarUsuario(formData);
    console.log('Usuario creado');
});

function check1(checkbox){
  var elems = document.getElementsByTagName("input");
    var currentState = checkbox.checked;
    var elemsLength = elems.length;
    if(checkbox.id=="check22"){
        $("#check11").value="false";
    }
    else{
         $("#check11").value="true";
    }
    for (i=0; i < elemsLength; i++){
        if(elems[i].type == "checkbox"){
            elems[i].checked = false;
            elems[i].disabled=false;
        }
    }
    checkbox.checked = currentState;
    checkbox.disabled=true;
}