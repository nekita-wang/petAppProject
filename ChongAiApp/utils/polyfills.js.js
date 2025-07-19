
if (typeof URLSearchParams === 'undefined') {
  class URLSearchParamsPolyfill {
    constructor(init) {
      this.params = {};
      if (typeof init === 'string') {
        init.replace(/^\?/, '').split('&').forEach(pair => {
          const [key, value] = pair.split('=');
          this.append(decodeURIComponent(key), decodeURIComponent(value || ''));
        });
      }
    }
    append(name, value) {
      this.params[name] = value;
    }
    toString() {
      return Object.keys(this.params)
        .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(this.params[key])}`)
        .join('&');
    }
  }
  global.URLSearchParams = URLSearchParamsPolyfill;
}