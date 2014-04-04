import groovy.text.SimpleTemplateEngine
import groovy.io.FileType

def cli = new CliBuilder()

cli.l(longOpt: "loop", "Continuous Build Loop for Writing the Cookbook")

def opts = cli.parse(args)

def runBuild = { ->
    def engine = new SimpleTemplateEngine()

    def pageDir = new File("src/book")

    def buildDir = new File("build")

    def config = new ConfigSlurper().parse(new File("config.groovy").toURI().toURL())

    buildDir.deleteDir()

    buildDir.mkdirs()

    pageDir.eachFileRecurse(FileType.FILES) { pageFile ->
        def out = engine.createTemplate(pageFile.newReader()).make(config)
        def outFile = new File(pageFile.absolutePath.replace("src/book/", "build/"))
        outFile.parentFile.mkdirs()
        out.writeTo(outFile.newWriter())
    }
}

if (!opts) {
    cli.usage()
    System.exit(0)
}

if (opts.loop) {
    println "Build Loop Started"
    while (true) {
        try {
            runBuild()
        } catch(e) {
            println "Build Failed: ${e.message}"
        }
        sleep(2000)
    }
} else {
    runBuild()
}
