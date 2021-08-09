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

$(document).ready(function(){
	$(".cartItemQty").on("change", function(){
		var id = this.id;
		//display the udpate button for book qty changing in cart item
		$("#update-item-" + id).css("display", "inline-block");
	});
	//when click the check box, execute checkBillingAddress function
	$("#theSameAsShippingAddress").on('click', checkBillingAddress);
});