package fpinscala.laziness

import Stream._
sealed trait Stream[+A] {
  
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
  
  def mapUsingUnfold[B](f: A => B): Stream[B] = unfold(this){
    case Empty => None 
    case Cons(h, t) => Some((f(h()),t())) 
  }
  
  def takeUsingUnfold(n: Int): Stream[A] = unfold[A, (Int, Stream[A])]((n, this)) {
    case (0, _) => None
    case (_, Empty) => None
    case (n, Cons(h, t)) => Some(h(), (n-1, t()))
  }
  
  def takeWhileUsingUnfold(p: A => Boolean): Stream[A] = unfold[A, Stream[A]](this)(s => s match {
    case Cons(h, t) if p(h()) => Some(h(), t())
    case _ => None
  })
  
  def zipWithUsingUnfold[B, C](bs: Stream[B])(f: (A, B) => C): Stream[C] = unfold((this, bs)){
    case (Cons(ha, ta), Cons(hb, tb)) => Some(f(ha(), hb()), (ta(), tb()))
    case _ => None
  }
  
  def zipAll[B](bs: Stream[B]): Stream[(Option[A], Option[B])] = {
    unfold[(Option[A], Option[B]), (Stream[A], Stream[B])]((this, bs)) {
      case (Cons(ha, ta), Cons(hb, tb)) => Some(
          (Some(ha()) -> Some(hb()), ta() -> tb())
      )
      case (Cons(ha, ta), Empty) => Some(
          ( Some(ha()) -> None, (ta() -> Empty) )
      )
      case (Empty, Cons(hb, tb)) => Some(
          ( (None -> Some(hb())), (Empty, tb()) )
      )
      case _ => None
    }
  }
  
  def startsWith[B](s: Stream[B]): Boolean = this.zipAll(s).takeWhile(_._2 != None).forAll(ab => ab._1 == ab._2)
  
  def tails: Stream[Stream[A]] = unfold[Stream[A], Option[Stream[A]]](Some(this)) {
    case None => None
    case Some(Empty) => Some(Empty, None)
    case Some(s @ Cons(h, t)) => Some((s, Some(t())))
  }
  
  def scanRight[B](z: B)(f: (A, => B) => B): Stream[B] = {
    def streamAndLast(s: Stream[A])(f: (A, => B) => B): (Stream[B], B) = {
      s match {
        case Empty => (Stream(z), z) 
        case Cons(h, t) => {
          val (strm, last) = streamAndLast(t())(f)
          val curr = f(h(), last)
          val newStrm = cons(curr, strm)
          (newStrm, curr)
        }
      }
    }
    
    val (finalStrm, _) = streamAndLast(this)(f)
    finalStrm
  }
  // Q: Could unfold be used? 
  // A: I don't think so... unfold goes from left to right, scanRight from right to left.
  
  // Q: Could it be implemented using another another function we've written?
  // A: I didn't think of any.
  // UPDATE: model answer uses foldRight.
  
  // Include following to test whether model answer streams better than my solution...
  def modelAnswerScanRight[B](z: B)(f: (A, => B) => B): Stream[B] =
    foldRight((z, Stream(z)))((a, p0) => {
      // p0 is passed by-name and used in by-name args in f and cons. So use lazy val to ensure only one evaluation...
      lazy val p1 = p0
      val b2 = f(a, p1._1)
      (b2, cons(b2, p1._2))
    })._2
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

  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = f(z) match {
    case None => Empty
    case Some((a, s)) => cons(a, unfold(s)(f))
  }
  
  def fibsUsingUnfold(): Stream[Int] = unfold((0,1)) {
    case (curr, next) => Some((curr, (next, curr + next)))
  }
  
  def fromUsingUnfold(n: Int): Stream[Int] = unfold(n)(i => Some(i, i + 1))
  
  def constantUsingUnfold[A](a: A): Stream[A] = unfold(a)(i => Some(i, i))
  
  def onesUsingUnfold(): Stream[Int] = unfold(1)(i => Some(1, 1))
}
