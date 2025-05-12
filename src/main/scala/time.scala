package time

// Capability of things that can access time.
trait Time:
  def epoch: Long

// Retrieve the current epoch
def epoch: Time ?=> Long = t ?=> t.epoch

// Executes the specified block using the system time.
def systemTime[A](body: Time ?=> A): A =
  given Time = new Time:
    override def epoch = System.currentTimeMillis
  body

// Executes the specified block using a fixed time.
def fixedTime[A](time: Long)(body: Time ?=> A): A =
  given Time = new Time:
    override def epoch = time
  body
