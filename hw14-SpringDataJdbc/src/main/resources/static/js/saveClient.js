function saveClient() {
    if(validateForm()==false){
        return;
    }
    const name = document.getElementById("name").value;
    const address = document.getElementById("address").value;
    const phone1 = document.getElementById("phone1").value;
    const phone2 = document.getElementById("phone2").value;
    fetch('/clients', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(
        {
            name: name,
            address:{street:address},
            phones:[{number:phone1},{number:phone2}]
        })
     }).then(response=>window.location.href=response.url)
}