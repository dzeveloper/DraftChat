var lastMessageTimestamp;

function scrollToBottom() {
    var chat_area = $('#chat_area');
    chat_area.scrollTop(chat_area[0].scrollHeight);
}

var entityMap = {
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': '&quot;',
    "'": '&#39;',
    "/": '&#x2F;'
};

function escapeHtml(string) {
    return String(string).replace(/[&<>"'\/]/g, function (s) {
        return entityMap[s];
    });
}

function appendMesage(message) {
    var messagesBlock = $("#chat-messages");
    message.time = new Date(message.timestamp).format('H:i:s');
    message.message = escapeHtml(message.message);
    messagesBlock.append($.templates("#messageTemplate").render(message));
    scrollToBottom();
}

function refreshMessages() {
    var messagesBlock = $("#chat-messages");
    $.get('message', function (messagesJSON) {
        messagesBlock.empty();
        $.each(messagesJSON, function (index, mes) {
            appendMesage(mes);
        });
        lastMessageTimestamp = messagesJSON[messagesJSON.length - 1].timestamp;
    });
    scrollToBottom();
}

function loadMessages() {
    $.get('message/last', {timestamp: lastMessageTimestamp}, function (messagesJSON) {
        $.each(messagesJSON, function (index, mes) {
            appendMesage(mes);
        });
        if (!jQuery.isEmptyObject(messagesJSON))
            lastMessageTimestamp = messagesJSON[messagesJSON.length - 1].timestamp;
    });
}

$(document).ready(function () {
    refreshMessages();

    setInterval(loadMessages, 1000);

    $("#send-button").click(function () {
        var messageBlock = $("#message-text");
        if (messageBlock.val()) {
            $.post('message/send', {message: messageBlock.val(), authorId: "1"}, function () {
                messageBlock.val('');
            });
        }
    });
});