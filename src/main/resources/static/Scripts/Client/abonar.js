const form=document.querySelector("form")

document.getElementById("buscarBtn").addEventListener("click",async e=>{
    e.preventDefault()
    if (!form.checkValidity()){
        form.reportValidity()
        return
    }
    const table=document.querySelector("table")
    table.setAttribute("class","accounts-table")
    const tbody=document.getElementById("products")
    tbody.innerHTML=""
    const name=document.getElementById("name").value
    try{
        let res=await fetch(`http://localhost:8080/client/findByName/${name}`)
        if (res.status===404){
            let msg=await res.text()
            alert(msg)
        }
        if (!res.ok){
            throw new Error("Error al buscar el cliente")
        }
        let client=await res.json()
        let response=await fetch(`http://localhost:8080/account/findByClient/${client.id}`)
        if (!response.ok){
            throw new Error("Error al buscar la cuenta")
        }
        let account=await response.json()
        let total=0
        for (let p of account.products) {
            let response1=(await fetch(`http://localhost:8080/account/getQuantity/${client.id}/product/${p.code}`))
            if (!response1.ok){
                throw new Error("Error al obtener la cantidad")
            }
            let stock=await response1.json()
            total+=stock*p.price
            let row = document.createElement("tr")
            row.innerHTML = `
            <td>${p.code}</td>
            <td>${p.name}</td>
            <td>${stock}</td>
            <td>${p.price}</td>
            <td>${stock*p.price}</td>
            `
            tbody.appendChild(row)
        }
        let row=document.createElement("tr")
        row.innerHTML=`
        <th>Total:</th>
        <td>${account.mount}</td>
        `
        tbody.appendChild(row)
        const abonarBtn=document.createElement("button")
        abonarBtn.textContent="Abonar"
        abonarBtn.setAttribute("class","button")
        abonarBtn.addEventListener("click",async e=>{
            e.preventDefault()

            form.innerHTML=""
            const label=document.createElement("label")
            label.textContent="Monto:"
            label.setAttribute("id","mount")
            const input=document.createElement("input")
            input.id="mount"
            input.placeholder=`Valor a pagar: ${account.mount}`
            const payBtn=document.createElement("button")
            payBtn.textContent="Pagar"
            payBtn.setAttribute("class","button")
            payBtn.addEventListener("click",async e=>{
                e.preventDefault()
                try {
                    let res=await fetch(`http://localhost:8080/account/pay/${input.value}/client/${client.id}`,{
                        method:"PUT",
                    })
                    let msg=await res.text()
                    if (!res.ok){
                        throw new Error("Error al realizar el pago")
                    }
                    alert(msg)
                    window.location.href="../../Html/GestionCliente/abonar.html"
                }catch (e){
                    console.error(e)
                }
            })
            form.appendChild(label)
            form.appendChild(input)
            form.appendChild(payBtn)
        })
        document.getElementById("btn").appendChild(abonarBtn)
    }catch (e){
        console.error(e)
    }
})