/** 
 * PrimeFaces Omega Layout
 */
PrimeFaces.widget.Omega = PrimeFaces.widget.BaseWidget.extend({
    
    init: function(cfg) {
        this._super(cfg);
        this.wrapper = $(document.body).children('.wrapper');
        this.sidebar = this.wrapper.children('.sidebar');
        this.menu = this.sidebar.find('div.menu');
        this.menulinks = this.menu.find('a');
        this.menuButton = $('#omega-menu-button');
        this.optionsMenuButton = $('#options-menu-button');
        this.profileButton = $('#profile-button');
        this.topbarIcons = $('#topbar-icons');
        this.expandedMenuitems = this.expandedMenuitems||[];

        $('.nano').nanoScroller({flash: true});
        this.bindEvents();        
        this.restoreMenuState();
    },
    
    bindEvents: function() {
        var $this = this;
        
        this.menulinks.off('click').on('click', function(e) {
            var link = $(this),
            item = link.parent(),
            submenu = item.children('ul');
            
            if(item.hasClass('active-menuitem')) {
                if(submenu.length) {
                    $this.removeMenuitem(item.attr('id'));
                    submenu.slideUp(function() {
                        item.removeClass('active-menuitem');
                    });
                }
            }
            else {
                $this.deactivateItems(item.siblings());
                $this.activate(item);
                $this.addMenuitem(item.attr('id'));
            }
            
            setTimeout(function() {
                $(".nano").nanoScroller();
            }, 750);
            
            if(submenu.length) {
                e.preventDefault();
            }
        });
                
        this.menuButton.off('click').on('click', function(e) {
            $(this).toggleClass('active');
            
            if($this.isDesktop()) {
                $this.wrapper.toggleClass('sidebar-inactive-l');
                
                if($this.wrapper.hasClass('sidebar-inactive-l')) {
                    $this.wrapper.removeClass('sidebar-active-m');
                }
            }
            else {
                $this.wrapper.toggleClass('sidebar-active-m');
                
                if($this.wrapper.hasClass('sidebar-active-m')) {
                    $this.wrapper.removeClass('sidebar-inactive-l');
                }
            }
            
            $this.topbarIcons.removeClass('topbar-icons-visible');
            e.preventDefault();
        });
        
        this.profileButton.off('click').on('click', function(e) {
            var profileMenu = $(this).next('ul');
            if(profileMenu.is(':visible')) {
                profileMenu.slideUp();
                $this.clearProfileMenuState();
            }
            else {
                profileMenu.slideDown();
                $this.saveProfileMenuState();
            }
            
            e.preventDefault();
        });
        
        this.optionsMenuButton.off('click').on('click', function(e) {
            if(!$this.animatingOptionsMenu) {
                $this.animatingOptionsMenu = true;
                if($this.topbarIcons.hasClass('topbar-icons-visible')) {
                    $this.topbarIcons.addClass('flipOutX');
                    setTimeout(function() {
                        $this.topbarIcons.removeClass('topbar-icons-visible flipOutX');
                        $this.animatingOptionsMenu = false;
                    },450);
                } else {
                    $this.topbarIcons.addClass('topbar-icons-visible');
                    $this.topbarIcons.addClass('flipInX');
                    $this.animatingOptionsMenu = false;
                }
            }

            $this.wrapper.removeClass('sidebar-active-m sidebar-inactive-l');
            e.preventDefault();
        });
    },
    
    activate: function(item) {
        var submenu = item.children('ul');
        item.addClass('active-menuitem');
        
        if(submenu.length) {
            submenu.slideDown();
        }
    },
    
    deactivate: function(item) {
        var submenu = item.children('ul');
        item.removeClass('active-menuitem');
        
        if(submenu.length) {
            submenu.hide();
        }
    },
        
    deactivateItems: function(items, animate) {
        var $this = this;
        
        for(var i = 0; i < items.length; i++) {
            var item = items.eq(i),
            submenu = item.children('ul');
            
            if(submenu.length) {
                if(item.hasClass('active-menuitem')) {
                    var activeSubItems = item.find('.active-menuitem');
                    submenu.slideUp('normal', function() {
                        var currentItem = $(this).parent();
                        currentItem.removeClass('active-menuitem');
                        currentItem.find('.active-menuitem').each(function() {
                            $this.deactivate($(this));
                        });
                    });
                    
                    $this.removeMenuitem(item.attr('id'));
                    activeSubItems.each(function() {
                        $this.removeMenuitem($(this).attr('id'));
                    });
                }
                else {
                    item.find('.active-menuitem').each(function() {
                        var subItem = $(this);
                        $this.deactivate(subItem);
                        $this.removeMenuitem(subItem.attr('id'));
                    });
                }
            }
            else if(item.hasClass('active-menuitem')) {
                $this.deactivate(item);
                $this.removeMenuitem(item.attr('id'));
            }
        }
    },
            
    removeMenuitem: function (id) {
        this.expandedMenuitems = $.grep(this.expandedMenuitems, function (value) {
            return value !== id;
        });
        this.saveMenuState();
    },
    
    addMenuitem: function (id) {
        if ($.inArray(id, this.expandedMenuitems) === -1) {
            this.expandedMenuitems.push(id);
        }
        this.saveMenuState();
    },
    
    saveMenuState: function() {
        $.cookie('omega_expandeditems', this.expandedMenuitems.join(','), {path: '/'});
    },
    
    clearMenuState: function() {
        $.removeCookie('omega_expandeditems', {path: '/'});
    },
    
    saveProfileMenuState: function() {
        $.cookie('omega_profile_expanded', "1", {path: '/'});
    },
    
    clearProfileMenuState: function() {
        $.removeCookie('omega_profile_expanded', {path: '/'});
    },
    
    restoreMenuState: function() {
        var menucookie = $.cookie('omega_expandeditems');
        if (menucookie) {
            this.expandedMenuitems = menucookie.split(',');
            for (var i = 0; i < this.expandedMenuitems.length; i++) {
                var id = this.expandedMenuitems[i];
                if (id) {
                    var menuitem = $("#" + this.expandedMenuitems[i].replace(/:/g, "\\:"));
                    menuitem.addClass('active-menuitem');
                    
                    var submenu = menuitem.children('ul');
                    if(submenu.length) {
                        submenu.show();
                    }
                }
            }
        }
        
        var profileMenuCookie = $.cookie('omega_profile_expanded');
        if(profileMenuCookie) {
            this.profileButton.next('ul').show();
        }
    },
    
    isDesktop: function() {
        return window.innerWidth > 1024;
    }
    
});

