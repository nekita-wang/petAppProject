var __wxsModules={};

__wxsModules["7f9de7cc"] = (() => {
  var __getOwnPropNames = Object.getOwnPropertyNames;
  var __commonJS = (cb, mod) => function __require() {
    return mod || (0, cb[__getOwnPropNames(cb)[0]])((mod = { exports: {} }).exports, mod), mod.exports;
  };

  // <stdin>
  var require_stdin = __commonJS({
    "<stdin>"(exports, module) {
      function scroll(event, ownerInstance) {
        var detail = event.detail;
        var scrollWidth = detail.scrollWidth;
        var scrollLeft = detail.scrollLeft;
        var dataset = event.currentTarget.dataset;
        var scrollComponentWidth = dataset.scrollWidth || dataset.scrollwidth || 0;
        var indicatorWidth = dataset.indicatorWidth || dataset.indicatorwidth || 0;
        var barWidth = dataset.barWidth || dataset.barwidth || 0;
        var x = scrollLeft / (scrollWidth - scrollComponentWidth) * (indicatorWidth - barWidth);
        setBarStyle(ownerInstance, x);
      }
      function scrolltolower(event, ownerInstance) {
        ownerInstance.callMethod("scrollEvent", "right");
        var dataset = event.currentTarget.dataset;
        var indicatorWidth = dataset.indicatorWidth || dataset.indicatorwidth || 0;
        var barWidth = dataset.barWidth || dataset.barwidth || 0;
        setBarStyle(ownerInstance, indicatorWidth - barWidth);
      }
      function scrolltoupper(event, ownerInstance) {
        ownerInstance.callMethod("scrollEvent", "left");
        setBarStyle(ownerInstance, 0);
      }
      function setBarStyle(ownerInstance, x) {
        ownerInstance.selectComponent(".u-scroll-list__indicator__line__bar") && ownerInstance.selectComponent(".u-scroll-list__indicator__line__bar").setStyle({
          transform: "translateX(" + x + "px)"
        });
      }
      module.exports = {
        scroll,
        scrolltolower,
        scrolltoupper
      };
    }
  });
  return require_stdin();
})();


