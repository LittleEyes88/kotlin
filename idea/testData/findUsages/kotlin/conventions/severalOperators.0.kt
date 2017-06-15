// PSI_ELEMENT: org.jetbrains.kotlin.psi.KtNamedFunction
// OPTIONS: usages

open class Diction {
    operator fun <caret>minus(other: Diction): Diction { return Diction() }
    operator fun plus(other: Diction): Diction { return Diction() }
}

operator fun Diction.times(other: Diction) = Diction()

fun test(d1: Diction, d2: Diction) {
    val a = d1 + d2
    val b = d1 - d2
    val c = d1 * d2
}