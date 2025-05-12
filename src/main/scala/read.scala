package read

import result.*

trait Reader:
  def readLine: String

val readLine: Reader ?=> String                = p ?=> p.readLine
val readInt: Reader ?=> Result[Throwable, Int] = result(catching(readLine.toInt))
