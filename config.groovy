import groovy.xml.*

def markup = { closure ->
    def writer = new StringWriter()
    def builder = new MarkupBuilder(new PrintWriter(writer))
    builder.doubleQuotes = true
    builder.expandEmptyElements = true
    closure.delegate = builder
    closure()
    return writer.toString()
}

this.markup = markup

indent = "&nbsp;&nbsp;&nbsp;&nbsp;"

highlighting = { ->
    markup {
        link(rel: "stylesheet", href: "http://alexgorbatchev.com/pub/sh/current/styles/shCore.css")
        link(rel: "stylesheet", href: "http://alexgorbatchev.com/pub/sh/current/styles/shThemeDefault.css")
        script(src: "http://alexgorbatchev.com/pub/sh/current/scripts/shCore.js")
        script(src: "http://alexgorbatchev.com/pub/sh/current/scripts/shAutoloader.js")
        script(src: "http://alexgorbatchev.com/pub/sh/current/scripts/shBrushGroovy.js")
        script("SyntaxHighlighter.all();")
    }
}

include_src = { path ->
    markup {
        pre(class: "brush: groovy", new File("src/groovy", path).text)
    }
}