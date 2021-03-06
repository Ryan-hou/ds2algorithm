package com.github.ryan.data_structure.concurrent.executor;

import com.github.ryan.data_structure.concurrent.Runnable;


/**
 * An object that executes submitted Runnable tasks. This
 * interface provides a way of decoupling task submission from
 * the mechanics of how each task will be run, including details
 * of thread use, scheduling, etc.
 * An executor is normally used instead of explicitly creating
 * threads. For example, rather than invoking new Thread(new RunnableTask()).start()
 * for each of a set of tasks, you might use:
 *
 * Executor executor = anExecutor;
 * executor.execute(new RunnableTask1());
 * executor.execute(new RunnableTask2());
 * ...
 *
 * However, the Executor interface does not strictly
 * require that execution be asynchronous. In the simplest case,
 * an executor can run the submitted task immediately in the call's
 * thread:
 *
 * class DirectExecutor implement Executor {
 *     public void executor(Runnable r) {
 *         r.run();
 *     }
 * }
 *
 * More typically, tasks are executed in some thread other than
 * the caller's thread. The executor below spawns a new thread
 * for each task.
 *
 * class ThreadPerTaskExecutor implements Executor {
 *     public void execute(Runnable r) {
 *         new Thread(r).start();
 *     }
 * }
 *
 * Many Executor implementations impose some sort of
 * limitation no how and when tasks are scheduled. The executor
 * below serializes the submission of tasks to a second executor,
 * illustrating a composite executor.
 *
 * class SerialExecutor implements Executor {
 *     final Queue<Runnable> task = new ArrayDeque<>();
 *     final Executor executor;
 *     Runnable active;
 *
 *     SerialExecutor(Executor executor) {
 *         this.executor = executor;
 *     }
 *
 *     public synchronized void execute(final Runnable r) {
 *         tasks.offer(new Runnable() {
 *             public void run() {
 *                 try {
 *                     r.run();
 *                 } finally {
 *                     scheduleNext();
 *                 }
 *             }
 *         });
 *         if (active == null) {
 *             scheduleNext();
 *         }
 *     }
 *
 *     protected synchronized void scheduleNext() {
 *         if ((active = tasks.poll()) != null) {
 *             executor.execute(active);
 *         }
 *     }
 * }
 *
 * The Executor implementations provided in this package
 * implement ExecutorService, which is a more extensive interface.
 */
public interface Executor {

    /**
     * Executes the given command at some time in the future. The command
     * may execute in a new thread, in a pooled thread, or in the
     * calling thread, at the discretion of the Executor implementation.
     *
     * @param command the runnable task
     * @throws java.util.concurrent.RejectedExecutionException
     *  if this task can't accepted for execution.
     * @throws NullPointerException if command is null
     */
    void execute(Runnable command);
}
