package custom.scriptDefinition

import kotlin.script.dependencies.*
import kotlin.script.templates.*
import java.io.File
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

class TestDependenciesResolver : ScriptDependenciesResolver {
    override fun resolve(
            script: ScriptContents,
            environment: Map<String, Any?>?,
            report: (ScriptDependenciesResolver.ReportSeverity, String, ScriptContents.Position?) -> Unit, previousDependencies: KotlinScriptExternalDependencies?
    ): Future<KotlinScriptExternalDependencies?> {
        script.text?.let { text ->
            text.lines().forEachIndexed { lineIndex, line->
                Regex("java").findAll(text).forEach {
                    val columnIndex = it.range.first
                    report(ScriptDependenciesResolver.ReportSeverity.ERROR, "Can't use java", ScriptContents.Position(lineIndex, columnIndex))
                }
                Regex("scala").findAll(text).forEach {
                    val columnIndex = it.range.first
                    report(ScriptDependenciesResolver.ReportSeverity.WARNING, "Shouldn't use scala", ScriptContents.Position(lineIndex, columnIndex))
                }

            }
        }


        return CompletableFuture.completedFuture(
                object : KotlinScriptExternalDependencies {
                    override val classpath: Iterable<File> = listOf(environment?.get("template-classes") as File)
                })

    }
}

@ScriptTemplateDefinition(TestDependenciesResolver::class, scriptFilePattern = "script.kts")
class Template: Base()

open class Base {
    val i = 3
    val str = ""
}