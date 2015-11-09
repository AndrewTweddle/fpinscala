import fpinscala.state.RNG._

object stateWorksheet {
  // Test nonNegativeInt when new seed is Int.MinValue
  intToNonNegativeInt(Int.MinValue) == Int.MaxValue
                                                  //> res0: Boolean = true
  intToNonNegativeInt(Int.MaxValue) == Int.MaxValue
                                                  //> res1: Boolean = true
  intToNonNegativeInt(-1) == 0                    //> res2: Boolean = true
  
  // Test nonNegativeInt:
  var rng = Simple(0)                             //> rng  : fpinscala.state.RNG.Simple = Simple(0)
  nonNegativeInt(rng)                             //> res3: (Int, fpinscala.state.RNG) = (0,Simple(11))

  // Test RNG.double:
  val (dbl1, rng2) = double(rng)                  //> dbl1  : Double = 0.0
                                                  //| rng2  : fpinscala.state.RNG = Simple(11)
  val (dbl2, rng3) = double(rng2)                 //> dbl2  : Double = 0.0019707889296114445
                                                  //| rng3  : fpinscala.state.RNG = Simple(277363943098)
  val (dbl3, rng4) = double(rng3)                 //> dbl3  : Double = 0.08326200302690268
                                                  //| rng4  : fpinscala.state.RNG = Simple(11718085204285)
  val (dbl4, rng5) = double(rng4)                 //> dbl4  : Double = 0.35328528471291065
                                                  //| rng5  : fpinscala.state.RNG = Simple(49720483695876)
  val (dbl5, rng6) = double(rng5)                 //> dbl5  : Double = 0.7292044963687658
                                                  //| rng6  : fpinscala.state.RNG = Simple(102626409374399)
  val (dbl6, rng7) = double(rng6)                 //> dbl6  : Double = 0.18266122415661812
                                                  //| rng7  : fpinscala.state.RNG = Simple(25707281917278)


  // Test RNG.intDouble, doubleInt, double3:
  val r0 = Simple(123456789)                      //> r0  : fpinscala.state.RNG.Simple = Simple(123456789)
  val ((i1, d1), r1) = intDouble(r0)              //> i1  : Int = 1820451251
                                                  //| d1  : Double = 0.5687516587786376
                                                  //| r1  : fpinscala.state.RNG = Simple(80044680009303)
  val ((d2, i2), r2) = doubleInt(r0)              //> d2  : Double = 0.8477136730216444
                                                  //| i2  : Int = 1221384887
                                                  //| r2  : fpinscala.state.RNG = Simple(80044680009303)
  val ((d3, d4, d5), r3) = double3(r0)            //> d3  : Double = 0.8477136730216444
                                                  //| d4  : Double = 0.5687516587786376
                                                  //| d5  : Double = 0.5685526188462973
                                                  //| r3  : fpinscala.state.RNG = Simple(80016667602198)
  // Shift along one step from r0, and see doubles shift along too
  val (_, r4) = r0.nextInt                        //> r4  : fpinscala.state.RNG = Simple(119305093197820)
  val ((d6, d7, d8), r5) = double3(r4)            //> d6  : Double = 0.5687516587786376
                                                  //| d7  : Double = 0.5685526188462973
                                                  //| d8  : Double = 0.9714055750519037
                                                  //| r5  : fpinscala.state.RNG = Simple(136713180821097)
  // Test ints:
  val (is, r6) = ints(10)(r0)                     //> is  : List[Int] = List(1820451251, 1221384887, 1220957452, 2086077588, -2846
                                                  //| 67191, -736789896, 1878896603, -589698343, -1187374355, -249255936)
                                                  //| r6  : fpinscala.state.RNG = Simple(265139739725951)
  // Test doubleViaMap:
  val (dvm1, mr2) = doubleViaMap(rng)             //> dvm1  : Double = 0.0
                                                  //| mr2  : fpinscala.state.RNG = Simple(11)
  dvm1 == dbl1                                    //> res4: Boolean = true
  mr2 == rng2                                     //> res5: Boolean = true
  
  val (dvm2, mr3) = doubleViaMap(mr2)             //> dvm2  : Double = 0.0019707889296114445
                                                  //| mr3  : fpinscala.state.RNG = Simple(277363943098)
  dvm2 == dbl2                                    //> res6: Boolean = true
  mr3 == rng3                                     //> res7: Boolean = true
  
