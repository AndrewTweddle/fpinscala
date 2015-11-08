import fpinscala.state.RNG._

object stateWorksheet {
  // Test nonNegativeInt when new seed is Int.MinValue
  intToNonNegativeInt(Int.MinValue) == Int.MaxValue
                                                  //> res0: Boolean = true
  intToNonNegativeInt(Int.MaxValue) == Int.MaxValue
                                                  //> res1: Boolean = true
  intToNonNegativeInt(-1) == 0                    //> res2: Boolean = true
  
  // Test nonNegativeInt:
  val rng = Simple(0)                             //> rng  : fpinscala.state.RNG.Simple = Simple(0)
  nonNegativeInt(rng)                             //> res3: (Int, fpinscala.state.RNG) = (0,Simple(11))
  
}