import {
  __commonJS
} from "./chunk-Y2F7D3TJ.js";

// ../../../../petAppProject/ChongAiApp/node_modules/url-search-params-polyfill/index.js
var require_url_search_params_polyfill = __commonJS({
  "../../../../petAppProject/ChongAiApp/node_modules/url-search-params-polyfill/index.js"(exports) {
    (function(self) {
      "use strict";
      var nativeURLSearchParams = function() {
        try {
          if (self.URLSearchParams && new self.URLSearchParams("foo=bar").get("foo") === "bar") {
            return self.URLSearchParams;
          }
        } catch (e) {
        }
        return null;
      }(), isSupportObjectConstructor = nativeURLSearchParams && new nativeURLSearchParams({ a: 1 }).toString() === "a=1", decodesPlusesCorrectly = nativeURLSearchParams && new nativeURLSearchParams("s=%2B").get("s") === "+", isSupportSize = nativeURLSearchParams && "size" in nativeURLSearchParams.prototype, __URLSearchParams__ = "__URLSearchParams__", encodesAmpersandsCorrectly = nativeURLSearchParams ? function() {
        var ampersandTest = new nativeURLSearchParams();
        ampersandTest.append("s", " &");
        return ampersandTest.toString() === "s=+%26";
      }() : true, prototype = URLSearchParamsPolyfill.prototype, iterable = !!(self.Symbol && self.Symbol.iterator);
      if (nativeURLSearchParams && isSupportObjectConstructor && decodesPlusesCorrectly && encodesAmpersandsCorrectly && isSupportSize) {
        return;
      }
      function URLSearchParamsPolyfill(search) {
        search = search || "";
        if (search instanceof URLSearchParams || search instanceof URLSearchParamsPolyfill) {
          search = search.toString();
        }
        this[__URLSearchParams__] = parseToDict(search);
      }
      prototype.append = function(name, value) {
        appendTo(this[__URLSearchParams__], name, value);
      };
      prototype["delete"] = function(name) {
        delete this[__URLSearchParams__][name];
      };
      prototype.get = function(name) {
        var dict = this[__URLSearchParams__];
        return this.has(name) ? dict[name][0] : null;
      };
      prototype.getAll = function(name) {
        var dict = this[__URLSearchParams__];
        return this.has(name) ? dict[name].slice(0) : [];
      };
      prototype.has = function(name) {
        return hasOwnProperty(this[__URLSearchParams__], name);
      };
      prototype.set = function set(name, value) {
        this[__URLSearchParams__][name] = ["" + value];
      };
      prototype.toString = function() {
        var dict = this[__URLSearchParams__], query = [], i, key, name, value;
        for (key in dict) {
          name = encode(key);
          for (i = 0, value = dict[key]; i < value.length; i++) {
            query.push(name + "=" + encode(value[i]));
          }
        }
        return query.join("&");
      };
      var useProxy = self.Proxy && nativeURLSearchParams && (!decodesPlusesCorrectly || !encodesAmpersandsCorrectly || !isSupportObjectConstructor || !isSupportSize);
      var propValue;
      if (useProxy) {
        propValue = new Proxy(nativeURLSearchParams, {
          construct: function(target, args) {
            return new target(new URLSearchParamsPolyfill(args[0]).toString());
          }
        });
        propValue.toString = Function.prototype.toString.bind(URLSearchParamsPolyfill);
      } else {
        propValue = URLSearchParamsPolyfill;
      }
      Object.defineProperty(self, "URLSearchParams", {
        value: propValue
      });
      var USPProto = self.URLSearchParams.prototype;
      USPProto.polyfill = true;
      if (!useProxy && self.Symbol) {
        USPProto[self.Symbol.toStringTag] = "URLSearchParams";
      }
      if (!("forEach" in USPProto)) {
        USPProto.forEach = function(callback, thisArg) {
          var dict = parseToDict(this.toString());
          Object.getOwnPropertyNames(dict).forEach(function(name) {
            dict[name].forEach(function(value) {
              callback.call(thisArg, value, name, this);
            }, this);
          }, this);
        };
      }
      if (!("sort" in USPProto)) {
        USPProto.sort = function() {
          var dict = parseToDict(this.toString()), keys = [], k, i, j;
          for (k in dict) {
            keys.push(k);
          }
          keys.sort();
          for (i = 0; i < keys.length; i++) {
            this["delete"](keys[i]);
          }
          for (i = 0; i < keys.length; i++) {
            var key = keys[i], values = dict[key];
            for (j = 0; j < values.length; j++) {
              this.append(key, values[j]);
            }
          }
        };
      }
      if (!("keys" in USPProto)) {
        USPProto.keys = function() {
          var items = [];
          this.forEach(function(item, name) {
            items.push(name);
          });
          return makeIterator(items);
        };
      }
      if (!("values" in USPProto)) {
        USPProto.values = function() {
          var items = [];
          this.forEach(function(item) {
            items.push(item);
          });
          return makeIterator(items);
        };
      }
      if (!("entries" in USPProto)) {
        USPProto.entries = function() {
          var items = [];
          this.forEach(function(item, name) {
            items.push([name, item]);
          });
          return makeIterator(items);
        };
      }
      if (iterable) {
        USPProto[self.Symbol.iterator] = USPProto[self.Symbol.iterator] || USPProto.entries;
      }
      if (!("size" in USPProto)) {
        Object.defineProperty(USPProto, "size", {
          get: function() {
            var dict = parseToDict(this.toString());
            if (USPProto === this) {
              throw new TypeError("Illegal invocation at URLSearchParams.invokeGetter");
            }
            return Object.keys(dict).reduce(function(prev, cur) {
              return prev + dict[cur].length;
            }, 0);
          }
        });
      }
      function encode(str) {
        var replace = {
          "!": "%21",
          "'": "%27",
          "(": "%28",
          ")": "%29",
          "~": "%7E",
          "%20": "+",
          "%00": "\0"
        };
        return encodeURIComponent(str).replace(/[!'\(\)~]|%20|%00/g, function(match) {
          return replace[match];
        });
      }
      function decode(str) {
        return str.replace(/[ +]/g, "%20").replace(/(%[a-f0-9]{2})+/ig, function(match) {
          return decodeURIComponent(match);
        });
      }
      function makeIterator(arr) {
        var iterator = {
          next: function() {
            var value = arr.shift();
            return { done: value === void 0, value };
          }
        };
        if (iterable) {
          iterator[self.Symbol.iterator] = function() {
            return iterator;
          };
        }
        return iterator;
      }
      function parseToDict(search) {
        var dict = {};
        if (typeof search === "object") {
          if (isArray(search)) {
            for (var i = 0; i < search.length; i++) {
              var item = search[i];
              if (isArray(item) && item.length === 2) {
                appendTo(dict, item[0], item[1]);
              } else {
                throw new TypeError("Failed to construct 'URLSearchParams': Sequence initializer must only contain pair elements");
              }
            }
          } else {
            for (var key in search) {
              if (search.hasOwnProperty(key)) {
                appendTo(dict, key, search[key]);
              }
            }
          }
        } else {
          if (search.indexOf("?") === 0) {
            search = search.slice(1);
          }
          var pairs = search.split("&");
          for (var j = 0; j < pairs.length; j++) {
            var value = pairs[j], index = value.indexOf("=");
            if (-1 < index) {
              appendTo(dict, decode(value.slice(0, index)), decode(value.slice(index + 1)));
            } else {
              if (value) {
                appendTo(dict, decode(value), "");
              }
            }
          }
        }
        return dict;
      }
      function appendTo(dict, name, value) {
        var val = typeof value === "string" ? value : value !== null && value !== void 0 && typeof value.toString === "function" ? value.toString() : JSON.stringify(value);
        if (hasOwnProperty(dict, name)) {
          dict[name].push(val);
        } else {
          dict[name] = [val];
        }
      }
      function isArray(val) {
        return !!val && "[object Array]" === Object.prototype.toString.call(val);
      }
      function hasOwnProperty(obj, prop) {
        return Object.prototype.hasOwnProperty.call(obj, prop);
      }
    })(typeof global !== "undefined" ? global : typeof window !== "undefined" ? window : exports);
  }
});
export default require_url_search_params_polyfill();
//# sourceMappingURL=url-search-params-polyfill.js.map
