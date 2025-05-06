const form=document.querySelector("form")
document.getElementById("deleteBtn").addEventListener("click",async e=>{
    e.preventDefault()

    if (!form.checkValidity()){
        form.reportValidity()
        return
    }
    const id=document.getElementById("id").value
    if (!confirm("Seguro que desea eliminar el cliente?")){
        alert("Operacion cancelada")
        form.reset()
        return
    }
    try{
        let res=await fetch(`http://localhost:8080/client/delete/${id}`,{
            method:"DELETE"
        })
        let msg=await res.text()
        if (res.status===404){

            alert(msg)
        }
        if (!res.ok){

            throw new Error(msg)
        }

        alert(msg)
        form.reset()
    }catch (e){
        console.error(e)
    }

})