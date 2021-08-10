/**
 * 
 */

function checkBillingAddress() {
	//if "the same as shipping address" check box has been checked, make the name and street address unable to modify
	if($("#theSameAsShippingAddress").is(":checked")) {
		$(".billingAddress").prop("disabled", true);
	} else {
		$(".billingAddress").prop("disabled", false);
	}
}

function checkPasswordMatch() {
	var password = $("#txtNewPassword").val();
	var confirmPassword = $("#txtConfirmPassword").val();
	
	if(password == "" && confirmPassword =="") {
		$("#checkPasswordMatch").html("");
		$("#updateUserInfoButton").prop('disabled', false);
	} else {
		if(password != confirmPassword) {
			$("#checkPasswordMatch").html("Passwords do not match!");
			$("#updateUserInfoButton").prop('disabled', true);
		} else {
			$("#checkPasswordMatch").html("Passwords match");
			$("#updateUserInfoButton").prop('disabled', false);
		}
	}
}

$(document).ready(function(){
	$(".cartItemQty").on("change", function(){
		var id = this.id;
		//display the udpate button for book qty changing in cart item
		$("#update-item-" + id).css("display", "inline-block");
	});
	//when click the check box, execute checkBillingAddress function
	$("#theSameAsShippingAddress").on('click', checkBillingAddress);
	$("#txtConfirmPassword").keyup(checkPasswordMatch);
	$("#txtNewPassword").keyup(checkPasswordMatch);
	
});