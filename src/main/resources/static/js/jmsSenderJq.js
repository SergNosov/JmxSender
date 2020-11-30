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
        buttonDelDiv.attr('owner',senderId);

        // buttonDelDiv.click(function () {
        //    // let $this = $(this);
        //     console.log("---!!!");
        //     console.log("--- this:"+$this.attr('owner'));
        //     $this.parent().remove($this);
        // });

        buttonDelDiv.appendTo(addingDiv);
        addingDiv.appendTo(sendersDiv);
    });
});

function btnDeleteSenderClick(element) {
    console.log("---!!!");
    console.log("--- this:"+element);
    $(element).parent().parent().remove();
}
