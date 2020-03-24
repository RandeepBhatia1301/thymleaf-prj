$(document).ready(function() {

    /*
    * ----------------------------------------------
    *  ON PAGE LOAD
    *  ---------------------------------------------
    * */

    setSSOFields();

    /*
    * ----------------------------------------------
    *  ON CLICK EVENT
    *  ---------------------------------------------
    * */

    /* $(".isSsoEnabled").next("ins").on("click",setSSOFields);
    $(".isSsoEnabled").on("change",setSSOFields);*/

    $('input').on('ifClicked ifChanged ifChecked ifUnchecked', setSSOFields);

});

function setSSOFields(){
    var isSsoEnabled = $(".isSsoEnabled:checked").val();
    if(isSsoEnabled == 1){
        $(".ssofields").prop("disabled",false);
    }else{
        $(".ssofields").prop("disabled",true);
    }
}