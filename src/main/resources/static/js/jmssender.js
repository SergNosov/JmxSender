function removeDiv() {
    const statusBlock = document.getElementById('status');

    if (statusBlock != null) {
        statusBlock.parentElement.removeChild(statusBlock);
    }
}

function addSenderInput(){
    console.log("-----click");
    let parentDiv = document.getElementById("senders");
    let childDiv = document.getElementsByClassName("oneSender").item(0).cloneNode(true);

    let buttonDel = document.createElement('button')
    buttonDel.textContent = "Удалить"
    buttonDel.type = "button";
    buttonDel.className = "btn-sm btn-secondary";
    childDiv.appendChild(buttonDel);

    // let buttonDel = childDiv.getElementById("buttonDel")
    // buttonDel.hidden = false;
     parentDiv.append(childDiv);

    // let div = document.createElement('div');
    // div.className = "oneSender";
    // div.className = "mt-10";
    //
    // let input = document.createElement('input');
    // input.type = "text";
    // input.className="form-control";
    // input.required = true;
    //
    // div.append(input);
    //
    // console.log("----- div:"+div);
    //parentDiv.append(div);

}