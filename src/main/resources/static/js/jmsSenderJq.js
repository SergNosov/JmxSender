$(function () {

    $('#btnAddSender').click(function () {
        let sendersDiv = $('#senders');
        let senderDiv = $('#sender0');

        const senderCount = $('.oneSender').length;

        let buttonDel = $('<div className="col-md-2">' +
                                '<button type="button" class="btn-sm btn-secondary">' +
                                    'Удалить' +
                                '</button>' +
                             '</div>'
        );

        let addingDiv = senderDiv.clone().attr('id', 'sender' + senderCount);
        buttonDel.appendTo(addingDiv);
        addingDiv.appendTo(sendersDiv);
    });
});
