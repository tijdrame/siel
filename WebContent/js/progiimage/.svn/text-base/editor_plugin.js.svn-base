/**
 * $Id: editor_plugin.js,v 1.1 2012-10-15 23:02:18 tariq Exp $
 *
 * @author Moxiecode
 * @copyright Copyright © 2004-2008, Moxiecode Systems AB, All rights reserved.
 */

(function() {
	try
	{
		/*
		var u,s=tinymce.EditorManager.settings;
		alert(u);
		u=this.urls['progiImage']+'/langs/'+s.language+'.js';
		alert(u);
		tinymce.PluginManager.requireLangPack('progiImage');*/
		tinymce.PluginManager.requireLangPack('progiImage');
	} catch(e)
	{alert('fdf');}
	
	tinymce.create('tinymce.plugins.ProgiImagePlugin', {
		init : function(ed, url) {
			// Register commands
			ed.addCommand('mceProgiImage', function() {
				// Internal image object like a flash placeholder
				if (ed.dom.getAttrib(ed.selection.getNode(), 'class').indexOf('mceItem') != -1)
					return;
				ed.windowManager.open({
					file : url + '/imagez.seam',
					width : 480 + parseInt(ed.getLang('progiImage.delta_width', 0)),
					height : 385 + parseInt(ed.getLang('progiImage.delta_height', 0)),
					inline : 1
				}, {
					plugin_url : url
				});
			});

			// Register buttons
			ed.addButton('image', {
				title : 'progiImage.image_desc',
				cmd : 'mceProgiImage'
			});
		},

		getInfo : function() {
			return {
				longname : 'Progi Advanced image',
				author : 'Moxiecode Systems AB',
				authorurl : 'http://tinymce.moxiecode.com',
				infourl : 'http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/advimage',
				version : tinymce.majorVersion + "." + tinymce.minorVersion
			};
		}
	});

	// Register plugin
	tinymce.PluginManager.add('progiImage', tinymce.plugins.ProgiImagePlugin);
})();