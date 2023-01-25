var script_url = 'https://script.google.com/macros/s/AKfycbwBNDa0KiMKBEJEduvkh3CKcSM3Ml5wnlewL84A/exec';
var tc_id, tc_license;
var blogId, blogUrl;
jQuery.ajax({
    type: 'POST',
    url: 'https://cdn.templateclue.com/gumroad.php',
    data: {
        'product_permalink': tc_id,
        'license_key': tc_license
    },
    dataType: 'json',
    success: function (_0xe746x6) {
        if (_0xe746x6.success === false) {
            var _0xe746x7 = script_url + '?callback=ctrlq&blogId=' + blogId + '&license=' + tc_license + '&blogUrl=' + blogUrl + '&productName=' + _0xe746x6.purchase.product_name + '&userEmail=' + _0xe746x6.purchase.email + '&action=insert';
            var _0xe746x8 = jQuery.ajax({
                crossDomain: true,
                url: _0xe746x7,
                method: 'GET',
                dataType: 'jsonp'
            })
        } else {
            jQuery('body').replaceWith("<div id='licensenotice'><a href='http://www.templateclue.com/'><h1>Templateclue</h1></a><h2>Oops!! Invalid License Key Found</h2><br /><h3>Please enter a valid license key.</h3><h4><a href='https://youtu.be/U80IYw6PieM' style='color: #F96E5B;'>Click here</a> to watch the full video on how to activate blogger template using license key</h4></div><style>#licensenotice{ opacity: 1 !important; display: flex; flex-direction: column; justify-content: center; align-items: center; visibility: visible !important; position: fixed !important; top: 0 !important; left: 0 !important; z-index: 9999999 !important; background: #f9f9f9; font-size: 120%; width: 100%; height: 100%; color: #222; line-height: 37px; text-align: center; }</style>")
        }
    },
    error: function () {}
});
function ctrlq(_0xe746xa) {
    if (_0xe746xa.result == true) {
        jQuery('body').replaceWith("<div id='licensenotice'><a href='http://www.templateclue.com/'><h1>Templateclue</h1></a><h2>Oops!! Duplicate License Key Found</h2><br /><h3>Please enter a valid license key.</h3></div><style>#licensenotice{ opacity: 1 !important; display: flex; flex-direction: column; justify-content: center; align-items: center; visibility: visible !important; position: fixed !important; top: 0 !important; left: 0 !important; z-index: 9999999 !important; background: #f9f9f9; font-size: 120%; width: 100%; height: 100%; color: #222; line-height: 37px; text-align: center; }</style>")
    } else {}
}
function read_value() {
    var _0xe746x7 = script_url + '?action=read';
    jQuery.getJSON(_0xe746x7, function (_0xe746xc) {
        console.log('read_value', _0xe746xc)
    })
}
