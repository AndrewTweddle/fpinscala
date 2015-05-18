package fpinscala.datastructures

sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]


object Tree {
  // Exercise 3.25:
  def size[A](tree: Tree[A]): Int = tree match {
    case Leaf(_) => 1
    case Branch(l, r) => size(l) + size(r) + 1
  }
  
  // Exercise 3.26: 
  def maximum(tree: Tree[Int]): Int = tree match {
    case Leaf(a) => a
    case Branch(l, r) => Math.max(maximum(l), maximum(r))
  }
  
  // Exercise 3.27:
  def depth[A](tree: Tree[A]): Int = tree match {
    case Leaf(_) => 1
    case Branch(l, r) => 1 + Math.max(depth(l), depth(r))
  }
  
  // Exercise 3.28:
  def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = tree match {
    case Leaf(a) => Leaf(f(a))
    case Branch(l, r) => Branch(map(l)(f), map(r)(f))
  }
}