import fpinscala.datastructures._
import fpinscala.datastructures.List._

object dataStructuresWorksheet {
  length(List(1,2,3))                             //> res0: Int = 3
  sumWithFoldLeft(List(1,2,3))                    //> res1: Int = 6
  productWithFoldLeft(List(1.0, 4.0, 2.5))        //> res2: Double = 10.0
  reverse(List(1,2,3))                            //> res3: fpinscala.datastructures.List[Int] = Cons(3,Cons(2,Cons(1,Nil)))
  foldLeft(List(1,2,3), 0)(_ - _)                 //> res4: Int = -6
  foldLeft2(List(1,2,3), 0)(_ - _)                //> res5: Int = -6
  foldRight(List(1,2,3), 0)(_ - _)                //> res6: Int = 2
  foldRight2(List(1,2,3), 0)(_ - _)               //> res7: Int = 2
  foldRight(List(1,2,3), Nil:List[Int])((a, l) => Cons(a, l))
                                                  //> res8: fpinscala.datastructures.List[Int] = Cons(1,Cons(2,Cons(3,Nil)))
  foldRight2(List(1,2,3), Nil:List[Int])((a, l) => Cons(a, l))
                                                  //> res9: fpinscala.datastructures.List[Int] = Cons(1,Cons(2,Cons(3,Nil)))
  foldLeft(List(1,2,3), Nil:List[Int])((l, a) => Cons(a, l))
                                                  //> res10: fpinscala.datastructures.List[Int] = Cons(3,Cons(2,Cons(1,Nil)))
  reverse(foldLeft(List(1,2,3), Nil:List[Int])((l, a) => Cons(a, l)))
                                                  //> res11: fpinscala.datastructures.List[Int] = Cons(1,Cons(2,Cons(3,Nil)))
  foldLeft2(List(1,2,3), Nil:List[Int])((l, a) => Cons(a, l))
                                                  //> res12: fpinscala.datastructures.List[Int] = Cons(3,Cons(2,Cons(1,Nil)))
}