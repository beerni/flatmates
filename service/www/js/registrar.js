$("#imageForm").submit(function(e){
    var login= $("#usernamesignup").val();
    var password=$("#passwordsignup").val();
    var fullname=$("#fullnamesignup").val();
    var email=$("#emailsignup").val();
    var info=$("#descriptionsignup").val();
    e.preventDefault();
    $('progress').toggle();
    //var formData = new FormData($('imageForm')[0]);
    var formData = new FormData();
    formData.append('login', login);
    formData.append('password', password);
    formData.append('email', email);
    formData.append('info', info);
    formData.append('fullname', fullname);
    formData.append('imagen', $('#inputFile')[0].files[0]);    
    registrarUsuario(formData);
    console.log('Usuario creado');
});