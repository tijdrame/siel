/*
* vertical news ticker
* Tadas Juozapaitis ( kasp3rito@gmail.com )
* http://plugins.jquery.com/project/vTicker
*/



(function($){
	
	
$.fn.vTickerProgisap = function(options) {
	var defaults = {
		speed: 700,
		pause: 4000,
		showItems: 45,
		animation: '',
		mousePause: true,
		isPaused: false,
		direction: 'up',
		height: 0
	};

	var options = $.extend(defaults, options);
	
	
	$.fn.extend({
		stopvTickerProgisap: function() { 
			options.isPaused = true;
			$(this).unbind("mouseenter").unbind("mouseleave");
		},
		startvTickerProgisap: function() { 
			options.isPaused = false;
			$(this).bind("mouseenter",function(){
				options.isPaused = true;
			}).bind("mouseleave",function(){
				options.isPaused = false;
			});
			
			
			
		
			
		}
	});

	moveUpProgisap = function(obj2, height, options){
		if(options.isPaused)
			return;
		
		var obj = obj2.children('ul');
		
    	var clone =  obj.children('li:first').clone(true);
		
		
		if(options.height > 0)
		{
			height = obj.children('li:first').height();
		}		
		
    	obj.animate({top: '-=' + height + 'px'}, options.speed, function() {
        	$(this).children('li:first').remove();
        	$(this).css('top', '0px');
        });
		
		if(options.animation == 'fade')
		{
			obj.children('li:first').fadeOut(options.speed);
			if(options.height == 0)
			{
			obj.children('li:eq(' + options.showItems + ')').hide().fadeIn(options.speed);
			}
		}

    	clone.appendTo(obj);
	};
	
	moveDown = function(obj2, height, options){
		if(options.isPaused)
			return;
		
		var obj = obj2.children('ul');
		
    	var clone = obj.children('li:last').clone(true);
		
		
		if(options.height > 0)
		{
			height = obj.children('li:first').height();
		}
		
		obj.css('top', '-' + height + 'px')
			.prepend(clone);
			
    	obj.animate({top: 0}, options.speed, function() {
        	$(this).children('li:last').remove();
        });
		
		if(options.animation == 'fade')
		{
			if(options.height == 0)
			{
				obj.children('li:eq(' + options.showItems + ')').fadeOut(options.speed);
			}
			obj.children('li:first').hide().fadeIn(options.speed);
		}
	};
	
	return this.each(function() {
		var obj = $(this);
		var maxHeight = 0;
		
		//alert(jQuery.fn.jquery);
		//alert('début');
		

		obj.css({overflow: 'hidden', position: 'relative'})
			.children('ul').css({position: 'absolute', margin: 0, padding: 0})
			.children('li').css({margin: 0, padding: 0});

		if(options.height == 0)
		{
			obj.children('ul').children('li').each(function(){
				if($(this).height() > maxHeight)
				{
					maxHeight = $(this).height();
				}
			});

			obj.children('ul').children('li').each(function(){
				$(this).height(maxHeight);
			});

			obj.height(maxHeight * options.showItems);
		}
		else
		{
			
			obj.height(options.height);
			jQuery("#mainTable").css("height", obj.height() + 165);
			//alert(obj.height());
		}
		
    	var interval = setInterval(function(){ 
			if(options.direction == 'up')
			{ 
				moveUpProgisap(obj, maxHeight, options); 
			}
			else
			{ 
				moveDown(obj, maxHeight, options); 
			} 
		}, options.pause);
		
		if(options.mousePause)
		{
			obj.bind("mouseenter",function(){
				options.isPaused = true;
			}).bind("mouseleave",function(){
				options.isPaused = false;
			});
		}
		
		var lastPauseStatus = false;
		
		$(window).blur(function() {
			lastPauseStatus = options.isPaused;
			options.isPaused = true;
	    }).focus(function(){
	    	options.isPaused = lastPauseStatus;
	    });
		
	});
};





})(jQuery);