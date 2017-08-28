
(function($) {
	$.fn.toast=function(options){
		var opts = $.extend($.fn.toast.defaults, options);
		
		return this.each(function(){
			$this = jQuery(this);
			$this.css('position','absolute')
				.css('bottom', opts.bottom)
				.css('right', opts.right)
				.css('height',opts.height)
				.hide();
			
			$triggers=opts.triggers;
			
			$triggers.each(function(){
				$(this).click(function(){ $this.trigger("show");});
			});
			
			$this.bind("show",function(){
				$this.slideToggle().delay(opts.delay).slideToggle();
			});
		});
	};	
	
	$.fn.showToast=function(){
		return this.trigger("show");
	};
	
	$.fn.toast.defaults={
		bottom:"0",
		right:"30px",
		triggers:[],
		delay: 3000,
		height: "30px"
	};
})(jQuery);