package logger

import console.*
import print.*
import time.*

// Abstracts over things that can log.
trait Logger:
  def info(msg: String): Unit
  def error(msg: String): Unit

// Convenient syntax - if you have a `Logger` in scope, you can simply
// call `info` and `error`.
def info(msg: String): Logger ?=> Unit  = l ?=> l.info(msg)
def error(msg: String): Logger ?=> Unit = l ?=> l.error(msg)

// If we have a Console in scope, we get a Logger for free.
given Console => Logger = new Logger:
  override def info(msg: String) =
    color(Color.Green)
    println(msg)

  override def error(msg: String) =
    color(Color.Red)
    println(msg)

// If we have a Printer in scope, we get a Logger for free.
given Printer => Logger = new Logger:
  override def info(msg: String)  = println(msg)
  override def error(msg: String) = println(msg)

/** Modifies an existing logger to add a timestamp on each entry. */
def timestamped[A](body: Logger ?=> A): (Time, Logger) ?=> A = (time, logger) ?=>
  val formattedLogger = new Logger:
    override def info(msg: String)  = logger.info(s"$epoch: $msg")
    override def error(msg: String) = logger.error(s"$epoch: $msg")

  body(using formattedLogger)
