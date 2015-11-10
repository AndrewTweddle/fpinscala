import fpinscala.state.State._
import fpinscala.state.{Machine, Input, Coin, Turn}

object StateMachineWorksheet {
  val initialMachine = Machine(locked = true, candies = 5, coins = 10)
                                                  //> initialMachine  : fpinscala.state.Machine = Machine(true,5,10)
  val inputs = List(Coin, Turn, Coin, Turn, Coin, Turn, Coin, Turn)
                                                  //> inputs  : List[Product with Serializable with fpinscala.state.Input] = List(
                                                  //| Coin, Turn, Coin, Turn, Coin, Turn, Coin, Turn)
  val stateMachine = simulateMachine(inputs)      //> stateMachine  : fpinscala.state.State[fpinscala.state.Machine,(Int, Int)] = 
                                                  //| State(<function1>)
  val ((_, _), finalMachine) = stateMachine.run(initialMachine)
                                                  //> finalMachine  : fpinscala.state.Machine = Machine(true,1,14)
  finalMachine.candies  == 1                      //> res0: Boolean = true
  finalMachine.coins == 14                        //> res1: Boolean = true
  
  val expectedCoins = 14                          //> expectedCoins  : Int = 14
  val expectedCandies = 1                         //> expectedCandies  : Int = 1
  val expectedMachine = Machine(true, expectedCandies, expectedCoins)
                                                  //> expectedMachine  : fpinscala.state.Machine = Machine(true,1,14)
  val state1 = simulateMachineNaively(inputs)     //> state1  : fpinscala.state.State[fpinscala.state.Machine,(Int, Int)] = State(
                                                  //| <function1>)
  val state2 = simulateMachineViaModifiers(inputs)//> state2  : fpinscala.state.State[fpinscala.state.Machine,(Int, Int)] = State(
                                                  //| <function1>)
  val state3 = simulateMachineViaModify(inputs)   //> state3  : fpinscala.state.State[fpinscala.state.Machine,(Int, Int)] = State(
                                                  //| <function1>)
  
  val actualStates = List(state1, state2, state3) //> actualStates  : List[fpinscala.state.State[fpinscala.state.Machine,(Int, Int
                                                  //| )]] = List(State(<function1>), State(<function1>), State(<function1>))
                                                  
  actualStates.map(
    s => {
      val ((coins, candies), finalMachine) = s.run(initialMachine)
      coins == expectedCoins && candies == expectedCandies && finalMachine == expectedMachine
    }
  )                                               //> res2: List[Boolean] = List(true, true, true)
}