package fpinscala.errorhandling


import scala.{Option => _, Some => _, Either => _, _} // hide std library `Option`, `Some` and `Either`, since we are writing our own in this chapter

sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(a) => Some(f(a))
  }
  
  def getOrElse[B>:A](default: => B): B = this match {
    case None => default
    case Some(a) => a
  }

  def flatMap[B](f: A => Option[B]): Option[B] = map(f).getOrElse(None: Option[B])

  def orElse[B>:A](ob: => Option[B]): Option[B] = map(Some(_)).getOrElse(ob)

  def filter(f: A => Boolean): Option[A] = flatMap(a => if (f(a)) Some(a) else None)
}
case class Some[+A](get: A) extends Option[A]
case object None extends Option[Nothing]

object Option {
  def failingFn(i: Int): Int = {
    val y: Int = throw new Exception("fail!") // `val y: Int = ...` declares `y` as having type `Int`, and sets it equal to the right hand side of the `=`.
    try {
      val x = 42 + 5
      x + y
    }
    catch { case e: Exception => 43 } // A `catch` block is just a pattern matching block like the ones we've seen. `case e: Exception` is a pattern that matches any `Exception`, and it binds this value to the identifier `e`. The match returns the value 43.
  }

  def failingFn2(i: Int): Int = {
    try {
      val x = 42 + 5
      x + ((throw new Exception("fail!")): Int) // A thrown Exception can be given any type; here we're annotating it with the type `Int`
    }
    catch { case e: Exception => 43 }
  }

  def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length)
  
  /* Following uses population variance formula to traverse the sequence once only:
   * 
   * SUM((x - mean)^2) = SUM(x^2 - 2 * mean * x + mean^2)
   *                   = SUM(x^2) - 2 * mean * SUM(x) + n * mean^2
   *                   = SUM(x^2) - 2 * mean * (n * mean) + n * mean^2
   *                   = SUM(x^2) - 2 * n * mean^2 + n * mean^2
   *                   = SUM(x^2) - n * mean^2
   */
  def variance1(xs: Seq[Double]): Option[Double] = {
    case class Accumulator(count: Int, sum: Double, squares: Double) {
      def mean = sum / count
    }
    
    val accumulator = xs.foldLeft(Accumulator(0, 0.0, 0.0))(
      (acc, x) => acc.copy(acc.count + 1, acc.sum + x, acc.squares + x * x)
    )
    
    accumulator match {
      case Accumulator(0, _, _) => None
      case a @ Accumulator(n, sm, ssq) => Some(ssq / n  - a.mean * a.mean)
    }
  }
  
  def variance2(xs: Seq[Double]): Option[Double] = {
    val mn = mean(xs)
    mn match {
      case None => None
      case Some(m) => mean(xs.map(x => math.pow(x - m, 2))) 
    }
    // Note: When seeing this pattern...
    // a. If the last expression returns an Option, convert to flatMap.
    // b. If the last expression returns the type of the sequence, convert to map.
  }
  
  // Implement variance in terms of flatMap, as per the instructions:
  def variance(xs: Seq[Double]): Option[Double] = mean(xs).flatMap( 
    m => mean(xs.map(x => math.pow(x - m, 2)))
  )
  
  def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = sys.error("todo")

  def sequence[A](a: List[Option[A]]): Option[List[A]] = sys.error("todo")

  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = sys.error("todo")
}