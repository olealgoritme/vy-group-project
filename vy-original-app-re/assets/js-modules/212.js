__d(function(g,r,i,a,m,e,d){'use strict';function t(t,n){if(!(t instanceof n))throw new TypeError("Cannot call a class as a function")}function n(t,n){for(var o=0;o<n.length;o++){var u=n[o];u.enumerable=u.enumerable||!1,u.configurable=!0,"value"in u&&(u.writable=!0),Object.defineProperty(t,u.key,u)}}function o(t){if(void 0===t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return t}function u(t,n,o){return(u="undefined"!=typeof Reflect&&Reflect.get?Reflect.get:function(t,n,o){var u=c(t,n);if(u){var _=Object.getOwnPropertyDescriptor(u,n);return _.get?_.get.call(o):_.value}})(t,n,o||t)}function c(t,n){for(;!Object.prototype.hasOwnProperty.call(t,n)&&null!==(t=_(t)););return t}function _(t){return(_=Object.setPrototypeOf?Object.getPrototypeOf:function(t){return t.__proto__||Object.getPrototypeOf(t)})(t)}function l(t,n){if("function"!=typeof n&&null!==n)throw new TypeError("Super expression must either be null or a function");t.prototype=Object.create(n&&n.prototype,{constructor:{value:t,writable:!0,configurable:!0}}),n&&f(t,n)}function f(t,n){return(f=Object.setPrototypeOf||function(t,n){return t.__proto__=n,t})(t,n)}var s=r(d[0]),h=(r(d[1]),r(d[2])),p=r(d[3]),y=(function(c){function f(n,u){var c,l,s;return t(this,f),l=this,(c=!(s=_(f).call(this))||"object"!=typeof s&&"function"!=typeof s?o(l):s)._a='number'==typeof n?new h(n):n,c._b='number'==typeof u?new h(u):u,c}var y,b,v;return l(f,p),y=f,(b=[{key:"__makeNative",value:function(){this._a.__makeNative(),this._b.__makeNative(),u(_(f.prototype),"__makeNative",this).call(this)}},{key:"__getValue",value:function(){return this._a.__getValue()*this._b.__getValue()}},{key:"interpolate",value:function(t){return new s(this,t)}},{key:"__attach",value:function(){this._a.__addChild(this),this._b.__addChild(this)}},{key:"__detach",value:function(){this._a.__removeChild(this),this._b.__removeChild(this),u(_(f.prototype),"__detach",this).call(this)}},{key:"__getNativeConfig",value:function(){return{type:'multiplication',input:[this._a.__getNativeTag(),this._b.__getNativeTag()]}}}])&&n(y.prototype,b),v&&n(y,v),f})();m.exports=y},212,[200,201,199,204]);