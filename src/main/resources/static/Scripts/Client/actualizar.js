const form=document.querySelector("form")
function createInput(id,lbl,value){
    let label=document.createElement("label")
    label.setAttribute("for",id)
    label.textContent=lbl
    let input=document.createElement("input")
    input.setAttribute("id",id)
    input.value=value

    return [label,input]
}
document.getElementById("searchBtn").addEventListener("click",async e=>{
  e.preventDefault()
    if (!form.checkValidity()){
        form.reportValidity()
        return
    }
    const id=document.getElementById("id").value

    try{
        let res=await fetch(`http://localhost:8080/client/search/${id}`)
        if (res.status===404){
            let msg=await res.text()
            alert("Error: "+msg)
            return
        }
        if (!res.ok){
            throw new Error("Error al buscar el cliente")
        }
        const client=await res.json()
        const [labelN,name]=createInput("name","Nombre:",client.name)
        const [labelP,phone]=createInput("phone","Telefono: ",client.phone)
        const [labelA, age]=createInput("age","Edad:",client.age)
        const actualizarBtn=document.createElement("button")
        const div=document.createElement("div")
        actualizarBtn.textContent="Actualizar"
        const volver=document.createElement("button")
        volver.setAttribute("onclick","location.href='../../Html/actualizarClient.html'")
        volver.textContent="Volver"
        div.appendChild(actualizarBtn)
        div.appendChild(volver)
        div.style.display="flex"
        actualizarBtn.addEventListener("click",async e=>{
           e.preventDefault()

        try{
               if (!form.checkValidity()){
                   form.reportValidity()
                   return
               }
            let response=await fetch(`http://localhost:8080/client/update`,{
                method:"POST",
                headers:{"Content-Type":"application/json"},
                body:JSON.stringify({
                    id:id,
                    name: name.value,
                    phone: phone.value,
                    age: age.value
                })
            })
            if (!response.ok){
                let error=await response.text()
                throw new Error(error)
            }
            let msg=await response.text()
            alert(msg)
        }catch (e){
            console.error(e)
        }
       })
        form.innerHTML=""
        form.appendChild(labelN)
        form.appendChild(name)
        form.appendChild(labelP)
        form.appendChild(phone)
        form.appendChild(labelA)
        form.appendChild(age)
        form.appendChild(div)
    }catch (e){
        console.error(e)
    }
})