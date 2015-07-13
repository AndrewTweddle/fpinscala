import fpinscala.errorhandling._
import fpinscala.errorhandling.Either._

object errorHandlingWithEitherWorksheet {
	Right(5).map(_+3) == Right(8)             //> res0: Boolean = true

  val left: Either[String, Int] = Left("error")   //> left  : fpinscala.errorhandling.Either[String,Int] = Left(error)
  left.map(_ + 1) == left                         //> res1: Boolean = true
  left.flatMap[String, Int]{ x => Right(x + 1) } == left
                                                  //> res2: Boolean = true
  left.orElse(Right(3)) == Right(3)               //> res3: Boolean = true
	left.orElse(Left("orError")) == Left("orError")
                                                  //> res4: Boolean = true
  val right = Right(10)                           //> right  : fpinscala.errorhandling.Right[Int] = Right(10)
  right.orElse(Right(3)) == right                 //> res5: Boolean = true
	right.orElse(Left("orError")) == right    //> res6: Boolean = true
  right.map(_ + 1) == Right(11)                   //> res7: Boolean = true
  right.flatMap(x => Right(x + 1)) == Right(11)   //> res8: Boolean = true
  right.flatMap(_ => Left("newError")) == Left("newError")
                                                  //> res9: Boolean = true
  val a = 4                                       //> a  : Int = 4
  val b = 10                                      //> b  : Int = 10
  def f(a: Int, b: Int): Int = a * a - b          //> f: (a: Int, b: Int)Int
  Right(a).map2(Right(b))(f) == Right(6)          //> res10: Boolean = true
  left.map2(Right(b))(f) == left                  //> res11: Boolean = true
  right.map2(left)(f) == left                     //> res12: Boolean = true
  left.map2(left)(f) == left                      //> res13: Boolean = true
}