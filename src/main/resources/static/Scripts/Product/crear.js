const form=document.querySelector("form")
document.getElementById("crearBtn").addEventListener("click",async e=>{
    e.preventDefault()
    if (!form.checkValidity()){
        form.reportValidity()
        return
    }
    const name=document.getElementById("name").value.trim()
    const price=document.getElementById("price").value
    const stock=document.getElementById("stock").value
    try{
        let res=await fetch(`http://localhost:8080/products/searchByName/${name}`)
        if (res.ok){
            alert("Ya existe un producto con este nombre")
            return
        }
        if (res.status===404){
            let response=await fetch("http://localhost:8080/products/save",{
                method:"POST",
                headers:{"Content-Type":"application/json"},
                body:JSON.stringify({
                    name: name,
                    price: price,
                    stock: stock
                })
            })
            if (!response.ok){
                throw new Error("Error al guardar el producto")
            }
            alert("Producto creado exitosamente")
            return
        }
        if (!res.ok && res.status!==404){
            throw  new Error("Error al buscar el producto")
        }
    }catch (e){
        console.error(e)
    }
})