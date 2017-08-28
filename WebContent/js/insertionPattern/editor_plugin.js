(function() {


tinymce.create('tinymce.plugins.Example2Plugin', {
     	    createControl: function(n, cm) {
     	        switch (n) {
     	            case 'mylistbox':
     	                var mlb = cm.createListBox('mylistbox', {
     	                     title : 'Champs personalises',
     	                   	 style : 'width:500px',
     	                     width : '500px',
     	                     onselect : function(v) {
     	                        // tinymce.activeEditor.windowManager.alert('Value selected:' + v);
     	                        tinyMCE.execInstanceCommand(tinyMCE.activeEditor.id,"mceInsertContent",false,v);
     	                     }
     	                });

     	                // Add some values to the list box
     	                mlb.add('Agence adresse', '@INTER_NOM');
     	                mlb.add('Agence RCS', '@INTER_NOM');
     	                mlb.add('Agence gerant', '@INTER_NOM');
     	                mlb.add('Agence num agrement', '@INTER_NOM');
     	                mlb.add('Agence naf', '@INTER_NOM');
     	               	mlb.add('Agence capital', '@INTER_NOM');
     	            	mlb.add('Nom Intervenant', '@INTER_NOM');
     	                mlb.add('Numero Secu Intervenant', '@INTER_SECU');
     	                mlb.add('Adresse Intervenant', '@INTER_ADRESSE_COMPLETE');
     	                mlb.add('Nom Beneficiaire', '@INTER_NOM');
    	                mlb.add('Numero Secu Beneficiaire', '@INTER_SECU');
    	                mlb.add('Adresse Beneficiaire', '@INTER_ADRESSE_COMPLETE');

     	                // Return the new listbox instance
     	                return mlb;
     	        }
     	        return null;
     	    }
     	});

     	// Register plugin with a short name
     	tinymce.PluginManager.add('insertionPattern', tinymce.plugins.Example2Plugin);
     	
})();