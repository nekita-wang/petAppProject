if (typeof Promise !== "undefined" && !Promise.prototype.finally) {
  Promise.prototype.finally = function(callback) {
    const promise = this.constructor;
    return this.then(
      (value) => promise.resolve(callback()).then(() => value),
      (reason) => promise.resolve(callback()).then(() => {
        throw reason;
      })
    );
  };
}
;
if (typeof uni !== "undefined" && uni && uni.requireGlobal) {
  const global2 = uni.requireGlobal();
  ArrayBuffer = global2.ArrayBuffer;
  Int8Array = global2.Int8Array;
  Uint8Array = global2.Uint8Array;
  Uint8ClampedArray = global2.Uint8ClampedArray;
  Int16Array = global2.Int16Array;
  Uint16Array = global2.Uint16Array;
  Int32Array = global2.Int32Array;
  Uint32Array = global2.Uint32Array;
  Float32Array = global2.Float32Array;
  Float64Array = global2.Float64Array;
  BigInt64Array = global2.BigInt64Array;
  BigUint64Array = global2.BigUint64Array;
}
;
if (uni.restoreGlobal) {
  uni.restoreGlobal(Vue, weex, plus, setTimeout, clearTimeout, setInterval, clearInterval);
}
(function(vue) {
  "use strict";
  const _imports_0$2 = "/static/logo.png";
  const _export_sfc = (sfc, props) => {
    const target = sfc.__vccOpts || sfc;
    for (const [key, val] of props) {
      target[key] = val;
    }
    return target;
  };
  const _sfc_main$b = {
    __name: "login",
    setup(__props, { expose: __expose }) {
      __expose();
      const agreed = vue.ref(false);
      const handlePhoneLogin = () => {
        if (!agreed.value) {
          uni.showToast({ title: "请先同意用户协议和隐私政策", icon: "none" });
          return;
        } else {
          uni.navigateTo({ url: "/pages/login/sms" });
        }
      };
      const navigateToOtherLogin = () => {
        uni.showToast({ title: "暂不支持其他登录方式，敬请期待", icon: "none" });
      };
      const handleAgreeChange = (e) => {
        agreed.value = e.detail.value.length > 0;
      };
      const __returned__ = { agreed, handlePhoneLogin, navigateToOtherLogin, handleAgreeChange, ref: vue.ref };
      Object.defineProperty(__returned__, "__isScriptSetup", { enumerable: false, value: true });
      return __returned__;
    }
  };
  function _sfc_render$a(_ctx, _cache, $props, $setup, $data, $options) {
    return vue.openBlock(), vue.createElementBlock("view", { class: "container" }, [
      vue.createCommentVNode(" 顶部logo "),
      vue.createElementVNode("view", { class: "logo" }, [
        vue.createElementVNode("image", {
          src: _imports_0$2,
          mode: "",
          style: { "width": "350rpx", "height": "350rpx", "border-radius": "50%" }
        })
      ]),
      vue.createElementVNode("view", { class: "title" }, " 宠爱 "),
      vue.createCommentVNode(" 登录按钮 "),
      vue.createElementVNode("view", { class: "button-box" }, [
        vue.createElementVNode("button", {
          type: "primary",
          class: "sjh",
          onClick: $setup.handlePhoneLogin
        }, "手机号登录/注册"),
        vue.createElementVNode("button", {
          type: "default",
          class: "qt",
          onClick: $setup.navigateToOtherLogin
        }, "其他方式登录> ")
      ]),
      vue.createTextVNode("、 "),
      vue.createCommentVNode(" 底部协议描述 "),
      vue.createElementVNode("view", { class: "desc" }, [
        vue.createElementVNode("view", { class: "desc-content" }, [
          vue.createElementVNode(
            "checkbox-group",
            { onChange: $setup.handleAgreeChange },
            [
              vue.createElementVNode("checkbox", {
                style: { "transform": "scale(0.7)" },
                color: "#50C2C1"
              })
            ],
            32
            /* NEED_HYDRATION */
          ),
          vue.createElementVNode("text", { class: "desc" }, [
            vue.createTextVNode(" 我已阅读并同意 "),
            vue.createElementVNode("text", {
              class: "link",
              onClick: _cache[0] || (_cache[0] = ($event) => _ctx.navigateTo("agreement"))
            }, "《宠主用户协议》"),
            vue.createTextVNode("、 "),
            vue.createElementVNode("text", {
              class: "link",
              onClick: _cache[1] || (_cache[1] = ($event) => _ctx.navigateTo("privacy"))
            }, "《个人信息保护政策》"),
            vue.createTextVNode("， 我已明确知晓宠主平台禁止未满18周岁的未成年注册和使用 ")
          ])
        ])
      ])
    ]);
  }
  const PagesLoginLogin = /* @__PURE__ */ _export_sfc(_sfc_main$b, [["render", _sfc_render$a], ["__scopeId", "data-v-e4e4508d"], ["__file", "E:/ChongAi/ChongAiApp/pages/login/login.vue"]]);
  const ON_LOAD = "onLoad";
  function requireNativePlugin(name) {
    return weex.requireModule(name);
  }
  function formatAppLog(type, filename, ...args) {
    if (uni.__log__) {
      uni.__log__(type, filename, ...args);
    } else {
      console[type].apply(console, [...args, filename]);
    }
  }
  function resolveEasycom(component, easycom) {
    return typeof component === "string" ? easycom : component;
  }
  const createHook = (lifecycle) => (hook, target = vue.getCurrentInstance()) => {
    !vue.isInSSRComponentSetup && vue.injectHook(lifecycle, hook, target);
  };
  const onLoad = /* @__PURE__ */ createHook(ON_LOAD);
  const fontData = [
    {
      "font_class": "arrow-down",
      "unicode": ""
    },
    {
      "font_class": "arrow-left",
      "unicode": ""
    },
    {
      "font_class": "arrow-right",
      "unicode": ""
    },
    {
      "font_class": "arrow-up",
      "unicode": ""
    },
    {
      "font_class": "auth",
      "unicode": ""
    },
    {
      "font_class": "auth-filled",
      "unicode": ""
    },
    {
      "font_class": "back",
      "unicode": ""
    },
    {
      "font_class": "bars",
      "unicode": ""
    },
    {
      "font_class": "calendar",
      "unicode": ""
    },
    {
      "font_class": "calendar-filled",
      "unicode": ""
    },
    {
      "font_class": "camera",
      "unicode": ""
    },
    {
      "font_class": "camera-filled",
      "unicode": ""
    },
    {
      "font_class": "cart",
      "unicode": ""
    },
    {
      "font_class": "cart-filled",
      "unicode": ""
    },
    {
      "font_class": "chat",
      "unicode": ""
    },
    {
      "font_class": "chat-filled",
      "unicode": ""
    },
    {
      "font_class": "chatboxes",
      "unicode": ""
    },
    {
      "font_class": "chatboxes-filled",
      "unicode": ""
    },
    {
      "font_class": "chatbubble",
      "unicode": ""
    },
    {
      "font_class": "chatbubble-filled",
      "unicode": ""
    },
    {
      "font_class": "checkbox",
      "unicode": ""
    },
    {
      "font_class": "checkbox-filled",
      "unicode": ""
    },
    {
      "font_class": "checkmarkempty",
      "unicode": ""
    },
    {
      "font_class": "circle",
      "unicode": ""
    },
    {
      "font_class": "circle-filled",
      "unicode": ""
    },
    {
      "font_class": "clear",
      "unicode": ""
    },
    {
      "font_class": "close",
      "unicode": ""
    },
    {
      "font_class": "closeempty",
      "unicode": ""
    },
    {
      "font_class": "cloud-download",
      "unicode": ""
    },
    {
      "font_class": "cloud-download-filled",
      "unicode": ""
    },
    {
      "font_class": "cloud-upload",
      "unicode": ""
    },
    {
      "font_class": "cloud-upload-filled",
      "unicode": ""
    },
    {
      "font_class": "color",
      "unicode": ""
    },
    {
      "font_class": "color-filled",
      "unicode": ""
    },
    {
      "font_class": "compose",
      "unicode": ""
    },
    {
      "font_class": "contact",
      "unicode": ""
    },
    {
      "font_class": "contact-filled",
      "unicode": ""
    },
    {
      "font_class": "down",
      "unicode": ""
    },
    {
      "font_class": "bottom",
      "unicode": ""
    },
    {
      "font_class": "download",
      "unicode": ""
    },
    {
      "font_class": "download-filled",
      "unicode": ""
    },
    {
      "font_class": "email",
      "unicode": ""
    },
    {
      "font_class": "email-filled",
      "unicode": ""
    },
    {
      "font_class": "eye",
      "unicode": ""
    },
    {
      "font_class": "eye-filled",
      "unicode": ""
    },
    {
      "font_class": "eye-slash",
      "unicode": ""
    },
    {
      "font_class": "eye-slash-filled",
      "unicode": ""
    },
    {
      "font_class": "fire",
      "unicode": ""
    },
    {
      "font_class": "fire-filled",
      "unicode": ""
    },
    {
      "font_class": "flag",
      "unicode": ""
    },
    {
      "font_class": "flag-filled",
      "unicode": ""
    },
    {
      "font_class": "folder-add",
      "unicode": ""
    },
    {
      "font_class": "folder-add-filled",
      "unicode": ""
    },
    {
      "font_class": "font",
      "unicode": ""
    },
    {
      "font_class": "forward",
      "unicode": ""
    },
    {
      "font_class": "gear",
      "unicode": ""
    },
    {
      "font_class": "gear-filled",
      "unicode": ""
    },
    {
      "font_class": "gift",
      "unicode": ""
    },
    {
      "font_class": "gift-filled",
      "unicode": ""
    },
    {
      "font_class": "hand-down",
      "unicode": ""
    },
    {
      "font_class": "hand-down-filled",
      "unicode": ""
    },
    {
      "font_class": "hand-up",
      "unicode": ""
    },
    {
      "font_class": "hand-up-filled",
      "unicode": ""
    },
    {
      "font_class": "headphones",
      "unicode": ""
    },
    {
      "font_class": "heart",
      "unicode": ""
    },
    {
      "font_class": "heart-filled",
      "unicode": ""
    },
    {
      "font_class": "help",
      "unicode": ""
    },
    {
      "font_class": "help-filled",
      "unicode": ""
    },
    {
      "font_class": "home",
      "unicode": ""
    },
    {
      "font_class": "home-filled",
      "unicode": ""
    },
    {
      "font_class": "image",
      "unicode": ""
    },
    {
      "font_class": "image-filled",
      "unicode": ""
    },
    {
      "font_class": "images",
      "unicode": ""
    },
    {
      "font_class": "images-filled",
      "unicode": ""
    },
    {
      "font_class": "info",
      "unicode": ""
    },
    {
      "font_class": "info-filled",
      "unicode": ""
    },
    {
      "font_class": "left",
      "unicode": ""
    },
    {
      "font_class": "link",
      "unicode": ""
    },
    {
      "font_class": "list",
      "unicode": ""
    },
    {
      "font_class": "location",
      "unicode": ""
    },
    {
      "font_class": "location-filled",
      "unicode": ""
    },
    {
      "font_class": "locked",
      "unicode": ""
    },
    {
      "font_class": "locked-filled",
      "unicode": ""
    },
    {
      "font_class": "loop",
      "unicode": ""
    },
    {
      "font_class": "mail-open",
      "unicode": ""
    },
    {
      "font_class": "mail-open-filled",
      "unicode": ""
    },
    {
      "font_class": "map",
      "unicode": ""
    },
    {
      "font_class": "map-filled",
      "unicode": ""
    },
    {
      "font_class": "map-pin",
      "unicode": ""
    },
    {
      "font_class": "map-pin-ellipse",
      "unicode": ""
    },
    {
      "font_class": "medal",
      "unicode": ""
    },
    {
      "font_class": "medal-filled",
      "unicode": ""
    },
    {
      "font_class": "mic",
      "unicode": ""
    },
    {
      "font_class": "mic-filled",
      "unicode": ""
    },
    {
      "font_class": "micoff",
      "unicode": ""
    },
    {
      "font_class": "micoff-filled",
      "unicode": ""
    },
    {
      "font_class": "minus",
      "unicode": ""
    },
    {
      "font_class": "minus-filled",
      "unicode": ""
    },
    {
      "font_class": "more",
      "unicode": ""
    },
    {
      "font_class": "more-filled",
      "unicode": ""
    },
    {
      "font_class": "navigate",
      "unicode": ""
    },
    {
      "font_class": "navigate-filled",
      "unicode": ""
    },
    {
      "font_class": "notification",
      "unicode": ""
    },
    {
      "font_class": "notification-filled",
      "unicode": ""
    },
    {
      "font_class": "paperclip",
      "unicode": ""
    },
    {
      "font_class": "paperplane",
      "unicode": ""
    },
    {
      "font_class": "paperplane-filled",
      "unicode": ""
    },
    {
      "font_class": "person",
      "unicode": ""
    },
    {
      "font_class": "person-filled",
      "unicode": ""
    },
    {
      "font_class": "personadd",
      "unicode": ""
    },
    {
      "font_class": "personadd-filled",
      "unicode": ""
    },
    {
      "font_class": "personadd-filled-copy",
      "unicode": ""
    },
    {
      "font_class": "phone",
      "unicode": ""
    },
    {
      "font_class": "phone-filled",
      "unicode": ""
    },
    {
      "font_class": "plus",
      "unicode": ""
    },
    {
      "font_class": "plus-filled",
      "unicode": ""
    },
    {
      "font_class": "plusempty",
      "unicode": ""
    },
    {
      "font_class": "pulldown",
      "unicode": ""
    },
    {
      "font_class": "pyq",
      "unicode": ""
    },
    {
      "font_class": "qq",
      "unicode": ""
    },
    {
      "font_class": "redo",
      "unicode": ""
    },
    {
      "font_class": "redo-filled",
      "unicode": ""
    },
    {
      "font_class": "refresh",
      "unicode": ""
    },
    {
      "font_class": "refresh-filled",
      "unicode": ""
    },
    {
      "font_class": "refreshempty",
      "unicode": ""
    },
    {
      "font_class": "reload",
      "unicode": ""
    },
    {
      "font_class": "right",
      "unicode": ""
    },
    {
      "font_class": "scan",
      "unicode": ""
    },
    {
      "font_class": "search",
      "unicode": ""
    },
    {
      "font_class": "settings",
      "unicode": ""
    },
    {
      "font_class": "settings-filled",
      "unicode": ""
    },
    {
      "font_class": "shop",
      "unicode": ""
    },
    {
      "font_class": "shop-filled",
      "unicode": ""
    },
    {
      "font_class": "smallcircle",
      "unicode": ""
    },
    {
      "font_class": "smallcircle-filled",
      "unicode": ""
    },
    {
      "font_class": "sound",
      "unicode": ""
    },
    {
      "font_class": "sound-filled",
      "unicode": ""
    },
    {
      "font_class": "spinner-cycle",
      "unicode": ""
    },
    {
      "font_class": "staff",
      "unicode": ""
    },
    {
      "font_class": "staff-filled",
      "unicode": ""
    },
    {
      "font_class": "star",
      "unicode": ""
    },
    {
      "font_class": "star-filled",
      "unicode": ""
    },
    {
      "font_class": "starhalf",
      "unicode": ""
    },
    {
      "font_class": "trash",
      "unicode": ""
    },
    {
      "font_class": "trash-filled",
      "unicode": ""
    },
    {
      "font_class": "tune",
      "unicode": ""
    },
    {
      "font_class": "tune-filled",
      "unicode": ""
    },
    {
      "font_class": "undo",
      "unicode": ""
    },
    {
      "font_class": "undo-filled",
      "unicode": ""
    },
    {
      "font_class": "up",
      "unicode": ""
    },
    {
      "font_class": "top",
      "unicode": ""
    },
    {
      "font_class": "upload",
      "unicode": ""
    },
    {
      "font_class": "upload-filled",
      "unicode": ""
    },
    {
      "font_class": "videocam",
      "unicode": ""
    },
    {
      "font_class": "videocam-filled",
      "unicode": ""
    },
    {
      "font_class": "vip",
      "unicode": ""
    },
    {
      "font_class": "vip-filled",
      "unicode": ""
    },
    {
      "font_class": "wallet",
      "unicode": ""
    },
    {
      "font_class": "wallet-filled",
      "unicode": ""
    },
    {
      "font_class": "weibo",
      "unicode": ""
    },
    {
      "font_class": "weixin",
      "unicode": ""
    }
  ];
  const getVal$1 = (val) => {
    const reg = /^[0-9]*$/g;
    return typeof val === "number" || reg.test(val) ? val + "px" : val;
  };
  const _sfc_main$a = {
    name: "UniIcons",
    emits: ["click"],
    props: {
      type: {
        type: String,
        default: ""
      },
      color: {
        type: String,
        default: "#333333"
      },
      size: {
        type: [Number, String],
        default: 16
      },
      customPrefix: {
        type: String,
        default: ""
      },
      fontFamily: {
        type: String,
        default: ""
      }
    },
    data() {
      return {
        icons: fontData
      };
    },
    computed: {
      unicode() {
        let code = this.icons.find((v) => v.font_class === this.type);
        if (code) {
          return code.unicode;
        }
        return "";
      },
      iconSize() {
        return getVal$1(this.size);
      },
      styleObj() {
        if (this.fontFamily !== "") {
          return `color: ${this.color}; font-size: ${this.iconSize}; font-family: ${this.fontFamily};`;
        }
        return `color: ${this.color}; font-size: ${this.iconSize};`;
      }
    },
    methods: {
      _onClick() {
        this.$emit("click");
      }
    }
  };
  function _sfc_render$9(_ctx, _cache, $props, $setup, $data, $options) {
    return vue.openBlock(), vue.createElementBlock(
      "text",
      {
        style: vue.normalizeStyle($options.styleObj),
        class: vue.normalizeClass(["uni-icons", ["uniui-" + $props.type, $props.customPrefix, $props.customPrefix ? $props.type : ""]]),
        onClick: _cache[0] || (_cache[0] = (...args) => $options._onClick && $options._onClick(...args))
      },
      [
        vue.renderSlot(_ctx.$slots, "default", {}, void 0, true)
      ],
      6
      /* CLASS, STYLE */
    );
  }
  const __easycom_0 = /* @__PURE__ */ _export_sfc(_sfc_main$a, [["render", _sfc_render$9], ["__scopeId", "data-v-d31e1c47"], ["__file", "E:/ChongAi/ChongAiApp/uni_modules/uni-icons/components/uni-icons/uni-icons.vue"]]);
  var isVue2 = false;
  function set(target, key, val) {
    if (Array.isArray(target)) {
      target.length = Math.max(target.length, key);
      target.splice(key, 1, val);
      return val;
    }
    target[key] = val;
    return val;
  }
  function del(target, key) {
    if (Array.isArray(target)) {
      target.splice(key, 1);
      return;
    }
    delete target[key];
  }
  function getDevtoolsGlobalHook() {
    return getTarget().__VUE_DEVTOOLS_GLOBAL_HOOK__;
  }
  function getTarget() {
    return typeof navigator !== "undefined" && typeof window !== "undefined" ? window : typeof global !== "undefined" ? global : {};
  }
  const isProxyAvailable = typeof Proxy === "function";
  const HOOK_SETUP = "devtools-plugin:setup";
  const HOOK_PLUGIN_SETTINGS_SET = "plugin:settings:set";
  let supported;
  let perf;
  function isPerformanceSupported() {
    var _a2;
    if (supported !== void 0) {
      return supported;
    }
    if (typeof window !== "undefined" && window.performance) {
      supported = true;
      perf = window.performance;
    } else if (typeof global !== "undefined" && ((_a2 = global.perf_hooks) === null || _a2 === void 0 ? void 0 : _a2.performance)) {
      supported = true;
      perf = global.perf_hooks.performance;
    } else {
      supported = false;
    }
    return supported;
  }
  function now() {
    return isPerformanceSupported() ? perf.now() : Date.now();
  }
  class ApiProxy {
    constructor(plugin, hook) {
      this.target = null;
      this.targetQueue = [];
      this.onQueue = [];
      this.plugin = plugin;
      this.hook = hook;
      const defaultSettings = {};
      if (plugin.settings) {
        for (const id in plugin.settings) {
          const item = plugin.settings[id];
          defaultSettings[id] = item.defaultValue;
        }
      }
      const localSettingsSaveId = `__vue-devtools-plugin-settings__${plugin.id}`;
      let currentSettings = Object.assign({}, defaultSettings);
      try {
        const raw = localStorage.getItem(localSettingsSaveId);
        const data = JSON.parse(raw);
        Object.assign(currentSettings, data);
      } catch (e) {
      }
      this.fallbacks = {
        getSettings() {
          return currentSettings;
        },
        setSettings(value) {
          try {
            localStorage.setItem(localSettingsSaveId, JSON.stringify(value));
          } catch (e) {
          }
          currentSettings = value;
        },
        now() {
          return now();
        }
      };
      if (hook) {
        hook.on(HOOK_PLUGIN_SETTINGS_SET, (pluginId, value) => {
          if (pluginId === this.plugin.id) {
            this.fallbacks.setSettings(value);
          }
        });
      }
      this.proxiedOn = new Proxy({}, {
        get: (_target, prop) => {
          if (this.target) {
            return this.target.on[prop];
          } else {
            return (...args) => {
              this.onQueue.push({
                method: prop,
                args
              });
            };
          }
        }
      });
      this.proxiedTarget = new Proxy({}, {
        get: (_target, prop) => {
          if (this.target) {
            return this.target[prop];
          } else if (prop === "on") {
            return this.proxiedOn;
          } else if (Object.keys(this.fallbacks).includes(prop)) {
            return (...args) => {
              this.targetQueue.push({
                method: prop,
                args,
                resolve: () => {
                }
              });
              return this.fallbacks[prop](...args);
            };
          } else {
            return (...args) => {
              return new Promise((resolve) => {
                this.targetQueue.push({
                  method: prop,
                  args,
                  resolve
                });
              });
            };
          }
        }
      });
    }
    async setRealTarget(target) {
      this.target = target;
      for (const item of this.onQueue) {
        this.target.on[item.method](...item.args);
      }
      for (const item of this.targetQueue) {
        item.resolve(await this.target[item.method](...item.args));
      }
    }
  }
  function setupDevtoolsPlugin(pluginDescriptor, setupFn) {
    const descriptor = pluginDescriptor;
    const target = getTarget();
    const hook = getDevtoolsGlobalHook();
    const enableProxy = isProxyAvailable && descriptor.enableEarlyProxy;
    if (hook && (target.__VUE_DEVTOOLS_PLUGIN_API_AVAILABLE__ || !enableProxy)) {
      hook.emit(HOOK_SETUP, pluginDescriptor, setupFn);
    } else {
      const proxy = enableProxy ? new ApiProxy(descriptor, hook) : null;
      const list = target.__VUE_DEVTOOLS_PLUGINS__ = target.__VUE_DEVTOOLS_PLUGINS__ || [];
      list.push({
        pluginDescriptor: descriptor,
        setupFn,
        proxy
      });
      if (proxy)
        setupFn(proxy.proxiedTarget);
    }
  }
  /*!
   * pinia v2.1.7
   * (c) 2023 Eduardo San Martin Morote
   * @license MIT
   */
  let activePinia;
  const setActivePinia = (pinia) => activePinia = pinia;
  const piniaSymbol = Symbol("pinia");
  function isPlainObject(o) {
    return o && typeof o === "object" && Object.prototype.toString.call(o) === "[object Object]" && typeof o.toJSON !== "function";
  }
  var MutationType;
  (function(MutationType2) {
    MutationType2["direct"] = "direct";
    MutationType2["patchObject"] = "patch object";
    MutationType2["patchFunction"] = "patch function";
  })(MutationType || (MutationType = {}));
  const IS_CLIENT = typeof window !== "undefined";
  const USE_DEVTOOLS = IS_CLIENT;
  const _global = /* @__PURE__ */ (() => typeof window === "object" && window.window === window ? window : typeof self === "object" && self.self === self ? self : typeof global === "object" && global.global === global ? global : typeof globalThis === "object" ? globalThis : { HTMLElement: null })();
  function bom(blob, { autoBom = false } = {}) {
    if (autoBom && /^\s*(?:text\/\S*|application\/xml|\S*\/\S*\+xml)\s*;.*charset\s*=\s*utf-8/i.test(blob.type)) {
      return new Blob([String.fromCharCode(65279), blob], { type: blob.type });
    }
    return blob;
  }
  function download(url, name, opts) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", url);
    xhr.responseType = "blob";
    xhr.onload = function() {
      saveAs(xhr.response, name, opts);
    };
    xhr.onerror = function() {
      console.error("could not download file");
    };
    xhr.send();
  }
  function corsEnabled(url) {
    const xhr = new XMLHttpRequest();
    xhr.open("HEAD", url, false);
    try {
      xhr.send();
    } catch (e) {
    }
    return xhr.status >= 200 && xhr.status <= 299;
  }
  function click(node) {
    try {
      node.dispatchEvent(new MouseEvent("click"));
    } catch (e) {
      const evt = document.createEvent("MouseEvents");
      evt.initMouseEvent("click", true, true, window, 0, 0, 0, 80, 20, false, false, false, false, 0, null);
      node.dispatchEvent(evt);
    }
  }
  const _navigator = typeof navigator === "object" ? navigator : { userAgent: "" };
  const isMacOSWebView = /* @__PURE__ */ (() => /Macintosh/.test(_navigator.userAgent) && /AppleWebKit/.test(_navigator.userAgent) && !/Safari/.test(_navigator.userAgent))();
  const saveAs = !IS_CLIENT ? () => {
  } : (
    // Use download attribute first if possible (#193 Lumia mobile) unless this is a macOS WebView or mini program
    typeof HTMLAnchorElement !== "undefined" && "download" in HTMLAnchorElement.prototype && !isMacOSWebView ? downloadSaveAs : (
      // Use msSaveOrOpenBlob as a second approach
      "msSaveOrOpenBlob" in _navigator ? msSaveAs : (
        // Fallback to using FileReader and a popup
        fileSaverSaveAs
      )
    )
  );
  function downloadSaveAs(blob, name = "download", opts) {
    const a = document.createElement("a");
    a.download = name;
    a.rel = "noopener";
    if (typeof blob === "string") {
      a.href = blob;
      if (a.origin !== location.origin) {
        if (corsEnabled(a.href)) {
          download(blob, name, opts);
        } else {
          a.target = "_blank";
          click(a);
        }
      } else {
        click(a);
      }
    } else {
      a.href = URL.createObjectURL(blob);
      setTimeout(function() {
        URL.revokeObjectURL(a.href);
      }, 4e4);
      setTimeout(function() {
        click(a);
      }, 0);
    }
  }
  function msSaveAs(blob, name = "download", opts) {
    if (typeof blob === "string") {
      if (corsEnabled(blob)) {
        download(blob, name, opts);
      } else {
        const a = document.createElement("a");
        a.href = blob;
        a.target = "_blank";
        setTimeout(function() {
          click(a);
        });
      }
    } else {
      navigator.msSaveOrOpenBlob(bom(blob, opts), name);
    }
  }
  function fileSaverSaveAs(blob, name, opts, popup) {
    popup = popup || open("", "_blank");
    if (popup) {
      popup.document.title = popup.document.body.innerText = "downloading...";
    }
    if (typeof blob === "string")
      return download(blob, name, opts);
    const force = blob.type === "application/octet-stream";
    const isSafari = /constructor/i.test(String(_global.HTMLElement)) || "safari" in _global;
    const isChromeIOS = /CriOS\/[\d]+/.test(navigator.userAgent);
    if ((isChromeIOS || force && isSafari || isMacOSWebView) && typeof FileReader !== "undefined") {
      const reader = new FileReader();
      reader.onloadend = function() {
        let url = reader.result;
        if (typeof url !== "string") {
          popup = null;
          throw new Error("Wrong reader.result type");
        }
        url = isChromeIOS ? url : url.replace(/^data:[^;]*;/, "data:attachment/file;");
        if (popup) {
          popup.location.href = url;
        } else {
          location.assign(url);
        }
        popup = null;
      };
      reader.readAsDataURL(blob);
    } else {
      const url = URL.createObjectURL(blob);
      if (popup)
        popup.location.assign(url);
      else
        location.href = url;
      popup = null;
      setTimeout(function() {
        URL.revokeObjectURL(url);
      }, 4e4);
    }
  }
  function toastMessage(message, type) {
    const piniaMessage = "🍍 " + message;
    if (typeof __VUE_DEVTOOLS_TOAST__ === "function") {
      __VUE_DEVTOOLS_TOAST__(piniaMessage, type);
    } else if (type === "error") {
      console.error(piniaMessage);
    } else if (type === "warn") {
      console.warn(piniaMessage);
    } else {
      console.log(piniaMessage);
    }
  }
  function isPinia(o) {
    return "_a" in o && "install" in o;
  }
  function checkClipboardAccess() {
    if (!("clipboard" in navigator)) {
      toastMessage(`Your browser doesn't support the Clipboard API`, "error");
      return true;
    }
  }
  function checkNotFocusedError(error) {
    if (error instanceof Error && error.message.toLowerCase().includes("document is not focused")) {
      toastMessage('You need to activate the "Emulate a focused page" setting in the "Rendering" panel of devtools.', "warn");
      return true;
    }
    return false;
  }
  async function actionGlobalCopyState(pinia) {
    if (checkClipboardAccess())
      return;
    try {
      await navigator.clipboard.writeText(JSON.stringify(pinia.state.value));
      toastMessage("Global state copied to clipboard.");
    } catch (error) {
      if (checkNotFocusedError(error))
        return;
      toastMessage(`Failed to serialize the state. Check the console for more details.`, "error");
      console.error(error);
    }
  }
  async function actionGlobalPasteState(pinia) {
    if (checkClipboardAccess())
      return;
    try {
      loadStoresState(pinia, JSON.parse(await navigator.clipboard.readText()));
      toastMessage("Global state pasted from clipboard.");
    } catch (error) {
      if (checkNotFocusedError(error))
        return;
      toastMessage(`Failed to deserialize the state from clipboard. Check the console for more details.`, "error");
      console.error(error);
    }
  }
  async function actionGlobalSaveState(pinia) {
    try {
      saveAs(new Blob([JSON.stringify(pinia.state.value)], {
        type: "text/plain;charset=utf-8"
      }), "pinia-state.json");
    } catch (error) {
      toastMessage(`Failed to export the state as JSON. Check the console for more details.`, "error");
      console.error(error);
    }
  }
  let fileInput;
  function getFileOpener() {
    if (!fileInput) {
      fileInput = document.createElement("input");
      fileInput.type = "file";
      fileInput.accept = ".json";
    }
    function openFile() {
      return new Promise((resolve, reject) => {
        fileInput.onchange = async () => {
          const files = fileInput.files;
          if (!files)
            return resolve(null);
          const file = files.item(0);
          if (!file)
            return resolve(null);
          return resolve({ text: await file.text(), file });
        };
        fileInput.oncancel = () => resolve(null);
        fileInput.onerror = reject;
        fileInput.click();
      });
    }
    return openFile;
  }
  async function actionGlobalOpenStateFile(pinia) {
    try {
      const open2 = getFileOpener();
      const result = await open2();
      if (!result)
        return;
      const { text, file } = result;
      loadStoresState(pinia, JSON.parse(text));
      toastMessage(`Global state imported from "${file.name}".`);
    } catch (error) {
      toastMessage(`Failed to import the state from JSON. Check the console for more details.`, "error");
      console.error(error);
    }
  }
  function loadStoresState(pinia, state) {
    for (const key in state) {
      const storeState = pinia.state.value[key];
      if (storeState) {
        Object.assign(storeState, state[key]);
      } else {
        pinia.state.value[key] = state[key];
      }
    }
  }
  function formatDisplay(display) {
    return {
      _custom: {
        display
      }
    };
  }
  const PINIA_ROOT_LABEL = "🍍 Pinia (root)";
  const PINIA_ROOT_ID = "_root";
  function formatStoreForInspectorTree(store) {
    return isPinia(store) ? {
      id: PINIA_ROOT_ID,
      label: PINIA_ROOT_LABEL
    } : {
      id: store.$id,
      label: store.$id
    };
  }
  function formatStoreForInspectorState(store) {
    if (isPinia(store)) {
      const storeNames = Array.from(store._s.keys());
      const storeMap = store._s;
      const state2 = {
        state: storeNames.map((storeId) => ({
          editable: true,
          key: storeId,
          value: store.state.value[storeId]
        })),
        getters: storeNames.filter((id) => storeMap.get(id)._getters).map((id) => {
          const store2 = storeMap.get(id);
          return {
            editable: false,
            key: id,
            value: store2._getters.reduce((getters, key) => {
              getters[key] = store2[key];
              return getters;
            }, {})
          };
        })
      };
      return state2;
    }
    const state = {
      state: Object.keys(store.$state).map((key) => ({
        editable: true,
        key,
        value: store.$state[key]
      }))
    };
    if (store._getters && store._getters.length) {
      state.getters = store._getters.map((getterName) => ({
        editable: false,
        key: getterName,
        value: store[getterName]
      }));
    }
    if (store._customProperties.size) {
      state.customProperties = Array.from(store._customProperties).map((key) => ({
        editable: true,
        key,
        value: store[key]
      }));
    }
    return state;
  }
  function formatEventData(events) {
    if (!events)
      return {};
    if (Array.isArray(events)) {
      return events.reduce((data, event) => {
        data.keys.push(event.key);
        data.operations.push(event.type);
        data.oldValue[event.key] = event.oldValue;
        data.newValue[event.key] = event.newValue;
        return data;
      }, {
        oldValue: {},
        keys: [],
        operations: [],
        newValue: {}
      });
    } else {
      return {
        operation: formatDisplay(events.type),
        key: formatDisplay(events.key),
        oldValue: events.oldValue,
        newValue: events.newValue
      };
    }
  }
  function formatMutationType(type) {
    switch (type) {
      case MutationType.direct:
        return "mutation";
      case MutationType.patchFunction:
        return "$patch";
      case MutationType.patchObject:
        return "$patch";
      default:
        return "unknown";
    }
  }
  let isTimelineActive = true;
  const componentStateTypes = [];
  const MUTATIONS_LAYER_ID = "pinia:mutations";
  const INSPECTOR_ID = "pinia";
  const { assign: assign$1 } = Object;
  const getStoreType = (id) => "🍍 " + id;
  function registerPiniaDevtools(app, pinia) {
    setupDevtoolsPlugin({
      id: "dev.esm.pinia",
      label: "Pinia 🍍",
      logo: "https://pinia.vuejs.org/logo.svg",
      packageName: "pinia",
      homepage: "https://pinia.vuejs.org",
      componentStateTypes,
      app
    }, (api) => {
      if (typeof api.now !== "function") {
        toastMessage("You seem to be using an outdated version of Vue Devtools. Are you still using the Beta release instead of the stable one? You can find the links at https://devtools.vuejs.org/guide/installation.html.");
      }
      api.addTimelineLayer({
        id: MUTATIONS_LAYER_ID,
        label: `Pinia 🍍`,
        color: 15064968
      });
      api.addInspector({
        id: INSPECTOR_ID,
        label: "Pinia 🍍",
        icon: "storage",
        treeFilterPlaceholder: "Search stores",
        actions: [
          {
            icon: "content_copy",
            action: () => {
              actionGlobalCopyState(pinia);
            },
            tooltip: "Serialize and copy the state"
          },
          {
            icon: "content_paste",
            action: async () => {
              await actionGlobalPasteState(pinia);
              api.sendInspectorTree(INSPECTOR_ID);
              api.sendInspectorState(INSPECTOR_ID);
            },
            tooltip: "Replace the state with the content of your clipboard"
          },
          {
            icon: "save",
            action: () => {
              actionGlobalSaveState(pinia);
            },
            tooltip: "Save the state as a JSON file"
          },
          {
            icon: "folder_open",
            action: async () => {
              await actionGlobalOpenStateFile(pinia);
              api.sendInspectorTree(INSPECTOR_ID);
              api.sendInspectorState(INSPECTOR_ID);
            },
            tooltip: "Import the state from a JSON file"
          }
        ],
        nodeActions: [
          {
            icon: "restore",
            tooltip: 'Reset the state (with "$reset")',
            action: (nodeId) => {
              const store = pinia._s.get(nodeId);
              if (!store) {
                toastMessage(`Cannot reset "${nodeId}" store because it wasn't found.`, "warn");
              } else if (typeof store.$reset !== "function") {
                toastMessage(`Cannot reset "${nodeId}" store because it doesn't have a "$reset" method implemented.`, "warn");
              } else {
                store.$reset();
                toastMessage(`Store "${nodeId}" reset.`);
              }
            }
          }
        ]
      });
      api.on.inspectComponent((payload, ctx) => {
        const proxy = payload.componentInstance && payload.componentInstance.proxy;
        if (proxy && proxy._pStores) {
          const piniaStores = payload.componentInstance.proxy._pStores;
          Object.values(piniaStores).forEach((store) => {
            payload.instanceData.state.push({
              type: getStoreType(store.$id),
              key: "state",
              editable: true,
              value: store._isOptionsAPI ? {
                _custom: {
                  value: vue.toRaw(store.$state),
                  actions: [
                    {
                      icon: "restore",
                      tooltip: "Reset the state of this store",
                      action: () => store.$reset()
                    }
                  ]
                }
              } : (
                // NOTE: workaround to unwrap transferred refs
                Object.keys(store.$state).reduce((state, key) => {
                  state[key] = store.$state[key];
                  return state;
                }, {})
              )
            });
            if (store._getters && store._getters.length) {
              payload.instanceData.state.push({
                type: getStoreType(store.$id),
                key: "getters",
                editable: false,
                value: store._getters.reduce((getters, key) => {
                  try {
                    getters[key] = store[key];
                  } catch (error) {
                    getters[key] = error;
                  }
                  return getters;
                }, {})
              });
            }
          });
        }
      });
      api.on.getInspectorTree((payload) => {
        if (payload.app === app && payload.inspectorId === INSPECTOR_ID) {
          let stores = [pinia];
          stores = stores.concat(Array.from(pinia._s.values()));
          payload.rootNodes = (payload.filter ? stores.filter((store) => "$id" in store ? store.$id.toLowerCase().includes(payload.filter.toLowerCase()) : PINIA_ROOT_LABEL.toLowerCase().includes(payload.filter.toLowerCase())) : stores).map(formatStoreForInspectorTree);
        }
      });
      api.on.getInspectorState((payload) => {
        if (payload.app === app && payload.inspectorId === INSPECTOR_ID) {
          const inspectedStore = payload.nodeId === PINIA_ROOT_ID ? pinia : pinia._s.get(payload.nodeId);
          if (!inspectedStore) {
            return;
          }
          if (inspectedStore) {
            payload.state = formatStoreForInspectorState(inspectedStore);
          }
        }
      });
      api.on.editInspectorState((payload, ctx) => {
        if (payload.app === app && payload.inspectorId === INSPECTOR_ID) {
          const inspectedStore = payload.nodeId === PINIA_ROOT_ID ? pinia : pinia._s.get(payload.nodeId);
          if (!inspectedStore) {
            return toastMessage(`store "${payload.nodeId}" not found`, "error");
          }
          const { path } = payload;
          if (!isPinia(inspectedStore)) {
            if (path.length !== 1 || !inspectedStore._customProperties.has(path[0]) || path[0] in inspectedStore.$state) {
              path.unshift("$state");
            }
          } else {
            path.unshift("state");
          }
          isTimelineActive = false;
          payload.set(inspectedStore, path, payload.state.value);
          isTimelineActive = true;
        }
      });
      api.on.editComponentState((payload) => {
        if (payload.type.startsWith("🍍")) {
          const storeId = payload.type.replace(/^🍍\s*/, "");
          const store = pinia._s.get(storeId);
          if (!store) {
            return toastMessage(`store "${storeId}" not found`, "error");
          }
          const { path } = payload;
          if (path[0] !== "state") {
            return toastMessage(`Invalid path for store "${storeId}":
${path}
Only state can be modified.`);
          }
          path[0] = "$state";
          isTimelineActive = false;
          payload.set(store, path, payload.state.value);
          isTimelineActive = true;
        }
      });
    });
  }
  function addStoreToDevtools(app, store) {
    if (!componentStateTypes.includes(getStoreType(store.$id))) {
      componentStateTypes.push(getStoreType(store.$id));
    }
    setupDevtoolsPlugin({
      id: "dev.esm.pinia",
      label: "Pinia 🍍",
      logo: "https://pinia.vuejs.org/logo.svg",
      packageName: "pinia",
      homepage: "https://pinia.vuejs.org",
      componentStateTypes,
      app,
      settings: {
        logStoreChanges: {
          label: "Notify about new/deleted stores",
          type: "boolean",
          defaultValue: true
        }
        // useEmojis: {
        //   label: 'Use emojis in messages ⚡️',
        //   type: 'boolean',
        //   defaultValue: true,
        // },
      }
    }, (api) => {
      const now2 = typeof api.now === "function" ? api.now.bind(api) : Date.now;
      store.$onAction(({ after, onError, name, args }) => {
        const groupId = runningActionId++;
        api.addTimelineEvent({
          layerId: MUTATIONS_LAYER_ID,
          event: {
            time: now2(),
            title: "🛫 " + name,
            subtitle: "start",
            data: {
              store: formatDisplay(store.$id),
              action: formatDisplay(name),
              args
            },
            groupId
          }
        });
        after((result) => {
          activeAction = void 0;
          api.addTimelineEvent({
            layerId: MUTATIONS_LAYER_ID,
            event: {
              time: now2(),
              title: "🛬 " + name,
              subtitle: "end",
              data: {
                store: formatDisplay(store.$id),
                action: formatDisplay(name),
                args,
                result
              },
              groupId
            }
          });
        });
        onError((error) => {
          activeAction = void 0;
          api.addTimelineEvent({
            layerId: MUTATIONS_LAYER_ID,
            event: {
              time: now2(),
              logType: "error",
              title: "💥 " + name,
              subtitle: "end",
              data: {
                store: formatDisplay(store.$id),
                action: formatDisplay(name),
                args,
                error
              },
              groupId
            }
          });
        });
      }, true);
      store._customProperties.forEach((name) => {
        vue.watch(() => vue.unref(store[name]), (newValue, oldValue) => {
          api.notifyComponentUpdate();
          api.sendInspectorState(INSPECTOR_ID);
          if (isTimelineActive) {
            api.addTimelineEvent({
              layerId: MUTATIONS_LAYER_ID,
              event: {
                time: now2(),
                title: "Change",
                subtitle: name,
                data: {
                  newValue,
                  oldValue
                },
                groupId: activeAction
              }
            });
          }
        }, { deep: true });
      });
      store.$subscribe(({ events, type }, state) => {
        api.notifyComponentUpdate();
        api.sendInspectorState(INSPECTOR_ID);
        if (!isTimelineActive)
          return;
        const eventData = {
          time: now2(),
          title: formatMutationType(type),
          data: assign$1({ store: formatDisplay(store.$id) }, formatEventData(events)),
          groupId: activeAction
        };
        if (type === MutationType.patchFunction) {
          eventData.subtitle = "⤵️";
        } else if (type === MutationType.patchObject) {
          eventData.subtitle = "🧩";
        } else if (events && !Array.isArray(events)) {
          eventData.subtitle = events.type;
        }
        if (events) {
          eventData.data["rawEvent(s)"] = {
            _custom: {
              display: "DebuggerEvent",
              type: "object",
              tooltip: "raw DebuggerEvent[]",
              value: events
            }
          };
        }
        api.addTimelineEvent({
          layerId: MUTATIONS_LAYER_ID,
          event: eventData
        });
      }, { detached: true, flush: "sync" });
      const hotUpdate = store._hotUpdate;
      store._hotUpdate = vue.markRaw((newStore) => {
        hotUpdate(newStore);
        api.addTimelineEvent({
          layerId: MUTATIONS_LAYER_ID,
          event: {
            time: now2(),
            title: "🔥 " + store.$id,
            subtitle: "HMR update",
            data: {
              store: formatDisplay(store.$id),
              info: formatDisplay(`HMR update`)
            }
          }
        });
        api.notifyComponentUpdate();
        api.sendInspectorTree(INSPECTOR_ID);
        api.sendInspectorState(INSPECTOR_ID);
      });
      const { $dispose } = store;
      store.$dispose = () => {
        $dispose();
        api.notifyComponentUpdate();
        api.sendInspectorTree(INSPECTOR_ID);
        api.sendInspectorState(INSPECTOR_ID);
        api.getSettings().logStoreChanges && toastMessage(`Disposed "${store.$id}" store 🗑`);
      };
      api.notifyComponentUpdate();
      api.sendInspectorTree(INSPECTOR_ID);
      api.sendInspectorState(INSPECTOR_ID);
      api.getSettings().logStoreChanges && toastMessage(`"${store.$id}" store installed 🆕`);
    });
  }
  let runningActionId = 0;
  let activeAction;
  function patchActionForGrouping(store, actionNames, wrapWithProxy) {
    const actions = actionNames.reduce((storeActions, actionName) => {
      storeActions[actionName] = vue.toRaw(store)[actionName];
      return storeActions;
    }, {});
    for (const actionName in actions) {
      store[actionName] = function() {
        const _actionId = runningActionId;
        const trackedStore = wrapWithProxy ? new Proxy(store, {
          get(...args) {
            activeAction = _actionId;
            return Reflect.get(...args);
          },
          set(...args) {
            activeAction = _actionId;
            return Reflect.set(...args);
          }
        }) : store;
        activeAction = _actionId;
        const retValue = actions[actionName].apply(trackedStore, arguments);
        activeAction = void 0;
        return retValue;
      };
    }
  }
  function devtoolsPlugin({ app, store, options }) {
    if (store.$id.startsWith("__hot:")) {
      return;
    }
    store._isOptionsAPI = !!options.state;
    patchActionForGrouping(store, Object.keys(options.actions), store._isOptionsAPI);
    const originalHotUpdate = store._hotUpdate;
    vue.toRaw(store)._hotUpdate = function(newStore) {
      originalHotUpdate.apply(this, arguments);
      patchActionForGrouping(store, Object.keys(newStore._hmrPayload.actions), !!store._isOptionsAPI);
    };
    addStoreToDevtools(
      app,
      // FIXME: is there a way to allow the assignment from Store<Id, S, G, A> to StoreGeneric?
      store
    );
  }
  function createPinia() {
    const scope = vue.effectScope(true);
    const state = scope.run(() => vue.ref({}));
    let _p = [];
    let toBeInstalled = [];
    const pinia = vue.markRaw({
      install(app) {
        setActivePinia(pinia);
        {
          pinia._a = app;
          app.provide(piniaSymbol, pinia);
          app.config.globalProperties.$pinia = pinia;
          if (USE_DEVTOOLS) {
            registerPiniaDevtools(app, pinia);
          }
          toBeInstalled.forEach((plugin) => _p.push(plugin));
          toBeInstalled = [];
        }
      },
      use(plugin) {
        if (!this._a && !isVue2) {
          toBeInstalled.push(plugin);
        } else {
          _p.push(plugin);
        }
        return this;
      },
      _p,
      // it's actually undefined here
      // @ts-expect-error
      _a: null,
      _e: scope,
      _s: /* @__PURE__ */ new Map(),
      state
    });
    if (USE_DEVTOOLS && typeof Proxy !== "undefined") {
      pinia.use(devtoolsPlugin);
    }
    return pinia;
  }
  function patchObject(newState, oldState) {
    for (const key in oldState) {
      const subPatch = oldState[key];
      if (!(key in newState)) {
        continue;
      }
      const targetValue = newState[key];
      if (isPlainObject(targetValue) && isPlainObject(subPatch) && !vue.isRef(subPatch) && !vue.isReactive(subPatch)) {
        newState[key] = patchObject(targetValue, subPatch);
      } else {
        {
          newState[key] = subPatch;
        }
      }
    }
    return newState;
  }
  const noop = () => {
  };
  function addSubscription(subscriptions, callback, detached, onCleanup = noop) {
    subscriptions.push(callback);
    const removeSubscription = () => {
      const idx = subscriptions.indexOf(callback);
      if (idx > -1) {
        subscriptions.splice(idx, 1);
        onCleanup();
      }
    };
    if (!detached && vue.getCurrentScope()) {
      vue.onScopeDispose(removeSubscription);
    }
    return removeSubscription;
  }
  function triggerSubscriptions(subscriptions, ...args) {
    subscriptions.slice().forEach((callback) => {
      callback(...args);
    });
  }
  const fallbackRunWithContext = (fn) => fn();
  function mergeReactiveObjects(target, patchToApply) {
    if (target instanceof Map && patchToApply instanceof Map) {
      patchToApply.forEach((value, key) => target.set(key, value));
    }
    if (target instanceof Set && patchToApply instanceof Set) {
      patchToApply.forEach(target.add, target);
    }
    for (const key in patchToApply) {
      if (!patchToApply.hasOwnProperty(key))
        continue;
      const subPatch = patchToApply[key];
      const targetValue = target[key];
      if (isPlainObject(targetValue) && isPlainObject(subPatch) && target.hasOwnProperty(key) && !vue.isRef(subPatch) && !vue.isReactive(subPatch)) {
        target[key] = mergeReactiveObjects(targetValue, subPatch);
      } else {
        target[key] = subPatch;
      }
    }
    return target;
  }
  const skipHydrateSymbol = Symbol("pinia:skipHydration");
  function shouldHydrate(obj) {
    return !isPlainObject(obj) || !obj.hasOwnProperty(skipHydrateSymbol);
  }
  const { assign } = Object;
  function isComputed(o) {
    return !!(vue.isRef(o) && o.effect);
  }
  function createOptionsStore(id, options, pinia, hot) {
    const { state, actions, getters } = options;
    const initialState = pinia.state.value[id];
    let store;
    function setup() {
      if (!initialState && !hot) {
        {
          pinia.state.value[id] = state ? state() : {};
        }
      }
      const localState = hot ? (
        // use ref() to unwrap refs inside state TODO: check if this is still necessary
        vue.toRefs(vue.ref(state ? state() : {}).value)
      ) : vue.toRefs(pinia.state.value[id]);
      return assign(localState, actions, Object.keys(getters || {}).reduce((computedGetters, name) => {
        if (name in localState) {
          console.warn(`[🍍]: A getter cannot have the same name as another state property. Rename one of them. Found with "${name}" in store "${id}".`);
        }
        computedGetters[name] = vue.markRaw(vue.computed(() => {
          setActivePinia(pinia);
          const store2 = pinia._s.get(id);
          return getters[name].call(store2, store2);
        }));
        return computedGetters;
      }, {}));
    }
    store = createSetupStore(id, setup, options, pinia, hot, true);
    return store;
  }
  function createSetupStore($id, setup, options = {}, pinia, hot, isOptionsStore) {
    let scope;
    const optionsForPlugin = assign({ actions: {} }, options);
    if (!pinia._e.active) {
      throw new Error("Pinia destroyed");
    }
    const $subscribeOptions = {
      deep: true
      // flush: 'post',
    };
    {
      $subscribeOptions.onTrigger = (event) => {
        if (isListening) {
          debuggerEvents = event;
        } else if (isListening == false && !store._hotUpdating) {
          if (Array.isArray(debuggerEvents)) {
            debuggerEvents.push(event);
          } else {
            console.error("🍍 debuggerEvents should be an array. This is most likely an internal Pinia bug.");
          }
        }
      };
    }
    let isListening;
    let isSyncListening;
    let subscriptions = [];
    let actionSubscriptions = [];
    let debuggerEvents;
    const initialState = pinia.state.value[$id];
    if (!isOptionsStore && !initialState && !hot) {
      {
        pinia.state.value[$id] = {};
      }
    }
    const hotState = vue.ref({});
    let activeListener;
    function $patch(partialStateOrMutator) {
      let subscriptionMutation;
      isListening = isSyncListening = false;
      {
        debuggerEvents = [];
      }
      if (typeof partialStateOrMutator === "function") {
        partialStateOrMutator(pinia.state.value[$id]);
        subscriptionMutation = {
          type: MutationType.patchFunction,
          storeId: $id,
          events: debuggerEvents
        };
      } else {
        mergeReactiveObjects(pinia.state.value[$id], partialStateOrMutator);
        subscriptionMutation = {
          type: MutationType.patchObject,
          payload: partialStateOrMutator,
          storeId: $id,
          events: debuggerEvents
        };
      }
      const myListenerId = activeListener = Symbol();
      vue.nextTick().then(() => {
        if (activeListener === myListenerId) {
          isListening = true;
        }
      });
      isSyncListening = true;
      triggerSubscriptions(subscriptions, subscriptionMutation, pinia.state.value[$id]);
    }
    const $reset = isOptionsStore ? function $reset2() {
      const { state } = options;
      const newState = state ? state() : {};
      this.$patch(($state) => {
        assign($state, newState);
      });
    } : (
      /* istanbul ignore next */
      () => {
        throw new Error(`🍍: Store "${$id}" is built using the setup syntax and does not implement $reset().`);
      }
    );
    function $dispose() {
      scope.stop();
      subscriptions = [];
      actionSubscriptions = [];
      pinia._s.delete($id);
    }
    function wrapAction(name, action) {
      return function() {
        setActivePinia(pinia);
        const args = Array.from(arguments);
        const afterCallbackList = [];
        const onErrorCallbackList = [];
        function after(callback) {
          afterCallbackList.push(callback);
        }
        function onError(callback) {
          onErrorCallbackList.push(callback);
        }
        triggerSubscriptions(actionSubscriptions, {
          args,
          name,
          store,
          after,
          onError
        });
        let ret;
        try {
          ret = action.apply(this && this.$id === $id ? this : store, args);
        } catch (error) {
          triggerSubscriptions(onErrorCallbackList, error);
          throw error;
        }
        if (ret instanceof Promise) {
          return ret.then((value) => {
            triggerSubscriptions(afterCallbackList, value);
            return value;
          }).catch((error) => {
            triggerSubscriptions(onErrorCallbackList, error);
            return Promise.reject(error);
          });
        }
        triggerSubscriptions(afterCallbackList, ret);
        return ret;
      };
    }
    const _hmrPayload = /* @__PURE__ */ vue.markRaw({
      actions: {},
      getters: {},
      state: [],
      hotState
    });
    const partialStore = {
      _p: pinia,
      // _s: scope,
      $id,
      $onAction: addSubscription.bind(null, actionSubscriptions),
      $patch,
      $reset,
      $subscribe(callback, options2 = {}) {
        const removeSubscription = addSubscription(subscriptions, callback, options2.detached, () => stopWatcher());
        const stopWatcher = scope.run(() => vue.watch(() => pinia.state.value[$id], (state) => {
          if (options2.flush === "sync" ? isSyncListening : isListening) {
            callback({
              storeId: $id,
              type: MutationType.direct,
              events: debuggerEvents
            }, state);
          }
        }, assign({}, $subscribeOptions, options2)));
        return removeSubscription;
      },
      $dispose
    };
    const store = vue.reactive(assign(
      {
        _hmrPayload,
        _customProperties: vue.markRaw(/* @__PURE__ */ new Set())
        // devtools custom properties
      },
      partialStore
      // must be added later
      // setupStore
    ));
    pinia._s.set($id, store);
    const runWithContext = pinia._a && pinia._a.runWithContext || fallbackRunWithContext;
    const setupStore = runWithContext(() => pinia._e.run(() => (scope = vue.effectScope()).run(setup)));
    for (const key in setupStore) {
      const prop = setupStore[key];
      if (vue.isRef(prop) && !isComputed(prop) || vue.isReactive(prop)) {
        if (hot) {
          set(hotState.value, key, vue.toRef(setupStore, key));
        } else if (!isOptionsStore) {
          if (initialState && shouldHydrate(prop)) {
            if (vue.isRef(prop)) {
              prop.value = initialState[key];
            } else {
              mergeReactiveObjects(prop, initialState[key]);
            }
          }
          {
            pinia.state.value[$id][key] = prop;
          }
        }
        {
          _hmrPayload.state.push(key);
        }
      } else if (typeof prop === "function") {
        const actionValue = hot ? prop : wrapAction(key, prop);
        {
          setupStore[key] = actionValue;
        }
        {
          _hmrPayload.actions[key] = prop;
        }
        optionsForPlugin.actions[key] = prop;
      } else {
        if (isComputed(prop)) {
          _hmrPayload.getters[key] = isOptionsStore ? (
            // @ts-expect-error
            options.getters[key]
          ) : prop;
          if (IS_CLIENT) {
            const getters = setupStore._getters || // @ts-expect-error: same
            (setupStore._getters = vue.markRaw([]));
            getters.push(key);
          }
        }
      }
    }
    {
      assign(store, setupStore);
      assign(vue.toRaw(store), setupStore);
    }
    Object.defineProperty(store, "$state", {
      get: () => hot ? hotState.value : pinia.state.value[$id],
      set: (state) => {
        if (hot) {
          throw new Error("cannot set hotState");
        }
        $patch(($state) => {
          assign($state, state);
        });
      }
    });
    {
      store._hotUpdate = vue.markRaw((newStore) => {
        store._hotUpdating = true;
        newStore._hmrPayload.state.forEach((stateKey) => {
          if (stateKey in store.$state) {
            const newStateTarget = newStore.$state[stateKey];
            const oldStateSource = store.$state[stateKey];
            if (typeof newStateTarget === "object" && isPlainObject(newStateTarget) && isPlainObject(oldStateSource)) {
              patchObject(newStateTarget, oldStateSource);
            } else {
              newStore.$state[stateKey] = oldStateSource;
            }
          }
          set(store, stateKey, vue.toRef(newStore.$state, stateKey));
        });
        Object.keys(store.$state).forEach((stateKey) => {
          if (!(stateKey in newStore.$state)) {
            del(store, stateKey);
          }
        });
        isListening = false;
        isSyncListening = false;
        pinia.state.value[$id] = vue.toRef(newStore._hmrPayload, "hotState");
        isSyncListening = true;
        vue.nextTick().then(() => {
          isListening = true;
        });
        for (const actionName in newStore._hmrPayload.actions) {
          const action = newStore[actionName];
          set(store, actionName, wrapAction(actionName, action));
        }
        for (const getterName in newStore._hmrPayload.getters) {
          const getter = newStore._hmrPayload.getters[getterName];
          const getterValue = isOptionsStore ? (
            // special handling of options api
            vue.computed(() => {
              setActivePinia(pinia);
              return getter.call(store, store);
            })
          ) : getter;
          set(store, getterName, getterValue);
        }
        Object.keys(store._hmrPayload.getters).forEach((key) => {
          if (!(key in newStore._hmrPayload.getters)) {
            del(store, key);
          }
        });
        Object.keys(store._hmrPayload.actions).forEach((key) => {
          if (!(key in newStore._hmrPayload.actions)) {
            del(store, key);
          }
        });
        store._hmrPayload = newStore._hmrPayload;
        store._getters = newStore._getters;
        store._hotUpdating = false;
      });
    }
    if (USE_DEVTOOLS) {
      const nonEnumerable = {
        writable: true,
        configurable: true,
        // avoid warning on devtools trying to display this property
        enumerable: false
      };
      ["_p", "_hmrPayload", "_getters", "_customProperties"].forEach((p) => {
        Object.defineProperty(store, p, assign({ value: store[p] }, nonEnumerable));
      });
    }
    pinia._p.forEach((extender) => {
      if (USE_DEVTOOLS) {
        const extensions = scope.run(() => extender({
          store,
          app: pinia._a,
          pinia,
          options: optionsForPlugin
        }));
        Object.keys(extensions || {}).forEach((key) => store._customProperties.add(key));
        assign(store, extensions);
      } else {
        assign(store, scope.run(() => extender({
          store,
          app: pinia._a,
          pinia,
          options: optionsForPlugin
        })));
      }
    });
    if (store.$state && typeof store.$state === "object" && typeof store.$state.constructor === "function" && !store.$state.constructor.toString().includes("[native code]")) {
      console.warn(`[🍍]: The "state" must be a plain object. It cannot be
	state: () => new MyClass()
Found in store "${store.$id}".`);
    }
    if (initialState && isOptionsStore && options.hydrate) {
      options.hydrate(store.$state, initialState);
    }
    isListening = true;
    isSyncListening = true;
    return store;
  }
  function defineStore(idOrOptions, setup, setupOptions) {
    let id;
    let options;
    const isSetupStore = typeof setup === "function";
    if (typeof idOrOptions === "string") {
      id = idOrOptions;
      options = isSetupStore ? setupOptions : setup;
    } else {
      options = idOrOptions;
      id = idOrOptions.id;
      if (typeof id !== "string") {
        throw new Error(`[🍍]: "defineStore()" must be passed a store id as its first argument.`);
      }
    }
    function useStore(pinia, hot) {
      const hasContext = vue.hasInjectionContext();
      pinia = // in test mode, ignore the argument provided as we can always retrieve a
      // pinia instance with getActivePinia()
      pinia || (hasContext ? vue.inject(piniaSymbol, null) : null);
      if (pinia)
        setActivePinia(pinia);
      if (!activePinia) {
        throw new Error(`[🍍]: "getActivePinia()" was called but there was no active Pinia. Are you trying to use a store before calling "app.use(pinia)"?
See https://pinia.vuejs.org/core-concepts/outside-component-usage.html for help.
This will fail in production.`);
      }
      pinia = activePinia;
      if (!pinia._s.has(id)) {
        if (isSetupStore) {
          createSetupStore(id, setup, options, pinia);
        } else {
          createOptionsStore(id, options, pinia);
        }
        {
          useStore._pinia = pinia;
        }
      }
      const store = pinia._s.get(id);
      if (hot) {
        const hotId = "__hot:" + id;
        const newStore = isSetupStore ? createSetupStore(hotId, setup, options, pinia, true) : createOptionsStore(hotId, assign({}, options), pinia, true);
        hot._hotUpdate(newStore);
        delete pinia.state.value[hotId];
        pinia._s.delete(hotId);
      }
      if (IS_CLIENT) {
        const currentInstance = vue.getCurrentInstance();
        if (currentInstance && currentInstance.proxy && // avoid adding stores that are just built for hot module replacement
        !hot) {
          const vm = currentInstance.proxy;
          const cache = "_pStores" in vm ? vm._pStores : vm._pStores = {};
          cache[id] = store;
        }
      }
      return store;
    }
    useStore.$id = id;
    return useStore;
  }
  const useAuthStore = defineStore("auth", () => {
    const token = vue.ref("");
    const userId = vue.ref("");
    const phone = vue.ref("");
    const setUserInfo = (userData) => {
      token.value = userData.token || "";
      userId.value = userData.userId || "";
      phone.value = userData.phone || "";
      uni.setStorageSync("authInfo", JSON.stringify({
        token: token.value,
        userId: userId.value,
        phone: phone.value
      }));
    };
    const clearUserInfo = () => {
      token.value = "";
      userId.value = "";
      phone.value = "";
      uni.removeStorageSync("authInfo");
    };
    return {
      token,
      userId,
      phone,
      setUserInfo,
      clearUserInfo
    };
  });
  const BASE_URL = "http://115.120.195.253";
  const requestInterceptor = (config) => {
    const authStore = useAuthStore();
    const token = authStore.token;
    if (token) {
      config.header = {
        ...config.header,
        "Authorization": `Bearer ${token}`
      };
    }
    return config;
  };
  function request(config = {}) {
    let {
      url,
      method = "GET",
      data = {},
      header = {}
    } = config;
    url = BASE_URL + url;
    header = {
      "Content-Type": "application/json",
      ...header
      // 允许外部覆盖
    };
    const interceptedConfig = requestInterceptor({
      url,
      method,
      data,
      header
    });
    return new Promise((resolve, reject) => {
      uni.request({
        url: interceptedConfig.url,
        method: interceptedConfig.method,
        data: JSON.stringify(interceptedConfig.data),
        header: interceptedConfig.header,
        success: (res) => {
          if (res.statusCode >= 200 && res.statusCode < 300) {
            resolve(res.data);
          } else {
            reject(res.data);
          }
        },
        fail: (err) => {
          reject({
            errMsg: err.errMsg,
            statusCode: -1
          });
        }
      });
    });
  }
  function apiGetPwd(grantType, phone, password, code) {
    return request({
      url: "/app/auth/login",
      method: "POST",
      data: {
        grantType,
        phone,
        password,
        code
      }
    });
  }
  function apiGetCode(phone, password) {
    return request({
      url: "/app/auth/sendCode",
      method: "POST",
      data: {
        phone
      }
    });
  }
  function apiCompleteProfile(data) {
    return request({
      url: "/app/user/completeProfile",
      method: "POST",
      data
    });
  }
  const _sfc_main$9 = {
    __name: "sms",
    setup(__props, { expose: __expose }) {
      __expose();
      const grantType = vue.ref("phone");
      const phone = vue.ref("");
      const code = vue.ref("");
      const showPhoneError = vue.ref(false);
      const inputClass = vue.ref(false);
      const showKeyboard = vue.ref(false);
      const countdown = vue.ref(0);
      const ToPasswordLogin = () => {
        uni.navigateTo({
          url: "/pages/login/pwd"
        });
      };
      const handleBack = () => {
        uni.navigateBack();
      };
      const isPhoneValid = vue.computed(() => {
        return /^1[3-9]\d{9}$/.test(phone.value);
      });
      const handlePhoneInput = (e) => {
        phone.value = e.detail.value.replace(/[^\d]/g, "");
        showPhoneError.value = isPhoneValid.value;
        inputClass.value = !isPhoneValid.value;
      };
      const isFormValid = vue.computed(() => {
        return phone.value.length === 11 && code.value.length >= 4;
      });
      const getSMSCode = async () => {
        if (countdown.value > 0)
          return;
        let res = await apiGetCode(phone.value);
        if (res.success == true) {
          uni.showToast({
            title: "验证码已发送,注意短信通知",
            icon: "success"
          }), // 开启验证码倒计时
          countdown.value = 60;
          const timer = setInterval(() => {
            if (countdown.value <= 0) {
              clearInterval(timer);
              return;
            }
            countdown.value--;
          }, 1e3);
          formatAppLog("log", "at pages/login/sms.vue:115", "验证码：", res.data);
          code.value = res.data;
        } else {
          uni.showToast({
            title: res.msg,
            icon: "error"
          });
        }
      };
      const handleLogin = async () => {
        let res = await apiGetPwd(grantType.value, phone.value, null, code.value);
        if (res.success == true) {
          const authStore = useAuthStore();
          authStore.setUserInfo({
            token: res.data.token,
            userId: res.data.userId,
            // 确保后端返回userId
            phone: phone.value
            // 使用前端输入或后端返回的phone
          });
          if (res.data.newUser = true) {
            uni.navigateTo({
              url: "/pages/login/register"
            });
          } else {
            uni.navigateTo({
              url: "/pages/petSelection/petSelection"
            });
          }
        } else {
          formatAppLog("log", "at pages/login/sms.vue:150", res);
          uni.showToast({
            title: res.msg,
            icon: "error"
          });
        }
      };
      const __returned__ = { grantType, phone, code, showPhoneError, inputClass, showKeyboard, countdown, ToPasswordLogin, handleBack, isPhoneValid, handlePhoneInput, isFormValid, getSMSCode, handleLogin, ref: vue.ref, computed: vue.computed, get apiGetCode() {
        return apiGetCode;
      }, get apiGetPwd() {
        return apiGetPwd;
      }, get useAuthStore() {
        return useAuthStore;
      } };
      Object.defineProperty(__returned__, "__isScriptSetup", { enumerable: false, value: true });
      return __returned__;
    }
  };
  function _sfc_render$8(_ctx, _cache, $props, $setup, $data, $options) {
    const _component_uni_icons = resolveEasycom(vue.resolveDynamicComponent("uni-icons"), __easycom_0);
    return vue.openBlock(), vue.createElementBlock("view", { class: "login-container" }, [
      vue.createElementVNode("view", { class: "status_bar" }, [
        vue.createCommentVNode(" 这里是状态栏 ")
      ]),
      vue.createCommentVNode(" 自定义导航栏 "),
      vue.createElementVNode("view", { class: "custom-navbar" }, [
        vue.createElementVNode("view", { class: "nav-left" }, [
          vue.createVNode(_component_uni_icons, {
            onClick: $setup.handleBack,
            type: "arrow-left",
            size: "24"
          })
        ]),
        vue.createElementVNode("view", {
          class: "nav-right",
          onClick: $setup.ToPasswordLogin
        }, " 手机号密码登录 ")
      ]),
      vue.createCommentVNode(" 手机号输入 "),
      vue.createElementVNode("view", { class: "phone-box" }, [
        vue.createElementVNode("view", { class: "input-group" }, [
          vue.createElementVNode("view", { class: "prefix" }, "+86"),
          vue.withDirectives(vue.createElementVNode(
            "input",
            {
              "onUpdate:modelValue": _cache[0] || (_cache[0] = ($event) => $setup.phone = $event),
              type: "number",
              placeholder: "请输入手机号",
              "placeholder-class": "placeholder",
              maxlength: "11",
              class: vue.normalizeClass({ error: $setup.inputClass }),
              onInput: $setup.handlePhoneInput,
              onFocus: _cache[1] || (_cache[1] = ($event) => $setup.showKeyboard = true)
            },
            null,
            34
            /* CLASS, NEED_HYDRATION */
          ), [
            [vue.vModelText, $setup.phone]
          ])
        ]),
        vue.createCommentVNode(" 验证码输入组 "),
        vue.createElementVNode("view", { class: "input-group-code" }, [
          vue.createCommentVNode(" 输入框 "),
          vue.withDirectives(vue.createElementVNode(
            "input",
            {
              "onUpdate:modelValue": _cache[2] || (_cache[2] = ($event) => $setup.code = $event),
              type: "number",
              placeholder: "输入验证码",
              "placeholder-class": "placeholder",
              maxlength: "6"
            },
            null,
            512
            /* NEED_PATCH */
          ), [
            [vue.vModelText, $setup.code]
          ]),
          vue.createCommentVNode(" 分隔线 "),
          $setup.showPhoneError ? (vue.openBlock(), vue.createElementBlock("view", {
            key: 0,
            class: "divider"
          })) : vue.createCommentVNode("v-if", true),
          vue.createCommentVNode(" 获取验证码按钮 "),
          $setup.showPhoneError ? (vue.openBlock(), vue.createElementBlock(
            "view",
            {
              key: 1,
              class: "get-code-btn",
              onClick: $setup.getSMSCode
            },
            vue.toDisplayString($setup.countdown > 0 ? `${$setup.countdown}s` : "获取验证码"),
            1
            /* TEXT */
          )) : vue.createCommentVNode("v-if", true)
        ]),
        vue.createCommentVNode(" 登录按钮 "),
        vue.createElementVNode(
          "button",
          {
            class: vue.normalizeClass(["login-btn", { active: $setup.isFormValid }]),
            onClick: $setup.handleLogin
          },
          " 登录 ",
          2
          /* CLASS */
        ),
        vue.createCommentVNode(" 底部提示 "),
        vue.createElementVNode("view", { class: "tip" }, "未注册时，登录将自动注册")
      ])
    ]);
  }
  const PagesLoginSms = /* @__PURE__ */ _export_sfc(_sfc_main$9, [["render", _sfc_render$8], ["__scopeId", "data-v-4e4cd1cc"], ["__file", "E:/ChongAi/ChongAiApp/pages/login/sms.vue"]]);
  const _sfc_main$8 = {
    __name: "pwd",
    setup(__props, { expose: __expose }) {
      __expose();
      const grantType = vue.ref("password");
      const phone = vue.ref("13812345678");
      const password = vue.ref("");
      const showPhoneError = vue.ref(false);
      const showPassword = vue.ref(false);
      const msg = vue.ref("");
      const pwdErr = vue.ref(false);
      const ToSMSLogin = () => {
        uni.navigateTo({
          url: "/pages/login/sms"
        });
      };
      const handleBack = () => {
        uni.navigateBack();
      };
      const handlePhoneInput = (e) => {
        phone.value = e.detail.value.replace(/[^\d]/g, "");
        let value = e.detail.value;
        if (value.length === 1 && value !== "1") {
          phone.value = "";
          return uni.showToast({
            title: "请输入正确的手机号",
            icon: "none"
          });
        }
        showPhoneError.value = phone.value.length > 0 && !isPhoneValid.value;
      };
      const isPhoneValid = vue.computed(() => {
        return /^1[3-9]\d{9}$/.test(phone.value);
      });
      const togglePassword = () => {
        showPassword.value = !showPassword.value;
      };
      const handlePaste = (e) => {
        e.preventDefault();
      };
      const isFormValid = vue.computed(() => {
        return phone.value.length === 11 && password.value.length >= 6;
      });
      const handleLogin = async () => {
        let res = await apiGetPwd(grantType.value, phone.value, password.value);
        if (res.code == 200) {
          uni.navigateTo({
            url: "/pages/petSelection/petSelection"
          });
          const authStore = useAuthStore();
          authStore.setUserInfo({
            token: res.data.token,
            userId: res.data.userId,
            // 确保后端返回userId
            phone: phone.value
            // 使用前端输入或后端返回的phone
          });
        } else if (res.code == 1e3) {
          pwdErr.value = true;
        } else {
          uni.showToast({
            title: res.msg,
            icon: "none"
          });
        }
      };
      const __returned__ = { grantType, phone, password, showPhoneError, showPassword, msg, pwdErr, ToSMSLogin, handleBack, handlePhoneInput, isPhoneValid, togglePassword, handlePaste, isFormValid, handleLogin, ref: vue.ref, computed: vue.computed, get apiGetPwd() {
        return apiGetPwd;
      }, get useAuthStore() {
        return useAuthStore;
      } };
      Object.defineProperty(__returned__, "__isScriptSetup", { enumerable: false, value: true });
      return __returned__;
    }
  };
  function _sfc_render$7(_ctx, _cache, $props, $setup, $data, $options) {
    const _component_uni_icons = resolveEasycom(vue.resolveDynamicComponent("uni-icons"), __easycom_0);
    return vue.openBlock(), vue.createElementBlock("view", { class: "login-container" }, [
      vue.createElementVNode("view", { class: "status_bar" }, [
        vue.createCommentVNode(" 状态栏 ")
      ]),
      vue.createElementVNode("view", { class: "custom-navbar" }, [
        vue.createElementVNode("view", { class: "nav-left" }, [
          vue.createVNode(_component_uni_icons, {
            onClick: $setup.handleBack,
            type: "arrow-left",
            size: "24"
          })
        ]),
        vue.createElementVNode("view", {
          class: "nav-right",
          onClick: $setup.ToSMSLogin
        }, " 手机号验证码登录 ")
      ]),
      vue.createCommentVNode(" 手机号输入 "),
      vue.createElementVNode("view", { class: "phone-box" }, [
        vue.createElementVNode("view", { class: "input-group" }, [
          vue.createElementVNode("view", { class: "prefix" }, "+86"),
          vue.withDirectives(vue.createElementVNode(
            "input",
            {
              "onUpdate:modelValue": _cache[0] || (_cache[0] = ($event) => $setup.phone = $event),
              type: "number",
              placeholder: "请输入手机号",
              "placeholder-class": "placeholder",
              maxlength: "11",
              class: vue.normalizeClass({ error: $setup.showPhoneError }),
              onInput: $setup.handlePhoneInput,
              onFocus: _cache[1] || (_cache[1] = ($event) => _ctx.showKeyboard = true)
            },
            null,
            34
            /* CLASS, NEED_HYDRATION */
          ), [
            [vue.vModelText, $setup.phone]
          ])
        ]),
        vue.createCommentVNode(" 密码输入组 "),
        vue.createElementVNode("view", { class: "input-group-pwd" }, [
          vue.withDirectives(vue.createElementVNode("input", {
            "onUpdate:modelValue": _cache[2] || (_cache[2] = ($event) => $setup.password = $event),
            password: !$setup.showPassword,
            placeholder: "请输入密码",
            "placeholder-class": "placeholder",
            "disable-default-paste": true,
            maxlength: "10"
          }, null, 8, ["password"]), [
            [vue.vModelText, $setup.password]
          ]),
          vue.createCommentVNode(" 眼睛图标按钮 "),
          vue.createElementVNode("view", {
            class: "eye-btn",
            onClick: $setup.togglePassword
          }, [
            vue.createElementVNode("image", {
              src: $setup.showPassword ? "/static/eye.svg" : "/static/eye_close.svg",
              class: "eye-icon"
            }, null, 8, ["src"])
          ])
        ]),
        $setup.pwdErr ? (vue.openBlock(), vue.createElementBlock("view", {
          key: 0,
          class: "pwdErr-text"
        }, " 该账号无法使用密码登录，建议切换其他方式登录。 ")) : vue.createCommentVNode("v-if", true),
        vue.createCommentVNode(" 登录按钮 "),
        vue.createElementVNode(
          "button",
          {
            class: vue.normalizeClass(["login-btn", { active: $setup.isFormValid }]),
            onClick: $setup.handleLogin
          },
          " 登录 ",
          2
          /* CLASS */
        ),
        vue.createElementVNode(
          "view",
          { class: "" },
          vue.toDisplayString($setup.msg),
          1
          /* TEXT */
        )
      ])
    ]);
  }
  const PagesLoginPwd = /* @__PURE__ */ _export_sfc(_sfc_main$8, [["render", _sfc_render$7], ["__scopeId", "data-v-8032d478"], ["__file", "E:/ChongAi/ChongAiApp/pages/login/pwd.vue"]]);
  const _sfc_main$7 = {
    __name: "DatePicker",
    props: {
      modelValue: String,
      defaultDate: {
        type: Date,
        default: () => /* @__PURE__ */ new Date()
      }
    },
    emits: ["update:modelValue", "date-change"],
    setup(__props, { expose: __expose, emit: __emit }) {
      __expose();
      const props = __props;
      const emit = __emit;
      const indicatorStyle = vue.ref("");
      const currentDate = /* @__PURE__ */ new Date();
      const years = vue.ref([]);
      const year = vue.ref(currentDate.getFullYear());
      const months = vue.ref([]);
      const month = vue.ref(currentDate.getMonth() + 1);
      const days = vue.ref([]);
      const day = vue.ref(currentDate.getDate());
      const pickerValue = vue.ref([]);
      const updateMonths = () => {
        months.value = [];
        const maxMonth = year.value === currentDate.getFullYear() ? currentDate.getMonth() + 1 : 12;
        for (let i = 1; i <= maxMonth; i++) {
          months.value.push(i);
        }
        if (month.value > maxMonth) {
          month.value = maxMonth;
        }
      };
      const updateDays = () => {
        days.value = [];
        let maxDay = new Date(year.value, month.value, 0).getDate();
        if (year.value === currentDate.getFullYear() && month.value === currentDate.getMonth() + 1) {
          maxDay = currentDate.getDate();
        }
        for (let i = 1; i <= maxDay; i++) {
          days.value.push(i);
        }
        if (day.value > maxDay) {
          day.value = maxDay;
        }
      };
      const initDatePicker = () => {
        for (let i = 1950; i <= currentDate.getFullYear(); i++) {
          years.value.push(i);
        }
        updateMonths();
        updateDays();
        years.value.indexOf(year.value);
        months.value.indexOf(month.value);
        days.value.indexOf(day.value);
        pickerValue.value = [74, 5, 5];
        indicatorStyle.value = `height: ${uni.upx2px(80)}px;`;
      };
      const handleDateChange = (event) => {
        const [yearIndex, monthIndex, dayIndex] = event.detail.value;
        year.value = years.value[yearIndex];
        if (yearIndex !== pickerValue.value[0]) {
          updateMonths();
          month.value = months.value[Math.min(monthIndex, months.value.length - 1)];
        }
        month.value = months.value[monthIndex];
        updateDays();
        day.value = days.value[Math.min(dayIndex, days.value.length - 1)];
        pickerValue.value = [
          years.value.indexOf(year.value),
          months.value.indexOf(month.value),
          days.value.indexOf(day.value)
        ];
        const selectedDate = `${year.value}-${String(month.value).padStart(2, "0")}-${String(day.value).padStart(2, "0")}`;
        emit("date-change", selectedDate);
      };
      vue.onMounted(() => {
        initDatePicker();
      });
      const __returned__ = { props, emit, indicatorStyle, currentDate, years, year, months, month, days, day, pickerValue, updateMonths, updateDays, initDatePicker, handleDateChange, ref: vue.ref, computed: vue.computed, onMounted: vue.onMounted };
      Object.defineProperty(__returned__, "__isScriptSetup", { enumerable: false, value: true });
      return __returned__;
    }
  };
  function _sfc_render$6(_ctx, _cache, $props, $setup, $data, $options) {
    return vue.openBlock(), vue.createElementBlock("view", { class: "date-picker-wrapper" }, [
      vue.createCommentVNode(" 日期选择器 "),
      vue.createElementVNode("picker-view", {
        class: "picker",
        value: $setup.pickerValue,
        onChange: $setup.handleDateChange,
        "indicator-style": $setup.indicatorStyle
      }, [
        vue.createElementVNode("picker-view-column", null, [
          (vue.openBlock(true), vue.createElementBlock(
            vue.Fragment,
            null,
            vue.renderList($setup.years, (year) => {
              return vue.openBlock(), vue.createElementBlock(
                "view",
                {
                  class: "item",
                  key: year
                },
                vue.toDisplayString(year) + "年",
                1
                /* TEXT */
              );
            }),
            128
            /* KEYED_FRAGMENT */
          ))
        ]),
        vue.createElementVNode("picker-view-column", null, [
          (vue.openBlock(true), vue.createElementBlock(
            vue.Fragment,
            null,
            vue.renderList($setup.months, (month) => {
              return vue.openBlock(), vue.createElementBlock(
                "view",
                {
                  class: "item",
                  key: month
                },
                vue.toDisplayString(month) + "月",
                1
                /* TEXT */
              );
            }),
            128
            /* KEYED_FRAGMENT */
          ))
        ]),
        vue.createElementVNode("picker-view-column", null, [
          (vue.openBlock(true), vue.createElementBlock(
            vue.Fragment,
            null,
            vue.renderList($setup.days, (day) => {
              return vue.openBlock(), vue.createElementBlock(
                "view",
                {
                  class: "item",
                  key: day
                },
                vue.toDisplayString(day) + "日",
                1
                /* TEXT */
              );
            }),
            128
            /* KEYED_FRAGMENT */
          ))
        ])
      ], 40, ["value", "indicator-style"])
    ]);
  }
  const DatePicker = /* @__PURE__ */ _export_sfc(_sfc_main$7, [["render", _sfc_render$6], ["__scopeId", "data-v-9f4f5132"], ["__file", "E:/ChongAi/ChongAiApp/components/DatePicker.vue"]]);
  var BI_RM = "0123456789abcdefghijklmnopqrstuvwxyz";
  function int2char(n) {
    return BI_RM.charAt(n);
  }
  function op_and(x, y) {
    return x & y;
  }
  function op_or(x, y) {
    return x | y;
  }
  function op_xor(x, y) {
    return x ^ y;
  }
  function op_andnot(x, y) {
    return x & ~y;
  }
  function lbit(x) {
    if (x == 0) {
      return -1;
    }
    var r = 0;
    if ((x & 65535) == 0) {
      x >>= 16;
      r += 16;
    }
    if ((x & 255) == 0) {
      x >>= 8;
      r += 8;
    }
    if ((x & 15) == 0) {
      x >>= 4;
      r += 4;
    }
    if ((x & 3) == 0) {
      x >>= 2;
      r += 2;
    }
    if ((x & 1) == 0) {
      ++r;
    }
    return r;
  }
  function cbit(x) {
    var r = 0;
    while (x != 0) {
      x &= x - 1;
      ++r;
    }
    return r;
  }
  var b64map = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
  var b64pad = "=";
  function hex2b64(h) {
    var i;
    var c;
    var ret = "";
    for (i = 0; i + 3 <= h.length; i += 3) {
      c = parseInt(h.substring(i, i + 3), 16);
      ret += b64map.charAt(c >> 6) + b64map.charAt(c & 63);
    }
    if (i + 1 == h.length) {
      c = parseInt(h.substring(i, i + 1), 16);
      ret += b64map.charAt(c << 2);
    } else if (i + 2 == h.length) {
      c = parseInt(h.substring(i, i + 2), 16);
      ret += b64map.charAt(c >> 2) + b64map.charAt((c & 3) << 4);
    }
    while ((ret.length & 3) > 0) {
      ret += b64pad;
    }
    return ret;
  }
  function b64tohex(s) {
    var ret = "";
    var i;
    var k = 0;
    var slop = 0;
    for (i = 0; i < s.length; ++i) {
      if (s.charAt(i) == b64pad) {
        break;
      }
      var v = b64map.indexOf(s.charAt(i));
      if (v < 0) {
        continue;
      }
      if (k == 0) {
        ret += int2char(v >> 2);
        slop = v & 3;
        k = 1;
      } else if (k == 1) {
        ret += int2char(slop << 2 | v >> 4);
        slop = v & 15;
        k = 2;
      } else if (k == 2) {
        ret += int2char(slop);
        ret += int2char(v >> 2);
        slop = v & 3;
        k = 3;
      } else {
        ret += int2char(slop << 2 | v >> 4);
        ret += int2char(v & 15);
        k = 0;
      }
    }
    if (k == 1) {
      ret += int2char(slop << 2);
    }
    return ret;
  }
  var decoder$1;
  var Hex = {
    decode: function(a) {
      var i;
      if (decoder$1 === void 0) {
        var hex = "0123456789ABCDEF";
        var ignore = " \f\n\r	 \u2028\u2029";
        decoder$1 = {};
        for (i = 0; i < 16; ++i) {
          decoder$1[hex.charAt(i)] = i;
        }
        hex = hex.toLowerCase();
        for (i = 10; i < 16; ++i) {
          decoder$1[hex.charAt(i)] = i;
        }
        for (i = 0; i < ignore.length; ++i) {
          decoder$1[ignore.charAt(i)] = -1;
        }
      }
      var out = [];
      var bits = 0;
      var char_count = 0;
      for (i = 0; i < a.length; ++i) {
        var c = a.charAt(i);
        if (c == "=") {
          break;
        }
        c = decoder$1[c];
        if (c == -1) {
          continue;
        }
        if (c === void 0) {
          throw new Error("Illegal character at offset " + i);
        }
        bits |= c;
        if (++char_count >= 2) {
          out[out.length] = bits;
          bits = 0;
          char_count = 0;
        } else {
          bits <<= 4;
        }
      }
      if (char_count) {
        throw new Error("Hex encoding incomplete: 4 bits missing");
      }
      return out;
    }
  };
  var decoder;
  var Base64 = {
    decode: function(a) {
      var i;
      if (decoder === void 0) {
        var b64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        var ignore = "= \f\n\r	 \u2028\u2029";
        decoder = /* @__PURE__ */ Object.create(null);
        for (i = 0; i < 64; ++i) {
          decoder[b64.charAt(i)] = i;
        }
        decoder["-"] = 62;
        decoder["_"] = 63;
        for (i = 0; i < ignore.length; ++i) {
          decoder[ignore.charAt(i)] = -1;
        }
      }
      var out = [];
      var bits = 0;
      var char_count = 0;
      for (i = 0; i < a.length; ++i) {
        var c = a.charAt(i);
        if (c == "=") {
          break;
        }
        c = decoder[c];
        if (c == -1) {
          continue;
        }
        if (c === void 0) {
          throw new Error("Illegal character at offset " + i);
        }
        bits |= c;
        if (++char_count >= 4) {
          out[out.length] = bits >> 16;
          out[out.length] = bits >> 8 & 255;
          out[out.length] = bits & 255;
          bits = 0;
          char_count = 0;
        } else {
          bits <<= 6;
        }
      }
      switch (char_count) {
        case 1:
          throw new Error("Base64 encoding incomplete: at least 2 bits missing");
        case 2:
          out[out.length] = bits >> 10;
          break;
        case 3:
          out[out.length] = bits >> 16;
          out[out.length] = bits >> 8 & 255;
          break;
      }
      return out;
    },
    re: /-----BEGIN [^-]+-----([A-Za-z0-9+\/=\s]+)-----END [^-]+-----|begin-base64[^\n]+\n([A-Za-z0-9+\/=\s]+)====/,
    unarmor: function(a) {
      var m = Base64.re.exec(a);
      if (m) {
        if (m[1]) {
          a = m[1];
        } else if (m[2]) {
          a = m[2];
        } else {
          throw new Error("RegExp out of sync");
        }
      }
      return Base64.decode(a);
    }
  };
  var max = 1e13;
  var Int10 = (
    /** @class */
    function() {
      function Int102(value) {
        this.buf = [+value || 0];
      }
      Int102.prototype.mulAdd = function(m, c) {
        var b = this.buf;
        var l = b.length;
        var i;
        var t2;
        for (i = 0; i < l; ++i) {
          t2 = b[i] * m + c;
          if (t2 < max) {
            c = 0;
          } else {
            c = 0 | t2 / max;
            t2 -= c * max;
          }
          b[i] = t2;
        }
        if (c > 0) {
          b[i] = c;
        }
      };
      Int102.prototype.sub = function(c) {
        var b = this.buf;
        var l = b.length;
        var i;
        var t2;
        for (i = 0; i < l; ++i) {
          t2 = b[i] - c;
          if (t2 < 0) {
            t2 += max;
            c = 1;
          } else {
            c = 0;
          }
          b[i] = t2;
        }
        while (b[b.length - 1] === 0) {
          b.pop();
        }
      };
      Int102.prototype.toString = function(base) {
        if ((base || 10) != 10) {
          throw new Error("only base 10 is supported");
        }
        var b = this.buf;
        var s = b[b.length - 1].toString();
        for (var i = b.length - 2; i >= 0; --i) {
          s += (max + b[i]).toString().substring(1);
        }
        return s;
      };
      Int102.prototype.valueOf = function() {
        var b = this.buf;
        var v = 0;
        for (var i = b.length - 1; i >= 0; --i) {
          v = v * max + b[i];
        }
        return v;
      };
      Int102.prototype.simplify = function() {
        var b = this.buf;
        return b.length == 1 ? b[0] : this;
      };
      return Int102;
    }()
  );
  var ellipsis = "…";
  var reTimeS = /^(\d\d)(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])([01]\d|2[0-3])(?:([0-5]\d)(?:([0-5]\d)(?:[.,](\d{1,3}))?)?)?(Z|[-+](?:[0]\d|1[0-2])([0-5]\d)?)?$/;
  var reTimeL = /^(\d\d\d\d)(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])([01]\d|2[0-3])(?:([0-5]\d)(?:([0-5]\d)(?:[.,](\d{1,3}))?)?)?(Z|[-+](?:[0]\d|1[0-2])([0-5]\d)?)?$/;
  function stringCut(str, len) {
    if (str.length > len) {
      str = str.substring(0, len) + ellipsis;
    }
    return str;
  }
  var Stream = (
    /** @class */
    function() {
      function Stream2(enc, pos) {
        this.hexDigits = "0123456789ABCDEF";
        if (enc instanceof Stream2) {
          this.enc = enc.enc;
          this.pos = enc.pos;
        } else {
          this.enc = enc;
          this.pos = pos;
        }
      }
      Stream2.prototype.get = function(pos) {
        if (pos === void 0) {
          pos = this.pos++;
        }
        if (pos >= this.enc.length) {
          throw new Error("Requesting byte offset ".concat(pos, " on a stream of length ").concat(this.enc.length));
        }
        return "string" === typeof this.enc ? this.enc.charCodeAt(pos) : this.enc[pos];
      };
      Stream2.prototype.hexByte = function(b) {
        return this.hexDigits.charAt(b >> 4 & 15) + this.hexDigits.charAt(b & 15);
      };
      Stream2.prototype.hexDump = function(start, end, raw) {
        var s = "";
        for (var i = start; i < end; ++i) {
          s += this.hexByte(this.get(i));
          if (raw !== true) {
            switch (i & 15) {
              case 7:
                s += "  ";
                break;
              case 15:
                s += "\n";
                break;
              default:
                s += " ";
            }
          }
        }
        return s;
      };
      Stream2.prototype.isASCII = function(start, end) {
        for (var i = start; i < end; ++i) {
          var c = this.get(i);
          if (c < 32 || c > 176) {
            return false;
          }
        }
        return true;
      };
      Stream2.prototype.parseStringISO = function(start, end) {
        var s = "";
        for (var i = start; i < end; ++i) {
          s += String.fromCharCode(this.get(i));
        }
        return s;
      };
      Stream2.prototype.parseStringUTF = function(start, end) {
        var s = "";
        for (var i = start; i < end; ) {
          var c = this.get(i++);
          if (c < 128) {
            s += String.fromCharCode(c);
          } else if (c > 191 && c < 224) {
            s += String.fromCharCode((c & 31) << 6 | this.get(i++) & 63);
          } else {
            s += String.fromCharCode((c & 15) << 12 | (this.get(i++) & 63) << 6 | this.get(i++) & 63);
          }
        }
        return s;
      };
      Stream2.prototype.parseStringBMP = function(start, end) {
        var str = "";
        var hi;
        var lo;
        for (var i = start; i < end; ) {
          hi = this.get(i++);
          lo = this.get(i++);
          str += String.fromCharCode(hi << 8 | lo);
        }
        return str;
      };
      Stream2.prototype.parseTime = function(start, end, shortYear) {
        var s = this.parseStringISO(start, end);
        var m = (shortYear ? reTimeS : reTimeL).exec(s);
        if (!m) {
          return "Unrecognized time: " + s;
        }
        if (shortYear) {
          m[1] = +m[1];
          m[1] += +m[1] < 70 ? 2e3 : 1900;
        }
        s = m[1] + "-" + m[2] + "-" + m[3] + " " + m[4];
        if (m[5]) {
          s += ":" + m[5];
          if (m[6]) {
            s += ":" + m[6];
            if (m[7]) {
              s += "." + m[7];
            }
          }
        }
        if (m[8]) {
          s += " UTC";
          if (m[8] != "Z") {
            s += m[8];
            if (m[9]) {
              s += ":" + m[9];
            }
          }
        }
        return s;
      };
      Stream2.prototype.parseInteger = function(start, end) {
        var v = this.get(start);
        var neg = v > 127;
        var pad = neg ? 255 : 0;
        var len;
        var s = "";
        while (v == pad && ++start < end) {
          v = this.get(start);
        }
        len = end - start;
        if (len === 0) {
          return neg ? -1 : 0;
        }
        if (len > 4) {
          s = v;
          len <<= 3;
          while (((+s ^ pad) & 128) == 0) {
            s = +s << 1;
            --len;
          }
          s = "(" + len + " bit)\n";
        }
        if (neg) {
          v = v - 256;
        }
        var n = new Int10(v);
        for (var i = start + 1; i < end; ++i) {
          n.mulAdd(256, this.get(i));
        }
        return s + n.toString();
      };
      Stream2.prototype.parseBitString = function(start, end, maxLength) {
        var unusedBit = this.get(start);
        var lenBit = (end - start - 1 << 3) - unusedBit;
        var intro = "(" + lenBit + " bit)\n";
        var s = "";
        for (var i = start + 1; i < end; ++i) {
          var b = this.get(i);
          var skip = i == end - 1 ? unusedBit : 0;
          for (var j = 7; j >= skip; --j) {
            s += b >> j & 1 ? "1" : "0";
          }
          if (s.length > maxLength) {
            return intro + stringCut(s, maxLength);
          }
        }
        return intro + s;
      };
      Stream2.prototype.parseOctetString = function(start, end, maxLength) {
        if (this.isASCII(start, end)) {
          return stringCut(this.parseStringISO(start, end), maxLength);
        }
        var len = end - start;
        var s = "(" + len + " byte)\n";
        maxLength /= 2;
        if (len > maxLength) {
          end = start + maxLength;
        }
        for (var i = start; i < end; ++i) {
          s += this.hexByte(this.get(i));
        }
        if (len > maxLength) {
          s += ellipsis;
        }
        return s;
      };
      Stream2.prototype.parseOID = function(start, end, maxLength) {
        var s = "";
        var n = new Int10();
        var bits = 0;
        for (var i = start; i < end; ++i) {
          var v = this.get(i);
          n.mulAdd(128, v & 127);
          bits += 7;
          if (!(v & 128)) {
            if (s === "") {
              n = n.simplify();
              if (n instanceof Int10) {
                n.sub(80);
                s = "2." + n.toString();
              } else {
                var m = n < 80 ? n < 40 ? 0 : 1 : 2;
                s = m + "." + (n - m * 40);
              }
            } else {
              s += "." + n.toString();
            }
            if (s.length > maxLength) {
              return stringCut(s, maxLength);
            }
            n = new Int10();
            bits = 0;
          }
        }
        if (bits > 0) {
          s += ".incomplete";
        }
        return s;
      };
      return Stream2;
    }()
  );
  var ASN1 = (
    /** @class */
    function() {
      function ASN12(stream, header, length, tag, sub) {
        if (!(tag instanceof ASN1Tag)) {
          throw new Error("Invalid tag value.");
        }
        this.stream = stream;
        this.header = header;
        this.length = length;
        this.tag = tag;
        this.sub = sub;
      }
      ASN12.prototype.typeName = function() {
        switch (this.tag.tagClass) {
          case 0:
            switch (this.tag.tagNumber) {
              case 0:
                return "EOC";
              case 1:
                return "BOOLEAN";
              case 2:
                return "INTEGER";
              case 3:
                return "BIT_STRING";
              case 4:
                return "OCTET_STRING";
              case 5:
                return "NULL";
              case 6:
                return "OBJECT_IDENTIFIER";
              case 7:
                return "ObjectDescriptor";
              case 8:
                return "EXTERNAL";
              case 9:
                return "REAL";
              case 10:
                return "ENUMERATED";
              case 11:
                return "EMBEDDED_PDV";
              case 12:
                return "UTF8String";
              case 16:
                return "SEQUENCE";
              case 17:
                return "SET";
              case 18:
                return "NumericString";
              case 19:
                return "PrintableString";
              case 20:
                return "TeletexString";
              case 21:
                return "VideotexString";
              case 22:
                return "IA5String";
              case 23:
                return "UTCTime";
              case 24:
                return "GeneralizedTime";
              case 25:
                return "GraphicString";
              case 26:
                return "VisibleString";
              case 27:
                return "GeneralString";
              case 28:
                return "UniversalString";
              case 30:
                return "BMPString";
            }
            return "Universal_" + this.tag.tagNumber.toString();
          case 1:
            return "Application_" + this.tag.tagNumber.toString();
          case 2:
            return "[" + this.tag.tagNumber.toString() + "]";
          case 3:
            return "Private_" + this.tag.tagNumber.toString();
        }
      };
      ASN12.prototype.content = function(maxLength) {
        if (this.tag === void 0) {
          return null;
        }
        if (maxLength === void 0) {
          maxLength = Infinity;
        }
        var content = this.posContent();
        var len = Math.abs(this.length);
        if (!this.tag.isUniversal()) {
          if (this.sub !== null) {
            return "(" + this.sub.length + " elem)";
          }
          return this.stream.parseOctetString(content, content + len, maxLength);
        }
        switch (this.tag.tagNumber) {
          case 1:
            return this.stream.get(content) === 0 ? "false" : "true";
          case 2:
            return this.stream.parseInteger(content, content + len);
          case 3:
            return this.sub ? "(" + this.sub.length + " elem)" : this.stream.parseBitString(content, content + len, maxLength);
          case 4:
            return this.sub ? "(" + this.sub.length + " elem)" : this.stream.parseOctetString(content, content + len, maxLength);
          case 6:
            return this.stream.parseOID(content, content + len, maxLength);
          case 16:
          case 17:
            if (this.sub !== null) {
              return "(" + this.sub.length + " elem)";
            } else {
              return "(no elem)";
            }
          case 12:
            return stringCut(this.stream.parseStringUTF(content, content + len), maxLength);
          case 18:
          case 19:
          case 20:
          case 21:
          case 22:
          case 26:
            return stringCut(this.stream.parseStringISO(content, content + len), maxLength);
          case 30:
            return stringCut(this.stream.parseStringBMP(content, content + len), maxLength);
          case 23:
          case 24:
            return this.stream.parseTime(content, content + len, this.tag.tagNumber == 23);
        }
        return null;
      };
      ASN12.prototype.toString = function() {
        return this.typeName() + "@" + this.stream.pos + "[header:" + this.header + ",length:" + this.length + ",sub:" + (this.sub === null ? "null" : this.sub.length) + "]";
      };
      ASN12.prototype.toPrettyString = function(indent) {
        if (indent === void 0) {
          indent = "";
        }
        var s = indent + this.typeName() + " @" + this.stream.pos;
        if (this.length >= 0) {
          s += "+";
        }
        s += this.length;
        if (this.tag.tagConstructed) {
          s += " (constructed)";
        } else if (this.tag.isUniversal() && (this.tag.tagNumber == 3 || this.tag.tagNumber == 4) && this.sub !== null) {
          s += " (encapsulates)";
        }
        s += "\n";
        if (this.sub !== null) {
          indent += "  ";
          for (var i = 0, max2 = this.sub.length; i < max2; ++i) {
            s += this.sub[i].toPrettyString(indent);
          }
        }
        return s;
      };
      ASN12.prototype.posStart = function() {
        return this.stream.pos;
      };
      ASN12.prototype.posContent = function() {
        return this.stream.pos + this.header;
      };
      ASN12.prototype.posEnd = function() {
        return this.stream.pos + this.header + Math.abs(this.length);
      };
      ASN12.prototype.toHexString = function() {
        return this.stream.hexDump(this.posStart(), this.posEnd(), true);
      };
      ASN12.decodeLength = function(stream) {
        var buf = stream.get();
        var len = buf & 127;
        if (len == buf) {
          return len;
        }
        if (len > 6) {
          throw new Error("Length over 48 bits not supported at position " + (stream.pos - 1));
        }
        if (len === 0) {
          return null;
        }
        buf = 0;
        for (var i = 0; i < len; ++i) {
          buf = buf * 256 + stream.get();
        }
        return buf;
      };
      ASN12.prototype.getHexStringValue = function() {
        var hexString = this.toHexString();
        var offset = this.header * 2;
        var length = this.length * 2;
        return hexString.substr(offset, length);
      };
      ASN12.decode = function(str) {
        var stream;
        if (!(str instanceof Stream)) {
          stream = new Stream(str, 0);
        } else {
          stream = str;
        }
        var streamStart = new Stream(stream);
        var tag = new ASN1Tag(stream);
        var len = ASN12.decodeLength(stream);
        var start = stream.pos;
        var header = start - streamStart.pos;
        var sub = null;
        var getSub = function() {
          var ret = [];
          if (len !== null) {
            var end = start + len;
            while (stream.pos < end) {
              ret[ret.length] = ASN12.decode(stream);
            }
            if (stream.pos != end) {
              throw new Error("Content size is not correct for container starting at offset " + start);
            }
          } else {
            try {
              for (; ; ) {
                var s = ASN12.decode(stream);
                if (s.tag.isEOC()) {
                  break;
                }
                ret[ret.length] = s;
              }
              len = start - stream.pos;
            } catch (e) {
              throw new Error("Exception while decoding undefined length content: " + e);
            }
          }
          return ret;
        };
        if (tag.tagConstructed) {
          sub = getSub();
        } else if (tag.isUniversal() && (tag.tagNumber == 3 || tag.tagNumber == 4)) {
          try {
            if (tag.tagNumber == 3) {
              if (stream.get() != 0) {
                throw new Error("BIT STRINGs with unused bits cannot encapsulate.");
              }
            }
            sub = getSub();
            for (var i = 0; i < sub.length; ++i) {
              if (sub[i].tag.isEOC()) {
                throw new Error("EOC is not supposed to be actual content.");
              }
            }
          } catch (e) {
            sub = null;
          }
        }
        if (sub === null) {
          if (len === null) {
            throw new Error("We can't skip over an invalid tag with undefined length at offset " + start);
          }
          stream.pos = start + Math.abs(len);
        }
        return new ASN12(streamStart, header, len, tag, sub);
      };
      return ASN12;
    }()
  );
  var ASN1Tag = (
    /** @class */
    function() {
      function ASN1Tag2(stream) {
        var buf = stream.get();
        this.tagClass = buf >> 6;
        this.tagConstructed = (buf & 32) !== 0;
        this.tagNumber = buf & 31;
        if (this.tagNumber == 31) {
          var n = new Int10();
          do {
            buf = stream.get();
            n.mulAdd(128, buf & 127);
          } while (buf & 128);
          this.tagNumber = n.simplify();
        }
      }
      ASN1Tag2.prototype.isUniversal = function() {
        return this.tagClass === 0;
      };
      ASN1Tag2.prototype.isEOC = function() {
        return this.tagClass === 0 && this.tagNumber === 0;
      };
      return ASN1Tag2;
    }()
  );
  var dbits;
  var canary = 244837814094590;
  var j_lm = (canary & 16777215) == 15715070;
  var lowprimes = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997];
  var lplim = (1 << 26) / lowprimes[lowprimes.length - 1];
  var BigInteger = (
    /** @class */
    function() {
      function BigInteger2(a, b, c) {
        if (a != null) {
          if ("number" == typeof a) {
            this.fromNumber(a, b, c);
          } else if (b == null && "string" != typeof a) {
            this.fromString(a, 256);
          } else {
            this.fromString(a, b);
          }
        }
      }
      BigInteger2.prototype.toString = function(b) {
        if (this.s < 0) {
          return "-" + this.negate().toString(b);
        }
        var k;
        if (b == 16) {
          k = 4;
        } else if (b == 8) {
          k = 3;
        } else if (b == 2) {
          k = 1;
        } else if (b == 32) {
          k = 5;
        } else if (b == 4) {
          k = 2;
        } else {
          return this.toRadix(b);
        }
        var km = (1 << k) - 1;
        var d;
        var m = false;
        var r = "";
        var i = this.t;
        var p = this.DB - i * this.DB % k;
        if (i-- > 0) {
          if (p < this.DB && (d = this[i] >> p) > 0) {
            m = true;
            r = int2char(d);
          }
          while (i >= 0) {
            if (p < k) {
              d = (this[i] & (1 << p) - 1) << k - p;
              d |= this[--i] >> (p += this.DB - k);
            } else {
              d = this[i] >> (p -= k) & km;
              if (p <= 0) {
                p += this.DB;
                --i;
              }
            }
            if (d > 0) {
              m = true;
            }
            if (m) {
              r += int2char(d);
            }
          }
        }
        return m ? r : "0";
      };
      BigInteger2.prototype.negate = function() {
        var r = nbi();
        BigInteger2.ZERO.subTo(this, r);
        return r;
      };
      BigInteger2.prototype.abs = function() {
        return this.s < 0 ? this.negate() : this;
      };
      BigInteger2.prototype.compareTo = function(a) {
        var r = this.s - a.s;
        if (r != 0) {
          return r;
        }
        var i = this.t;
        r = i - a.t;
        if (r != 0) {
          return this.s < 0 ? -r : r;
        }
        while (--i >= 0) {
          if ((r = this[i] - a[i]) != 0) {
            return r;
          }
        }
        return 0;
      };
      BigInteger2.prototype.bitLength = function() {
        if (this.t <= 0) {
          return 0;
        }
        return this.DB * (this.t - 1) + nbits(this[this.t - 1] ^ this.s & this.DM);
      };
      BigInteger2.prototype.mod = function(a) {
        var r = nbi();
        this.abs().divRemTo(a, null, r);
        if (this.s < 0 && r.compareTo(BigInteger2.ZERO) > 0) {
          a.subTo(r, r);
        }
        return r;
      };
      BigInteger2.prototype.modPowInt = function(e, m) {
        var z2;
        if (e < 256 || m.isEven()) {
          z2 = new Classic(m);
        } else {
          z2 = new Montgomery(m);
        }
        return this.exp(e, z2);
      };
      BigInteger2.prototype.clone = function() {
        var r = nbi();
        this.copyTo(r);
        return r;
      };
      BigInteger2.prototype.intValue = function() {
        if (this.s < 0) {
          if (this.t == 1) {
            return this[0] - this.DV;
          } else if (this.t == 0) {
            return -1;
          }
        } else if (this.t == 1) {
          return this[0];
        } else if (this.t == 0) {
          return 0;
        }
        return (this[1] & (1 << 32 - this.DB) - 1) << this.DB | this[0];
      };
      BigInteger2.prototype.byteValue = function() {
        return this.t == 0 ? this.s : this[0] << 24 >> 24;
      };
      BigInteger2.prototype.shortValue = function() {
        return this.t == 0 ? this.s : this[0] << 16 >> 16;
      };
      BigInteger2.prototype.signum = function() {
        if (this.s < 0) {
          return -1;
        } else if (this.t <= 0 || this.t == 1 && this[0] <= 0) {
          return 0;
        } else {
          return 1;
        }
      };
      BigInteger2.prototype.toByteArray = function() {
        var i = this.t;
        var r = [];
        r[0] = this.s;
        var p = this.DB - i * this.DB % 8;
        var d;
        var k = 0;
        if (i-- > 0) {
          if (p < this.DB && (d = this[i] >> p) != (this.s & this.DM) >> p) {
            r[k++] = d | this.s << this.DB - p;
          }
          while (i >= 0) {
            if (p < 8) {
              d = (this[i] & (1 << p) - 1) << 8 - p;
              d |= this[--i] >> (p += this.DB - 8);
            } else {
              d = this[i] >> (p -= 8) & 255;
              if (p <= 0) {
                p += this.DB;
                --i;
              }
            }
            if ((d & 128) != 0) {
              d |= -256;
            }
            if (k == 0 && (this.s & 128) != (d & 128)) {
              ++k;
            }
            if (k > 0 || d != this.s) {
              r[k++] = d;
            }
          }
        }
        return r;
      };
      BigInteger2.prototype.equals = function(a) {
        return this.compareTo(a) == 0;
      };
      BigInteger2.prototype.min = function(a) {
        return this.compareTo(a) < 0 ? this : a;
      };
      BigInteger2.prototype.max = function(a) {
        return this.compareTo(a) > 0 ? this : a;
      };
      BigInteger2.prototype.and = function(a) {
        var r = nbi();
        this.bitwiseTo(a, op_and, r);
        return r;
      };
      BigInteger2.prototype.or = function(a) {
        var r = nbi();
        this.bitwiseTo(a, op_or, r);
        return r;
      };
      BigInteger2.prototype.xor = function(a) {
        var r = nbi();
        this.bitwiseTo(a, op_xor, r);
        return r;
      };
      BigInteger2.prototype.andNot = function(a) {
        var r = nbi();
        this.bitwiseTo(a, op_andnot, r);
        return r;
      };
      BigInteger2.prototype.not = function() {
        var r = nbi();
        for (var i = 0; i < this.t; ++i) {
          r[i] = this.DM & ~this[i];
        }
        r.t = this.t;
        r.s = ~this.s;
        return r;
      };
      BigInteger2.prototype.shiftLeft = function(n) {
        var r = nbi();
        if (n < 0) {
          this.rShiftTo(-n, r);
        } else {
          this.lShiftTo(n, r);
        }
        return r;
      };
      BigInteger2.prototype.shiftRight = function(n) {
        var r = nbi();
        if (n < 0) {
          this.lShiftTo(-n, r);
        } else {
          this.rShiftTo(n, r);
        }
        return r;
      };
      BigInteger2.prototype.getLowestSetBit = function() {
        for (var i = 0; i < this.t; ++i) {
          if (this[i] != 0) {
            return i * this.DB + lbit(this[i]);
          }
        }
        if (this.s < 0) {
          return this.t * this.DB;
        }
        return -1;
      };
      BigInteger2.prototype.bitCount = function() {
        var r = 0;
        var x = this.s & this.DM;
        for (var i = 0; i < this.t; ++i) {
          r += cbit(this[i] ^ x);
        }
        return r;
      };
      BigInteger2.prototype.testBit = function(n) {
        var j = Math.floor(n / this.DB);
        if (j >= this.t) {
          return this.s != 0;
        }
        return (this[j] & 1 << n % this.DB) != 0;
      };
      BigInteger2.prototype.setBit = function(n) {
        return this.changeBit(n, op_or);
      };
      BigInteger2.prototype.clearBit = function(n) {
        return this.changeBit(n, op_andnot);
      };
      BigInteger2.prototype.flipBit = function(n) {
        return this.changeBit(n, op_xor);
      };
      BigInteger2.prototype.add = function(a) {
        var r = nbi();
        this.addTo(a, r);
        return r;
      };
      BigInteger2.prototype.subtract = function(a) {
        var r = nbi();
        this.subTo(a, r);
        return r;
      };
      BigInteger2.prototype.multiply = function(a) {
        var r = nbi();
        this.multiplyTo(a, r);
        return r;
      };
      BigInteger2.prototype.divide = function(a) {
        var r = nbi();
        this.divRemTo(a, r, null);
        return r;
      };
      BigInteger2.prototype.remainder = function(a) {
        var r = nbi();
        this.divRemTo(a, null, r);
        return r;
      };
      BigInteger2.prototype.divideAndRemainder = function(a) {
        var q = nbi();
        var r = nbi();
        this.divRemTo(a, q, r);
        return [q, r];
      };
      BigInteger2.prototype.modPow = function(e, m) {
        var i = e.bitLength();
        var k;
        var r = nbv(1);
        var z2;
        if (i <= 0) {
          return r;
        } else if (i < 18) {
          k = 1;
        } else if (i < 48) {
          k = 3;
        } else if (i < 144) {
          k = 4;
        } else if (i < 768) {
          k = 5;
        } else {
          k = 6;
        }
        if (i < 8) {
          z2 = new Classic(m);
        } else if (m.isEven()) {
          z2 = new Barrett(m);
        } else {
          z2 = new Montgomery(m);
        }
        var g = [];
        var n = 3;
        var k1 = k - 1;
        var km = (1 << k) - 1;
        g[1] = z2.convert(this);
        if (k > 1) {
          var g2 = nbi();
          z2.sqrTo(g[1], g2);
          while (n <= km) {
            g[n] = nbi();
            z2.mulTo(g2, g[n - 2], g[n]);
            n += 2;
          }
        }
        var j = e.t - 1;
        var w;
        var is1 = true;
        var r2 = nbi();
        var t2;
        i = nbits(e[j]) - 1;
        while (j >= 0) {
          if (i >= k1) {
            w = e[j] >> i - k1 & km;
          } else {
            w = (e[j] & (1 << i + 1) - 1) << k1 - i;
            if (j > 0) {
              w |= e[j - 1] >> this.DB + i - k1;
            }
          }
          n = k;
          while ((w & 1) == 0) {
            w >>= 1;
            --n;
          }
          if ((i -= n) < 0) {
            i += this.DB;
            --j;
          }
          if (is1) {
            g[w].copyTo(r);
            is1 = false;
          } else {
            while (n > 1) {
              z2.sqrTo(r, r2);
              z2.sqrTo(r2, r);
              n -= 2;
            }
            if (n > 0) {
              z2.sqrTo(r, r2);
            } else {
              t2 = r;
              r = r2;
              r2 = t2;
            }
            z2.mulTo(r2, g[w], r);
          }
          while (j >= 0 && (e[j] & 1 << i) == 0) {
            z2.sqrTo(r, r2);
            t2 = r;
            r = r2;
            r2 = t2;
            if (--i < 0) {
              i = this.DB - 1;
              --j;
            }
          }
        }
        return z2.revert(r);
      };
      BigInteger2.prototype.modInverse = function(m) {
        var ac = m.isEven();
        if (this.isEven() && ac || m.signum() == 0) {
          return BigInteger2.ZERO;
        }
        var u = m.clone();
        var v = this.clone();
        var a = nbv(1);
        var b = nbv(0);
        var c = nbv(0);
        var d = nbv(1);
        while (u.signum() != 0) {
          while (u.isEven()) {
            u.rShiftTo(1, u);
            if (ac) {
              if (!a.isEven() || !b.isEven()) {
                a.addTo(this, a);
                b.subTo(m, b);
              }
              a.rShiftTo(1, a);
            } else if (!b.isEven()) {
              b.subTo(m, b);
            }
            b.rShiftTo(1, b);
          }
          while (v.isEven()) {
            v.rShiftTo(1, v);
            if (ac) {
              if (!c.isEven() || !d.isEven()) {
                c.addTo(this, c);
                d.subTo(m, d);
              }
              c.rShiftTo(1, c);
            } else if (!d.isEven()) {
              d.subTo(m, d);
            }
            d.rShiftTo(1, d);
          }
          if (u.compareTo(v) >= 0) {
            u.subTo(v, u);
            if (ac) {
              a.subTo(c, a);
            }
            b.subTo(d, b);
          } else {
            v.subTo(u, v);
            if (ac) {
              c.subTo(a, c);
            }
            d.subTo(b, d);
          }
        }
        if (v.compareTo(BigInteger2.ONE) != 0) {
          return BigInteger2.ZERO;
        }
        if (d.compareTo(m) >= 0) {
          return d.subtract(m);
        }
        if (d.signum() < 0) {
          d.addTo(m, d);
        } else {
          return d;
        }
        if (d.signum() < 0) {
          return d.add(m);
        } else {
          return d;
        }
      };
      BigInteger2.prototype.pow = function(e) {
        return this.exp(e, new NullExp());
      };
      BigInteger2.prototype.gcd = function(a) {
        var x = this.s < 0 ? this.negate() : this.clone();
        var y = a.s < 0 ? a.negate() : a.clone();
        if (x.compareTo(y) < 0) {
          var t2 = x;
          x = y;
          y = t2;
        }
        var i = x.getLowestSetBit();
        var g = y.getLowestSetBit();
        if (g < 0) {
          return x;
        }
        if (i < g) {
          g = i;
        }
        if (g > 0) {
          x.rShiftTo(g, x);
          y.rShiftTo(g, y);
        }
        while (x.signum() > 0) {
          if ((i = x.getLowestSetBit()) > 0) {
            x.rShiftTo(i, x);
          }
          if ((i = y.getLowestSetBit()) > 0) {
            y.rShiftTo(i, y);
          }
          if (x.compareTo(y) >= 0) {
            x.subTo(y, x);
            x.rShiftTo(1, x);
          } else {
            y.subTo(x, y);
            y.rShiftTo(1, y);
          }
        }
        if (g > 0) {
          y.lShiftTo(g, y);
        }
        return y;
      };
      BigInteger2.prototype.isProbablePrime = function(t2) {
        var i;
        var x = this.abs();
        if (x.t == 1 && x[0] <= lowprimes[lowprimes.length - 1]) {
          for (i = 0; i < lowprimes.length; ++i) {
            if (x[0] == lowprimes[i]) {
              return true;
            }
          }
          return false;
        }
        if (x.isEven()) {
          return false;
        }
        i = 1;
        while (i < lowprimes.length) {
          var m = lowprimes[i];
          var j = i + 1;
          while (j < lowprimes.length && m < lplim) {
            m *= lowprimes[j++];
          }
          m = x.modInt(m);
          while (i < j) {
            if (m % lowprimes[i++] == 0) {
              return false;
            }
          }
        }
        return x.millerRabin(t2);
      };
      BigInteger2.prototype.copyTo = function(r) {
        for (var i = this.t - 1; i >= 0; --i) {
          r[i] = this[i];
        }
        r.t = this.t;
        r.s = this.s;
      };
      BigInteger2.prototype.fromInt = function(x) {
        this.t = 1;
        this.s = x < 0 ? -1 : 0;
        if (x > 0) {
          this[0] = x;
        } else if (x < -1) {
          this[0] = x + this.DV;
        } else {
          this.t = 0;
        }
      };
      BigInteger2.prototype.fromString = function(s, b) {
        var k;
        if (b == 16) {
          k = 4;
        } else if (b == 8) {
          k = 3;
        } else if (b == 256) {
          k = 8;
        } else if (b == 2) {
          k = 1;
        } else if (b == 32) {
          k = 5;
        } else if (b == 4) {
          k = 2;
        } else {
          this.fromRadix(s, b);
          return;
        }
        this.t = 0;
        this.s = 0;
        var i = s.length;
        var mi = false;
        var sh = 0;
        while (--i >= 0) {
          var x = k == 8 ? +s[i] & 255 : intAt(s, i);
          if (x < 0) {
            if (s.charAt(i) == "-") {
              mi = true;
            }
            continue;
          }
          mi = false;
          if (sh == 0) {
            this[this.t++] = x;
          } else if (sh + k > this.DB) {
            this[this.t - 1] |= (x & (1 << this.DB - sh) - 1) << sh;
            this[this.t++] = x >> this.DB - sh;
          } else {
            this[this.t - 1] |= x << sh;
          }
          sh += k;
          if (sh >= this.DB) {
            sh -= this.DB;
          }
        }
        if (k == 8 && (+s[0] & 128) != 0) {
          this.s = -1;
          if (sh > 0) {
            this[this.t - 1] |= (1 << this.DB - sh) - 1 << sh;
          }
        }
        this.clamp();
        if (mi) {
          BigInteger2.ZERO.subTo(this, this);
        }
      };
      BigInteger2.prototype.clamp = function() {
        var c = this.s & this.DM;
        while (this.t > 0 && this[this.t - 1] == c) {
          --this.t;
        }
      };
      BigInteger2.prototype.dlShiftTo = function(n, r) {
        var i;
        for (i = this.t - 1; i >= 0; --i) {
          r[i + n] = this[i];
        }
        for (i = n - 1; i >= 0; --i) {
          r[i] = 0;
        }
        r.t = this.t + n;
        r.s = this.s;
      };
      BigInteger2.prototype.drShiftTo = function(n, r) {
        for (var i = n; i < this.t; ++i) {
          r[i - n] = this[i];
        }
        r.t = Math.max(this.t - n, 0);
        r.s = this.s;
      };
      BigInteger2.prototype.lShiftTo = function(n, r) {
        var bs = n % this.DB;
        var cbs = this.DB - bs;
        var bm = (1 << cbs) - 1;
        var ds = Math.floor(n / this.DB);
        var c = this.s << bs & this.DM;
        for (var i = this.t - 1; i >= 0; --i) {
          r[i + ds + 1] = this[i] >> cbs | c;
          c = (this[i] & bm) << bs;
        }
        for (var i = ds - 1; i >= 0; --i) {
          r[i] = 0;
        }
        r[ds] = c;
        r.t = this.t + ds + 1;
        r.s = this.s;
        r.clamp();
      };
      BigInteger2.prototype.rShiftTo = function(n, r) {
        r.s = this.s;
        var ds = Math.floor(n / this.DB);
        if (ds >= this.t) {
          r.t = 0;
          return;
        }
        var bs = n % this.DB;
        var cbs = this.DB - bs;
        var bm = (1 << bs) - 1;
        r[0] = this[ds] >> bs;
        for (var i = ds + 1; i < this.t; ++i) {
          r[i - ds - 1] |= (this[i] & bm) << cbs;
          r[i - ds] = this[i] >> bs;
        }
        if (bs > 0) {
          r[this.t - ds - 1] |= (this.s & bm) << cbs;
        }
        r.t = this.t - ds;
        r.clamp();
      };
      BigInteger2.prototype.subTo = function(a, r) {
        var i = 0;
        var c = 0;
        var m = Math.min(a.t, this.t);
        while (i < m) {
          c += this[i] - a[i];
          r[i++] = c & this.DM;
          c >>= this.DB;
        }
        if (a.t < this.t) {
          c -= a.s;
          while (i < this.t) {
            c += this[i];
            r[i++] = c & this.DM;
            c >>= this.DB;
          }
          c += this.s;
        } else {
          c += this.s;
          while (i < a.t) {
            c -= a[i];
            r[i++] = c & this.DM;
            c >>= this.DB;
          }
          c -= a.s;
        }
        r.s = c < 0 ? -1 : 0;
        if (c < -1) {
          r[i++] = this.DV + c;
        } else if (c > 0) {
          r[i++] = c;
        }
        r.t = i;
        r.clamp();
      };
      BigInteger2.prototype.multiplyTo = function(a, r) {
        var x = this.abs();
        var y = a.abs();
        var i = x.t;
        r.t = i + y.t;
        while (--i >= 0) {
          r[i] = 0;
        }
        for (i = 0; i < y.t; ++i) {
          r[i + x.t] = x.am(0, y[i], r, i, 0, x.t);
        }
        r.s = 0;
        r.clamp();
        if (this.s != a.s) {
          BigInteger2.ZERO.subTo(r, r);
        }
      };
      BigInteger2.prototype.squareTo = function(r) {
        var x = this.abs();
        var i = r.t = 2 * x.t;
        while (--i >= 0) {
          r[i] = 0;
        }
        for (i = 0; i < x.t - 1; ++i) {
          var c = x.am(i, x[i], r, 2 * i, 0, 1);
          if ((r[i + x.t] += x.am(i + 1, 2 * x[i], r, 2 * i + 1, c, x.t - i - 1)) >= x.DV) {
            r[i + x.t] -= x.DV;
            r[i + x.t + 1] = 1;
          }
        }
        if (r.t > 0) {
          r[r.t - 1] += x.am(i, x[i], r, 2 * i, 0, 1);
        }
        r.s = 0;
        r.clamp();
      };
      BigInteger2.prototype.divRemTo = function(m, q, r) {
        var pm = m.abs();
        if (pm.t <= 0) {
          return;
        }
        var pt = this.abs();
        if (pt.t < pm.t) {
          if (q != null) {
            q.fromInt(0);
          }
          if (r != null) {
            this.copyTo(r);
          }
          return;
        }
        if (r == null) {
          r = nbi();
        }
        var y = nbi();
        var ts = this.s;
        var ms = m.s;
        var nsh = this.DB - nbits(pm[pm.t - 1]);
        if (nsh > 0) {
          pm.lShiftTo(nsh, y);
          pt.lShiftTo(nsh, r);
        } else {
          pm.copyTo(y);
          pt.copyTo(r);
        }
        var ys = y.t;
        var y0 = y[ys - 1];
        if (y0 == 0) {
          return;
        }
        var yt = y0 * (1 << this.F1) + (ys > 1 ? y[ys - 2] >> this.F2 : 0);
        var d1 = this.FV / yt;
        var d2 = (1 << this.F1) / yt;
        var e = 1 << this.F2;
        var i = r.t;
        var j = i - ys;
        var t2 = q == null ? nbi() : q;
        y.dlShiftTo(j, t2);
        if (r.compareTo(t2) >= 0) {
          r[r.t++] = 1;
          r.subTo(t2, r);
        }
        BigInteger2.ONE.dlShiftTo(ys, t2);
        t2.subTo(y, y);
        while (y.t < ys) {
          y[y.t++] = 0;
        }
        while (--j >= 0) {
          var qd = r[--i] == y0 ? this.DM : Math.floor(r[i] * d1 + (r[i - 1] + e) * d2);
          if ((r[i] += y.am(0, qd, r, j, 0, ys)) < qd) {
            y.dlShiftTo(j, t2);
            r.subTo(t2, r);
            while (r[i] < --qd) {
              r.subTo(t2, r);
            }
          }
        }
        if (q != null) {
          r.drShiftTo(ys, q);
          if (ts != ms) {
            BigInteger2.ZERO.subTo(q, q);
          }
        }
        r.t = ys;
        r.clamp();
        if (nsh > 0) {
          r.rShiftTo(nsh, r);
        }
        if (ts < 0) {
          BigInteger2.ZERO.subTo(r, r);
        }
      };
      BigInteger2.prototype.invDigit = function() {
        if (this.t < 1) {
          return 0;
        }
        var x = this[0];
        if ((x & 1) == 0) {
          return 0;
        }
        var y = x & 3;
        y = y * (2 - (x & 15) * y) & 15;
        y = y * (2 - (x & 255) * y) & 255;
        y = y * (2 - ((x & 65535) * y & 65535)) & 65535;
        y = y * (2 - x * y % this.DV) % this.DV;
        return y > 0 ? this.DV - y : -y;
      };
      BigInteger2.prototype.isEven = function() {
        return (this.t > 0 ? this[0] & 1 : this.s) == 0;
      };
      BigInteger2.prototype.exp = function(e, z2) {
        if (e > 4294967295 || e < 1) {
          return BigInteger2.ONE;
        }
        var r = nbi();
        var r2 = nbi();
        var g = z2.convert(this);
        var i = nbits(e) - 1;
        g.copyTo(r);
        while (--i >= 0) {
          z2.sqrTo(r, r2);
          if ((e & 1 << i) > 0) {
            z2.mulTo(r2, g, r);
          } else {
            var t2 = r;
            r = r2;
            r2 = t2;
          }
        }
        return z2.revert(r);
      };
      BigInteger2.prototype.chunkSize = function(r) {
        return Math.floor(Math.LN2 * this.DB / Math.log(r));
      };
      BigInteger2.prototype.toRadix = function(b) {
        if (b == null) {
          b = 10;
        }
        if (this.signum() == 0 || b < 2 || b > 36) {
          return "0";
        }
        var cs = this.chunkSize(b);
        var a = Math.pow(b, cs);
        var d = nbv(a);
        var y = nbi();
        var z2 = nbi();
        var r = "";
        this.divRemTo(d, y, z2);
        while (y.signum() > 0) {
          r = (a + z2.intValue()).toString(b).substr(1) + r;
          y.divRemTo(d, y, z2);
        }
        return z2.intValue().toString(b) + r;
      };
      BigInteger2.prototype.fromRadix = function(s, b) {
        this.fromInt(0);
        if (b == null) {
          b = 10;
        }
        var cs = this.chunkSize(b);
        var d = Math.pow(b, cs);
        var mi = false;
        var j = 0;
        var w = 0;
        for (var i = 0; i < s.length; ++i) {
          var x = intAt(s, i);
          if (x < 0) {
            if (s.charAt(i) == "-" && this.signum() == 0) {
              mi = true;
            }
            continue;
          }
          w = b * w + x;
          if (++j >= cs) {
            this.dMultiply(d);
            this.dAddOffset(w, 0);
            j = 0;
            w = 0;
          }
        }
        if (j > 0) {
          this.dMultiply(Math.pow(b, j));
          this.dAddOffset(w, 0);
        }
        if (mi) {
          BigInteger2.ZERO.subTo(this, this);
        }
      };
      BigInteger2.prototype.fromNumber = function(a, b, c) {
        if ("number" == typeof b) {
          if (a < 2) {
            this.fromInt(1);
          } else {
            this.fromNumber(a, c);
            if (!this.testBit(a - 1)) {
              this.bitwiseTo(BigInteger2.ONE.shiftLeft(a - 1), op_or, this);
            }
            if (this.isEven()) {
              this.dAddOffset(1, 0);
            }
            while (!this.isProbablePrime(b)) {
              this.dAddOffset(2, 0);
              if (this.bitLength() > a) {
                this.subTo(BigInteger2.ONE.shiftLeft(a - 1), this);
              }
            }
          }
        } else {
          var x = [];
          var t2 = a & 7;
          x.length = (a >> 3) + 1;
          b.nextBytes(x);
          if (t2 > 0) {
            x[0] &= (1 << t2) - 1;
          } else {
            x[0] = 0;
          }
          this.fromString(x, 256);
        }
      };
      BigInteger2.prototype.bitwiseTo = function(a, op, r) {
        var i;
        var f;
        var m = Math.min(a.t, this.t);
        for (i = 0; i < m; ++i) {
          r[i] = op(this[i], a[i]);
        }
        if (a.t < this.t) {
          f = a.s & this.DM;
          for (i = m; i < this.t; ++i) {
            r[i] = op(this[i], f);
          }
          r.t = this.t;
        } else {
          f = this.s & this.DM;
          for (i = m; i < a.t; ++i) {
            r[i] = op(f, a[i]);
          }
          r.t = a.t;
        }
        r.s = op(this.s, a.s);
        r.clamp();
      };
      BigInteger2.prototype.changeBit = function(n, op) {
        var r = BigInteger2.ONE.shiftLeft(n);
        this.bitwiseTo(r, op, r);
        return r;
      };
      BigInteger2.prototype.addTo = function(a, r) {
        var i = 0;
        var c = 0;
        var m = Math.min(a.t, this.t);
        while (i < m) {
          c += this[i] + a[i];
          r[i++] = c & this.DM;
          c >>= this.DB;
        }
        if (a.t < this.t) {
          c += a.s;
          while (i < this.t) {
            c += this[i];
            r[i++] = c & this.DM;
            c >>= this.DB;
          }
          c += this.s;
        } else {
          c += this.s;
          while (i < a.t) {
            c += a[i];
            r[i++] = c & this.DM;
            c >>= this.DB;
          }
          c += a.s;
        }
        r.s = c < 0 ? -1 : 0;
        if (c > 0) {
          r[i++] = c;
        } else if (c < -1) {
          r[i++] = this.DV + c;
        }
        r.t = i;
        r.clamp();
      };
      BigInteger2.prototype.dMultiply = function(n) {
        this[this.t] = this.am(0, n - 1, this, 0, 0, this.t);
        ++this.t;
        this.clamp();
      };
      BigInteger2.prototype.dAddOffset = function(n, w) {
        if (n == 0) {
          return;
        }
        while (this.t <= w) {
          this[this.t++] = 0;
        }
        this[w] += n;
        while (this[w] >= this.DV) {
          this[w] -= this.DV;
          if (++w >= this.t) {
            this[this.t++] = 0;
          }
          ++this[w];
        }
      };
      BigInteger2.prototype.multiplyLowerTo = function(a, n, r) {
        var i = Math.min(this.t + a.t, n);
        r.s = 0;
        r.t = i;
        while (i > 0) {
          r[--i] = 0;
        }
        for (var j = r.t - this.t; i < j; ++i) {
          r[i + this.t] = this.am(0, a[i], r, i, 0, this.t);
        }
        for (var j = Math.min(a.t, n); i < j; ++i) {
          this.am(0, a[i], r, i, 0, n - i);
        }
        r.clamp();
      };
      BigInteger2.prototype.multiplyUpperTo = function(a, n, r) {
        --n;
        var i = r.t = this.t + a.t - n;
        r.s = 0;
        while (--i >= 0) {
          r[i] = 0;
        }
        for (i = Math.max(n - this.t, 0); i < a.t; ++i) {
          r[this.t + i - n] = this.am(n - i, a[i], r, 0, 0, this.t + i - n);
        }
        r.clamp();
        r.drShiftTo(1, r);
      };
      BigInteger2.prototype.modInt = function(n) {
        if (n <= 0) {
          return 0;
        }
        var d = this.DV % n;
        var r = this.s < 0 ? n - 1 : 0;
        if (this.t > 0) {
          if (d == 0) {
            r = this[0] % n;
          } else {
            for (var i = this.t - 1; i >= 0; --i) {
              r = (d * r + this[i]) % n;
            }
          }
        }
        return r;
      };
      BigInteger2.prototype.millerRabin = function(t2) {
        var n1 = this.subtract(BigInteger2.ONE);
        var k = n1.getLowestSetBit();
        if (k <= 0) {
          return false;
        }
        var r = n1.shiftRight(k);
        t2 = t2 + 1 >> 1;
        if (t2 > lowprimes.length) {
          t2 = lowprimes.length;
        }
        var a = nbi();
        for (var i = 0; i < t2; ++i) {
          a.fromInt(lowprimes[Math.floor(Math.random() * lowprimes.length)]);
          var y = a.modPow(r, this);
          if (y.compareTo(BigInteger2.ONE) != 0 && y.compareTo(n1) != 0) {
            var j = 1;
            while (j++ < k && y.compareTo(n1) != 0) {
              y = y.modPowInt(2, this);
              if (y.compareTo(BigInteger2.ONE) == 0) {
                return false;
              }
            }
            if (y.compareTo(n1) != 0) {
              return false;
            }
          }
        }
        return true;
      };
      BigInteger2.prototype.square = function() {
        var r = nbi();
        this.squareTo(r);
        return r;
      };
      BigInteger2.prototype.gcda = function(a, callback) {
        var x = this.s < 0 ? this.negate() : this.clone();
        var y = a.s < 0 ? a.negate() : a.clone();
        if (x.compareTo(y) < 0) {
          var t2 = x;
          x = y;
          y = t2;
        }
        var i = x.getLowestSetBit();
        var g = y.getLowestSetBit();
        if (g < 0) {
          callback(x);
          return;
        }
        if (i < g) {
          g = i;
        }
        if (g > 0) {
          x.rShiftTo(g, x);
          y.rShiftTo(g, y);
        }
        var gcda1 = function() {
          if ((i = x.getLowestSetBit()) > 0) {
            x.rShiftTo(i, x);
          }
          if ((i = y.getLowestSetBit()) > 0) {
            y.rShiftTo(i, y);
          }
          if (x.compareTo(y) >= 0) {
            x.subTo(y, x);
            x.rShiftTo(1, x);
          } else {
            y.subTo(x, y);
            y.rShiftTo(1, y);
          }
          if (!(x.signum() > 0)) {
            if (g > 0) {
              y.lShiftTo(g, y);
            }
            setTimeout(function() {
              callback(y);
            }, 0);
          } else {
            setTimeout(gcda1, 0);
          }
        };
        setTimeout(gcda1, 10);
      };
      BigInteger2.prototype.fromNumberAsync = function(a, b, c, callback) {
        if ("number" == typeof b) {
          if (a < 2) {
            this.fromInt(1);
          } else {
            this.fromNumber(a, c);
            if (!this.testBit(a - 1)) {
              this.bitwiseTo(BigInteger2.ONE.shiftLeft(a - 1), op_or, this);
            }
            if (this.isEven()) {
              this.dAddOffset(1, 0);
            }
            var bnp_1 = this;
            var bnpfn1_1 = function() {
              bnp_1.dAddOffset(2, 0);
              if (bnp_1.bitLength() > a) {
                bnp_1.subTo(BigInteger2.ONE.shiftLeft(a - 1), bnp_1);
              }
              if (bnp_1.isProbablePrime(b)) {
                setTimeout(function() {
                  callback();
                }, 0);
              } else {
                setTimeout(bnpfn1_1, 0);
              }
            };
            setTimeout(bnpfn1_1, 0);
          }
        } else {
          var x = [];
          var t2 = a & 7;
          x.length = (a >> 3) + 1;
          b.nextBytes(x);
          if (t2 > 0) {
            x[0] &= (1 << t2) - 1;
          } else {
            x[0] = 0;
          }
          this.fromString(x, 256);
        }
      };
      return BigInteger2;
    }()
  );
  var NullExp = (
    /** @class */
    function() {
      function NullExp2() {
      }
      NullExp2.prototype.convert = function(x) {
        return x;
      };
      NullExp2.prototype.revert = function(x) {
        return x;
      };
      NullExp2.prototype.mulTo = function(x, y, r) {
        x.multiplyTo(y, r);
      };
      NullExp2.prototype.sqrTo = function(x, r) {
        x.squareTo(r);
      };
      return NullExp2;
    }()
  );
  var Classic = (
    /** @class */
    function() {
      function Classic2(m) {
        this.m = m;
      }
      Classic2.prototype.convert = function(x) {
        if (x.s < 0 || x.compareTo(this.m) >= 0) {
          return x.mod(this.m);
        } else {
          return x;
        }
      };
      Classic2.prototype.revert = function(x) {
        return x;
      };
      Classic2.prototype.reduce = function(x) {
        x.divRemTo(this.m, null, x);
      };
      Classic2.prototype.mulTo = function(x, y, r) {
        x.multiplyTo(y, r);
        this.reduce(r);
      };
      Classic2.prototype.sqrTo = function(x, r) {
        x.squareTo(r);
        this.reduce(r);
      };
      return Classic2;
    }()
  );
  var Montgomery = (
    /** @class */
    function() {
      function Montgomery2(m) {
        this.m = m;
        this.mp = m.invDigit();
        this.mpl = this.mp & 32767;
        this.mph = this.mp >> 15;
        this.um = (1 << m.DB - 15) - 1;
        this.mt2 = 2 * m.t;
      }
      Montgomery2.prototype.convert = function(x) {
        var r = nbi();
        x.abs().dlShiftTo(this.m.t, r);
        r.divRemTo(this.m, null, r);
        if (x.s < 0 && r.compareTo(BigInteger.ZERO) > 0) {
          this.m.subTo(r, r);
        }
        return r;
      };
      Montgomery2.prototype.revert = function(x) {
        var r = nbi();
        x.copyTo(r);
        this.reduce(r);
        return r;
      };
      Montgomery2.prototype.reduce = function(x) {
        while (x.t <= this.mt2) {
          x[x.t++] = 0;
        }
        for (var i = 0; i < this.m.t; ++i) {
          var j = x[i] & 32767;
          var u0 = j * this.mpl + ((j * this.mph + (x[i] >> 15) * this.mpl & this.um) << 15) & x.DM;
          j = i + this.m.t;
          x[j] += this.m.am(0, u0, x, i, 0, this.m.t);
          while (x[j] >= x.DV) {
            x[j] -= x.DV;
            x[++j]++;
          }
        }
        x.clamp();
        x.drShiftTo(this.m.t, x);
        if (x.compareTo(this.m) >= 0) {
          x.subTo(this.m, x);
        }
      };
      Montgomery2.prototype.mulTo = function(x, y, r) {
        x.multiplyTo(y, r);
        this.reduce(r);
      };
      Montgomery2.prototype.sqrTo = function(x, r) {
        x.squareTo(r);
        this.reduce(r);
      };
      return Montgomery2;
    }()
  );
  var Barrett = (
    /** @class */
    function() {
      function Barrett2(m) {
        this.m = m;
        this.r2 = nbi();
        this.q3 = nbi();
        BigInteger.ONE.dlShiftTo(2 * m.t, this.r2);
        this.mu = this.r2.divide(m);
      }
      Barrett2.prototype.convert = function(x) {
        if (x.s < 0 || x.t > 2 * this.m.t) {
          return x.mod(this.m);
        } else if (x.compareTo(this.m) < 0) {
          return x;
        } else {
          var r = nbi();
          x.copyTo(r);
          this.reduce(r);
          return r;
        }
      };
      Barrett2.prototype.revert = function(x) {
        return x;
      };
      Barrett2.prototype.reduce = function(x) {
        x.drShiftTo(this.m.t - 1, this.r2);
        if (x.t > this.m.t + 1) {
          x.t = this.m.t + 1;
          x.clamp();
        }
        this.mu.multiplyUpperTo(this.r2, this.m.t + 1, this.q3);
        this.m.multiplyLowerTo(this.q3, this.m.t + 1, this.r2);
        while (x.compareTo(this.r2) < 0) {
          x.dAddOffset(1, this.m.t + 1);
        }
        x.subTo(this.r2, x);
        while (x.compareTo(this.m) >= 0) {
          x.subTo(this.m, x);
        }
      };
      Barrett2.prototype.mulTo = function(x, y, r) {
        x.multiplyTo(y, r);
        this.reduce(r);
      };
      Barrett2.prototype.sqrTo = function(x, r) {
        x.squareTo(r);
        this.reduce(r);
      };
      return Barrett2;
    }()
  );
  function nbi() {
    return new BigInteger(null);
  }
  function parseBigInt(str, r) {
    return new BigInteger(str, r);
  }
  var inBrowser = typeof navigator !== "undefined";
  if (inBrowser && j_lm && navigator.appName == "Microsoft Internet Explorer") {
    BigInteger.prototype.am = function am2(i, x, w, j, c, n) {
      var xl = x & 32767;
      var xh = x >> 15;
      while (--n >= 0) {
        var l = this[i] & 32767;
        var h = this[i++] >> 15;
        var m = xh * l + h * xl;
        l = xl * l + ((m & 32767) << 15) + w[j] + (c & 1073741823);
        c = (l >>> 30) + (m >>> 15) + xh * h + (c >>> 30);
        w[j++] = l & 1073741823;
      }
      return c;
    };
    dbits = 30;
  } else if (inBrowser && j_lm && navigator.appName != "Netscape") {
    BigInteger.prototype.am = function am1(i, x, w, j, c, n) {
      while (--n >= 0) {
        var v = x * this[i++] + w[j] + c;
        c = Math.floor(v / 67108864);
        w[j++] = v & 67108863;
      }
      return c;
    };
    dbits = 26;
  } else {
    BigInteger.prototype.am = function am3(i, x, w, j, c, n) {
      var xl = x & 16383;
      var xh = x >> 14;
      while (--n >= 0) {
        var l = this[i] & 16383;
        var h = this[i++] >> 14;
        var m = xh * l + h * xl;
        l = xl * l + ((m & 16383) << 14) + w[j] + c;
        c = (l >> 28) + (m >> 14) + xh * h;
        w[j++] = l & 268435455;
      }
      return c;
    };
    dbits = 28;
  }
  BigInteger.prototype.DB = dbits;
  BigInteger.prototype.DM = (1 << dbits) - 1;
  BigInteger.prototype.DV = 1 << dbits;
  var BI_FP = 52;
  BigInteger.prototype.FV = Math.pow(2, BI_FP);
  BigInteger.prototype.F1 = BI_FP - dbits;
  BigInteger.prototype.F2 = 2 * dbits - BI_FP;
  var BI_RC = [];
  var rr;
  var vv;
  rr = "0".charCodeAt(0);
  for (vv = 0; vv <= 9; ++vv) {
    BI_RC[rr++] = vv;
  }
  rr = "a".charCodeAt(0);
  for (vv = 10; vv < 36; ++vv) {
    BI_RC[rr++] = vv;
  }
  rr = "A".charCodeAt(0);
  for (vv = 10; vv < 36; ++vv) {
    BI_RC[rr++] = vv;
  }
  function intAt(s, i) {
    var c = BI_RC[s.charCodeAt(i)];
    return c == null ? -1 : c;
  }
  function nbv(i) {
    var r = nbi();
    r.fromInt(i);
    return r;
  }
  function nbits(x) {
    var r = 1;
    var t2;
    if ((t2 = x >>> 16) != 0) {
      x = t2;
      r += 16;
    }
    if ((t2 = x >> 8) != 0) {
      x = t2;
      r += 8;
    }
    if ((t2 = x >> 4) != 0) {
      x = t2;
      r += 4;
    }
    if ((t2 = x >> 2) != 0) {
      x = t2;
      r += 2;
    }
    if ((t2 = x >> 1) != 0) {
      x = t2;
      r += 1;
    }
    return r;
  }
  BigInteger.ZERO = nbv(0);
  BigInteger.ONE = nbv(1);
  var lookup = [
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    62,
    0,
    62,
    0,
    63,
    52,
    53,
    54,
    55,
    56,
    57,
    58,
    59,
    60,
    61,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    1,
    2,
    3,
    4,
    5,
    6,
    7,
    8,
    9,
    10,
    11,
    12,
    13,
    14,
    15,
    16,
    17,
    18,
    19,
    20,
    21,
    22,
    23,
    24,
    25,
    0,
    0,
    0,
    0,
    63,
    0,
    26,
    27,
    28,
    29,
    30,
    31,
    32,
    33,
    34,
    35,
    36,
    37,
    38,
    39,
    40,
    41,
    42,
    43,
    44,
    45,
    46,
    47,
    48,
    49,
    50,
    51
  ];
  function base64Decode(source, target) {
    var sourceLength = source.length;
    var paddingLength = source[sourceLength - 2] === "=" ? 2 : source[sourceLength - 1] === "=" ? 1 : 0;
    var tmp;
    var byteIndex = 0;
    var baseLength = sourceLength - paddingLength & 4294967292;
    for (var i = 0; i < baseLength; i += 4) {
      tmp = lookup[source.charCodeAt(i)] << 18 | lookup[source.charCodeAt(i + 1)] << 12 | lookup[source.charCodeAt(i + 2)] << 6 | lookup[source.charCodeAt(i + 3)];
      target[byteIndex++] = tmp >> 16 & 255;
      target[byteIndex++] = tmp >> 8 & 255;
      target[byteIndex++] = tmp & 255;
    }
    if (paddingLength === 1) {
      tmp = lookup[source.charCodeAt(i)] << 10 | lookup[source.charCodeAt(i + 1)] << 4 | lookup[source.charCodeAt(i + 2)] >> 2;
      target[byteIndex++] = tmp >> 8 & 255;
      target[byteIndex++] = tmp & 255;
    }
    if (paddingLength === 2) {
      tmp = lookup[source.charCodeAt(i)] << 2 | lookup[source.charCodeAt(i + 1)] >> 4;
      target[byteIndex++] = tmp & 255;
    }
  }
  const $inject_window_crypto = {
    getRandomValues(arr) {
      if (!(arr instanceof Int8Array || arr instanceof Uint8Array || arr instanceof Int16Array || arr instanceof Uint16Array || arr instanceof Int32Array || arr instanceof Uint32Array || arr instanceof Uint8ClampedArray)) {
        throw new Error("Expected an integer array");
      }
      if (arr.byteLength > 65536) {
        throw new Error("Can only request a maximum of 65536 bytes");
      }
      var crypto = requireNativePlugin("DCloud-Crypto");
      base64Decode(crypto.getRandomValues(arr.byteLength), new Uint8Array(
        arr.buffer,
        arr.byteOffset,
        arr.byteLength
      ));
      return arr;
    }
  };
  var Arcfour = (
    /** @class */
    function() {
      function Arcfour2() {
        this.i = 0;
        this.j = 0;
        this.S = [];
      }
      Arcfour2.prototype.init = function(key) {
        var i;
        var j;
        var t2;
        for (i = 0; i < 256; ++i) {
          this.S[i] = i;
        }
        j = 0;
        for (i = 0; i < 256; ++i) {
          j = j + this.S[i] + key[i % key.length] & 255;
          t2 = this.S[i];
          this.S[i] = this.S[j];
          this.S[j] = t2;
        }
        this.i = 0;
        this.j = 0;
      };
      Arcfour2.prototype.next = function() {
        var t2;
        this.i = this.i + 1 & 255;
        this.j = this.j + this.S[this.i] & 255;
        t2 = this.S[this.i];
        this.S[this.i] = this.S[this.j];
        this.S[this.j] = t2;
        return this.S[t2 + this.S[this.i] & 255];
      };
      return Arcfour2;
    }()
  );
  function prng_newstate() {
    return new Arcfour();
  }
  var rng_psize = 256;
  var rng_state;
  var rng_pool = null;
  var rng_pptr;
  if (rng_pool == null) {
    rng_pool = [];
    rng_pptr = 0;
    var t$1 = void 0;
    if (typeof window !== "undefined" && $inject_window_crypto && $inject_window_crypto.getRandomValues) {
      var z = new Uint32Array(256);
      $inject_window_crypto.getRandomValues(z);
      for (t$1 = 0; t$1 < z.length; ++t$1) {
        rng_pool[rng_pptr++] = z[t$1] & 255;
      }
    }
    var count = 0;
    var onMouseMoveListener_1 = function(ev) {
      count = count || 0;
      if (count >= 256 || rng_pptr >= rng_psize) {
        if (window.removeEventListener) {
          window.removeEventListener("mousemove", onMouseMoveListener_1, false);
        } else if (window.detachEvent) {
          window.detachEvent("onmousemove", onMouseMoveListener_1);
        }
        return;
      }
      try {
        var mouseCoordinates = ev.x + ev.y;
        rng_pool[rng_pptr++] = mouseCoordinates & 255;
        count += 1;
      } catch (e) {
      }
    };
    if (typeof window !== "undefined") {
      if (window.addEventListener) {
        window.addEventListener("mousemove", onMouseMoveListener_1, false);
      } else if (window.attachEvent) {
        window.attachEvent("onmousemove", onMouseMoveListener_1);
      }
    }
  }
  function rng_get_byte() {
    if (rng_state == null) {
      rng_state = prng_newstate();
      while (rng_pptr < rng_psize) {
        var random = Math.floor(65536 * Math.random());
        rng_pool[rng_pptr++] = random & 255;
      }
      rng_state.init(rng_pool);
      for (rng_pptr = 0; rng_pptr < rng_pool.length; ++rng_pptr) {
        rng_pool[rng_pptr] = 0;
      }
      rng_pptr = 0;
    }
    return rng_state.next();
  }
  var SecureRandom = (
    /** @class */
    function() {
      function SecureRandom2() {
      }
      SecureRandom2.prototype.nextBytes = function(ba) {
        for (var i = 0; i < ba.length; ++i) {
          ba[i] = rng_get_byte();
        }
      };
      return SecureRandom2;
    }()
  );
  function pkcs1pad1(s, n) {
    if (n < s.length + 22) {
      formatAppLog("error", "at node_modules/jsencrypt/lib/lib/jsbn/rsa.js:23", "Message too long for RSA");
      return null;
    }
    var len = n - s.length - 6;
    var filler = "";
    for (var f = 0; f < len; f += 2) {
      filler += "ff";
    }
    var m = "0001" + filler + "00" + s;
    return parseBigInt(m, 16);
  }
  function pkcs1pad2(s, n) {
    if (n < s.length + 11) {
      formatAppLog("error", "at node_modules/jsencrypt/lib/lib/jsbn/rsa.js:37", "Message too long for RSA");
      return null;
    }
    var ba = [];
    var i = s.length - 1;
    while (i >= 0 && n > 0) {
      var c = s.charCodeAt(i--);
      if (c < 128) {
        ba[--n] = c;
      } else if (c > 127 && c < 2048) {
        ba[--n] = c & 63 | 128;
        ba[--n] = c >> 6 | 192;
      } else {
        ba[--n] = c & 63 | 128;
        ba[--n] = c >> 6 & 63 | 128;
        ba[--n] = c >> 12 | 224;
      }
    }
    ba[--n] = 0;
    var rng = new SecureRandom();
    var x = [];
    while (n > 2) {
      x[0] = 0;
      while (x[0] == 0) {
        rng.nextBytes(x);
      }
      ba[--n] = x[0];
    }
    ba[--n] = 2;
    ba[--n] = 0;
    return new BigInteger(ba);
  }
  var RSAKey = (
    /** @class */
    function() {
      function RSAKey2() {
        this.n = null;
        this.e = 0;
        this.d = null;
        this.p = null;
        this.q = null;
        this.dmp1 = null;
        this.dmq1 = null;
        this.coeff = null;
      }
      RSAKey2.prototype.doPublic = function(x) {
        return x.modPowInt(this.e, this.n);
      };
      RSAKey2.prototype.doPrivate = function(x) {
        if (this.p == null || this.q == null) {
          return x.modPow(this.d, this.n);
        }
        var xp = x.mod(this.p).modPow(this.dmp1, this.p);
        var xq = x.mod(this.q).modPow(this.dmq1, this.q);
        while (xp.compareTo(xq) < 0) {
          xp = xp.add(this.p);
        }
        return xp.subtract(xq).multiply(this.coeff).mod(this.p).multiply(this.q).add(xq);
      };
      RSAKey2.prototype.setPublic = function(N, E) {
        if (N != null && E != null && N.length > 0 && E.length > 0) {
          this.n = parseBigInt(N, 16);
          this.e = parseInt(E, 16);
        } else {
          formatAppLog("error", "at node_modules/jsencrypt/lib/lib/jsbn/rsa.js:114", "Invalid RSA public key");
        }
      };
      RSAKey2.prototype.encrypt = function(text) {
        var maxLength = this.n.bitLength() + 7 >> 3;
        var m = pkcs1pad2(text, maxLength);
        if (m == null) {
          return null;
        }
        var c = this.doPublic(m);
        if (c == null) {
          return null;
        }
        var h = c.toString(16);
        var length = h.length;
        for (var i = 0; i < maxLength * 2 - length; i++) {
          h = "0" + h;
        }
        return h;
      };
      RSAKey2.prototype.setPrivate = function(N, E, D) {
        if (N != null && E != null && N.length > 0 && E.length > 0) {
          this.n = parseBigInt(N, 16);
          this.e = parseInt(E, 16);
          this.d = parseBigInt(D, 16);
        } else {
          formatAppLog("error", "at node_modules/jsencrypt/lib/lib/jsbn/rsa.js:146", "Invalid RSA private key");
        }
      };
      RSAKey2.prototype.setPrivateEx = function(N, E, D, P, Q, DP, DQ, C) {
        if (N != null && E != null && N.length > 0 && E.length > 0) {
          this.n = parseBigInt(N, 16);
          this.e = parseInt(E, 16);
          this.d = parseBigInt(D, 16);
          this.p = parseBigInt(P, 16);
          this.q = parseBigInt(Q, 16);
          this.dmp1 = parseBigInt(DP, 16);
          this.dmq1 = parseBigInt(DQ, 16);
          this.coeff = parseBigInt(C, 16);
        } else {
          formatAppLog("error", "at node_modules/jsencrypt/lib/lib/jsbn/rsa.js:163", "Invalid RSA private key");
        }
      };
      RSAKey2.prototype.generate = function(B, E) {
        var rng = new SecureRandom();
        var qs = B >> 1;
        this.e = parseInt(E, 16);
        var ee = new BigInteger(E, 16);
        for (; ; ) {
          for (; ; ) {
            this.p = new BigInteger(B - qs, 1, rng);
            if (this.p.subtract(BigInteger.ONE).gcd(ee).compareTo(BigInteger.ONE) == 0 && this.p.isProbablePrime(10)) {
              break;
            }
          }
          for (; ; ) {
            this.q = new BigInteger(qs, 1, rng);
            if (this.q.subtract(BigInteger.ONE).gcd(ee).compareTo(BigInteger.ONE) == 0 && this.q.isProbablePrime(10)) {
              break;
            }
          }
          if (this.p.compareTo(this.q) <= 0) {
            var t2 = this.p;
            this.p = this.q;
            this.q = t2;
          }
          var p1 = this.p.subtract(BigInteger.ONE);
          var q1 = this.q.subtract(BigInteger.ONE);
          var phi = p1.multiply(q1);
          if (phi.gcd(ee).compareTo(BigInteger.ONE) == 0) {
            this.n = this.p.multiply(this.q);
            this.d = ee.modInverse(phi);
            this.dmp1 = this.d.mod(p1);
            this.dmq1 = this.d.mod(q1);
            this.coeff = this.q.modInverse(this.p);
            break;
          }
        }
      };
      RSAKey2.prototype.decrypt = function(ctext) {
        var c = parseBigInt(ctext, 16);
        var m = this.doPrivate(c);
        if (m == null) {
          return null;
        }
        return pkcs1unpad2(m, this.n.bitLength() + 7 >> 3);
      };
      RSAKey2.prototype.generateAsync = function(B, E, callback) {
        var rng = new SecureRandom();
        var qs = B >> 1;
        this.e = parseInt(E, 16);
        var ee = new BigInteger(E, 16);
        var rsa = this;
        var loop1 = function() {
          var loop4 = function() {
            if (rsa.p.compareTo(rsa.q) <= 0) {
              var t2 = rsa.p;
              rsa.p = rsa.q;
              rsa.q = t2;
            }
            var p1 = rsa.p.subtract(BigInteger.ONE);
            var q1 = rsa.q.subtract(BigInteger.ONE);
            var phi = p1.multiply(q1);
            if (phi.gcd(ee).compareTo(BigInteger.ONE) == 0) {
              rsa.n = rsa.p.multiply(rsa.q);
              rsa.d = ee.modInverse(phi);
              rsa.dmp1 = rsa.d.mod(p1);
              rsa.dmq1 = rsa.d.mod(q1);
              rsa.coeff = rsa.q.modInverse(rsa.p);
              setTimeout(function() {
                callback();
              }, 0);
            } else {
              setTimeout(loop1, 0);
            }
          };
          var loop3 = function() {
            rsa.q = nbi();
            rsa.q.fromNumberAsync(qs, 1, rng, function() {
              rsa.q.subtract(BigInteger.ONE).gcda(ee, function(r) {
                if (r.compareTo(BigInteger.ONE) == 0 && rsa.q.isProbablePrime(10)) {
                  setTimeout(loop4, 0);
                } else {
                  setTimeout(loop3, 0);
                }
              });
            });
          };
          var loop2 = function() {
            rsa.p = nbi();
            rsa.p.fromNumberAsync(B - qs, 1, rng, function() {
              rsa.p.subtract(BigInteger.ONE).gcda(ee, function(r) {
                if (r.compareTo(BigInteger.ONE) == 0 && rsa.p.isProbablePrime(10)) {
                  setTimeout(loop3, 0);
                } else {
                  setTimeout(loop2, 0);
                }
              });
            });
          };
          setTimeout(loop2, 0);
        };
        setTimeout(loop1, 0);
      };
      RSAKey2.prototype.sign = function(text, digestMethod, digestName) {
        var header = getDigestHeader(digestName);
        var digest = header + digestMethod(text).toString();
        var m = pkcs1pad1(digest, this.n.bitLength() / 4);
        if (m == null) {
          return null;
        }
        var c = this.doPrivate(m);
        if (c == null) {
          return null;
        }
        var h = c.toString(16);
        if ((h.length & 1) == 0) {
          return h;
        } else {
          return "0" + h;
        }
      };
      RSAKey2.prototype.verify = function(text, signature, digestMethod) {
        var c = parseBigInt(signature, 16);
        var m = this.doPublic(c);
        if (m == null) {
          return null;
        }
        var unpadded = m.toString(16).replace(/^1f+00/, "");
        var digest = removeDigestHeader(unpadded);
        return digest == digestMethod(text).toString();
      };
      return RSAKey2;
    }()
  );
  function pkcs1unpad2(d, n) {
    var b = d.toByteArray();
    var i = 0;
    while (i < b.length && b[i] == 0) {
      ++i;
    }
    if (b.length - i != n - 1 || b[i] != 2) {
      return null;
    }
    ++i;
    while (b[i] != 0) {
      if (++i >= b.length) {
        return null;
      }
    }
    var ret = "";
    while (++i < b.length) {
      var c = b[i] & 255;
      if (c < 128) {
        ret += String.fromCharCode(c);
      } else if (c > 191 && c < 224) {
        ret += String.fromCharCode((c & 31) << 6 | b[i + 1] & 63);
        ++i;
      } else {
        ret += String.fromCharCode((c & 15) << 12 | (b[i + 1] & 63) << 6 | b[i + 2] & 63);
        i += 2;
      }
    }
    return ret;
  }
  var DIGEST_HEADERS = {
    md2: "3020300c06082a864886f70d020205000410",
    md5: "3020300c06082a864886f70d020505000410",
    sha1: "3021300906052b0e03021a05000414",
    sha224: "302d300d06096086480165030402040500041c",
    sha256: "3031300d060960864801650304020105000420",
    sha384: "3041300d060960864801650304020205000430",
    sha512: "3051300d060960864801650304020305000440",
    ripemd160: "3021300906052b2403020105000414"
  };
  function getDigestHeader(name) {
    return DIGEST_HEADERS[name] || "";
  }
  function removeDigestHeader(str) {
    for (var name_1 in DIGEST_HEADERS) {
      if (DIGEST_HEADERS.hasOwnProperty(name_1)) {
        var header = DIGEST_HEADERS[name_1];
        var len = header.length;
        if (str.substr(0, len) == header) {
          return str.substr(len);
        }
      }
    }
    return str;
  }
  /*!
  Copyright (c) 2011, Yahoo! Inc. All rights reserved.
  Code licensed under the BSD License:
  http://developer.yahoo.com/yui/license.html
  version: 2.9.0
  */
  var YAHOO = {};
  YAHOO.lang = {
    /**
     * Utility to set up the prototype, constructor and superclass properties to
     * support an inheritance strategy that can chain constructors and methods.
     * Static members will not be inherited.
     *
     * @method extend
     * @static
     * @param {Function} subc   the object to modify
     * @param {Function} superc the object to inherit
     * @param {Object} overrides  additional properties/methods to add to the
     *                              subclass prototype.  These will override the
     *                              matching items obtained from the superclass
     *                              if present.
     */
    extend: function(subc, superc, overrides) {
      if (!superc || !subc) {
        throw new Error("YAHOO.lang.extend failed, please check that all dependencies are included.");
      }
      var F = function() {
      };
      F.prototype = superc.prototype;
      subc.prototype = new F();
      subc.prototype.constructor = subc;
      subc.superclass = superc.prototype;
      if (superc.prototype.constructor == Object.prototype.constructor) {
        superc.prototype.constructor = superc;
      }
      if (overrides) {
        var i;
        for (i in overrides) {
          subc.prototype[i] = overrides[i];
        }
        var _IEEnumFix = function() {
        }, ADD = ["toString", "valueOf"];
        try {
          if (/MSIE/.test(navigator.userAgent)) {
            _IEEnumFix = function(r, s) {
              for (i = 0; i < ADD.length; i = i + 1) {
                var fname = ADD[i], f = s[fname];
                if (typeof f === "function" && f != Object.prototype[fname]) {
                  r[fname] = f;
                }
              }
            };
          }
        } catch (ex) {
        }
        _IEEnumFix(subc.prototype, overrides);
      }
    }
  };
  /**
   * @fileOverview
   * @name asn1-1.0.js
   * @author Kenji Urushima kenji.urushima@gmail.com
   * @version asn1 1.0.13 (2017-Jun-02)
   * @since jsrsasign 2.1
   * @license <a href="https://kjur.github.io/jsrsasign/license/">MIT License</a>
   */
  var KJUR = {};
  if (typeof KJUR.asn1 == "undefined" || !KJUR.asn1)
    KJUR.asn1 = {};
  KJUR.asn1.ASN1Util = new function() {
    this.integerToByteHex = function(i) {
      var h = i.toString(16);
      if (h.length % 2 == 1)
        h = "0" + h;
      return h;
    };
    this.bigIntToMinTwosComplementsHex = function(bigIntegerValue) {
      var h = bigIntegerValue.toString(16);
      if (h.substr(0, 1) != "-") {
        if (h.length % 2 == 1) {
          h = "0" + h;
        } else {
          if (!h.match(/^[0-7]/)) {
            h = "00" + h;
          }
        }
      } else {
        var hPos = h.substr(1);
        var xorLen = hPos.length;
        if (xorLen % 2 == 1) {
          xorLen += 1;
        } else {
          if (!h.match(/^[0-7]/)) {
            xorLen += 2;
          }
        }
        var hMask = "";
        for (var i = 0; i < xorLen; i++) {
          hMask += "f";
        }
        var biMask = new BigInteger(hMask, 16);
        var biNeg = biMask.xor(bigIntegerValue).add(BigInteger.ONE);
        h = biNeg.toString(16).replace(/^-/, "");
      }
      return h;
    };
    this.getPEMStringFromHex = function(dataHex, pemHeader) {
      return hextopem(dataHex, pemHeader);
    };
    this.newObject = function(param) {
      var _KJUR = KJUR, _KJUR_asn1 = _KJUR.asn1, _DERBoolean = _KJUR_asn1.DERBoolean, _DERInteger = _KJUR_asn1.DERInteger, _DERBitString = _KJUR_asn1.DERBitString, _DEROctetString = _KJUR_asn1.DEROctetString, _DERNull = _KJUR_asn1.DERNull, _DERObjectIdentifier = _KJUR_asn1.DERObjectIdentifier, _DEREnumerated = _KJUR_asn1.DEREnumerated, _DERUTF8String = _KJUR_asn1.DERUTF8String, _DERNumericString = _KJUR_asn1.DERNumericString, _DERPrintableString = _KJUR_asn1.DERPrintableString, _DERTeletexString = _KJUR_asn1.DERTeletexString, _DERIA5String = _KJUR_asn1.DERIA5String, _DERUTCTime = _KJUR_asn1.DERUTCTime, _DERGeneralizedTime = _KJUR_asn1.DERGeneralizedTime, _DERSequence = _KJUR_asn1.DERSequence, _DERSet = _KJUR_asn1.DERSet, _DERTaggedObject = _KJUR_asn1.DERTaggedObject, _newObject = _KJUR_asn1.ASN1Util.newObject;
      var keys = Object.keys(param);
      if (keys.length != 1)
        throw "key of param shall be only one.";
      var key = keys[0];
      if (":bool:int:bitstr:octstr:null:oid:enum:utf8str:numstr:prnstr:telstr:ia5str:utctime:gentime:seq:set:tag:".indexOf(":" + key + ":") == -1)
        throw "undefined key: " + key;
      if (key == "bool")
        return new _DERBoolean(param[key]);
      if (key == "int")
        return new _DERInteger(param[key]);
      if (key == "bitstr")
        return new _DERBitString(param[key]);
      if (key == "octstr")
        return new _DEROctetString(param[key]);
      if (key == "null")
        return new _DERNull(param[key]);
      if (key == "oid")
        return new _DERObjectIdentifier(param[key]);
      if (key == "enum")
        return new _DEREnumerated(param[key]);
      if (key == "utf8str")
        return new _DERUTF8String(param[key]);
      if (key == "numstr")
        return new _DERNumericString(param[key]);
      if (key == "prnstr")
        return new _DERPrintableString(param[key]);
      if (key == "telstr")
        return new _DERTeletexString(param[key]);
      if (key == "ia5str")
        return new _DERIA5String(param[key]);
      if (key == "utctime")
        return new _DERUTCTime(param[key]);
      if (key == "gentime")
        return new _DERGeneralizedTime(param[key]);
      if (key == "seq") {
        var paramList = param[key];
        var a = [];
        for (var i = 0; i < paramList.length; i++) {
          var asn1Obj = _newObject(paramList[i]);
          a.push(asn1Obj);
        }
        return new _DERSequence({ "array": a });
      }
      if (key == "set") {
        var paramList = param[key];
        var a = [];
        for (var i = 0; i < paramList.length; i++) {
          var asn1Obj = _newObject(paramList[i]);
          a.push(asn1Obj);
        }
        return new _DERSet({ "array": a });
      }
      if (key == "tag") {
        var tagParam = param[key];
        if (Object.prototype.toString.call(tagParam) === "[object Array]" && tagParam.length == 3) {
          var obj = _newObject(tagParam[2]);
          return new _DERTaggedObject({
            tag: tagParam[0],
            explicit: tagParam[1],
            obj
          });
        } else {
          var newParam = {};
          if (tagParam.explicit !== void 0)
            newParam.explicit = tagParam.explicit;
          if (tagParam.tag !== void 0)
            newParam.tag = tagParam.tag;
          if (tagParam.obj === void 0)
            throw "obj shall be specified for 'tag'.";
          newParam.obj = _newObject(tagParam.obj);
          return new _DERTaggedObject(newParam);
        }
      }
    };
    this.jsonToASN1HEX = function(param) {
      var asn1Obj = this.newObject(param);
      return asn1Obj.getEncodedHex();
    };
  }();
  KJUR.asn1.ASN1Util.oidHexToInt = function(hex) {
    var s = "";
    var i01 = parseInt(hex.substr(0, 2), 16);
    var i0 = Math.floor(i01 / 40);
    var i1 = i01 % 40;
    var s = i0 + "." + i1;
    var binbuf = "";
    for (var i = 2; i < hex.length; i += 2) {
      var value = parseInt(hex.substr(i, 2), 16);
      var bin = ("00000000" + value.toString(2)).slice(-8);
      binbuf = binbuf + bin.substr(1, 7);
      if (bin.substr(0, 1) == "0") {
        var bi = new BigInteger(binbuf, 2);
        s = s + "." + bi.toString(10);
        binbuf = "";
      }
    }
    return s;
  };
  KJUR.asn1.ASN1Util.oidIntToHex = function(oidString) {
    var itox = function(i2) {
      var h2 = i2.toString(16);
      if (h2.length == 1)
        h2 = "0" + h2;
      return h2;
    };
    var roidtox = function(roid) {
      var h2 = "";
      var bi = new BigInteger(roid, 10);
      var b = bi.toString(2);
      var padLen = 7 - b.length % 7;
      if (padLen == 7)
        padLen = 0;
      var bPad = "";
      for (var i2 = 0; i2 < padLen; i2++)
        bPad += "0";
      b = bPad + b;
      for (var i2 = 0; i2 < b.length - 1; i2 += 7) {
        var b8 = b.substr(i2, 7);
        if (i2 != b.length - 7)
          b8 = "1" + b8;
        h2 += itox(parseInt(b8, 2));
      }
      return h2;
    };
    if (!oidString.match(/^[0-9.]+$/)) {
      throw "malformed oid string: " + oidString;
    }
    var h = "";
    var a = oidString.split(".");
    var i0 = parseInt(a[0]) * 40 + parseInt(a[1]);
    h += itox(i0);
    a.splice(0, 2);
    for (var i = 0; i < a.length; i++) {
      h += roidtox(a[i]);
    }
    return h;
  };
  KJUR.asn1.ASN1Object = function() {
    var hV = "";
    this.getLengthHexFromValue = function() {
      if (typeof this.hV == "undefined" || this.hV == null) {
        throw "this.hV is null or undefined.";
      }
      if (this.hV.length % 2 == 1) {
        throw "value hex must be even length: n=" + hV.length + ",v=" + this.hV;
      }
      var n = this.hV.length / 2;
      var hN = n.toString(16);
      if (hN.length % 2 == 1) {
        hN = "0" + hN;
      }
      if (n < 128) {
        return hN;
      } else {
        var hNlen = hN.length / 2;
        if (hNlen > 15) {
          throw "ASN.1 length too long to represent by 8x: n = " + n.toString(16);
        }
        var head = 128 + hNlen;
        return head.toString(16) + hN;
      }
    };
    this.getEncodedHex = function() {
      if (this.hTLV == null || this.isModified) {
        this.hV = this.getFreshValueHex();
        this.hL = this.getLengthHexFromValue();
        this.hTLV = this.hT + this.hL + this.hV;
        this.isModified = false;
      }
      return this.hTLV;
    };
    this.getValueHex = function() {
      this.getEncodedHex();
      return this.hV;
    };
    this.getFreshValueHex = function() {
      return "";
    };
  };
  KJUR.asn1.DERAbstractString = function(params) {
    KJUR.asn1.DERAbstractString.superclass.constructor.call(this);
    this.getString = function() {
      return this.s;
    };
    this.setString = function(newS) {
      this.hTLV = null;
      this.isModified = true;
      this.s = newS;
      this.hV = stohex(this.s);
    };
    this.setStringHex = function(newHexString) {
      this.hTLV = null;
      this.isModified = true;
      this.s = null;
      this.hV = newHexString;
    };
    this.getFreshValueHex = function() {
      return this.hV;
    };
    if (typeof params != "undefined") {
      if (typeof params == "string") {
        this.setString(params);
      } else if (typeof params["str"] != "undefined") {
        this.setString(params["str"]);
      } else if (typeof params["hex"] != "undefined") {
        this.setStringHex(params["hex"]);
      }
    }
  };
  YAHOO.lang.extend(KJUR.asn1.DERAbstractString, KJUR.asn1.ASN1Object);
  KJUR.asn1.DERAbstractTime = function(params) {
    KJUR.asn1.DERAbstractTime.superclass.constructor.call(this);
    this.localDateToUTC = function(d) {
      utc = d.getTime() + d.getTimezoneOffset() * 6e4;
      var utcDate = new Date(utc);
      return utcDate;
    };
    this.formatDate = function(dateObject, type, withMillis) {
      var pad = this.zeroPadding;
      var d = this.localDateToUTC(dateObject);
      var year = String(d.getFullYear());
      if (type == "utc")
        year = year.substr(2, 2);
      var month = pad(String(d.getMonth() + 1), 2);
      var day = pad(String(d.getDate()), 2);
      var hour = pad(String(d.getHours()), 2);
      var min = pad(String(d.getMinutes()), 2);
      var sec = pad(String(d.getSeconds()), 2);
      var s = year + month + day + hour + min + sec;
      if (withMillis === true) {
        var millis = d.getMilliseconds();
        if (millis != 0) {
          var sMillis = pad(String(millis), 3);
          sMillis = sMillis.replace(/[0]+$/, "");
          s = s + "." + sMillis;
        }
      }
      return s + "Z";
    };
    this.zeroPadding = function(s, len) {
      if (s.length >= len)
        return s;
      return new Array(len - s.length + 1).join("0") + s;
    };
    this.getString = function() {
      return this.s;
    };
    this.setString = function(newS) {
      this.hTLV = null;
      this.isModified = true;
      this.s = newS;
      this.hV = stohex(newS);
    };
    this.setByDateValue = function(year, month, day, hour, min, sec) {
      var dateObject = new Date(Date.UTC(year, month - 1, day, hour, min, sec, 0));
      this.setByDate(dateObject);
    };
    this.getFreshValueHex = function() {
      return this.hV;
    };
  };
  YAHOO.lang.extend(KJUR.asn1.DERAbstractTime, KJUR.asn1.ASN1Object);
  KJUR.asn1.DERAbstractStructured = function(params) {
    KJUR.asn1.DERAbstractString.superclass.constructor.call(this);
    this.setByASN1ObjectArray = function(asn1ObjectArray) {
      this.hTLV = null;
      this.isModified = true;
      this.asn1Array = asn1ObjectArray;
    };
    this.appendASN1Object = function(asn1Object) {
      this.hTLV = null;
      this.isModified = true;
      this.asn1Array.push(asn1Object);
    };
    this.asn1Array = new Array();
    if (typeof params != "undefined") {
      if (typeof params["array"] != "undefined") {
        this.asn1Array = params["array"];
      }
    }
  };
  YAHOO.lang.extend(KJUR.asn1.DERAbstractStructured, KJUR.asn1.ASN1Object);
  KJUR.asn1.DERBoolean = function() {
    KJUR.asn1.DERBoolean.superclass.constructor.call(this);
    this.hT = "01";
    this.hTLV = "0101ff";
  };
  YAHOO.lang.extend(KJUR.asn1.DERBoolean, KJUR.asn1.ASN1Object);
  KJUR.asn1.DERInteger = function(params) {
    KJUR.asn1.DERInteger.superclass.constructor.call(this);
    this.hT = "02";
    this.setByBigInteger = function(bigIntegerValue) {
      this.hTLV = null;
      this.isModified = true;
      this.hV = KJUR.asn1.ASN1Util.bigIntToMinTwosComplementsHex(bigIntegerValue);
    };
    this.setByInteger = function(intValue) {
      var bi = new BigInteger(String(intValue), 10);
      this.setByBigInteger(bi);
    };
    this.setValueHex = function(newHexString) {
      this.hV = newHexString;
    };
    this.getFreshValueHex = function() {
      return this.hV;
    };
    if (typeof params != "undefined") {
      if (typeof params["bigint"] != "undefined") {
        this.setByBigInteger(params["bigint"]);
      } else if (typeof params["int"] != "undefined") {
        this.setByInteger(params["int"]);
      } else if (typeof params == "number") {
        this.setByInteger(params);
      } else if (typeof params["hex"] != "undefined") {
        this.setValueHex(params["hex"]);
      }
    }
  };
  YAHOO.lang.extend(KJUR.asn1.DERInteger, KJUR.asn1.ASN1Object);
  KJUR.asn1.DERBitString = function(params) {
    if (params !== void 0 && typeof params.obj !== "undefined") {
      var o = KJUR.asn1.ASN1Util.newObject(params.obj);
      params.hex = "00" + o.getEncodedHex();
    }
    KJUR.asn1.DERBitString.superclass.constructor.call(this);
    this.hT = "03";
    this.setHexValueIncludingUnusedBits = function(newHexStringIncludingUnusedBits) {
      this.hTLV = null;
      this.isModified = true;
      this.hV = newHexStringIncludingUnusedBits;
    };
    this.setUnusedBitsAndHexValue = function(unusedBits, hValue) {
      if (unusedBits < 0 || 7 < unusedBits) {
        throw "unused bits shall be from 0 to 7: u = " + unusedBits;
      }
      var hUnusedBits = "0" + unusedBits;
      this.hTLV = null;
      this.isModified = true;
      this.hV = hUnusedBits + hValue;
    };
    this.setByBinaryString = function(binaryString) {
      binaryString = binaryString.replace(/0+$/, "");
      var unusedBits = 8 - binaryString.length % 8;
      if (unusedBits == 8)
        unusedBits = 0;
      for (var i = 0; i <= unusedBits; i++) {
        binaryString += "0";
      }
      var h = "";
      for (var i = 0; i < binaryString.length - 1; i += 8) {
        var b = binaryString.substr(i, 8);
        var x = parseInt(b, 2).toString(16);
        if (x.length == 1)
          x = "0" + x;
        h += x;
      }
      this.hTLV = null;
      this.isModified = true;
      this.hV = "0" + unusedBits + h;
    };
    this.setByBooleanArray = function(booleanArray) {
      var s = "";
      for (var i = 0; i < booleanArray.length; i++) {
        if (booleanArray[i] == true) {
          s += "1";
        } else {
          s += "0";
        }
      }
      this.setByBinaryString(s);
    };
    this.newFalseArray = function(nLength) {
      var a = new Array(nLength);
      for (var i = 0; i < nLength; i++) {
        a[i] = false;
      }
      return a;
    };
    this.getFreshValueHex = function() {
      return this.hV;
    };
    if (typeof params != "undefined") {
      if (typeof params == "string" && params.toLowerCase().match(/^[0-9a-f]+$/)) {
        this.setHexValueIncludingUnusedBits(params);
      } else if (typeof params["hex"] != "undefined") {
        this.setHexValueIncludingUnusedBits(params["hex"]);
      } else if (typeof params["bin"] != "undefined") {
        this.setByBinaryString(params["bin"]);
      } else if (typeof params["array"] != "undefined") {
        this.setByBooleanArray(params["array"]);
      }
    }
  };
  YAHOO.lang.extend(KJUR.asn1.DERBitString, KJUR.asn1.ASN1Object);
  KJUR.asn1.DEROctetString = function(params) {
    if (params !== void 0 && typeof params.obj !== "undefined") {
      var o = KJUR.asn1.ASN1Util.newObject(params.obj);
      params.hex = o.getEncodedHex();
    }
    KJUR.asn1.DEROctetString.superclass.constructor.call(this, params);
    this.hT = "04";
  };
  YAHOO.lang.extend(KJUR.asn1.DEROctetString, KJUR.asn1.DERAbstractString);
  KJUR.asn1.DERNull = function() {
    KJUR.asn1.DERNull.superclass.constructor.call(this);
    this.hT = "05";
    this.hTLV = "0500";
  };
  YAHOO.lang.extend(KJUR.asn1.DERNull, KJUR.asn1.ASN1Object);
  KJUR.asn1.DERObjectIdentifier = function(params) {
    var itox = function(i) {
      var h = i.toString(16);
      if (h.length == 1)
        h = "0" + h;
      return h;
    };
    var roidtox = function(roid) {
      var h = "";
      var bi = new BigInteger(roid, 10);
      var b = bi.toString(2);
      var padLen = 7 - b.length % 7;
      if (padLen == 7)
        padLen = 0;
      var bPad = "";
      for (var i = 0; i < padLen; i++)
        bPad += "0";
      b = bPad + b;
      for (var i = 0; i < b.length - 1; i += 7) {
        var b8 = b.substr(i, 7);
        if (i != b.length - 7)
          b8 = "1" + b8;
        h += itox(parseInt(b8, 2));
      }
      return h;
    };
    KJUR.asn1.DERObjectIdentifier.superclass.constructor.call(this);
    this.hT = "06";
    this.setValueHex = function(newHexString) {
      this.hTLV = null;
      this.isModified = true;
      this.s = null;
      this.hV = newHexString;
    };
    this.setValueOidString = function(oidString) {
      if (!oidString.match(/^[0-9.]+$/)) {
        throw "malformed oid string: " + oidString;
      }
      var h = "";
      var a = oidString.split(".");
      var i0 = parseInt(a[0]) * 40 + parseInt(a[1]);
      h += itox(i0);
      a.splice(0, 2);
      for (var i = 0; i < a.length; i++) {
        h += roidtox(a[i]);
      }
      this.hTLV = null;
      this.isModified = true;
      this.s = null;
      this.hV = h;
    };
    this.setValueName = function(oidName) {
      var oid = KJUR.asn1.x509.OID.name2oid(oidName);
      if (oid !== "") {
        this.setValueOidString(oid);
      } else {
        throw "DERObjectIdentifier oidName undefined: " + oidName;
      }
    };
    this.getFreshValueHex = function() {
      return this.hV;
    };
    if (params !== void 0) {
      if (typeof params === "string") {
        if (params.match(/^[0-2].[0-9.]+$/)) {
          this.setValueOidString(params);
        } else {
          this.setValueName(params);
        }
      } else if (params.oid !== void 0) {
        this.setValueOidString(params.oid);
      } else if (params.hex !== void 0) {
        this.setValueHex(params.hex);
      } else if (params.name !== void 0) {
        this.setValueName(params.name);
      }
    }
  };
  YAHOO.lang.extend(KJUR.asn1.DERObjectIdentifier, KJUR.asn1.ASN1Object);
  KJUR.asn1.DEREnumerated = function(params) {
    KJUR.asn1.DEREnumerated.superclass.constructor.call(this);
    this.hT = "0a";
    this.setByBigInteger = function(bigIntegerValue) {
      this.hTLV = null;
      this.isModified = true;
      this.hV = KJUR.asn1.ASN1Util.bigIntToMinTwosComplementsHex(bigIntegerValue);
    };
    this.setByInteger = function(intValue) {
      var bi = new BigInteger(String(intValue), 10);
      this.setByBigInteger(bi);
    };
    this.setValueHex = function(newHexString) {
      this.hV = newHexString;
    };
    this.getFreshValueHex = function() {
      return this.hV;
    };
    if (typeof params != "undefined") {
      if (typeof params["int"] != "undefined") {
        this.setByInteger(params["int"]);
      } else if (typeof params == "number") {
        this.setByInteger(params);
      } else if (typeof params["hex"] != "undefined") {
        this.setValueHex(params["hex"]);
      }
    }
  };
  YAHOO.lang.extend(KJUR.asn1.DEREnumerated, KJUR.asn1.ASN1Object);
  KJUR.asn1.DERUTF8String = function(params) {
    KJUR.asn1.DERUTF8String.superclass.constructor.call(this, params);
    this.hT = "0c";
  };
  YAHOO.lang.extend(KJUR.asn1.DERUTF8String, KJUR.asn1.DERAbstractString);
  KJUR.asn1.DERNumericString = function(params) {
    KJUR.asn1.DERNumericString.superclass.constructor.call(this, params);
    this.hT = "12";
  };
  YAHOO.lang.extend(KJUR.asn1.DERNumericString, KJUR.asn1.DERAbstractString);
  KJUR.asn1.DERPrintableString = function(params) {
    KJUR.asn1.DERPrintableString.superclass.constructor.call(this, params);
    this.hT = "13";
  };
  YAHOO.lang.extend(KJUR.asn1.DERPrintableString, KJUR.asn1.DERAbstractString);
  KJUR.asn1.DERTeletexString = function(params) {
    KJUR.asn1.DERTeletexString.superclass.constructor.call(this, params);
    this.hT = "14";
  };
  YAHOO.lang.extend(KJUR.asn1.DERTeletexString, KJUR.asn1.DERAbstractString);
  KJUR.asn1.DERIA5String = function(params) {
    KJUR.asn1.DERIA5String.superclass.constructor.call(this, params);
    this.hT = "16";
  };
  YAHOO.lang.extend(KJUR.asn1.DERIA5String, KJUR.asn1.DERAbstractString);
  KJUR.asn1.DERUTCTime = function(params) {
    KJUR.asn1.DERUTCTime.superclass.constructor.call(this, params);
    this.hT = "17";
    this.setByDate = function(dateObject) {
      this.hTLV = null;
      this.isModified = true;
      this.date = dateObject;
      this.s = this.formatDate(this.date, "utc");
      this.hV = stohex(this.s);
    };
    this.getFreshValueHex = function() {
      if (typeof this.date == "undefined" && typeof this.s == "undefined") {
        this.date = /* @__PURE__ */ new Date();
        this.s = this.formatDate(this.date, "utc");
        this.hV = stohex(this.s);
      }
      return this.hV;
    };
    if (params !== void 0) {
      if (params.str !== void 0) {
        this.setString(params.str);
      } else if (typeof params == "string" && params.match(/^[0-9]{12}Z$/)) {
        this.setString(params);
      } else if (params.hex !== void 0) {
        this.setStringHex(params.hex);
      } else if (params.date !== void 0) {
        this.setByDate(params.date);
      }
    }
  };
  YAHOO.lang.extend(KJUR.asn1.DERUTCTime, KJUR.asn1.DERAbstractTime);
  KJUR.asn1.DERGeneralizedTime = function(params) {
    KJUR.asn1.DERGeneralizedTime.superclass.constructor.call(this, params);
    this.hT = "18";
    this.withMillis = false;
    this.setByDate = function(dateObject) {
      this.hTLV = null;
      this.isModified = true;
      this.date = dateObject;
      this.s = this.formatDate(this.date, "gen", this.withMillis);
      this.hV = stohex(this.s);
    };
    this.getFreshValueHex = function() {
      if (this.date === void 0 && this.s === void 0) {
        this.date = /* @__PURE__ */ new Date();
        this.s = this.formatDate(this.date, "gen", this.withMillis);
        this.hV = stohex(this.s);
      }
      return this.hV;
    };
    if (params !== void 0) {
      if (params.str !== void 0) {
        this.setString(params.str);
      } else if (typeof params == "string" && params.match(/^[0-9]{14}Z$/)) {
        this.setString(params);
      } else if (params.hex !== void 0) {
        this.setStringHex(params.hex);
      } else if (params.date !== void 0) {
        this.setByDate(params.date);
      }
      if (params.millis === true) {
        this.withMillis = true;
      }
    }
  };
  YAHOO.lang.extend(KJUR.asn1.DERGeneralizedTime, KJUR.asn1.DERAbstractTime);
  KJUR.asn1.DERSequence = function(params) {
    KJUR.asn1.DERSequence.superclass.constructor.call(this, params);
    this.hT = "30";
    this.getFreshValueHex = function() {
      var h = "";
      for (var i = 0; i < this.asn1Array.length; i++) {
        var asn1Obj = this.asn1Array[i];
        h += asn1Obj.getEncodedHex();
      }
      this.hV = h;
      return this.hV;
    };
  };
  YAHOO.lang.extend(KJUR.asn1.DERSequence, KJUR.asn1.DERAbstractStructured);
  KJUR.asn1.DERSet = function(params) {
    KJUR.asn1.DERSet.superclass.constructor.call(this, params);
    this.hT = "31";
    this.sortFlag = true;
    this.getFreshValueHex = function() {
      var a = new Array();
      for (var i = 0; i < this.asn1Array.length; i++) {
        var asn1Obj = this.asn1Array[i];
        a.push(asn1Obj.getEncodedHex());
      }
      if (this.sortFlag == true)
        a.sort();
      this.hV = a.join("");
      return this.hV;
    };
    if (typeof params != "undefined") {
      if (typeof params.sortflag != "undefined" && params.sortflag == false)
        this.sortFlag = false;
    }
  };
  YAHOO.lang.extend(KJUR.asn1.DERSet, KJUR.asn1.DERAbstractStructured);
  KJUR.asn1.DERTaggedObject = function(params) {
    KJUR.asn1.DERTaggedObject.superclass.constructor.call(this);
    this.hT = "a0";
    this.hV = "";
    this.isExplicit = true;
    this.asn1Object = null;
    this.setASN1Object = function(isExplicitFlag, tagNoHex, asn1Object) {
      this.hT = tagNoHex;
      this.isExplicit = isExplicitFlag;
      this.asn1Object = asn1Object;
      if (this.isExplicit) {
        this.hV = this.asn1Object.getEncodedHex();
        this.hTLV = null;
        this.isModified = true;
      } else {
        this.hV = null;
        this.hTLV = asn1Object.getEncodedHex();
        this.hTLV = this.hTLV.replace(/^../, tagNoHex);
        this.isModified = false;
      }
    };
    this.getFreshValueHex = function() {
      return this.hV;
    };
    if (typeof params != "undefined") {
      if (typeof params["tag"] != "undefined") {
        this.hT = params["tag"];
      }
      if (typeof params["explicit"] != "undefined") {
        this.isExplicit = params["explicit"];
      }
      if (typeof params["obj"] != "undefined") {
        this.asn1Object = params["obj"];
        this.setASN1Object(this.isExplicit, this.hT, this.asn1Object);
      }
    }
  };
  YAHOO.lang.extend(KJUR.asn1.DERTaggedObject, KJUR.asn1.ASN1Object);
  var __extends = /* @__PURE__ */ function() {
    var extendStatics = function(d, b) {
      extendStatics = Object.setPrototypeOf || { __proto__: [] } instanceof Array && function(d2, b2) {
        d2.__proto__ = b2;
      } || function(d2, b2) {
        for (var p in b2)
          if (Object.prototype.hasOwnProperty.call(b2, p))
            d2[p] = b2[p];
      };
      return extendStatics(d, b);
    };
    return function(d, b) {
      if (typeof b !== "function" && b !== null)
        throw new TypeError("Class extends value " + String(b) + " is not a constructor or null");
      extendStatics(d, b);
      function __() {
        this.constructor = d;
      }
      d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
  }();
  var JSEncryptRSAKey = (
    /** @class */
    function(_super) {
      __extends(JSEncryptRSAKey2, _super);
      function JSEncryptRSAKey2(key) {
        var _this = _super.call(this) || this;
        if (key) {
          if (typeof key === "string") {
            _this.parseKey(key);
          } else if (JSEncryptRSAKey2.hasPrivateKeyProperty(key) || JSEncryptRSAKey2.hasPublicKeyProperty(key)) {
            _this.parsePropertiesFrom(key);
          }
        }
        return _this;
      }
      JSEncryptRSAKey2.prototype.parseKey = function(pem) {
        try {
          var modulus = 0;
          var public_exponent = 0;
          var reHex = /^\s*(?:[0-9A-Fa-f][0-9A-Fa-f]\s*)+$/;
          var der = reHex.test(pem) ? Hex.decode(pem) : Base64.unarmor(pem);
          var asn1 = ASN1.decode(der);
          if (asn1.sub.length === 3) {
            asn1 = asn1.sub[2].sub[0];
          }
          if (asn1.sub.length === 9) {
            modulus = asn1.sub[1].getHexStringValue();
            this.n = parseBigInt(modulus, 16);
            public_exponent = asn1.sub[2].getHexStringValue();
            this.e = parseInt(public_exponent, 16);
            var private_exponent = asn1.sub[3].getHexStringValue();
            this.d = parseBigInt(private_exponent, 16);
            var prime1 = asn1.sub[4].getHexStringValue();
            this.p = parseBigInt(prime1, 16);
            var prime2 = asn1.sub[5].getHexStringValue();
            this.q = parseBigInt(prime2, 16);
            var exponent1 = asn1.sub[6].getHexStringValue();
            this.dmp1 = parseBigInt(exponent1, 16);
            var exponent2 = asn1.sub[7].getHexStringValue();
            this.dmq1 = parseBigInt(exponent2, 16);
            var coefficient = asn1.sub[8].getHexStringValue();
            this.coeff = parseBigInt(coefficient, 16);
          } else if (asn1.sub.length === 2) {
            if (asn1.sub[0].sub) {
              var bit_string = asn1.sub[1];
              var sequence = bit_string.sub[0];
              modulus = sequence.sub[0].getHexStringValue();
              this.n = parseBigInt(modulus, 16);
              public_exponent = sequence.sub[1].getHexStringValue();
              this.e = parseInt(public_exponent, 16);
            } else {
              modulus = asn1.sub[0].getHexStringValue();
              this.n = parseBigInt(modulus, 16);
              public_exponent = asn1.sub[1].getHexStringValue();
              this.e = parseInt(public_exponent, 16);
            }
          } else {
            return false;
          }
          return true;
        } catch (ex) {
          return false;
        }
      };
      JSEncryptRSAKey2.prototype.getPrivateBaseKey = function() {
        var options = {
          array: [
            new KJUR.asn1.DERInteger({ int: 0 }),
            new KJUR.asn1.DERInteger({ bigint: this.n }),
            new KJUR.asn1.DERInteger({ int: this.e }),
            new KJUR.asn1.DERInteger({ bigint: this.d }),
            new KJUR.asn1.DERInteger({ bigint: this.p }),
            new KJUR.asn1.DERInteger({ bigint: this.q }),
            new KJUR.asn1.DERInteger({ bigint: this.dmp1 }),
            new KJUR.asn1.DERInteger({ bigint: this.dmq1 }),
            new KJUR.asn1.DERInteger({ bigint: this.coeff })
          ]
        };
        var seq = new KJUR.asn1.DERSequence(options);
        return seq.getEncodedHex();
      };
      JSEncryptRSAKey2.prototype.getPrivateBaseKeyB64 = function() {
        return hex2b64(this.getPrivateBaseKey());
      };
      JSEncryptRSAKey2.prototype.getPublicBaseKey = function() {
        var first_sequence = new KJUR.asn1.DERSequence({
          array: [
            new KJUR.asn1.DERObjectIdentifier({ oid: "1.2.840.113549.1.1.1" }),
            new KJUR.asn1.DERNull()
          ]
        });
        var second_sequence = new KJUR.asn1.DERSequence({
          array: [
            new KJUR.asn1.DERInteger({ bigint: this.n }),
            new KJUR.asn1.DERInteger({ int: this.e })
          ]
        });
        var bit_string = new KJUR.asn1.DERBitString({
          hex: "00" + second_sequence.getEncodedHex()
        });
        var seq = new KJUR.asn1.DERSequence({
          array: [first_sequence, bit_string]
        });
        return seq.getEncodedHex();
      };
      JSEncryptRSAKey2.prototype.getPublicBaseKeyB64 = function() {
        return hex2b64(this.getPublicBaseKey());
      };
      JSEncryptRSAKey2.wordwrap = function(str, width) {
        width = width || 64;
        if (!str) {
          return str;
        }
        var regex = "(.{1," + width + "})( +|$\n?)|(.{1," + width + "})";
        return str.match(RegExp(regex, "g")).join("\n");
      };
      JSEncryptRSAKey2.prototype.getPrivateKey = function() {
        var key = "-----BEGIN RSA PRIVATE KEY-----\n";
        key += JSEncryptRSAKey2.wordwrap(this.getPrivateBaseKeyB64()) + "\n";
        key += "-----END RSA PRIVATE KEY-----";
        return key;
      };
      JSEncryptRSAKey2.prototype.getPublicKey = function() {
        var key = "-----BEGIN PUBLIC KEY-----\n";
        key += JSEncryptRSAKey2.wordwrap(this.getPublicBaseKeyB64()) + "\n";
        key += "-----END PUBLIC KEY-----";
        return key;
      };
      JSEncryptRSAKey2.hasPublicKeyProperty = function(obj) {
        obj = obj || {};
        return obj.hasOwnProperty("n") && obj.hasOwnProperty("e");
      };
      JSEncryptRSAKey2.hasPrivateKeyProperty = function(obj) {
        obj = obj || {};
        return obj.hasOwnProperty("n") && obj.hasOwnProperty("e") && obj.hasOwnProperty("d") && obj.hasOwnProperty("p") && obj.hasOwnProperty("q") && obj.hasOwnProperty("dmp1") && obj.hasOwnProperty("dmq1") && obj.hasOwnProperty("coeff");
      };
      JSEncryptRSAKey2.prototype.parsePropertiesFrom = function(obj) {
        this.n = obj.n;
        this.e = obj.e;
        if (obj.hasOwnProperty("d")) {
          this.d = obj.d;
          this.p = obj.p;
          this.q = obj.q;
          this.dmp1 = obj.dmp1;
          this.dmq1 = obj.dmq1;
          this.coeff = obj.coeff;
        }
      };
      return JSEncryptRSAKey2;
    }(RSAKey)
  );
  var define_process_env_default = {};
  var _a;
  var version = typeof process !== "undefined" ? (_a = define_process_env_default) === null || _a === void 0 ? void 0 : _a.npm_package_version : void 0;
  var JSEncrypt = (
    /** @class */
    function() {
      function JSEncrypt2(options) {
        if (options === void 0) {
          options = {};
        }
        options = options || {};
        this.default_key_size = options.default_key_size ? parseInt(options.default_key_size, 10) : 1024;
        this.default_public_exponent = options.default_public_exponent || "010001";
        this.log = options.log || false;
        this.key = null;
      }
      JSEncrypt2.prototype.setKey = function(key) {
        if (this.log && this.key) {
          formatAppLog("warn", "at node_modules/jsencrypt/lib/JSEncrypt.js:37", "A key was already set, overriding existing.");
        }
        this.key = new JSEncryptRSAKey(key);
      };
      JSEncrypt2.prototype.setPrivateKey = function(privkey) {
        this.setKey(privkey);
      };
      JSEncrypt2.prototype.setPublicKey = function(pubkey) {
        this.setKey(pubkey);
      };
      JSEncrypt2.prototype.decrypt = function(str) {
        try {
          return this.getKey().decrypt(b64tohex(str));
        } catch (ex) {
          return false;
        }
      };
      JSEncrypt2.prototype.encrypt = function(str) {
        try {
          return hex2b64(this.getKey().encrypt(str));
        } catch (ex) {
          return false;
        }
      };
      JSEncrypt2.prototype.sign = function(str, digestMethod, digestName) {
        try {
          return hex2b64(this.getKey().sign(str, digestMethod, digestName));
        } catch (ex) {
          return false;
        }
      };
      JSEncrypt2.prototype.verify = function(str, signature, digestMethod) {
        try {
          return this.getKey().verify(str, b64tohex(signature), digestMethod);
        } catch (ex) {
          return false;
        }
      };
      JSEncrypt2.prototype.getKey = function(cb) {
        if (!this.key) {
          this.key = new JSEncryptRSAKey();
          if (cb && {}.toString.call(cb) === "[object Function]") {
            this.key.generateAsync(this.default_key_size, this.default_public_exponent, cb);
            return;
          }
          this.key.generate(this.default_key_size, this.default_public_exponent);
        }
        return this.key;
      };
      JSEncrypt2.prototype.getPrivateKey = function() {
        return this.getKey().getPrivateKey();
      };
      JSEncrypt2.prototype.getPrivateKeyB64 = function() {
        return this.getKey().getPrivateBaseKeyB64();
      };
      JSEncrypt2.prototype.getPublicKey = function() {
        return this.getKey().getPublicKey();
      };
      JSEncrypt2.prototype.getPublicKeyB64 = function() {
        return this.getKey().getPublicBaseKeyB64();
      };
      JSEncrypt2.version = version;
      return JSEncrypt2;
    }()
  );
  async function getPublicKey() {
    const res = await request({
      url: "/publicKey",
      method: "GET",
      header: {
        "ngrok-skip-browser-warning": "true"
        //测试 添加请求头绕过ngrok拦截
      }
    });
    formatAppLog("log", "at utils/rsa.js:16", "公钥接口完整响应:", res.publicKey);
    return res.publicKey;
  }
  function encryptWithRSA(publicKey, text) {
    const encryptor = new JSEncrypt();
    encryptor.setPublicKey(publicKey);
    return encryptor.encrypt(text);
  }
  const _imports_0$1 = "/static/nan.svg";
  const _imports_1 = "/static/nv.svg";
  const _sfc_main$6 = {
    __name: "register",
    setup(__props, { expose: __expose }) {
      __expose();
      const strengthLevel = vue.ref(0);
      const ShowStrenth = vue.ref(false);
      const authStore = useAuthStore();
      const avatar = vue.ref("/static/touxiang.svg");
      const phone = vue.ref("");
      const nickname = vue.ref("");
      const password = vue.ref("");
      const confirmPassword = vue.ref("");
      const gender = vue.ref("");
      const birthday = vue.ref("");
      const isFormValid = vue.computed(() => {
        return nickname.value.trim() !== "" && gender.value !== "" && password.value.length !== 0 && confirmPassword.value.length !== 0;
      });
      const handleBirthdayChange = (date) => {
        birthday.value = date;
      };
      const handelNext = async () => {
        if (password.value !== confirmPassword.value) {
          uni.showToast({
            title: "两次密码不一致",
            icon: "none"
          });
        }
        let publicKey;
        try {
          publicKey = await getPublicKey();
        } catch (error) {
          uni.showToast({
            title: "获取加密密钥失败",
            icon: "none"
          });
          return;
        }
        let encryptedPwd;
        try {
          encryptedPwd = encryptWithRSA(publicKey, password.value);
        } catch (error) {
          uni.showToast({
            title: "密码加密失败",
            icon: "none"
          });
          return;
        }
        const res = await apiCompleteProfile({
          userId: authStore.userId,
          // 从store获取用户ID
          nickname: nickname.value,
          password: encryptedPwd,
          avatar: avatar.value,
          gender: gender.value,
          birthday: birthday.value
        });
        if (res.code === 200) {
          uni.showToast({
            title: "注册成功",
            icon: "success"
          });
          uni.navigateTo({
            url: "/pages/petSelection/petSelection"
          });
        }
      };
      const strengthText = vue.computed(() => {
        if (strengthLevel.value === 0)
          return "弱";
        if (strengthLevel.value === 1)
          return "弱";
        if (strengthLevel.value === 2)
          return "中";
        return "强";
      });
      const checkPasswordStrength = () => {
        password.value = password.value.replace(/[^a-zA-Z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/g, "");
        const pass = password.value;
        if (pass.length > 7) {
          ShowStrenth.value = true;
        } else {
          ShowStrenth.value = false;
        }
        if (!pass || pass.length < 8) {
          strengthLevel.value = 0;
          return;
        }
        let score = 0;
        if (pass.length >= 5)
          score += 1;
        if (pass.length >= 7)
          score += 1;
        if (/[a-z]/.test(pass))
          score += 1;
        if (/[A-Z]/.test(pass))
          score += 1;
        if (/\d/.test(pass))
          score += 1;
        if (/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(pass))
          score += 2;
        if (score <= 3)
          strengthLevel.value = 1;
        else if (score <= 6)
          strengthLevel.value = 2;
        else
          strengthLevel.value = 3;
      };
      const UploadImage = () => {
        uni.chooseImage({
          count: 1,
          success(res) {
            avatar.value = res.tempFilePaths[0];
          }
        });
      };
      const __returned__ = { strengthLevel, ShowStrenth, authStore, avatar, phone, nickname, password, confirmPassword, gender, birthday, isFormValid, handleBirthdayChange, handelNext, strengthText, checkPasswordStrength, UploadImage, DatePicker, onMounted: vue.onMounted, computed: vue.computed, ref: vue.ref, get useAuthStore() {
        return useAuthStore;
      }, get apiCompleteProfile() {
        return apiCompleteProfile;
      }, get getPublicKey() {
        return getPublicKey;
      }, get encryptWithRSA() {
        return encryptWithRSA;
      } };
      Object.defineProperty(__returned__, "__isScriptSetup", { enumerable: false, value: true });
      return __returned__;
    }
  };
  function _sfc_render$5(_ctx, _cache, $props, $setup, $data, $options) {
    return vue.openBlock(), vue.createElementBlock("view", { class: "profile-container" }, [
      vue.createCommentVNode(" 头像上传 "),
      vue.createElementVNode("view", {
        class: "avatar-upload",
        onClick: $setup.UploadImage
      }, [
        vue.createElementVNode("image", {
          src: $setup.avatar,
          class: "avatar"
        }, null, 8, ["src"]),
        vue.createCommentVNode(' <image src="/static/camera-icon.svg" class="camera-icon" /> ')
      ]),
      vue.createCommentVNode(" 表单区域 "),
      vue.createElementVNode("view", { class: "form-group" }, [
        vue.createCommentVNode(" 手机号"),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "手机号:"),
          vue.withDirectives(vue.createElementVNode(
            "input",
            {
              "onUpdate:modelValue": _cache[0] || (_cache[0] = ($event) => $setup.authStore.phone = $event),
              placeholder: "请输入",
              maxlength: "11",
              disabled: ""
            },
            null,
            512
            /* NEED_PATCH */
          ), [
            [vue.vModelText, $setup.authStore.phone]
          ])
        ]),
        vue.createCommentVNode(" 昵称 "),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "昵称:"),
          vue.withDirectives(vue.createElementVNode(
            "input",
            {
              "onUpdate:modelValue": _cache[1] || (_cache[1] = ($event) => $setup.nickname = $event),
              placeholder: "请输入",
              maxlength: "10"
            },
            null,
            512
            /* NEED_PATCH */
          ), [
            [vue.vModelText, $setup.nickname]
          ])
        ]),
        vue.createCommentVNode(" 性别 "),
        vue.createCommentVNode(" 性别选择区域 "),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "性别:"),
          vue.createElementVNode("view", { class: "gender-container" }, [
            vue.createCommentVNode(" 男性选项 "),
            vue.createElementVNode("view", { class: "gender-option" }, [
              vue.createElementVNode(
                "view",
                {
                  class: vue.normalizeClass(["sex-male", { active: $setup.gender === "0" }])
                },
                [
                  vue.createElementVNode("image", {
                    src: _imports_0$1,
                    class: "gender-icon"
                  })
                ],
                2
                /* CLASS */
              ),
              vue.createElementVNode(
                "text",
                {
                  class: vue.normalizeClass(["gender-tag male", { active: $setup.gender === "0" }]),
                  onClick: _cache[2] || (_cache[2] = ($event) => $setup.gender = "0")
                },
                "男",
                2
                /* CLASS */
              )
            ]),
            vue.createCommentVNode(" 女性选项 "),
            vue.createElementVNode("view", { class: "gender-option" }, [
              vue.createElementVNode(
                "view",
                {
                  class: vue.normalizeClass(["sex-female", { active: $setup.gender === "1" }])
                },
                [
                  vue.createElementVNode("image", {
                    src: _imports_1,
                    class: "gender-icon"
                  })
                ],
                2
                /* CLASS */
              ),
              vue.createElementVNode(
                "text",
                {
                  class: vue.normalizeClass(["gender-tag female", { active: $setup.gender === "1" }]),
                  onClick: _cache[3] || (_cache[3] = ($event) => $setup.gender = "1")
                },
                "女",
                2
                /* CLASS */
              )
            ])
          ])
        ]),
        vue.createCommentVNode(" 生日"),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "您的生日:"),
          vue.createVNode($setup["DatePicker"], { onDateChange: $setup.handleBirthdayChange })
        ]),
        vue.createCommentVNode(" 密码 "),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "密码:"),
          vue.withDirectives(vue.createElementVNode(
            "input",
            {
              "onUpdate:modelValue": _cache[4] || (_cache[4] = ($event) => $setup.password = $event),
              placeholder: "请输入",
              onInput: $setup.checkPasswordStrength,
              maxlength: "10"
            },
            null,
            544
            /* NEED_HYDRATION, NEED_PATCH */
          ), [
            [vue.vModelText, $setup.password]
          ])
        ]),
        vue.createCommentVNode(" 密码强度 "),
        $setup.ShowStrenth ? (vue.openBlock(), vue.createElementBlock("view", {
          key: 0,
          class: "password-strength"
        }, [
          vue.createElementVNode(
            "view",
            {
              class: vue.normalizeClass(["strength-bar", {
                "weak": $setup.strengthLevel === 1,
                "medium": $setup.strengthLevel === 2,
                "strong": $setup.strengthLevel === 3,
                "active": $setup.strengthLevel >= 1
              }])
            },
            null,
            2
            /* CLASS */
          ),
          vue.createElementVNode(
            "view",
            {
              class: vue.normalizeClass(["strength-bar", {
                "medium": $setup.strengthLevel === 2,
                "strong": $setup.strengthLevel === 3,
                "active": $setup.strengthLevel >= 2
              }])
            },
            null,
            2
            /* CLASS */
          ),
          vue.createElementVNode(
            "view",
            {
              class: vue.normalizeClass(["strength-bar", {
                "strong": $setup.strengthLevel === 3,
                "active": $setup.strengthLevel >= 3
              }])
            },
            null,
            2
            /* CLASS */
          ),
          vue.createElementVNode(
            "text",
            { class: "strength-text" },
            vue.toDisplayString($setup.strengthText),
            1
            /* TEXT */
          )
        ])) : vue.createCommentVNode("v-if", true),
        vue.createCommentVNode(" 密码确认 "),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "密码确认:"),
          vue.withDirectives(vue.createElementVNode(
            "input",
            {
              "onUpdate:modelValue": _cache[5] || (_cache[5] = ($event) => $setup.confirmPassword = $event),
              placeholder: "请输入",
              maxlength: "10"
            },
            null,
            512
            /* NEED_PATCH */
          ), [
            [vue.vModelText, $setup.confirmPassword]
          ])
        ])
      ]),
      vue.createCommentVNode(" 下一步按钮 "),
      vue.createElementVNode(
        "button",
        {
          class: vue.normalizeClass(["next-btn", { active: $setup.isFormValid }]),
          onClick: $setup.handelNext
        },
        "下一步",
        2
        /* CLASS */
      )
    ]);
  }
  const PagesLoginRegister = /* @__PURE__ */ _export_sfc(_sfc_main$6, [["render", _sfc_render$5], ["__scopeId", "data-v-838b72c9"], ["__file", "E:/ChongAi/ChongAiApp/pages/login/register.vue"]]);
  const _sfc_main$5 = {
    name: "UniStatusBar",
    data() {
      return {
        statusBarHeight: uni.getSystemInfoSync().statusBarHeight + "px"
      };
    }
  };
  function _sfc_render$4(_ctx, _cache, $props, $setup, $data, $options) {
    return vue.openBlock(), vue.createElementBlock(
      "view",
      {
        style: vue.normalizeStyle({ height: $data.statusBarHeight }),
        class: "uni-status-bar"
      },
      [
        vue.renderSlot(_ctx.$slots, "default", {}, void 0, true)
      ],
      4
      /* STYLE */
    );
  }
  const statusBar = /* @__PURE__ */ _export_sfc(_sfc_main$5, [["render", _sfc_render$4], ["__scopeId", "data-v-7920e3e0"], ["__file", "E:/ChongAi/ChongAiApp/uni_modules/uni-nav-bar/components/uni-nav-bar/uni-status-bar.vue"]]);
  const getVal = (val) => typeof val === "number" ? val + "px" : val;
  const _sfc_main$4 = {
    name: "UniNavBar",
    components: {
      statusBar
    },
    emits: ["clickLeft", "clickRight", "clickTitle"],
    props: {
      dark: {
        type: Boolean,
        default: false
      },
      title: {
        type: String,
        default: ""
      },
      leftText: {
        type: String,
        default: ""
      },
      rightText: {
        type: String,
        default: ""
      },
      leftIcon: {
        type: String,
        default: ""
      },
      rightIcon: {
        type: String,
        default: ""
      },
      fixed: {
        type: [Boolean, String],
        default: false
      },
      color: {
        type: String,
        default: ""
      },
      backgroundColor: {
        type: String,
        default: ""
      },
      statusBar: {
        type: [Boolean, String],
        default: false
      },
      shadow: {
        type: [Boolean, String],
        default: false
      },
      border: {
        type: [Boolean, String],
        default: true
      },
      height: {
        type: [Number, String],
        default: 44
      },
      leftWidth: {
        type: [Number, String],
        default: 60
      },
      rightWidth: {
        type: [Number, String],
        default: 60
      },
      stat: {
        type: [Boolean, String],
        default: ""
      }
    },
    data() {
      return {
        navWidth: "auto"
      };
    },
    computed: {
      themeBgColor() {
        if (this.dark) {
          if (this.backgroundColor) {
            return this.backgroundColor;
          } else {
            return this.dark ? "#333" : "#FFF";
          }
        }
        return this.backgroundColor || "#FFF";
      },
      themeColor() {
        if (this.dark) {
          if (this.color) {
            return this.color;
          } else {
            return this.dark ? "#fff" : "#333";
          }
        }
        return this.color || "#333";
      },
      navbarHeight() {
        if (this.fixed && this.height === 0) {
          return getVal(44);
        }
        return getVal(this.height);
      },
      leftIconWidth() {
        return getVal(this.leftWidth);
      },
      rightIconWidth() {
        return getVal(this.rightWidth);
      }
    },
    created() {
    },
    mounted() {
      if (uni.report && this.stat && this.title !== "") {
        uni.report("title", this.title);
      }
    },
    methods: {
      onClickLeft() {
        this.$emit("clickLeft");
      },
      onClickRight() {
        this.$emit("clickRight");
      },
      onClickTitle() {
        this.$emit("clickTitle");
      }
    }
  };
  function _sfc_render$3(_ctx, _cache, $props, $setup, $data, $options) {
    const _component_status_bar = vue.resolveComponent("status-bar");
    const _component_uni_icons = resolveEasycom(vue.resolveDynamicComponent("uni-icons"), __easycom_0);
    return vue.openBlock(), vue.createElementBlock(
      "view",
      {
        class: vue.normalizeClass(["uni-navbar", { "uni-dark": $props.dark, "uni-nvue-fixed": $props.fixed }])
      },
      [
        vue.createElementVNode(
          "view",
          {
            class: vue.normalizeClass(["uni-navbar__content", { "uni-navbar--fixed": $props.fixed, "uni-navbar--shadow": $props.shadow, "uni-navbar--border": $props.border }]),
            style: vue.normalizeStyle({ "background-color": $options.themeBgColor })
          },
          [
            $props.statusBar ? (vue.openBlock(), vue.createBlock(_component_status_bar, { key: 0 })) : vue.createCommentVNode("v-if", true),
            vue.createElementVNode(
              "view",
              {
                style: vue.normalizeStyle({ color: $options.themeColor, backgroundColor: $options.themeBgColor, height: $options.navbarHeight, width: $data.navWidth + "px" }),
                class: "uni-navbar__header"
              },
              [
                vue.createElementVNode(
                  "view",
                  {
                    onClick: _cache[0] || (_cache[0] = (...args) => $options.onClickLeft && $options.onClickLeft(...args)),
                    class: "uni-navbar__header-btns uni-navbar__header-btns-left",
                    style: vue.normalizeStyle({ width: $options.leftIconWidth })
                  },
                  [
                    vue.renderSlot(_ctx.$slots, "left", {}, () => [
                      $props.leftIcon.length > 0 ? (vue.openBlock(), vue.createElementBlock("view", {
                        key: 0,
                        class: "uni-navbar__content_view"
                      }, [
                        vue.createVNode(_component_uni_icons, {
                          color: $options.themeColor,
                          type: $props.leftIcon,
                          size: "20"
                        }, null, 8, ["color", "type"])
                      ])) : vue.createCommentVNode("v-if", true),
                      $props.leftText.length ? (vue.openBlock(), vue.createElementBlock(
                        "view",
                        {
                          key: 1,
                          class: vue.normalizeClass([{ "uni-navbar-btn-icon-left": !$props.leftIcon.length > 0 }, "uni-navbar-btn-text"])
                        },
                        [
                          vue.createElementVNode(
                            "text",
                            {
                              style: vue.normalizeStyle({ color: $options.themeColor, fontSize: "12px" })
                            },
                            vue.toDisplayString($props.leftText),
                            5
                            /* TEXT, STYLE */
                          )
                        ],
                        2
                        /* CLASS */
                      )) : vue.createCommentVNode("v-if", true)
                    ], true)
                  ],
                  4
                  /* STYLE */
                ),
                vue.createElementVNode("view", {
                  class: "uni-navbar__header-container",
                  onClick: _cache[1] || (_cache[1] = (...args) => $options.onClickTitle && $options.onClickTitle(...args))
                }, [
                  vue.renderSlot(_ctx.$slots, "default", {}, () => [
                    $props.title.length > 0 ? (vue.openBlock(), vue.createElementBlock("view", {
                      key: 0,
                      class: "uni-navbar__header-container-inner"
                    }, [
                      vue.createElementVNode(
                        "text",
                        {
                          class: "uni-nav-bar-text uni-ellipsis-1",
                          style: vue.normalizeStyle({ color: $options.themeColor })
                        },
                        vue.toDisplayString($props.title),
                        5
                        /* TEXT, STYLE */
                      )
                    ])) : vue.createCommentVNode("v-if", true)
                  ], true)
                ]),
                vue.createElementVNode(
                  "view",
                  {
                    onClick: _cache[2] || (_cache[2] = (...args) => $options.onClickRight && $options.onClickRight(...args)),
                    class: "uni-navbar__header-btns uni-navbar__header-btns-right",
                    style: vue.normalizeStyle({ width: $options.rightIconWidth })
                  },
                  [
                    vue.renderSlot(_ctx.$slots, "right", {}, () => [
                      $props.rightIcon.length ? (vue.openBlock(), vue.createElementBlock("view", { key: 0 }, [
                        vue.createVNode(_component_uni_icons, {
                          color: $options.themeColor,
                          type: $props.rightIcon,
                          size: "22"
                        }, null, 8, ["color", "type"])
                      ])) : vue.createCommentVNode("v-if", true),
                      $props.rightText.length && !$props.rightIcon.length ? (vue.openBlock(), vue.createElementBlock("view", {
                        key: 1,
                        class: "uni-navbar-btn-text"
                      }, [
                        vue.createElementVNode(
                          "text",
                          {
                            class: "uni-nav-bar-right-text",
                            style: vue.normalizeStyle({ color: $options.themeColor })
                          },
                          vue.toDisplayString($props.rightText),
                          5
                          /* TEXT, STYLE */
                        )
                      ])) : vue.createCommentVNode("v-if", true)
                    ], true)
                  ],
                  4
                  /* STYLE */
                )
              ],
              4
              /* STYLE */
            )
          ],
          6
          /* CLASS, STYLE */
        ),
        $props.fixed ? (vue.openBlock(), vue.createElementBlock("view", {
          key: 0,
          class: "uni-navbar__placeholder"
        }, [
          $props.statusBar ? (vue.openBlock(), vue.createBlock(_component_status_bar, { key: 0 })) : vue.createCommentVNode("v-if", true),
          vue.createElementVNode(
            "view",
            {
              class: "uni-navbar__placeholder-view",
              style: vue.normalizeStyle({ height: $options.navbarHeight })
            },
            null,
            4
            /* STYLE */
          )
        ])) : vue.createCommentVNode("v-if", true)
      ],
      2
      /* CLASS */
    );
  }
  const __easycom_1 = /* @__PURE__ */ _export_sfc(_sfc_main$4, [["render", _sfc_render$3], ["__scopeId", "data-v-26544265"], ["__file", "E:/ChongAi/ChongAiApp/uni_modules/uni-nav-bar/components/uni-nav-bar/uni-nav-bar.vue"]]);
  const isObject = (val) => val !== null && typeof val === "object";
  const defaultDelimiters = ["{", "}"];
  class BaseFormatter {
    constructor() {
      this._caches = /* @__PURE__ */ Object.create(null);
    }
    interpolate(message, values, delimiters = defaultDelimiters) {
      if (!values) {
        return [message];
      }
      let tokens = this._caches[message];
      if (!tokens) {
        tokens = parse(message, delimiters);
        this._caches[message] = tokens;
      }
      return compile(tokens, values);
    }
  }
  const RE_TOKEN_LIST_VALUE = /^(?:\d)+/;
  const RE_TOKEN_NAMED_VALUE = /^(?:\w)+/;
  function parse(format, [startDelimiter, endDelimiter]) {
    const tokens = [];
    let position = 0;
    let text = "";
    while (position < format.length) {
      let char = format[position++];
      if (char === startDelimiter) {
        if (text) {
          tokens.push({ type: "text", value: text });
        }
        text = "";
        let sub = "";
        char = format[position++];
        while (char !== void 0 && char !== endDelimiter) {
          sub += char;
          char = format[position++];
        }
        const isClosed = char === endDelimiter;
        const type = RE_TOKEN_LIST_VALUE.test(sub) ? "list" : isClosed && RE_TOKEN_NAMED_VALUE.test(sub) ? "named" : "unknown";
        tokens.push({ value: sub, type });
      } else {
        text += char;
      }
    }
    text && tokens.push({ type: "text", value: text });
    return tokens;
  }
  function compile(tokens, values) {
    const compiled = [];
    let index = 0;
    const mode = Array.isArray(values) ? "list" : isObject(values) ? "named" : "unknown";
    if (mode === "unknown") {
      return compiled;
    }
    while (index < tokens.length) {
      const token = tokens[index];
      switch (token.type) {
        case "text":
          compiled.push(token.value);
          break;
        case "list":
          compiled.push(values[parseInt(token.value, 10)]);
          break;
        case "named":
          if (mode === "named") {
            compiled.push(values[token.value]);
          } else {
            {
              console.warn(`Type of token '${token.type}' and format of value '${mode}' don't match!`);
            }
          }
          break;
        case "unknown":
          {
            console.warn(`Detect 'unknown' type of token!`);
          }
          break;
      }
      index++;
    }
    return compiled;
  }
  const LOCALE_ZH_HANS = "zh-Hans";
  const LOCALE_ZH_HANT = "zh-Hant";
  const LOCALE_EN = "en";
  const LOCALE_FR = "fr";
  const LOCALE_ES = "es";
  const hasOwnProperty = Object.prototype.hasOwnProperty;
  const hasOwn = (val, key) => hasOwnProperty.call(val, key);
  const defaultFormatter = new BaseFormatter();
  function include(str, parts) {
    return !!parts.find((part) => str.indexOf(part) !== -1);
  }
  function startsWith(str, parts) {
    return parts.find((part) => str.indexOf(part) === 0);
  }
  function normalizeLocale(locale, messages2) {
    if (!locale) {
      return;
    }
    locale = locale.trim().replace(/_/g, "-");
    if (messages2 && messages2[locale]) {
      return locale;
    }
    locale = locale.toLowerCase();
    if (locale === "chinese") {
      return LOCALE_ZH_HANS;
    }
    if (locale.indexOf("zh") === 0) {
      if (locale.indexOf("-hans") > -1) {
        return LOCALE_ZH_HANS;
      }
      if (locale.indexOf("-hant") > -1) {
        return LOCALE_ZH_HANT;
      }
      if (include(locale, ["-tw", "-hk", "-mo", "-cht"])) {
        return LOCALE_ZH_HANT;
      }
      return LOCALE_ZH_HANS;
    }
    let locales = [LOCALE_EN, LOCALE_FR, LOCALE_ES];
    if (messages2 && Object.keys(messages2).length > 0) {
      locales = Object.keys(messages2);
    }
    const lang = startsWith(locale, locales);
    if (lang) {
      return lang;
    }
  }
  class I18n {
    constructor({ locale, fallbackLocale, messages: messages2, watcher, formater: formater2 }) {
      this.locale = LOCALE_EN;
      this.fallbackLocale = LOCALE_EN;
      this.message = {};
      this.messages = {};
      this.watchers = [];
      if (fallbackLocale) {
        this.fallbackLocale = fallbackLocale;
      }
      this.formater = formater2 || defaultFormatter;
      this.messages = messages2 || {};
      this.setLocale(locale || LOCALE_EN);
      if (watcher) {
        this.watchLocale(watcher);
      }
    }
    setLocale(locale) {
      const oldLocale = this.locale;
      this.locale = normalizeLocale(locale, this.messages) || this.fallbackLocale;
      if (!this.messages[this.locale]) {
        this.messages[this.locale] = {};
      }
      this.message = this.messages[this.locale];
      if (oldLocale !== this.locale) {
        this.watchers.forEach((watcher) => {
          watcher(this.locale, oldLocale);
        });
      }
    }
    getLocale() {
      return this.locale;
    }
    watchLocale(fn) {
      const index = this.watchers.push(fn) - 1;
      return () => {
        this.watchers.splice(index, 1);
      };
    }
    add(locale, message, override = true) {
      const curMessages = this.messages[locale];
      if (curMessages) {
        if (override) {
          Object.assign(curMessages, message);
        } else {
          Object.keys(message).forEach((key) => {
            if (!hasOwn(curMessages, key)) {
              curMessages[key] = message[key];
            }
          });
        }
      } else {
        this.messages[locale] = message;
      }
    }
    f(message, values, delimiters) {
      return this.formater.interpolate(message, values, delimiters).join("");
    }
    t(key, locale, values) {
      let message = this.message;
      if (typeof locale === "string") {
        locale = normalizeLocale(locale, this.messages);
        locale && (message = this.messages[locale]);
      } else {
        values = locale;
      }
      if (!hasOwn(message, key)) {
        console.warn(`Cannot translate the value of keypath ${key}. Use the value of keypath as default.`);
        return key;
      }
      return this.formater.interpolate(message[key], values).join("");
    }
  }
  function watchAppLocale(appVm, i18n) {
    if (appVm.$watchLocale) {
      appVm.$watchLocale((newLocale) => {
        i18n.setLocale(newLocale);
      });
    } else {
      appVm.$watch(() => appVm.$locale, (newLocale) => {
        i18n.setLocale(newLocale);
      });
    }
  }
  function getDefaultLocale() {
    if (typeof uni !== "undefined" && uni.getLocale) {
      return uni.getLocale();
    }
    if (typeof global !== "undefined" && global.getLocale) {
      return global.getLocale();
    }
    return LOCALE_EN;
  }
  function initVueI18n(locale, messages2 = {}, fallbackLocale, watcher) {
    if (typeof locale !== "string") {
      const options = [
        messages2,
        locale
      ];
      locale = options[0];
      messages2 = options[1];
    }
    if (typeof locale !== "string") {
      locale = getDefaultLocale();
    }
    if (typeof fallbackLocale !== "string") {
      fallbackLocale = typeof __uniConfig !== "undefined" && __uniConfig.fallbackLocale || LOCALE_EN;
    }
    const i18n = new I18n({
      locale,
      fallbackLocale,
      messages: messages2,
      watcher
    });
    let t2 = (key, values) => {
      if (typeof getApp !== "function") {
        t2 = function(key2, values2) {
          return i18n.t(key2, values2);
        };
      } else {
        let isWatchedAppLocale = false;
        t2 = function(key2, values2) {
          const appVm = getApp().$vm;
          if (appVm) {
            appVm.$locale;
            if (!isWatchedAppLocale) {
              isWatchedAppLocale = true;
              watchAppLocale(appVm, i18n);
            }
          }
          return i18n.t(key2, values2);
        };
      }
      return t2(key, values);
    };
    return {
      i18n,
      f(message, values, delimiters) {
        return i18n.f(message, values, delimiters);
      },
      t(key, values) {
        return t2(key, values);
      },
      add(locale2, message, override = true) {
        return i18n.add(locale2, message, override);
      },
      watch(fn) {
        return i18n.watchLocale(fn);
      },
      getLocale() {
        return i18n.getLocale();
      },
      setLocale(newLocale) {
        return i18n.setLocale(newLocale);
      }
    };
  }
  const en = {
    "uni-search-bar.cancel": "cancel",
    "uni-search-bar.placeholder": "Search enter content"
  };
  const zhHans = {
    "uni-search-bar.cancel": "取消",
    "uni-search-bar.placeholder": "请输入搜索内容"
  };
  const zhHant = {
    "uni-search-bar.cancel": "取消",
    "uni-search-bar.placeholder": "請輸入搜索內容"
  };
  const messages = {
    en,
    "zh-Hans": zhHans,
    "zh-Hant": zhHant
  };
  const {
    t
  } = initVueI18n(messages);
  const _sfc_main$3 = {
    name: "UniSearchBar",
    emits: ["input", "update:modelValue", "clear", "cancel", "confirm", "blur", "focus"],
    props: {
      placeholder: {
        type: String,
        default: ""
      },
      radius: {
        type: [Number, String],
        default: 5
      },
      clearButton: {
        type: String,
        default: "auto"
      },
      cancelButton: {
        type: String,
        default: "auto"
      },
      cancelText: {
        type: String,
        default: ""
      },
      bgColor: {
        type: String,
        default: "#F8F8F8"
      },
      textColor: {
        type: String,
        default: "#000000"
      },
      maxlength: {
        type: [Number, String],
        default: 100
      },
      value: {
        type: [Number, String],
        default: ""
      },
      modelValue: {
        type: [Number, String],
        default: ""
      },
      focus: {
        type: Boolean,
        default: false
      },
      readonly: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        show: false,
        showSync: false,
        searchVal: ""
      };
    },
    computed: {
      cancelTextI18n() {
        return this.cancelText || t("uni-search-bar.cancel");
      },
      placeholderText() {
        return this.placeholder || t("uni-search-bar.placeholder");
      }
    },
    watch: {
      modelValue: {
        immediate: true,
        handler(newVal) {
          this.searchVal = newVal;
          if (newVal) {
            this.show = true;
          }
        }
      },
      focus: {
        immediate: true,
        handler(newVal) {
          if (newVal) {
            if (this.readonly)
              return;
            this.show = true;
            this.$nextTick(() => {
              this.showSync = true;
            });
          }
        }
      },
      searchVal(newVal, oldVal) {
        this.$emit("input", newVal);
        this.$emit("update:modelValue", newVal);
      }
    },
    methods: {
      searchClick() {
        if (this.readonly)
          return;
        if (this.show) {
          return;
        }
        this.show = true;
        this.$nextTick(() => {
          this.showSync = true;
        });
      },
      clear() {
        this.searchVal = "";
        this.$nextTick(() => {
          this.$emit("clear", { value: "" });
        });
      },
      cancel() {
        if (this.readonly)
          return;
        this.$emit("cancel", {
          value: this.searchVal
        });
        this.searchVal = "";
        this.show = false;
        this.showSync = false;
        plus.key.hideSoftKeybord();
      },
      confirm() {
        plus.key.hideSoftKeybord();
        this.$emit("confirm", {
          value: this.searchVal
        });
      },
      blur() {
        plus.key.hideSoftKeybord();
        this.$emit("blur", {
          value: this.searchVal
        });
      },
      emitFocus(e) {
        this.$emit("focus", e.detail);
      }
    }
  };
  function _sfc_render$2(_ctx, _cache, $props, $setup, $data, $options) {
    const _component_uni_icons = resolveEasycom(vue.resolveDynamicComponent("uni-icons"), __easycom_0);
    return vue.openBlock(), vue.createElementBlock("view", { class: "uni-searchbar" }, [
      vue.createElementVNode(
        "view",
        {
          style: vue.normalizeStyle({ borderRadius: $props.radius + "px", backgroundColor: $props.bgColor }),
          class: "uni-searchbar__box",
          onClick: _cache[5] || (_cache[5] = (...args) => $options.searchClick && $options.searchClick(...args))
        },
        [
          vue.createElementVNode("view", { class: "uni-searchbar__box-icon-search" }, [
            vue.renderSlot(_ctx.$slots, "searchIcon", {}, () => [
              vue.createVNode(_component_uni_icons, {
                color: "#c0c4cc",
                size: "18",
                type: "search"
              })
            ], true)
          ]),
          $data.show || $data.searchVal ? vue.withDirectives((vue.openBlock(), vue.createElementBlock("input", {
            key: 0,
            focus: $data.showSync,
            disabled: $props.readonly,
            placeholder: $options.placeholderText,
            maxlength: $props.maxlength,
            class: "uni-searchbar__box-search-input",
            "confirm-type": "search",
            type: "text",
            "onUpdate:modelValue": _cache[0] || (_cache[0] = ($event) => $data.searchVal = $event),
            style: vue.normalizeStyle({ color: $props.textColor }),
            onConfirm: _cache[1] || (_cache[1] = (...args) => $options.confirm && $options.confirm(...args)),
            onBlur: _cache[2] || (_cache[2] = (...args) => $options.blur && $options.blur(...args)),
            onFocus: _cache[3] || (_cache[3] = (...args) => $options.emitFocus && $options.emitFocus(...args))
          }, null, 44, ["focus", "disabled", "placeholder", "maxlength"])), [
            [vue.vModelText, $data.searchVal]
          ]) : (vue.openBlock(), vue.createElementBlock(
            "text",
            {
              key: 1,
              class: "uni-searchbar__text-placeholder"
            },
            vue.toDisplayString($props.placeholder),
            1
            /* TEXT */
          )),
          $data.show && ($props.clearButton === "always" || $props.clearButton === "auto" && $data.searchVal !== "") && !$props.readonly ? (vue.openBlock(), vue.createElementBlock("view", {
            key: 2,
            class: "uni-searchbar__box-icon-clear",
            onClick: _cache[4] || (_cache[4] = (...args) => $options.clear && $options.clear(...args))
          }, [
            vue.renderSlot(_ctx.$slots, "clearIcon", {}, () => [
              vue.createVNode(_component_uni_icons, {
                color: "#c0c4cc",
                size: "20",
                type: "clear"
              })
            ], true)
          ])) : vue.createCommentVNode("v-if", true)
        ],
        4
        /* STYLE */
      ),
      $props.cancelButton === "always" || $data.show && $props.cancelButton === "auto" ? (vue.openBlock(), vue.createElementBlock(
        "text",
        {
          key: 0,
          onClick: _cache[6] || (_cache[6] = (...args) => $options.cancel && $options.cancel(...args)),
          class: "uni-searchbar__cancel"
        },
        vue.toDisplayString($options.cancelTextI18n),
        1
        /* TEXT */
      )) : vue.createCommentVNode("v-if", true)
    ]);
  }
  const __easycom_2 = /* @__PURE__ */ _export_sfc(_sfc_main$3, [["render", _sfc_render$2], ["__scopeId", "data-v-f07ef577"], ["__file", "E:/ChongAi/ChongAiApp/uni_modules/uni-search-bar/components/uni-search-bar/uni-search-bar.vue"]]);
  function apiGetBreedList(petClass, petBreed) {
    return request({
      url: "/app/pet/breeds",
      method: "GET",
      data: {
        petClass,
        petBreed
      }
    });
  }
  function apiGetPetTypeList() {
    return request({
      url: "/app/pet/pet",
      method: "GET",
      header: {
        "ngrok-skip-browser-warning": "true"
        //测试 添加请求头绕过ngrok拦截
      }
    });
  }
  const _imports_0 = "/static/home.png";
  const _sfc_main$2 = {
    __name: "petSelection",
    emits: ["petSelected"],
    setup(__props, { expose: __expose, emit: __emit }) {
      __expose();
      const tabs = vue.ref("");
      const buttonText = vue.ref("完成");
      const activeTab = vue.ref("0");
      const currentLetter = vue.ref("letter-A");
      const petClass = vue.ref("");
      const petData = vue.ref("");
      const petBreed = vue.ref("");
      const hotPets = vue.ref([]);
      const emit = __emit;
      const handleBack = () => {
        uni.navigateBack();
      };
      const handleSkip = () => {
        uni.navigateTo({
          url: "./petInfo"
        });
      };
      const handleTabChange = async (tab) => {
        petClass.value = tab.petClass;
        activeTab.value = tab.petClassId;
        if (activeTab.value != 0) {
          buttonText.value = "下一个";
        } else {
          buttonText.value = "完成";
        }
        GetBreedList();
      };
      const scrollToLetter = (letter) => {
        currentLetter.value = `letter-${letter}`;
      };
      vue.onMounted(() => {
        GetPetTypeList();
      });
      const GetPetTypeList = async () => {
        try {
          const res = await apiGetPetTypeList();
          tabs.value = res.data;
        } catch (error) {
          formatAppLog("error", "at pages/petSelection/petSelection.vue:150", "获取宠物类型失败:", error);
        }
      };
      const GetBreedList = async () => {
        const authStore = useAuthStore();
        const token = authStore.token;
        uni.request({
          // url:'https://637c-112-48-4-41.ngrok-free.app/app/pet/breeds',
          url: "http://115.120.195.253/app/pet/breeds",
          method: "GET",
          data: {
            petClass: petClass.value,
            // 必填参数
            petBreed: petBreed.value
            // 选填参数
          },
          header: {
            "Authorization": `Bearer ${token}`,
            // 携带 token
            "Content-Type": "application/json",
            // 推荐添加
            "ngrok-skip-browser-warning": "true"
            //测试 添加请求头绕过ngrok拦截
          },
          success: (res) => {
            var _a2;
            formatAppLog("log", "at pages/petSelection/petSelection.vue:171", res);
            if (res.statusCode === 200) {
              formatAppLog("log", "at pages/petSelection/petSelection.vue:173", "宠物列表数据:", res.data);
              hotPets.value = res.data.data.hot;
              petData.value = res.data.data.breeds;
            } else {
              formatAppLog("error", "at pages/petSelection/petSelection.vue:177", "请求失败:", res);
              uni.showToast({ title: `请求失败: ${((_a2 = res.data) == null ? void 0 : _a2.message) || "未知错误"}`, icon: "none" });
            }
          },
          fail: (err) => {
            formatAppLog("error", "at pages/petSelection/petSelection.vue:182", "网络错误:", err);
            uni.showToast({ title: "网络连接失败", icon: "none" });
          }
        });
      };
      const petSearch = () => {
        GetBreedList();
      };
      const checkPet = (petBreed2) => {
        uni.navigateTo({
          url: `/pages/petSelection/petInfo?petBreed=${encodeURIComponent(petBreed2)}`
        });
      };
      const __returned__ = { tabs, buttonText, activeTab, currentLetter, petClass, petData, petBreed, hotPets, emit, handleBack, handleSkip, handleTabChange, scrollToLetter, GetPetTypeList, GetBreedList, petSearch, checkPet, ref: vue.ref, onMounted: vue.onMounted, nextTick: vue.nextTick, getCurrentInstance: vue.getCurrentInstance, get apiGetBreedList() {
        return apiGetBreedList;
      }, get apiGetPetTypeList() {
        return apiGetPetTypeList;
      }, get useAuthStore() {
        return useAuthStore;
      } };
      Object.defineProperty(__returned__, "__isScriptSetup", { enumerable: false, value: true });
      return __returned__;
    }
  };
  function _sfc_render$1(_ctx, _cache, $props, $setup, $data, $options) {
    const _component_uni_icons = resolveEasycom(vue.resolveDynamicComponent("uni-icons"), __easycom_0);
    const _component_uni_nav_bar = resolveEasycom(vue.resolveDynamicComponent("uni-nav-bar"), __easycom_1);
    const _component_uni_search_bar = resolveEasycom(vue.resolveDynamicComponent("uni-search-bar"), __easycom_2);
    return vue.openBlock(), vue.createElementBlock("view", { class: "pet-selection-container" }, [
      vue.createCommentVNode(" 顶部导航栏 "),
      vue.createVNode(_component_uni_nav_bar, {
        border: false,
        "background-color": "#ffffff",
        fixed: "",
        "status-bar": ""
      }, {
        left: vue.withCtx(() => [
          vue.createElementVNode("view", {
            class: "nav-left",
            onClick: $setup.handleBack
          }, [
            vue.createVNode(_component_uni_icons, {
              type: "arrow-left",
              size: "24"
            })
          ])
        ]),
        right: vue.withCtx(() => [
          vue.createElementVNode("view", {
            class: "nav-right",
            onClick: $setup.handleSkip
          }, [
            vue.createElementVNode("text", { class: "skip-text" }, "跳过")
          ])
        ]),
        default: vue.withCtx(() => [
          vue.createElementVNode("view", { class: "nav-title" }, "您养的宠物(1/2)")
        ]),
        _: 1
        /* STABLE */
      }),
      vue.createCommentVNode(" 页签区域 "),
      vue.createElementVNode("view", { class: "tabs" }, [
        (vue.openBlock(true), vue.createElementBlock(
          vue.Fragment,
          null,
          vue.renderList($setup.tabs, (tab) => {
            return vue.openBlock(), vue.createElementBlock("view", {
              key: tab.petClassId,
              class: vue.normalizeClass(["tab-item", { active: $setup.activeTab === tab.petClassId }]),
              onClick: ($event) => $setup.handleTabChange(tab)
            }, vue.toDisplayString(tab.petClass), 11, ["onClick"]);
          }),
          128
          /* KEYED_FRAGMENT */
        ))
      ]),
      vue.createCommentVNode(" 未养宠内容区域 "),
      $setup.activeTab === "0" ? (vue.openBlock(), vue.createElementBlock("view", {
        key: 0,
        class: "no-pet-section"
      }, [
        vue.createCommentVNode(" 全屏背景图 "),
        vue.createElementVNode("image", {
          src: _imports_0,
          class: "fullscreen-bg",
          mode: "aspectFill"
        }),
        vue.createCommentVNode(" 文字内容 "),
        vue.createElementVNode("view", { class: "prompt-content" }, [
          vue.createElementVNode("text", { class: "prompt-text" }, "快来领养一只宠物吧"),
          vue.createElementVNode("text", { class: "brand-text" }, "优爱宠，期待您加入"),
          vue.createElementVNode("text", { class: "slogan" }, "宠物生活一站式服务平台")
        ])
      ])) : (vue.openBlock(), vue.createElementBlock(
        vue.Fragment,
        { key: 1 },
        [
          vue.createCommentVNode(" 选择宠物状态 "),
          vue.createElementVNode("view", { class: "pet-selection-section" }, [
            vue.createCommentVNode(" 搜索栏 "),
            vue.createElementVNode("view", { class: "search-bar" }, [
              vue.createVNode(_component_uni_search_bar, {
                style: { "padding": "5rpx 5rpx" },
                placeholder: "搜索宠物品种",
                radius: "30",
                bgColor: "#F5F5F5",
                cancelButton: "none",
                onConfirm: $setup.petSearch,
                modelValue: $setup.petBreed,
                "onUpdate:modelValue": _cache[0] || (_cache[0] = ($event) => $setup.petBreed = $event)
              }, null, 8, ["modelValue"])
            ]),
            vue.createCommentVNode(" 热门品种 "),
            vue.createElementVNode("view", { class: "hot-section" }, [
              vue.createElementVNode("view", { class: "section-title" }, "热门:"),
              vue.createElementVNode("view", { class: "hot-tags" }, [
                (vue.openBlock(true), vue.createElementBlock(
                  vue.Fragment,
                  null,
                  vue.renderList($setup.hotPets, (pet, index) => {
                    return vue.openBlock(), vue.createElementBlock("text", {
                      key: index,
                      class: "hot-tag",
                      onClick: ($event) => $setup.checkPet(pet)
                    }, vue.toDisplayString(pet), 9, ["onClick"]);
                  }),
                  128
                  /* KEYED_FRAGMENT */
                ))
              ])
            ]),
            vue.createCommentVNode(" 字母分类列表 "),
            vue.createElementVNode("view", { class: "alphabet-section" }, [
              vue.createElementVNode("scroll-view", {
                class: "alphabet-list",
                "show-scrollbar": false,
                "scroll-y": "",
                "scroll-into-view": $setup.currentLetter
              }, [
                (vue.openBlock(true), vue.createElementBlock(
                  vue.Fragment,
                  null,
                  vue.renderList($setup.petData, (pets, letter) => {
                    return vue.openBlock(), vue.createElementBlock("view", {
                      key: letter,
                      id: `letter-${letter}`
                    }, [
                      vue.createElementVNode(
                        "text",
                        { class: "letter-title" },
                        vue.toDisplayString(letter),
                        1
                        /* TEXT */
                      ),
                      (vue.openBlock(true), vue.createElementBlock(
                        vue.Fragment,
                        null,
                        vue.renderList(pets, (pet, index) => {
                          return vue.openBlock(), vue.createElementBlock("view", {
                            key: index,
                            class: "pet-item",
                            onClick: ($event) => $setup.checkPet(pet.petBreed)
                          }, vue.toDisplayString(pet.petBreed), 9, ["onClick"]);
                        }),
                        128
                        /* KEYED_FRAGMENT */
                      ))
                    ], 8, ["id"]);
                  }),
                  128
                  /* KEYED_FRAGMENT */
                ))
              ], 8, ["scroll-into-view"]),
              vue.createCommentVNode(" 字母导航（右侧固定定位） "),
              vue.createElementVNode("view", { class: "alphabet-nav-wrapper" }, [
                vue.createElementVNode("view", { class: "alphabet-nav" }, [
                  (vue.openBlock(true), vue.createElementBlock(
                    vue.Fragment,
                    null,
                    vue.renderList(Object.keys($setup.petData), (letter) => {
                      return vue.openBlock(), vue.createElementBlock("text", {
                        key: letter,
                        class: vue.normalizeClass(["alphabet-char", { active: $setup.currentLetter === `letter-${letter}` }]),
                        onClick: ($event) => $setup.scrollToLetter(letter)
                      }, vue.toDisplayString(letter), 11, ["onClick"]);
                    }),
                    128
                    /* KEYED_FRAGMENT */
                  ))
                ])
              ])
            ])
          ])
        ],
        2112
        /* STABLE_FRAGMENT, DEV_ROOT_FRAGMENT */
      )),
      vue.createCommentVNode(" 固定底部按钮 "),
      vue.createElementVNode("view", { class: "fixed-footer" }, [
        vue.createElementVNode(
          "button",
          { class: "next-btn" },
          vue.toDisplayString($setup.buttonText),
          1
          /* TEXT */
        )
      ])
    ]);
  }
  const PagesPetSelectionPetSelection = /* @__PURE__ */ _export_sfc(_sfc_main$2, [["render", _sfc_render$1], ["__scopeId", "data-v-4cc5dec4"], ["__file", "E:/ChongAi/ChongAiApp/pages/petSelection/petSelection.vue"]]);
  const _sfc_main$1 = {
    __name: "petInfo",
    setup(__props, { expose: __expose }) {
      __expose();
      const avatar = vue.ref("/static/touxiang.svg");
      const petBreed = vue.ref("");
      const petNickName = vue.ref("");
      const petGender = vue.ref("");
      const sterilized = vue.ref(false);
      const petBirthday = vue.ref("");
      const arrivalDate = vue.ref("");
      const handlePetSelected = (selectedPet) => {
        formatAppLog("log", "at pages/petSelection/petInfo.vue:113", "接收到宠物数据:", selectedPet);
      };
      const handleBack = () => {
        uni.navigateBack();
      };
      const handleSkip = () => {
      };
      const UploadImage = () => {
        uni.chooseImage({
          count: 1,
          success(res) {
            imagePath.value = res.tempFilePaths[0];
          }
        });
      };
      const handleBirthdayChange = (date) => {
        petBirthday.value = date;
      };
      const handleArrivalDateChange = (date) => {
        arrivalDate.value = date;
      };
      const isFormValid = vue.computed(() => {
        return petNickName.value !== "" && petGender.value !== "" && petBirthday.value !== "" && arrivalDate.value !== "";
      });
      const complete = () => {
        handlePetSelected();
        formatAppLog("log", "at pages/petSelection/petInfo.vue:155", petBirthday.value);
        formatAppLog("log", "at pages/petSelection/petInfo.vue:156", arrivalDate.value);
      };
      onLoad((options) => {
        petBreed.value = decodeURIComponent(options.petBreed);
        formatAppLog("log", "at pages/petSelection/petInfo.vue:160", petBreed.value);
      });
      const __returned__ = { avatar, petBreed, petNickName, petGender, sterilized, petBirthday, arrivalDate, handlePetSelected, handleBack, handleSkip, UploadImage, handleBirthdayChange, handleArrivalDateChange, isFormValid, complete, DatePicker, onMounted: vue.onMounted, computed: vue.computed, ref: vue.ref, get onLoad() {
        return onLoad;
      } };
      Object.defineProperty(__returned__, "__isScriptSetup", { enumerable: false, value: true });
      return __returned__;
    }
  };
  function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
    const _component_uni_icons = resolveEasycom(vue.resolveDynamicComponent("uni-icons"), __easycom_0);
    const _component_uni_nav_bar = resolveEasycom(vue.resolveDynamicComponent("uni-nav-bar"), __easycom_1);
    return vue.openBlock(), vue.createElementBlock("view", { class: "pet-info-container" }, [
      vue.createCommentVNode(" 顶部导航栏 "),
      vue.createVNode(_component_uni_nav_bar, {
        border: false,
        "background-color": "#ffffff",
        fixed: "",
        "status-bar": ""
      }, {
        left: vue.withCtx(() => [
          vue.createElementVNode("view", {
            class: "nav-left",
            onClick: $setup.handleBack
          }, [
            vue.createVNode(_component_uni_icons, {
              type: "arrow-left",
              size: "24"
            })
          ])
        ]),
        right: vue.withCtx(() => [
          vue.createElementVNode("view", {
            class: "nav-right",
            onClick: $setup.handleSkip
          }, [
            vue.createElementVNode("text", { class: "skip-text" }, "跳过")
          ])
        ]),
        default: vue.withCtx(() => [
          vue.createElementVNode("view", { class: "nav-title" }, "您养的宠物(2/2)")
        ]),
        _: 1
        /* STABLE */
      }),
      vue.createCommentVNode(" 头像上传 "),
      vue.createElementVNode("view", {
        class: "avatar-upload",
        onClick: $setup.UploadImage
      }, [
        vue.createElementVNode("image", {
          src: $setup.avatar,
          class: "avatar"
        }, null, 8, ["src"]),
        vue.createCommentVNode(' <image src="/static/camera-icon.svg" class="camera-icon" /> ')
      ]),
      vue.createElementVNode("view", { class: "section-title" }, "为您的爱宠选一张靓照做头像"),
      vue.createCommentVNode(" 表单区域 "),
      vue.createElementVNode("view", { class: "form-group" }, [
        vue.createCommentVNode(" 手机号"),
        vue.createElementVNode(
          "view",
          {
            class: "form-item",
            "on:petSelected": $setup.handlePetSelected
          },
          [
            vue.createElementVNode("text", { class: "label" }, "宠物品种:"),
            vue.withDirectives(vue.createElementVNode(
              "input",
              {
                "onUpdate:modelValue": _cache[0] || (_cache[0] = ($event) => $setup.petBreed = $event),
                disabled: "",
                placeholder: "请输入"
              },
              null,
              512
              /* NEED_PATCH */
            ), [
              [vue.vModelText, $setup.petBreed]
            ])
          ],
          32
          /* NEED_HYDRATION */
        ),
        vue.createCommentVNode(" 昵称 "),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "宠物昵称:"),
          vue.withDirectives(vue.createElementVNode(
            "input",
            {
              "onUpdate:modelValue": _cache[1] || (_cache[1] = ($event) => $setup.petNickName = $event),
              placeholder: "请输入宠物昵称"
            },
            null,
            512
            /* NEED_PATCH */
          ), [
            [vue.vModelText, $setup.petNickName]
          ])
        ]),
        vue.createCommentVNode(" 性别 "),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "宠物性别:"),
          vue.createElementVNode("view", { class: "gender-container" }, [
            vue.createCommentVNode(" 男性选项 "),
            vue.createElementVNode("view", { class: "gender-option" }, [
              vue.createElementVNode(
                "view",
                {
                  class: vue.normalizeClass(["sex-male", { active: $setup.petGender === "0" }])
                },
                [
                  vue.createElementVNode("image", {
                    src: _imports_0$1,
                    class: "gender-icon"
                  })
                ],
                2
                /* CLASS */
              ),
              vue.createElementVNode(
                "text",
                {
                  class: vue.normalizeClass(["gender-tag male", { active: $setup.petGender === "0" }]),
                  onClick: _cache[2] || (_cache[2] = ($event) => $setup.petGender = "0")
                },
                "男",
                2
                /* CLASS */
              )
            ]),
            vue.createCommentVNode(" 女性选项 "),
            vue.createElementVNode("view", { class: "gender-option" }, [
              vue.createElementVNode(
                "view",
                {
                  class: vue.normalizeClass(["sex-female", { active: $setup.petGender === "1" }])
                },
                [
                  vue.createElementVNode("image", {
                    src: _imports_1,
                    class: "gender-icon"
                  })
                ],
                2
                /* CLASS */
              ),
              vue.createElementVNode(
                "text",
                {
                  class: vue.normalizeClass(["gender-tag female", { active: $setup.petGender === "1" }]),
                  onClick: _cache[3] || (_cache[3] = ($event) => $setup.petGender = "1")
                },
                "女",
                2
                /* CLASS */
              )
            ])
          ])
        ]),
        vue.createCommentVNode(" 绝育状态 "),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "是否绝育:"),
          vue.createElementVNode("view", { class: "neuter-options" }, [
            vue.createElementVNode(
              "view",
              {
                class: vue.normalizeClass(["neuter-option", { active: $setup.sterilized === true }]),
                onClick: _cache[4] || (_cache[4] = ($event) => $setup.sterilized = true)
              },
              " 是 ",
              2
              /* CLASS */
            ),
            vue.createElementVNode(
              "view",
              {
                class: vue.normalizeClass(["neuter-option", { active: $setup.sterilized === false }]),
                onClick: _cache[5] || (_cache[5] = ($event) => $setup.sterilized = false)
              },
              " 否 ",
              2
              /* CLASS */
            )
          ])
        ]),
        vue.createCommentVNode(" 宠物生日 "),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "宠物生日:"),
          vue.createVNode($setup["DatePicker"], { onDateChange: $setup.handleBirthdayChange })
        ]),
        vue.createCommentVNode(" 到家日期 "),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "到家日期:"),
          vue.createVNode($setup["DatePicker"], { onDateChange: $setup.handleArrivalDateChange })
        ])
      ]),
      vue.createCommentVNode(" 完成按钮 "),
      vue.createElementVNode(
        "button",
        {
          class: vue.normalizeClass(["next-btn", { active: $setup.isFormValid }]),
          onClick: $setup.complete
        },
        "完成",
        2
        /* CLASS */
      )
    ]);
  }
  const PagesPetSelectionPetInfo = /* @__PURE__ */ _export_sfc(_sfc_main$1, [["render", _sfc_render], ["__scopeId", "data-v-d243e359"], ["__file", "E:/ChongAi/ChongAiApp/pages/petSelection/petInfo.vue"]]);
  __definePage("pages/login/login", PagesLoginLogin);
  __definePage("pages/login/sms", PagesLoginSms);
  __definePage("pages/login/pwd", PagesLoginPwd);
  __definePage("pages/login/register", PagesLoginRegister);
  __definePage("pages/petSelection/petSelection", PagesPetSelectionPetSelection);
  __definePage("pages/petSelection/petInfo", PagesPetSelectionPetInfo);
  const _sfc_main = {
    onLaunch: function() {
      formatAppLog("log", "at App.vue:4", "App Launch");
    },
    onShow: function() {
      formatAppLog("log", "at App.vue:7", "App Show");
    },
    onHide: function() {
      formatAppLog("log", "at App.vue:10", "App Hide");
    }
  };
  const App = /* @__PURE__ */ _export_sfc(_sfc_main, [["__file", "E:/ChongAi/ChongAiApp/App.vue"]]);
  function createApp() {
    const app = vue.createVueApp(App);
    const pinia = createPinia();
    app.use(pinia);
    return {
      app,
      pinia
    };
  }
  const { app: __app__, Vuex: __Vuex__, Pinia: __Pinia__ } = createApp();
  uni.Vuex = __Vuex__;
  uni.Pinia = __Pinia__;
  __app__.provide("__globalStyles", __uniConfig.styles);
  __app__._component.mpType = "app";
  __app__._component.render = () => {
  };
  __app__.mount("#app");
})(Vue);
