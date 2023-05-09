function validateForm(){
    var form = document.getElementById("clientForm");
    for(const input of form.getElementsByTagName("input")){
         if(input.value==""){
            alert("Форма не заполнена");
            return false;
         }
    }
    return true;
}