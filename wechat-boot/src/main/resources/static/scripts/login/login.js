$(function(){
	//登录
	$("#login").click(function(){
		var loginUserName = $("#username").val();
		var loginPassword = $("#password").val();
		var data = {
						name : loginUserName,
						pwd : loginPassword
					};
		//请求
		$.ajax({
			type: "post",
			url: "login.do",
			data: data,
			dataType : "json",
			success: function(result){
				if(result.status != 0){
					alert(result.msg);
					return;
				}
				loginSuccess(result);
			},
			error:loginError
		});
	});
	
	//注册
	$("#regist_button").click(function(){
		//允许跨域访问
		jQuery.support.cors = true;
		var username = $("#regist_username").val();
		var password = $("#regist_password").val();
		var nikeName = $("#nickname").val;
		alert(username +","+password)
		var user = {
				"cnUserName":username ,
				"cnUserPassword":password,
				"nickName":nickname
				};

		$.ajax({
			type:"post",
			url : basePath + "user/regist.do",
			data:JSON.stringify(user),
			dataType : "json",
			contentType : "application/json",
//			beforeSend: function(xhr){
//				xhr.setRequestHeader('Content-Type', 'application/json');
//				//xhr.setRequestHeader('Authorization', token);
//				return checkUserName(username);
//			},
			success:function(result){
				if($.isFunction(registSuccess)){
					registSuccess(result);
				}
			},
			error:registError
		});
		
	});
	
	//登出
	$("#logout").click(function(){
		logout();
	});
	

	
});


/***
 * 判断用户名是否重复
 * @param username
 * @returns {Boolean}
 */
function checkUserName(username){
	var b = false;
	$.ajax({//提交之前验证用户名是否重复
		type:"post",
		async:false,
		url: basePath + "user/checkUserName?" + "username=" + username, 
		dataType : "json",
		contentType : "application/json",
		beforeSend: function(xhr){xhr.setRequestHeader('Content-Type', 'application/json');},
		success: function(result){
	        if(result.status == -2){
	        	get('warning_1').style.display='block';
	        	b= false;
	        }else{
	        	b=true;
	        }
        },
        error:function(){
        	b=false;
        }
	});
	return b;
};

/**
 * 退出登录
 */
function logout(){
	var userId = getCookie("tarena_cloud_note_user");
	delCookie("tarena_cloud_note_user");//删除用户cookie
	delCookie(userId+"_token");//删除token
	location.href="login";
}

/**
 * 修改密码时验证老密码是否正确
 * @param oldPwd
 * @returns {Boolean}
 */
function validOldPwd(oldPwd){
	var flog = false;
	var loginUserName = getCookie(UserName);

	jQuery.support.cors = true;
	$.ajax({
		type:"post",
		async:false,
		url:basePath + "user/checkUserOldpwd/"+loginUserName+"/"+oldPwd,
		dataType : "json",
		beforeSend: function(xhr){
			xhr.setRequestHeader('Content-Type', 'application/json');
		},
		success:function(result){
			if(result.status == 1){
				flog = true;
			}
		}
	});
	return flog;
}


/**
 * 修改密码
 * @param changepwdSuccess
 * @param changepwdError
 */
function changepwd(changepwdSuccess,changepwdError){
	var loginUserId = getCookie(cookie_key);
	var pwd=$("#new_password").val();
	$.ajax({
		type:"post",
		url: basePath + "user/resetPwd/"+loginUserId+"/"+pwd, 
		dataType : "json",
		contentType : "application/json",
		beforeSend: function(xhr){
			var token=getCookie(loginUserId+"_token");
			xhr.setRequestHeader('Content-Type', 'application/json');
			xhr.setRequestHeader('Authorization', token);
		},
		success: function(result){
			changepwdSuccess(result);
        },
        error:function(){
        	changepwdError();
        }
	});
}



