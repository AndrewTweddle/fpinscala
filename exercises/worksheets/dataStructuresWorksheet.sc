import fpinscala.datastructures._
import fpinscala.datastructures.List._
import fpinscala.datastructures.Tree.{map => mapTree, _}

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
  
  // Exercise 3:22:
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
  
  // Exercise 3.23:
  zipWith(Nil: List[Int], Nil: List[Int])(_ + _)  //> res35: fpinscala.datastructures.List[Int] = Nil
  zipWith(List(1,2,3), Nil: List[Int])(_ + _)     //> res36: fpinscala.datastructures.List[Int] = Nil
  zipWith(Nil: List[Int], List(1,2,3))(_ + _)     //> res37: fpinscala.datastructures.List[Int] = Nil
  zipWith(List(1,2,3), List(0, -2, -4))(_ + _)    //> res38: fpinscala.datastructures.List[Int] = Cons(1,Cons(0,Cons(-1,Nil)))
  zipWith(List(1,2,3,4), List(-1, -2, -3))(_ + _) //> res39: fpinscala.datastructures.List[Int] = Cons(0,Cons(0,Cons(0,Nil)))
  zipWith(List(1,2,3), List(-1, -2, -3, -4))(_ + _)
                                                  //> res40: fpinscala.datastructures.List[Int] = Cons(0,Cons(0,Cons(0,Nil)))
  zipWith(List(1), List(2))(_ + _)                //> res41: fpinscala.datastructures.List[Int] = Cons(3,Nil)
  
  // Exercise 3.24: expect the following to all return true...
  !startsWith(List(1,2,3), List(2,3))             //> res42: Boolean = true
  !startsWith(List(1,2,3), List(1,2,3,4))         //> res43: Boolean = true
  startsWith(List(1,2,3), List(1,2,3))            //> res44: Boolean = true
  startsWith(List(1,2,3), Nil)                    //> res45: Boolean = true
  startsWith(Nil, Nil)                            //> res46: Boolean = true
  !startsWith(Nil, List(1))                       //> res47: Boolean = true
  startsWith(List(1,2,3), List(1))                //> res48: Boolean = true
  
  hasSubsequence(List(1,2,3), List(2,3))          //> res49: Boolean = true
  hasSubsequence(List(1,2,3,4), List(2,3))        //> res50: Boolean = true
  hasSubsequence(List(1,2,3), List(1,2))          //> res51: Boolean = true
  hasSubsequence(List(1,2,3), List(1,2,3))        //> res52: Boolean = true
  hasSubsequence(List(1,2,3), Nil)                //> res53: Boolean = true
  hasSubsequence(Nil, Nil)                        //> res54: Boolean = true
  !hasSubsequence(Nil, List(1))                   //> res55: Boolean = true
  hasSubsequence(List(1,2,3), List(1))            //> res56: Boolean = true
  !hasSubsequence(List(1,2,3),List(1,4))          //> res57: Boolean = true
  !hasSubsequence(List(1,2,3), List(2,4))         //> res58: Boolean = true
  
  // Data for the tree exercises:
  val twig = Leaf(1)                              //> twig  : fpinscala.datastructures.Leaf[Int] = Leaf(1)
  val shallowTree = Branch(Leaf(1), Branch(Leaf(2), Leaf(3)))
                                                  //> shallowTree  : fpinscala.datastructures.Branch[Int] = Branch(Leaf(1),Branch
                                                  //| (Leaf(2),Leaf(3)))
  val deepTree =
    Branch(  // T
      Leaf("TL"),
      Branch(  // TR
        Branch(
          Leaf("TRL"),
        	Branch(  // TRR
        	  Branch(  // TRRL
        			Leaf("TRRLL"),
        			Leaf("TRRLR")
        	  ),
        	  Leaf("TRRR")
        	)
        ),
        Leaf("TRR")
      )
    )                                             //> deepTree  : fpinscala.datastructures.Branch[String] = Branch(Leaf(TL),Branc
                                                  //| h(Branch(Leaf(TRL),Branch(Branch(Leaf(TRRLL),Leaf(TRRLR)),Leaf(TRRR))),Leaf
                                                  //| (TRR)))
  // Exercise 3.25: expect the following to all return true...
  size(twig) == 1                                 //> res59: Boolean = true
  size(Branch(Leaf(1), Leaf(2))) == 3             //> res60: Boolean = true
  size(shallowTree) == 5                          //> res61: Boolean = true
  
  // Exercise 3.26: expect the following to all return true
  maximum(twig) == 1                              //> res62: Boolean = true
  maximum(Branch(Leaf(1), Leaf(2))) == 2          //> res63: Boolean = true
  maximum(shallowTree) == 3                       //> res64: Boolean = true
  
  // vary order, so that the max is not also the last
  maximum(
    Branch(
      Leaf(3),
      Branch(
        Leaf(1),
        Leaf(2)
      )
    )
  ) == 3                                          //> res65: Boolean = true
  
  maximum(
    Branch(
      Leaf(1),
      Branch(
        Leaf(3),
        Leaf(2)
      )
    )
  ) == 3                                          //> res66: Boolean = true
  
  // Do exercise 3.27: expect the following to all return true
  depth(twig) == 1                                //> res67: Boolean = true
  depth(shallowTree) == 3                         //> res68: Boolean = true
  depth[String](deepTree) == 6                    //> res69: Boolean = true
  
  // Exercise 3.28:
  mapTree(twig)(_ + 0.5)                          //> res70: fpinscala.datastructures.Tree[Double] = Leaf(1.5)
  mapTree(shallowTree)(_ + 0.5)                   //> res71: fpinscala.datastructures.Tree[Double] = Branch(Leaf(1.5),Branch(Leaf
                                                  //| (2.5),Leaf(3.5)))
  mapTree(deepTree)(x => s"_${x}_")               //> res72: fpinscala.datastructures.Tree[String] = Branch(Leaf(_TL_),Branch(Bra
                                                  //| nch(Leaf(_TRL_),Branch(Branch(Leaf(_TRRLL_),Leaf(_TRRLR_)),Leaf(_TRRR_))),L
                                                  //| eaf(_TRR_)))
                                                  
  // Exercise 3.29:
  sizeViaFold(twig) == 1                          //> res73: Boolean = true
  sizeViaFold(Branch(Leaf(1), Leaf(2))) == 3      //> res74: Boolean = true
  sizeViaFold(shallowTree) == 5                   //> res75: Boolean = true
  maximumViaFold(twig) == 1                       //> res76: Boolean = true
  maximumViaFold(Branch(Leaf(1), Leaf(2))) == 2   //> res77: Boolean = true
  maximumViaFold(shallowTree) == 3                //> res78: Boolean = true
  depthViaFold(twig) == 1                         //> res79: Boolean = true
  depthViaFold(shallowTree) == 3                  //> res80: Boolean = true
  depthViaFold(deepTree) == 6                     //> res81: Boolean = true
  mapViaFold(twig)(_ + 0.5)                       //> res82: fpinscala.datastructures.Tree[Double] = Leaf(1.5)
  mapViaFold(shallowTree)(_ + 0.5)                //> res83: fpinscala.datastructures.Tree[Double] = Branch(Leaf(1.5),Branch(Leaf
                                                  //| (2.5),Leaf(3.5)))
  mapViaFold(deepTree)(x => s"_${x}_")            //> res84: fpinscala.datastructures.Tree[String] = Branch(Leaf(_TL_),Branch(Bra
                                                  //| nch(Leaf(_TRL_),Branch(Branch(Leaf(_TRRLL_),Leaf(_TRRLR_)),Leaf(_TRRR_))),L
                                                  //| eaf(_TRR_)))
}