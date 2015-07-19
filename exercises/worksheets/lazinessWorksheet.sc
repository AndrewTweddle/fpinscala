import fpinscala.laziness._
import fpinscala.laziness.Stream._

object lazinessWorksheet {
  Empty.toList.isEmpty                            //> res0: Boolean = true
  val streamFrom1To3 = Cons[Int](() => 1, () => Cons(() => 2, () => Cons(() => 3, () => Empty)))
                                                  //> streamFrom1To3  : fpinscala.laziness.Cons[Int] = Cons(<function0>,<function0
                                                  //| >)
  streamFrom1To3.toList == 1 :: 2 :: 3 :: Nil     //> res1: Boolean = true
}