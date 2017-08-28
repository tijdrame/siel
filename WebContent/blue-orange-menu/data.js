/*
   Deluxe Menu Data File
   Created by Deluxe Tuner v3.10
   http://deluxe-menu.com
*/



//--- Common
var menuIdentifier="blue-orange-menu";
var isHorizontal=1;
var smColumns=1;
var smOrientation=0;
var dmRTL=0;
var pressedItem=-2;
var itemCursor="pointer";
var itemTarget="_self";
var statusString="link";
var blankImage="blue-orange-menu.files/blank.gif";
var pathPrefix_img="";
var pathPrefix_link="";

//--- Dimensions
var menuWidth="545px";
var menuHeight="";
var smWidth="";
var smHeight="";

//--- Positioning
var absolutePos=0;
var posX="0px";
var posY="0px";
var topDX=0;
var topDY=0;
var DX=0;
var DY=0;
var subMenuAlign="left";
var subMenuVAlign="top";

//--- Font
var fontStyle=["normal 11px Trebuchet MS,Arial,san-serif","normal 11px Trebuchet MS,Arial,san-serif"];
var fontColor=["#043454","#FFFFFF"];
var fontDecoration=["none","none"];
var fontColorDisabled="#AAAAAA";

//--- Appearance
var menuBackColor="transparent";
var menuBackImage="";
var menuBackRepeat="repeat";
var menuBorderColor="#C0AF62";
var menuBorderWidth="0px";
var menuBorderStyle="solid";
var smFrameImage="blue-orange-menu.files/sub.png";
var smFrameWidth=10;

//--- Item Appearance
var itemBackColor=["transparent","transparent"];
var itemBackImage=["blue-orange-menu.files/blank.gif","blue-orange-menu.files/hlight.png"];
var itemSlideBack=6;
var beforeItemImage=["",""];
var afterItemImage=["",""];
var beforeItemImageW="";
var afterItemImageW="";
var beforeItemImageH="";
var afterItemImageH="";
var itemBorderWidth="0px";
var itemBorderColor=["#FCEEB0","#4C99AB"];
var itemBorderStyle=["none","none"];
var itemSpacing=0;
var itemPadding="0px 13px 0px 3px";
var itemAlignTop="center";
var itemAlign="left";

//--- Icons
var iconTopWidth="";
var iconTopHeight="";
var iconWidth="";
var iconHeight="";
var arrowWidth="";
var arrowHeight="";
var arrowImageMain=["",""];
var arrowWidthSub="";
var arrowHeightSub="";
var arrowImageSub=["",""];

//--- Separators
var separatorImage="";
var separatorColor="";
var separatorWidth="100%";
var separatorHeight="0px";
var separatorAlignment="left";
var separatorVImage="";
var separatorVColor="";
var separatorVWidth="0px";
var separatorVHeight="100%";
var separatorPadding="0px";

//--- Floatable Menu
var floatable=0;
var floatIterations=6;
var floatableX=1;
var floatableY=1;
var floatableDX=15;
var floatableDY=15;

//--- Movable Menu
var movable=0;
var moveWidth=12;
var moveHeight=20;
var moveColor="#DECA9A";
var moveImage="";
var moveCursor="move";
var smMovable=0;
var closeBtnW=15;
var closeBtnH=15;
var closeBtn="";

//--- Transitional Effects & Filters
var transparency="100";
var transition=102;
var transOptions="";
var transDuration=350;
var transDuration2=200;
var shadowLen=0;
var shadowColor="#B1B1B1";
var shadowTop=0;

//--- CSS Support (CSS-based Menu)
var cssStyle=0;
var cssSubmenu="";
var cssItem=["",""];
var cssItemText=["",""];

//--- Advanced
var dmObjectsCheck=0;
var saveNavigationPath=1;
var showByClick=0;
var noWrap=1;
var smShowPause=200;
var smHidePause=1000;
var smSmartScroll=1;
var topSmartScroll=0;
var smHideOnClick=1;
var dm_writeAll=1;
var useIFRAME=0;
var dmSearch=0;

//--- AJAX-like Technology
var dmAJAX=0;
var dmAJAXCount=0;
var ajaxReload=0;

//--- Dynamic Menu
var dynamic=0;

//--- Popup Menu
var popupMode=0;

//--- Keystrokes Support
var keystrokes=0;
var dm_focus=1;
var dm_actKey=113;

//--- Sound
var onOverSnd="";
var onClickSnd="";

var itemStyles = [
    ["itemHeight=46px","itemBackColor=transparent,transparent","itemBackImage=blue-orange-menu.files/back-left-normal.png,blue-orange-menu.files/back-left-over.png","itemSlideBack=10","itemBorderWidth=0px","itemBorderStyle=none,none","fontStyle='bold 11px Trebuchet MS,Arial,san-serif','bold 11px Trebuchet MS,Arial,san-serif'","fontColor=#FFFFFF,#043454","fontDecoration=none,none"],
    ["itemHeight=46px","itemBackColor=transparent,transparent","itemBackImage=blue-orange-menu.files/back-right-normal.png,blue-orange-menu.files/back-right-over.png","itemSlideBack=10","itemBorderWidth=0px","itemBorderStyle=none,none","fontStyle='bold 11px Trebuchet MS,Arial,san-serif','bold 11px Trebuchet MS,Arial,san-serif'","fontColor=#FFFFFF,#043454","fontDecoration=none,none"],
    ["itemHeight=46px","itemBackColor=transparent,transparent","itemBackImage=blue-orange-menu.files/back-center-normal.png,blue-orange-menu.files/back-center-over.png","itemSlideBack=10","itemBorderWidth=0px","itemBorderStyle=none,none","fontStyle='bold 11px Trebuchet MS,Arial,san-serif','bold 11px Trebuchet MS,Arial,san-serif'","fontColor=#FFFFFF,#043454","fontDecoration=none,none"],
];
var menuStyles = [
    ["menuBackColor=transparent","menuBorderWidth=0px","menuBorderStyle=none","itemSpacing=2","itemPadding=4px 10px 4px 0px"],
];
dm_init();