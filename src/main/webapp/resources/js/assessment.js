//****Global Vars ******************************

var $body = $('body');
var $btnSaveResponse =  $('#btnSaveResponse');
var $btnSkipResponse =  $('#btnSkipResponse');
var $btnGrpLoadTask =  $('.btn-task-index');

var $itemContentPane =  $('#dvItemContentPane');
var $itemDetailsPane =  $('#dvItemDetailsPane');

var $errorPane =  $('#dvErrorPane');
var $progressPane = $('#dvProgressPane');
var $processForm  = $("#processForm");
var $inpTaskId = $("#inpTaskId");
var $inpResponseId = $("#inpResponseId");
var $taskIndexPane = $("#dvTaskIndexPane");

var taskCount = 0;
var taskIndex = 0;


var $btnCurrentTask = $('#btnTaskIndex0');
var $btnPrevTask = $('#btnTaskIndex0');

var classBtnActiveIndex = 'btn-task-index-current';
var classInputResponse = 'inp-h-item-response';

//**********************************************
$(document).ready(function()
{
	//* *********************************************
	$processForm.on('submit', function(e)
	{
	    onLoadTaskStart();

		var postData = $(this).serializeArray();
		var formURL = $(this).attr("action")+'?taskIndex='+taskIndex;

		$.ajax(
	    {
	        url : formURL,
	        type: "POST",
	        data : postData,
	        success:function(data, textStatus, jqXHR)
	        {
	        	onLoadTaskSuccess(data);
	        },
	        error: function(jqXHR, textStatus, errorThrown)
	        {
	            onLoadTaskError(errorThrown);
	        }
	    });

	    e.preventDefault();

	    return false;
	});
	// ----------------------------

});


//**********************************************
$btnSaveResponse.on('click',function()
{
    taskIndex++;
    $processForm.submit();
});

//**********************************************/
$btnSkipResponse.on('click',function()
{
	loadTask(++taskIndex)
});


//**********************************************
$btnGrpLoadTask.on('click',function()
{
    taskIndex = parseInt($(this).data("index"));
	  loadTask();
});


//**********************************************/
function loadTask()
{
	onLoadTaskStart();

	var formURL = 'rest/assessment/process/task?taskIndex='+taskIndex;

    $.ajax(
    {
        url : formURL,
        type: "GET",
        success:function(data, textStatus, jqXHR)
        {
            onLoadTaskSuccess(data);
        },
        error: function(jqXHR, textStatus, errorThrown)
        {
            onLoadTaskError(jqXHR, textStatus, errorThrown);
        }
    });
}


//**********************************************/
function onLoadTaskStart()
{
    if(taskIndex >= taskCount)
    {
        taskIndex = 0;
    }

    $btnCurrentTask.removeClass(classBtnActiveIndex);
    $taskIndexPane.html('...');
	$progressPane.show();
	$btnSaveResponse.prop('disabled', true);
	$btnSkipResponse.prop('disabled', true);
}

//**********************************************/
function onLoadTaskSuccess(data)
{
    displayTask(data);

    $btnCurrentTask = $('#btnTaskIndex'+taskIndex);
    $btnCurrentTask.addClass(classBtnActiveIndex);

    $btnSaveResponse.prop('disabled', false);
	$btnSkipResponse.prop('disabled', false);

	$taskIndexPane.html(taskIndex+1);
	$progressPane.hide();
}

//**********************************************/
function onLoadTaskError(jqXHR, textStatus, errorThrown)
{
    $btnSaveResponse.prop('disabled', false);
    $btnSkipResponse.prop('disabled', false);

	$progressPane.hide();

	$errorPane.html(jqXHR.status);
}

//**********************************************/
function displayTask(processResponse)
{
    var inp = "";
    var selected = "";
    var task = processResponse.task;

    if(processResponse.prevResponseStatus == 2)
        $btnCurrentTask.addClass("btn-success");
    else if(processResponse.prevResponseStatus == 3)
        $btnCurrentTask.addClass("btn-primary");


    $itemContentPane.html(task.itemContent);
    $itemDetailsPane.html('');

    //-----------------------------
    setProcessResponseParams(processResponse);
    //-----------------------------
    

    $.each(task.details, function(key,taskDetail)
    {
        //-----------------------------------
        $.each(processResponse.details, function(selIndex,responseDetail)
        {
            if(responseDetail.taskDetailId == taskDetail.id )
            {
                if(task.modeType == 1 || task.modeType == 2)
                {   
                    selected = "checked=checked";   
                    inp += '<input type="hidden" name="details['+selIndex+'].id" value="'+responseDetail.id+'" />';
                }
                else
                {
                    selected = responseDetail.itemResponse;   
                }
            } 
        });
        //-----------------------------------

        if(task.modeType == 1) // Single Choice
    	{
    	    inp += '<div class="input-item-detail"><div class="radio"><label>'+
    		       '<input type="radio" '+selected+' name="details[0].taskDetail.id"' +
    		       'value="'+taskDetail.id+'"/>'+
    		       '&#'+(key + 65)+') '+ taskDetail.itemDetail+'</label></div></div>';
    	}
    	else if(task.modeType == 2) // Multi-Choice
    	{
            inp += '<div class="input-item-detail"><div class="checkbox"><label>'+
                   '<input type="checkbox" '+selected+' name="details['+ key +'].taskDetail.id" value="'+taskDetail.id+'"/>'+
                   '&#'+(key + 65)+') '+ taskDetail.itemDetail+'</label></div></div>';
    	}
    	else if(task.modeType == 3) // Short Text
        {
    	    inp += '<input type="hidden" name="details['+key+'].taskDetail.id" value="'+taskDetail.id+'" />';
    	    inp += '<div class="input-item-detail"><div class="text">' +
                   '<label>&#'+(key + 65)+ ') </label>'+
                   '<input size="35" type="text" name="details['+ key +'].itemResponse" value="'+selected+'"/>'+
                   '</div></div>';
        }
    	else if(task.modeType == 4) // Esse
        {
    	    inp += '<input type="hidden" name="details['+key+'].taskDetail.id" value="'+taskDetail.id+'" />';
    	    inp += '<div class="col-md-12 col-sm-12 col-xs-12">'+
                   '<textarea rows="14" name="" class="resizable_textarea form-control">'+
                   selected+'</textarea></div>';

            return false;
        }
        
        //------------------
        selected = '';
        //-----------------
    });

    $itemDetailsPane.html(inp);

}

//* *********************************************
function setProcessResponseParams(processResponse)
{
    $inpTaskId.val(processResponse.task.id);
    $inpResponseId.val(processResponse.id);
}


//* *********************************************
function setResponseDetailParams(responseDetail)
{
    if(responseDetail != null)
    {
        $('<input>').addClass(classInputResponse).attr('type','hidden').attr('name','foo[]').attr('value','bar').appendTo('form');
    }
}
