/* Defines a URL */
def url = "http://www.google.com/".toURL()
/* Downloads the File to the Specified Path */
new File("google-index.html") << url.newInputStream()
