$(function () {
   
    // Modal Link
    $('#modal_link').click(function () {
        $('#dialog-message').dialog('open');
        return false;
    });

    // Dialogs
    $("#dialog-message").dialog({
        autoOpen: false,
        width: 400,
        modal: true,
        buttons: {
            "Ok": function () {
                $(this).dialog("close");
            },
            "Cancel": function () {
                $(this).dialog("close");
            }
        }
    });

});