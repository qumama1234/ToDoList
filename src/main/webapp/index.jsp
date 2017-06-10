<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- [START_EXCLUDE] -->
<%--
Created by Karl Jean-Brice
  --%>
<!-- [END_EXCLUDE] -->
<html>
<head>
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="webresources/css/index_styles.css">
    <title>To Do List Maker</title>
</head>
<body class="background-app ">
<div id="top"></div>
<div id="bottom"></div>
<div id="left"></div>
<div id="right"></div>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Quarantine</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active">
                <a id="new_list" href="javascript:void(0)" data-toggle="modal" title="Create a new to do list"><span
                        class="glyphicon glyphicon-folder-close"></span> New
                    List</a>
            </li>

            <li class ="dropdown">
                <a data-toggle="dropdown" id="load_list" href="javascript:void(0)" title="Load an existing to do list"><span class="glyphicon glyphicon-folder-open"></span>
                    Load List <span class = "caret"></span></a>
                    <ul id = "load_list_area" class="dropdown-menu scrollable-menu">
                        <li><a href="javascript:void(0)">Searching...</a></li>
                    </ul>
            </li>
            <li>
                <a id="save_list" href="javascript:void(0)" title="Save to do list"><span class="glyphicon glyphicon-save"></span> Save List</a>
            </li>
            <li>
                <a id="a_email" email_val="" href="javascript:void(0)"></a>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="/login.htm"><span class="glyphicon glyphicon-log-in"></span> <span id="a_login"></span></a>
            </li>
        </ul>
    </div>
</nav>

