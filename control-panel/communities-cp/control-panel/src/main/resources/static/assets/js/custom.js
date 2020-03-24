$( document ).ready(function() {
	//iCheck for checkbox and radio inputs
    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
      checkboxClass: 'icheckbox_minimal-blue',
      radioClass   : 'iradio_minimal-blue'
    })

    $(".modal").on("show.bs.modal",function(){
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass   : 'iradio_minimal-blue'
        });
	});

	//Nice Select Option 
	$('.select-items').niceSelect();
	
	//Nice scroller bar
	if(("#accordion .panel-body").length > 0 ) {
		$(window).on("load", function () {
			$("#accordion .panel-body").mCustomScrollbar({
				setHeight: 500,
				theme: "dark-3"
			});
			0.
		});
    }
	//home dashboard hover box
	if($('ul.da-thumbs > li').length > 0){
        jQuery(function() {
            jQuery('ul.da-thumbs > li').hoverdir();
        });
	}

	    //equal height Function

		equalheight = function(container){

		var currentTallest = 0,
			 currentRowStart = 0,
			 rowDivs = new Array(),
			 $el,
			 topPosition = 0;
		 $(container).each(function() {

		   $el = $(this);
		   $($el).height('auto')
		   topPostion = $el.position().top;

		   if (currentRowStart != topPostion) {
			 for (currentDiv = 0 ; currentDiv < rowDivs.length ; currentDiv++) {
			   rowDivs[currentDiv].height(currentTallest);
			 }
			 rowDivs.length = 0; // empty the array
			 currentRowStart = topPostion;
			 currentTallest = $el.height();
			 rowDivs.push($el);
		   } else {
			 rowDivs.push($el);
			 currentTallest = (currentTallest < $el.height()) ? ($el.height()) : (currentTallest);
		  }
		   for (currentDiv = 0 ; currentDiv < rowDivs.length ; currentDiv++) {
			 rowDivs[currentDiv].height(currentTallest);
		   }
		 });
		}

		$(window).on("load", function() {
		  	equalheight('.section-contents .caption');
		});


		$(window).resize(function(){
		  	equalheight('.section-contents .caption');
		});


		$("input[type='file']").on("change",function(e){
            $("#orgImgName").attr("placeholder",e.target.files[0].name);
            $("#orgImgName").val(e.target.files[0].name);
            readURL(this);
		});
});

function readURL(input) {

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function(e) {
            $('#preview-image').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
		