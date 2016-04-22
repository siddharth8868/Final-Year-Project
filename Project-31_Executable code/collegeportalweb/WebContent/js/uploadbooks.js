
function validate(thisform){
	clears();
	var title=document.getElementById("title").value;
	var author=document.getElementById("author").value;
	var subject=document.getElementById("subject").value;
	var file=document.getElementById("file").value;
	var cover=document.getElementById("cover").value;
	//document.getElementById("errormsg").innerHTML="hello buddy";
	//return false;
	//types(thisform);
	
	if(title=="")
		{
			document.getElementById("errormsg").innerHTML="Title cannot be empty";
			document.getElementById('title').style.border = "solid 3px red";
			return false;
		}
	if(title.length>=20)
	{
		document.getElementById("errormsg").innerHTML="Title should not exceed 20words";
		document.getElementById('title').style.border = "solid 3px red";
		return false;
	}
	if(author=="")
	{
		document.getElementById("errormsg").innerHTML="Title cannot be empty";
		document.getElementById('author').style.border = "solid 3px red";
		return false;
	}
	if(author.length>=20)
	{
		document.getElementById("errormsg").innerHTML="author should not exceed 20words";
		document.getElementById('author').style.border = "solid 3px red";
		return false;
	}
	if(subject=="")
	{
		document.getElementById("errormsg").innerHTML="Title cannot be empty";
		document.getElementById('subject').style.border = "solid 3px red";
		return false;
	}
	if(subject.length>=20)
	{
		document.getElementById("errormsg").innerHTML="subject should not exceed 20words";
		document.getElementById('subject').style.border = "solid 3px red";
		return false;
	}
	

   	  if (file=="") {
		  document.getElementById("errormsg").innerHTML="Title cannot be empty";
		  document.getElementById('file').style.border = "solid 3px red";
		  return false;
	  }
	  if(!(checkfile())) {
		return false;  
	  }
	  
	  if (cover=="") {
		  document.getElementById("errormsg").innerHTML="Title cannot be empty";
		  document.getElementById('cover').style.border = "solid 3px red";
		  return false;
	  }
	  if(!(checkcover())) {
		return false;  
	  }
	  
	  return true;
	
}



function checkfile(){
	document.getElementById("errormsg").innerHTML=""; 
    document.getElementById("file").style.border="";
	if(!(checkfilename()))
		{
			return false;
		}
	if(!(checkfilesize()))
	{
		return false;
	}

	return true;
	
}

function checkfilename()
{
	var docs= document.getElementById("file").value;
	var extns=document.getElementById("type").value;
	var ext=docs.substring(docs.lastIndexOf('.')+1);
	if(!(extns == ext))
    	{
    	document.getElementById("errormsg").innerHTML="File Type and File not matched";
        document.getElementById("file").style.border="solid 3px red";
        return false;
    	}
    return true;
}


function checkfilesize() {
	var sizes = document.getElementById('file').files[0].size;
	if(sizes>=30000000) //file size should be more than 30mb
		{
		document.getElementById("errormsg").innerHTML="file size should be less than 30mb";
        document.getElementById("file").style.border="solid 3px red";
        return false;
		}
	return true;
}



function checkcover(){
	
	document.getElementById("cover").style.border="";
	document.getElementById("errormsg").innerHTML=""; 
	if(!(checkcovername()))
		{
			return false;
		}
	if(!(checkcoversize()))
	{
		return false;
	}

	return true;
}


function checkcovername()
{
	var docs = document.getElementById("cover").value;
	var ext = docs.substring(docs.lastIndexOf('.')+1);
    if(! ((ext=="jpg") || (ext=="jpeg") || (ext=="png") || (ext=="gif")))
    	{
    	document.getElementById("errormsg").innerHTML="only jpg/jpeg/png/gif files supported";
        document.getElementById("cover").style.border="solid 3px red";
        return false;
    	}
    return true;
}


function checkcoversize() {
	var sizes = document.getElementById('cover').files[0].size;
	if((500000<=sizes)) //file size should be less than 500kb
		{
		document.getElementById("errormsg").innerHTML="file size should be less than 500kb";
        document.getElementById("cover").style.border="solid 3px red";
        return false;
		}
	return true;
}


function clears()
{
	document.getElementById("errormsg").innerHTML=""; 
    document.getElementById("title").style.border="";
    document.getElementById("author").style.border="";
    document.getElementById("subject").style.border="";
    document.getElementById("type").style.border="";
    document.getElementById("file").style.border="";
    document.getElementById("cover").style.border="";
    
}
