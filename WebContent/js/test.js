 $(function() {
	 
            $("#yben, #2, #3").lavaLamp({
                fx: "backout", 
                speed: 700,
                click: function(event, menuItem) {
            	alert('fdsf');
                    return false;
                }
            });
        });