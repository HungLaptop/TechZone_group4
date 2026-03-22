if (typeof SockJS === 'undefined' || typeof Stomp === 'undefined') {
    console.warn('Realtime features are unavailable because SockJS or Stomp is not loaded.');
} else {
    const socket = new SockJS('/ws');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        console.log('WebSocket connected');
        stompClient.subscribe('/topic/greetings', function (message) {
            console.log('Realtime message: ' + message.body);
        });
    });

    window.sendRealTime = function (message) {
        if (stompClient && stompClient.send) {
            stompClient.send('/app/hello', {}, message);
        }
    };
}
