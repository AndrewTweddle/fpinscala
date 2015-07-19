import fpinscala.laziness._
import fpinscala.laziness.Stream._

object lazinessWorksheet {
  Empty.toList.isEmpty                            //> res0: Boolean = true
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
}