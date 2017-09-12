$(document).ready(function () {
    if(typeof(EventSource) !== "undefined") {
        var source = new EventSource("http://localhost:8080/advertising/stand/connection");
        source.onmessage = function(event) {
             location.reload();
        };
    }
});
