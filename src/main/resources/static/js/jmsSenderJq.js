$(function () {
    $('#btnAddSender').click(function () {
        const baseDiv = $('#signature');
        let sendersDiv = $('#senders');

        const sendersCount = $('#senders .oneSender').length;

        if (sendersDiv.length == 0) {
            console.log("--- sendersDiv.length = " + sendersDiv.length + "; создаем и добавляем новый блок senders");

            sendersDiv = $("<div/>", {
                "class": "mt-10",
                "id": "senders"
            }).appendTo(baseDiv);
        }

        const senderDiv = $("<div class='row oneSender mt-10'></div>")
            .attr("id","sender" + sendersCount)
            .appendTo(sendersDiv);

        const inputDiv = $("<div class='col-md-10'></div>")
            .appendTo(senderDiv);

        const input = $("<input class='form-control' type='text'></input>")
            .attr("id","senders" + sendersCount + ".title")
            .attr("name", "senders[" + sendersCount + "].title")
            .appendTo(inputDiv);

        const buttonDiv = $("<div class='col-md-2'></div>")
            .appendTo(senderDiv);

        const button = $("<button class='btn-sm btn-secondary btnDeleteSender'" +
            " type='button' " +
            "onclick='btnDeleteSenderClick(this)'>Удалить</button>")
            .appendTo(buttonDiv);
    });
});

function btnDeleteSenderClick(element) {
    $(element).parent().parent().remove();

    const oneSendersDiv = $('#senders .oneSender');

    oneSendersDiv.each(function (index){
        $(this).attr("id","sender"+index)
            .find("input")
            .attr("id","senders" + index + ".title")
            .attr("name", "senders[" + index + "].title");
    });
}

function removeDiv() {
    //todo перенести в нужное место и перейти на jquery
    const statusBlock = document.getElementById('status');

    if (statusBlock != null) {
        statusBlock.parentElement.removeChild(statusBlock);
    }
    console.log("--- removeDiv()");
}