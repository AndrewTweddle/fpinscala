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
  var (dbl1, rng2) = double(rng)                  //> dbl1  : Double = 0.0
                                                  //| rng2  : fpinscala.state.RNG = Simple(11)
  var (dbl2, rng3) = double(rng2)                 //> dbl2  : Double = 0.0019707889296114445
                                                  //| rng3  : fpinscala.state.RNG = Simple(277363943098)
  var (dbl3, rng4) = double(rng3)                 //> dbl3  : Double = 0.08326200302690268
                                                  //| rng4  : fpinscala.state.RNG = Simple(11718085204285)
  var (dbl4, rng5) = double(rng4)                 //> dbl4  : Double = 0.35328528471291065
                                                  //| rng5  : fpinscala.state.RNG = Simple(49720483695876)
  var (dbl5, rng6) = double(rng5)                 //> dbl5  : Double = 0.7292044963687658
                                                  //| rng6  : fpinscala.state.RNG = Simple(102626409374399)
  var (dbl6, rng7) = double(rng6)                 //> dbl6  : Double = 0.18266122415661812
                                                  //| rng7  : fpinscala.state.RNG = Simple(25707281917278)
                                                  
}