  // Test map2:
  val die: Rand[Int] = map(int)(intToNonNegativeInt(_) % 6 + 1)
                                                  //> die  : fpinscala.state.RNG.Rand[Int] = <function1>
  val sumOf2Dice = map2(die, die)(_ + _)          //> sumOf2Dice  : fpinscala.state.RNG.Rand[Int] = <function1>
  val (s1, r7) = sumOf2Dice(r0)                   //> s1  : Int = 12
                                                  //| r7  : fpinscala.state.RNG = Simple(80044680009303)
  val (s2, r8) = sumOf2Dice(r7)                   //> s2  : Int = 6
                                                  //| r8  : fpinscala.state.RNG = Simple(136713180821097)
  val (s3, r9) = sumOf2Dice(r8)                   //> s3  : Int = 11
                                                  //| r9  : fpinscala.state.RNG = Simple(233188714107339)
  val (s4, r10) = sumOf2Dice(r9)                  //> s4  : Int = 7
                                                  //| r10  : fpinscala.state.RNG = Simple(242828506128637)
                                                  
  // Test intsViaSequence:
  val (is2, r6_2) = intsViaSequence(10)(r0)       //> is2  : List[Int] = List(1820451251, 1221384887, 1220957452, 2086077588, -28
                                                  //| 4667191, -736789896, 1878896603, -589698343, -1187374355, -249255936)
                                                  //| r6_2  : fpinscala.state.RNG = Simple(265139739725951)
  is2 == is                                       //> res8: Boolean = true
  r6_2 == r6                                      //> res9: Boolean = true
  
  // after looking at the model answer and improving my code...
  val (is3, r6_3) = intsViaSequenceAsRand(10)(r0) //> is3  : List[Int] = List(1820451251, 1221384887, 1220957452, 2086077588, -28
                                                  //| 4667191, -736789896, 1878896603, -589698343, -1187374355, -249255936)
                                                  //| r6_3  : fpinscala.state.RNG = Simple(265139739725951)
  is3 == is2                                      //> res10: Boolean = true
  r6_3 == r6_2                                    //> res11: Boolean = true
  
  // Test flatMap and nonNegativeLessThan:
  val throwManyDice = sequence(List.fill(60)(nonNegativeLessThan(6)))
                                                  //> throwManyDice  : fpinscala.state.RNG.Rand[List[Int]] = <function1>
  val throws = throwManyDice(r0)                  //> throws  : (List[Int], fpinscala.state.RNG) = (List(5, 5, 4, 0, 4, 5, 5, 0, 
                                                  //| 4, 5, 2, 3, 4, 1, 0, 0, 3, 2, 1, 0, 2, 3, 1, 5, 1, 4, 3, 3, 0, 4, 1, 2, 5, 
                                                  //| 1, 0, 5, 0, 3, 3, 1, 1, 0, 1, 3, 2, 5, 5, 0, 1, 3, 3, 1, 0, 5, 3, 4, 2, 2, 
                                                  //| 1, 4),Simple(44147254380609))
  val histogram = throws._1.groupBy(_ + 1).mapValues(_.length)
                                                  //> histogram  : scala.collection.immutable.Map[Int,Int] = Map(5 -> 8, 1 -> 11,
                                                  //|  6 -> 11, 2 -> 12, 3 -> 7, 4 -> 11)
  
  // Test mapUsingFlatMap, map2UsingFlatMap:
  val (dvm3, mr4) = doubleViaMapUsingFlatMap(mr2) //> dvm3  : Double = 0.0019707889296114445
                                                  //| mr4  : fpinscala.state.RNG = Simple(277363943098)
  dvm3 == dvm2                                    //> res12: Boolean = true

  // Test map2UsingFlatMap:
  val die2: Rand[Int] = mapUsingFlatMap(int)(intToNonNegativeInt(_) % 6 + 1)
                                                  //> die2  : fpinscala.state.RNG.Rand[Int] = <function1>
  val sumOf2Dice2 = map2UsingFlatMap(die2, die2)(_ + _)
                                                  //> sumOf2Dice2  : fpinscala.state.RNG.Rand[Int] = <function1>
  val (s1_2, r7_2) = sumOf2Dice(r0)               //> s1_2  : Int = 12
                                                  //| r7_2  : fpinscala.state.RNG = Simple(80044680009303)
  val (s2_2, r8_2) = sumOf2Dice(r7)               //> s2_2  : Int = 6
                                                  //| r8_2  : fpinscala.state.RNG = Simple(136713180821097)
  val (s3_2, r9_2) = sumOf2Dice(r8)               //> s3_2  : Int = 11
                                                  //| r9_2  : fpinscala.state.RNG = Simple(233188714107339)
  val (s4_2, r10_2) = sumOf2Dice(r9)              //> s4_2  : Int = 7
                                                  //| r10_2  : fpinscala.state.RNG = Simple(242828506128637)
  
  s1_2 == s1                                      //> res13: Boolean = true
  s2_2 == s2                                      //> res14: Boolean = true
  s3_2 == s3                                      //> res15: Boolean = true
  s4_2 == s4                                      //> res16: Boolean = true
}