
let body= document.getElementById("table-body")
function createRow(product){
    let row=document.createElement("tr")
    row.innerHTML=`
    <td>${product.code}</td>
    <td>${product.name}</td>
    <td>${product.stock}</td> 
     <td>${product.price}</td>
    `
    return row
}
async function mostrar(){
    try{
        let res=await fetch("http://localhost:8080/products/showAll")
        if (!res.ok){
            throw new Error("Error al obtener los productos")
        }
        let products=await res.json()
        products.slice(0,10).forEach(p=>{
            let row=createRow(p)
            body.appendChild(row)
        })
    }catch (e){
        console.error(e)
    }
}
document.addEventListener("DOMContentLoaded",mostrar)
document.getElementById("name").addEventListener("input",async e=>{
    e.preventDefault()
    const name=e.target.value
    body.innerHTML=""
    const info=document.getElementById("info")
    info.textContent=""
    try{
        if (name===""){
            mostrar()
            return
        }
        let res=await fetch(`http://localhost:8080/products/findByName/${name}`)

        if (!res.ok){
            throw new Error("Error al obtener los productos")
        }
        let products=await res.json()
        if (products.length===0){
            info.textContent="Producto no encontrado"
            info.style.color="red"
        }
        products.forEach(p=>{
            let row=createRow(p)
            body.appendChild(row)
        })
    }catch (e){
        console.error(e)
    }
})