$(document).ready(function () {

    function scrollToBottom() {
        var chat_area = $('#chat_area');
        var height = chat_area[0].scrollHeight;
        chat_area.scrollTop(height);
    }

    scrollToBottom();

    $("#send-button").click(function () {
        var messageBlock = $("#message-text");
        $.post('sendMessage', {message: messageBlock.val(), authorId: "1", timestamp: +new Date()});
        messageBlock.val('');
        scrollToBottom();
        // location.reload();
    });
});