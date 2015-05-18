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
  append2(List(1,2,3), List(4,5,6))               //> res13: fpinscala.datastructures.List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Cons
                                                  //| (5,Cons(6,Nil))))))
  concatListOfLists(List(List(1,2,3), List(4,5,6), Nil: List[Int], List(7,8,9)))
                                                  //> res14: fpinscala.datastructures.List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Cons
                                                  //| (5,Cons(6,Cons(7,Cons(8,Cons(9,Nil)))))))))
  addOne(List(1,2,3))                             //> res15: fpinscala.datastructures.List[Int] = Cons(2,Cons(3,Cons(4,Nil)))
  addOne(Nil : List[Int])                         //> res16: fpinscala.datastructures.List[Int] = Nil
  doublesToStrings(List(1.0, 2.5, Math.PI))       //> res17: fpinscala.datastructures.List[String] = Cons(1.0,Cons(2.5,Cons(3.1415
                                                  //| 92653589793,Nil)))
  doublesToStrings(Nil: List[Double])             //> res18: fpinscala.datastructures.List[String] = Nil
  map(List(1,2,3))(_ + 1)                         //> res19: fpinscala.datastructures.List[Int] = Cons(2,Cons(3,Cons(4,Nil)))
  map(Nil: List[Int])(_ + 1)                      //> res20: fpinscala.datastructures.List[Int] = Nil
  filter(List(1,2,3,4,5))(_ % 2 == 0)             //> res21: fpinscala.datastructures.List[Int] = Cons(2,Cons(4,Nil))
  filter(Nil: List[Int])(_ % 2 == 0)              //> res22: fpinscala.datastructures.List[Int] = Nil
  flatMap(List(1,2,3))(a => List(a, 3 + a, 6 + a))//> res23: fpinscala.datastructures.List[Int] = Cons(1,Cons(4,Cons(7,Cons(2,Con
                                                  //| s(5,Cons(8,Cons(3,Cons(6,Cons(9,Nil)))))))))
  flatMap2(List(1,2,3))(a => List(a, 3 + a, 6 + a))
                                                  //> res24: fpinscala.datastructures.List[Int] = Cons(1,Cons(4,Cons(7,Cons(2,Con
                                                  //| s(5,Cons(8,Cons(3,Cons(6,Cons(9,Nil)))))))))
  flatMap(List(1,2,3))(i => List(i,i))            //> res25: fpinscala.datastructures.List[Int] = Cons(1,Cons(1,Cons(2,Cons(2,Con
                                                  //| s(3,Cons(3,Nil))))))
  filterViaFlatMap(List(1,2,3,4,5))(_ % 2 == 0)   //> res26: fpinscala.datastructures.List[Int] = Cons(2,Cons(4,Nil))
  filterViaFlatMap(Nil: List[Int])(_ % 2 == 0)    //> res27: fpinscala.datastructures.List[Int] = Nil
  addCorrespondingElements(Nil, Nil)              //> res28: fpinscala.datastructures.List[Int] = Nil
  addCorrespondingElements(List(1,2,3), Nil)      //> res29: fpinscala.datastructures.List[Int] = Nil
  addCorrespondingElements(Nil, List(1,2,3))      //> res30: fpinscala.datastructures.List[Int] = Nil
  addCorrespondingElements(List(1,2,3), List(0, -2, -4))
                                                  //> res31: fpinscala.datastructures.List[Int] = Cons(1,Cons(0,Cons(-1,Nil)))
  addCorrespondingElements(List(1,2,3,4), List(-1, -2, -3))
                                                  //> res32: fpinscala.datastructures.List[Int] = Cons(0,Cons(0,Cons(0,Nil)))
  addCorrespondingElements(List(1,2,3), List(-1, -2, -3, -4))
                                                  //> res33: fpinscala.datastructures.List[Int] = Cons(0,Cons(0,Cons(0,Nil)))
  addCorrespondingElements(List(1), List(2))      //> res34: fpinscala.datastructures.List[Int] = Cons(3,Nil)
}