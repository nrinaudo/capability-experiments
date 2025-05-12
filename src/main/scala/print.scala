package print

/** How to print things "somewhere", such as to stdout or a file. */
trait Printer:
  def print(msg: String): Unit
  def println(msg: String): Unit

def print(msg: String): Printer ?=> Unit   = p ?=> p.print(msg)
def println(msg: String): Printer ?=> Unit = p ?=> p.println(msg)
