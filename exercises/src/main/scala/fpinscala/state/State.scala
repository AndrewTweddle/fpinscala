package fpinscala.state


trait RNG {
  def nextInt: (Int, RNG) // Should generate a random `Int`. We'll later define other functions in terms of `nextInt`.
}

object RNG {
  // NB - this was called SimpleRNG in the book text

  case class Simple(seed: Long) extends RNG {
    def nextInt: (Int, RNG) = {
      val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL // `&` is bitwise AND. We use the current seed to generate a new seed.
      val nextRNG = Simple(newSeed) // The next state, which is an `RNG` instance created from the new seed.
      val n = (newSeed >>> 16).toInt // `>>>` is right binary shift with zero fill. The value `n` is our new pseudo-random integer.
      (n, nextRNG) // The return value is a tuple containing both a pseudo-random integer and the next `RNG` state.
    }
  }

  type Rand[+A] = RNG => (A, RNG)

  val int: Rand[Int] = _.nextInt

  def unit[A](a: A): Rand[A] =
    rng => (a, rng)

  def map[A,B](s: Rand[A])(f: A => B): Rand[B] =
    rng => {
      val (a, rng2) = s(rng)
      (f(a), rng2)
    }
    
  // helper method, not nested so that it can be tested:
  def intToNonNegativeInt(i: Int): Int = 
    if (i >= 0) i else -(i + 1)
    // i.e. use 2's complement to handle the situation where i1 = Int.MinValue
  
  def nonNegativeInt(rng: RNG): (Int, RNG) = {
    val (i1, rng2) = rng.nextInt
    (intToNonNegativeInt(i1), rng2)
  }

  def double(rng: RNG): (Double, RNG) = {
    val (i1, rng1) = nonNegativeInt(rng)
    val frac = i1 / (Int.MaxValue.toDouble + 1)
    (frac, rng1)
  }

  def intDouble(rng: RNG): ((Int,Double), RNG) = {
    val (i1, rng1) = rng.nextInt
    val (d1, rng2) = double(rng1)
    ((i1, d1), rng2)
  }

  def doubleInt(rng: RNG): ((Double,Int), RNG) = {
    val (d1, rng1) = double(rng)
    val (i1, rng2) = rng1.nextInt
    ((d1, i1), rng2)
  }

  def double3(rng: RNG): ((Double,Double,Double), RNG) = {
    val (d1, rng1) = double(rng)
    val (d2, rng2) = double(rng1)
    val (d3, rng3) = double(rng2)
    ((d1, d2, d3), rng3)
  }

  def ints(count: Int)(rng: RNG): (List[Int], RNG) =
    if (count == 0) (List(), rng) else {
      val (i, r1) = rng.nextInt
      val (lst, r2) = ints(count - 1)(r1)
      (i :: lst, r2)
    }
  
  def doubleViaMap(rng: RNG): (Double, RNG) = (map(int) {
    intToNonNegativeInt(_) / (Int.MaxValue.toDouble + 1)
  })(rng)
  
