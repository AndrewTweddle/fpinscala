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
}