/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2006, 2014 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD (Register as an anonymous module)
		define(['jquery'], factory);
	} else if (typeof exports === 'object') {
		// Node/CommonJS
		module.exports = factory(require('jquery'));
	} else {
		// Browser globals
		factory(jQuery);
	}
}(function ($) {

	var pluses = /\+/g;

	function encode(s) {
		return config.raw ? s : encodeURIComponent(s);
	}

	function decode(s) {
		return config.raw ? s : decodeURIComponent(s);
	}

	function stringifyCookieValue(value) {
		return encode(config.json ? JSON.stringify(value) : String(value));
	}

	function parseCookieValue(s) {
		if (s.indexOf('"') === 0) {
			// This is a quoted cookie as according to RFC2068, unescape...
			s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}

		try {
			// Replace server-side written pluses with spaces.
			// If we can't decode the cookie, ignore it, it's unusable.
			// If we can't parse the cookie, ignore it, it's unusable.
			s = decodeURIComponent(s.replace(pluses, ' '));
			return config.json ? JSON.parse(s) : s;
		} catch(e) {}
	}

	function read(s, converter) {
		var value = config.raw ? s : parseCookieValue(s);
		return $.isFunction(converter) ? converter(value) : value;
	}

	var config = $.cookie = function (key, value, options) {

		// Write

		if (arguments.length > 1 && !$.isFunction(value)) {
			options = $.extend({}, config.defaults, options);

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setMilliseconds(t.getMilliseconds() + days * 864e+5);
			}

			return (document.cookie = [
				encode(key), '=', stringifyCookieValue(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
				options.path    ? '; path=' + options.path : '',
				options.domain  ? '; domain=' + options.domain : '',
				options.secure  ? '; secure' : ''
			].join(''));
		}

		// Read

		var result = key ? undefined : {},
			// To prevent the for loop in the first place assign an empty array
			// in case there are no cookies at all. Also prevents odd result when
			// calling $.cookie().
			cookies = document.cookie ? document.cookie.split('; ') : [],
			i = 0,
			l = cookies.length;

		for (; i < l; i++) {
			var parts = cookies[i].split('='),
				name = decode(parts.shift()),
				cookie = parts.join('=');

			if (key === name) {
				// If second argument (value) is a function it's a converter...
				result = read(cookie, value);
				break;
			}

			// Prevent storing a cookie that we couldn't decode.
			if (!key && (cookie = read(cookie)) !== undefined) {
				result[name] = cookie;
			}
		}

		return result;
	};

	config.defaults = {};

	$.removeCookie = function (key, options) {
		// Must not alter options, thus extending a fresh object...
		$.cookie(key, '', $.extend({}, options, { expires: -1 }));
		return !$.cookie(key);
	};

}));

/* Issue #924 is fixed for 5.3+ and 6.0. (compatibility with 5.3) */
if(window['PrimeFaces'] && window['PrimeFaces'].widget.Dialog) {
    PrimeFaces.widget.Dialog = PrimeFaces.widget.Dialog.extend({
        
        enableModality: function() {
            this._super();
            $(document.body).children(this.jqId + '_modal').addClass('ui-dialog-mask');
        },
        
        syncWindowResize: function() {}
    });
}
    

