$(function () {
    $('#btnAddSender').click(function () {
        const baseDiv = $('#signature');
        let sendersDiv = $('#senders');
        console.log("--- sendersDiv.length = " + sendersDiv.length);

        const sendersCount = $('#senders .oneSender').length;

        if (sendersDiv.length == 0) {
            console.log("--- sendersDiv.length = " + sendersDiv.length + "; создаем и добавляем новый блок senders");

            sendersDiv = $("<div/>", {
                "class": "mt-10",
                "id": "senders"
            }).appendTo(baseDiv);
        }
        console.log("--- sendersCount: " + sendersCount);

        const senderDiv = $("<div class='row oneSender mt-10'></div>")
            .attr("id","sender" + sendersCount)
            .appendTo(sendersDiv);

        const inputDiv = $("<div>")
            .class("col-md-10")
            .appendTo(senderDiv);

        const input = $("<input>")
            .type("text")
            .class("form-control")
            .id("senders" + sendersCount + ".title")
            .attr("name", "senders[" + sendersCount + "].title")
            .appendTo(inputDiv);

        const buttonDiv = $("<div>")
            .class("col-md-2")
            .appendTo(senderDiv);

        const button = $("<button>")
            .type("button")
            .class("btn-sm btn-secondary btnDeleteSender")
            .click("btnDeleteSenderClick(this)")
            .text("Удалить")
            .appendTo(buttonDiv);


        // const senderDiv = $('#sender0');
        //
        // const sendersCount = $('#senders .oneSender').length;
        // const senderId = 'sender'+sendersCount;
        //
        // const addingDiv = senderDiv.clone().attr('id', senderId);
        // const input = addingDiv.find('input')
        //     .attr('id','senders'+sendersCount+'.title')
        //     .attr('name','senders['+sendersCount+'].title');
        //
        // const buttonDelDiv = $('<div class="col-md-2">' +
        //     '<button type="button" class="btn-sm btn-secondary btnDeleteSender" onclick="btnDeleteSenderClick(this)">' +
        //     'Удалить' +
        //     '</button>' +
        //     '</div>'
        // );
        //
        // buttonDelDiv.appendTo(addingDiv);
        // addingDiv.appendTo(sendersDiv);
    });
});

function btnDeleteSenderClick(element) {
    $(element).parent().parent().remove();
    // todo пересчет атрибутов id и name у всех input из div c id='senders'
}

function removeDiv() {
    //todo перенести в нужное место и перейти на jquery
    const statusBlock = document.getElementById('status');

    if (statusBlock != null) {
        statusBlock.parentElement.removeChild(statusBlock);
    }
    console.log("--- removeDiv()");
}