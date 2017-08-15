$(function () {

    var idCSRFToken = "csrf-hidden-token";
    var idCSRFHeader = "csrf-hidden-header";

    //caso post normal
    $('form').submit(function () {
        var csrfHiddenTokenElement = $("#" + idCSRFToken);
        $(this).append(csrfHiddenTokenElement);
        return true;
    });

    //caso ajax primefaces
    $(document).on("pfAjaxSend", function(event, xhr, options) {
        var csrfHiddenTokenElement = $("#" + idCSRFToken);
        var csrfHiddenHeaderElement = $("#" + idCSRFHeader);
        xhr.setRequestHeader(csrfHiddenHeaderElement.val(), csrfHiddenTokenElement.val());
    });

});