<div class="container">

    <div class = "row">
         <div id = "welcome_area" class = "col-xs-6 col-xs-offset-3 center-text hide-tag">
             <div  class = "vertical-center welcome-properties">
                 <span>WELCOME</span>
             </div>
         </div>
     </div>

    <div area="content_area" class="hide-tag">
        <div class="row-padding row">
            <span class="col-md-4 heading-label">Things To Do:</span>
        </div>


        <div class=" form-group border-pane">
            <div class="row">
                <span class="col-md-2 subheading-label">Details</span>
            </div>
            <div class="style-glow">
                <div class="row padding-top">
                    <div class="col-md-2">
                        <label for="list_name" class="input-label col-form-label">List name: </label>
                    </div>
                    <div class="col-md-2">
                        <input type="text" id="list_name">
                    </div>
                    <div class = "col-md-4">
                        <span access="admin" class="lbl-error" id="err_save_item"></span>
                    </div>
                    <div class="col-md-4"></div>
                </div>
                <div class="row padding-top">
                    <div class="col-md-2">
                        <label for="owner_name" class="input-label col-form-label">Owner: </label>
                    </div>
                    <div class="col-md-2">
                        <input type="text" id="owner_name">
                    </div>
                    <div class="col-md-8"></div>
                </div>
                <div class="row padding-top" >
                    <div class="col-md-2">
                        <label for="owner_name" class="input-label col-form-label">List Privacy: </label>
                    </div>
                    <div class="col-md-2">
                        <input id="chkbox_privacy" data-on="Private" data-width="100" data-off="Public" data-toggle="toggle" data-onstyle="primary" type="checkbox">
                    </div>
                    <div class="col-md-8"></div>
                </div>
            </div>
        </div>

        <div class="form-group border-pane">
            <div class="row">
                <span class="col-md-2 subheading-label subheading-padding">List</span>
            </div>
            <div class="row subheading-padding">
                <div class="col-md-6">
                    <button id="btn_add_glyph" type="button" class="btn btn-secondary" data-toggle="modal" data-target="#add_modal">
                        <span class="glyphicon glyphicon-plus-sign"></span>
                    </button>
                    <button type="button" class="btn btn-secondary" data-toggle="modal" id = "btn_delete_glyph">
                        <span class="glyphicon glyphicon-minus-sign"></span>
                    </button>
                    <button id = "btn_move_up" type="button" class="btn btn-secondary">
                        <span  class="glyphicon glyphicon-circle-arrow-up"></span>
                    </button>
                    <button  id = "btn_move_down" type="button" class="btn btn-secondary">
                        <span class="glyphicon glyphicon-circle-arrow-down"></span>
                    </button>
                </div>
            </div>


            <div class="row">
                <div class="col-md-12">
                    <table class="table table-hover table-bordered" width="100%" cellspacing="0">
                        <thead>
                        <tr class = "row2 header">
                            <th methodval ="default" id = "header_category" class="disable-header ro disable-select">Category <span id = "category_image" class="glyphicon"></span></th>
                            <th methodval ="default" id = "header_description"  class="disable-header disable-select">Description <span id = "description_image" class="glyphicon"></span></th>
                            <th methodval ="default" id = "header_startdate" class="disable-header disable-select">Start Date <span id = "startdate_image" class="glyphicon"></span></th>
                            <th methodval ="default" id = "header_enddate" class="disable-header disable-select">End Date <span id = "enddate_image" class="glyphicon"></span></th>
                            <th class="disable-header disable-select">Completed</th>
                        </tr>
                        </thead>
                        <tbody id="item_area" class="tbody-default">
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr >
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<!--Modal area-->
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="add_modal" class="modal fade"
     style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">Add Item</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Category</label>
                        <div class="col-lg-9">
                            <input id="txt_category" type="text" placeholder="" name="txt_category"
                                   class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Description</label>
                        <div class="col-lg-9">
                            <input id="txt_description" type="text" placeholder="" name="txt_description"
                                   class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Start Date</label>
                        <div class="col-lg-9">
                            <input id="txt_startdate" type="text" placeholder="" name="txt_start" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">End Date</label>
                        <div class="col-lg-9">
                            <input id="txt_enddate" type="text" placeholder="" name="txt_end" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="chkbox_completed" class="col-lg-3 control-label">Completed</label>
                        <div class="col-lg-3">
                            <input  data-on="Yes" data-off="No" data-toggle="toggle" type="checkbox" value="" id="chkbox_completed" name="chkbox_completed"
                                   class="checkbox-inline">
                        </div>
                        <div class="col-lg-offset-6"></div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-offset-3 col-lg-9">
                            <span access="admin" class="lbl-error" id="err_add_item"></span>
                            <br>
                            <button class="btn btn-primary pull-right" type="button" id="btn_add_submit">Add Item
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="delete_modal" class="modal fade"
     style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">Delete this item</h4>
            </div>
            <div class="modal-body">
                <form action="userpage.jsp" id="form_delete" role="form" class="form-horizontal"
                      style="text-align: center;">
                    <div class="form-group">
                        <label class="col-lg-7 col-lg-offset-2 control-label">Are you sure you want to delete this
                            item?</label>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-offset-2 col-lg-8">
                            <button class="btn btn-primary" type="button" id="btn_delete_confirm"> Yes</button>
                            <button class="btn btn-primary" aria-hidden="true" id = "btn_delete_close" data-dismiss="modal" type="button"> No
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="notification_modal" class="modal fade"
     style="display: none;">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header modal-header-primary">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><span id = "notification_title" class = "notification-font"></span></h4>
            </div>
            <div class="modal-body text-center">
                <p><b><span id="notification_textarea" class = "notification-text"></span></b></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="edit_modal" class="modal fade"
     style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">Edit This Item</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Category</label>
                        <div class="col-lg-9">
                            <input id="edit_category" type="text" placeholder="" name="edit_category"
                                   class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Description</label>
                        <div class="col-lg-9">
                            <input id="edit_description" type="text" placeholder="" name="edit_description"
                                   class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Start Date</label>
                        <div class="col-lg-9">
                            <input id="edit_startdate" type="text" placeholder="" name="edit_start" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">End Date</label>
                        <div class="col-lg-9">
                            <input id="edit_enddate" type="text" placeholder="" name="edit_end" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="chkbox_completed" class="col-lg-3 control-label">Completed</label>
                        <div class="col-lg-3">
                            <input  data-on="Yes" data-off="No" data-toggle="toggle" type="checkbox" type="checkbox" value="" id="edit_completed" name="edit_completed"
                                   class="checkbox-inline">
                        </div>
                        <div class="col-lg-offset-6"></div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-offset-3 col-lg-9">
                            <span id = "edit_id" edit_id = ""></span>
                            <span access="admin" class="lbl-error" id="err_edit_item"></span>
                            <br>
                            <button class="btn btn-primary pull-right" type="button" id="btn_edit_submit">Edit Item
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


</body>
<script src="https://code.jquery.com/jquery-3.1.1.min.js" type="text/javascript"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.3.0/mustache.min.js"></script>
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
<script src="webresources/js/to_do_list_transactions.js"></script>
</html>
