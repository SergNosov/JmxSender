$(function () {
    $('#btnAddSender').on('click', function () {
        btnAddSenderClick();
    });

    if ($('#status').length > 0) {
        $('.statusField').on('input', function () {
            $('#status').remove();
        });
    }

    $('#uploadFile').on('change', function () {
        checkFileSize(this);
    });
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

    const $button = $("<button class='btn-sm btn-danger btnDeleteSender'" +
        " type='button' " +
        "title='Удалить подпись' " +
        "onclick='btnDeleteSenderClick(this)'>" +
        "<i class='fa fa-trash' aria-hidden='true'></i>" +
        "</button>")
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

function checkFileSize(element){
    console.log("--- on checkFileSize")
    const size = (element.files[0].size / 1024 / 1024).toFixed(2);
    if (size > 5) {
        alert("Размер файла не должен привышать 5 MB");
        $(element).val('');
        $("label[for='" + element.id + "']").text("Загрузить файл");
    }
}