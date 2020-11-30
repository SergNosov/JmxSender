$(function () {
    $('#btnAddSender').click(function () {
        let sendersDiv = $('#senders');
        let senderDiv = $('#sender0');

        const senderId = 'sender'+$('#senders .oneSender').length;
        let addingDiv = senderDiv.clone().attr('id', senderId);

        let buttonDelDiv = $('<div class="col-md-2">' +
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
}
