package fpinscala.datastructures

sealed trait List[+A] // `List` data type, parameterized on a type, `A`
case object Nil extends List[Nothing] // A `List` data constructor representing the empty list
/* Another data constructor, representing nonempty lists. Note that `tail` is another `List[A]`,
which may be `Nil` or another `Cons`.
 */
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List { // `List` companion object. Contains functions for creating and working with lists.
  def sum(ints: List[Int]): Int = ints match { // A function that uses pattern matching to add up a list of integers
    case Nil => 0 // The sum of the empty list is 0.
    case Cons(x,xs) => x + sum(xs) // The sum of a list starting with `x` is `x` plus the sum of the rest of the list.
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x,xs) => x * product(xs)
  }

  def apply[A](as: A*): List[A] = // Variadic function syntax
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  val x = List(1,2,3,4,5) match {
    case Cons(x, Cons(2, Cons(4, _))) => x
    case Nil => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t) => h + sum(t)
    case _ => 101
  }

  def append[A](a1: List[A], a2: List[A]): List[A] =
    a1 match {
      case Nil => a2
      case Cons(h,t) => Cons(h, append(t, a2))
    }

  def foldRight[A,B](as: List[A], z: B)(f: (A, B) => B): B = // Utility functions
    as match {
      case Nil => z
      case Cons(x, xs) => f(x, foldRight(xs, z)(f))
    }

  def sum2(ns: List[Int]) =
    foldRight(ns, 0)((x,y) => x + y)

  def product2(ns: List[Double]) =
    foldRight(ns, 1.0)(_ * _) // `_ * _` is more concise notation for `(x,y) => x * y`; see sidebar


  def tail[A](l: List[A]): List[A] = l match {
    case Nil => throw new IllegalArgumentException("Cannot take the tail of an empty list")
    case Cons(h, t) => t
  }

  def setHead[A](l: List[A], h: A): List[A] = l match {
    case Nil => throw new IllegalArgumentException("Cannot set the head of an empty list")
    case Cons(_, t) => Cons(h, t)
  }

  def drop[A](l: List[A], n: Int): List[A] = if (n <= 0) l else l match {
    case Nil => Nil
    case Cons(h, t) => drop(t, n-1)
  }

  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = l match {
    case Nil => Nil
    case Cons(h, t) if f(h) => dropWhile(t, f)
    case _ => l 
  }

  def init[A](l: List[A]): List[A] = l match {
    case Nil => throw new IllegalArgumentException("Cannot find the initial part of an empty list")
    case Cons(h, Nil) => Nil
    case Cons(h, Cons(_, Nil)) => Cons(h, Nil)
    case Cons(h, t) => Cons(h, init(t))
  }

  def length[A](l: List[A]): Int = foldRight(l, 0)((a: A, l: Int) => l + 1)

  @annotation.tailrec
  def foldLeft[A,B](l: List[A], z: B)(f: (B, A) => B): B = l match {
    case Nil => z
    case Cons(h, t) => foldLeft(t, f(z, h))(f) 
  }
  
  def sumWithFoldLeft(l: List[Int]): Int = foldLeft(l, 0)(_ + _)
  def productWithFoldLeft(l: List[Double]): Double = foldLeft(l, 1.0)(_ * _)
  def reverse[A](l: List[A]): List[A] = foldLeft(l, Nil: List[A])((rev,a)=>Cons(a,rev))
  
  // Exercise 3.13: implement foldLeft and foldRight in terms of the other...
  def foldLeft2[A,B](l: List[A], z: B)(f: (B, A) => B): B = foldRight(reverse(l), z)((a,b)=>f(b, a)) 
  def foldRight2[A,B](l: List[A], z: B)(f: (A, B) => B): B = foldLeft(reverse(l), z)((b,a)=>f(a, b))
  
  // Exercise 3.14: implement append in terms of either foldLeft or foldRight:
  def append2[A](a1: List[A], a2: List[A]): List[A] = foldRight(a1, a2)(Cons(_, _))
  
  // Exercise 3.15: Write a function that concatenates a list of lists into a single list
  def concatListOfLists[A](ll: List[List[A]]) = foldRight(ll, Nil: List[A])(append)
  
  // Exercise 3.16: Write a function that transforms a list of integers by adding 1 to each element:
  def addOne(l: List[Int]): List[Int] = foldRight(l, Nil: List[Int])((a,b) => Cons(a + 1, b))
  
  // Exercise 3.17: Turn each value in a List[Double] into a string using d.ToString
  def doublesToStrings(l: List[Double]): List[String] = 
    foldRight(l, Nil: List[String])((h, t) => Cons(h.toString, t))
  
  // Exercise 3.18: Write a function map that generalizes modifications 
  // to the contents of a list while preserving its structure:
  def map[A,B](l: List[A])(f: A => B): List[B] = foldRight(l, Nil: List[B])((h, t) => Cons(f(h), t))
  
  // Exercise 3.19: Write a filter function. Use it to remove all odd numbers from a List[Int]:
  def filter[A](l: List[A])(pred: A => Boolean): List[A] = 
    foldRight2(l, Nil: List[A])((h, t) => if (pred(h)) Cons(h, t) else t)
  
  // Exercise 3.20: flatMap
  def flatMap[A, B](l: List[A])(f: A => List[B]): List[B] = concatListOfLists(map(l)(f))
  
  def flatMap2[A, B](l: List[A])(f: A => List[B]): List[B] =
    foldRight(l, Nil: List[B])((h, t) => append(f(h), t))
  
  // Exercise 3.21: Use flatMap to implement filter
  def filterViaFlatMap[A](l: List[A])(pred: A => Boolean): List[A] =
    flatMap(l)(a => if (pred(a)) List(a) else Nil: List[A])
    
  // Exercise 3.22: Write a function taking two lists 
  // and constructs a list consisting of the sum of each corresponding pair of elements:
  def addCorrespondingElements(as: List[Int], bs: List[Int]): List[Int] = (as, bs) match {
    case (Nil, _) => Nil
    case (_, Nil) => Nil
    case (Cons(ha, ta), Cons(hb, tb)) => Cons(ha + hb, addCorrespondingElements(ta, tb))
  }
  
  // Exercise 3.23: Generalize the preceding function as zipWith
  def zipWith[A, B, C](as: List[A], bs: List[B])(f: (A,B) => C): List[C] = (as, bs) match {
    case (Nil, _) => Nil
    case (_, Nil) => Nil
    case (Cons(ha, ta), Cons(hb, tb)) => Cons(f(ha, hb), zipWith(ta, tb)(f))
  }
  
  // Exercise 3.24: Implement hasSubsequence for checking whether a List contains another List as a sub-sequence:
  @annotation.tailrec
  def startsWith[A](a: List[A], b: List[A]): Boolean = (a,b) match {
    case (_, Nil) => true
    case (Nil, _) => false
    case (Cons(ha, ta), Cons(hb, tb)) => if (ha == hb) startsWith(ta, tb) else false
  }
    
  @annotation.tailrec
  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = sup match {
    case Nil => sub == Nil
    case _ if startsWith(sup, sub) => true
    case Cons(h, t) => hasSubsequence(t, sub)
  }
}
