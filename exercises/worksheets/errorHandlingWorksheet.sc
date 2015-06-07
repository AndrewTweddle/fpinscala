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
}