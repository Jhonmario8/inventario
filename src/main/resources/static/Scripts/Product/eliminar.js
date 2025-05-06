const form=document.querySelector("form")
document.getElementById("deleteBtn").addEventListener("click",async e=>{
    e.preventDefault()
    if (!form.checkValidity()) {
        form.reportValidity()
        return
    }
    const name=document.getElementById("name").value.trim()
    try{
         let response=await fetch(`http://localhost:8080/products/searchByName/${name}`)
        if (response.status===404) {
            let error=await response.text()
            alert(error)
            form.reset()
            return
        }
        if (!response.ok){
            throw new Error("Error al buscar el producto")
        }
        if (!confirm("Seguro que desea eliminar el producto?")){
            alert("Operacion cancelada")
            form.reset()
            return
        }
        let res=await fetch(`http://localhost:8080/products/deleteByNama/${name}`,{
            method:"DELETE"
        })
        if (!res.ok){
            throw new Error("Error al eliminar el producto")
        }
        alert("Producto eliminado exitosamente")
        form.reset()
    }catch (e){
        console.error(e)
    }

})