package result

import scala.util.control.NonFatal

/** Simple `Either`-like type specialized for success / failure. */
enum Result[+E, +A]:
  case Success(value: A)
  case Failure(error: E)

  def map[B](f: A => B): Result[E, B] = this match
    case Success(value) => Success(f(value))
    case Failure(e)     => Failure(e)

  def orElse[AA >: A](a: => AA): AA = this match
    case Success(value) => value
    case Failure(_)     => a

/** Useful syntax to be able to write things like `1.success` or `"some error".failure`. */
extension [A](value: A)
  def success[E]: Result[E, A] = Result.Success(value)
  def failure[E]: Result[A, E] = Result.Failure(value)

/** Type of things that can fail. */
infix type failsWith[A, E] = boundary.Label[Result[E, Nothing]] ?=> A

inline def apply[E, A](inline body: A failsWith E): Result[E, A] =
  boundary:
    Result.Success(body)

def catching[A](value: => A): A failsWith Throwable =
  try value
  catch case NonFatal(e) => fail(e)

def fail[E](error: E)(using boundary.Label[Result[E, Nothing]]): Nothing =
  boundary.break(error.failure)

extension [E, A](result: Result[E, A])
  def ?(using boundary.Label[Result[E, Nothing]]): A =
    result match
      case Result.Success(a) => a
      case Result.Failure(e) => boundary.break(Result.Failure(e))

  def !@#(using boundary.Label[Result[Nothing, A]]): E =
    result match
      case Result.Success(a) => boundary.break(Result.Success(a))
      case Result.Failure(e) => e
