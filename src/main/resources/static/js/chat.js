var stompClient = Stomp.over(new SockJS('/api/chat'));

$(function(){
    $(".chat").niceScroll();
});

$('document').ready(function() {
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/user/' + frame.headers['user-name'] + '/chat/messages', function(message) {
            console.log(message);
        });
        stompClient.subscribe('/user/' + frame.headers['user-name'] + '/chat/dialogs', function(dialogs) {
            console.log(dialogs);
            var dialogList = JSON.parse(dialogs.body);
            dialogList.forEach(function(dialog) {
                $('#chat-users').append(getUserTemplate(dialog))
            });

        });

        stompClient.send('/api/chat/dialogs', {}, {});
    });
});


function getUserTemplate(dialog) {
    return '<div id="user-label" class="user" data-dialog-id="' + dialog.id + '">' +
                '<div class="avatar">' +
                    '<img src="http://bootdey.com/img/Content/avatar/avatar1.png" alt="User name">' +
                    '<div class="status off"></div>' +
                '</div>' +
                '<div class="name">' + dialog.title + '</div>' +
                '<div class="mood">User mood</div>' +
            '</div>'
}

function setDialogListener() {
    $('#user-label').on('click', function() {
        var dialogId = $(this).data('dialog-id');

    });
}