$(function () {
    $('#btnAddSender').click(function () {
        const sendersDiv = $('#senders');
        const senderDiv = $('#sender0');

        const sendersCount = $('#senders .oneSender').length;
        const senderId = 'sender'+sendersCount;

        const addingDiv = senderDiv.clone().attr('id', senderId);
        const input = addingDiv.find('input')
            .attr('id','senders'+sendersCount+'.title')
            .attr('name','senders['+sendersCount+'].title');

        const buttonDelDiv = $('<div class="col-md-2">' +
            '<button type="button" class="btn-sm btn-secondary btnDeleteSender" onclick="btnDeleteSenderClick(this)">' +
            'Удалить' +
            '</button>' +
            '</div>'
        );

        buttonDelDiv.appendTo(addingDiv);
        addingDiv.appendTo(sendersDiv);
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