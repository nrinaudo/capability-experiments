package boundary

/** Shows how implement "go to" statements.
  *
  * This simply relies on exceptions under the hood (but relatively cheap ones, with no stack trace allocated).
  */

/** Marks a place where one can "go" to, possibly carrying a value. */
final class Label[-A]

/** Internal exception, used to jump from anywhere to a given `Label`. */
private final case class Break[A](value: A, label: Label[A]) extends RuntimeException(null, null, false, false)

/** Breaks to the specified label with the specified value. Breaking requires a `Label`, which marks the presence of
  * someone ready to catch it.
  */
def break[A](value: A)(using l: Label[A]): Nothing = throw Break(value, l)

/** Allows the following syntax:
  * {{{
  * boundary:
  *   // ...
  *   break("foo")
  * }}}
  *
  * It's possible to nest boundaries and to specify which one to break to by explicitly manipulating `Label` values.
  */
inline def apply[A](inline body: Label[A] ?=> A): A =
  val label = new Label[A]
  try body(using label)
  catch case Break(value, `label`) => value
