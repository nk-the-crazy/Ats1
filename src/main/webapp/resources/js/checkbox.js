    $("#cxSelectAll").change(function()
    {   
        $(".cxinput").prop('checked', $(this).prop("checked")); 
    });

    $(document).on('change','.cxinput',function()
    { 
    	if(false == $(this).prop("checked"))
        { 
        	$("#cxSelectAll").prop('checked', false); 
        }
        if ($('.cxinput:checked').length == $('.checkbox').length )
        {
            $("#cxSelectAll").prop('checked', true);
        }
    });
    
