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
  const _sfc_main$a = {
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
  function _sfc_render$9(_ctx, _cache, $props, $setup, $data, $options) {
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
  const PagesLoginLogin = /* @__PURE__ */ _export_sfc(_sfc_main$a, [["render", _sfc_render$9], ["__scopeId", "data-v-e4e4508d"], ["__file", "E:/ChongAi/ChongAiApp/pages/login/login.vue"]]);
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
  const _sfc_main$9 = {
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
  function _sfc_render$8(_ctx, _cache, $props, $setup, $data, $options) {
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
  const __easycom_0 = /* @__PURE__ */ _export_sfc(_sfc_main$9, [["render", _sfc_render$8], ["__scopeId", "data-v-d31e1c47"], ["__file", "E:/ChongAi/ChongAiApp/uni_modules/uni-icons/components/uni-icons/uni-icons.vue"]]);
  const _sfc_main$8 = {
    __name: "sms",
    setup(__props, { expose: __expose }) {
      __expose();
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
      const getSMSCode = () => {
        if (countdown.value > 0)
          return;
        uni.request({
          // url: 'http://localhost/dev-api/auth/sendCode',
          url: "http://192.168.0.224:8080/auth/sendCode",
          data: {
            phone: phone.value
          },
          method: "POST",
          header: {
            "Content-Type": "application/x-www-form-urlencoded"
          },
          success: (res) => {
            formatAppLog("log", "at pages/login/sms.vue:106", res.data);
            if (res.data.success == true) {
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
              formatAppLog("log", "at pages/login/sms.vue:122", "验证码：", res.data.data);
              code.value = res.data.data;
            } else {
              uni.showToast({
                title: res.data.msg,
                icon: "error"
              });
            }
          }
        });
      };
      const handleLogin = () => {
        uni.navigateTo({
          url: "/pages/petSelection/petSelection"
        });
      };
      const __returned__ = { phone, code, showPhoneError, inputClass, showKeyboard, countdown, ToPasswordLogin, handleBack, isPhoneValid, handlePhoneInput, isFormValid, getSMSCode, handleLogin, ref: vue.ref, computed: vue.computed };
      Object.defineProperty(__returned__, "__isScriptSetup", { enumerable: false, value: true });
      return __returned__;
    }
  };
  function _sfc_render$7(_ctx, _cache, $props, $setup, $data, $options) {
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
  const PagesLoginSms = /* @__PURE__ */ _export_sfc(_sfc_main$8, [["render", _sfc_render$7], ["__scopeId", "data-v-4e4cd1cc"], ["__file", "E:/ChongAi/ChongAiApp/pages/login/sms.vue"]]);
  const _sfc_main$7 = {
    __name: "pwd",
    setup(__props, { expose: __expose }) {
      __expose();
      const phone = vue.ref("13812345678");
      const password = vue.ref("");
      const showPhoneError = vue.ref(false);
      const showPassword = vue.ref(false);
      const msg = vue.ref("");
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
      const handleLogin = () => {
        uni.request({
          // url: 'http://localhost/dev-api/auth/login',  //本地地址
          url: "http://192.168.0.224:8080/auth/login",
          //真机调试地址
          // url:'https://637c-112-48-4-41.ngrok-free.app/auth/login', 
          sslVerify: false,
          // 真机调试时关闭证书验证（仅开发环境）
          data: {
            grantType: "password",
            //后端指定类型
            phone: phone.value,
            password: password.value
          },
          method: "POST",
          header: {
            "Content-Type": "application/json",
            // 添加必要头信息
            "Accept": "application/json",
            "ngrok-skip-browser-warning": "true"
            // 关键！
          },
          success: (res) => {
            formatAppLog("log", "at pages/login/pwd.vue:114", res.data);
            if (res.data.code == 200) {
              formatAppLog("log", "at pages/login/pwd.vue:117", res.data.data.token);
              uni.setStorageSync("token", res.data.data.token);
              uni.navigateTo({
                url: "/pages/petSelection/petSelection"
              });
            } else if (res.data.code == 1e3) {
              uni.showToast({
                title: res.data.msg,
                icon: "none"
              }), // 跳转到注册页面
              uni.navigateTo({
                url: "/pages/register/register"
              });
            } else {
              uni.showToast({
                title: res.data.msg,
                icon: "error"
              });
            }
          },
          fail: (err) => {
            msg.value = err.errMsg;
            uni.showToast({
              title: err.errMsg,
              icon: "none"
            });
          }
        });
      };
      const __returned__ = { phone, password, showPhoneError, showPassword, msg, ToSMSLogin, handleBack, handlePhoneInput, isPhoneValid, togglePassword, handlePaste, isFormValid, handleLogin, ref: vue.ref, computed: vue.computed };
      Object.defineProperty(__returned__, "__isScriptSetup", { enumerable: false, value: true });
      return __returned__;
    }
  };
  function _sfc_render$6(_ctx, _cache, $props, $setup, $data, $options) {
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
            "disable-default-paste": true
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
  const PagesLoginPwd = /* @__PURE__ */ _export_sfc(_sfc_main$7, [["render", _sfc_render$6], ["__scopeId", "data-v-8032d478"], ["__file", "E:/ChongAi/ChongAiApp/pages/login/pwd.vue"]]);
  const _imports_0$1 = "/static/nan.svg";
  const _imports_1 = "/static/nv.svg";
  const _sfc_main$6 = {
    __name: "register",
    setup(__props, { expose: __expose }) {
      __expose();
      const imagePath = vue.ref("/static/图片.svg");
      const phone = vue.ref("");
      const nickname = vue.ref("");
      const password = vue.ref("");
      const confirmPassword = vue.ref("");
      const gender = vue.ref("male");
      const indicatorStyle = vue.ref("");
      const maskStyle = vue.ref("");
      const currentDate = /* @__PURE__ */ new Date();
      const years = vue.ref([]);
      const year = vue.ref(currentDate.getFullYear());
      const months = vue.ref([]);
      const month = vue.ref(currentDate.getMonth() + 1);
      const days = vue.ref([]);
      const day = vue.ref(currentDate.getDate());
      const pickerValue = vue.ref([]);
      const handelNext = () => {
        uni.navigateTo({
          url: "/pages/petSelection/petSelection"
        });
      };
      const UploadImage = () => {
        uni.chooseImage({
          count: 1,
          success(res) {
            imagePath.value = res.tempFilePaths[0];
          }
        });
      };
      vue.onMounted(() => {
        for (let i = 1990; i <= currentDate.getFullYear(); i++) {
          years.value.push(i);
        }
        for (let i = 1; i <= 12; i++) {
          months.value.push(i);
        }
        for (let i = 1; i <= 31; i++) {
          days.value.push(i);
        }
        const yearIndex = years.value.indexOf(year.value);
        pickerValue.value = [yearIndex, month.value - 1, day.value - 1];
        indicatorStyle.value = `height: ${uni.upx2px(80)}px;`;
      });
      const handleDateChange = (event) => {
        const selectedValues = event.detail.value;
        year.value = years.value[selectedValues[0]];
        month.value = months.value[selectedValues[1]];
        day.value = days.value[selectedValues[2]];
      };
      const __returned__ = { imagePath, phone, nickname, password, confirmPassword, gender, indicatorStyle, maskStyle, currentDate, years, year, months, month, days, day, pickerValue, handelNext, UploadImage, handleDateChange, onMounted: vue.onMounted, ref: vue.ref };
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
          src: $setup.imagePath,
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
              "onUpdate:modelValue": _cache[0] || (_cache[0] = ($event) => $setup.phone = $event),
              placeholder: "请输入"
            },
            null,
            512
            /* NEED_PATCH */
          ), [
            [vue.vModelText, $setup.phone]
          ])
        ]),
        vue.createCommentVNode(" 昵称 "),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "昵称:"),
          vue.withDirectives(vue.createElementVNode(
            "input",
            {
              "onUpdate:modelValue": _cache[1] || (_cache[1] = ($event) => $setup.nickname = $event),
              placeholder: "请输入"
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
                  class: vue.normalizeClass(["sex-male", { active: $setup.gender === "male" }])
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
                  class: vue.normalizeClass(["gender-tag male", { active: $setup.gender === "male" }]),
                  onClick: _cache[2] || (_cache[2] = ($event) => $setup.gender = "male")
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
                  class: vue.normalizeClass(["sex-female", { active: $setup.gender === "female" }])
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
                  class: vue.normalizeClass(["gender-tag female", { active: $setup.gender === "female" }]),
                  onClick: _cache[3] || (_cache[3] = ($event) => $setup.gender = "female")
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
          vue.createCommentVNode(" 日期选择器容器 "),
          vue.createElementVNode("view", { class: "date-picker-container" }, [
            vue.createElementVNode("picker-view", {
              "indicator-style": $setup.indicatorStyle,
              "mask-style": $setup.maskStyle,
              value: $setup.pickerValue,
              onChange: $setup.handleDateChange
            }, [
              vue.createElementVNode("picker-view-column", null, [
                (vue.openBlock(true), vue.createElementBlock(
                  vue.Fragment,
                  null,
                  vue.renderList($setup.years, (item, index) => {
                    return vue.openBlock(), vue.createElementBlock(
                      "view",
                      {
                        class: "item",
                        key: index
                      },
                      vue.toDisplayString(item) + "年",
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
                  vue.renderList($setup.months, (item, index) => {
                    return vue.openBlock(), vue.createElementBlock(
                      "view",
                      {
                        class: "item",
                        key: index
                      },
                      vue.toDisplayString(item) + "月",
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
                  vue.renderList($setup.days, (item, index) => {
                    return vue.openBlock(), vue.createElementBlock(
                      "view",
                      {
                        class: "item",
                        key: index
                      },
                      vue.toDisplayString(item) + "日",
                      1
                      /* TEXT */
                    );
                  }),
                  128
                  /* KEYED_FRAGMENT */
                ))
              ])
            ], 40, ["indicator-style", "mask-style", "value"])
          ])
        ]),
        vue.createCommentVNode(" 密码 "),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "密码:"),
          vue.withDirectives(vue.createElementVNode(
            "input",
            {
              "onUpdate:modelValue": _cache[4] || (_cache[4] = ($event) => $setup.password = $event),
              password: "",
              placeholder: "请输入"
            },
            null,
            512
            /* NEED_PATCH */
          ), [
            [vue.vModelText, $setup.password]
          ])
        ]),
        vue.createCommentVNode(" 密码确认 "),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "密码确认:"),
          vue.withDirectives(vue.createElementVNode(
            "input",
            {
              "onUpdate:modelValue": _cache[5] || (_cache[5] = ($event) => $setup.confirmPassword = $event),
              password: "",
              placeholder: "请输入"
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
      vue.createElementVNode("button", {
        class: "next-btn",
        onClick: $setup.handelNext
      }, "下一步")
    ]);
  }
  const PagesRegisterRegister = /* @__PURE__ */ _export_sfc(_sfc_main$6, [["render", _sfc_render$5], ["__scopeId", "data-v-bac4a35d"], ["__file", "E:/ChongAi/ChongAiApp/pages/register/register.vue"]]);
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
  const _imports_0 = "/static/home.png";
  const _sfc_main$2 = {
    __name: "petSelection",
    setup(__props, { expose: __expose }) {
      __expose();
      const tabs = vue.ref("");
      const token = vue.ref(
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImxvZ2luX3VzZXJfa2V5IjoiMWMwNTRiOTItYzQyZi00OTI0LWI1YzgtYzlmNzU3ZDUxNGVjIn0.C9rJ-jH5ZTPh7YQzDql2_O1uLlrCfaa-AcOI79cUoCpowTJ9qC3RP_JOuQ2lfkYn5BB_f75mmHA83fQ1OXseEw"
      );
      const buttonText = vue.ref("完成");
      const activeTab = vue.ref("none");
      const currentLetter = vue.ref("letter-A");
      const petClass = vue.ref("");
      const petData = vue.ref("");
      const hotPets = vue.ref([
        "布偶猫",
        "欧洲蓝猫",
        "美国短毛猫",
        "埃及猫",
        "暹罗猫",
        "日本短尾猫"
      ]);
      const handleBack = () => {
        uni.navigateBack();
      };
      const handleSkip = () => {
        uni.navigateTo({
          url: "./petInfo"
        });
      };
      const handleTabChange = (tab) => {
        petClass.value = tab.petClass;
        activeTab.value = tab.petClassEn;
        if (activeTab.value != "none") {
          buttonText.value = "下一个";
        } else {
          buttonText.value = "完成";
        }
        uni.request({
          // url: 'http://localhost/dev-api/breed/list',
          url: "http://192.168.0.224:8080/breed/list",
          method: "GET",
          header: {
            "Authorization": `Bearer ${token.value}`
          },
          data: {
            petClass: petClass.value,
            pageNum: 1,
            pageSize: 9999
            // 模拟获取全部
          },
          success: (res) => {
            if (res.data.rows.length === 0) {
              uni.showToast({ title: "暂无数据", icon: "none" });
              petData.value = {};
              return;
            }
            const groupedData = {};
            const sortedRows = [...res.data.rows].sort((a, b) => {
              return (a.cnInitials || a.petBreed.charAt(0)).localeCompare(b.cnInitials || b.petBreed.charAt(0), "zh-Hans-CN");
            });
            sortedRows.forEach((item) => {
              const initial = item.cnInitials || item.petBreed.charAt(0).toUpperCase();
              if (!groupedData[initial]) {
                groupedData[initial] = [];
              }
              groupedData[initial].push(item.petBreed);
            });
            petData.value = groupedData;
          }
        });
      };
      const scrollToLetter = (letter) => {
        currentLetter.value = `letter-${letter}`;
      };
      uni.request({
        // url: `http://localhost/dev-api/petType/list`,
        url: "http://192.168.0.224:8080/petType/list",
        method: "GET",
        header: {
          "Authorization": `Bearer ${token.value}`
        },
        success: (res) => {
          formatAppLog("log", "at pages/petSelection/petSelection.vue:236", res);
          tabs.value = res.data.rows;
          tabs.value.unshift({
            petClass: "尚未养宠",
            petClassEn: "none"
          });
        }
      });
      const __returned__ = { tabs, token, buttonText, activeTab, currentLetter, petClass, petData, hotPets, handleBack, handleSkip, handleTabChange, scrollToLetter, ref: vue.ref, onMounted: vue.onMounted, nextTick: vue.nextTick, getCurrentInstance: vue.getCurrentInstance };
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
              key: tab.petClassEn,
              class: vue.normalizeClass(["tab-item", { active: $setup.activeTab === tab.petClassEn }]),
              onClick: ($event) => $setup.handleTabChange(tab)
            }, vue.toDisplayString(tab.petClass), 11, ["onClick"]);
          }),
          128
          /* KEYED_FRAGMENT */
        ))
      ]),
      vue.createCommentVNode(" 未养宠内容区域 "),
      $setup.activeTab === "none" ? (vue.openBlock(), vue.createElementBlock("view", {
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
                cancelButton: "none"
              })
            ]),
            vue.createCommentVNode(" 热门品种 "),
            vue.createElementVNode("view", { class: "hot-section" }, [
              vue.createElementVNode("view", { class: "section-title" }, "热门:"),
              vue.createElementVNode("view", { class: "hot-tags" }, [
                (vue.openBlock(true), vue.createElementBlock(
                  vue.Fragment,
                  null,
                  vue.renderList($setup.hotPets, (pet, index) => {
                    return vue.openBlock(), vue.createElementBlock(
                      "text",
                      {
                        key: index,
                        class: "hot-tag"
                      },
                      vue.toDisplayString(pet),
                      1
                      /* TEXT */
                    );
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
                          return vue.openBlock(), vue.createElementBlock(
                            "view",
                            {
                              key: index,
                              class: "pet-item"
                            },
                            vue.toDisplayString(pet),
                            1
                            /* TEXT */
                          );
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
      const imagePath = vue.ref("/static/touxiang.svg");
      const phone = vue.ref("");
      const nickname = vue.ref("");
      const password = vue.ref("");
      const isNeutered = vue.ref(true);
      const confirmPassword = vue.ref("");
      const gender = vue.ref("male");
      const indicatorStyle = vue.ref("");
      const maskStyle = vue.ref("");
      const currentDate = /* @__PURE__ */ new Date();
      const years = vue.ref([]);
      const year = vue.ref(currentDate.getFullYear());
      const months = vue.ref([]);
      const month = vue.ref(currentDate.getMonth() + 1);
      const days = vue.ref([]);
      const day = vue.ref(currentDate.getDate());
      const pickerValue = vue.ref([]);
      const handleBack = () => {
        uni.navigateBack();
      };
      const handleSkip = () => {
        uni.navigateTo({
          url: "./petInfo"
        });
      };
      const UploadImage = () => {
        uni.chooseImage({
          count: 1,
          success(res) {
            imagePath.value = res.tempFilePaths[0];
          }
        });
      };
      vue.onMounted(() => {
        for (let i = 1990; i <= currentDate.getFullYear(); i++) {
          years.value.push(i);
        }
        for (let i = 1; i <= 12; i++) {
          months.value.push(i);
        }
        for (let i = 1; i <= 31; i++) {
          days.value.push(i);
        }
        const yearIndex = years.value.indexOf(year.value);
        pickerValue.value = [yearIndex, month.value - 1, day.value - 1];
        indicatorStyle.value = `height: ${uni.upx2px(80)}px;`;
      });
      const handleDateChange = (event) => {
        const selectedValues = event.detail.value;
        year.value = years.value[selectedValues[0]];
        month.value = months.value[selectedValues[1]];
        day.value = days.value[selectedValues[2]];
      };
      const __returned__ = { imagePath, phone, nickname, password, isNeutered, confirmPassword, gender, indicatorStyle, maskStyle, currentDate, years, year, months, month, days, day, pickerValue, handleBack, handleSkip, UploadImage, handleDateChange, onMounted: vue.onMounted, ref: vue.ref };
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
          src: $setup.imagePath,
          class: "avatar"
        }, null, 8, ["src"]),
        vue.createCommentVNode(' <image src="/static/camera-icon.svg" class="camera-icon" /> ')
      ]),
      vue.createElementVNode("view", { class: "section-title" }, "为您的爱宠选一张靓照做头像"),
      vue.createCommentVNode(" 表单区域 "),
      vue.createElementVNode("view", { class: "form-group" }, [
        vue.createCommentVNode(" 手机号"),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "宠物品种:"),
          vue.withDirectives(vue.createElementVNode(
            "input",
            {
              "onUpdate:modelValue": _cache[0] || (_cache[0] = ($event) => $setup.phone = $event),
              placeholder: "请输入"
            },
            null,
            512
            /* NEED_PATCH */
          ), [
            [vue.vModelText, $setup.phone]
          ])
        ]),
        vue.createCommentVNode(" 昵称 "),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "宠物昵称:"),
          vue.withDirectives(vue.createElementVNode(
            "input",
            {
              "onUpdate:modelValue": _cache[1] || (_cache[1] = ($event) => $setup.nickname = $event),
              placeholder: "请输入"
            },
            null,
            512
            /* NEED_PATCH */
          ), [
            [vue.vModelText, $setup.nickname]
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
                  class: vue.normalizeClass(["sex-male", { active: $setup.gender === "male" }])
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
                  class: vue.normalizeClass(["gender-tag male", { active: $setup.gender === "male" }]),
                  onClick: _cache[2] || (_cache[2] = ($event) => $setup.gender = "male")
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
                  class: vue.normalizeClass(["sex-female", { active: $setup.gender === "female" }])
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
                  class: vue.normalizeClass(["gender-tag female", { active: $setup.gender === "female" }]),
                  onClick: _cache[3] || (_cache[3] = ($event) => $setup.gender = "female")
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
                class: vue.normalizeClass(["neuter-option", { active: $setup.isNeutered === true }]),
                onClick: _cache[4] || (_cache[4] = ($event) => $setup.isNeutered = true)
              },
              " 是 ",
              2
              /* CLASS */
            ),
            vue.createElementVNode(
              "view",
              {
                class: vue.normalizeClass(["neuter-option", { active: $setup.isNeutered === false }]),
                onClick: _cache[5] || (_cache[5] = ($event) => $setup.isNeutered = false)
              },
              " 否 ",
              2
              /* CLASS */
            )
          ])
        ]),
        vue.createCommentVNode(" 生日"),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "宠物生日:"),
          vue.createCommentVNode(" 日期选择器容器 "),
          vue.createElementVNode("view", { class: "date-picker-container" }, [
            vue.createElementVNode("picker-view", {
              "indicator-style": $setup.indicatorStyle,
              "mask-style": $setup.maskStyle,
              value: $setup.pickerValue,
              onChange: $setup.handleDateChange
            }, [
              vue.createElementVNode("picker-view-column", null, [
                (vue.openBlock(true), vue.createElementBlock(
                  vue.Fragment,
                  null,
                  vue.renderList($setup.years, (item, index) => {
                    return vue.openBlock(), vue.createElementBlock(
                      "view",
                      {
                        class: "item",
                        key: index
                      },
                      vue.toDisplayString(item) + "年",
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
                  vue.renderList($setup.months, (item, index) => {
                    return vue.openBlock(), vue.createElementBlock(
                      "view",
                      {
                        class: "item",
                        key: index
                      },
                      vue.toDisplayString(item) + "月",
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
                  vue.renderList($setup.days, (item, index) => {
                    return vue.openBlock(), vue.createElementBlock(
                      "view",
                      {
                        class: "item",
                        key: index
                      },
                      vue.toDisplayString(item) + "日",
                      1
                      /* TEXT */
                    );
                  }),
                  128
                  /* KEYED_FRAGMENT */
                ))
              ])
            ], 40, ["indicator-style", "mask-style", "value"])
          ])
        ]),
        vue.createElementVNode("view", { class: "form-item" }, [
          vue.createElementVNode("text", { class: "label" }, "到家日期:"),
          vue.createCommentVNode(" 日期选择器容器 "),
          vue.createElementVNode("view", { class: "date-picker-container" }, [
            vue.createElementVNode("picker-view", {
              "indicator-style": $setup.indicatorStyle,
              "mask-style": $setup.maskStyle,
              value: $setup.pickerValue,
              onChange: $setup.handleDateChange
            }, [
              vue.createElementVNode("picker-view-column", null, [
                (vue.openBlock(true), vue.createElementBlock(
                  vue.Fragment,
                  null,
                  vue.renderList($setup.years, (item, index) => {
                    return vue.openBlock(), vue.createElementBlock(
                      "view",
                      {
                        class: "item",
                        key: index
                      },
                      vue.toDisplayString(item) + "年",
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
                  vue.renderList($setup.months, (item, index) => {
                    return vue.openBlock(), vue.createElementBlock(
                      "view",
                      {
                        class: "item",
                        key: index
                      },
                      vue.toDisplayString(item) + "月",
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
                  vue.renderList($setup.days, (item, index) => {
                    return vue.openBlock(), vue.createElementBlock(
                      "view",
                      {
                        class: "item",
                        key: index
                      },
                      vue.toDisplayString(item) + "日",
                      1
                      /* TEXT */
                    );
                  }),
                  128
                  /* KEYED_FRAGMENT */
                ))
              ])
            ], 40, ["indicator-style", "mask-style", "value"])
          ])
        ])
      ]),
      vue.createCommentVNode(" 完成按钮 "),
      vue.createElementVNode("button", { class: "next-btn" }, "完成")
    ]);
  }
  const PagesPetSelectionPetInfo = /* @__PURE__ */ _export_sfc(_sfc_main$1, [["render", _sfc_render], ["__scopeId", "data-v-d243e359"], ["__file", "E:/ChongAi/ChongAiApp/pages/petSelection/petInfo.vue"]]);
  __definePage("pages/login/login", PagesLoginLogin);
  __definePage("pages/login/sms", PagesLoginSms);
  __definePage("pages/login/pwd", PagesLoginPwd);
  __definePage("pages/register/register", PagesRegisterRegister);
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
    return {
      app
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
