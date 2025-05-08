let tbody=document.getElementById("table-body")
let products=[]
function crearFila(p, cant = null) {
    let row = document.createElement("tr");
    let cantidad = cant !== null ? cant : p.stock;
    let total = cantidad * p.price;

    row.innerHTML = `
        <td>${p.code}</td>
        <td>${p.name}</td>
        <td><input type="number" class="input" value="${cantidad}" min="0"></td>
        <td>${p.price}</td>
        <td class="total">${total}</td>
    `;

    const input = row.querySelector("input");
    const totalCell = row.querySelector(".total");

        input.addEventListener("input", e => {
            const nuevaCantidad = parseFloat(e.target.value) || 0;
            const nuevoTotal = nuevaCantidad * p.price;
            totalCell.textContent = nuevoTotal;

            // Asegurarse que se actualiza el objeto correcto en el array
            const producto = products.find(prod => prod.code === p.code);
            if (producto) {
                producto.stock = nuevaCantidad;
            }
        });


        return row;
}

document.getElementById("product").addEventListener("input",async e=>{
    e.preventDefault()

    const product=e.target.value
    tbody.innerHTML=""
    try{
        if (product===""){
            products.forEach(p=>{
                let row=crearFila(p)
                tbody.appendChild(row)
            })
        }
        let res=await fetch(`http://localhost:8080/products/findByName/${product}`)
        if (!res.ok){
            throw new Error("Error al buascar los productos")
        }
        let prods=await res.json()
        prods.forEach(p=>{
            let row=crearFila(p)
            tbody.appendChild(row)
            row.addEventListener("dblclick", e=>{
                e.preventDefault()
                pr=p
                pr.stock=1
                products.push(pr)
                document.getElementById("product").value=""
                tbody.innerHTML=""
                products.forEach(prod => {
                    let fila = crearFila(prod);

                    tbody.appendChild(fila);
                })
            })
        })

    }catch (e){
        console.error(e)
    }
})
document.getElementById("cant")

document.querySelector("button").addEventListener("click",async e=>{
    e.preventDefault()

    let cuenta=document.getElementById("accoutn").value
    if (cuenta===""){
        cuenta="vaarios"
    }
    try{
        for (const p of products) {
            try {
                let response=await fetch(`http://localhost:8080/client/findByName/${cuenta}`)
                if (response.status===404){
                    alert("La cuenta no se encontro")
                    return
                }
                if (!response.ok){
                    throw new Error("Error al buscar la cuenta")
                }
                let Cliente=await response.json()
                let res = await fetch(`http://localhost:8080/account/addProduct`,{
                    method:"PUT",
                    headers:{"Content-Type":"application/json"},
                    body:JSON.stringify({
                        idCliente:Cliente.id,
                        productId:p.code,
                        stock:p.stock
                    })
                })
                if (!res.ok){
                    throw new Error("Error al añadir el producto")
                }
                tbody.innerHTML=""
                alert("Venta Realizada")
            }catch (e){
                console.error(e)
            }
        }
    }catch (e){
        console.error(e)
    }
})

document.getElementById("accoutn").addEventListener("input",async e=>{
    e.preventDefault()

    const name=e.target.value

    let accs= document.getElementById("accounts")
    accs.setAttribute("class","accounts-table")
    let table=document.getElementById("cuentas-tabla")
    if (name===""){
        accs.removeAttribute("class")
        accs.style.display="none"

    }
    table.innerHTML=""
    try{
        let res=await fetch(`http://localhost:8080/client/nameContain/${name}`)
        if (!res.ok){
            throw new Error("Error al obtener los clientes")
        }
        let accounts=await res.json();
        accounts.forEach(ac=>{
            let row=document.createElement("tr")
            row.innerHTML=`
            <td>${ac.id}</td>
            <td>${ac.name}</td>
            `
            row.addEventListener("dblclick",ev=>{
                e.target.value=ac.name
                accs.removeAttribute("class")
                accs.style.display="none"
            })
            table.appendChild(row)
        })
    }catch (e){
        console.error(e)
    }
})