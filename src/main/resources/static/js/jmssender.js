function removeDiv() {
    const statusBlock = document.getElementById('status');

    if (statusBlock != null) {
        statusBlock.parentElement.removeChild(statusBlock);
    }
}

function addSenderInput1() {
    let parentDiv = document.getElementById("senders");
    let childDiv = document.getElementsByClassName("oneSender").item(0).cloneNode(true);

    parentDiv.append(childDiv);

    buttonSetVisible(document.getElementsByClassName("oneSender"));
}

function buttonSetVisible(elements) {
    for (let i = 0; i < elements.length; i++) {
        if (i != 0) {
            let buttonDel = elements[i].querySelector('button');
            buttonDel.hidden = false;
        }
    }
}

function deleteParent(){
    console.log("--- deleteParent");
}