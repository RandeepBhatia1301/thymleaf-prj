(function ( $ ) {

    $.fn.switcher = function(options) {

        var settings = $.extend({
            source:this,
            destination: "#list-container",
            output: "<li>{value}<input type=\"hidden\" class=\"form-control\" name=\"{value}\" id=\"{value}\" ></li>"
        }, options );

        this.switch = function(){
            var val = $(settings.source).val();
            console.log("Before",settings.output);
            settings.output = settings.output.replace("{value}",val);
            console.log("After",settings.output);
            $(settings.destination).append(settings.output);
        }
        
        this.removeSelected = function () {
            $(settings.destination).find(".selected").remove();
        }

        $(settings.destination).children().on("click",function(){
            $(settings.destination).children().removeClass("selected");
            $(this).addClass("selected");
        });

        return this;
    };

}( jQuery ));