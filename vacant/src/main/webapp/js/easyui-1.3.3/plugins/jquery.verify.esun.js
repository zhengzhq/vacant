/**
 * 表单验证Esun扩展
 * idCard:身份证号简单验证
 */
(function($){
	if($.fn.validatebox){
		var idCardRegex = /^\d{15}$|^\d{17}[a-zA-Z0-9]$/;
		$.extend($.fn.validatebox.defaults.rules, {
			idCard : {
				validator : function(value, params){
					return idCardRegex.test(value);
				},
				message : '请输入正确身份证号.'
			}
		});
		$.fn.verify = $.fn.validatebox;
	}
})(jQuery);