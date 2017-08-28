jQuery.extend({
init_news: function(option){

 option = jQuery.extend({
    firstname:"",
	secondname:"",
	thirdname:"",
	fourthname:"",
	playingtitle:"Now Playing:",
	nexttitle:"Next News:",
	prevtitle:"Prev News:",
	newsspeed:6000,
	effectis:0,
	mouseover:true,
	effectspeed:600,
	imagedir:"",
	newscountname:"",
	disablenewscount:false
  }, option);
		
 var firstname=option.firstname;
		var secondname=option.secondname;
		var thirdname=option.thirdname;
		var fourthname=option.fourthname;
		var newsspeed=option.newsspeed;
		var effectis=option.effectis;
		var playingtitle=option.playingtitle;
		var nexttitle=option.nexttitle;
		var prevtitle=option.prevtitle;
		var mouseover=option.mouseover;
		var effectspeed=option.effectspeed;
		var imagedir=option.imagedir;
		var newscountname=option.newscountname;
		var disablenewscount=option.disablenewscount;

		if (newscountname){var news_sp=1;}if (disablenewscount===true){var news_dis=1;}
		effectis=parseInt(effectis,10);
		effectspeed=parseInt(effectspeed,10);
		var myprevimg=jQuery('#news_prev').attr('src'); if (!myprevimg){myprevimg=imagedir+'prev.gif';}
	var mynextimg=jQuery('#news_next').attr('src'); if (!mynextimg){mynextimg=imagedir+'next.gif';}
	var mypauseimg=jQuery('#news_pause').attr('src'); if (!mypauseimg){mypauseimg=imagedir+'pause.gif';}
	var myprevimg0=jQuery('#news_prev0').attr('src'); if (!myprevimg0){myprevimg0=imagedir+'prev0.gif';}
	var mynextimg0=jQuery('#news_next0').attr('src'); if (!mynextimg0){mynextimg0=imagedir+'next0.gif';}
	var mypauseimg0=jQuery('#news_pause0').attr('src'); if (!mypauseimg0){mypauseimg0=imagedir+'pause0.gif';}

	var activechk,activechkmore,mysize,myfirst,myfirst_explain,active,timer,splaynum;
			mysize=jQuery('#'+firstname+' .news_style').size();
			myfirst=jQuery('#'+firstname+' .news_style').eq(0).html();
			myfirst_explain=jQuery('#'+firstname+' .news_style').eq(1).attr('rel');
			active=0;
				jQuery('#'+secondname).append(myfirst);
				if (news_sp===1 && news_dis!=1)
				{jQuery('#'+newscountname).html('1/'+mysize);}
				if (news_sp!=1 && news_dis!=1){jQuery('#'+thirdname).html('&nbsp;&nbsp;'+playingtitle+'1/'+mysize+'&nbsp;&nbsp;<br>');}
				jQuery('#'+thirdname).append(nexttitle+myfirst_explain);

			jQuery('#'+fourthname+' #news_next').click(function(){
					clearTimeout(timer);
					//jQuery(this).attr({src:mynextimg0});
					
					jQuery(this).attr('src',mynextimg0);
					//jQuery('#'+fourthname+' #news_prev').attr({src:myprevimg});
					//jQuery('#'+fourthname+' #news_pause').attr({src:mypauseimg});
					jQuery('#'+fourthname+' #news_prev').attr('src',myprevimg);
					jQuery('#'+fourthname+' #news_pause').attr('src',mypauseimg);
						active=active+1;
				if (active==mysize){active=0;}
					var mynum=active+1;
					var mynow=jQuery('#'+firstname+' .news_style').eq(active).html();
					var nextnum=mynum;
							if (nextnum==mysize){nextnum=0;}
				var mynow_explain=jQuery('#'+firstname+' .news_style').eq(nextnum).attr('rel');
				
				switch (effectis)
				{
					
				case 0:
				jQuery('#'+secondname).fadeOut(effectspeed,function(){
					jQuery('#'+secondname).empty();
					jQuery('#'+secondname).html(mynow);
				if (news_sp===1 && news_dis!=1)
				{jQuery('#'+newscountname).html(mynum+'/'+mysize);jQuery('#'+thirdname).html(nexttitle+mynow_explain);}				
				if (news_sp!=1 && news_dis!=1){jQuery('#'+thirdname).empty().html('&nbsp;&nbsp;'+playingtitle+''+mynum+'/'+mysize+'&nbsp;&nbsp;<br>');jQuery('#'+thirdname).append(nexttitle+mynow_explain);}				
				if (news_dis===1)
				{jQuery('#'+thirdname).html(nexttitle+mynow_explain);}					
				jQuery('#'+secondname).fadeIn(effectspeed);
						
				});
				break;
				case 1:
				jQuery('#'+secondname).slideUp(effectspeed,function(){
					jQuery('#'+secondname).empty();
					jQuery('#'+secondname).html(mynow);
				if (news_sp===1 && news_dis!=1)
				{jQuery('#'+newscountname).html(mynum+'/'+mysize);jQuery('#'+thirdname).html(nexttitle+mynow_explain);}				
				if (news_sp!=1 && news_dis!=1){jQuery('#'+thirdname).empty().html('&nbsp;&nbsp;'+playingtitle+''+mynum+'/'+mysize+'&nbsp;&nbsp;<br>');jQuery('#'+thirdname).append(nexttitle+mynow_explain);}				
				if (news_dis===1)
				{jQuery('#'+thirdname).html(nexttitle+mynow_explain);}
					jQuery('#'+secondname).slideDown(effectspeed);
						
				});
				break;
				case 2:
					jQuery('#'+secondname).animate({width:"0px",opacity: 0.33},effectspeed,function(){
					jQuery('#'+secondname).empty();
					jQuery('#'+secondname).html(mynow);
					jQuery('#'+secondname).animate({width:"100%",opacity: 1},effectspeed,function(){
				if (news_sp===1 && news_dis!=1)
				{jQuery('#'+newscountname).html(mynum+'/'+mysize);jQuery('#'+thirdname).html(nexttitle+mynow_explain);}				
				if (news_sp!=1 && news_dis!=1){jQuery('#'+thirdname).empty().html('&nbsp;&nbsp;'+playingtitle+''+mynum+'/'+mysize+'&nbsp;&nbsp;<br>');jQuery('#'+thirdname).append(nexttitle+mynow_explain);}				
				if (news_dis===1)
				{jQuery('#'+thirdname).html(nexttitle+mynow_explain);}

					});
									
					});
					
					
					break;
					
				case 3:
					jQuery('#'+secondname).html(mynow);
				if (news_sp===1 && news_dis!=1)
				{jQuery('#'+newscountname).html(mynum+'/'+mysize);jQuery('#'+thirdname).html(nexttitle+mynow_explain);}				
				if (news_sp!=1 && news_dis!=1){jQuery('#'+thirdname).empty().html('&nbsp;&nbsp;'+playingtitle+''+mynum+'/'+mysize+'&nbsp;&nbsp;<br>');jQuery('#'+thirdname).append(nexttitle+mynow_explain);}				
				if (news_dis===1)
				{jQuery('#'+thirdname).html(nexttitle+mynow_explain);}					

						
				
				break;
					default:
					jQuery('#'+secondname).fadeOut(effectspeed,function(){
					jQuery('#'+secondname).empty();
					jQuery('#'+secondname).html(mynow);
				if (news_sp===1 && news_dis!=1)
				{jQuery('#'+newscountname).html(mynum+'/'+mysize);jQuery('#'+thirdname).html(nexttitle+mynow_explain);}				
				if (news_sp!=1 && news_dis!=1){jQuery('#'+thirdname).empty().html('&nbsp;&nbsp;'+playingtitle+''+mynum+'/'+mysize+'&nbsp;&nbsp;<br>');jQuery('#'+thirdname).append(nexttitle+mynow_explain);}				
				if (news_dis===1)
				{jQuery('#'+thirdname).html(nexttitle+mynow_explain);}
					jQuery('#'+secondname).fadeIn(effectspeed);
						
				});
				break;
				}
					
					timer=setTimeout(autonext,newsspeed,active);
		});
					jQuery('#'+fourthname+' #news_prev').click(function(){
						clearTimeout(timer);
							jQuery(this).attr({src:myprevimg0});
						jQuery('#'+fourthname+' #news_next').attr({src:mynextimg});
						jQuery('#'+fourthname+' #news_pause').attr({src:mypauseimg});
							active=active-1;
					if (active<0){active=mysize-1;}
						var mynum=active+1;
						var myprevnum=mynum-2;
					if (myprevnum<0){myprevnum=mysize-1;}
							var mynow=jQuery('#'+firstname+' .news_style').eq(active).html();
							var mynow_explain=jQuery('#'+firstname+' .news_style').eq(myprevnum).attr('rel');
							switch (effectis)
				{
						case 0:
								jQuery('#'+secondname).fadeOut(effectspeed,function(){
						jQuery('#'+secondname).empty();
						jQuery('#'+secondname).html(mynow);
				if (news_sp===1 && news_dis!=1)
				{jQuery('#'+newscountname).html(mynum+'/'+mysize);jQuery('#'+thirdname).html(prevtitle+mynow_explain);}				
				if (news_sp!=1 && news_dis!=1){jQuery('#'+thirdname).empty().html('&nbsp;&nbsp;'+playingtitle+''+mynum+'/'+mysize+'&nbsp;&nbsp;<br>');jQuery('#'+thirdname).append(prevtitle+mynow_explain);}				
				if (news_dis===1)
				{jQuery('#'+thirdname).html(prevtitle+mynow_explain);}
						jQuery('#'+secondname).fadeIn(effectspeed);
						});
							break;
							case 1:
								jQuery('#'+secondname).slideUp(effectspeed,function(){
						jQuery('#'+secondname).empty();
						jQuery('#'+secondname).html(mynow);
				if (news_sp===1 && news_dis!=1)
				{jQuery('#'+newscountname).html(mynum+'/'+mysize);jQuery('#'+thirdname).html(prevtitle+mynow_explain);}				
				if (news_sp!=1 && news_dis!=1){jQuery('#'+thirdname).empty().html('&nbsp;&nbsp;'+playingtitle+''+mynum+'/'+mysize+'&nbsp;&nbsp;<br>');jQuery('#'+thirdname).append(prevtitle+mynow_explain);}				
				if (news_dis===1)
				{jQuery('#'+thirdname).html(prevtitle+mynow_explain);}	
						jQuery('#'+secondname).slideDown(effectspeed);
						});
							break;
							case 2:
								jQuery('#'+secondname).animate({width:"0px",opacity: 0.33},effectspeed,function(){
					jQuery('#'+secondname).empty();
					jQuery('#'+secondname).html(mynow);
					jQuery('#'+secondname).animate({width:"100%",opacity: 1},effectspeed,function(){
				if (news_sp===1 && news_dis!=1)
				{jQuery('#'+newscountname).html(mynum+'/'+mysize);jQuery('#'+thirdname).html(prevtitle+mynow_explain);}				
				if (news_sp!=1 && news_dis!=1){jQuery('#'+thirdname).empty().html('&nbsp;&nbsp;'+playingtitle+''+mynum+'/'+mysize+'&nbsp;&nbsp;<br>');jQuery('#'+thirdname).append(prevtitle+mynow_explain);}				
				if (news_dis===1)
				{jQuery('#'+thirdname).html(prevtitle+mynow_explain);}		


					});
									
					});
							break;
						case 3:
								
						jQuery('#'+secondname).html(mynow);
						if (news_sp===1 && news_dis!=1)
				{jQuery('#'+newscountname).html(mynum+'/'+mysize);jQuery('#'+thirdname).html(prevtitle+mynow_explain);}				
				if (news_sp!=1 && news_dis!=1){jQuery('#'+thirdname).empty().html('&nbsp;&nbsp;'+playingtitle+''+mynum+'/'+mysize+'&nbsp;&nbsp;<br>');jQuery('#'+thirdname).append(prevtitle+mynow_explain);}				
				if (news_dis===1)
				{jQuery('#'+thirdname).html(prevtitle+mynow_explain);}	
						
							break;
				
							default:
						jQuery('#'+secondname).fadeOut(effectspeed,function(){
						jQuery('#'+secondname).empty();
						jQuery('#'+secondname).html(mynow);

				if (news_sp===1 && news_dis!=1)
				{jQuery('#'+newscountname).html(mynum+'/'+mysize);jQuery('#'+thirdname).html(nexttitle+mynow_explain);}				
				if (news_sp!=1 && news_dis!=1){jQuery('#'+thirdname).empty().html('&nbsp;&nbsp;'+playingtitle+''+mynum+'/'+mysize+'&nbsp;&nbsp;<br>');jQuery('#'+thirdname).append(prevtitle+mynow_explain);}				
				if (news_dis===1)
				{jQuery('#'+thirdname).html(prevtitle+mynow_explain);}		
						jQuery('#'+secondname).fadeIn(effectspeed);
						});
							break;
							}

					
							timer=setTimeout(autoprev,newsspeed,active);
					});

					jQuery('#'+fourthname+' #news_pause').click(function(){
							jQuery(this).attr({src:mypauseimg0});
						jQuery('#'+fourthname+' #news_next').attr({src:mynextimg});
						jQuery('#'+fourthname+' #news_prev').attr({src:myprevimg});
							clearTimeout(timer);
						});
					//add by request
					if (mouseover===true)
					{					
					jQuery('#'+secondname).hover(function(){
						clearTimeout(timer);
						activechk=jQuery('#'+fourthname+' #news_next').attr('src');
						activechkmore=jQuery('#'+fourthname+' #news_prev').attr('src');	
						jQuery('#'+fourthname+' #news_pause').attr({src:mypauseimg0});
						jQuery('#'+fourthname+' #news_next').attr({src:mynextimg});
						jQuery('#'+fourthname+' #news_prev').attr({src:myprevimg});
							},function(){
						jQuery('#'+fourthname+' #news_pause').attr({src:mypauseimg});
						if (activechk==mynextimg && activechkmore==myprevimg){
						timer=setTimeout(autonext,100,active);
							}
						if (activechk==mynextimg0){timer=setTimeout(autonext,100,active);}
						if (activechk==mynextimg && activechkmore==myprevimg0){timer=setTimeout(autoprev,100,active);}
					});
					}
					//end
					var _st = window.setTimeout; 
						window.setTimeout = function(fRef, mDelay) { 
							if(typeof fRef == 'function'){ 
								var argu = Array.prototype.slice.call(arguments,2); 
								var f = (function(){ fRef.apply(null, argu); }); 
								return _st(f, mDelay); 
							} 
						 return _st(fRef,mDelay); 
						}; 

					function autonext(q){
						if (!q){q=0;}
							myend=jQuery('#'+firstname+' .news_hide_style').size();
							myend=myend-1;
							if (q >= myend){q=0;}
								jQuery('#'+fourthname+' #news_next').eq(q).click();
								q=q+1;					
					}
					function autoprev(q){
						if (!q){q=0;}
							myend=jQuery(".news_hide_style").size();
							myend=myend-1;
							if (q >= myend){q=0;}
								jQuery('#'+fourthname+' #news_prev').eq(q).click();
								q=q+1;					
					}
					timer=setTimeout(autonext,newsspeed,1);


}
});