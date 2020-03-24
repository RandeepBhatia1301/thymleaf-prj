$(document).ready(function () {

    /*
    * ==============================================|
    *    Organization form                          |
    * ==============================================|
    * */

    $('#organizationForm').validate({
        rules: {
            clientName: {required: true, maxlength: 60},
            clientAbb: {required: true, maxlength: 60},
            address1: {required: true, maxlength: 100},
            address2: {required: true, maxlength: 100},
            city: {required: true, maxlength: 60},
            state: {required: true, maxlength: 60},
            zip: {required: true, maxlength: 10},
            country: {required: true, maxlength: 60},
            phone: {required: true, maxlength: 15},
            orgImg: {
                required: {
                    depends: function (element) {
                        return ($("#logo_image").length <= 0 ) ? true : false;
                    }
                }, extension: "jpg|jpeg|png"
            },
            appName: {required: true, maxlength: 60},
            appUrl: {
                required: true, url: true,
                normalizer: function (value) {
                    var url = value;
                    // Check if it doesn't start with http:// or https:// or ftp://
                    if (url && url.substr(0, 7) !== "http://"
                        && url.substr(0, 8) !== "https://"
                        && url.substr(0, 6) !== "ftp://") {
                        // then prefix with http://
                        url = "http://" + url;
                    }
                    // Return the new url
                    return url;
                }
            },
            apiUrl: {
                required: true, url: true,
                normalizer: function (value) {
                    var url = value;
                    // Check if it doesn't start with http:// or https:// or ftp://
                    if (url && url.substr(0, 7) !== "http://"
                        && url.substr(0, 8) !== "https://"
                        && url.substr(0, 6) !== "ftp://") {
                        // then prefix with http://
                        url = "http://" + url;
                    }
                    // Return the new url
                    return url;
                }
            },
            firstName: {required: true, maxlength: 60},
            lastName: {required: true, maxlength: 60},
            email: {required: true, maxlength: 60, email: true},
            password: {required: true, maxlength: 10, minlength: 6},
            confPassword: {equalTo: "#password"},
            ssoRadio: {required: true},
            ssourl: {required: true, maxlength: 60},
            ssokey: {required: true, maxlength: 60},
            ssotype: {required: true, maxlength: 60},
            "content[]": {required: true}
        },
        messages: {
            clientName: {
                required: "Organization name is required",
                maxlength: "Organization name must be no longer than 60 characters"
            },
            clientAbb: {
                required: "Abbreviation is required",
                maxlength: "Abbreviation must be no longer than 60 characters"
            },
            address1: {required: "Address is required", maxlength: "Address must be no longer than 100 characters"},
            address2: {required: "Address 2 is required", maxlength: "Address must be no longer than 100 characters"},
            city: {required: "City is required", maxlength: "City must be no longer than 60 characters"},
            state: {required: "State is required", maxlength: "State must be no longer than 60 characters"},
            zip: {required: "Zip code is required", maxlength: "Zip code must be no longer than 60 characters"},
            country: {required: "Country is required", maxlength: "Country must be no longer than 60 characters"},
            phone: {
                required: "Phone number is required",
                maxlength: "Phone number must be no longer than 15 characters"
            },
            orgImg: {required: "Organization image is required", extension: "The file type is not valid"},
            appName: {required: "App name is required", maxlength: "App name must be no longer than 60 characters"},
            appUrl: {required: "App URL is required"},
            apiUrl: {required: "API URL is required"},
            firstName: {
                required: "First name is required",
                maxlength: "First name must be no longer than 60 characters"
            },
            lastName: {required: "Last name is required", maxlength: "Last name must be no longer than 60 characters"},
            email: {required: "Email is required", maxlength: "Email must be no longer than 60 characters"},
            password: {
                required: "Password is required",
                maxlength: "Password must be no longer than 10 characters",
                minlength: "Password must be no less than 6 characters"
            },
            confPassword: {equalTo: "Confirm password must be match with password"},
            ssoRadio: {required: "sso setting is required"},
            ssourl: {required: "Sso URL is required", maxlength: "Sso URL must be no longer than 60 characters"},
            ssokey: {required: "Sso key is required", maxlength: "Sso key must be no longer than 60 characters"},
            ssotype: {required: "Sso type is required", maxlength: "Sso type must be no longer than 60 characters"},
            "content[]": {required: "At least one product is required"}
        },
        errorPlacement: function (error, element) {
            error.attr('style', 'color:rgb(239, 101, 47)');

            if (element.attr('name') == 'orgImg') {
                var parent = element.parents('.file-upload-box');
                error.insertAfter(parent);
            } else if (element.attr("name") == "content[]") {
                error.insertAfter("#contentErrorPlacement");
            } else {
                error.insertAfter(element);
                error.css('color', '#ef652f');
            }
        }
    });

    $('#add-Content').on('show.bs.modal', function (event) {

        $('#add-content').validate({
            rules: {
                name: {required: true, maxlength: 60},
                description: {required: true, maxlength: 100}
            },
            messages: {
                name: {
                    required: "Content name is required",
                    maxlength: "Content name must be no longer than 60 characters"
                },
                description: {
                    required: "description is required",
                    maxlength: "description must be no longer than 100 characters"
                }
            },
            errorPlacement: function (error, element) {
                error.attr('style', 'color:rgb(239, 101, 47)');

                if (element.attr('name') == 'communityImg') {
                    var parent = element.parents('.file-upload-box');
                    error.insertAfter(parent);
                } else {
                    error.insertAfter(element);
                    error.css('color', '#ef652f');
                }
            }
        });

        $('#editContent-saas').validate({
            rules: {
                name: {required: true, maxlength: 60},
                description: {required: true, maxlength: 100}
            },
            messages: {
                name: {
                    required: "Content name is required",
                    maxlength: "Content name must be no longer than 60 characters"
                },
                description: {
                    required: "description is required",
                    maxlength: "description must be no longer than 100 characters"
                }
            },
            errorPlacement: function (error, element) {
                error.attr('style', 'color:rgb(239, 101, 47)');

                if (element.attr('name') == 'communityImg') {
                    var parent = element.parents('.file-upload-box');
                    error.insertAfter(parent);
                } else {
                    error.insertAfter(element);
                    error.css('color', '#ef652f');
                }
            }
        });
    });

    $('#editLanguage').validate({
        rules: {
            name: {required: true, maxlength: 60},
            jsonInput: {required: true}

        },
        messages: {
            name: {
                required: "Language name is required",
                maxlength: "Language name must be no longer than 60 characters"
            },
            jsonInput: {
                required: "This field cannot be empty"
            }
        },
        errorPlacement: function (error, element) {
            error.attr('style', 'color:rgb(239, 101, 47)');

            if (element.attr('name') == 'communityImg') {
                var parent = element.parents('.file-upload-box');
                error.insertAfter(parent);
            } else {
                error.insertAfter(element);
                error.css('color', '#ef652f');
            }
        }
    });

    $('#add-language').on('show.bs.modal', function (event) {
        $('#addLanguage').validate({
            rules: {
                name: {required: true, maxlength: 60},
                uploadFile: {required: true}
            },
            messages: {
                name: {
                    required: "Language name is required",
                    maxlength: "Language name must be no longer than 60 characters"
                },
                uploadFile: {
                    required: "File is required"
                }
            }
        });
    });
    /*user creation and edit*/
    $('#add-User').on('show.bs.modal', function (event) {
        $('#add-user').validate({
            rules: {
                firstName: {required: true, maxlength: 60},
                lastName: {required: true, maxlength: 60},
                email: {required: true, maxlength: 60, email: true},
                password: {required: true, maxlength: 10, minlength: 6},
                orgList: {required: true},
                confPassword: {equalTo: "#password"}
            },
            messages: {
                firstName: {
                    required: "First name is required",
                    maxlength: "First name must be no longer than 60 characters"
                },
                lastName: {
                    required: "Last name is required",
                    maxlength: "Last name must be no longer than 60 characters"
                },
                email: {required: "Email is required", maxlength: "Email must be no longer than 60 characters"},
                password: {
                    required: "Password is required",
                    maxlength: "Password must be no longer than 10 characters",
                    minlength: "Password must be no less than 6 characters"
                },
                confPassword: {equalTo: "Confirm password must be match with password"},
                orgList: {required: "Organization is required"}
            }
        });
    });
    /*product hierarchy edit*/
    $('#add-product').on('show.bs.modal', function (event) {
        $('#productForm').validate({
            rules: {
                name: {required: true, maxlength: 60},
                description: {required: true, maxlength: 100}
            },
            messages: {
                name: {
                    required: "Name is required",
                    maxlength: "Name must be no longer than 60 characters"
                },
                description: {
                    required: "Description is required",
                    maxlength: "Description must be no longer than 100 characters"
                }
            }
        });
    });

    /*sub org create*/


    $('#createSubOrg').validate({
        rules: {
            name: {required: true, maxlength: 60},
            abbreviations: {required: true, maxlength: 60},
            address_line1: {required: true, maxlength: 100},
            address_line2: {required: true, maxlength: 100},
            city: {required: true, maxlength: 60},
            state: {required: true, maxlength: 60},
            zip: {required: true, maxlength: 10, number: true},
            country: {required: true, maxlength: 60},
            phone_number: {required: true, maxlength: 15, number: true},
            subOrgImg: {
                required: {
                    depends: function (element) {
                        return ($("#logo_image").length <= 0 ) ? true : false;
                    }
                }, extension: "jpg|jpeg|png"
            },
            appName: {required: true, maxlength: 60},
            appUrl: {
                required: true, url: true,
                normalizer: function (value) {
                    var url = value;
                    // Check if it doesn't start with http:// or https:// or ftp://
                    if (url && url.substr(0, 7) !== "http://"
                        && url.substr(0, 8) !== "https://"
                        && url.substr(0, 6) !== "ftp://") {
                        // then prefix with http://
                        url = "http://" + url;
                    }
                    // Return the new url
                    return url;
                }
            },
            apiUrl: {
                required: true, url: true,
                normalizer: function (value) {
                    var url = value;
                    // Check if it doesn't start with http:// or https:// or ftp://
                    if (url && url.substr(0, 7) !== "http://"
                        && url.substr(0, 8) !== "https://"
                        && url.substr(0, 6) !== "ftp://") {
                        // then prefix with http://
                        url = "http://" + url;
                    }
                    // Return the new url
                    return url;
                }
            },
            first_name: {required: true, maxlength: 60},
            last_name: {required: true, maxlength: 60},
            email: {required: true, maxlength: 60, email: true},
            password: {required: true, maxlength: 10, minlength: 6},
            confirm_password: {equalTo: "#password"},
            sso_url: {required: true, maxlength: 60},
            sso_key: {required: true, maxlength: 60},
            sso_type: {required: true, maxlength: 60},
            is_default_communities: {required: true}
        },
        messages: {
            name: {
                required: "Sub-Organization name is required",
                maxlength: "Sub-Organization name must be no longer than 60 characters"
            },
            abbreviations: {
                required: "Abbreviation is required",
                maxlength: "Abbreviation must be no longer than 60 characters"
            },
            address_line1: {
                required: "Address is required",
                maxlength: "Address must be no longer than 100 characters"
            },
            address_line2: {
                required: "Address 2 is required",
                maxlength: "Address must be no longer than 100 characters"
            },
            city: {required: "City is required", maxlength: "City must be no longer than 60 characters"},
            state: {required: "State is required", maxlength: "State must be no longer than 60 characters"},
            zip: {required: "Zip code is required", maxlength: "Zip code must be no longer than 60 characters"},
            country: {required: "Country is required", maxlength: "Country must be no longer than 60 characters"},
            phone_number: {
                required: "Phone number is required",
                maxlength: "Phone number must be no longer than 15 characters"
            },
            subOrgImg: {required: "Sub-Organization image is required", extension: "The file type is not valid"},
            appName: {required: "App name is required", maxlength: "App name must be no longer than 60 characters"},
            appUrl: {required: "App URL is required"},
            apiUrl: {required: "API URL is required"},
            first_name: {
                required: "First name is required",
                maxlength: "First name must be no longer than 60 characters"
            },
            last_name: {required: "Last name is required", maxlength: "Last name must be no longer than 60 characters"},
            email: {required: "Email is required", maxlength: "Email must be no longer than 60 characters"},
            password: {
                required: "Password is required",
                maxlength: "Password must be no longer than 10 characters",
                minlength: "Password must be no less than 6 characters"
            },
            confirm_password: {equalTo: "Confirm password must be match with password"},
            sso_url: {required: "Sso URL is required", maxlength: "Sso URL must be no longer than 60 characters"},
            sso_key: {required: "Sso key is required", maxlength: "Sso key must be no longer than 60 characters"},
            sso_type: {required: "Sso type is required", maxlength: "Sso type must be no longer than 60 characters"},
            is_default_communities: {required: "This field  is required"}

        },
        errorPlacement: function (error, element) {
            error.attr('style', 'color:rgb(239, 101, 47)');

            if (element.attr('name') == 'subOrgImg') {
                var parent = element.parents('.file-upload-box');
                error.insertAfter(parent);
            } else {
                error.insertAfter(element);
                error.css('color', '#ef652f');
            }
        }
    });
    /*add/edit activity*/

    $('#add-activity').on('show.bs.modal', function (event) {
        $('#add-category').validate({
            rules: {
                name: {required: true, maxlength: 60}
            },
            messages: {
                name: {
                    required: "Activity name is required",
                    maxlength: "First name must be no longer than 60 characters"
                }
            },
            errorPlacement: function (error, element) {
                error.attr('style', 'color:rgb(239, 101, 47)');

                if (element.attr('name') == 'communityImg') {
                    var parent = element.parents('.file-upload-box');
                    error.insertAfter(parent);
                } else {
                    error.insertAfter(element);
                    error.css('color', '#ef652f');
                }
            }
        });

        $('#addactivity').validate({
            rules: {
                name: {required: true},
                c1: {required: true, number: true},
                c2: {required: true, number: true},
                c3: {required: true, number: true},
                c4: {required: true, number: true},
                c5: {required: true, number: true},
                category: {required: true},
                code: {required: true}
            },
            messages: {
                name: {
                    required: "Activity name is required"
                },
                c1: {required: "This is required", number: "invalid entry"},
                c2: {required: "This is required", number: "invalid entry"},
                c3: {required: "This is required", number: "invalid entry"},
                c4: {required: "This is required", number: "invalid entry"},
                c5: {required: "This is required", number: "invalid entry"},
                category: {required: "Category is required"}
            },
            errorPlacement: function (error, element) {
                error.attr('style', 'color:rgb(239, 101, 47)');

                if (element.attr('name') == 'communityImg') {
                    var parent = element.parents('.file-upload-box');
                    error.insertAfter(parent);
                } else {
                    error.insertAfter(element);
                    error.css('color', '#ef652f');
                }
            }

        });

    });
    /*module add/edit*/
    $('#add-module').on('show.bs.modal', function (event) {
        $('#add-modules').validate({
            rules: {
                name: {required: true, maxlength: 60},
                description: {required: true, maxlength: 100}
            },
            messages: {
                name: {
                    required: "Module name is required",
                    maxlength: "First name must be no longer than 60 characters"
                },
                description: {
                    required: "Description is required",
                    maxlength: "Description must be no longer than 100 characters"
                }
            },
            errorPlacement: function (error, element) {
                error.attr('style', 'color:rgb(239, 101, 47)');

                if (element.attr('name') == 'communityImg') {
                    var parent = element.parents('.file-upload-box');
                    error.insertAfter(parent);
                } else {
                    error.insertAfter(element);
                    error.css('color', '#ef652f');
                }
            }
        });

    });


    $('#orgContentEdit').validate({
        rules: {
            languageList: {required: true},
            archivalView: {required: true},
            archivalPeriod: {required: true},
            jsonInput: {required: true}

        },
        messages: {
            languageList: {
                required: "Language is required"
            },
            archivalView: {
                required: "This is required"
            },
            archivalPeriod: {
                required: "Archival Period is required"
            },
            jsonInput: {
                required: "This field cannot be empty"
            }
        },
        errorPlacement: function (error, element) {
            error.attr('style', 'color:rgb(239, 101, 47)');

            if (element.attr('name') == 'communityImg') {
                var parent = element.parents('.file-upload-box');
                error.insertAfter(parent);
            } else {
                error.insertAfter(element);
                error.css('color', '#ef652f');
            }
        }
    });
    /*community add/edit*/
    $('#community').validate({
        rules: {
            title: {required: true, maxlength: 60},
            isPrivate: {required: true},
            categoryType: {required: true},
            communityImg: {required: true},
            description: {required: true, maxlength: 100}

        },
        messages: {
            title: {
                required: "Name is required"
            }
        },
        errorPlacement: function (error, element) {
            error.attr('style', 'color:rgb(239, 101, 47)');

            if (element.attr('name') == 'communityImg') {
                var parent = element.parents('.file-upload-box');
                error.insertAfter(parent);
            } else {
                error.insertAfter(element);
                error.css('color', '#ef652f');
            }
        }
    });
    /*AOI add/edit*/
    $('#aoi').validate({
        rules: {
            title: {required: true, maxlength: 60},
            isPrivate: {required: true},
            categoryId: {required: true},
            aoiImg: {required: true},
            description: {required: true, maxlength: 100}

        },
        messages: {
            title: {
                required: "Name is required"
            }
        },
        errorPlacement: function (error, element) {
            error.attr('style', 'color:rgb(239, 101, 47)');

            if (element.attr('name') == 'aoiImg') {
                var parent = element.parents('.file-upload-box');
                error.insertAfter(parent);
            } else {
                error.insertAfter(element);
                error.css('color', '#ef652f');
            }
        }
    });
    /*category add/edit*/
    $('#add-category').on('show.bs.modal', function (event) {
        $('#categoryForm').validate({
            rules: {
                name: {required: true, maxlength: 60}
            },
            messages: {
                name: {
                    required: "Category name is required",
                    maxlength: "Category name must be no longer than 60 characters"
                }
            },
            errorPlacement: function (error, element) {
                error.attr('style', 'color:rgb(239, 101, 47)');

                if (element.attr('name') == 'badgeImg') {
                    var parent = element.parents('.file-upload-box');
                    error.insertAfter(parent);
                } else {
                    error.insertAfter(element);
                    error.css('color', '#ef652f');
                }
            }
        });

    });

    /*badge add/edit*/
    $('#add-badge').on('show.bs.modal', function (event) {
        $('#add-badges').validate({
            rules: {
                name: {required: true, maxlength: 60},
                description: {required: true, maxlength: 100},
                level_name: {required: true},
              /*  "level_value[]": {required: true},*/
                isAutomatic: {required: true},
                isPeerToPeer: {required: true},
                isPeerToAdmin: {required: true},
                popMsg: {required: true, maxlength: 200},
                dashBoardMsg: {required: true, maxlength: 100},
                code: {required: true}
            },
            messages: {
                name: {
                    required: "Badge name is required",
                    maxlength: "Badge name must be no longer than 60 characters"
                },
                badgeImg: {
                    required: "Badge Image is required"

                },
                description: {
                    required: "Badge description is required",
                    maxlength: "Badge description must be no longer than 60 characters"

                },
                popMsg: {
                    required: "Popup message is required",
                    maxlength: "Popup message must be no longer than 200 characters"

                },
                dashBoardMsg: {
                    required: "Dashboard message is required",
                    maxlength: "Dashboard message must be no longer than 60 characters"

                }
            },
            errorPlacement: function (error, element) {
                error.attr('style', 'color:rgb(239, 101, 47)');

                if (element.attr('name') == 'badgeImg') {
                    var parent = element.parents('.file-upload-box');
                    error.insertAfter(parent);
                } else {
                    error.insertAfter(element);
                    error.css('color', '#ef652f');
                }
            }
        });


    });
    /*Moderation validation*/
    $('#moderation').validate({
        rules: {
            categoryName: {required: true},
            joinComm: {required: true},
            roleId: {required: true}
        },
        errorPlacement: function (error, element) {
            error.attr('style', 'color:rgb(239, 101, 47)');

            if (element.attr('name') == 'aoiImg') {
                var parent = element.parents('.file-upload-box');
                error.insertAfter(parent);
            } else {
                error.insertAfter(element);
                error.css('color', '#ef652f');
            }
        }
    });
    /*stop words */

    $('#add-stopword').on('show.bs.modal', function (event) {
        $('#stopWordForm').validate({
            rules: {
                name: {required: true, maxlength: 60}
            },
            messages: {
                name: {
                    required: "Stop word name is required",
                    maxlength: "Name must be no longer than 60 characters"
                }
            },
            errorPlacement: function (error, element) {
                error.attr('style', 'color:rgb(239, 101, 47)');

                if (element.attr('name') == 'aoiImg') {
                    var parent = element.parents('.file-upload-box');
                    error.insertAfter(parent);
                } else {
                    error.insertAfter(element);
                    error.css('color', '#ef652f');
                }
            }
        });
    });
    /*Moderation content*/
    $('#contentModeration').validate({
        rules: {
            categoryId: {required: true},
            numberOfLevel: {required: true},
            level1RoleId: {required: true},
            level2RoleId: {required: true}
        },
        errorPlacement: function (error, element) {
            error.attr('style', 'color:rgb(239, 101, 47)');

            if (element.attr('name') == 'aoiImg') {
                var parent = element.parents('.file-upload-box');
                error.insertAfter(parent);
            } else {
                error.insertAfter(element);
                error.css('color', '#ef652f');
            }
        }
    });
});