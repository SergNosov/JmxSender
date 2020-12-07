$(function () {
    $('#btnAddSender').on('click', function () {
        btnAddSenderClick();
    });

    if ($('#status').length > 0) {
        $('.statusField').on('input', function () {
            $('#status').remove();
        });
    }
});

function btnAddSenderClick() {
    const $baseDiv = $('#signature');
    let $sendersDiv = $('#senders');

    if ($sendersDiv.length == 0) {
        $sendersDiv = $("<div/>", {
            "class": "mt-10",
            "id": "senders"
        }).appendTo($baseDiv);
    }

    const sendersCount = $('#senders .oneSender').length;

    const $senderDiv = $("<div class='row oneSender mt-10'></div>")
        .attr("id", "sender" + sendersCount)
        .appendTo($sendersDiv);

    const $inputDiv = $("<div class='col-md-10'></div>")
        .appendTo($senderDiv);

    const $input = $("<input class='form-control' type='text'></input>")
        .attr("id", "documentDto.senders" + sendersCount + ".title")
        .attr("name", "documentDto.senders[" + sendersCount + "].title")
        .appendTo($inputDiv);

    const $buttonDiv = $("<div class='col-md-2'></div>")
        .appendTo($senderDiv);

    const $button = $("<button class='btn-sm btn-secondary btnDeleteSender'" +
        " type='button' " +
        "onclick='btnDeleteSenderClick(this)'>Удалить</button>")
        .appendTo($buttonDiv);
}

function btnDeleteSenderClick(element) {
    $(element).parent().parent().remove();

    const $oneSendersDiv = $('#senders .oneSender');

    $oneSendersDiv.each(function (index) {
        $(this).attr("id", "sender" + index)
            .find("input")
            .attr("id", "documentDto.senders" + index + ".title")
            .attr("name", "documentDto.senders[" + index + "].title");
    });
}