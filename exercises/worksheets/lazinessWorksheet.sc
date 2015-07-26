import fpinscala.laziness._
import fpinscala.laziness.Stream._

object lazinessWorksheet {
  val emptyStream: Stream[Int] = Empty            //> emptyStream  : fpinscala.laziness.Stream[Int] = Empty
  emptyStream.toList.isEmpty                      //> res0: Boolean = true
  val streamFrom1To3 = Cons[Int](() => 1, () => Cons(() => 2, () => Cons(() => 3, () => Empty)))
                                                  //> streamFrom1To3  : fpinscala.laziness.Cons[Int] = Cons(<function0>,<function0
                                                  //| >)
  streamFrom1To3.toList == 1 :: 2 :: 3 :: Nil     //> res1: Boolean = true
  
  streamFrom1To3.take(0)                          //> res2: fpinscala.laziness.Stream[Int] = Empty
  streamFrom1To3.take(1)                          //> res3: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  streamFrom1To3.take(2)                          //> res4: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  streamFrom1To3.take(3)                          //> res5: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  streamFrom1To3.take(4)                          //> res6: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  streamFrom1To3.take(0).toList == List.empty     //> res7: Boolean = true
  streamFrom1To3.take(1).toList == List(1)        //> res8: Boolean = true
  streamFrom1To3.take(2).toList == List(1, 2)     //> res9: Boolean = true
  streamFrom1To3.take(3).toList == List(1, 2, 3)  //> res10: Boolean = true
  streamFrom1To3.take(4).toList == List(1, 2, 3)  //> res11: Boolean = true
  
  streamFrom1To3.drop(0)                          //> res12: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  streamFrom1To3.drop(1)                          //> res13: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  streamFrom1To3.drop(2)                          //> res14: fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
  streamFrom1To3.drop(3)                          //> res15: fpinscala.laziness.Stream[Int] = Empty
  streamFrom1To3.drop(4)                          //> res16: fpinscala.laziness.Stream[Int] = Empty
  try {
    streamFrom1To3.drop(-1)
    false
 } catch { case _: Exception => true}             //> res17: Boolean = true
  streamFrom1To3.drop(0).toList == List(1, 2, 3)  //> res18: Boolean = true
  streamFrom1To3.drop(1).toList == List(2, 3)     //> res19: Boolean = true
  streamFrom1To3.drop(2).toList == List(3)        //> res20: Boolean = true
  streamFrom1To3.drop(3).toList == List.empty     //> res21: Boolean = true
  streamFrom1To3.drop(4).toList == List.empty     //> res22: Boolean = true
  
  def isOdd(i: Int) = {
    println(s"Checking if $i is odd")
    i % 2 != 0
  }                                               //> isOdd: (i: Int)Boolean
  emptyStream.takeWhile(isOdd).toList.isEmpty     //> res23: Boolean = true
  streamFrom1To3.takeWhile(isOdd).toList == List(1)
                                                  //> Checking if 1 is odd
                                                  //| Checking if 2 is odd
                                                  //| res24: Boolean = true
  
  def isLessThanFour(i: Int) = {
    println(s"Checking if $i is less than 4")
    i < 4
  }                                               //> isLessThanFour: (i: Int)Boolean
  streamFrom1To3.takeWhile(isLessThanFour).toList == List(1, 2, 3)
                                                  //> Checking if 1 is less than 4
                                                  //| Checking if 2 is less than 4
                                                  //| Checking if 3 is less than 4
                                                  //| res25: Boolean = true
  
  Stream(1, 2, 3, 4).takeWhile(isLessThanFour).toList == List(1, 2, 3)
                                                  //> Checking if 1 is less than 4
                                                  //| Checking if 2 is less than 4
                                                  //| Checking if 3 is less than 4
                                                  //| Checking if 4 is less than 4
                                                  //| res26: Boolean = true
  !streamFrom1To3.forAll(isOdd)                   //> Checking if 1 is odd
                                                  //| Checking if 2 is odd
                                                  //| res27: Boolean = true
  streamFrom1To3.forAll(isLessThanFour)           //> Checking if 1 is less than 4
                                                  //| Checking if 2 is less than 4
                                                  //| Checking if 3 is less than 4
                                                  //| res28: Boolean = true
  Stream(1, 2, 3, 4).forAll(isLessThanFour)       //> Checking if 1 is less than 4
                                                  //| Checking if 2 is less than 4
                                                  //| Checking if 3 is less than 4
                                                  //| Checking if 4 is less than 4
                                                  //| res29: Boolean = false
  emptyStream.takeWhileUsingFoldRight(isOdd).toList.isEmpty
                                                  //> res30: Boolean = true
  streamFrom1To3.takeWhileUsingFoldRight(isOdd).toList == List(1)
                                                  //> Checking if 1 is odd
                                                  //| Checking if 2 is odd
                                                  //| res31: Boolean = true
  