__wxsModules["229ea0d2"] = (() => {
  var __getOwnPropNames = Object.getOwnPropertyNames;
  var __commonJS = (cb, mod) => function __require() {
    return mod || (0, cb[__getOwnPropNames(cb)[0]])((mod = { exports: {} }).exports, mod), mod.exports;
  };

  // <stdin>
  var require_stdin = __commonJS({
    "<stdin>"(exports, module) {
      function touchstart(event, ownerInstance) {
        var instance = event.instance;
        var state = instance.getState();
        if (state.disabled)
          return;
        var touches = event.touches;
        if (touches && touches.length > 1)
          return;
        state.moving = true;
        state.startX = touches[0].pageX;
        state.startY = touches[0].pageY;
        ownerInstance.callMethod("closeOther");
      }
      function touchmove(event, ownerInstance) {
        var instance = event.instance;
        var state = instance.getState();
        if (state.disabled || !state.moving)
          return;
        var touches = event.touches;
        var pageX = touches[0].pageX;
        var pageY = touches[0].pageY;
        var moveX = pageX - state.startX;
        var moveY = pageY - state.startY;
        var buttonsWidth = state.buttonsWidth;
        if (Math.abs(moveX) > Math.abs(moveY) || Math.abs(moveX) > state.threshold) {
          event.preventDefault && event.preventDefault();
          event.stopPropagation && event.stopPropagation();
        }
        if (Math.abs(moveX) < Math.abs(moveY))
          return;
        if (state.status === "open") {
          if (moveX < 0)
            moveX = 0;
          if (moveX > buttonsWidth)
            moveX = buttonsWidth;
          moveSwipeAction(-buttonsWidth + moveX, instance, ownerInstance);
        } else {
          if (moveX > 0)
            moveX = 0;
          if (Math.abs(moveX) > buttonsWidth)
            moveX = -buttonsWidth;
          moveSwipeAction(moveX, instance, ownerInstance);
        }
      }
      function touchend(event, ownerInstance) {
        var instance = event.instance;
        var state = instance.getState();
        if (!state.moving || state.disabled)
          return;
        var touches = event.changedTouches ? event.changedTouches[0] : {};
        var pageX = touches.pageX;
        var pageY = touches.pageY;
        var moveX = pageX - state.startX;
        if (state.status === "open") {
          if (moveX < 0)
            return;
          if (moveX === 0) {
            return closeSwipeAction(instance, ownerInstance);
          }
          if (Math.abs(moveX) < state.threshold) {
            openSwipeAction(instance, ownerInstance);
          } else {
            closeSwipeAction(instance, ownerInstance);
          }
        } else {
          if (moveX > 0)
            return;
          if (Math.abs(moveX) < state.threshold) {
            closeSwipeAction(instance, ownerInstance);
          } else {
            openSwipeAction(instance, ownerInstance);
          }
        }
      }
      function getDuration(value) {
        if (value.toString().indexOf("s") >= 0)
          return value;
        return value > 30 ? value + "ms" : value + "s";
      }
      function moveSwipeAction(moveX, instance, ownerInstance) {
        var state = instance.getState();
        var buttons = ownerInstance.selectAllComponents(".u-swipe-action-item__right__button");
        instance.requestAnimationFrame(function() {
          instance.setStyle({
            // 设置translateX的值
            "transition": "none",
            transform: "translateX(" + moveX + "px)",
            "-webkit-transform": "translateX(" + moveX + "px)"
          });
        });
      }
      function openSwipeAction(instance, ownerInstance) {
        var state = instance.getState();
        var buttons = ownerInstance.selectAllComponents(".u-swipe-action-item__right__button");
        var duration = getDuration(state.duration);
        var buttonsWidth = -state.buttonsWidth;
        instance.requestAnimationFrame(function() {
          instance.setStyle({
            "transition": "transform " + duration,
            "transform": "translateX(" + buttonsWidth + "px)",
            "-webkit-transform": "translateX(" + buttonsWidth + "px)"
          });
        });
        setStatus("open", instance, ownerInstance);
      }
      function setStatus(status, instance, ownerInstance) {
        var state = instance.getState();
        state.status = status;
        ownerInstance.callMethod("setState", status);
      }
      function closeSwipeAction(instance, ownerInstance) {
        var state = instance.getState();
        var buttons = ownerInstance.selectAllComponents(".u-swipe-action-item__right__button");
        var len = buttons.length;
        var duration = getDuration(state.duration);
        instance.requestAnimationFrame(function() {
          instance.setStyle({
            "transition": "transform " + duration,
            "transform": "translateX(0px)",
            "-webkit-transform": "translateX(0px)"
          });
          for (var i = len - 1; i >= 0; i--) {
            buttons[i].setStyle({
              "transition": "transform " + duration,
              "transform": "translateX(0px)",
              "-webkit-transform": "translateX(0px)"
            });
          }
        });
        setStatus("close", instance, ownerInstance);
      }
      function statusChange(newValue, oldValue, ownerInstance, instance) {
        var state = instance.getState();
        if (state.disabled)
          return;
        if (newValue === "close" && state.status === "open") {
          closeSwipeAction(instance, ownerInstance);
        } else if (newValue === "open" && state.status === "close") {
          openSwipeAction(instance, ownerInstance);
        }
      }
      function sizeChange(newValue, oldValue, ownerInstance, instance) {
        var state = instance.getState();
        if (!state || !newValue) {
          return;
        }
        state.disabled = newValue.disabled;
        state.duration = newValue.duration;
        state.show = newValue.show;
        state.threshold = newValue.threshold;
        state.buttons = newValue.buttons;
        if (state.buttons) {
          var len = state.buttons.length;
          var buttonsWidth = 0;
          var buttons = newValue.buttons;
          for (var i = 0; i < len; i++) {
            buttonsWidth += buttons[i].width;
          }
        }
        state.buttonsWidth = buttonsWidth;
        if (state.show) {
          openSwipeAction(instance, ownerInstance);
        }
      }
      module.exports = {
        touchstart,
        touchmove,
        touchend,
        sizeChange,
        statusChange
      };
    }
  });
  return require_stdin();
})();


__wxsModules["6006ff9a"] = (() => {
  var __getOwnPropNames = Object.getOwnPropertyNames;
  var __commonJS = (cb, mod) => function __require() {
    return mod || (0, cb[__getOwnPropNames(cb)[0]])((mod = { exports: {} }).exports, mod), mod.exports;
  };

  // <stdin>
  var require_stdin = __commonJS({
    "<stdin>"(exports, module) {
      var inlineTags = {
        abbr: true,
        b: true,
        big: true,
        code: true,
        del: true,
        em: true,
        i: true,
        ins: true,
        label: true,
        q: true,
        small: true,
        span: true,
        strong: true,
        sub: true,
        sup: true
      };
      module.exports = {
        isInline: function(tagName, style) {
          return inlineTags[tagName] || (style || "").indexOf("display:inline") !== -1;
        }
      };
    }
  });
  return require_stdin();
})();
