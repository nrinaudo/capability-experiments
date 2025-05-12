package file

import java.io.*
import result.*
import print.*

def printer[A](file: File)(body: Printer ?=> A): A failsWith IOException =
  printWriter(file): out ?=>
    val printer = new Printer:
      override def print(msg: String)   = out.print(msg)
      override def println(msg: String) = out.println(msg)
    body(using printer)

def printWriter[A](file: File)(body: PrintWriter ?=> A): A failsWith IOException =
  var out: PrintWriter | Null = null
  try
    out = new PrintWriter(file, "UTF-8")
    body(using out)
  catch case e: IOException => fail(e)
  finally
    if out != null then
      try out.close()
      catch case e: IOException => fail(e)
