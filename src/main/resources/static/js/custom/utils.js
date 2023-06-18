var ValidationUtils = (function () {
  function isEmpty(val) {
    if (val === null || val === undefined) return true;
    if (typeof val === "string" && val.trim() === "") return true;
    if (typeof val === "object" && Object.keys(val).length === 0) return true;
    return false;
  }

  return {
    isEmpty: isEmpty,
  };
})();

var UrlUtils = (function () {
  function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
      results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return "";
    return decodeURIComponent(results[2].replace(/\+/g, " "));
  }

  return {
    getParameterByName: getParameterByName,
  };
})();

var StringUtils = (function () {
  function getMoneyFormat(val) {
    if (ValidationUtils.isEmpty(val)) return "";
    if (typeof val !== "number") val = Number(val);
    return val
      .toFixed(1)
      .replace(/\d(?=(\d{3})+\.)/g, "$&,")
      .slice(0, -2);
  }

  function getNumber(val) {
    if (isNaN(+val)) return 1;
    return +val;
  }

  return {
    getMoneyFormat: getMoneyFormat,
    getNumber: getNumber,
  };
})();

var AlertUtils = (function () {
  function showAlert(options) {
    $.alert({
      title: options.title,
      content: options.content,
      type: options.type,
    });
  }

  function showConfirm(options, onConfirm, onCancel) {
    $.confirm({
      title: options.title,
      content: options.content,
      buttons: {
        confirm: {
          text: constants.confirm,
          btnClass: "btn-green",
          keys: ["enter"],
          action: function () {
            onConfirm();
          },
        },
        cancel: {
          text: constants.cancel,
          action: function () {
            onCancel();
          },
        },
      },
    });
  }

  return {
    showConfirm: showConfirm,
    showAlert: showAlert,
  };
})();
