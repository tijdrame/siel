

(function() {
	
	// Load plugin specific language pack
	tinymce.PluginManager.requireLangPack('selectPastePattern');
	tinymce.create('tinymce.plugins.SelectPastePatternPlugin', {

		init : function(ed, url) {
			// Register the command so that it can be invoked by using tinyMCE.activeEditor.execCommand('mceExample');
			ed.addCommand('mceSelectPastePattern', function() {
				ed.windowManager.open({
					file : url + '/dialog.seam',
					width : 320 + parseInt(ed.getLang('selectPastePattern.delta_width', 0)),
					height : 200 + parseInt(ed.getLang('selectPastePattern.delta_height', 0)),
					inline : 1
				}, {
					plugin_url : url, 
					some_custom_arg : 'custom arg fdfd' 

				});
			});

			// Register selectPastePattern button
			ed.addButton('selectPastePattern', {
				title : 'selectPastePattern.desc',
				cmd : 'mceSelectPastePattern',
				image : url + '/img/selectPastePattern.gif'
			});

			// Add a node change handler, selects the button in the UI when a image is selected
			ed.onNodeChange.add(function(ed, cm, n) {
				cm.setActive('selectPastePattern', n.nodeName == 'IMG');
			});
		},


		createControl : function(n, cm) {
			return null;
		},


		getInfo : function() {
			return {
				longname : 'SelectPastePattern plugin',
				author : 'Some author',
				authorurl : 'http://tinymce.moxiecode.com',
				infourl : 'http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/selectPastePattern',
				version : "1.0"
			};
		}
	});


	tinymce.PluginManager.add('selectPastePattern', tinymce.plugins.SelectPastePatternPlugin);
})();