function validate() {
	
	clear();
	var sub=document.getElementById("subject").value;
	var validity=document.getElementById("validity").value;
	if (sub=="")
	{
		document.getElementById("errormsg").innerHTML="Subject cannot be empty";
		document.getElementById('subject').style.border = "solid 3px red";
		return false;
	}
	if(validity=="")
	{
		document.getElementById("errormsg").innerHTML="select validity date";
		document.getElementById('validity').style.border = "solid 3px red";
		return false;
	}
	
	return true;
}

function clear()
{
	document.getElementById("errormsg").innerHTML="";
	document.getElementById('subject').style.border = "";
	document.getElementById('validity').style.border = "";

}