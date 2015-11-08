# Purpose of this fork

This is my fork of the original 
[fpinscala/fpinscala](https://github.com/fpinscala/fpinscala)
repository for the book ["Functional Programming in Scala"](http://www.manning.com/bjarnason/). 
The purpose is to do the exercises and compare to the model answers.

# Learnings

## Notes

| Exercises   | Notes  |
| ---         | ---    |
| 2.1         | I thought of an elegant solution for an efficient tail recursive Fibonacci function. |
| 3.1 - 3.6   | I had a redundant case statement in exercise 3.5 and 3.6. |
| 3.6         | Q: Why can't the init function be implemented in constant time like tail? A: There are a long chain of pointers referencing the last item, so removing that last item causes cascading changes all the way up the structure. |
| 3.7         | Q: Can product, implemented using foldRight, immediately halt the recursion if it encounters a zero? A: Not without remaining type-agnostic. Possibly define a foldRightWhile function taking an extra predicate and outcome if false. |
| 3.8         | Q: foldRight(List(1,2,3), Nil: List[Int])(Cons(_,_))) = Cons(1,Cons(2,Cons(3,Nil))) = List(1,2,3). What does this say about their relationship? A: foldRight with Cons is the identity function for lists? }
| 3.9 - 3.12  | No comments |
| 3.13        | How to write foldLeft in terms of foldRight and vice versa... reverse(foldLeft(List(1,2,3), Nil:List[Int])((l, a) => Cons(a, l))) == foldRight(List(1,2,3), Nil:List[Int])((a, l) => Cons(a, l)) == Cons(1,Cons(2,Cons(3,Nil))) |
| 3.14        | No comments |
| 3.15        | Implemented concatListOfLists as a foldRight over append. append is linear in the first list, but foldLeft keeps on re-generating the first term, so the effect is quadratic. So I changed to foldRight to fix this. |
| 3.16        | Why don't underscores work? def addOne(l: List[Int]): List[Int] = foldRight(l, Nil: List[Int])((a,b) => Cons(a + 1, b)) but not def addOne(l: List[Int]): List[Int] = foldRight(l, Nil: List[Int])(Cons(_ + 1, _)) |
| 3.17        | No comments |
| 3.18        | Generalizing map is easy via foldRight. But the model answer also give two other implementations that are safer against stack overflows. |
| 3.19 - 3.21 | filter, flatMap and filter via flatMap. Note that I returned "Nil: List[A]" where Nil would be sufficient, according to model answer. |
| 3.22 - 3.23 | No comments |
| 3.24        | My implementation appears fine. But the model answer is slightly better, because it is more obvious that all possible cases have been handled. |
| 3.25 - 3.27 | No comments |
| 3.28        | I don't think I agree with the discussion of ADTs and encapsulation on page 46 of the book. Encapsulation is about allowing the implementation to be changed without code changes cascading to consumers of the interface. Mutability is moot. |
| 3.29        | 3 parameter lists were used, not 2, so that the g: (B,B) => B could benefit from type inference over the previous f: A=>B. In some cases the types needed to be specified after the function name in the function call, to specify the most base type. |
| 4.1         | Fine, with a minor tweak: I passed None: Option[B] in the implementation of flatMap. There was no need to specify the type of None. |
| 4.2         | A bit tricky - remember that map and flatMap can operate on Options as easily as sequences. See tip 5 below for noticing opportunities to use map or flatMap instead. |
| 4.3         | Pretty easy, based on using tip 5 for working out when to use map or flatMap |
| 4.4         | Quite easy, using map2. The model answer uses a recursive call as its first solution, which is a bit different. |
| 4.5         | No comments |
| 4.6         | A bit tricky |
| 4.7         | Either.traverse() and sequence(). A bit tricky. I didn't see that map2 could be used to implement traverse. |
| 4.8         | Qu: map2 can only report error. What change is required to report multiple errors. Ans: Different data type to store multiple errors; different logic to not short circuit the error path; perhaps a way to distinguish sequential from independent calculation steps; perhaps make short circuiting optional. |
| 5.1         | Straightforward. But the model answer points out that it's not a tail recursive solution. Also a case _ => would have been more succinct. |
| 5.2         | Easy, unless you try to make drop lazy as well. I couldn't see how to do this, and the model answer doesn't try. I suspect it may not be possible, because one must call t() to know whether to return a Cons or Empty - a stream can't *lazily* decide whether to be an instance of Cons or Empty... Unless you added a 3rd choice: `case class ConsOrEmpty[+A](decide: () => Stream[A]) extends Stream[A]` |
| 5.3         | My solution is more verbose than the model answer. Although I avoid calculating head twice (so don't assume that the head function is referentially transparent). |
| 5.4         | Stream.forAll() was very easy using the provided foldRight() |
| 5.5         | Stream.takeWhileUsingFoldRight() - a little tricky getting the types right (e.g. knowing when to use a value or a HOF that returns the value) |
| 5.6         | Stream.headOption was surprisingly easy using foldRight, but it feels like cheating as you ignore the => B parameter in the 2nd parameter to foldRight! |
| 5.7         | Easy, except for getting the function signature right to support covariance. I'm concerned that the first stream element gets processed eagerly by foldRight, not lazily. |
| 5.8         | Stream.constant(n): Easy, but the model answer gives a more efficient implementation using a lazy val |
| 5.9         | Stream.from(n): straightforward |
| 5.10        | fibs(): I made it more cumbersome than necessary by using nextFibs(prev, curr): Stream[Int] instead of nextFibs(curr, next) |
| 5.11        | Stream.unfold(): No comment. |
| 5.12        | fibs, from, constant and ones in terms of unfold: easy |
| 5.13        | map, take, takeWhile, zipWith, zipAll in terms of unfold: Fairly easy. But beware of getting the order of parameters wrong. |
| 5.14        | Implement startsWith using previous functions: easy |
| 5.15        | Implement tails using unfold. It was tricky to include the empty stream. I used Option. The model answer just appended it. |
| 5.16        | Implement scanRight: a streaming foldRight. Hard. My solution was verbose. Model answer used foldRight. Both weren't lazy enough (see worksheet for discussion). |
| 6.1         | nonNegativeInt(rng: RNG): (Int, RNG). Simple, but how to test it? What seed will transform to Int.MinValue? I defined a separate conversion method and tested that. |
| 6.2         | double(rng: RNG): (Double, RNG). Simple to code. But how to test that the result is in the range [0.0, 1.0)? |
| 6.3         | intDouble(), doubleInt(), double3(): Simple enough. |
| 6.4         | ints(). Seemed easy, but I missed an opportunity to make the function tail recursive. |
| 6.5         | Re-implement double() using map(). Easy enough. But is it better to use a Rand[Double]? |
| 6.6         | map2 as a state action over a random number generator. Straightforward. |

## Tips

1. Check pattern matches for redundant case statements i.e. where a more general case statement covers an earlier specific case.
2. When using foldRight, consider using foldLeft on the reversed list or a mutable local variable for greater efficiency and safety against stack overflows.
   _Edit: Not necessary... The [Scala 2.11.0 implementation](https://github.com/scala/scala/blob/v2.11.0/src/library/scala/collection/immutable/List.scala#L399) does this already_
3. Nil: List[B] is necessary in fold calls to bind the return type. Just Nil is probably sufficient in the implementation as the type is already bound.
4. In pattern matches, see if there is a way to make the last clause a "case _ => ...", as this ensures that all scenarios have been covered.
5. See opportunities for using map or flatMap on an option by looking for pattern match expression where None maps to None, and Some(?) maps to an expression. If the result is a Some(expr) convert to map(expr). If the result can evaluate to Some or None, then use flatMap.
6. In exercise 4.6, Left[E] and Right[A] only implement one type parameter, so one must typecast to Either[E,A] to get correct signature for map, flatMap, etc.
7. In 4.7, "(_)" didn't work for an identity map. I thought it would be necessary to specify the type of the parameter. But "(x => x)" would have worked.
8. Always consider whether a solution could cause a stack overflow. If so, look for a tail recursive solution.
9. Consider whether using a non-shared mutable variable (i.e. internal to the function) can improve performance. See Stream.toListFast in the model answer.
10. Make a method final to allow it to be made tail recursive. See Stream.drop in the model answer.
11. When two case clauses have the same right hand side, consider whether the matches can be re-ordered to collapse them into one. See the model answer for Stream.drop() which does this.
12. If ignoring a parameter in a lambda expression, don't give it a name. Use _ to make it obvious that it is being ignored (see the model answer for Stream.headOption).
13. If seeing "by name" parameters, consider whether a "lazy val" is needed to avoid multiple evaluations of the parameter value.

## Thoughts

1. It sucks that Scala doesn't have automatic currying and type inference over all parameters. Multiple parameter lists is a clever but kludgy idea.
2. I don't have a mental model for when (_) will work as an identity map, and when (x => x) is required (see exercise 4.7). TODO: Update when I understand.
3. The Either data type feels very wrong to me (the names, not the concept):
  1. It looks symmetrical, but isn't. The left side short-circuits but the right side doesn't.
  2. The code misses an opportunity to be self-documenting. 
     The names don't reveal the intention, which is that one side should represent success and the other failure.
     As a result additional explanation is needed to explain that you should remember that "right" can also mean "correct".
  3. Even if you argue that general purpose names don't limit the use, there should still be a clue in the names that one side will short-circuit and the other not e.g. shortened and normal (short would be better, but Short is a data type).

# Original readme file contents

[![Build status](https://travis-ci.org/fpinscala/fpinscala.svg?branch=master)](https://travis-ci.org/fpinscala/fpinscala)

This repository contains exercises, hints, and answers for the book
[Functional Programming in Scala](http://manning.com/bjarnason/). Along
with the book itself, it's the closest you'll get to having your own
private functional programming tutor without actually having one.

Here's how to use this repository:

Each chapter in the book develops a fully working library of functions
and data types, built up through a series of exercises and example code
given in the book text. The shell of this working library and exercise
stubs live in
`exercises/src/main/scala/fpinscala/<chapter-description>`, where
`<chapter-description>` is a package name that corresponds to the
chapter title (see below). When you begin working on a chapter, we
recommend you open the exercise file(s) for that chapter, and when you
encounter exercises, implement them in the exercises file and make sure
they work.

If you get stuck on an exercise, let's say exercise 4 in the chapter,
you can find hints in `answerkey/<chapter-description>/04.hint.txt` (if
no hints are available for a problem, the file will just have a single
'-' as its contents) and the answer along with an explanation of the
answer and any variations in
`answerkey/<chapter-description>/04.answer.scala` or
`04.answer.markdown`. The finished Scala modules, with all answers for
each chapter live in
`answers/src/main/scala/fpinscala/<chapter-description>`. Please feel
free to submit pull requests for alternate answers, improved hints, and
so on, so we can make this repo the very best resource for people
working through the book.

Chapter descriptions:

* Chapter 2: gettingstarted
* Chapter 3: datastructures
* Chapter 4: errorhandling
* Chapter 5: laziness
* Chapter 6: state
* Chapter 7: parallelism
* Chapter 8: testing
* Chapter 9: parsing
* Chapter 10: monoids
* Chapter 11: monads
* Chapter 12: applicative
* Chapter 13: iomonad
* Chapter 14: localeffects
* Chapter 15: streamingio

To build the code for the first time, if on windows:

    $ .\sbt.cmd

If on mac/linux, first make sure you have not checked out the code onto
an encrypted file system, otherwise you will get compile errors
regarding too long file names (one solution is to put the fpinscala repo
on a unencrypted usb key, and symlink it into your preferred code
location).

    $ chmod a+x ./sbt
    $ ./sbt

This will download and launch [sbt](http://scala-sbt.org), a build tool
for Scala. Once it is finished downloading, you'll get a prompt from
which you can issue commands to build and interact with your code. Try
the following:

    > project exercises
    > compile

This switches to the exercises project, where your code lives, and
compiles the code. You can also do:

    > console

to get a Scala REPL with access to your exercises, and

    > run

To get a menu of possible main methods to execute.

To create project files for the eclipse IDE you can install the
[sbteclipse](https://github.com/typesafehub/sbteclipse)
[sbt](http://scala-sbt.org) plugin. This makes a new command available
in [sbt](http://scala-sbt.org):

    > eclipse

All code in this repository is
[MIT-licensed](http://opensource.org/licenses/mit-license.php). See the
LICENSE file for details.

Have fun, and good luck! Also be sure to check out [the community
wiki](https://github.com/fpinscala/fpinscala/wiki) for the **chapter
notes**, links to more reading, and more.

_Paul and Rúnar_

