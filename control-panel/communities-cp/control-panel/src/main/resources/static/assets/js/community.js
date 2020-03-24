var members = [];

function getResponse() {
    $.ajax({
        type: 'GET',
        url: 'http://192.168.3.186:9010/user',
        data: {},
        dataType: 'json',
        beforeSend: function (setting, xhr) {
            $("#loader").show();
        },
        success: function (data) {
            $("#loader").hide();
            $.each(data, function (i, v) {
                var html = setUsers(v);
                $("#user-list-container").append(html);
                $("#add-comm-popup").modal('show');
            });
        },
        error: function () {
            alert('something bad happened');
        }
    });
}

String.prototype.format = function () {
    var a = this;
    for (k in arguments) {
        a = a.replace("{" + k + "}", arguments[k]);
    }
    return a;
};

function setUsers(user) {
    var wrapper = '<div class="human-srh-result">{0}{1}{2}{3}</div>';
    var userImage = '<span class="r-pic"><img src="{0}"></span>';
    var userName = '<span class="r-title">{0}</span>';
    var userDepartment = '<span class="r-desc"> {0} </span>';
    var input = '<div class="form-group r-check"><label><input type="checkbox" class="minimal userids" name="userIds[]" value="{0}"></label></div>';

    userImage = userImage.format(user.imgPath);
    userName = userName.format(user.userName);
    userDepartment = userDepartment.format(user.department);
    input = input.format(user.id);

    var html = wrapper.format(userImage, userName, userDepartment, input);

    return html;
}

$(document).ready(function () {

    $("#add-comm-popup").on('show.bs.modal', function () {
        $(".userids").next("ins").on("click", function () {
            if ($("#community > .adminInput").length > 0) {
                $("#community > .adminInput").remove();
            }
            $("input.userids:checked").each(function (i, el) {
                var admin = '<input type="checkbox" class="minimal adminInput" name="adminIds[]" value="{0}">';
                admin = admin.format($(el).val());
                $("#community").append(admin);

            });
        });
    });
});

function addSubOrg() {
    var subOrg = $("#subOrg").val();
    var subOrgName = $("#subOrg option:selected").html();
    if (subOrg != "") {
        var li = document.createElement("li");
        $(li).attr("id",subOrg);
        var input = document.createElement("input");
        $(input).attr("type", "hidden");
        $(input).addClass("form-control");
        $(input).attr("name", "suborgId[]");
        $(input).attr("id", "suborgId[]");
        $(input).val(subOrg);
        li.append(subOrgName);
        li.appendChild(input);
        if($("#list-container").find("li#" + subOrg).length <= 0){
            document.getElementById("list-container").appendChild(li);
        }else {
            alert("already exist")
        }

        //$("#list-container").appendChild(li);
    } else {
        alert("Please select a organizations");
    }
}

function removeSubOrg() {
    alert("here");
    //
     $('#list-container li:last-child').remove();
}
