let form= document.querySelector("form")

document.getElementById("buscarBtn").addEventListener("click",async e=>{
    e.preventDefault()
    if (!form.checkValidity()){
        form.reportValidity()
        return
    }
    const id=document.getElementById("id").value
    let info=document.querySelector(".info")
    try{
        let res=await fetch(`http://localhost:8080/client/search/${id}`)
        if (res.status===404){
            info.textContent="El cliente no se encontro"
            info.style.color="red"
            return
        }
        if (!res.ok){
            throw new Error("Error al buscar el cliente")
        }
        let client=await res.json()
        info.innerHTML=`Nombre: ${client.name} <br>
        Telefono: ${client.phone} <br>
        Edad: ${client.age}
`
        info.style.color="#ecf0f1"
    }catch (e){
        console.error(e)
    }
})