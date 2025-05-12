package rand

import scala.util.Random

/** Declares a block in which a `Random` is available. Or, put another way, executes the specified computation using the
  * default `Random`.
  */
inline def apply[A](body: Random ?=> A): A = body(using new Random)

val int: Random ?=> Int = r ?=> r.nextInt

val bool: Random ?=> Boolean = r ?=> r.nextBoolean

def range(min: Int, max: Int): Random ?=> Int =
  val intervalLength = max.toLong - min.toLong
  val totalLength    = (Int.MaxValue.toLong - Int.MinValue.toLong).toDouble

  ((int - Int.MinValue.toLong) / totalLength * intervalLength + min).toInt

val lowerAscii: Random ?=> Char = range('a'.toInt, 'z'.toInt).toChar

val upperAscii: Random ?=> Char = range('A'.toInt, 'Z'.toInt).toChar

val digit: Random ?=> Char = range('0'.toInt, '9'.toInt).toChar

def oneOf[A](rands: Random ?=> A*): Random ?=> A = rands(range(0, rands.length))

val alpha: Random ?=> Char = oneOf(lowerAscii, upperAscii)

val alphaNum: Random ?=> Char = oneOf(lowerAscii, upperAscii, digit)

def listOfN[A](n: Int, ra: Random ?=> A): Random ?=> List[A] =
  if n <= 0 then Nil
  else ra :: listOfN(n - 1, ra)

def list[A](size: Random ?=> Int, ra: Random ?=> A): Random ?=> List[A] =
  listOfN(size, ra)

def identifier: Random ?=> String =
  (oneOf(alpha) :: list(range(0, 9), alphaNum)).mkString
