/**
 * Created by Karl Jean-Brice and Raymond Xue
 */
$(document).ready(function () {

    show_welcome_area();
    hide_list_area();
    check_user_status();

    var file_list_template = "" +
        "{{#Files}}" +
        "<li class='list_file' list_name='{{listname}}' owner_name ='{{ownername}}'><a href='javascript:void(0)'>{{listname}}/{{ownername}}</a></li>" +
        "{{/Files}}";

    var list_items_template = "" +
        "{{#Items}}" +
        "<tr class='list_item' item_id = '{{frontID}}'>" +
        "<td class= 'category'  item_value='{{category}}'>{{category}}</td>" +
        "<td class= 'description' item_value='{{description}}'>{{description}}</td>" +
        "<td class= 'startDate' item_value='{{startDate}}'>{{startDate}}</td>" +
        "<td class= 'endDate' item_value='{{endDate}}'>{{endDate}}</td>" +
        "<td class= 'completed' item_value='{{completed}}'>{{completed}}</td>" +
        "</tr>{{/Items}}"
        + "<tr listnum ='null'>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>"
        + "<tr listnum ='null'>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>"
        + "<tr listnum ='null'>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>"
        + "<tr listnum ='null'>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>" +
        "<td>&nbsp;</td>";


    function load_table_items(type) {
        var item_area = $('#item_area');
        var $url = "/requestitems.htm";
        console.log("Type:" + type);
        $.ajax({
            method: 'get',
            url: $url,
            dataType: 'json',
            success: function (item_table) {
                console.log("Get List Items:Success");
                var JSON_list_items = item_table;

                $('#owner_name').val(JSON_list_items.owner);


                if (type === "LOADING") {
                    $('#list_name').val(JSON_list_items.listname);
                    console.log("Private:" + JSON_list_items.private);
                    if (JSON_list_items.private === "true") {
                        enable_components();
                        console.log("Private: CHECKED");
                        $('#chkbox_privacy').bootstrapToggle('on');
                    }
                    else {
                        enable_components();
                        console.log("Private: UNCHECKED");
                        $('#chkbox_privacy').bootstrapToggle('off');
                    }
                }

                if (type === "NEW_LIST") {
                    $('#list_name').val("");
                    $('#chkbox_privacy').removeAttr("checked");
                }

                var owner_name = $('#owner_name').val().trim();
                var active_email = $('#a_email').attr("email_val").trim();
                console.log("owner_name" + owner_name);
                console.log("active_email" + active_email);

                var list_data = Mustache.render(list_items_template, JSON_list_items);
                item_area.hide();
                item_area.html(list_data);
                item_area.show();

                if (owner_name === active_email) {
                    enable_components();
                }
                else {

                    disable_components();
                }
                console.log(list_data);

            },
            error: function () {
                console.log("Loading the table: Aw, It didn't connect to the servlet :(");
            }

        });
    }

    function disable_components() {
        $('#save_list').hide();
        $('#list_name').prop("disabled", true);
        $('#owner_name').prop("disabled", true);
        $('#btn_add_glyph').prop("disabled", true);
        $('#btn_delete_glyph').prop("disabled", true);
        $('#btn_move_up').prop("disabled", true);
        $('#btn_move_down').prop("disabled", true);
        $('#btn_edit_submit').prop("disabled", true);
        $('#btn_edit_submit').prop("disabled", true);
        $('#chkbox_privacy').bootstrapToggle('disable');
    }


    function enable_components() {
        $('#save_list').show();
        $('#list_name').prop("disabled", false);
        $('#owner_name').prop("disabled", true);
        $('#btn_add_glyph').prop("disabled", false);
        $('#btn_delete_glyph').prop("disabled", false);
        $('#btn_move_up').prop("disabled", false);
        $('#btn_move_down').prop("disabled", false);
        $('#btn_edit_submit').prop("disabled", false);
        $('#btn_edit_submit').prop("disabled", false);
        $('#chkbox_privacy').bootstrapToggle('enable');
    }

    function load_files_list() {
        var list_area = $('#load_list_area');
        list_area.html("<li><a href='javascript:void(0)'>Searching...</a></li>");
        var $url = "/requestfiles.htm";

        $.ajax({
            method: 'get',
            url: $url,
            dataType: 'json',
            success: function (file_items) {
                console.log("FILE LIST:Success");
                var JSON_file_items = file_items;
                var list_data = Mustache.render(file_list_template, JSON_file_items);
                list_area.html(list_data);
                console.log(list_data);
            },
            error: function () {
                console.log("Loading file list: Aw, It didn't connect to the servlet :(");
            }

        });
    }


    $('body').on('click', 'li.list_file', function (e) {
        var file_row = $(this);
        var list_name = file_row.attr("list_name");
        var owner_name = file_row.attr("owner_name");

        var notification_textarea = $('#notification_textarea');
        var notification_title = $('#notification_title');

        var url = "/loadlist.htm?listName=" + list_name + "&owner=" + owner_name;
        $.ajax({
            method: 'get',
            url: url,
            dataType: 'text',
            success: function (loadlist_status) {
                console.log(loadlist_status);
                if (loadlist_status.trim() === "SUCCESS") {
                    hide_welcome_area();
                    hide_list_area();
                    //notification_title.html("Load Status");
                    //notification_textarea.text("\"" + list_name + "\" has loaded successfully.");
                    //$('#notification_modal').modal('show');
                    load_table_items("LOADING");

                    show_list_area();
                }
                else {
                    console.log("LOAD FAILURE: This is strange. I wonder why it failed.");
                }

            },
            error: function () {
                console.log("LOAD LIST FAILURE: Aw, It didn't connect to the servlet :(");
            }
        });


    });

    $('body').on('dblclick', 'tr.list_item', function (e) {
        var item_row = $(this);
        var item_id = item_row.attr("item_id");
        var category = item_row.find('td[class="category"]').attr("item_value");
        var description = item_row.find('td[class="description"]').attr("item_value");
        var startDate = item_row.find('td[class="startDate"]').attr("item_value");
        var endDate = item_row.find('td[class="endDate"]').attr("item_value");
        var completed = item_row.find('td[class="completed"]').attr("item_value");

        $('#edit_category').val(category);
        $('#edit_description').val(description);
        $('#edit_startdate').val(startDate);
        $('#edit_enddate').val(endDate);
        $('#edit_completed').prop("checked", "checked");
        $('#edit_id').attr("edit_id", item_id);

        $('#edit_modal').modal('show');

    });

    $('body').on('click', 'tr', function (e) {
        $('.selectTable').removeClass('selectTable');
        $(this).addClass("selectTable");


    });


    $('body').on('click', '#new_list', function () {
        var notification_textarea = $('#notification_textarea');
        var notification_title = $('#notification_title');

        var url = "/newlist.htm";
        $.ajax({
            method: 'get',
            url: url,
            dataType: 'text',
            success: function (newlist_status) {
                if (newlist_status.trim() === "NEWLIST:SUCCESS") {
                    //hide_welcome_screen();
                    console.log("NEWLIST:SUCCESS");
                    hide_list_area();
                    hide_welcome_area();
                    show_list_area();

                    notification_title.html("New List");
                    notification_textarea.text("You just created a new list!");
                    $('#notification_modal').modal('show');
                    load_table_items("NEW_LIST");
                    //clear table();
                }
                else {
                    hide_list_area();
                    show_welcome_area();

                    console.log("NEWLIST:FAILURE");
                    notification_title.html("Login");
                    notification_textarea.text("You must login first before you can create a new list.");
                    $('#notification_modal').modal('show');
                    hide_list_area();
                }

                console.log(newlist_status);
            },
            error: function () {
                console.log("NEW LIST FAILURE: Aw, It didn't connect to the servlet :(");
            }
        });
    });

    $('body').on('click', '#load_list', function () {
        load_files_list();
    });

    $('body').on('click', '#save_list', function () {
        var notification_textarea = $('#notification_textarea');
        var notification_title = $('#notification_title');

        if (check_user_status() === false) {
            hide_list_area();
            show_welcome_area();
            notification_title.html("Login");
            notification_textarea.text("You must login first before you can make this request.");
            $('#notification_modal').modal('show');
        }
        else {
            var list_name = $('#list_name').val().trim();
            var privacy = $('#chkbox_privacy').prop("checked");
            var err_label = $('#err_save_item');
            err_label.hide();

            var url = "/savelist.htm?listName=" + list_name + "&privacy=" + privacy;
            $.ajax({
                method: 'get',
                url: url,
                dataType: 'text',
                success: function (savelist_status) {
                    if (savelist_status.trim() === "SUCCESS") {
                        //hide_welcome_screen();
                        console.log("SAVELIST:SUCCESS");
                        notification_title.html("Save Successful");
                        notification_textarea.text("Save successful. Your list has been saved as \"" + list_name + "\"");
                        $('#notification_modal').modal('show');
                    }
                    else {
                        err_label.text("The list name is required to save this list.");
                        err_label.fadeIn(1000);
                        console.log("SAVELIST:FAILURE");
                    }

                    console.log(savelist_status);
                },
                error: function () {
                    console.log("SAVE LIST FAILURE: Aw, It didn't connect to the servlet :(");
                }
            });
        }
    });


    $('body').on('click', '#btn_delete_glyph', function (e) {
        if ($('.list_item.selectTable').length) {
            $('#delete_modal').modal('show');
        }
    });


    $('body').on('click', '#btn_delete_close', function (e) {
        $('#delete_modal').modal('hide');
    });


    $('body').on('click', '#btn_delete_confirm', function (e) {

        var notification_textarea = $('#notification_textarea');
        var notification_title = $('#notification_title');

        if (check_user_status() === false) {
            hide_list_area();
            show_welcome_area();
            notification_title.html("Login");
            notification_textarea.text("You must login first before you can make this request.");
            $('#notification_modal').modal('show');
        } else {
            var item_id;
            if ($('.list_item.selectTable').length) {
                item_id = $('.list_item.selectTable').attr("item_id");
            }
            else {
                console.log("DELETE_CONFIRM: An error occurred.");
            }


            var url = "/deleteitem.htm?frontID=" + item_id;
            $.ajax({
                method: 'get',
                url: url,
                dataType: 'text',
                success: function (deleteitem_status) {
                    if (deleteitem_status.trim() === "SUCCESS") {
                        load_table_items("DELETE");
                    } else {
                        console.log("DELETE ITEM:ERROR");
                    }
                    $('#delete_modal').modal('hide');
                },
                error: function () {
                    console.log("Delete Item Failure: Aw, It didn't connect to the servlet :(");
                }

            });
        }
    });


    $('body').on('click', '#btn_move_up', function (e) {
        var notification_textarea = $('#notification_textarea');
        var notification_title = $('#notification_title');

        if (check_user_status() === false) {
            hide_list_area();
            show_welcome_area();
            notification_title.html("Login");
            notification_textarea.text("You must login first before you can make this request.");
            $('#notification_modal').modal('show');
        } else {
            var item_id;
            if ($('.list_item.selectTable').length) {
                item_id = $('.list_item.selectTable').attr("item_id");

                var url = "/moveup.htm?frontID=" + item_id;
                $.ajax({
                    method: 'get',
                    url: url,
                    dataType: 'text',
                    success: function (move_up_status) {
                        if (move_up_status.trim() === "SUCCESS") {
                            load_table_items("MOVE_UP");
                        } else {
                            console.log("MOVE UP ITEM:ERROR");
                        }
                    },
                    error: function () {
                        console.log("MOVE UP Item Failure: Aw, It didn't connect to the servlet :(");
                    }

                });
            } else {
                console.log("MOVE UP ERROR: Can't find the item to move up");
            }
        }
    });


    $('body').on('click', '#btn_move_down', function (e) {

        var notification_textarea = $('#notification_textarea');
        var notification_title = $('#notification_title');

        if (check_user_status() === false) {
            hide_list_area();
            show_welcome_area();
            notification_title.html("Login");
            notification_textarea.text("You must login first before you can make this request.");
            $('#notification_modal').modal('show');
        } else {
            var item_id;
            if ($('.list_item.selectTable').length) {
                item_id = $('.list_item.selectTable').attr("item_id");

                var url = "/movedown.htm?frontID=" + item_id;
                $.ajax({
                    method: 'get',
                    url: url,
                    dataType: 'text',
                    success: function (move_down_status) {
                        if (move_down_status.trim() === "SUCCESS") {
                            load_table_items("MOVE_DOWN");
                        } else {
                            console.log("MOVE DOWN ITEM:ERROR");
                        }
                    },
                    error: function () {
                        console.log("MOVE DOWN Item Failure: Aw, It didn't connect to the servlet :(");
                    }

                });
            } else {
                console.log("MOVE DOWN ERROR: Can't find the item to move down");
            }
        }
    });


    /*Sorts selection by category*/
    $('body').on('click', '#header_category', function (e) {
        var header_category = $(this);
        var category_image = $('#category_image');
        var method = header_category.attr("methodval").trim();

        var url = "/sortbycat.htm";
        if (method === "default") {
            sort_header(url, "ascend");
            header_category.attr("methodval", "ascend");
            category_image.addClass("glyphicon-arrow-up");
        }
        else if (method === "ascend") {
            sort_header(url, "descend");
            header_category.attr("methodval", "descend");
            category_image.removeClass("glyphicon-arrow-up");
            category_image.addClass("glyphicon-arrow-down");
        }
        else {
            sort_header(url, "ascend");
            header_category.attr("methodval", "ascend");
            category_image.removeClass("glyphicon-arrow-down");
            category_image.addClass("glyphicon-arrow-up");
        }
    });

    /*Sorts selection by description*/
    $('body').on('click', '#header_description', function (e) {
        var header_description = $(this);
        var description_image = $('#description_image');
        var method = header_description.attr("methodval").trim();

        var url = "/sortbydesc.htm";
        if (method === "default") {
            sort_header(url, "ascend");
            header_description.attr("methodval", "ascend");
            description_image.addClass("glyphicon-arrow-up");
        }
        else if (method === "ascend") {
            sort_header(url, "descend");
            header_description.attr("methodval", "descend");
            description_image.removeClass("glyphicon-arrow-up");
            description_image.addClass("glyphicon-arrow-down");
        }
        else {
            sort_header(url, "ascend");
            header_description.attr("methodval", "ascend");
            description_image.removeClass("glyphicon-arrow-down");
            description_image.addClass("glyphicon-arrow-up");
        }
    });


    /*Sorts selection by start date*/
    $('body').on('click', '#header_startdate', function (e) {
        var header_startdate = $(this);
        var startdate_image = $('#startdate_image');
        var method = header_startdate.attr("methodval").trim();

        var url = "/sortbystart.htm";
        if (method === "default") {
            sort_header(url, "ascend");
            header_startdate.attr("methodval", "ascend");
            startdate_image.addClass("glyphicon-arrow-up");
        }
        else if (method === "ascend") {
            sort_header(url, "descend");
            header_startdate.attr("methodval", "descend");
            startdate_image.removeClass("glyphicon-arrow-up");
            startdate_image.addClass("glyphicon-arrow-down");
        }
        else {
            sort_header(url, "ascend");
            header_startdate.attr("methodval", "ascend");
            startdate_image.removeClass("glyphicon-arrow-down");
            startdate_image.addClass("glyphicon-arrow-up");
        }
    });


    /*Sorts selection by end date*/
    $('body').on('click', '#header_enddate', function (e) {
        var header_enddate = $(this);
        var enddate_image = $('#enddate_image');
        var method = header_enddate.attr("methodval").trim();

        var url = "/sortbyend.htm";
        if (method === "default") {
            sort_header(url, "ascend");
            header_enddate.attr("methodval", "ascend");
            enddate_image.addClass("glyphicon-arrow-up");
        }
        else if (method === "ascend") {
            sort_header(url, "descend");
            header_enddate.attr("methodval", "descend");
            enddate_image.removeClass("glyphicon-arrow-up");
            enddate_image.addClass("glyphicon-arrow-down");
        }
        else {
            sort_header(url, "ascend");
            header_enddate.attr("methodval", "ascend");
            enddate_image.removeClass("glyphicon-arrow-down");
            enddate_image.addClass("glyphicon-arrow-up");
        }
    });


    function sort_header(url, method) {
        var notification_textarea = $('#notification_textarea');
        var notification_title = $('#notification_title');

        if (check_user_status() === false) {
            hide_list_area();
            show_welcome_area();
            notification_title.html("Login");
            notification_textarea.text("You must login first before you can make this request.");
            $('#notification_modal').modal('show');
        } else {
            var url = url.trim() + "?method=" + method;
            $.ajax({
                method: 'get',
                url: url,
                dataType: 'text',
                success: function (sort_status) {
                    if (sort_status.trim() === "SUCCESS") {
                        load_table_items("SORT");
                    } else {
                        console.log("SORT FAILURE:ERROR");
                    }
                },
                error: function () {
                    console.log("SORT FAILURE Item Failure: Aw, It didn't connect to the servlet :(");
                }

            });
        }
    }


    $('body').on('click', '#btn_add_submit', function (e) {
        var notification_textarea = $('#notification_textarea');
        var notification_title = $('#notification_title');

        if (check_user_status() === false) {
            hide_list_area();
            show_welcome_area();
            $('#add_modal').modal('hide');

            notification_title.html("Login");
            notification_textarea.text("You must login first before you can make this request.");
            $('#notification_modal').modal('show');
        }
        else {
            var category = $('#txt_category').val().trim();
            var description = $('#txt_description').val().trim();
            var startdate = $('#txt_startdate').val().trim();
            var enddate = $('#txt_enddate').val().trim();
            var completed = $('#chkbox_completed').prop("checked");
            var err_label = $('#err_add_item');
            err_label.hide();


            var url = "/additem.htm?category=" + category + "&description=" + description + "&startDate=" + startdate + "&endDate=" + enddate + "&completed=" + completed;
            $.ajax({
                method: 'post',
                url: url,
                dataType: 'text',
                success: function (additem_status) {
                    if (additem_status.trim() === "SUCCESS") {
                        $('#add_modal').modal('hide');
                        clear_form_data();
                        load_table_items("ADD_ITEM");
                    } else {
                        err_label.text("One or more fields are empty. All fields are required.");
                        err_label.fadeIn(1000);
                        console.log("ADD ITEM:ERROR");
                    }
                },
                error: function () {
                    console.log("Add Item Failure: Aw, It didn't connect to the servlet :(");
                }

            });
        }
    });


    $('body').on('click', '#btn_edit_submit', function (e) {

        var notification_textarea = $('#notification_textarea');
        var notification_title = $('#notification_title');

        if (check_user_status() === false) {
            hide_list_area();
            show_welcome_area();
            notification_title.html("Login");
            notification_textarea.text("You must login first before you can make this request.");
            $('#notification_modal').modal('show');
        } else {

            var item_id = $('#edit_id').attr('edit_id');
            var category = $('#edit_category').val().trim();
            var description = $('#edit_description').val().trim();
            var startdate = $('#edit_startdate').val().trim();
            var enddate = $('#edit_enddate').val().trim();
            var completed = $('#edit_completed').prop("checked");
            var err_label = $('#err_edit_item');
            err_label.hide();


            var url = "/edititem.htm?category=" + category + "&frontID=" + item_id + "&description=" + description + "&startDate=" + startdate + "&endDate=" + enddate + "&completed=" + completed;
            $.ajax({
                method: 'get',
                url: url,
                dataType: 'text',
                success: function (edititem_status) {
                    if (edititem_status.trim() === "SUCCESS") {
                        $('#edit_modal').modal('hide');
                        load_table_items("EDIT_ITEM");
                    } else {
                        err_label.text("One or more fields are empty. All fields are required.");
                        err_label.fadeIn(1000);
                        console.log("Edit ITEM:ERROR");
                    }
                },
                error: function () {
                    console.log("Edit Item Failure: Aw, It didn't connect to the servlet :(");
                }

            });
        }
    });


    function check_user_status() {
        var user_email = $('#a_email');
        var user_login = $('#a_login');
        var url = "/checkuserstatus.htm";
        var user_data = false;
        $.ajax({
            method: 'get',
            url: url,
            dataType: 'text',
            async: false,
            success: function (user_status) {
                console.log(user_status);
                if (user_status.trim() == "NOT_LOGGED_IN") {
                    hide_list_area();
                    show_welcome_area();
                    user_email.hide();
                    user_login.hide();

                    user_email.text("");
                    user_email.attr("email_val", "");
                    user_login.text("Sign in");

                    user_email.fadeIn(600);
                    user_login.fadeIn(600);

                    user_data = false;
                } else {
                    user_email.hide();
                    user_login.hide();

                    user_email.text("");
                    user_email.text(user_status.trim());
                    user_email.attr("email_val", user_status.trim());
                    user_login.text("Sign out");

                    user_login.fadeIn(600);
                    user_email.fadeIn(600);

                    user_data = true;
                }
            },
            error: function () {
                console.log("CHECK USER FAILURE: Aw, It didn't connect to the servlet :(");
            }
        });

        return user_data;
    }


    function clear_form_data() {

        //Add form
        $('#txt_category').val("");
        $('#txt_description').val("");
        $('#txt_startdate').val("");
        $('#txt_enddate').val("");
        $('#chkbox_completed').removeAttr('checked');

        //Edit form
        $('#edit_category').val("");
        $('#edit_description').val("");
        $('#edit_startdate').val("");
        $('#edit_enddate').val("");
        $('#edit_completed').removeAttr('checked');

        //Error labels
        $('#err_edit_item').hide();
        $('#err_add_item').hide();
        $('#err_save_item').hide();
    }

    function hide_list_area() {
        var list_area = $('[area="content_area"]');
        list_area.removeClass("hide-tag");
        list_area.hide();
        $('#save_list').hide();
    }


    function show_list_area() {
        var list_area = $('[area="content_area"]');
        list_area.fadeIn(1500);
        $('#save_list').fadeIn(1500);
    }


    function show_welcome_area() {
        var welcome_area = $('#welcome_area');
        welcome_area.removeClass("hide-tag");
        welcome_area.hide();
        welcome_area.fadeIn(1500);
    }

    function hide_welcome_area() {
        var welcome_area = $('#welcome_area');
        welcome_area.hide();
    }


});


