package console

import print.*
import read.*

/** A console is both a `Printer` (to stdout) and a `Reader` (from stdin). */
trait Console extends Printer with Reader:
  def color(c: Color): Unit

/** Supported colors. */
enum Color:
  case Red
  case Green
  case Reset

def color[A](c: Color)(body: Console ?=> A): Console ?=> A = console ?=>
  console.color(c)
  val result = body
  console.color(Color.Reset)
  result

def apply[A](body: Console ?=> A): A =
  given Console = new Console:
    def readLine             = Console.in.readLine
    def print(msg: String)   = Console.print(msg)
    def println(msg: String) = Console.println(msg)
    def color(c: Color) = c match
      case Color.Red   => Console.print(Console.RED)
      case Color.Green => Console.print(Console.GREEN)
      case Color.Reset => Console.print(Console.RESET)

  body
