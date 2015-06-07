import fpinscala.errorhandling._
import fpinscala.errorhandling.Option._

object errorHandlingWorksheet {
  Some(5).map(_+3)                                //> res0: fpinscala.errorhandling.Option[Int] = Some(8)
  (None: Option[Int]).map(_+3)                    //> res1: fpinscala.errorhandling.Option[Int] = None
  (None: Option[Int]).map((a: Int) => None)       //> res2: fpinscala.errorhandling.Option[fpinscala.errorhandling.None.type] = No
                                                  //| ne
                                                  
  Some(5).getOrElse(-1)                           //> res3: Int = 5
  (None: Option[Int]).getOrElse(-1)               //> res4: Int = -1
  
  def g(a: Int): Option[Int] = if (a % 2 == 0) Some(a) else None
                                                  //> g: (a: Int)fpinscala.errorhandling.Option[Int]
  
  (None: Option[Int]).flatMap(g)                  //> res5: fpinscala.errorhandling.Option[Int] = None
  Some(4).flatMap(g)                              //> res6: fpinscala.errorhandling.Option[Int] = Some(4)
  Some(5).flatMap(g)                              //> res7: fpinscala.errorhandling.Option[Int] = None
  
  Some(5).orElse(Some(-1))                        //> res8: fpinscala.errorhandling.Option[Int] = Some(5)
  Some(5).orElse(None)                            //> res9: fpinscala.errorhandling.Option[Int] = Some(5)
  (None: Option[Int]).orElse(Some(-1))            //> res10: fpinscala.errorhandling.Option[Int] = Some(-1)
  (None: Option[Int]).orElse(None)                //> res11: fpinscala.errorhandling.Option[Int] = None
  
  def isEven(i: Int) = i % 2 == 0                 //> isEven: (i: Int)Boolean
  Some(4).filter(isEven)                          //> res12: fpinscala.errorhandling.Option[Int] = Some(4)
  Some(3).filter(isEven)                          //> res13: fpinscala.errorhandling.Option[Int] = None
  None.filter(isEven)                             //> res14: fpinscala.errorhandling.Option[Nothing] = None
  
  val xs = List(1.0,3.0,8.0)                      //> xs  : List[Double] = List(1.0, 3.0, 8.0)
  mean(xs)                                        //> res15: fpinscala.errorhandling.Option[Double] = Some(4.0)
  mean(Nil: List[Double])                         //> res16: fpinscala.errorhandling.Option[Double] = None
  
  variance1(xs)                                   //> res17: fpinscala.errorhandling.Option[Double] = Some(8.666666666666668)
  variance1(Nil: List[Double])                    //> res18: fpinscala.errorhandling.Option[Double] = None
  variance2(xs)                                   //> res19: fpinscala.errorhandling.Option[Double] = Some(8.666666666666666)
  variance2(Nil: List[Double])                    //> res20: fpinscala.errorhandling.Option[Double] = None
  variance(xs)                                    //> res21: fpinscala.errorhandling.Option[Double] = Some(8.666666666666666)
  variance(Nil: List[Double])                     //> res22: fpinscala.errorhandling.Option[Double] = None
  
  val a = 4                                       //> a  : Int = 4
  val b = 10                                      //> b  : Int = 10
  val none: Option[Int] = None                    //> none  : fpinscala.errorhandling.Option[Int] = None
  def f(a: Int, b: Int): Int = a * a - b          //> f: (a: Int, b: Int)Int
  map2(Some(a), Some(b))(f)                       //> res23: fpinscala.errorhandling.Option[Int] = Some(6)
  map2(none, Some(b))(f)                          //> res24: fpinscala.errorhandling.Option[Int] = None
  map2(Some(a), none)(f)                          //> res25: fpinscala.errorhandling.Option[Int] = None
  map2(none, none)(f)                             //> res26: fpinscala.errorhandling.Option[Int] = None
  
  sequence(List(Some(1),Some(2),Some(3)))         //> res27: fpinscala.errorhandling.Option[List[Int]] = Some(List(1, 2, 3))
  sequence(List(Some(1),None,Some(3)))            //> res28: fpinscala.errorhandling.Option[List[Int]] = None
  sequence(List[Option[Int]](None,None,None))     //> res29: fpinscala.errorhandling.Option[List[Int]] = None
  sequence(Nil: List[Option[Int]])                //> res30: fpinscala.errorhandling.Option[List[Int]] = Some(List())
  
  def isOdd(i: Int): Option[Int] = if (i % 2 == 0) None else Some(i)
                                                  //> isOdd: (i: Int)fpinscala.errorhandling.Option[Int]
  val oddsAndEvens = List(1,2,3,4)                //> oddsAndEvens  : List[Int] = List(1, 2, 3, 4)
  val odds = List(1,3,5)                          //> odds  : List[Int] = List(1, 3, 5)
  val evens = List(2, 4, 6)                       //> evens  : List[Int] = List(2, 4, 6)
  val neitherOddsNorEvens = Nil: List[Int]        //> neitherOddsNorEvens  : List[Int] = List()
  traverse(oddsAndEvens)(isOdd)                   //> res31: fpinscala.errorhandling.Option[List[Int]] = None
  traverse(odds)(isOdd)                           //> res32: fpinscala.errorhandling.Option[List[Int]] = Some(List(1, 3, 5))
  traverse(evens)(isOdd)                          //> res33: fpinscala.errorhandling.Option[List[Int]] = None
  traverse(neitherOddsNorEvens)(isOdd)            //> res34: fpinscala.errorhandling.Option[List[Int]] = Some(List())
  
  sequenceViaTraverse(List(Some(1),Some(2),Some(3)))
                                                  //> res35: fpinscala.errorhandling.Option[List[Int]] = Some(List(1, 2, 3))
  sequenceViaTraverse(List(Some(1),None,Some(3))) //> res36: fpinscala.errorhandling.Option[List[Int]] = None
  sequenceViaTraverse(List[Option[Int]](None,None,None))
                                                  //> res37: fpinscala.errorhandling.Option[List[Int]] = None
  sequenceViaTraverse(Nil: List[Option[Int]])     //> res38: fpinscala.errorhandling.Option[List[Int]] = Some(List())
}