function validate() {
	
	clear();
	var sub=document.getElementById("subject").value;
	if (sub=="")
	{
		document.getElementById("errormsg").innerHTML="Subject cannot be empty";
		document.getElementById('subject').style.border = "solid 3px red";
		return false;
	}
	
	
	return true;
}

function clear()
{
	document.getElementById("errormsg").innerHTML="";
	document.getElementById('subject').style.border = "";

}