const form=document.querySelector("form")

document.getElementById("crearBtn").addEventListener("click",async e=>{
    e.preventDefault()
    if (!form.checkValidity()){
        form.reportValidity()
        return
    }
    const id=document.getElementById("id").value
    const name=document.getElementById("name").value
    const phone=document.getElementById("phone").value
    const age=document.getElementById("age").value

    try{
        let res=await fetch("http://localhost:8080/client/save",{
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({
                id:id,
                name: name,
                phone: phone,
                age:age
            })
        })
        if (!res.ok){
            throw new Error("Error al crear el cliente")
        }
        alert("Cliente creado")
    }catch (e) {
        console.error(e)
    }

})