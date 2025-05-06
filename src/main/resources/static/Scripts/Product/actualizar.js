const form=document.querySelector("form")
function createInput(id,lbl,value){
    let label=document.createElement("label")
    label.setAttribute("for",id)
    label.textContent=lbl
    let input=document.createElement("input")
    input.setAttribute("id",id)
    input.setAttribute("required",true)
    input.value=value
    if (id === "price" || id === "stock") {
        input.type = "number"
        input.min = "0"
    }

    return [label,input]
}
document.getElementById("buscarBtn").addEventListener("click",async e=> {
    e.preventDefault()
    if (!form.checkValidity()) {
        form.reportValidity()
        return
    }
    const nombre = document.getElementById("name").value
    try {
        let res = await fetch(`http://localhost:8080/products/searchByName/${nombre}`)
        if (res.status === 404) {
            let error = await res.text()
            alert(error)
        }
        if (!res.ok) {
            throw new Error("Error al buscar el producto")
        }
        let per = await res.json()
        form.innerHTML = ""
        const [labelN, name] = createInput("name", "Nombre:", per.name)
        const [labelP, price] = createInput("price", "Precio:", per.price)
        const [labelS, stock] = createInput("stock", "Stock:", per.stock)
        const div=document.createElement("div")
        div.style.display="flex"
        const actualizarBtn = document.createElement("button")
        actualizarBtn.textContent="Actualizar"
    const volver = document.createElement("button")
    volver.textContent = "Volver"
    volver.addEventListener("click",()=>location.href="../../Html/GestionProducto/actualizarProducto.html")
    actualizarBtn.addEventListener("click", async e => {
        e.preventDefault()
        try {
            let response = await fetch(`http://localhost:8080/products/update`, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    id: per.id,
                    name: name.value,
                    price: price.value,
                    stock: stock.value
                })
            })
            if (!response.ok) {
                throw new Error("Error al actualizar el producto")
            }
            alert("Persona actualizada con exito")
        } catch (e) {
            console.error(e)
        }
        })
        div.appendChild(actualizarBtn)
        div.appendChild(volver)
        form.appendChild(labelN)
        form.appendChild(name)
        form.appendChild(labelP)
        form.appendChild(price)
        form.appendChild(labelS)
        form.appendChild(stock)
        form.appendChild(div)
    }catch (e){
        console.error(e)
    }
})