  def map2[A,B,C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = 
    rng => {
      val (a, rng1) = ra(rng)
      val (b, rng2) = rb(rng1)
      val c = f(a, b)
      (c, rng2)
    }

  def sequence[A](fs: List[Rand[A]]): Rand[List[A]] =
    {
      fs.foldRight((r: RNG) => (List[A](), r)) {
        (r: Rand[A], rs: Rand[List[A]]) => map2(r, rs)(_ :: _)
      } 
    }
  
  def intsViaSequence(count: Int)(rng: RNG): (List[Int], RNG) = {
    (sequence(List.fill(count)(int)))(rng)
  }
  
  // After looking at the model answer, I should have done this rather:
  def intsViaSequenceAsRand(count: Int): Rand[List[Int]] =
    (sequence(List.fill(count)(int)))

  def flatMap[A,B](f: Rand[A])(g: A => Rand[B]): Rand[B] =
    rng => {
      val (a, r2) = f(rng)
      g(a)(r2)
    }
    
  /* Explanation of "i - mod + n - 1" formula below:
   * 
   * i - mod n is the start of the block of n numbers that i is in.
   * If the final block fits into the Int range perfectly, then
   * adding "n-1" allows i to be any item in the block,
   * 
   * including the final or (n-1)th item, without overflowing.
   * But adding n would cause overflow even if the final block fitted perfectly.
   * If the final block doesn't fit, then the (n-1)th item will overflow.
   */
  def nonNegativeLessThan(n: Int): Rand[Int] = flatMap(nonNegativeInt) {
    i: Int => {
      val mod = i % n
      if (i - mod + n - 1 < 0)  
      {
        // try again, using the next random number:
        nonNegativeLessThan(n)
      }
      else
      {
        // return the correct value and the current RNG:
        unit(i % n)
      }
    }
  }
  
  def mapUsingFlatMap[A,B](s: Rand[A])(f: A => B): Rand[B] = 
    flatMap(s) { a => unit(f(a)) }
  
  def map2UsingFlatMap[A,B,C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = 
    flatMap(ra) { a => 
      flatMap(rb) { b => unit(f(a, b)) }
    }
    // Checked against model answer. Just use map, not flatMap, in the inner computation
  
  // For testing mapUsingFlatMap:
  def doubleViaMapUsingFlatMap(rng: RNG): (Double, RNG) = (mapUsingFlatMap(int) {
    intToNonNegativeInt(_) / (Int.MaxValue.toDouble + 1)
  })(rng)
  
  
}

case class State[S,+A](run: S => (A, S)) {
  def map[B](f: A => B): State[S, B] =
    State(s => {
        val (a, s1) = run(s)
        (f(a), s1)
      }
    )
    
  def map2[B,C](sb: State[S, B])(f: (A, B) => C): State[S, C] =
    State(s => {
        val (a, s1) = run(s)
        val (b, s2) = sb.run(s1)
        (f(a,b), s2)
      } 
    )
    
  def flatMap[B](f: A => State[S, B]): State[S, B] =
    State(s => {
        val (a, s1) = run(s)
        f(a).run(s1)
      } 
    )
}

sealed trait Input
case object Coin extends Input
case object Turn extends Input

case class Machine(locked: Boolean, candies: Int, coins: Int)

object State {
  def unit[S, A](a: A): State[S, A] = State(s => (a, s))
  
  def sequence[S, A](states: List[State[S, A]]): State[S, List[A]] = {
    val ini = unit[S, List[A]](List())
    states.foldRight(ini) { 
      (state, lstState) => state.map2(lstState)(_ :: _)
    }
  }
    
  type Rand[A] = State[RNG, A]
  
  // Naive implementation of the vending machine simulator:
  def simulateMachineNaively(inputs: List[Input]): State[Machine, (Int, Int)] = {
    State[Machine, (Int, Int)](
      pre => {
        val states = inputs.map(inputToState)
        val finalMachine = states.foldLeft(pre) { 
          (pre, r) => {
            val ((_, _), post) = r.run(pre) 
              // It feels wrong to generate then ignore the (Int, Int) pairs 
            post
          }
        }
        ((finalMachine.coins, finalMachine.candies), finalMachine)
      }
    )
  }
  
  def inputToState(input: Input): State[Machine, (Int, Int)] = {
    input match {
      case Coin => coinToState
      case Turn => turnToState
    }
  }
  
  val coinToState = State[Machine, (Int, Int)]{ pre =>
    val post = 
      if (pre.locked && pre.candies > 0)
        pre.copy(locked = false, coins = pre.coins + 1)
      else
        pre
    ((post.coins, post.candies), post)
  }
  
  val turnToState = State[Machine, (Int, Int)] { pre =>
    val post =
      if (pre.locked || pre.candies == 0) 
        pre 
      else 
        pre.copy(locked = true, candies = pre.candies - 1)
    ((post.coins, post.candies), post)
  }

  /* One issue with the above approach, is that the (coins, candies) "A value" 
   * is artificially created and then ignored most of the time.
   */  
  
  // In practice, there was no need for modify. Avoid States until the end:
  def getMachineModifier(input: Input): Machine => Machine =
    input match {
      case Coin => coinMachineModifier
      case Turn => turnMachineModifier
    }
  
  val coinMachineModifier: Machine => Machine = pre =>
    if (pre.locked && pre.candies > 0)
      pre.copy(locked = false, coins = pre.coins + 1)
    else
      pre
  
  val turnMachineModifier: Machine => Machine = pre =>
    if (pre.locked || pre.candies == 0) 
      pre 
    else 
      pre.copy(locked = true, candies = pre.candies - 1)
  
  def simulateMachineViaModifiers(inputs: List[Input]): State[Machine, (Int, Int)] = { 
    State[Machine, (Int, Int)]( 
      pre => {
        val modifiers = inputs.map(getMachineModifier(_))
        val post = modifiers.foldLeft(pre) { (mach, modif) => modif(mach) }
        ((post.coins, post.candies ), post)
      }
    )
  }
  
  /* The model answer uses get and modify 
   * i.e. the approach advised in section 6.6.
   * 
   * It also uses Sequence, but in a kludgy way.
   * It feels like an alternative to Sequence is needed 
   * which only returns the final value and state e.g. combine. 
   * Re-write the model answer using combine: 
   */
    
  // Name with underscores, otherwise Applicative.scala breaks...
  def _modify[S](f: S => S): State[S, Unit] = for {
    s <- _get
    _ <- _set(f(s))
  } yield ()
  
  def _get[S]: State[S, S] = State(s => (s, s))
  def _set[S](s: S): State[S, Unit] = State(_ => ((), s))
  
  def combine[S, A](stateActions: List[State[S, A]]): State[S, A] =
    stateActions.reduce((s1, s2) => s1.map2(s2) { (a1, a2) => a2 })
  
  def modifyStateWithInput(i: Input)(s: Machine): Machine = {
    (i, s) match {
      case (_, Machine(_, 0, _)) => s
      case (Turn, Machine(true, _, _)) => s
      case (Coin, Machine(false, _, _)) => s
      case (Turn, Machine(_, cdy, _)) => s.copy(locked=true, candies = cdy - 1)
      case (Coin, Machine(_, _, cns)) => s.copy(locked=false, coins = cns + 1)
    }  
  }
  
  def simulateMachineViaModify(inputs: List[Input]): State[Machine, (Int, Int)] = {
    State(
      pre => { 
        val states = inputs.map(i => _modify(modifyStateWithInput(i))) 
        val postState = combine(states)
        val (_, post) = postState.run(pre)
        ((post.coins, post.candies ), post)
      } 
    )
  }
  
  val simulateMachine = simulateMachineViaModifiers _
}