  streamFrom1To3.takeWhileUsingFoldRight(isLessThanFour).toList == List(1, 2, 3)
                                                  //> Checking if 1 is less than 4
                                                  //| Checking if 2 is less than 4
                                                  //| Checking if 3 is less than 4
                                                  //| res32: Boolean = true
  
  Stream(1, 2, 3, 4).takeWhileUsingFoldRight(isLessThanFour).toList == List(1, 2, 3)
                                                  //> Checking if 1 is less than 4
                                                  //| Checking if 2 is less than 4
                                                  //| Checking if 3 is less than 4
                                                  //| Checking if 4 is less than 4
                                                  //| res33: Boolean = true
  empty.headOption == None                        //> res34: Boolean = true
  streamFrom1To3.headOption == Some(1)            //> res35: Boolean = true

  // Test map:
  def doubleIt(i: Int) = {
    println(s"doubling $i")
    2 * i
  }                                               //> doubleIt: (i: Int)Int
  val evensStream1 = streamFrom1To3.map(doubleIt) //> evensStream1  : fpinscala.laziness.Stream[Int] = Cons(<function0>,<function
                                                  //| 0>)
  evensStream1.toList == List(2, 4, 6)            //> doubling 1
                                                  //| doubling 2
                                                  //| doubling 3
                                                  //| res36: Boolean = true
  // Test filter:
  val oddsStream = Stream(1,2,3,4,5).filter(isOdd)//> Checking if 1 is odd
                                                  //| oddsStream  : fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>
                                                  //| )
  oddsStream.toList == List(1, 3, 5)              //> Checking if 2 is odd
                                                  //| Checking if 3 is odd
                                                  //| Checking if 4 is odd
                                                  //| Checking if 5 is odd
                                                  //| res37: Boolean = true
  val evensThatAreOdd = evensStream1.filter(isOdd)//> Checking if 2 is odd
                                                  //| Checking if 4 is odd
                                                  //| Checking if 6 is odd
                                                  //| evensThatAreOdd  : fpinscala.laziness.Stream[Int] = Empty
  evensThatAreOdd.toList == List[Int]()           //> res38: Boolean = true
  val emptyThatIsOdd = emptyStream.filter(isOdd)  //> emptyThatIsOdd  : fpinscala.laziness.Stream[Int] = Empty
  emptyThatIsOdd.toList == List[Int]()            //> res39: Boolean = true
  
  // Test append:
  def makeOdd(i: Int) = {
    println(s"making odd number $i")
    2 * i - 1
  }                                               //> makeOdd: (i: Int)Int
  val oddStream = streamFrom1To3.map(makeOdd)     //> oddStream  : fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0>)
                                                  //| 
  val evensStream2 = streamFrom1To3.map(doubleIt) //> evensStream2  : fpinscala.laziness.Stream[Int] = Cons(<function0>,<function
                                                  //| 0>)
  val oddsThenEvens = oddStream.append(evensStream2)
                                                  //> making odd number 1
                                                  //| oddsThenEvens  : fpinscala.laziness.Stream[Int] = Cons(<function0>,<functio
                                                  //| n0>)
  oddsThenEvens.toList == List(1, 3, 5, 2, 4, 6)  //> making odd number 2
                                                  //| making odd number 3
                                                  //| doubling 1
                                                  //| doubling 2
                                                  //| doubling 3
                                                  //| res40: Boolean = true
  val evensStream3 = streamFrom1To3.map(doubleIt) //> evensStream3  : fpinscala.laziness.Stream[Int] = Cons(<function0>,<function
                                                  //| 0>)
  val emptyThenEvens = emptyStream.append(evensStream3)
                                                  //> emptyThenEvens  : fpinscala.laziness.Stream[Int] = Cons(<function0>,<functi
                                                  //| on0>)
  emptyThenEvens.toList == List(2, 4, 6)          //> doubling 1
                                                  //| doubling 2
                                                  //| doubling 3
                                                  //| res41: Boolean = true
  streamFrom1To3.append(emptyStream).toList == List(1, 2, 3)
                                                  //> res42: Boolean = true
  // Test flatMap:
  def makePowerStream(i: Int) = {
    println(s"Raising $i to the first 3 powers")
    Stream(i, i * i, i * i * i)
  }                                               //> makePowerStream: (i: Int)fpinscala.laziness.Stream[Int]
  val powerStream = streamFrom1To3.flatMap(makePowerStream)
                                                  //> Raising 1 to the first 3 powers
                                                  //| powerStream  : fpinscala.laziness.Stream[Int] = Cons(<function0>,<function0
                                                  //| >)
  powerStream.toList == List(1,1,1,2,4,8,3,9,27)  //> Raising 2 to the first 3 powers
                                                  //| Raising 3 to the first 3 powers
                                                  //| res43: Boolean = true
  emptyStream.flatMap(makePowerStream).toList == List[Int]()
                                                  //> res44: Boolean = true
  def clearStream(i: Int) = empty[Int]            //> clearStream: (i: Int)fpinscala.laziness.Stream[Int]
  powerStream.flatMap(clearStream).toList == List[Int]()
                                                  //> res45: Boolean = true
}