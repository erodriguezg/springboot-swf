$(function () {

    var idCSRFToken = "csrf-hidden-token";
    var idCSRFHeader = "csrf-hidden-header";

    //caso post normal
    $('form').submit(function () {
        var csrfHiddenTokenElement = $("#" + idCSRFToken);
        $(this).append(csrfHiddenTokenElement);
        return true;
    });

    //caso ajax
    $.ajaxPrefilter(function( options, originalOptions, xhr ) {
        var csrfHiddenTokenElement = $("#" + idCSRFToken);
        var csrfHiddenHeaderElement = $("#" + idCSRFHeader);
        xhr.setRequestHeader(csrfHiddenHeaderElement.val(), csrfHiddenTokenElement.val());
    });

});

