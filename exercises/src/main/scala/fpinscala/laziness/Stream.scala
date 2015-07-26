package fpinscala.laziness

import Stream._
trait Stream[+A] {

  def foldRight[B](z: => B)(f: (A, => B) => B): B = // The arrow `=>` in front of the argument type `B` means that the function `f` takes its second argument by name and may choose not to evaluate it.
    this match {
      case Cons(h,t) => f(h(), t().foldRight(z)(f)) // If `f` doesn't evaluate its second argument, the recursion never occurs.
      case _ => z
    }

  def exists(p: A => Boolean): Boolean = 
    foldRight(false)((a, b) => p(a) || b) // Here `b` is the unevaluated recursive step that folds the tail of the stream. If `p(a)` returns `true`, `b` will never be evaluated and the computation terminates early.
  
  def toList: List[A] = this match {
    case Empty => List.empty
    case Cons(h, t) => h() :: t().toList
  }
      
  @annotation.tailrec
  final def find(f: A => Boolean): Option[A] = this match {
    case Empty => None
    case Cons(h, t) => if (f(h())) Some(h()) else t().find(f)
  }
  
  def take(n: Int): Stream[A] = this match {
    case Cons(h, t) if n <= 0 => Empty
    case Cons(h, t) => Cons(h, () => t().take(n-1))
    case _ => Empty
  }

  def drop(n: Int): Stream[A] = this match {
    case _ if n < 0 => error("It isn't possible to drop a negative number of items from a stream")
    case _ if n == 0 => this
    case Cons(_, t) => t().drop(n-1)
    case _ => Empty
  }

  def takeWhile(p: A => Boolean): Stream[A] = this match {
    case Cons(h, t) => {
      val head = h()
      if (p(head)) {
        Cons(() => head, () => t().takeWhile(p)) 
      } else {
        Empty
      }
    }
    case _ => Empty
  }

  def forAll(p: A => Boolean): Boolean = foldRight(true)(p(_) && _)
  
  def takeWhileUsingFoldRight(p: A => Boolean): Stream[A] = foldRight[Stream[A]](Empty)(
    (a, b) => if (p(a)) cons(a, b) else Empty
  )

  def headOption: Option[A] = foldRight[Option[A]](None)((a,b) => Some(a))

  // 5.7 map, filter, append, flatmap using foldRight. Part of the exercise is
  // writing your own function signatures.
  def map[B](f: A => B): Stream[B] = foldRight[Stream[B]](Empty){ (a, bs) => cons(f(a), bs)}
  
  def filter[B >: A](p: B => Boolean): Stream[B] = foldRight[Stream[B]](Empty){
    (a, bs) => if (p(a)) cons(a, bs) else bs
  }
  
  def append[B >: A](bs: => Stream[B]): Stream[B] = foldRight(bs)(cons(_, _))
  
  def flatMap[B >: A](f: A => Stream[B]): Stream[B] = foldRight[Stream[B]](Empty){
    (a, bs) => f(a).append[B](bs)
  }

  def startsWith[B](s: Stream[B]): Boolean = sys.error("todo")
}

case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty 
    else cons(as.head, apply(as.tail: _*))

  val ones: Stream[Int] = Stream.cons(1, ones)
  
  def constant[A](a: A): Stream[A] = cons(a, constant(a))
  
  def from(n: Int): Stream[Int] = cons(n, from(n+1))
  
  def fibs(): Stream[Int] = {
    def nextFibs(prev: Int, curr: Int): Stream[Int] = cons(curr, nextFibs(curr, prev + curr))
    nextFibs(1, 0)  // Passing 1 as prev is a hack to kick-start the 1's
    
    // blah... I could have written this more cleanly as nextFibs(curr, next) to avoid the hack
  } 

  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = sys.error("todo")
}