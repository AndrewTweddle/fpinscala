import fpinscala.laziness._
import fpinscala.laziness.Stream._

object lazinessWorksheet {
  val empty: Stream[Int] = Empty                  //> empty  : fpinscala.laziness.Stream[Int] = Empty
  empty.toList.isEmpty                            //> res0: Boolean = true
  val streamFrom1To3 = Cons[Int](() => 1, () => Cons(() => 2, () => Cons(() => 3, () => Empty)))
                                                  //> streamFrom1To3  : fpinscala.laziness.Cons[Int] = Cons(<function0>,<function0
                                                  //| >)
  streamFrom1To3.toList == 1 :: 2 :: 3 :: Nil     //> res1: Boolean = true
  
  streamFrom1To3.take(0)                          //> res2: fpinscala.laziness.Stream[Int] = Empty
  streamFrom1To3.take(1)                          //> res3: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  streamFrom1To3.take(2)                          //> res4: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  streamFrom1To3.take(3)                          //> res5: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  streamFrom1To3.take(4)                          //> res6: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  streamFrom1To3.take(0).toList == List.empty     //> res7: Boolean = true
  streamFrom1To3.take(1).toList == List(1)        //> res8: Boolean = true
  streamFrom1To3.take(2).toList == List(1, 2)     //> res9: Boolean = true
  streamFrom1To3.take(3).toList == List(1, 2, 3)  //> res10: Boolean = true
  streamFrom1To3.take(4).toList == List(1, 2, 3)  //> res11: Boolean = true
  
  streamFrom1To3.drop(0)                          //> res12: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  streamFrom1To3.drop(1)                          //> res13: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  streamFrom1To3.drop(2)                          //> res14: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  streamFrom1To3.drop(3)                          //> res15: fpinscala.laziness.Stream[Int] = Empty
  streamFrom1To3.drop(4)                          //> res16: fpinscala.laziness.Stream[Int] = Empty
  try {
    streamFrom1To3.drop(-1)
    false
 } catch { case _: Exception => true}             //> res17: Boolean = true
  streamFrom1To3.drop(0).toList == List(1, 2, 3)  //> res18: Boolean = true
  streamFrom1To3.drop(1).toList == List(2, 3)     //> res19: Boolean = true
  streamFrom1To3.drop(2).toList == List(3)        //> res20: Boolean = true
  streamFrom1To3.drop(3).toList == List.empty     //> res21: Boolean = true
  streamFrom1To3.drop(4).toList == List.empty     //> res22: Boolean = true
  
  def isOdd(i: Int) = {
    println(s"Checking if $i is odd")
    i % 2 != 0
  }                                               //> isOdd: (i: Int)Boolean
  empty.takeWhile(isOdd).toList.isEmpty           //> res23: Boolean = true
  streamFrom1To3.takeWhile(isOdd).toList == List(1)
                                                  //> Checking if 1 is odd
                                                  //| Checking if 2 is odd
                                                  //| res24: Boolean = true
  
  def isLessThanFour(i: Int) = {
    println(s"Checking if $i is less than 4")
    i < 4
  }                                               //> isLessThanFour: (i: Int)Boolean
  streamFrom1To3.takeWhile(isLessThanFour).toList == List(1, 2, 3)
                                                  //> Checking if 1 is less than 4
                                                  //| Checking if 2 is less than 4
                                                  //| Checking if 3 is less than 4
                                                  //| res25: Boolean = true
  
  Stream(1, 2, 3, 4).takeWhile(isLessThanFour).toList == List(1, 2, 3)
                                                  //> Checking if 1 is less than 4
                                                  //| Checking if 2 is less than 4
                                                  //| Checking if 3 is less than 4
                                                  //| Checking if 4 is less than 4
                                                  //| res26: Boolean = true
  !streamFrom1To3.forAll(isOdd)                   //> Checking if 1 is odd
                                                  //| Checking if 2 is odd
                                                  //| res27: Boolean = true
  streamFrom1To3.forAll(isLessThanFour)           //> Checking if 1 is less than 4
                                                  //| Checking if 2 is less than 4
                                                  //| Checking if 3 is less than 4
                                                  //| res28: Boolean = true
  Stream(1, 2, 3, 4).forAll(isLessThanFour)       //> Checking if 1 is less than 4
                                                  //| Checking if 2 is less than 4
                                                  //| Checking if 3 is less than 4
                                                  //| Checking if 4 is less than 4
                                                  //| res29: Boolean = false
  empty.takeWhileUsingFoldRight(isOdd).toList.isEmpty
                                                  //> res30: Boolean = true
  streamFrom1To3.takeWhileUsingFoldRight(isOdd).toList == List(1)
                                                  //> Checking if 1 is odd
                                                  //| Checking if 2 is odd
                                                  //| res31: Boolean = true
  
  streamFrom1To3.takeWhileUsingFoldRight(isLessThanFour).toList == List(1, 2, 3)
                                                  //> Checking if 1 is less than 4
                                                  //| Checking if 2 is less than 4
                                                  //| Checking if 3 is less than 4
                                                  //| res32: Boolean = true
  
  Stream(1, 2, 3, 4).takeWhileUsingFoldRight(isLessThanFour).toList == List(1, 2, 3)
                                                  //> Checking if 1 is less than 4
                                                  //| Checking if 2 is less than 4
                                                  //| Checking if 3 is less than 4
                                                  //| Checking if 4 is less than 4
                                                  //| res33: Boolean = true
                                                  
}