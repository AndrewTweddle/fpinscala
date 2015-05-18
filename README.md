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
| 3.9 - 3.12  | None |
| 3.13        | How to write foldLeft in terms of foldRight and vice versa... reverse(foldLeft(List(1,2,3), Nil:List[Int])((l, a) => Cons(a, l))) == foldRight(List(1,2,3), Nil:List[Int])((a, l) => Cons(a, l)) == Cons(1,Cons(2,Cons(3,Nil))) |
| 3.14        | None |
| 3.15        | Implemented concatListOfLists as a foldRight over append. append is linear in the first list, but foldLeft keeps on re-generating the first term, so the effect is quadratic. So I changed to foldRight to fix this. |
| 3.16        | Why don't underscores work? def addOne(l: List[Int]): List[Int] = foldRight(l, Nil: List[Int])((a,b) => Cons(a + 1, b)) but not def addOne(l: List[Int]): List[Int] = foldRight(l, Nil: List[Int])(Cons(_ + 1, _)) |
| 3.17        | None |
| 3.18        | Generalizing map is easy via foldRight. But the model answer also give two other implementations that are safer against stack overflows. |
| 3.19 - 3.21 | filter, flatMap and filter via flatMap. Note that I returned "Nil: List[A]" where Nil would be sufficient, according to model answer. |
| 3.22        | None |


## Tips

1. Check pattern matches for redundant case statements i.e. where a more general case statement covers an earlier specific case.
2. When using foldRight, consider using foldLeft on the reversed list or a mutable local variable for greater efficiency and safety against stack overflows.
   _Edit: Not necessary... The [Scala 2.11.0 implementation](https://github.com/scala/scala/blob/v2.11.0/src/library/scala/collection/immutable/List.scala#L399) does this already_
3. Nil: List[B] is necessary in fold calls to bind the return type. Just Nil is probably sufficient in the implementation as the type is already bound.


## Thoughts

1. It sucks that Scala doesn't have automatic currying and type inference over all parameters. Multiple parameter lists is a clever but kludgy idea.


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

