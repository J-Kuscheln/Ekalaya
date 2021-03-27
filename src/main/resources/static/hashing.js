function hashCode() {
	
  str = document.getElementsByName("email").item(0).value + document.getElementsByName("password").item(0).value;
  var hash = 0, i, chr;
  for (i = 0; i < str.length; i++) {
    chr   = str.charCodeAt(i);
    hash  = ((hash << 5) - hash) + chr;
    hash |= 0; // Convert to 32bit integer
  }
	if(document.getElementsByName("password").item(0).value==null) return false;
	document.getElementsByName("password").item(0).value = hash;
	console.log(hash);

}