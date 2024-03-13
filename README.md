## Synchronous VS Asynchronous

- In synchronous execution, tasks are performed one after the other in a sequential manner.
  Each task must wait for the previous one to complete before starting execution.
- Synchronous operations are blocking, meaning that the program is paused or blocked
  while waiting for a task to complete.
  The program cannot proceed to the next instruction until the current task finishes.
- In asynchronous execution, tasks are initiated without waiting for the previous tasks to complete.
  Asynchronous tasks can run <strong>concurrently</strong> with other tasks,
  allowing the program to continue its execution without waiting for the asynchronous tasks to finish.
- Use asynchronous programming for I/O-bound tasks or heavy CPU-bound computations to offload main thread and make it
  responsive.

## Thread

Threads allow concurrent execution of tasks,
enabling multiple operations to be performed simultaneously or interleaved.

- Threads don't run immediately, os decide when to run them.
- Creation of thread has cost, use thread pool specially when your app create so many threads.

## Thread Pool

A thread pool is a collection of pre-instantiated, idle threads
that stand ready to be given work.
This approach is preferred over instantiating new threads for each task
when there is a large number of short tasks to be done
rather than a small number of long ones,
as it prevents the overhead of creating a thread a large number of times.

## Executor Service
The ExecutorService providing a more powerful and flexible way to manage and execute tasks in asynchronous mode.
It is normally used instead of explicitly creating threads for each task, 
which provides a number of benefits, such as reusing threads, managing a pool of threads, and handling the execution lifecycle.

#### Future
Future interface represents the result of an asynchronous computation. 
It provides methods to check if the computation is complete, to wait for its completion, and to retrieve the result of the computation.

#### ForkJoinPool vs ExecutorService
- ForkJoinPool is designed to be used for CPU-intensive workloads. 
The default number of threads in ForkJoinPool is equal to the number of CPUs on the system.
- Use the Executor service for doing IO-intensive tasks. 
In ExecutorService you can set a number of threads according to the IO capacity of your system instead of the CPU capacity of your system

## Completable Future
You can think of completable futures as a better Future. 
It allows you to chain multiple asynchronous operations together. 
It provides a rich set of methods for registering callbacks to be executed when the asynchronous task completes, either successfully or exceptionally.

## Synchronized
A <strong>race condition</strong> is a condition of a program where its behavior 
depends on relative timing or interleaving of multiple threads or processes. 
One or more possible outcomes may be undesirable, resulting in a bug. 
We refer to this kind of behavior as nondeterministic.

<strong>Thread-safe</strong> is the term we use to describe a program, code, or data structure free of race conditions when accessed by multiple threads.


A `mutex` (or mutual exclusion) is the simplest type of synchronizer.
 It ensures that only one thread can execute the critical section of a computer program at a time.<br/>
In Java a piece of code marked with `synchronized` becomes a synchronized block, allowing only one thread to execute at any given time.

While in case of a mutex only one thread can access a critical section, 
`Semaphore` allows a fixed number of threads to access a critical section. 
Semaphores can be used to enforce rate limiting or throttling in systems where 
the rate of requests or operations needs to be limited to prevent overload or excessive resource consumption.

`Lock` is a general concept referring to any synchronization mechanism used to coordinate access to shared resources.

`Deadlock` can occur in a situation when a thread is waiting for an object lock, 
that is acquired by another thread and second thread is waiting for an object lock that is acquired by first thread. 
Since, both threads are waiting for each other to release the lock, the condition is called deadlock.


A `monitor` is a synchronization mechanism that allows threads to have:
- mutual exclusion: only one thread can execute the method at a certain point in time, using locks.
- cooperation: the ability to make threads wait for certain conditions to be met.

Why is this feature called “monitor”? Because it monitors how threads access some resources. 
The keyword synchronized in java is an example of monitor.

<br/>
Some synchronization classes provided in java:

- ReentrantReadWriteLock: allows multiple threads to read from a shared resource concurrently, while ensuring exclusive write access.
- AtomicInteger: a thread-safe integer variable that supports atomic operations.
- ArrayBlockingQueue: is a bounded blocking queue implementation that stores elements in a fixed-size array and blocks when the queue is full or empty during insertion or removal.
  blocking queue managing data transfer between producer and consumer threads.
- CyclicBarrier: in this synchronization, we have multiple threads working on a single algorithm. 
Algorithm works in phases. All threads must complete phase 1 then they can continue to phase 2. 
Until all the threads do not complete the phase 1, all threads must wait for all the threads to reach at phase 1.
- ThreadLocalRandom: A random number generator isolated to the current thread.
- CountDownLatch: is used to make sure that a task waits for other threads before it starts.

## Thread Starvation
Also known as `thread exhaustion` is the condition or state when all the threads of the Thread Pool are in use and app don't have any available or free thread to process a request.

#### Virtual Thread
In old version of java there was only one type of thread. 
It’s called as ‘classic’ or `platform thread`. 
Whenever a platform thread is created, an operating system thread is allocated to it. 
Only when the platform thread exits(i.e., dies) the JVM, this operating system thread is free to do other tasks. 
Until then, it cannot do any other tasks. Basically, there is a 1:1 mapping between a platform thread and an operating system thread.
According to this architecture, OS thread will be unnecessarily locked down and not doing anything when thread waits for I/O operation.
In order to efficiently use underlying operating system threads, `virtual threads` have been introduced.
A virtual thread still runs code on an OS thread. 
However, when code running in a virtual thread calls a blocking I/O operation, 
the Java runtime suspends the virtual thread until it can be resumed. 
The OS thread associated with the suspended virtual thread is now free to perform operations for other virtual threads. 
This way each OS thread handle multiple virtual thread. 
Platform threads take 20 MB memory and 1ms to lunch, but virtual threads are cheap, about 1000 times cheaper. 
Virtual threads are particularly useful in scenarios where the overhead of context switching between threads is high, 
such as in I/O-bound applications or when dealing with a large number of lightweight tasks.

## Thread Local
ThreadLocal provides thread-local variables. The data stored in a ThreadLocal is isolated to each thread and does not interfere with the same variable accessed by other threads
ThreadLocal instances are typically used to store data that is specific to a particular thread, 
such as user IDs, session IDs, transaction IDs, or any other context that needs to be propagated across method calls within the same thread.
- we should be extra careful when we’re using ThreadLocals and thread pools together because value of thread local doesn't necessary cleanups when thread back to pool.

---
**Note**

- It is convention to add suffix `Async` for functions which are asynchronous.
- The `main thread` refers to the initial thread of execution that is created when a Java program starts. 
It is the thread from which the main() method is invoked, and it serves as the entry point for the program's execution
- In threading, `interleaving` occurs when different threads take turns executing their instructions on a CPU. 
It's like juggling multiple tasks simultaneously, where the CPU switches between threads rapidly, giving the impression of parallel execution.
- When the scheduler decides to switch from executing one thread to another, 
it performs a `context switch`. During this switch, the scheduler saves information associated with the currently running thread. 
It then restores the state of the next thread that is scheduled to run. 
This allows the CPU to seamlessly switch between multiple threads or processes, giving the illusion of concurrent execution.