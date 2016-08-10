/**
 * Created by kolyan on 06.08.16.
 */
var stompClient = Stomp.over(new SockJS('/api/chat'));

$(function(){
    $(".chat").niceScroll();
});
$('document').ready(function() {
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/chat/messages', function(message) {
            console.log(message);
        });

        stompClient.send('/api/chat/messages', {}, 1);
    });
});