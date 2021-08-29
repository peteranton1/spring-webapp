TDD for those who don't need it
---

[Back to Table of Contents](../README.md)

# Overview

Test Driven Development (TDD) is all the rage these days. 

## The TDD Benefits arguments

* Gives you confidence to Refactor
* Helps you write a better designed API
* Serves as documentation for the intended behaviour
* Untested code is legacy code

## The Anti-TDD counter-arguments

* I already have HUGE confidence
* My code is already well-designed. Unlike yours.
* My commit log is more awesome than your resume
* Untested tests are legacy code too

# What Matters Most

Say you are a professional developer. 
What are you thinking about?

| **Thinking about** | **Which Means** |
| ----------- | ---------------------- |
| **Machinery** | The technologies and approaches you use to mean the requirements. |
| **Requirements** | Hand-waving vagueness that is subject to change at the whim of the customer |

The main idea is that you are paid to produce software that meets requirements and 
is done as quick and as correct as you can. The machinery is just the technologies you 
use to achieve that. The programming language, framework and paradigm are not relevant to 
the customer. All they really care about is does it meet there requirements.
Unfortunately the requirements start off vague and even after they are well defined they
are subject to change in the light of a dynamic business environment. 

## Write down requirements

So what should a developer do in this situation? How about writing down the requirements. 
Even better than writing the requirements down in English is to write the requirements
down in code. These are otherwise known as tests. Code is unambiguous and achievable, 
whereas the requirements as specified by the customer may well not be. 

## Update the written requirements

When requirements change, the requirements in code should change first. 
This means the implementation will be out of step with the requirements until
the implementation catches up with the requirements. This means tests fail initially. 
Tests failing is good. It means once you get the tests to pass by changing the 
implementation, you know the requirements have been met. 

## Failing tests are good

If you have a set of tests which reflect the actual customer requirements 
this is good. If this is the start of the development then you now have a 
good roadmap to what is left to do. Once all the tests pass you have met 
the requirement and you can stop. If you write the tests at the end, then 
you have already met the requirement, so why do you need to then write the 
tests? It feels like extra work, since you know that you have met the 
requirements. If timelines are short, some developers therefore don't do 
this extra work. This leads to defects being found in production too often. 

# Caveat

## JUnit or Cucumber?

It doesn't matter. Whichever test technology makes sense. So long as you can 
express the customer requirements in the test. Sometimes it feels like the 
customer requirements are too high level to express in a JUnit. This is not 
usually true. Usually there is a way to express the requirement with 
sufficient granularity that it can be written in a test at a low enough 
level for the current development task. If it can't be expressed then 
probably that means the requirements are not known by the developer 
well enough and there needs to be a deeper requirements gathering exercise. 

## Iterations vs Complete Requirements

The usual situation is that not enough detail is known about the problem space
yet to implement everything required of the development, in one sitting. Also, 
there is real value in getting your attempts into a demonstrable state early in 
the lifecycle so that it can be put in front of users before you get too far 
along. Then you can check your understanding agrees with the customer 
expectation. There is a danger of being off course for whatever reason and that 
will be harder to correct the further along you are in the development. 

The TDD cycle should be small enough to be useful. It is disheartening to have 
too many failing tests. Write a few tests for the development you will be doing 
today if you get no interruptions. But wait. You always get interruptions. 

Well the tests that fail today are the tasks to do today, so when returning 
after an interruption, the failing tests remind you where you were up to and 
what is left to do today. 

Thus there is no need to write all the tests for the project, up front. Just 
write enough tests to support the next small amount of development you will be 
doing. How about a few tests for the work you intend to do today only? When 
those few tests pass, it is time to repeat the cycle for the next day.    

## Customers should approve tests

The basis of behaviour driven tests is that the customer can understand and 
approve the tests to ensure they correctly test the requirements. Unfortunately
customers rarely actually read tests. At least by using a convention like 
cucumber, you *could* show the test to the customer. Maybe it is useful 
if the only way you can demonstrate the software to the customer is to 
show them the test and run it to prove it passes. 
