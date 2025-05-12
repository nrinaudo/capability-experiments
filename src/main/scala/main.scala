import console.*
import read.*
import print.*
import rand.*
import result.*
import scala.util.Random

/** Demonstration of the various effects we have by asking the user to guess a random number between 0 and 10.
  *
  * This involves console interaction (both printing and reading), error handling (for bad input), random numbers (for
  * generating the original random number)...
  */
@main
def run =
  // Reads from stdin, looping until a valid integer is entered.
  def nextInput: (Reader, Printer) ?=> Int =
    readInt.orElse:
      println("Invalid input. Please type in a number")
      nextInput

  val p: (Random, Reader, Printer) ?=> Unit =
    def run(remaining: Int, target: Int): Unit =
      if remaining == 0 then println("Sorry, no remaining attempts")
      else
        val guess = nextInput
        if guess == target then println("Congratulations!")
        else
          if guess < target then println("Target is higher")
          else println("Target is lower")
          run(remaining - 1, target)

    println("Enter a number between 1 and 10")
    run(5, rand.range(1, 10))

  rand:
    console:
      p
